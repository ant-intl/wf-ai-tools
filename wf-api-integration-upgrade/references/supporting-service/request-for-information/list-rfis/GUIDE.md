# List RFIs

> 规格来源：[官方 list_rfis](https://docs.worldfirst.com/wfdocs/api-sdk/list_rfis)；核对日期：2026-11-18。
> 生成前先读 [模块 README](../README.md)。保留官方字段、大小写、必填条件及本接口错误表；不要由其他模块推断。

`POST /api/open/v1/rfis/list`

Returns a paginated list of Requests for Information (RFIs) raised against an account. Results use cursor-based pagination ordered by creation time, and can be filtered by status, type, and creation-time range. Each item is a summary that does not include the questions array; call [Retrieve an RFI](https://docs.worldfirst.com/wfdocs/api-sdk/retrieve_an_rfi.md) to obtain the full question details.

## Request Parameters

#### limit (integer)

Number of records to return per page. Value range: 1–100. Defaults to 20 when omitted.

#### cursor (string)

Pagination token for turning pages. Omit it on the first request; for subsequent pages, pass the nextCursor or prevCursor returned by the previous response.

#### fromCreatedAt (datetime)

Start of the creation-time range to filter by, inclusive. ISO 8601 UTC format (ending with Z). See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### toCreatedAt (datetime)

End of the creation-time range to filter by, inclusive. ISO 8601 UTC format (ending with Z). See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### status (string)

Filters results by RFI status. When omitted, RFIs of all statuses are returned. See [RfiStatus](https://docs.worldfirst.com/wfdocs/api-sdk/request_for_information_overview.md#MZfNh).

#### rfiType (string)

Filters results by RFI type. When omitted, RFIs of all types are returned.

-   `KYC_RFI`: Information request raised during the KYC process.
-   `KYC_ONGOING_CDD_RFI`: Information request raised during ongoing customer due diligence.

#### accountId (string)

Filters results by account ID. When omitted, returns all RFIs for the authenticated account, or for the proxied account specified in the account-id header.

## Response Parameters

#### result (Result)

See [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result.md).

#### nextCursor (string)

Cursor token for the next page. Pass it as cursor to fetch the following page. null when there is no next page.

#### prevCursor (string)

Cursor token for the previous page. Pass it as cursor to fetch the preceding page. null when the current page is the first page.

#### items (array)

List of RFI summaries, ordered by creation time. Each summary omits the questions array; retrieve full question details with [Retrieve an RFI](https://docs.worldfirst.com/wfdocs/api-sdk/retrieve_an_rfi.md).

##### id (string)

Unique identifier of the RFI.

##### accountId (string)

ID of the account the RFI belongs to.

##### rfiType (string)

Type of the RFI, indicating the business scenario it originates from. See [RfiType](https://docs.worldfirst.com/wfdocs/api-sdk/request_for_information_overview.md#i0VUp).

##### status (string)

Current status of the RFI. See [RfiStatus](https://docs.worldfirst.com/wfdocs/api-sdk/request_for_information_overview.md#MZfNh).

##### instruction (string)

Human-readable, top-level instruction describing what the merchant needs to provide. Returned only when the RFI has a top-level instruction.

##### expiresAt (datetime)

Deadline for answering the RFI; the RFI is automatically marked EXPIRED once passed. ISO 8601 UTC format (ending with Z). Returned only when the RFI has a deadline.

##### createdAt (datetime)

Time the RFI was created. ISO 8601 UTC format (ending with Z).

##### updatedAt (datetime)

Time the RFI was last updated. ISO 8601 UTC format (ending with Z).

## Error Codes

| resultCode | resultStatus | resultMessage | Troubleshooting |
| --- | --- | --- | --- |
| SUCCESS | S | success | N/A |
| AUTHORIZATION_NOT_EXIST | F | You do not have permission to operate this RFI. Please verify your account-id. | Check the `account-id` header is linked to the calling client. |
| PARAM_ILLEGAL | F | The request parameters are invalid. | Check `limit` is within 1–100 and `fromCreatedAt` / `toCreatedAt` follow ISO 8601 UTC. |
| PROCESS_FAIL | F | A general business failure occurred. | Retry later; if it persists, contact WorldFirst support. |

## 请求头与 Java 集成

本 Skill 沿用官方仍支持的 RSA 模式：Content-Type、Client-Id、Request-Time、Signature 由 [WfApiClient](../../../common/java/client/WfApiClient.java) 生成，响应强制验签。官网 API Key 示例不与本模板 RSA 请求混用。代理账户头使用 WfRequestContext（account-id / access-token 二选一）；未提供则操作凭证所属账户。公共配置与依赖见 [common README](../../../common/README.md)。

复用 `RfiService`；请求 `ListRfisRequest`，响应 `ListRfisResponse`，均位于本模块 `../java/`。Service 是单次请求薄封装，不负责业务校验、自动重试或异步轮询。

```java
// apiClient 由公共配置构建；请求字段按下方 JSON 示例和参数规格填写。
RfiService service = new RfiService(apiClient);
ListRfisRequest request = new ListRfisRequest();
// 填入本接口必填字段后再调用（不要直接发送空请求）。
ListRfisResponse response = service.listRfis(request);
// 先检查 response.getResult().getResultStatus()，再按模块流程检查业务状态。
// 不输出完整请求/响应，特别是卡号、CVV、令牌、下载链接和 RFI 材料。
```

## 请求 JSON 示例

```json
{
  "limit": 20,
  "accountId": "21201203489****3",
  "rfiType": "KYC_ONGOING_CDD_RFI"
}
```

## 响应 JSON 示例

```json
{
  "result": {
    "resultStatus": "S",
    "resultCode": "SUCCESS",
    "resultMessage": "success"
  },
  "nextCursor": null,
  "prevCursor": null,
  "items": [
    {
      "id": "20260818191210001360300000****1",
      "accountId": "21201203489****3",
      "rfiType": "KYC_ONGOING_CDD_RFI",
      "status": "CLOSED",
      "createdAt": "2026-08-18T12:37:05Z",
      "updatedAt": "2026-08-19T02:53:31Z"
    }
  ]
}
```

## 生成后检查

对照上表检查全部字段（包括嵌套对象及列表）；String 保留日期时区和未知枚举，金额用公共 Amount。编译并核对实际包名/import；不能把 HTTP 成功当作业务成功。具体状态、重试和分页规则见模块 README 与本页错误表。
