# Update a Card

> 规格来源：[官方 update_a_card](https://docs.worldfirst.com/wfdocs/api-sdk/update_a_card)；核对日期：2026-11-18。
> 生成前先读 [模块 README](../README.md)。保留官方字段、大小写、必填条件及本接口错误表；不要由其他模块推断。

`POST /api/open/v1/issuing/cards/update`

Update a card's status, nickname, contact info, transaction limits, or authorization controls. Only pass the fields you want to change.

## Request Parameters

#### requestId (string, REQUIRED)

Idempotency key for this card-creation request.

#### id (string, REQUIRED)

Unique card identifier. Max 64 characters.

#### status (string)

Target card status. Pass only to change the card state.

-   `FROZEN`: Freeze the card for risk or security reasons.
-   `CANCELLED`: Cancel the card. This is terminal and cannot be undone.
-   `ACTIVE`: Unfreeze the card and resume transactions.

#### cardNickName (string)

Card nickname. Max 120 characters.

#### note (string)

Free-text note. Max 512 characters.

#### purpose (string)

Card purpose description. Max 64 characters.

#### email (string)

Email associated with the card. Max 256 characters.

#### mobilePhone (string)

Mobile phone associated with the card, including the international dialing code. Max 32 characters.

#### billingAddress (Address)

Billing address. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### transactionLimits (array of TransactionLimits)

Transaction limit configuration, aggregated by currency. Maximum 5 entries. Replaced as a whole — pass the complete configuration. On update, each limit only needs interval and rangeAmount.

##### currency (string)

Limit currency. 3-letter ISO 4217 code. Supported value: USD.

##### limits (array of TransactionLimit)

Limit rules for this currency. Maximum 4 entries (one per interval).

###### interval (string)

Limit period. Supported values: PER_TRANSACTION, DAILY, MONTHLY, and ALL_TIME.

###### rangeAmount (integer)

Limit ceiling, in the smallest currency unit. Minimum 1.

#### authorizationControl (AuthorizationControl)

Authorization control rules. Replaced as a whole — pass the complete configuration.

##### cardActiveAt (datetime)

Card activation time. Transactions before this time are declined. ISO 8601 format.

##### cardCancelAt (datetime)

Card expiry time. Transactions after this time are declined. ISO 8601 format; must be later than cardActiveAt.

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

## Response Parameters

#### result (Result)

See [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result.md).

#### id (string)

Unique card identifier. Max 64 characters.

#### status (string)

Current card status after the update (PENDING, ACTIVE, FROZEN, FAILED, or CANCELLED).

## Error Codes

| resultCode | resultStatus | resultMessage | Troubleshooting |
| --- | --- | --- | --- |
| SUCCESS | S | Success. | N/A |
| CARD_NOT_FOUND | F | Card {id} not found. | Verify the `id` is correct. |
| CARD_STATUS_INVALID | F | Card status does not allow this update. | The current card status does not permit the requested change. |
| PARAM_ILLEGAL | F | Illegal parameters exist. | Verify request fields against this reference. |
| USER_NO_PERMISSION | F | User does not have permission. | Retry with a user that has permission for this card. |
| AUTHORIZATION_NOT_EXIST | F | The authorization does not exist. | Verify the `account-id` / `access-token` authorization. |
| PROCESS_FAIL | F | A general business failure occurred. Do not retry. | Do not retry. Contact WorldFirst support. |

## 请求头与 Java 集成

本 Skill 沿用官方仍支持的 RSA 模式：Content-Type、Client-Id、Request-Time、Signature 由 [WfApiClient](../../../common/java/client/WfApiClient.java) 生成，响应强制验签。官网 API Key 示例不与本模板 RSA 请求混用。代理账户头使用 WfRequestContext（account-id / access-token 二选一）；未提供则操作凭证所属账户。公共配置与依赖见 [common README](../../../common/README.md)。

复用 `CardsManagementService`；请求 `UpdateCardRequest`，响应 `UpdateCardResponse`，均位于本模块 `../java/`。Service 是单次请求薄封装，不负责业务校验、自动重试或异步轮询。

```java
// apiClient 由公共配置构建；请求字段按下方 JSON 示例和参数规格填写。
CardsManagementService service = new CardsManagementService(apiClient);
UpdateCardRequest request = new UpdateCardRequest();
// 填入本接口必填字段后再调用（不要直接发送空请求）。
UpdateCardResponse response = service.updateCard(request);
// 先检查 response.getResult().getResultStatus()，再按模块流程检查业务状态。
// 不输出完整请求/响应，特别是卡号、CVV、令牌、下载链接和 RFI 材料。
```

## 请求 JSON 示例

```json
{
  "requestId": "req_card_update_001",
  "id": "2026061819027200120851600100****",
  "status": "FROZEN"
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
  "id": "2026061819027200120851600100****",
  "status": "FROZEN"
}
```

## 生成后检查

对照上表检查全部字段（包括嵌套对象及列表）；String 保留日期时区和未知枚举，金额用公共 Amount。编译并核对实际包名/import；不能把 HTTP 成功当作业务成功。具体状态、重试和分页规则见模块 README 与本页错误表。
