# Update a Beneficiary 接口接入指引

## 接口说明

更新已激活（`ACTIVE`）收款人的信息。仅需传入要修改的字段，省略的字段保持不变。

## 官方文档

- [update_a_beneficiary 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/update_a_beneficiary)

## 请求地址

`POST /api/open/v1/beneficiaries/update`

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | string | Yes | 收款人 ID |
| `nickname` | string | No | 新的显示名称 |
| `paymentTypeList` | array[string] | No | 设置支持的支付方式列表：`LOCAL`、`CROSS` |
| `phone` | string | No | 新的联系电话 |
| `email` | string | No | 新的联系邮箱（最多 64 字符） |
| `address` | string | No | 新的街道地址（最多 256 字符） |
| `city` | string | No | 新的城市 |
| `bankDetails` | BankDetail | No | 更新的银行账户详情（仅需修改银行信息时传入） |
| `walletDetails` | WalletDetail | No | 更新的钱包账户详情（仅需修改钱包信息时传入） |

### 请求示例

```json
{
  "id": "20260401123123124",
  "paymentTypeList": ["LOCAL"],
  "bankDetails": {
    "accountNumber": "50001121",
    "routingNumber": "021000021",
    "bankName": "JPMorgan Chase Bank, NA"
  }
}
```

## 响应参数

返回更新后的完整 Beneficiary 对象。

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果，见 [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result) |
| `id` | string | 收款人唯一标识 |
| `status` | string | 状态，见 [BeneficiaryStatus](https://docs.worldfirst.com/wfdocs/api-sdk/beneficiaries_overview) |
| `region` | string | 地区（ISO 3166 两位字母代码） |
| `accountType` | string | 账户类型：`BANK_ACCOUNT`、`DIGITAL_WALLET` |
| `entityType` | string | 实体类型：`COMPANY`、`PERSONAL` |
| `relationType` | string | 关系类型：`SAME_NAME`、`THIRD_PARTY`、`RELATED_MERCHANT` |
| `currency` | string | 账户币种（ISO 4217 三字母代码） |
| `paymentType` | string | 更新后的支付方式：`LOCAL`、`CROSS` |
| `nickname` | string | 显示名称 |
| `phone` | string | 联系电话 |
| `email` | string | 联系邮箱 |
| `address` | string | 街道地址 |
| `city` | string | 城市 |
| `bankDetails` | BankDetail | 银行账户详情（`accountType` 为 `BANK_ACCOUNT` 时返回） |
| `walletDetails` | WalletDetail | 钱包账户详情（`accountType` 为 `DIGITAL_WALLET` 时返回） |
| `createdAt` | datetime | 原始创建时间（ISO 8601） |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "id": "20260401123123124",
  "status": "ACTIVE",
  "createdAt": "2026-03-30T12:08:56+08:00",
  "nickname": "Supplier A",
  "currency": "USD",
  "region": "US",
  "paymentType": "LOCAL",
  "accountType": "BANK_ACCOUNT",
  "entityType": "PERSONAL",
  "relationType": "THIRD_PARTY",
  "bankDetails": {
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
| `INVALID_PARAMETER` | F | 参数无效 | 检查请求参数格式和有效值 |
| `BENEFICIARY_NOT_FOUND` | F | 收款人不存在 | 确认 `id` 正确，可使用 List Beneficiaries 查找正确的 ID |
| `BENEFICIARY_STATUS_INVALID` | F | 收款人状态非 ACTIVE，无法更新 | 仅 `ACTIVE` 状态可更新，先查询确认当前状态 |
| `INVALID_BANK_CODE` | F | 银行代码未注册 | 确认银行路由号或 BIC 有效且受支持 |

## 示例代码

参考 [references/payouts/beneficiaries/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
beneficiaries/java/
├── service/
│   └── BeneficiaryService.java              # 薄封装 Service，包含 updateBeneficiary 方法
└── model/
    ├── domain/
    │   ├── Beneficiary.java                 # 收款人对象
    │   ├── BankDetail.java                  # 银行账户详情
    │   └── WalletDetail.java                # 钱包账户详情
    ├── request/
    │   └── UpdateBeneficiaryRequest.java    # 更新请求
    └── response/
        └── BeneficiaryResponse.java         # 响应结果
```

## 集成使用方式

BeneficiaryService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `UpdateBeneficiaryRequest` 设置收款人 ID 和需更新的字段
2. 调用 `BeneficiaryService.updateBeneficiary(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理更新后的收款人数据

### 业务代码示例

```java
// 构造请求
UpdateBeneficiaryRequest request = new UpdateBeneficiaryRequest();
request.setId("20260401123123124");
request.setPaymentTypeList(Arrays.asList("LOCAL"));

BankDetail bankDetail = new BankDetail();
bankDetail.setAccountNumber("50001121");
bankDetail.setRoutingNumber("021000021");
bankDetail.setBankName("JPMorgan Chase Bank, NA");
request.setBankDetails(bankDetail);

// 调用 Service（内部已强制验签，验签失败抛 WfException）
BeneficiaryResponse response = beneficiaryService.updateBeneficiary(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("更新成功，状态: " + response.getStatus());
} else {
    System.err.println("更新失败: " + response.getResult().getResultMessage());
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 BeneficiaryService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new BeneficiaryService(config)` 创建实例。
