# Create a Payout 接口接入指引

## 接口说明

发起代发，将资金发送到收款方银行账户或电子钱包。支持同币种和跨币种代发。

## 官方文档

- [create_a_payout 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_payout)

## 请求地址

`POST /api/open/v1/payouts/create`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2024-01-01T12:08:56+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `requestId` | string | Yes | 幂等键，唯一请求标识（如 UUID） |
| `paymentType` | string | No | 支付通道类型：`LOCAL` 或 `CROSS` |
| `paymentNetwork` | string | No | 具体清算网络，如 `ACH`、`SEPA`、`SWIFT` |
| `beneficiary` | Beneficiary | Yes | 收款方信息，提供 `beneficiaryId`、`bankDetails` 或 `walletDetails` 之一 |
| `payer` | Payer | No | 付款方信息，OBO 场景必填 |
| `sourceAmount` | Amount | No | 源币种金额（扣款金额），与 `payoutAmount.value` 二选一 |
| `payoutAmount` | Amount | No | 目标币种金额（收款金额），与 `sourceAmount.value` 二选一 |
| `transferQuote` | Quote | No | 汇率报价，跨币种代发时传入 `consult_a_payout` 返回的 `quoteId` |
| `businessSceneCode` | string | No | 业务场景码 |
| `purposeCode` | string | Yes | 付款用途码：`GDS`、`TXS`、`ACM`、`GST`、`COM`、`TOA`、`SAL` |
| `fvtPreference` | string | No | 全额到账偏好：`NEED_FVT` |
| `oboPreference` | string | No | 代发偏好：`NEED_OBO`、`NO_OBO` |
| `reference` | string | No | 银行转账附言 |
| `transferMemo` | string | No | 内部转账备注 |
| `additionalInfo` | object | No | 附加业务信息（最多 10 个键值对） |

### 请求示例

```json
{
  "requestId": "CTS123456",
  "businessSceneCode": "THIRD_PARTY_PAYOUT",
  "paymentType": "LOCAL",
  "paymentNetwork": "ACH",
  "beneficiary": {
    "region": "HK",
    "beneficiaryId": "xxxxxxxxxx"
  },
  "sourceAmount": { "currency": "HKD" },
  "payoutAmount": { "currency": "USD", "value": 10000 },
  "purposeCode": "GDS",
  "reference": "pay to xxx"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果 |
| `id` | string | 代发单唯一标识 |
| `status` | string | 代发状态：`PROCESSING`、`SUCCESS`、`FAIL`、`RETURN` |
| `failureCode` | string | 失败原因码（`status=FAIL` 时返回） |
| `failureMessage` | string | 失败原因描述（`status=FAIL` 时返回） |
| `paymentType` | string | 支付通道类型 |
| `paymentNetwork` | string | 清算网络 |
| `payer` | Payer | 付款方信息 |
| `beneficiary` | Beneficiary | 收款方信息 |
| `sourceAmount` | Amount | 源币种金额 |
| `payoutAmount` | Amount | 目标币种金额 |
| `transferQuote` | Quote | 汇率报价（跨币种时返回） |
| `feeAmount` | Amount | 总手续费 |
| `feeItemList` | array[FeeItem] | 手续费明细 |
| `purposeCode` | string | 付款用途码 |
| `reference` | string | 银行转账附言 |
| `transferMemo` | string | 内部转账备注 |

### 响应示例

```json
{
  "result": {
    "resultCode": "PROCESSING",
    "resultMessage": "request is processing.",
    "resultStatus": "S"
  },
  "id": "20260401xxxxx",
  "status": "PROCESSING",
  "businessSceneCode": "THIRD_PARTY_PAYOUT",
  "paymentType": "LOCAL",
  "paymentNetwork": "ACH",
  "payoutAmount": { "currency": "USD", "value": 10000 },
  "feeAmount": { "currency": "USD", "value": 100 },
  "purposeCode": "GDS",
  "reference": "pay to xxx"
}
```

## 错误码

| resultCode | resultStatus | 说明 | 排查建议 |
|------------|--------------|------|----------|
| `SUCCESS` | S | 成功 | — |
| `PROCESSING` | S | 处理中 | 代发异步处理，通过 query_a_payout 轮询最终状态 |
| `PARAM_ILLEGAL` | F | 参数非法 | 检查 requestId、purposeCode 等必填字段 |
| `PROCESS_FAIL` | F | 业务失败 | 不可重试，检查 failureCode |
| `UNKNOWN_EXCEPTION` | U | 未知异常 | 可重试，使用相同 requestId |
| `REPEAT_REQ_INCONSISTENT` | F | 重复请求不一致 | 使用新的 requestId |
| `BALANCE_NOT_ENOUGH` | F | 余额不足 | 确保账户有足够余额 |
| `AMOUNT_EXCEED_LIMIT` | F | 金额超限 | 降低代发金额 |
| `BENEFICIARY_NOT_EXIST` | F | 收款人不存在 | 检查 beneficiaryId |
| `CURRENCY_NOT_SUPPORT` | F | 币种不支持 | 检查 sourceAmount 和 payoutAmount 币种 |

## 示例代码

参考 [references/payouts/payouts/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
payouts/java/
├── service/
│   └── PayoutService.java                 # 薄封装 Service，包含 createPayout 方法
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
    │   └── CreatePayoutRequest.java      # 创建代发请求
    └── response/
        └── PayoutResponse.java           # 代发响应
```

## 集成使用方式

PayoutService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `CreatePayoutRequest` 设置 requestId、purposeCode、beneficiary 和金额
2. 调用 `PayoutService.createPayout(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理代发单数据

### 业务代码示例

```java
// 构造请求
CreatePayoutRequest request = new CreatePayoutRequest();
request.setRequestId(UUID.randomUUID().toString());
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
PayoutResponse response = payoutService.createPayout(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("代发单ID: " + response.getId());
    System.out.println("状态: " + response.getStatus());
} else {
    System.err.println("创建失败: " + response.getResult().getResultMessage());
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 PayoutService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new PayoutService(config)` 创建实例。
