# Consult a Payout 接口接入指引

## 接口说明

预校验代发并获取手续费和汇率估算。不实际移动资金。用于验证路由可行性、预览费用、获取汇率报价 ID 以锁定汇率。

## 官方文档

- [consult_a_payout 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/consult_a_payout)

## 请求地址

`POST /api/open/v1/payouts/consult`

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
| `paymentType` | string | No | 支付通道类型，需与 `paymentNetwork` 配合使用。可选值：`LOCAL`（本地清算，如 ACH、SEPA、Faster Payments）、`CROSS`（跨境 SWIFT 电汇） |
| `paymentNetwork` | string | No | 具体清算网络，需与 `paymentType` 配合使用。参见 [PaymentNetwork](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview) |
| `beneficiary` | Beneficiary | Yes | 收款方（受益人）信息 |
| `payer` | Payer | No | 付款方（汇款人）信息。默认使用已入驻账户信息。当指定 On-Behalf-Of 代发偏好时必填 |
| `sourceAmount` | Amount | No | 从付款方账户扣款的源币种金额。`sourceAmount` 和 `payoutAmount` 均需指定 `currency`，但仅可在一个金额对象中指定 `value`。参见 [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types) |
| `payoutAmount` | Amount | No | 收款方收到的目标币种金额。`sourceAmount` 和 `payoutAmount` 均需指定 `currency`，但仅可在一个金额对象中指定 `value`。参见 [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types) |
| `businessSceneCode` | string | No | 标识代发类型的业务场景码。参见 [BusinessSceneCode](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview) |
| `purposeCode` | string | Yes | 付款用途码。参见 [PurposeCode](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview) |
| `fvtPreference` | string | No | 全额到账偏好。指定后请求保证收款方收到全额（中间行手续费由付款方承担）。可选值：`NEED_FVT`（请求全额到账） |
| `oboPreference` | string | No | On-Behalf-Of 代发路由偏好。设为 `NEED_OBO` 时，WorldFirst 将指定的付款方信息转发给收款银行。注意 WorldFirst 无法控制付款方信息在银行对账单上的显示方式，对任何差异不承担责任。可选值：`NEED_OBO`（以指定付款方名义代发，仅适用于支持 OBO 的渠道）、`NO_OBO`（无 On-Behalf-Of 路由偏好） |
| `reference` | string | No | 银行转账附言。可能出现在收款方银行对账单上，具体取决于当地清算系统和收款银行 |
| `transferMemo` | string | No | 内部转账备注，仅供商户自己记录使用，收款方不可见 |

### 请求示例

```json
{
  "businessSceneCode": "THIRD_PARTY_PAYOUT",
  "paymentType": "LOCAL",
  "paymentNetwork": "ACH",
  "payer": {
    "region": "HK",
    "phone": "176xxxxxxxx",
    "email": "123@gmail.com",
    "name": {
      "firstName": "Lebron",
      "lastName": "James",
      "fullName": "Lebron James"
    },
    "address": {
      "address1": "xxxxxx",
      "address2": "xxxxx2",
      "zipCode": "4001001"
    }
  },
  "beneficiary": {
    "region": "HK",
    "phone": "176xxxxxxxx",
    "email": "123@gmail.com",
    "beneficiaryId": "xxxxxxxxxx"
  },
  "sourceAmount": { "currency": "HKD" },
  "payoutAmount": { "currency": "USD", "value": 10000 },
  "purposeCode": "GDS",
  "fvtPreference": "NEED_FVT",
  "oboPreference": "NEED_OBO",
  "reference": "pay to xxx",
  "transferMemo": "rest memo"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果，参见 [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result) |
| `businessSceneCode` | string | 应用于此代发的业务场景码。参见 [BusinessSceneCode](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview) |
| `paymentType` | string | 本次代发解析出的支付通道类型。可选值：`LOCAL`（本地清算）、`CROSS`（跨境 SWIFT 电汇） |
| `paymentNetwork` | string | 本次代发解析出的具体清算网络。参见 [PaymentNetwork](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview) |
| `transferQuote` | Quote | 跨币种代发的汇率报价。需要货币兑换时返回。将 `quoteId` 传入 create_a_payout 可锁定此汇率 |
| `payer` | Payer | 付款方信息 |
| `beneficiary` | Beneficiary | 收款方信息 |
| `sourceAmount` | Amount | 从付款方账户扣款的源币种金额。参见 [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types) |
| `payoutAmount` | Amount | 收款方收到的目标币种金额。参见 [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types) |
| `feeAmount` | Amount | 本次代发的总手续费。参见 [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types) |
| `feeItemList` | array[FeeItem] | 手续费明细。当存在多个费用组成部分时返回 |
| `originalFeeRate` | string | 促销折扣前的原始费率 |
| `feeRate` | string | 应用促销折扣后的实际费率 |
| `originalFeeAmount` | Amount | 促销折扣前的原始手续费。参见 [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types) |
| `discountFeeAmount` | Amount | 促销减免金额。应用促销时返回。参见 [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types) |
| `availableQuota` | Amount | 剩余结汇额度。代发涉及结汇时返回。参见 [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types) |
| `promotionList` | array[Promotion] | 应用于本次代发的有效促销列表 |
| `purposeCode` | string | 应用的付款用途码。参见 [PurposeCode](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview) |
| `fvtPreference` | string | 应用的全额到账偏好 |
| `oboPreference` | string | 应用的 On-Behalf-Of 路由偏好 |
| `reference` | string | 银行转账附言 |
| `transferMemo` | string | 内部转账备注 |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "businessSceneCode": "THIRD_PARTY_PAYOUT",
  "paymentType": "LOCAL",
  "paymentNetwork": "ACH",
  "transferQuote": {
    "quoteId": "12345678910xxx",
    "currencyPair": "HKD/USD",
    "clientRate": "7.892342342",
    "effectiveAt": "2025-04-19T13:08:56Z",
    "expiresAt": "2026-04-19T13:08:56Z"
  },
  "payer": {
    "region": "HK",
    "phone": "176xxxxxxxx",
    "email": "123@gmail.com",
    "name": {
      "firstName": "Lebron",
      "lastName": "James",
      "fullName": "Lebron James"
    },
    "address": {
      "address1": "xxxxxx",
      "address2": "xxxxx2",
      "zipCode": "4001001"
    }
  },
  "beneficiary": {
    "region": "HK",
    "phone": "176xxxxxxxx",
    "email": "123@gmail.com",
    "beneficiaryId": "xxxxxxxxxx"
  },
  "sourceAmount": { "currency": "HKD", "value": 78900 },
  "payoutAmount": { "currency": "USD", "value": 10000 },
  "feeAmount": { "currency": "USD", "value": 100 },
  "purposeCode": "GDS",
  "fvtPreference": "NEED_FVT",
  "oboPreference": "NEED_OBO",
  "reference": "pay to xxx",
  "transferMemo": "rest memo"
}
```

