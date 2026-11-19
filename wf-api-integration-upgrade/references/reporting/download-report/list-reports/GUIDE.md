# List Reports

> 规格来源：[官方 list_reports](https://docs.worldfirst.com/wfdocs/api-sdk/list_reports)；核对日期：2026-11-18。
> 生成前先读 [模块 README](../README.md)。保留官方字段、大小写、必填条件及本接口错误表；不要由其他模块推断。

`POST /api/open/v1/reports/list`

Retrieve a paginated list of report tasks created by the current account, ordered by creation time (most recent first). Use the cursor fields to page through results. Download links are not included in list responses; call Query a Report to obtain a fresh downloadUrl for a specific report.

## Request Parameters

#### limit (integer)

Maximum number of records to return per page. Range: 1–100. Defaults to 20 if not provided.

#### cursor (string)

Pagination cursor. Omit for the first page; pass the nextCursor or prevCursor from a previous response to fetch the adjacent page. Max length: 2048 characters.

#### fromCreatedAt (datetime)

Filter for report tasks created at or after this time, following the ISO 8601 standard. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### toCreatedAt (datetime)

Filter for report tasks created at or before this time, following the ISO 8601 standard. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

## Response Parameters

#### result (Result)

See [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result.md).

#### items (array)

List of report tasks matching the query. In list responses, download links are not included; call Query a Report to obtain them.

##### id (string)

Unique report task identifier. Use this ID to call Query a Report.

##### status (string)

Report generation task status. See [ReportStatus](https://docs.worldfirst.com/wfdocs/api-sdk/download_report_overview.md). Transaction-level statuses are defined by [StatementStatus](https://docs.worldfirst.com/wfdocs/api-sdk/statement_report_overview.md#Ihfc3).

-   `PROCESSING`: The report is being generated.
-   `SUCCESS`: The report is ready; call Query a Report to obtain the download link.
-   `FAILED`: Report generation failed; see failureCode and failureMessage.

##### failureCode (string)

Business failure reason code. Returned when status is FAILED. This is an open value passed through from the downstream system, not a fixed enum; handle unknown values gracefully.

##### failureMessage (string)

Business failure reason description. Returned when status is FAILED. Use it together with failureCode to determine the root cause.

##### reportType (string)

Report type.

-   `ACCOUNT_STATEMENTS_REPORT`: Account statement report.

##### fileFormat (string)

Output file format.

-   `CSV`: Comma-separated values format.
-   `XLSX`: Microsoft Excel (XLSX) format.
-   `PDF`: PDF format.

##### fileName (string)

File name. Returned once the file name has been determined.

##### fromDate (string)

Start date of the report data range (inclusive), following the ISO 8601 standard. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

##### toDate (string)

End date of the report data range (inclusive), following the ISO 8601 standard. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

##### createdAt (datetime)

Creation time, following the ISO 8601 standard. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

#### nextCursor (string)

Cursor for the next page. Returned when more records are available; pass it as cursor to fetch the next page. Absent on the last page.

#### prevCursor (string)

Cursor for the previous page. Returned when a previous page exists; pass it as cursor to fetch the previous page.

## Error Codes

| resultCode | resultStatus | resultMessage | Troubleshooting |
| --- | --- | --- | --- |
| SUCCESS | S | success | N/A |
| PARAM_ILLEGAL | F | Illegal parameter. Refer to the API documentation to verify the request header and parameters, then retry. | Ensure `limit` is within 1–100 and `cursor` is a value returned by a previous response. |
| USER_NOT_EXIST | F | The specified user does not exist. Verify the credentials and retry. | Verify your authentication credentials and the `account-id` header. |
| AUTHORIZATION_NOT_EXIST | F | The authorization does not exist. Verify the OAuth token and retry. | Verify the `access-token`; re-authorize if it has expired. |
| PROCESS_FAIL | F | A general business failure occurred. Do not retry. | Do not retry. Contact customer support if the issue persists. |

## 请求头与 Java 集成

本 Skill 沿用官方仍支持的 RSA 模式：Content-Type、Client-Id、Request-Time、Signature 由 [WfApiClient](../../../common/java/client/WfApiClient.java) 生成，响应强制验签。官网 API Key 示例不与本模板 RSA 请求混用。代理账户头使用 WfRequestContext（account-id / access-token 二选一）；未提供则操作凭证所属账户。公共配置与依赖见 [common README](../../../common/README.md)。

复用 `DownloadReportService`；请求 `ListReportsRequest`，响应 `ListReportsResponse`，均位于本模块 `../java/`。Service 是单次请求薄封装，不负责业务校验、自动重试或异步轮询。

```java
// apiClient 由公共配置构建；请求字段按下方 JSON 示例和参数规格填写。
DownloadReportService service = new DownloadReportService(apiClient);
ListReportsRequest request = new ListReportsRequest();
// 填入本接口必填字段后再调用（不要直接发送空请求）。
ListReportsResponse response = service.listReports(request);
// 先检查 response.getResult().getResultStatus()，再按模块流程检查业务状态。
// 不输出完整请求/响应，特别是卡号、CVV、令牌、下载链接和 RFI 材料。
```

## 请求 JSON 示例

```json
{
  "limit": 10,
  "fromCreatedAt": "2026-08-01T00:00:00+08:00",
  "toCreatedAt": "2026-08-08T23:59:59+08:00"
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
      "id": "RPT202608080000****",
      "status": "SUCCESS",
      "reportType": "ACCOUNT_STATEMENTS_REPORT",
      "fileFormat": "CSV",
      "fileName": "STATEMENTS_2026-08-01_2026-08-08",
      "fromDate": "2026-08-01",
      "toDate": "2026-08-08",
      "createdAt": "2026-08-08T12:08:56+08:00"
    }
  ],
  "nextCursor": "eyJpZCI6IjQ1NiJ9"
}
```

## 生成后检查

对照上表检查全部字段（包括嵌套对象及列表）；String 保留日期时区和未知枚举，金额用公共 Amount。编译并核对实际包名/import；不能把 HTTP 成功当作业务成功。具体状态、重试和分页规则见模块 README 与本页错误表。
