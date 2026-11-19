# Cancel a Batch Payout

> 规格来源：[官方 cancel_a_batch_payout](https://docs.worldfirst.com/wfdocs/api-sdk/cancel_a_batch_payout)；核对日期：2026-11-18。
> 生成前先读 [模块 README](../README.md)。保留官方字段、大小写、必填条件及本接口错误表；不要由其他模块推断。

`POST /api/open/v1/batchPayouts/cancel`

Cancels a batch payout that is under drafting or awaiting approval. Only batches in INIT or PENDING_APPROVAL status can be cancelled through this endpoint; once execution has begun, cancellation is no longer possible.

## Request Parameters

#### id (string, REQUIRED)

Unique identifier of the batch payout to cancel. The batch must be in INIT or PENDING_APPROVAL status.

#### memo (string)

Optional note describing why the batch is being cancelled. Max length: 256 characters.

## Response Parameters

#### result (Result)

See [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result.md).

#### id (string)

Unique identifier of the batch payout.

#### status (string)

Batch status after cancellation. Always CANCELLED. See [BatchPayoutStatus](https://docs.worldfirst.com/wfdocs/api-sdk/batch_payouts_overview.md#VrCNk).

#### createdAt (datetime)

Time the batch was created, in ISO 8601 extended format.

#### updatedAt (datetime)

Time the batch was most recently updated, in ISO 8601 extended format.

#### requestId (string)

Idempotency key supplied when the batch was created.

#### batchName (string)

Human-readable batch name, if provided at creation.

#### paymentType (string)

Payment channel type set on the batch, if provided at creation. See [PaymentType](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md).

#### cancelledAt (datetime)

Time the cancellation was recorded, in ISO 8601 extended format.

#### memo (string)

Note describing why the batch is being cancelled if provided.

#### totalItemCount (integer)

Total number of items in the batch at cancellation. Range: 0 - 100.

#### validItemCount (integer)

Number of items that had passed validation at cancellation. Range: 0 - 100.

#### calculationSummary (CalculationSummary)

Latest reference pricing summary. Returned once calculate (or submit) has completed pricing. All totals and rates are indicative — final values are determined at payout processing.

##### lastCalculatedAt (datetime)

Time the batch was most recently priced, in ISO 8601 format. Indicates how recent the reference preview is.

##### currencyPairs (array)

Aggregated reference pricing per currency pair covered by the batch. Up to 10 entries.

###### currencyPair (string)

Currency pair in {sourceCurrency}/{payoutCurrency} format, composed of two ISO 4217 three-letter currency codes (for example, USD/EUR). See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

###### clientRate (decimal)

Indicative exchange rate applied to items in this currency pair for this reference preview. The final rate is determined at payout processing. Up to 6 decimal places.

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
| PARAM_ILLEGAL | F | Illegal parameters exist. | Verify `id` is present and no longer than 64 characters; ensure `memo` is at most 256 characters. |
| BATCH_NOT_EXIST | F | The batch payout does not exist. | Confirm `id` matches a batch returned by \[Create a Batch Payout\](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_batch_payout). |
| BATCH_PAYOUT_STATUS_NOT_SUPPORTED | F | Only a batch payout in INIT or PENDING_APPROVAL status can be cancelled. | Check the batch `status` via \[Query a Batch Payout\](https://docs.worldfirst.com/wfdocs/api-sdk/query_a_batch_payout); use \[Delete a Batch Payout\](https://docs.worldfirst.com/wfdocs/api-sdk/delete_a_batch_payout) for drafts in `INIT`, and contact support if execution has already started. |

## 请求头与 Java 集成

本 Skill 沿用官方仍支持的 RSA 模式：Content-Type、Client-Id、Request-Time、Signature 由 [WfApiClient](../../../common/java/client/WfApiClient.java) 生成，响应强制验签。官网 API Key 示例不与本模板 RSA 请求混用。代理账户头使用 WfRequestContext（account-id / access-token 二选一）；未提供则操作凭证所属账户。公共配置与依赖见 [common README](../../../common/README.md)。

复用 `BatchPayoutService`；请求 `CancelBatchPayoutRequest`，响应 `CancelBatchPayoutResponse`，均位于本模块 `../java/`。Service 是单次请求薄封装，不负责业务校验、自动重试或异步轮询。

```java
// apiClient 由公共配置构建；请求字段按下方 JSON 示例和参数规格填写。
BatchPayoutService service = new BatchPayoutService(apiClient);
CancelBatchPayoutRequest request = new CancelBatchPayoutRequest();
// 填入本接口必填字段后再调用（不要直接发送空请求）。
CancelBatchPayoutResponse response = service.cancelBatchPayout(request);
// 先检查 response.getResult().getResultStatus()，再按模块流程检查业务状态。
// 不输出完整请求/响应，特别是卡号、CVV、令牌、下载链接和 RFI 材料。
```

## 请求 JSON 示例

```json
{
  "id": "20260808BATCH00001",
  "memo": "Superseded by a corrected batch created on 2026-08-09."
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
  "status": "CANCELLED",
  "createdAt": "2026-08-08T09:15:00+08:00",
  "updatedAt": "2026-08-08T18:00:00+08:00",
  "requestId": "req_batch_20260808_0001",
  "batchName": "Aug 8 supplier payouts",
  "cancelledAt": "2026-08-08T18:00:00+08:00",
  "memo": "Superseded by a corrected batch created on 2026-08-09.",
  "totalItemCount": 3,
  "validItemCount": 3
}
```

## 生成后检查

对照上表检查全部字段（包括嵌套对象及列表）；String 保留日期时区和未知枚举，金额用公共 Amount。编译并核对实际包名/import；不能把 HTTP 成功当作业务成功。具体状态、重试和分页规则见模块 README 与本页错误表。
