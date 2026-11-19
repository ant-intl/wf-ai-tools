# List Batch Payout Items

> 规格来源：[官方 list_batch_payout_items](https://docs.worldfirst.com/wfdocs/api-sdk/list_batch_payout_items)；核对日期：2026-11-18。
> 生成前先读 [模块 README](../README.md)。保留官方字段、大小写、必填条件及本接口错误表；不要由其他模块推断。

`POST /api/open/v1/batchPayouts/listItems`

Retrieves a paginated list of items in a batch payout. Use this endpoint to inspect the validation, execution, or failure state of each item; filter by [BatchPayoutItemStatus](https://docs.worldfirst.com/wfdocs/api-sdk/batch_payouts_overview.md#qo8r6) to focus on items that require attention (for example, INVALID items awaiting cleanup or FAILED items to be investigated).

## Request Parameters

#### id (string, REQUIRED)

Unique identifier of the target existing batch payout.

#### limit (integer)

Number of results per page. Value range: 1–100. Defaults to 20 if omitted.

#### cursor (string)

Pagination cursor. Omit on the first call; pass the nextCursor (or prevCursor) returned by a previous call to move to the next (or previous) page. Max length: 256 characters.

#### status (string)

Filter items by latest status. See [BatchPayoutItemStatus](https://docs.worldfirst.com/wfdocs/api-sdk/batch_payouts_overview.md#qo8r6).

## Response Parameters

#### result (Result)

See [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result.md).

#### items (array)

Batch items on the current page. Max 100 elements. Each item follows the [Query a Payout](https://docs.worldfirst.com/wfdocs/api-sdk/query_a_payout.md) schema, with itemId replacing id. The itemId is the payout identifier; pass it as id to Query a Payout.

#### nextCursor (string)

Cursor for the next page. Returned only when more results are available. Pass it back as cursor to fetch the next page. Max length: 256 characters.

#### prevCursor (string)

Cursor for the previous page. Returned only when a previous page exists. Max length: 256 characters.

## Error Codes

| resultCode | resultStatus | resultMessage | Troubleshooting |
| --- | --- | --- | --- |
| SUCCESS | S | Success | N/A |
| PARAM_ILLEGAL | F | Illegal parameters exist. | Verify `id`, `limit` (1 - 100), and `cursor` format; check that `status` matches an accepted enum value. |
| AUTHORIZATION_NOT_EXIST | F | The authorization does not exist. | Confirm the OAuth authorization is still active for the target account. |
| BATCH_NOT_EXIST | F | The batch payout does not exist. | Confirm `id` matches a batch returned by \[Create a Batch Payout\](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_batch_payout). |
| PROCESS_FAIL | F | A general business failure occurred. | Retry with the same `cursor`; if the issue persists, contact WorldFirst support. |

## items[] 完整字段

以下为 Query a Payout 的响应字段（移除外层 result，将 id 改为 itemId）。批次项还可能带 requestId；其 status 使用本模块的 BatchPayoutItemStatus。

#### itemId (string)

WorldFirst-assigned unique payout ID. Use this ID for subsequent query calls.

#### status (string)

Current payout status. See [PayoutStatus](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#iyEQy).

#### failureCode (string)

Failure reason code. Returned when status is FAILED. Inspect this field to determine the cause and whether to retry.

#### failureMessage (string)

A plain-text description of the failure reason. Returned when status is FAILED.

#### businessSceneCode (string)

Business scene code for this payout. See [BusinessSceneCode](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#UqJiZ).

#### purposeCode (string)

Payment purpose code applied. See [PurposeCode](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#XOHdL).

#### sourceAmount (Amount)

Source currency amount debited from the payer's account. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### payoutAmount (Amount)

Target currency amount received (or to be received) by the beneficiary. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### transferQuote (Quote)

Exchange rate applied to this payout. Returned for cross-currency payouts.

##### quoteId (string)

Quote ID used for this payout.

##### currencyPair (string)

Currency pair (e.g., HKD/USD). Two ISO 4217 three-letter codes joined by /.

##### clientRate (decimal)

Applied exchange rate. Up to 6 decimal places.

##### effectiveAt (datetime)

Time when the rate became effective. ISO 8601 extended format.

##### expiresAt (datetime)

Time when the quote expires. ISO 8601 extended format.

#### payer (Payer)

Payer information recorded for this payout.

##### balanceType (string)

Payer account type. Default: BALANCE.

##### accountNumber (string)

Payer account number.

##### region (string)

Payer's country/region. ISO 3166 two-letter code.

##### phone (string)

Payer's phone number.

##### email (string)

Payer's email address.

##### name (UserName)

Payer's legal name. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

##### address (Address)

Payer's mailing address. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### beneficiary (Beneficiary)

Beneficiary information recorded for this payout.

##### referenceBeneficiaryId (string)

External beneficiary reference ID assigned by your system. Use this only if you want to declare recipient info/quota for CNY settlement.

##### region (string)

Beneficiary's country/region if beneficiaryId/referenceBeneficiaryId is not provided. ISO 3166 two-letter code.

##### phone (string)

Beneficiary's phone number.

##### email (string)

Beneficiary's email address.

##### city (string)

Beneficiary's city.

##### address (string)

Beneficiary's street address.

##### accountType (string)

Beneficiary account type. See [AccountType](https://docs.worldfirst.com/wfdocs/api-sdk/beneficiaries_overview.md#OvdvT).

##### entityType (string)

Beneficiary entity type if beneficiaryId/referenceBeneficiaryId is not provided.

##### relationType (string)

Relationship between payer and beneficiary if beneficiaryId/referenceBeneficiaryId is not provided.

##### beneficiaryId (string)

Beneficiary ID.

##### bankDetails (BankDetail)

Bank account details of the recipient.

###### accountNumber (string)

Bank account number.

###### iban (string)

International Bank Account Number (IBAN). Used for SEPA and IBAN-based transfers.

###### bankBIC (string)

SWIFT/BIC code of the receiving bank.

###### routingNumber (string)

Local clearing routing number (e.g., ABA routing number for ACH transfers).

###### bankName (string)

Receiving bank name.

###### bankLocalName (string)

Bank name in local language.

###### branchBankName (string)

Branch bank name.

###### bankBranchCode (string)

Bank branch code.

###### bankAccountLocalName (string)

Account holder's name in local language.

###### bankAddress (string)

Bank's address.

###### beneficiaryDepositType (string)

Beneficiary deposit category.

###### cardClientCode (string)

Beneficiary ID document type.

###### cardClientId (string)

Beneficiary ID document number.

###### beneficiaryTIN (string)

Beneficiary Tax Identification Number (TIN).

###### beneficiaryAccountNumberType (string)

Beneficiary account number type.

##### walletDetails (WalletDetail)

E-wallet account details.

###### walletAccountType (string)

Wallet account type. See [WalletAccountType](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#TpMpT).

###### accountNumber (string)

Wallet account number.

###### walletBrandName (string)

Wallet brand. See [WalletBrandName](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#lZBBs).

###### iban (string)

IBAN for the wallet account, if applicable.

#### feeAmount (Amount)

Total service fee charged for this payout. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### feeItemList (array)

Itemized fee breakdown. Returned when multiple fee components apply.

##### feeItemType (string)

Fee item type. See [FeeItemType](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#gOAUt).

##### feeItemAmount (Amount)

Fee amount for this item. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### originalFeeRate (string)

Original fee rate before any promotional discounts.

#### feeRate (string)

Actual fee rate after promotional discounts are applied.

#### originalFeeAmount (Amount)

Original service fee before promotional discounts. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### discountFeeAmount (Amount)

Promotional fee reduction amount. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### returnedAmount (Amount)

Amount returned to the payer. Returned when status is RETURNED. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### returnedAt (datetime)

Timestamp when the funds were returned to the payer. Returned when status is RETURNED. ISO 8601 extended format. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### promotionList (array)

Active promotions applied to this payout.

##### promotionName (string)

Promotion name.

##### promotionType (string)

Promotion type.

-   `FEE_DISCOUNT`: Service fee discount.

##### activeTime (datetime)

Promotion activation time. ISO 8601 extended format.

##### expiredTime (datetime)

Promotion expiration time. ISO 8601 extended format.

#### fvtPreference (string)

Full Value Transfer preference of the payout.

-   `NEED_FVT`: Request Full Value Transfer.

#### oboPreference (string)

On-Behalf-Of routing preference of the payout.

-   `NEED_OBO`: Route the payout on behalf of the specified payer.
-   `NO_OBO`: Do not use On-Behalf-Of routing.

#### paymentType (string)

Payment channel type used for this payout.

-   `LOCAL`: Local clearing.
-   `CROSS`: Cross-border wire transfer via SWIFT.

#### paymentNetwork (string)

Specific clearing network used. See [PaymentNetwork](https://docs.worldfirst.com/wfdocs/api-sdk/payouts_overview.md#UFKUW).

#### reference (string)

Bank transfer reference text.

#### transferMemo (string)

Internal transfer memo.

#### additionalInfo (PayoutAdditionalInfo)

Supplementary business fields for this payout.

##### needMigrateMaterial (boolean)

Whether settlement materials were migrated.

## 请求头与 Java 集成

本 Skill 沿用官方仍支持的 RSA 模式：Content-Type、Client-Id、Request-Time、Signature 由 [WfApiClient](../../../common/java/client/WfApiClient.java) 生成，响应强制验签。官网 API Key 示例不与本模板 RSA 请求混用。代理账户头使用 WfRequestContext（account-id / access-token 二选一）；未提供则操作凭证所属账户。公共配置与依赖见 [common README](../../../common/README.md)。

复用 `BatchPayoutService`；请求 `ListBatchPayoutItemsRequest`，响应 `ListBatchPayoutItemsResponse`，均位于本模块 `../java/`。Service 是单次请求薄封装，不负责业务校验、自动重试或异步轮询。

```java
// apiClient 由公共配置构建；请求字段按下方 JSON 示例和参数规格填写。
BatchPayoutService service = new BatchPayoutService(apiClient);
ListBatchPayoutItemsRequest request = new ListBatchPayoutItemsRequest();
// 填入本接口必填字段后再调用（不要直接发送空请求）。
ListBatchPayoutItemsResponse response = service.listBatchPayoutItems(request);
// 先检查 response.getResult().getResultStatus()，再按模块流程检查业务状态。
// 不输出完整请求/响应，特别是卡号、CVV、令牌、下载链接和 RFI 材料。
```

## 请求 JSON 示例

```json
{
  "id": "20260808BATCH00001",
  "limit": 20,
  "status": "FAILED"
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
  "items": [
    {
      "requestId": "req_item_20260808_0003",
      "itemId": "item_20260808_0003",
      "status": "FAILED",
      "businessSceneCode": "THIRD_PARTY_PAYOUT",
      "payer": {
        "balanceType": "BALANCE",
        "region": "SG"
      },
      "beneficiary": {
        "accountType": "BANK_ACCOUNT",
        "beneficiaryId": "ben_****0003"
      },
      "sourceAmount": {
        "value": 300000,
        "currency": "USD"
      },
      "payoutAmount": {
        "value": 300000,
        "currency": "USD"
      },
      "feeAmount": {
        "value": 500,
        "currency": "USD"
      },
      "purposeCode": "SAL",
      "reference": "PAYROLL-2026-08-003",
      "failureCode": "INSUFFICIENT_BALANCE",
      "failureMessage": "Insufficient balance to fund this payout. Top up the account and create a new batch for the failed items."
    }
  ],
  "nextCursor": "eyJpdGVtU2VxIjoyMH0="
}
```

## 生成后检查

对照上表检查全部字段（包括嵌套对象及列表）；String 保留日期时区和未知枚举，金额用公共 Amount。编译并核对实际包名/import；不能把 HTTP 成功当作业务成功。具体状态、重试和分页规则见模块 README 与本页错误表。
