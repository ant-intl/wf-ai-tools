# Query a Beneficiary 接口接入指引

## 接口说明

根据收款人 ID 查询完整详情，包括状态、账户信息和银行/钱包详情。

## 官方文档

- [query_a_beneficiary 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/query_a_beneficiary)

## 请求地址

`POST /api/open/v1/beneficiaries/query`

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | string | Yes | 收款人 ID |

### 请求示例

```json
{
  "id": "20260412123235345"
}
```

## 响应参数

返回完整的 Beneficiary 对象（与 create_a_beneficiary 响应结构相同）。

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果，见 [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result) |
| `id` | string | 收款人唯一标识，用于后续 update/delete/payout 操作 |
| `status` | string | 状态，见 [BeneficiaryStatus](https://docs.worldfirst.com/wfdocs/api-sdk/beneficiaries_overview)，仅 `ACTIVE` 状态可用于代发 |
| `region` | string | 地区（ISO 3166 两位字母代码） |
| `accountType` | string | 账户类型：`BANK_ACCOUNT`、`DIGITAL_WALLET` |
| `entityType` | string | 实体类型：`COMPANY`、`PERSONAL` |
| `relationType` | string | 关系类型：`SAME_NAME`、`THIRD_PARTY`、`RELATED_MERCHANT` |
| `currency` | string | 账户币种（ISO 4217 三字母代码） |
| `paymentType` | string | 支付方式：`LOCAL`、`CROSS` |
| `nickname` | string | 显示名称 |
| `phone` | string | 联系电话 |
| `email` | string | 联系邮箱 |
| `address` | string | 街道地址 |
| `city` | string | 城市 |
| `bankDetails` | BankDetail | 银行账户详情（`accountType` 为 `BANK_ACCOUNT` 时返回） |
| `walletDetails` | WalletDetail | 钱包账户详情（`accountType` 为 `DIGITAL_WALLET` 时返回） |
| `createdAt` | datetime | 创建时间（ISO 8601） |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success.",
    "resultStatus": "S"
  },
  "id": "20260412123235345",
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

| resultCode | resultStatus | 说明 | 排查建议 |
|------------|--------------|------|----------|
| `SUCCESS` | S | 成功 | — |
| `PARAM_ILLEGAL` | F | 参数非法 | 确认 `id` 参数不为空且不超过 64 字符 |
| `BENEFICIARY_NOT_FOUND` | F | 收款人不存在 | 确认 `id` 正确，可使用 List Beneficiaries 查找正确的 ID |
| `PROCESS_FAIL` | F | 业务处理失败，请勿重试 | 联系 WorldFirst 技术支持 |
| `UN_SUPPORT_BUSINESS` | F | 不支持的业务 | 确认参数值在支持范围内后重试 |
| `USER_NO_PERMISSION` | F | 无权限 | 使用有权限的账户重试 |
| `INVALID_CLIENT` | F | 客户端无效 | 确认 Client ID 存在且有效 |
| `USER_NOT_EXIST` | F | 用户不存在 | 使用有效的用户凭证重试 |

## 示例代码

参考 [references/payouts/beneficiaries/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
beneficiaries/java/
├── service/
│   └── BeneficiaryService.java              # 薄封装 Service，包含 queryBeneficiary 方法
└── model/
    ├── domain/
    │   ├── Beneficiary.java                 # 收款人对象
    │   ├── BankDetail.java                  # 银行账户详情
    │   └── WalletDetail.java                # 钱包账户详情
    ├── request/
    │   └── QueryBeneficiaryRequest.java     # 查询请求
    └── response/
        └── BeneficiaryResponse.java         # 响应结果
```

## 集成使用方式

BeneficiaryService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `QueryBeneficiaryRequest` 设置收款人 ID
2. 调用 `BeneficiaryService.queryBeneficiary(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理收款人数据

### 业务代码示例

```java
// 构造请求
QueryBeneficiaryRequest request = new QueryBeneficiaryRequest();
request.setId("20260412123235345");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
BeneficiaryResponse response = beneficiaryService.queryBeneficiary(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("收款人ID: " + response.getId());
    System.out.println("状态: " + response.getStatus());
} else {
    System.err.println("查询失败: " + response.getResult().getResultMessage());
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 BeneficiaryService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new BeneficiaryService(config)` 创建实例。
