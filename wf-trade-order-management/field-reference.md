# TradeOrder Field Reference

## TradeOrder — Full Field List

| Field | Type | Scene | Required | Notes |
|---|---|---|---|---|
| referenceOrderNo | String | Both | YES | Max 64; B2B: >3 chars, no 4 consecutive repeats |
| paymentTime | DateTime | Both | YES | ISO8601, ≤ API call time |
| transAmount | Amount | Both | YES | B2C: buyer payment; B2B: expected association amount |
| tradeAmount | Amount | Both | YES | B2C: seller received; B2B: total batch amount |
| tradeType | String | Both | YES | `GOODS` or `SERVICE` |
| orderTime | DateTime | B2C | YES | ≤ API call time |
| orderType | String | B2C | YES | `LOAN` (payment) or `REFUND` |
| merchant | Merchant | B2C | YES | See Merchant below |
| seller | Customer | B2C | YES | See Customer below |
| buyer | Buyer | B2C | YES | See Buyer below |
| goods | List\<Goods\> | Both | YES when tradeType=GOODS | See Goods below |
| shipping | Shipping | Both | YES when tradeType=GOODS | See Shipping below |
| tradeTerms | String | B2B | YES | See TradeTerms enum |
| isUsedForExchange | String | B2B | YES | `Y` or `N` |
| bizContractInfo | BizContractInfo | B2B | YES | See BizContractInfo below |
| logisticsMode | String | B2B | YES when tradeType=GOODS | `DROPSHIPPING` or `REGULAR_MODE` |
| market | String | Both | NO | `CHN` or `USA` |
| revenueShare | String | Both | NO | Revenue share ratio |

## Amount

| Field | Type | Required | Notes |
|---|---|---|---|
| currency | String | YES | ISO-4217, max 3 chars |
| value | String | YES | Smallest currency unit, max 16 chars |

## Merchant (B2C)

| Field | Type | Required | Notes |
|---|---|---|---|
| store.storeShopUrl | String | YES | Max 64 chars |
| merchantId | String | NO | WF-assigned merchant ID |

## Customer / Seller (B2C)

| Field | Type | Required | Notes |
|---|---|---|---|
| customerId | String | YES | WF-assigned user ID, max 64 chars |
| referenceCustomerId | String | YES | Integrator-assigned user ID, max 64 chars |
| customerCompanyName | String | NO | Max 256 chars |
| certificateList[].certificateNo | String | NO | Business license number, max 128 chars |
| certificateList[].certificateType | String | NO | `ENTERPRISE_REGISTRATION` |

## Buyer (B2C)

At least one of `referenceBuyerId`, `buyerName`, `buyerEmail` must be provided.

| Field | Type | Required | Notes |
|---|---|---|---|
| referenceBuyerId | String | CONDITIONAL | Max 64 chars |
| buyerName.firstName | String | NO | Max 32 chars |
| buyerName.middleName | String | NO | Max 32 chars |
| buyerName.lastName | String | NO | Max 32 chars |
| buyerName.fullName | String | YES (if buyerName provided) | Max 96 chars |
| buyerEmail | String | CONDITIONAL | Max 128 chars |
| buyerCountry | String | NO | ISO-3166 2-letter code |
| buyerPhoneNo | String | NO | Max 24 chars |

## Goods

| Field | Type | Scene | Required | Notes |
|---|---|---|---|---|
| goodsName | String | Both | YES | Max 256 chars |
| goodsCategory | String | B2C | YES | Max 256 chars |
| goodsQuantity | String | Both | YES | |
| goodsUnit | String | B2B | YES | Unit of measurement |
| goodsCnName | String | B2B | YES | Chinese name |
| storeUrl | String | B2B | YES | Max not specified |

## Shipping

