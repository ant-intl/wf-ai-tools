# Create a Beneficiary 接口接入指引

## 接口说明

注册新的付款收款人（银行账户或数字钱包）。创建后生成唯一 beneficiaryId，可用于后续代发请求。

## 官方文档

- [create_a_beneficiary 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_beneficiary)

## 请求地址

`POST /api/open/v1/beneficiaries/create`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601 |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `accountType` | string | Yes | 账户类型：`BANK_ACCOUNT`、`DIGITAL_WALLET` |
| `entityType` | string | Yes | 实体类型：`COMPANY`、`PERSONAL` |
| `relationType` | string | Yes | 关系类型：`SAME_NAME`、`THIRD_PARTY`、`RELATED_MERCHANT` |
| `region` | string | Yes | 收款方地区（ISO 3166） |
| `currency` | string | Yes | 账户币种（ISO 4217） |
| `paymentType` | string | No | 支付方式：`LOCAL`、`CROSS` |
| `nickname` | string | No | 显示名称（最多 70 字符） |
| `phone` | string | No | 联系电话（最多 64 字符） |
| `email` | string | No | 联系邮箱（最多 64 字符） |
| `address` | string | No | 街道地址（最多 256 字符） |
| `city` | string | No | 城市（最多 64 字符） |
| `referenceBeneficiaryId` | string | No | 内部参考 ID（最多 64 字符） |
| `serviceCategory` | string | No | 业务类别 |
| `bankDetails` | BankDetail | No | 银行账户详情（accountType 为 BANK_ACCOUNT 时必填） |
| `walletDetails` | WalletDetail | No | 钱包账户详情（accountType 为 DIGITAL_WALLET 时必填） |

### BankDetail 主要字段

| Field | Type | Description |
|-------|------|-------------|
| `accountHolderName` | UserName | 账户持有人合法姓名 |
| `accountNumber` | string | 银行账号 |
| `iban` | string | IBAN |
| `bankBIC` | string | SWIFT/BIC 代码 |
| `routingNumber` | string | 本地清算路由号 |
| `bankName` | string | 银行名称 |
| `beneficiaryRegion` | string | 收款方国家/地区代码 |
| `beneficiaryDepositType` | string | 存款类型：`CC`（活期）、`CP`（定期） |

### WalletDetail 字段

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `accountHolderName` | UserName | Yes | 钱包账户持有人姓名 |
| `walletBrandName` | string | Yes | 钱包品牌（OVO、GOPAY、ALIPAY 等） |
| `accountNumber` | string | No | 钱包账号或关联手机号/邮箱 |
| `walletAccountType` | string | No | 钱包账户类型（WORLDFIRST、ALIPAY） |
| `iban` | string | No | 关联 IBAN |

### 请求示例

```json
{
  "region": "US",
  "currency": "USD",
  "nickname": "Supplier A",
  "paymentType": "LOCAL",
  "accountType": "BANK_ACCOUNT",
  "entityType": "PERSONAL",
  "relationType": "THIRD_PARTY",
  "bankDetails": {
    "beneficiaryRegion": "US",
    "accountNumber": "50001121",
    "routingNumber": "021000021",
    "bankName": "JPMorgan Chase Bank, NA"
  }
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果 |
| `id` | string | 收款人唯一标识 |
| `status` | string | 状态：`PROCESSING`、`ACTIVE`、`REJECTED` |
| `region` | string | 地区 |
| `accountType` | string | 账户类型 |
| `entityType` | string | 实体类型 |
| `relationType` | string | 关系类型 |
| `currency` | string | 币种 |
| `paymentType` | string | 支付方式 |
| `nickname` | string | 显示名称 |
| `bankDetails` | BankDetail | 银行账户详情 |
| `walletDetails` | WalletDetail | 钱包账户详情 |
| `createdAt` | datetime | 创建时间 |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success.",
    "resultStatus": "S"
  },
  "id": "202604011001010101",
  "status": "ACTIVE",
  "createdAt": "2026-03-30T12:08:56+08:00",
  "currency": "USD",
  "region": "US",
  "nickname": "Supplier A",
  "paymentType": "LOCAL",
  "accountType": "BANK_ACCOUNT",
  "entityType": "PERSONAL",
  "relationType": "THIRD_PARTY",
  "bankDetails": {
    "beneficiaryRegion": "US",
    "accountNumber": "50001121",
    "routingNumber": "021000021",
    "bankName": "JPMorgan Chase Bank, NA"
  }
}
```

## 错误码

| resultCode | resultStatus | 说明 |
|------------|--------------|------|
| `SUCCESS` | S | 成功 |
| `PARAM_ILLEGAL` | F | 参数非法 |
| `CONTRACT_CHECK_FAIL` | F | 合约校验失败 |
| `RISK_REJECT` | F | 风控拒绝 |
| `BENEFICIARY_ALREADY_EXISTED` | F | 收款人已存在 |
| `USER_NO_PERMISSION` | F | 无权限 |

## 示例代码

参考 [references/payouts/beneficiaries/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
beneficiaries/java/
├── service/
│   └── BeneficiaryService.java              # 薄封装 Service，包含 createBeneficiary 方法
└── model/
    ├── domain/
    │   ├── Beneficiary.java                 # 收款人对象
    │   ├── BankDetail.java                  # 银行账户详情
    │   └── WalletDetail.java                # 钱包账户详情
    ├── request/
    │   └── CreateBeneficiaryRequest.java    # 创建请求
    └── response/
        └── BeneficiaryResponse.java         # 响应结果
```

## 集成使用方式

BeneficiaryService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `CreateBeneficiaryRequest` 设置 accountType、entityType、relationType、region、currency 和 bankDetails/walletDetails
2. 调用 `BeneficiaryService.createBeneficiary(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理收款人数据

### 业务代码示例

```java
// 构造请求
CreateBeneficiaryRequest request = new CreateBeneficiaryRequest();
request.setAccountType("BANK_ACCOUNT");
request.setEntityType("PERSONAL");
request.setRelationType("THIRD_PARTY");
request.setRegion("US");
request.setCurrency("USD");
request.setPaymentType("LOCAL");
request.setNickname("Supplier A");

BankDetail bankDetail = new BankDetail();
bankDetail.setBeneficiaryRegion("US");
bankDetail.setAccountNumber("50001121");
bankDetail.setRoutingNumber("021000021");
bankDetail.setBankName("JPMorgan Chase Bank, NA");
request.setBankDetails(bankDetail);

// 调用 Service（内部已强制验签，验签失败抛 WfException）
BeneficiaryResponse response = beneficiaryService.createBeneficiary(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("收款人ID: " + response.getId());
    System.out.println("状态: " + response.getStatus());
} else {
    System.err.println("创建失败: " + response.getResult().getResultMessage());
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 BeneficiaryService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new BeneficiaryService(config)` 创建实例。
