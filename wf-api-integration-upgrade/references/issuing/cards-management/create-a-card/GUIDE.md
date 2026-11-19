# Create a Card

> 规格来源：[官方 create_a_card](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_card)；核对日期：2026-11-18。
> 生成前先读 [模块 README](../README.md)。保留官方字段、大小写、必填条件及本接口错误表；不要由其他模块推断。

`POST /api/open/v1/issuing/cards/create`

Issue a new virtual card, optionally linked to a cardholder and a budget account, with transaction limits and authorization controls.

## Request Parameters

#### requestId (string, REQUIRED)

Idempotency key for this card-creation request. Assign a unique value per request to safely retry without issuing duplicate cards. Must be unique within your partnerId. Max 64 characters.

#### cardBrand (string, REQUIRED)

Card network brand.

-   `MASTERCARD`: Mastercard network.

#### cardholderId (string)

Associated cardholder ID. Optional — a card can be created without a cardholder. Max 64 characters.

#### mediaType (string)

Card media type. Defaults to VIRTUAL.

-   `VIRTUAL`: Virtual card.

#### budgetId (string)

Associated budget account ID. Can only be set at creation and cannot be changed later. Max 64 characters.

#### cardNickName (string)

Merchant-defined friendly name for the card. Max 120 characters.

#### note (string)

Free-text note. Max 512 characters.

#### purpose (string)

Card purpose description. Max 64 characters.

#### email (string, REQUIRED)

Cardholder's email address, used for 3DS authentication. Must be a valid email format. Max 256 characters.

#### mobilePhone (string, REQUIRED)

Cardholder's mobile phone number, used for 3DS authentication. Include the international dialing code (for example, +6512345678). Max 32 characters.

#### billingAddress (Address, REQUIRED)

Billing address. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### transactionLimits (array of TransactionLimits)

Transaction limit configuration, aggregated by currency. Maximum 5 entries. At creation, each limit only needs interval and rangeAmount.

##### currency (string, REQUIRED)

Limit currency. 3-letter ISO 4217 code. Supported value: USD.

##### limits (array of TransactionLimit, REQUIRED)

Limit rules for this currency. Maximum 4 entries (one per interval).

###### interval (string, REQUIRED)

Limit period. Supported values: PER_TRANSACTION, DAILY, MONTHLY, and ALL_TIME.

###### rangeAmount (integer, REQUIRED)

Limit ceiling, in the smallest currency unit. Minimum 1.

#### authorizationControl (AuthorizationControl)

Authorization control rules governing when and where the card can transact.

##### cardActiveAt (datetime)

Card activation time. Transactions before this time are declined. ISO 8601 format; must be later than the current time.

##### cardCancelAt (datetime)

Card expiry time. Transactions after this time are declined. ISO 8601 format; must be later than cardActiveAt.

##### allowedMerchantCategories (array of string)

MCC allowlist. Only transactions within these 4-digit MCCs are permitted. Mutually exclusive with blockedMerchantCategories.

##### blockedMerchantCategories (array of string)

MCC blocklist. Transactions within these 4-digit MCCs are declined. Mutually exclusive with allowedMerchantCategories.

##### allowedAuthTimes (integer)

Maximum number of authorizations allowed for the card. Further authorizations are declined once the limit is reached. Minimum 1.

##### allowedCurrencies (array of string)

Allowed transaction currency allowlist. Each item is a 3-letter ISO 4217 code. Omit to allow all currencies.

#### metadata (object)

Merchant-defined metadata. Up to 50 keys.

#### enableSyncActivation (boolean)

Whether to activate the card synchronously. Defaults to asynchronous issuance.

#### threeDsFrictionless (boolean)

Whether to waive the 3DS challenge (true = transactions require no additional 3DS verification).

## Response Parameters

#### result (Result)

See [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result.md).

#### id (string)

Unique card identifier. Use this ID for subsequent query, sensitive-info, and update operations. Max 64 characters.

#### status (string)

Current card status.

-   `PENDING`: Card application submitted, awaiting review or production.
-   `ACTIVE`: Card is activated and ready for transactions.
-   `FAILED`: Card creation failed.

#### cardholderId (string)

Associated cardholder ID. Max 64 characters.

#### mediaType (string)

Card media type.

#### createdAt (datetime)

Creation timestamp. ISO 8601 extended format.

#### cardBrand (string)

Card network brand.

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

Transaction limit configuration per request.

##### currency (string)

Limit currency. Currently, USD is the only supported value.

##### limits (array of TransactionLimit)

Limit rules for this currency.

###### interval (string)

Limit period. Supported values: PER_TRANSACTION, DAILY, MONTHLY, and ALL_TIME.

###### rangeAmount (integer)

Limit ceiling, in the smallest currency unit.

###### usedAmount (integer)

Amount used in the current period. Returned in query responses only.

###### remainAmount (integer)

Remaining available amount in the current period. Returned in query responses only.

#### authorizationControl (AuthorizationControl)

Authorization control rules (activation/expiry time, MCC allow/blocklists, allowed currencies, authorization count cap).

#### metadata (object)

Merchant-defined metadata. Up to 50 keys; key up to 40 characters, value up to 500 characters.

## Error Codes

