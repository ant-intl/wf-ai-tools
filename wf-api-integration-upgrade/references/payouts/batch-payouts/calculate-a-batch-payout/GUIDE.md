# Calculate a Batch Payout

> 规格来源：[官方 calculate_a_batch_payout](https://docs.worldfirst.com/wfdocs/api-sdk/calculate_a_batch_payout)；核对日期：2026-11-18。
> 生成前先读 [模块 README](../README.md)。保留官方字段、大小写、必填条件及本接口错误表；不要由其他模块推断。

`POST /api/open/v1/batchPayouts/calculate`

Runs validation and pricing across every item in a batch payout and returns the aggregated FX quote. Call this endpoint on a batch in INIT status to preview indicative totals per currency pair and confirm that all items are valid before calling [Submit a Batch Payout](https://docs.worldfirst.com/wfdocs/api-sdk/submit_a_batch_payout.md). The returned totals, fees, and rates are for reference only — final amounts and exchange rates are calculated at real payout processing. Items flagged as INVALID must be corrected via [Remove Items from a Batch Payout](https://docs.worldfirst.com/wfdocs/api-sdk/remove_items_from_a_batch_payout.md) plus [Add Items to a Batch Payout](https://docs.worldfirst.com/wfdocs/api-sdk/add_items_to_a_batch_payout.md) before submission.

## Request Parameters

#### id (string, REQUIRED)

Unique identifier of the target batch payout.

## Response Parameters

#### result (Result)

See [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result.md).

#### id (string)

Unique identifier of the batch payout.

#### status (string)

Current batch status. Remains INIT after calculation. See [BatchPayoutStatus](https://docs.worldfirst.com/wfdocs/api-sdk/batch_payouts_overview.md#VrCNk).

#### createdAt (datetime)

Time the batch was created, in ISO 8601 extended format.

#### updatedAt (datetime)

Time the batch was most recently updated, in ISO 8601 extended format.

#### requestId (string)

Idempotency key supplied when the batch was created.

#### batchName (string)

Human-readable batch name, if provided at creation.

#### transferMemo (string)

Internal memo attached to the batch, if provided at creation.

#### businessSceneCode (string)

Business scene code set on the batch, if provided at creation. See [BusinessSceneCode](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#UqJiZ).

#### paymentNetwork (string)

Payment clearing network set on the batch, if provided at creation. See [PaymentNetwork](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#UFKUW).

#### paymentType (string)

Payment channel type set on the batch, if provided at creation. See [PaymentType](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md).

#### fvtPreference (string)

Full value transfer (FVT) preference set on the batch, if provided at creation. See [FvtPreference](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md).

#### oboPreference (string)

On-behalf-of (OBO) routing preference set on the batch, if provided at creation. See [OboPreference](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md).

#### totalItemCount (integer)

Total number of items currently in the batch. Range: 0 - 100.

#### validItemCount (integer)

Number of items that passed validation and are eligible for submission. Range: 0 - 100.

#### calculationSummary (CalculationSummary)

Latest reference pricing summary. Returned once calculate (or submit) has completed pricing. All totals and rates are indicative — final values are determined at payout processing.

##### lastCalculatedAt (datetime)

Time the batch was most recently priced, in ISO 8601 format. Indicates how recent the reference preview is.

##### currencyPairs (array)

Aggregated reference pricing per currency pair covered by the batch. Up to 10 entries.

###### currencyPair (string)

Currency pair in {sourceCurrency}/{payoutCurrency} format, composed of two ISO 4217 three-letter currency codes (for example, USD/EUR). See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

###### clientRate (decimal)

Indicative exchange rate applied to items in this currency pair for this reference preview. Note: the final rate is determined at payout processing. Up to 6 decimal places.

###### sourceAmount (Amount)

Total amount debited from the payer across all items in this currency pair. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

###### payoutAmount (Amount)

Total amount received by beneficiaries across all items in this currency pair. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

###### feeRate (string)

Actual service fee rate applied, after any promotional discount. Returned when a service fee is charged.

###### feeAmount (Amount)

Payout service fee for this currency pair, after any promotional discount. Returned when a service fee is charged. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

###### feeItemList (array)

Itemized breakdown of the service fee, with one entry per fee item when multiple fee items apply. Returned when a service fee is charged. Up to 10 entries.

**feeItemType (string)** — 路径：`calculationSummary.currencyPairs[].feeItemList[].feeItemType`

Fee item type. See [FeeItemType](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#gOAUt).

**feeItemAmount (Amount)** — 路径：`calculationSummary.currencyPairs[].feeItemList[].feeItemAmount`

Fee charged for this fee item. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

###### originalFeeRate (string)

Service fee rate before promotional discounts. Returned when a promotion applies. Compare with feeRate to see the discount.

###### originalFeeAmount (Amount)

Service fee before promotional discounts. Returned when a promotion applies and a service fee is charged. Compare with feeAmount to see the discount. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

###### promotionList (array)

Promotions applied to this currency pair. Returned when a promotion applies. Up to 10 entries.

**promotionName (string)** — 路径：`calculationSummary.currencyPairs[].promotionList[].promotionName`

Name of the promotion.

**promotionType (string)** — 路径：`calculationSummary.currencyPairs[].promotionList[].promotionType`

Type of the promotion.

-   `FEE_DISCOUNT`: Service fee discount.

**activeTime (datetime)** — 路径：`calculationSummary.currencyPairs[].promotionList[].activeTime`

Time the promotion takes effect, in ISO 8601 format.

**expiredTime (datetime)** — 路径：`calculationSummary.currencyPairs[].promotionList[].expiredTime`

Time the promotion expires, in ISO 8601 format.

## Error Codes

| resultCode | resultStatus | resultMessage | Troubleshooting |
| --- | --- | --- | --- |
| SUCCESS | S | Success | N/A |
| SYSTEM_ERROR | U | A system error occurred. | Retry after a short delay. If the issue persists, contact WorldFirst technical support. |
| PARAM_ILLEGAL | F | Illegal parameters exist. | Verify `id` is present and no longer than 64 characters. |
| UN_SUPPORT_BUSINESS | F | Unsupported business. | Verify the `businessSceneCode` value and ensure the currency combination is supported. |
| CONTRACT_NOT_EXIST | F | The contract does not exist. | Ensure the required payout contract is in place, then retry. |
| AUTHORIZATION_NOT_EXIST | F | The authorization does not exist. | Confirm the OAuth authorization is still active for the target account. |
| USER_NOT_EXIST | F | The user does not exist. | Verify the user identified by the request, then retry with correct user information. |
| USER_STATUS_ABNORMAL | F | The status of the user is abnormal. | Contact WorldFirst technical support to check the user status. |
| ACCOUNT_NOT_EXIST | F | The account does not exist. | Verify the payer account exists, then retry. |
| ACCOUNT_STATUS_ABNORMAL | F | The account status is abnormal. | Retry with an account in a valid status, or contact WorldFirst technical support. |
| BATCH_STATUS_INVALID | F | A quote can only be created in INIT status. | Confirm the batch is in `INIT` via \[Query a Batch Payout\](https://docs.worldfirst.com/wfdocs/api-sdk/query_a_batch_payout); submitted or terminated batches cannot be re-quoted. |
| BATCH_NOT_EXIST | F | The batch payout does not exist. | Confirm `id` matches a batch returned by \[Create a Batch Payout\](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_batch_payout). |
| BATCH_ITEM_EMPTY | F | No item in the batch payout. | Add at least one item using \[Add Items to a Batch Payout\](https://docs.worldfirst.com/wfdocs/api-sdk/add_items_to_a_batch_payout). |
| BATCH_HAS_INVALID_ITEM | F | An item in the batch payout failed validation. | Call \[List Batch Payout Items\](https://docs.worldfirst.com/wfdocs/api-sdk/list_batch_payout_items) with `status=INVALID` to inspect failures; remove or replace the offending items and retry. |
| CURRENCY_NOT_SUPPORTED | F | The specified currency is not supported. | Ensure each item's `sourceAmount.currency` and `payoutAmount.currency` are supported by your account; contact WorldFirst if unsure. |
| TRADE_AMOUNT_IS_NOT_ENOUGH | F | The trade amount is not enough. | Check the trade orders associated with the items and ensure their available amount covers the payout amounts. |
| BENEFICIARY_NOT_EXIST | F | The beneficiary does not exist. | Verify each item's `beneficiary.beneficiaryId` refers to an existing beneficiary. |
| BALANCE_INSUFFICIENT | F | Insufficient balance. | Ensure the payer account has sufficient balance to fund the batch, or reduce the payout amounts. |
| EXCEED_MAX_COUNT_LIMIT | F | The maximum count limit has been exceeded. | Reduce the number of items or contact WorldFirst technical support to increase the limit. |

## 请求头与 Java 集成

本 Skill 沿用官方仍支持的 RSA 模式：Content-Type、Client-Id、Request-Time、Signature 由 [WfApiClient](../../../common/java/client/WfApiClient.java) 生成，响应强制验签。官网 API Key 示例不与本模板 RSA 请求混用。代理账户头使用 WfRequestContext（account-id / access-token 二选一）；未提供则操作凭证所属账户。公共配置与依赖见 [common README](../../../common/README.md)。

复用 `BatchPayoutService`；请求 `CalculateBatchPayoutRequest`，响应 `CalculateBatchPayoutResponse`，均位于本模块 `../java/`。Service 是单次请求薄封装，不负责业务校验、自动重试或异步轮询。

```java
// apiClient 由公共配置构建；请求字段按下方 JSON 示例和参数规格填写。
BatchPayoutService service = new BatchPayoutService(apiClient);
CalculateBatchPayoutRequest request = new CalculateBatchPayoutRequest();
// 填入本接口必填字段后再调用（不要直接发送空请求）。
CalculateBatchPayoutResponse response = service.calculateBatchPayout(request);
// 先检查 response.getResult().getResultStatus()，再按模块流程检查业务状态。
// 不输出完整请求/响应，特别是卡号、CVV、令牌、下载链接和 RFI 材料。
```

## 请求 JSON 示例

```json
{
  "id": "20260808BATCH00001"
}
```

## 响应 JSON 示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultStatus": "S",
    "resultMessage": "success"
  },
  "id": "20260808BATCH00001",
  "status": "INIT",
  "createdAt": "2026-08-08T09:15:00+08:00",
  "updatedAt": "2026-08-08T11:00:00+08:00",
  "requestId": "req_batch_20260808_0001",
  "batchName": "Aug 8 supplier payouts",
  "totalItemCount": 2,
  "validItemCount": 2,
  "calculationSummary": {
    "lastCalculatedAt": "2026-08-08T11:00:00+08:00",
    "currencyPairs": [
      {
        "currencyPair": "USD/USD",
        "clientRate": 1.0,
        "sourceAmount": {
          "currency": "USD",
          "value": 500000
        },
        "payoutAmount": {
          "currency": "USD",
          "value": 500000
        },
        "feeRate": "0.001",
        "feeAmount": {
          "currency": "USD",
          "value": 500
        },
        "feeItemList": [
          {
            "feeItemType": "PAYOUT_SERVICE_FEE",
            "feeItemAmount": {
              "currency": "USD",
              "value": 500
            }
          }
        ]
      },
      {
        "currencyPair": "USD/EUR",
        "clientRate": 0.9234,
        "sourceAmount": {
          "currency": "USD",
          "value": 300000
        },
        "payoutAmount": {
          "currency": "EUR",
          "value": 277020
        },
        "feeRate": "0.0005",
        "feeAmount": {
          "currency": "USD",
          "value": 150
        },
        "feeItemList": [
          {
            "feeItemType": "PAYOUT_SERVICE_FEE",
            "feeItemAmount": {
              "currency": "USD",
              "value": 150
            }
          }
        ],
        "originalFeeRate": "0.001",
        "originalFeeAmount": {
          "currency": "USD",
          "value": 300
        },
        "promotionList": [
          {
            "promotionName": "August Fee Discount",
            "promotionType": "FEE_DISCOUNT",
            "activeTime": "2026-08-01T00:00:00+08:00",
            "expiredTime": "2026-08-31T23:59:59+08:00"
          }
        ]
      }
    ]
  }
}
```

## 生成后检查

对照上表检查全部字段（包括嵌套对象及列表）；String 保留日期时区和未知枚举，金额用公共 Amount。编译并核对实际包名/import；不能把 HTTP 成功当作业务成功。具体状态、重试和分页规则见模块 README 与本页错误表。
