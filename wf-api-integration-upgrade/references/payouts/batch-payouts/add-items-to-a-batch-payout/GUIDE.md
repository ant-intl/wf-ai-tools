# Add Items to a Batch Payout

> 规格来源：[官方 add_items_to_a_batch_payout](https://docs.worldfirst.com/wfdocs/api-sdk/add_items_to_a_batch_payout)；核对日期：2026-11-18。
> 生成前先读 [模块 README](../README.md)。保留官方字段、大小写、必填条件及本接口错误表；不要由其他模块推断。

`POST /api/open/v1/batchPayouts/addItems`

Append up to 10 payout items to an existing batch per call until the batch reaches its 100-item limit. The batch must be in INIT status; invalid items are returned with validation details without blocking other items in the request. Each item follows the same structure as a [Create a Payout](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_payout.md) request for its payout scenario. fvtPreference, oboPreference, paymentType, and paymentNetwork are defined in [Create a Batch Payout](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_batch_payout.md) and applied to every item in the batch.

## Request Parameters

#### id (string, REQUIRED)

Unique identifier of the target batch, returned by [Create a Batch Payout](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_batch_payout.md).

#### items (array, REQUIRED)

Payout items to append to the batch. Provide 1 to 10 items per request. Build each item from the request of the matching payout scenario in [Create a Payout](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_payout.md). Items in the same batch can use different payout scenarios. Note: fvtPreference, oboPreference, paymentType, and paymentNetwork are defined in [Create a Batch Payout](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_batch_payout.md) and applied to every item in the batch. transferQuote is not applicable; call [Calculate a Batch Payout](https://docs.worldfirst.com/wfdocs/api-sdk/calculate_a_batch_payout.md) to preview amounts, fees, and FX rates later.

##### requestId (string, REQUIRED)

Idempotency key for this payout item. Assign a unique value to each item and reuse the same value only when retrying an identical item.

##### businessSceneCode (string)

Business scene code that identifies the payout use case. If specified, it overrides the businessSceneCode set on the batch for this item. See [BusinessSceneCode](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#UqJiZ).

##### purposeCode (string, REQUIRED)

Payment purpose code used for compliance and routing. Supported values depend on the payout scenario. See [PurposeCode](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#XOHdL).

##### sourceAmount (Amount)

Funds debited from the payer. Provide value on either sourceAmount or payoutAmount, but not both; include currency on both objects. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

##### payoutAmount (Amount)

Funds received by the beneficiary. Provide value on either sourceAmount or payoutAmount, but not both; include currency on both objects. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

##### beneficiary (Beneficiary, REQUIRED)

Beneficiary information. Required fields depend on the payout scenario; use the beneficiary definition of the matching scenario in [Create a Payout](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_payout.md).

##### payer (Payer)

Sender information. Required when the batch oboPreference is NEED_OBO. Omit it to debit the default balance account. Same structure as in [Create a Payout](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_payout.md).

##### quotaConsumptionPreference (QuotaConsumptionPreference)

How the CNY quota is consumed for this payout. Same structure as in [Create a Payout](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_payout.md).

##### reference (string)

Bank transfer reference that may appear on the beneficiary's bank statement.

##### transferMemo (string)

Internal memo for your records. It is not visible to the beneficiary.

##### additionalInfo (PayoutAdditionalInfo)

Additional fields for WorldFirst-defined business scenarios. Same structure as in [Create a Payout](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_payout.md).

## Response Parameters

#### result (Result)

API call result. See [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result.md).

#### id (string)

Unique identifier of the batch payout.

#### status (string)

Current batch lifecycle status after the operation. See [BatchPayoutStatus](https://docs.worldfirst.com/wfdocs/api-sdk/batch_payouts_overview.md#VrCNk).

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

Total number of payout items currently in the batch, from 0 to 100.

#### validItemCount (integer)

Number of payout items in the batch that have passed validation, from 0 to 100.

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

#### items (array)

Results for the items submitted in this request.

##### requestId (string)

Idempotency key supplied for the payout item.

##### itemId (string)

WorldFirst-assigned unique identifier for the payout item. Use it to correlate later batch item results.

##### status (string)

Current lifecycle status of the payout item. See [BatchPayoutItemStatus](https://docs.worldfirst.com/wfdocs/api-sdk/batch_payouts_overview.md#qo8r6).

##### failureCode (string)

Machine-readable failure reason. Returned when status is INVALID, FAILED, or RETURNED. See [ItemFailureCode](https://docs.worldfirst.com/wfdocs/api-sdk/batch_payouts_overview.md).

##### failureMessage (string)

Failure description and recommended remediation. Returned when status is INVALID, FAILED, or RETURNED.

##### businessSceneCode (string)

Business scene code for this item. Returned when supplied in the request. See [BusinessSceneCode](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#UqJiZ).

##### purposeCode (string)

Payment purpose code. See [PurposeCode](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#XOHdL).

##### payer (Payer)

Sender information.

##### beneficiary (Beneficiary)

Recipient information.

##### sourceAmount (Amount)

Funds debited from the payer. Returned after the quote is completed. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

##### payoutAmount (Amount)

Funds received by the beneficiary. Returned after the quote is completed. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

##### feeAmount (Amount)

Service fee for this item. Returned after the quote is completed. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

##### feeItemList (array)

Breakdown of service fee charges. Returned when fees apply. See [FeeItemDetails](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

##### promotionList (array)

Active promotions applied to this item. Returned when a promotion is matched. See [PromotionInfo](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

##### originalFeeRate (string)

Original fee rate before promotional discount. Returned when a promotion is matched.

##### feeRate (string)

Actual fee rate after promotional discount. Returned when fees apply.

##### originalFeeAmount (Amount)

Original fee amount before promotional discount. Returned when a promotion is matched. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

##### oboPreference (string)

On-behalf-of (OBO) routing preference applied from the batch. See [OboPreference](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md).

##### fvtPreference (string)

Full value transfer (FVT) preference applied from the batch. See [FvtPreference](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md).

##### paymentType (string)

Payment channel type applied from the batch. See [PaymentType](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md).

##### paymentNetwork (string)

Payment clearing network applied from the batch. See [PaymentNetwork](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#UFKUW).

##### quotaConsumptionPreference (QuotaConsumptionPreference)

CNY quota consumption preference for this item.

##### reference (string)

Bank transfer reference. Returned when supplied in the request.

##### transferMemo (string)

Internal memo. Returned when supplied in the request.

##### additionalInfo (PayoutAdditionalInfo)

Additional fields for WorldFirst-defined business scenarios.

## Error Codes

| resultCode | resultStatus | resultMessage | Troubleshooting |
| --- | --- | --- | --- |
| SUCCESS | S | Success | N/A |
| PARAM_ILLEGAL | F | Illegal parameters exist | Verify that `items` contains 1–10 entries and that each field meets its documented constraints. |
| REPEAT_REQ_INCONSISTENT | F | Repeated requests are inconsistent | Reuse the original payload for the same item `requestId`, or assign a new `requestId`. |
| CONTRACT_CHECK_FAIL | F | The contract check has failed | Check the contract status, then retry. |
| CONTRACT_NOT_EXIST | F | The contract does not exist | Ensure the required payout contract is in place, then retry. |
| UN_SUPPORT_BUSINESS | F | Unsupported business | Verify each item's `businessSceneCode` value and ensure the currency combination is supported. |
| USER_NOT_EXIST | F | The user does not exist | Verify the user identified by the request, then retry with correct user information. |
| ACCOUNT_NOT_EXIST | F | The account does not exist | Verify the payer account exists, then retry. |
| ACCOUNT_STATUS_ABNORMAL | F | The account status is abnormal | Retry with an account in a valid status, or contact WorldFirst technical support. |
| USER_ACCOUNT_ABNORMAL | F | The user account status is abnormal | The payer account is in an abnormal state. Retry with a valid account. |
| USER_NO_PERMISSION | F | User does not have permission | Confirm the user has permission to manage batch payouts, or retry with a user that has the required permissions. |
| AUTHORIZATION_NOT_EXIST | F | The authorization does not exist | Confirm the OAuth authorization is still active for the target account. |
| CURRENCY_NOT_SUPPORTED | F | The specified currency is not supported | Confirm that the currency codes in each item's `sourceAmount` and `payoutAmount` are supported by your account. |
| CARD_STATUS_INVALID | F | Card status is invalid | Check the card status and retry with a valid card. |
| CARD_INFO_ABNORMAL | F | Card info abnormal | Check the item's `beneficiary.bankDetails` fields and retry with valid account information. |
| CARD_CERTIFICATE_INFO_MISS | F | Card certificate info is missing | Provide the required certificate information in the item's `beneficiary.bankDetails`. |
| WALLET_INFO_ABNORMAL | F | Wallet info abnormal | Check the item's `beneficiary.walletDetails` fields and retry with valid wallet information. |
| WALLET_TEMPLATE_NOT_EXIST | F | Wallet template does not exist | Check the item's `beneficiary.walletDetails.walletBrandName`. |
| BALANCE_NOT_ENOUGH | F | Insufficient balance | Ensure the payer account has sufficient balance. |
| AMOUNT_EXCEED_LIMIT | F | Amount exceeds limit | Reduce the item amount to within the allowed limit. |
| BATCH_PAYOUT_NOT_FOUND | F | Batch payout not found | Confirm that `id` belongs to the current account context. |
| BATCH_ITEM_STATUS_NOT_SUPPORTED | F | Item status does not support this operation | Do not resubmit an item that has already advanced to an unsupported status. |
| BATCH_PAYOUT_STATUS_NOT_SUPPORTED | F | Batch status does not support this operation | Add items only while the batch is in `INIT` status. |

## 项级场景结构

读取 [item-scenarios.md](../item-scenarios.md)，按银行账户、钱包、已有收款人、WF 账户或中国付款场景填充嵌套字段。

## 请求头与 Java 集成

本 Skill 沿用官方仍支持的 RSA 模式：Content-Type、Client-Id、Request-Time、Signature 由 [WfApiClient](../../../common/java/client/WfApiClient.java) 生成，响应强制验签。官网 API Key 示例不与本模板 RSA 请求混用。代理账户头使用 WfRequestContext（account-id / access-token 二选一）；未提供则操作凭证所属账户。公共配置与依赖见 [common README](../../../common/README.md)。

复用 `BatchPayoutService`；请求 `AddItemsToBatchPayoutRequest`，响应 `AddItemsToBatchPayoutResponse`，均位于本模块 `../java/`。Service 是单次请求薄封装，不负责业务校验、自动重试或异步轮询。

```java
// apiClient 由公共配置构建；请求字段按下方 JSON 示例和参数规格填写。
BatchPayoutService service = new BatchPayoutService(apiClient);
AddItemsToBatchPayoutRequest request = new AddItemsToBatchPayoutRequest();
// 填入本接口必填字段后再调用（不要直接发送空请求）。
AddItemsToBatchPayoutResponse response = service.addItemsToBatchPayout(request);
// 先检查 response.getResult().getResultStatus()，再按模块流程检查业务状态。
// 不输出完整请求/响应，特别是卡号、CVV、令牌、下载链接和 RFI 材料。
```

## 请求 JSON 示例

```json
{
  "id": "bp_20260808****",
  "items": [
    {
      "requestId": "req_item_20260808_0001",
      "businessSceneCode": "THIRD_PARTY_PAYOUT",
      "beneficiary": {
        "accountType": "BANK_ACCOUNT",
        "beneficiaryId": "ben_9f7c****"
      },
      "payoutAmount": {
        "value": 500000,
        "currency": "USD"
      },
      "purposeCode": "SAL",
      "reference": "Salary August 2026",
      "transferMemo": "Employee ID E1001"
    }
  ]
}
```

## 响应 JSON 示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "id": "bp_20260808****",
  "status": "INIT",
  "createdAt": "2026-08-08T10:00:00+08:00",
  "updatedAt": "2026-08-08T10:05:00+08:00",
  "requestId": "req_batch_20260808_0001",
  "batchName": "Payroll 2026-08",
  "transferMemo": "August payroll run",
  "totalItemCount": 2,
  "validItemCount": 0,
  "items": [
    {
      "requestId": "req_item_20260808_0001",
      "itemId": "item_20260808_0001",
      "status": "INVALID",
      "failureCode": "BENEFICIARY_INACTIVE",
      "failureMessage": "The referenced beneficiary is not ACTIVE. Activate the beneficiary or use an active beneficiary."
    }
  ]
}
```

## 生成后检查

对照上表检查全部字段（包括嵌套对象及列表）；String 保留日期时区和未知枚举，金额用公共 Amount。编译并核对实际包名/import；不能把 HTTP 成功当作业务成功。具体状态、重试和分页规则见模块 README 与本页错误表。