## 错误码

| resultCode | resultStatus | resultMessage | 排查建议 |
|------------|--------------|---------------|----------|
| `SUCCESS` | S | Success | — |
| `PARAM_ILLEGAL` | F | Illegal parameters exist. | 对照 API 文档检查请求头和参数 |
| `UNKNOWN_EXCEPTION` | U | The API call failed due to an unknown error. | 服务端错误，稍后重试。如问题持续，联系 WorldFirst 技术支持 |
| `UN_SUPPORT_BUSINESS` | F | Unsupported business. | 验证 `businessSceneCode` 值并确保币种组合受支持 |
| `CURRENCY_NOT_SUPPORTED` | F | The currency is not supported. | 确认 `sourceAmount` 和 `payoutAmount` 中的币种代码正确 |
| `USER_NOT_EXIST` | F | The user does not exist. | 使用有效的用户凭证重试 |
| `USER_STATUS_ABNORMAL` | F | The status of the user is abnormal. | 使用状态正常的其他用户重试 |
| `USER_NO_PERMISSION` | F | User does not have permission. | 确保用户具有此代发类型所需的权限 |
| `CONTRACT_CHECK_FAIL` | F | The contract check has failed. | 验证合约状态后重试 |
| `WALLET_TEMPLATE_NOT_EXIST` | F | Wallet template does not exist. | 此代发类型的钱包模板不存在，检查 `beneficiary.walletDetails.walletBrandName` |
| `WALLET_INFO_ABNORMAL` | F | Wallet info abnormal. | 检查 `beneficiary.walletDetails` 字段并使用有效的钱包信息重试 |
| `BENEFICIARY_NOT_EXIST` | F | Beneficiary does not exist. | 确保 `beneficiary.beneficiaryId` 指向已注册的收款人 |
| `CARD_INFO_ABNORMAL` | F | Card information is abnormal. | 检查 `beneficiary.bankDetails` 字段并使用有效的账户信息重试 |

## 示例代码

参考 [references/payouts/payouts/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
payouts/java/
├── service/
│   └── PayoutService.java                 # 薄封装 Service，包含 consultPayout 方法
└── model/
    ├── domain/
    │   ├── Payout.java                    # 代发基础对象
    │   ├── Beneficiary.java         # 代发收款方信息（含 bankDetails/walletDetails）
    │   ├── Payer.java              # 代发付款方信息（引用 common.UserName / common.Address）
    │   ├── BankDetail.java               # 收款方银行账户详情
    │   ├── WalletDetail.java             # 收款方电子钱包账户详情
    │   ├── AdditionalInfo.java           # 附加业务信息
    │   ├── Quote.java                    # 汇率报价
    │   ├── FeeItem.java                  # 手续费明细项（引用 common.Amount）
    │   └── Promotion.java                # 促销信息
    # 说明：Amount / Address / UserName 定义在 common 模块
    ├── request/
    │   └── ConsultPayoutRequest.java     # 咨询代发请求
    └── response/
        └── PayoutResponse.java           # 代发响应
```

## 集成使用方式

PayoutService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `ConsultPayoutRequest` 设置 paymentType、beneficiary、金额和 purposeCode
2. 调用 `PayoutService.consultPayout(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，提取 `transferQuote.quoteId` 用于后续 create_a_payout

### 业务代码示例

```java
// 构造请求
ConsultPayoutRequest request = new ConsultPayoutRequest();
request.setBusinessSceneCode("THIRD_PARTY_PAYOUT");
request.setPaymentType("LOCAL");
request.setPaymentNetwork("ACH");
request.setPurposeCode("GDS");

Beneficiary beneficiary = new Beneficiary();
beneficiary.setBeneficiaryId("202604011001010101");
request.setBeneficiary(beneficiary);

request.setPayoutAmount(new Amount("USD", 10000));
request.setSourceAmount(new Amount("HKD", null));

// 调用 Service（内部已强制验签，验签失败抛 WfException）
PayoutResponse response = payoutService.consultPayout(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    String quoteId = response.getTransferQuote().getQuoteId();
    // 将 quoteId 传入 createPayout 的 transferQuote.quoteId 锁定汇率
} else {
    System.err.println("咨询失败: " + response.getResult().getResultMessage());
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 PayoutService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new PayoutService(config)` 创建实例。
