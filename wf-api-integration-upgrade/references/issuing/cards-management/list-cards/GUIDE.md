# List Cards

> 规格来源：[官方 list_cards](https://docs.worldfirst.com/wfdocs/api-sdk/list_cards)；核对日期：2026-11-18。
> 生成前先读 [模块 README](../README.md)。保留官方字段、大小写、必填条件及本接口错误表；不要由其他模块推断。

`POST /api/open/v1/issuing/cards/list`

List cards under the current account with optional filters and cursor-based pagination. Each item is a Card summary view.

## Request Parameters

#### limit (integer)

Number of records per page. Value range 1–100. Defaults to 20.

#### cursor (string)

Pagination cursor token taken from the previous response's nextCursor or prevCursor. Omit for the first page.

#### status (string)

Filter by card status. Omit to include all statuses (including CANCELLED).

-   `PENDING`: Card application submitted, awaiting review or production.
-   `ACTIVE`: Card is activated and ready for transactions.
-   `FROZEN`: Card is frozen; transactions are paused and can be resumed.
-   `FAILED`: Card creation failed (terminal).
-   `CANCELLED`: Card cancelled (terminal).

#### last4Digits (string)

Filter by the last 4 digits of the card number (exact match). Max 4 characters.

#### cardNickName (string)

Filter by card nickname (fuzzy match). Max 120 characters.

#### budgetId (string)

The unique ID assigned by WorldFirst to identify a budget account. When specified, only cards under this budget account are returned; if omitted, cards under the primary budget account are returned by default.

## Response Parameters

#### result (Result)

See [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result.md).

#### items (array of Card)

Card summary array. Maximum 100 entries. Each element includes only summary fields; fetch full transaction limits and authorization controls via Query a Card.

##### id (string)

Unique card identifier. Max 64 characters.

##### status (string)

Current card status.

##### createdAt (datetime)

Creation timestamp. ISO 8601 extended format.

##### cardholderId (string)

Associated cardholder ID. Max 64 characters.

##### mediaType (string)

Card media type (VIRTUAL or PHYSICAL).

##### cardBrand (string)

Card network brand (VISA or MASTERCARD).

##### cardNumber (string)

Masked card number. Max 19 characters.

##### last4Digits (string)

Last 4 digits of the card number. Max 4 characters.

##### cardNickName (string)

Merchant-defined friendly name. Max 120 characters.

##### metadata (object)

Merchant-defined metadata. Up to 50 keys.

#### nextCursor (string)

Cursor for the next page. null indicates the last page.

#### prevCursor (string)

Cursor for the previous page. null indicates the first page.

## Error Codes

| resultCode | resultStatus | resultMessage | Troubleshooting |
| --- | --- | --- | --- |
| SUCCESS | S | Success. | N/A |
| PARAM_ILLEGAL | F | Illegal parameters exist. | Verify request fields against this reference. |
| USER_NO_PERMISSION | F | User does not have permission. | Retry with a user that has permission. |
| INVALID_CLIENT | F | The client is invalid. | Verify your client credentials. |
| USER_NOT_EXIST | F | The user does not exist. | Retry with correct user information. |
| ASSET_NOT_EXIT | F | Asset does not exist. | Verify the query filters. |
| PROCESS_FAIL | F | A general business failure occurred. Do not retry. | Do not retry. Contact WorldFirst support. |

## 请求头与 Java 集成

本 Skill 沿用官方仍支持的 RSA 模式：Content-Type、Client-Id、Request-Time、Signature 由 [WfApiClient](../../../common/java/client/WfApiClient.java) 生成，响应强制验签。官网 API Key 示例不与本模板 RSA 请求混用。代理账户头使用 WfRequestContext（account-id / access-token 二选一）；未提供则操作凭证所属账户。公共配置与依赖见 [common README](../../../common/README.md)。

复用 `CardsManagementService`；请求 `ListCardsRequest`，响应 `ListCardsResponse`，均位于本模块 `../java/`。Service 是单次请求薄封装，不负责业务校验、自动重试或异步轮询。

```java
// apiClient 由公共配置构建；请求字段按下方 JSON 示例和参数规格填写。
CardsManagementService service = new CardsManagementService(apiClient);
ListCardsRequest request = new ListCardsRequest();
// 填入本接口必填字段后再调用（不要直接发送空请求）。
ListCardsResponse response = service.listCards(request);
// 先检查 response.getResult().getResultStatus()，再按模块流程检查业务状态。
// 不输出完整请求/响应，特别是卡号、CVV、令牌、下载链接和 RFI 材料。
```

## 请求 JSON 示例

```json
{
  "limit": 3
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
  "items": [
    {
      "id": "2026062529027200120855200003801",
      "status": "ACTIVE",
      "createdAt": "2026-06-25T15:08:06Z",
      "cardholderId": "2025062529027200120854400001454",
      "mediaType": "VIRTUAL",
      "cardBrand": "MASTERCARD",
      "cardNumber": "****5427",
      "last4Digits": "5427",
      "cardNickName": "Example Card"
    },
    {
      "id": "2026062529027200120855200003802",
      "status": "ACTIVE",
      "createdAt": "2026-06-25T15:09:12Z",
      "cardholderId": "2025062529027200120854400001454",
      "mediaType": "VIRTUAL",
      "cardBrand": "MASTERCARD",
      "cardNumber": "****7340",
      "last4Digits": "7340",
      "cardNickName": "Example Card"
    }
  ],
  "nextCursor": "eyJpZCI6****"
}
```

## 生成后检查

对照上表检查全部字段（包括嵌套对象及列表）；String 保留日期时区和未知枚举，金额用公共 Amount。编译并核对实际包名/import；不能把 HTTP 成功当作业务成功。具体状态、重试和分页规则见模块 README 与本页错误表。
