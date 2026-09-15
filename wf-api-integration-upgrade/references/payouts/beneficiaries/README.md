# 收款人（Beneficiaries）模块

## 官方文档

- [WorldFirst 开发者文档 - Beneficiaries](https://docs.worldfirst.com/wfdocs/api-sdk/beneficiaries_overview)

## 接口列表

| 接口 | 目录 | 说明 | Endpoint |
|------|------|------|----------|
| 查询字段模板 | `query-beneficiary-template/GUIDE.md` | 查询指定地区/币种/账户类型的收款人字段模板 | `POST /api/open/v1/beneficiaries/queryTemplate` |
| 创建收款人 | `create-a-beneficiary/GUIDE.md` | 注册新的收款人（银行账户或数字钱包） | `POST /api/open/v1/beneficiaries/create` |
| 查询收款人 | `query-a-beneficiary/GUIDE.md` | 根据 ID 查询收款人详情 | `POST /api/open/v1/beneficiaries/query` |
| 列表查询 | `list-beneficiaries/GUIDE.md` | 分页查询收款人列表 | `POST /api/open/v1/beneficiaries/list` |
| 更新收款人 | `update-a-beneficiary/GUIDE.md` | 更新已激活收款人信息 | `POST /api/open/v1/beneficiaries/update` |
| 删除收款人 | `delete-a-beneficiary/GUIDE.md` | 永久删除收款人 | `POST /api/open/v1/beneficiaries/delete` |
| 校验收款人 | `validate-a-beneficiary/GUIDE.md` | 预校验收款人信息（不实际创建） | `POST /api/open/v1/beneficiaries/validate` |

## BeneficiaryService 说明

`BeneficiaryService` 是收款人模块的服务入口，采用**薄封装模式**：

- 不做参数校验（由调用方或 WF 服务端负责）
- 验签后自动反序列化响应体，直接返回业务响应对象
- 自动注入签名与验签（由底层 `WfApiClient` 完成，验签失败抛 `WfException`）

### 方法列表

| 方法 | 请求类型 | 响应类型 | 说明 |
|------|----------|----------|------|
| `queryBeneficiaryTemplate(QueryBeneficiaryTemplateRequest)` | `QueryBeneficiaryTemplateRequest` | `QueryBeneficiaryTemplateResponse` | 查询字段模板 |
| `createBeneficiary(CreateBeneficiaryRequest)` | `CreateBeneficiaryRequest` | `BeneficiaryResponse` | 创建收款人 |
| `queryBeneficiary(QueryBeneficiaryRequest)` | `QueryBeneficiaryRequest` | `BeneficiaryResponse` | 查询收款人 |
| `listBeneficiaries(ListBeneficiariesRequest)` | `ListBeneficiariesRequest` | `ListBeneficiariesResponse` | 列表查询 |
| `updateBeneficiary(UpdateBeneficiaryRequest)` | `UpdateBeneficiaryRequest` | `BeneficiaryResponse` | 更新收款人 |
| `deleteBeneficiary(DeleteBeneficiaryRequest)` | `DeleteBeneficiaryRequest` | `BeneficiaryResponse` | 删除收款人 |
| `validateBeneficiary(ValidateBeneficiaryRequest)` | `ValidateBeneficiaryRequest` | `ValidateBeneficiaryResponse` | 校验收款人 |

### 构造器

| 构造器 | 说明 |
|--------|------|
| `BeneficiaryService(WfClientConfig config)` | 传入配置，内部创建 `WfApiClient` |
| `BeneficiaryService(WfApiClient apiClient)` | 传入已构造的 `WfApiClient`，便于测试注入 |

## 对接流程

### 推荐接入顺序

1. 查询字段模板 → 了解需要的字段
2. 创建收款人 → 获取 beneficiaryId
3. 查询收款人 → 确认收款人状态（需达到 ACTIVE）
4. 列表查询 → 管理已创建的收款人
5. 更新/删除/校验 → 按需操作

## 注意事项

- 创建收款人后状态为 `PROCESSING`，需通过风控审核后变为 `ACTIVE` 才可用于代发
- `accountType` 决定需要提供 `bankDetails`（BANK_ACCOUNT）还是 `walletDetails`（DIGITAL_WALLET）
- 删除操作不可逆，删除后的 ID 不可在代发请求中复用
- 仅 `ACTIVE` 状态的收款人可更新
- 建议先调用 `validate` 接口校验信息完整性，再调用 `create` 实际创建

## 枚举参考

### BeneficiaryStatus

| 值 | 说明 |
|----|------|
| `PROCESSING` | 创建中，等待风控审核 |
| `ACTIVE` | 已激活，可用于代发 |
| `REJECTED` | 审核拒绝，检查 failureReason 后重新创建 |
| `DELETED` | 已删除，ID 不可复用 |

### AccountType

| 值 | 说明 |
|----|------|
| `BANK_ACCOUNT` | 银行账户，需提供 bankDetails |
| `DIGITAL_WALLET` | 数字钱包，需提供 walletDetails |

### EntityType

| 值 | 说明 |
|----|------|
| `COMPANY` | 企业 |
| `PERSONAL` | 个人 |

### RelationType

| 值 | 说明 |
|----|------|
| `SAME_NAME` | 同名账户 |
| `THIRD_PARTY` | 第三方 |
| `RELATED_MERCHANT` | 关联商户 |

## 文件结构

```
beneficiaries/
├── README.md
├── java/
│   ├── service/
│   │   └── BeneficiaryService.java              # 收款人服务（薄封装）
│   └── model/
│       ├── domain/
│       │   ├── Beneficiary.java                 # 收款人对象
│       │   ├── BankDetail.java                  # 银行账户详情
│       │   ├── WalletDetail.java                # 钱包账户详情
│       │   ├── BeneficiaryTemplate.java          # 字段模板（包含 fields 列表）
│       │   ├── TemplateField.java               # 模板字段（含校验规则）
│       │   ├── TemplateOption.java              # 字段选项（枚举值）
│       │   └── ValidationError.java             # 校验错误信息（validate 响应）
│       ├── request/
│       │   ├── CreateBeneficiaryRequest.java    # 创建请求
│       │   ├── QueryBeneficiaryRequest.java     # 查询请求
│       │   ├── ListBeneficiariesRequest.java    # 列表查询请求
│       │   ├── UpdateBeneficiaryRequest.java    # 更新请求
│       │   ├── DeleteBeneficiaryRequest.java    # 删除请求
│       │   ├── ValidateBeneficiaryRequest.java  # 校验请求
│       │   └── QueryBeneficiaryTemplateRequest.java # 模板查询请求
│       └── response/
│           ├── BeneficiaryResponse.java         # 单条响应（create/query/update/delete）
│           ├── ListBeneficiariesResponse.java   # 列表响应
│           ├── QueryBeneficiaryTemplateResponse.java # 模板响应
│           └── ValidateBeneficiaryResponse.java  # 校验响应（含 validationErrors）
├── query-beneficiary-template/
│   └── GUIDE.md
├── create-a-beneficiary/
│   └── GUIDE.md
├── query-a-beneficiary/
│   └── GUIDE.md
├── list-beneficiaries/
│   └── GUIDE.md
├── update-a-beneficiary/
│   └── GUIDE.md
├── delete-a-beneficiary/
│   └── GUIDE.md
└── validate-a-beneficiary/
    └── GUIDE.md
```
