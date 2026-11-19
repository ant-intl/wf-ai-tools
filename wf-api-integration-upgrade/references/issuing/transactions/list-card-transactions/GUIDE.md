# List Card Transactions

> 规格来源：[官方 list_card_transactions](https://docs.worldfirst.com/wfdocs/api-sdk/list_card_transactions)；核对日期：2026-11-18。
> 生成前先读 [模块 README](../README.md)。保留官方字段、大小写、必填条件及本接口错误表；不要由其他模块推断。

`POST /api/open/v1/issuing/transactions/list`

List card transaction records with time-range and attribute filters, using cursor-based pagination.

## Request Parameters

#### limit (integer, REQUIRED)

Maximum number of items per page. Value range 1–100 (10 recommended).

#### fromCreatedAt (datetime, REQUIRED)

Query start time. ISO 8601 extended format. The range between fromCreatedAt and toCreatedAt must not exceed 100 days.

#### toCreatedAt (datetime, REQUIRED)

Query end time. ISO 8601 extended format. The range must not exceed 100 days.

#### cursor (string)

Pagination cursor taken from the previous response's nextCursor or prevCursor. Omit for the first page. Max 256 characters.

#### statuses (array of string)

Filter by the latest transaction status. Maximum 9 items.

-   `WAITING_DEDUCT`: Awaiting deduction.
-   `PROCESSING`: Processing.
-   `WAITING_CLEARANCE`: Awaiting clearance.
-   `SUCCESS`: Transaction succeeded.
-   `FAILED`: Transaction failed.
-   `PARTIAL_CANCELLED`: Partial authorization cancelled.
-   `CANCELLED`: Authorization cancelled.
-   `PARTIAL_REFUNDED`: Partially refunded.
-   `REFUNDED`: Refunded.

#### cardNo (string)

Filter by card number. Pass either 4 digits (the last four) or the 16-digit full card number.

#### merchantName (string)

Filter by merchant name. Max 256 characters.

#### id (string)

Filter by transaction ID. Max 64 characters.

#### cardNickName (string)

Filter by card nickname. Max 128 characters.

#### budgetAccountIds (array of string)

Filter by budget account IDs. Maximum 50 items.

#### cardId (string)

Filter by the associated Card resource ID. Max 64 characters.

## Response Parameters

#### result (Result)

See [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result.md).

#### items (array of Transaction)

Card transaction list. Returned only when resultStatus=S.

##### id (string)

Transaction ID. Max 64 characters.

##### createdAt (datetime)

Creation time. ISO 8601 extended format.

##### status (string)

Current transaction status.

-   `WAITING_DEDUCT`: Awaiting deduction.
-   `PROCESSING`: Processing.
-   `WAITING_CLEARANCE`: Awaiting clearance.
-   `SUCCESS`: Transaction succeeded.
-   `FAILED`: Transaction failed.
-   `PARTIAL_CANCELLED`: Partial authorization cancelled.
-   `CANCELLED`: Authorization cancelled.
-   `PARTIAL_REFUNDED`: Partially refunded.
-   `REFUNDED`: Refunded.

##### transactedAt (datetime)

Transaction time. ISO 8601 extended format.

##### updatedAt (datetime)

Update time. Returned only in Webhook notification snapshots. ISO 8601 extended format.

##### maskedCardNo (string)

Masked card number. Max 32 characters.

##### cardNickName (string)

Card nickname. Max 128 characters.

##### cardId (string)

Associated Card resource ID. Returned only for card payment and refund transactions (billType = CARD_PAYMENT or CARD_REFUND). Max 64 characters.

##### merchantName (string)

Merchant name. Max 256 characters.

##### merchantCategoryCode (string)

Merchant category code (MCC). Max 16 characters.

##### merchantRegion (string)

Merchant region. 2-letter ISO 3166 country code.

##### authCode (string)

Authorization code. Max 32 characters.

##### tradeRegion (string)

Transaction region. 2-letter ISO 3166 country code.

##### tradeAmount (Amount)

Transaction amount with currency. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

##### inAmount (Amount)

Inflow amount credited to the card, for example, a refund. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

##### outAmount (Amount)

Outflow amount debited from the card. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

##### exchangeRate (string)

Exchange rate applied to the transaction. Max 32 characters.

##### billType (string)

Bill type.

-   `CARD_PAYMENT`: Card payment.
-   `CARD_REFUND`: An unreferenced refund, a refund that is not linked to an original card payment transaction.

##### payerAccountNo (string)

Payer account number. Returned by query only. Max 64 characters.

##### payerBalanceType (string)

Payer account type.

-   `NORMAL_BALANCE`: Standard balance.
-   `SAME_NAME_TOP_UP_BALANCE`: Same-name top-up balance.
-   `BUDGET_BALANCE`: Budget balance.

##### payerBudgetId (string)

Payer budget account ID. Max 64 characters.

##### failureCode (string)

Transaction failure reason code. Returned only when status=FAILED.

##### failureMessage (string)

Transaction failure description. Returned only when status=FAILED.

#### nextCursor (string)

Cursor for the next page. An empty value indicates there is no next page. Max 256 characters.

#### prevCursor (string)

Cursor for the previous page. An empty value indicates there is no previous page. Max 256 characters.

## Error Codes

| resultCode | resultStatus | resultMessage | Troubleshooting |
| --- | --- | --- | --- |
| SUCCESS | S | Success. | N/A |
| PARAM_ILLEGAL | F | Illegal parameters exist. For example, an invalid date or the time period exceeds 100 days. | Verify request fields, especially the date range (≤ 100 days). |
| CONTRACT_NOT_EXIST | F | The contract check has failed. | Contact WorldFirst support to confirm the contract status, then retry. |
| ACCESS_TOKEN_EXPIRED | F | The access token expired. | Refresh the token to obtain a new access token, then retry. |
| AUTHORIZATION_NOT_EXIST | F | The authorization does not exist. | Contact WorldFirst support to verify the authorization relationship. |
| USER_NOT_EXIST | F | The user does not exist. | Verify the `account-id` or `access-token`. |

## 请求头与 Java 集成

本 Skill 沿用官方仍支持的 RSA 模式：Content-Type、Client-Id、Request-Time、Signature 由 [WfApiClient](../../../common/java/client/WfApiClient.java) 生成，响应强制验签。官网 API Key 示例不与本模板 RSA 请求混用。代理账户头使用 WfRequestContext（account-id / access-token 二选一）；未提供则操作凭证所属账户。公共配置与依赖见 [common README](../../../common/README.md)。

复用 `CardTransactionService`；请求 `ListCardTransactionsRequest`，响应 `ListCardTransactionsResponse`，均位于本模块 `../java/`。Service 是单次请求薄封装，不负责业务校验、自动重试或异步轮询。

```java
// apiClient 由公共配置构建；请求字段按下方 JSON 示例和参数规格填写。
CardTransactionService service = new CardTransactionService(apiClient);
ListCardTransactionsRequest request = new ListCardTransactionsRequest();
// 填入本接口必填字段后再调用（不要直接发送空请求）。
ListCardTransactionsResponse response = service.listCardTransactions(request);
// 先检查 response.getResult().getResultStatus()，再按模块流程检查业务状态。
// 不输出完整请求/响应，特别是卡号、CVV、令牌、下载链接和 RFI 材料。
```

## 请求 JSON 示例

```json
{
  "limit": 10,
  "fromCreatedAt": "2026-01-01T00:00:00+08:00",
  "toCreatedAt": "2026-01-31T00:00:00+08:00",
  "statuses": [
    "SUCCESS"
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
  "items": [
    {
      "id": "2026****",
      "createdAt": "2026-01-01T12:01:01+08:00",
      "status": "SUCCESS",
      "transactedAt": "2026-01-15T12:01:01+08:00",
      "maskedCardNo": "************9509",
      "cardNickName": "My Travel Card",
      "cardId": "2026****",
      "merchantName": "AMAZON****",
      "merchantCategoryCode": "5411",
      "merchantRegion": "US",
      "authCode": "123456",
      "tradeRegion": "US",
      "tradeAmount": {
        "value": 10000,
        "currency": "USD"
      },
      "inAmount": {
        "value": 0,
        "currency": "USD"
      },
      "outAmount": {
        "value": 10000,
        "currency": "USD"
      },
      "exchangeRate": "7.1234",
      "billType": "CARD_PAYMENT",
      "payerAccountNo": "2208****",
      "payerBudgetId": "34589****",
      "payerBalanceType": "NORMAL_BALANCE"
    }
  ],
  "nextCursor": "eyJwYWdlIjoyfQ=="
}
```

## 生成后检查

对照上表检查全部字段（包括嵌套对象及列表）；String 保留日期时区和未知枚举，金额用公共 Amount。编译并核对实际包名/import；不能把 HTTP 成功当作业务成功。具体状态、重试和分页规则见模块 README 与本页错误表。