| resultCode | resultStatus | resultMessage | Troubleshooting |
| --- | --- | --- | --- |
| SUCCESS | S | Success. | N/A |
| PARAM_ILLEGAL | F | Illegal parameters exist. | Provide `requestId`, `cardBrand`, `email`, `mobilePhone`, and `billingAddress`. Verify any supplied `transactionLimits` and `authorizationControl` against their field definitions. |
| RISK_REJECT | F | Request rejected due to risk concerns. | Contact WorldFirst support to review the risk decision. |
| CARDHOLDER_NOT_EXIST | F | Cardholder {cardholderId} not found. | Verify the `cardholderId` is correct, or omit it to create a card without a cardholder. |
| USER_NOT_EXIST | F | The user does not exist. | Retry with correct user information. |
| USER_NO_PERMISSION | F | User does not have permission. | Retry with a user that has card-issuing permission. |
| CONTRACT_CHECK_FAIL | F | The contract check has failed. | Contact WorldFirst support to confirm the contract status, then retry. |
| REPEAT_REQ_INCONSISTENT | F | Repeated requests are inconsistent. | A prior request reused this `requestId` with different parameters. Keep all parameters identical, or use a new `requestId`. |
| UN_SUPPORT_BUSINESS | F | Unsupported business. | The requested card configuration is not supported. Contact WorldFirst support. |
| AUTHORIZATION_NOT_EXIST | F | The authorization does not exist. | Verify the `account-id` / `access-token` authorization before retrying. |
| PROCESS_FAIL | F | A general business failure occurred. Do not retry. | Do not retry. Contact WorldFirst support. |

## 请求头与 Java 集成

本 Skill 沿用官方仍支持的 RSA 模式：Content-Type、Client-Id、Request-Time、Signature 由 [WfApiClient](../../../common/java/client/WfApiClient.java) 生成，响应强制验签。官网 API Key 示例不与本模板 RSA 请求混用。代理账户头使用 WfRequestContext（account-id / access-token 二选一）；未提供则操作凭证所属账户。公共配置与依赖见 [common README](../../../common/README.md)。

复用 `CardsManagementService`；请求 `CreateCardRequest`，响应 `CreateCardResponse`，均位于本模块 `../java/`。Service 是单次请求薄封装，不负责业务校验、自动重试或异步轮询。

```java
// apiClient 由公共配置构建；请求字段按下方 JSON 示例和参数规格填写。
CardsManagementService service = new CardsManagementService(apiClient);
CreateCardRequest request = new CreateCardRequest();
// 填入本接口必填字段后再调用（不要直接发送空请求）。
CreateCardResponse response = service.createCard(request);
// 先检查 response.getResult().getResultStatus()，再按模块流程检查业务状态。
// 不输出完整请求/响应，特别是卡号、CVV、令牌、下载链接和 RFI 材料。
```

## 请求 JSON 示例

```json
{
  "requestId": "req_card_create_001",
  "cardBrand": "MASTERCARD",
  "cardNickName": "Team Travel Card",
  "note": "Marketing Q2 budget",
  "email": "cardholder@example.com",
  "mobilePhone": "+8613800138000",
  "billingAddress": {
    "address1": "No.1 Test Street",
    "address2": "Suite 100",
    "city": "Shanghai",
    "state": "Shanghai",
    "region": "CN",
    "zipCode": "200000"
  },
  "transactionLimits": [
    {
      "currency": "USD",
      "limits": [
        {
          "interval": "PER_TRANSACTION",
          "rangeAmount": 1000
        },
        {
          "interval": "DAILY",
          "rangeAmount": 5000
        },
        {
          "interval": "MONTHLY",
          "rangeAmount": 50000
        },
        {
          "interval": "ALL_TIME",
          "rangeAmount": 200000
        }
      ]
    }
  ],
  "authorizationControl": {
    "allowedAuthTimes": 100,
    "allowedCurrencies": [
      "USD",
      "EUR",
      "GBP"
    ],
    "cardActiveAt": "2026-06-19T00:00:00Z",
    "cardCancelAt": "2027-06-18T00:00:00Z"
  },
  "metadata": {
    "purpose": "vendor-payment",
    "department": "finance"
  }
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
  "id": "CARD_20260618190272001208****",
  "status": "PENDING",
  "cardholderId": "CH_20260302290272001208****",
  "mediaType": "VIRTUAL",
  "createdAt": "2026-06-18T02:06:34Z",
  "cardBrand": "MASTERCARD",
  "cardNickName": "Team Travel Card",
  "note": "Marketing Q2 budget",
  "email": "cardholder@example.com",
  "mobilePhone": "+86*******0881",
  "billingAddress": {
    "city": "Shanghai",
    "region": "CN",
    "zipCode": "200000"
  },
  "transactionLimits": [
    {
      "currency": "USD",
      "limits": [
        {
          "interval": "PER_TRANSACTION",
          "rangeAmount": 1000
        },
        {
          "interval": "DAILY",
          "rangeAmount": 5000
        },
        {
          "interval": "MONTHLY",
          "rangeAmount": 50000
        },
        {
          "interval": "ALL_TIME",
          "rangeAmount": 200000
        }
      ]
    }
  ],
  "authorizationControl": {
    "allowedAuthTimes": 100,
    "allowedCurrencies": [
      "USD",
      "EUR",
      "GBP"
    ],
    "cardActiveAt": "2026-06-19T00:00:00Z",
    "cardCancelAt": "2027-06-18T00:00:00Z"
  }
}
```

## 生成后检查

对照上表检查全部字段（包括嵌套对象及列表）；String 保留日期时区和未知枚举，金额用公共 Amount。编译并核对实际包名/import；不能把 HTTP 成功当作业务成功。具体状态、重试和分页规则见模块 README 与本页错误表。