| Field | Type | Scene | Required | Condition |
|---|---|---|---|---|
| isShipped | String | B2B | CONDITIONAL | Required when B2B + isUsedForExchange=Y. `Y`/`N` |
| isDeclared | String | B2B | CONDITIONAL | Required when B2B + isUsedForExchange=Y + isShipped=Y. `Y`/`N` |
| isNewBuyer | String | B2B | CONDITIONAL | Required when B2B + isUsedForExchange=Y + isShipped=N. `Y`/`N` |
| wayBillInfos | List\<WayBillInfo\> | Both | CONDITIONAL | Required: (B2B + isUsedForExchange=Y + isShipped=Y + isDeclared=N) OR B2C |
| wayBillInfos[].shippingOrderReferenceNo | String | B2B | CONDITIONAL | Required when B2B + isUsedForExchange=Y + isShipped=Y + isDeclared=N |
| shippingAddress | Address | B2C | YES | See Address below |
| shippingMethod | String | B2B | CONDITIONAL | Required when B2B + isUsedForExchange=Y + isShipped=Y + isDeclared=N. Values: `RAILWAY`/`LAND_TRANSPORTATION`/`BY_SEA`/`AIR_CARGO`/`EXPRESS`/`SPECIAL_LINE_TRANSPORTATION`/`OTHER` |
| shippingProofAttachmentList | List\<AttachmentInfo\> | B2B | CONDITIONAL | Required when B2B + isUsedForExchange=Y + isShipped=Y + isDeclared=N |
| logisticsCompany | LogisticsCompany | B2B | CONDITIONAL | Required when B2B + isUsedForExchange=Y + isShipped=Y + isDeclared=N |
| logisticsCompany.providerKey | String | B2B | CONDITIONAL | Max 8 chars |
| logisticsCompany.providerValue | String | B2B | CONDITIONAL | Max 128 chars |
| declarationInfos | List\<DeclarationInfo\> | B2B | CONDITIONAL | Required when B2B + isUsedForExchange=Y + isShipped=Y + isDeclared=Y |
| declarationInfos[].declarationOrderReferenceNo | String | B2B | YES | Max 100 chars |
| declarationInfos[].supervisionMethod | String | B2B | YES | `9810`/`9710`/`0110` |
| declarationInfos[].customsDeclarationAttachmentList | List\<AttachmentInfo\> | B2B | YES | |
| expectedShippingDate | String | B2B | CONDITIONAL | 13-digit timestamp. Required when B2B + isUsedForExchange=Y + isShipped=N |
| inquiryChatRecordAttachmentList | List\<AttachmentInfo\> | B2B | CONDITIONAL | Required when B2B + isUsedForExchange=Y + isShipped=N + isNewBuyer=Y |
| logisticsChatRecordAttachmentList | List\<AttachmentInfo\> | B2B | CONDITIONAL | Required when B2B + isUsedForExchange=Y + isShipped=N + isNewBuyer=Y |

## Address (B2C shippingAddress)

| Field | Type | Required | Notes |
|---|---|---|---|
| region | String | YES | ISO-3166 2-letter code, max 2 chars |
| state | String | NO | Max 8 chars |
| city | String | NO | Max 32 chars |
| address1 | String | NO | Max 128 chars |
| address2 | String | NO | Max 128 chars |
| zipCode | String | NO | Max 32 chars |

## AttachmentInfo

| Field | Type | Required | Notes |
|---|---|---|---|
| fileName | String | YES | Original filename, e.g. `file.pdf`, max 64 chars |
| fileKey | String | YES | File key from upload API, max 128 chars |

## BizContractInfo (B2B)

| Field | Type | Required | Condition |
|---|---|---|---|
| buyerEnName | String | CONDITIONAL | Required when tradeType=GOODS, max 100 chars |
| tradeCountry | String | CONDITIONAL | Required when tradeType=GOODS, ISO-3166 2-letter. BY/RU not supported |
| deliverCountry | String | CONDITIONAL | Required when tradeType=GOODS, ISO-3166 2-letter. BY/RU not supported |
| contractList | List\<AttachmentInfo\> | CONDITIONAL | Required when tradeType=GOODS. Pro-forma invoice, commercial invoice, or contract |
| otherAttachmentList | List\<AttachmentInfo\> | YES (B2B) | Required for B2B |
| attachmentDesc | String | YES (B2B) | Required for B2B, max 200 chars |

## TradeOrderResult (Response — PAY_INTO_CHINA)

> Exact sub-fields not documented. Generate with at minimum: `tradeOrderId` (String) and `resultStatus` (String).

## Unsupported Countries

Do not submit orders where `tradeCountry` or `deliverCountry` is:
- `BY` (Belarus)
- `RU` (Russia)
