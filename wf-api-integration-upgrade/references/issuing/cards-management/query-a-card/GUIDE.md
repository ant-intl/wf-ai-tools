# Query a Card

> 规格来源：[官方 query_a_card](https://docs.worldfirst.com/wfdocs/api-sdk/query_a_card)；核对日期：2026-11-18。
> 生成前先读 [模块 README](../README.md)。保留官方字段、大小写、必填条件及本接口错误表；不要由其他模块推断。

`POST /api/open/v1/issuing/cards/query`

Retrieve the full details of a card by its ID, including status, limits, and authorization controls. A cancelled card (status=CANCELLED) is still returned by this query; filter by the status field client-side if needed.

## Request Parameters

#### id (string, REQUIRED)

Unique card identifier. Max 64 characters.

## Response Parameters

#### result (Result)

See [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result.md).

#### id (string)

Unique card identifier. Max 64 characters.

#### status (string)

Current card status.

-   `PENDING`: Card application submitted, awaiting review or production.
-   `ACTIVE`: Card is activated and ready for transactions.
-   `FROZEN`: Card is frozen; transactions are paused and can be resumed.
-   `FAILED`: Card creation failed (terminal).
-   `CANCELLED`: Card cancelled (terminal).

#### cardholderId (string)

Associated cardholder ID. Max 64 characters.

#### mediaType (string)

Card media type.

-   `VIRTUAL`: Virtual card.
-   `PHYSICAL`: Physical card.

#### createdAt (datetime)

Creation timestamp. ISO 8601 extended format.

#### cardBrand (string)

Card network brand.

-   `VISA`: Visa network.
-   `MASTERCARD`: Mastercard network.

#### budgetId (string)

Associated budget account ID. Max 64 characters.

#### cardNumber (string)

Masked card number. Returned only when status is ACTIVE. Max 19 characters.

#### expiryDate (string)

Card expiry date. Returned only when status is ACTIVE. Format MM/YY.

#### last4Digits (string)

Last 4 digits of the card number. Max 4 characters.

#### cardNickName (string)

Merchant-defined friendly name. Max 120 characters.

#### note (string)

Free-text note. Max 512 characters.

#### purpose (string)

Card purpose description. Max 64 characters.

#### email (string)

Email associated with the card. Max 256 characters.

#### mobilePhone (string)

Mobile phone associated with the card. Max 32 characters.

#### billingAddress (Address)

Billing address. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### transactionLimits (array of TransactionLimits)

Transaction limit configuration, aggregated by currency. Maximum 5 entries. At creation, each limit only needs interval and rangeAmount.

##### currency (string)

Limit currency. 3-letter ISO 4217 code. Supported value: USD.

##### limits (array of TransactionLimit)

Limit rules for this currency. Maximum 4 entries (one per interval).

###### interval (string)

Limit period. Supported values: PER_TRANSACTION, DAILY, MONTHLY, and ALL_TIME.

###### rangeAmount (integer)

Limit ceiling, in the smallest currency unit. Minimum 1.

###### usedAmount (integer)

Amount used in the current period.

###### remainAmount (integer)

Remaining available amount in the current period.

#### authorizationControl (AuthorizationControl)

Authorization control rules governing when and where the card can transact.

##### cardActiveAt (datetime)

Card activation time. Transactions before this time are declined. ISO 8601 format.

##### cardCancelAt (datetime)

Card expiry time. Transactions after this time are declined. ISO 8601 format.

##### allowedMerchantCategories (array of string)

MCC allowlist. Only transactions within these 4-digit MCCs are permitted. Mutually exclusive with blockedMerchantCategories.

##### blockedMerchantCategories (array of string)

MCC blocklist. Transactions within these 4-digit MCCs are declined. Mutually exclusive with allowedMerchantCategories.

##### allowedAuthTimes (integer)

Maximum number of authorizations allowed for the card. Minimum 1.

##### allowedCurrencies (array of string)

Allowed transaction currency allowlist. Each item is a 3-letter ISO 4217 code.

#### metadata (object)

Merchant-defined metadata. Up to 50 keys.

## Error Codes

| resultCode | resultStatus | resultMessage | Troubleshooting |
| --- | --- | --- | --- |
| SUCCESS | S | Success. | N/A |
| CARD_NOT_FOUND | F | Card {id} not found. | Verify the `id` is correct. |
| PARAM_ILLEGAL | F | Illegal parameters exist. | Verify request fields against this reference. |
| USER_NO_PERMISSION | F | User does not have permission. | Retry with a user that has permission for this card. |
| INVALID_CLIENT | F | The client is invalid. | Verify your client credentials. |
| ASSET_NOT_EXIT | F | Asset does not exist. | The card asset does not exist; verify the `id`. |
| PROCESS_FAIL | F | A general business failure occurred. Do not retry. | Do not retry. Contact WorldFirst support. |

## 请求头与 Java 集成

本 Skill 沿用官方仍支持的 RSA 模式：Content-Type、Client-Id、Request-Time、Signature 由 [WfApiClient](../../../common/java/client/WfApiClient.java) 生成，响应强制验签。官网 API Key 示例不与本模板 RSA 请求混用。代理账户头使用 WfRequestContext（account-id / access-token 二选一）；未提供则操作凭证所属账户。公共配置与依赖见 [common README](../../../common/README.md)。

复用 `CardsManagementService`；请求 `QueryCardRequest`，响应 `QueryCardResponse`，均位于本模块 `../java/`。Service 是单次请求薄封装，不负责业务校验、自动重试或异步轮询。

```java
// apiClient 由公共配置构建；请求字段按下方 JSON 示例和参数规格填写。
CardsManagementService service = new CardsManagementService(apiClient);
QueryCardRequest request = new QueryCardRequest();
// 填入本接口必填字段后再调用（不要直接发送空请求）。
QueryCardResponse response = service.queryCard(request);
// 先检查 response.getResult().getResultStatus()，再按模块流程检查业务状态。
// 不输出完整请求/响应，特别是卡号、CVV、令牌、下载链接和 RFI 材料。
```

## 请求 JSON 示例

```json
{
  "id": "CARD_20260624290272001208****"
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
  "id": "CARD_20260624290272001208****",
  "status": "ACTIVE",
  "cardholderId": "CH_20250625290272001208****",
  "mediaType": "VIRTUAL",
  "createdAt": "2026-06-24T06:45:35Z",
  "cardBrand": "MASTERCARD",
  "cardNumber": "****3226",
  "expiryDate": "06/31",
  "last4Digits": "3226",
  "cardNickName": "Team Travel Card",
  "note": "QA note",
  "purpose": "LODGING",
  "email": "cardholder@example.com",
  "mobilePhone": "+86*******0881",
  "billingAddress": {
    "address1": "No.1 Test Street",
    "city": "Shanghai",
    "state": "Shanghai",
    "region": "CN",
    "zipCode": "000000"
  },
  "transactionLimits": [
    {
      "currency": "USD",
      "limits": [
        {
          "interval": "PER_TRANSACTION",
          "rangeAmount": 100000,
          "usedAmount": 0,
          "remainAmount": 100000
        },
        {
          "interval": "DAILY",
          "rangeAmount": 200000,
          "usedAmount": 0,
          "remainAmount": 200000
        },
        {
          "interval": "MONTHLY",
          "rangeAmount": 300000,
          "usedAmount": 0,
          "remainAmount": 300000
        },
        {
          "interval": "ALL_TIME",
          "rangeAmount": 400000,
          "usedAmount": 0,
          "remainAmount": 400000
        }
      ]
    }
  ],
  "authorizationControl": {
    "allowedAuthTimes": 5,
    "allowedCurrencies": [
      "USD"
    ],
    "cardActiveAt": "2026-06-24T09:29:59Z",
    "cardCancelAt": "2026-06-27T07:30:00Z"
  }
}
```

## 生成后检查

对照上表检查全部字段（包括嵌套对象及列表）；String 保留日期时区和未知枚举，金额用公共 Amount。编译并核对实际包名/import；不能把 HTTP 成功当作业务成功。具体状态、重试和分页规则见模块 README 与本页错误表。
