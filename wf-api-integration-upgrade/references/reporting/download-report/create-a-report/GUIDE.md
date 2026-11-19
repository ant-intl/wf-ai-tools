# Create a Report

> 规格来源：[官方 create_a_report](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_report)；核对日期：2026-11-18。
> 生成前先读 [模块 README](../README.md)。保留官方字段、大小写、必填条件及本接口错误表；不要由其他模块推断。

`POST /api/open/v1/reports/create`

Create an asynchronous report generation task. The report is not returned immediately; use the returned id to poll Query a Report until the status becomes SUCCESS, then download the file by sending a GET request directly to the returned downloadUrl.

## Request Parameters

#### requestId (string, REQUIRED)

Idempotency key. Use a unique value (e.g., UUID) for each creation.

#### reportType (string, REQUIRED)

Type of report to generate.

-   `ACCOUNT_STATEMENTS_REPORT`: Account statement report for reconciliation, audit, and third-party verification.

#### fileFormat (string, REQUIRED)

Output file format.

-   `CSV`: Comma-separated values format.
-   `XLSX`: Microsoft Excel (XLSX) format.
-   `PDF`: PDF format; supports needEsign and pdfFields.

#### language (string)

Report language. Defaults to en_US when omitted.

-   `en_US`: English.
-   `zh_CN`: Simplified Chinese.

#### fromDate (string)

Start date of the report data range (inclusive), following the ISO 8601 standard. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md). Provide fromDate and toDate together or omit both; toDate must be greater than or equal to fromDate.

#### toDate (string)

End date of the report data range (inclusive), following the ISO 8601 standard. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md). Provide fromDate and toDate together or omit both.

#### currencies (array)

Filter the report to specific currencies, following the ISO 4217 standard. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md). Up to 10 currencies. Omit to include all currencies.

#### transactionTypes (array)

Filter the report to specific transaction types. Omit to include all transaction types. See [TransactionType](https://docs.worldfirst.com/wfdocs/api-sdk/download_report_overview.md#oIX4W).

#### balanceTypes (array)

Filter the report to specific balance types. Omit to use the server default; explicitly supply balanceTypes when a specific balance selection is required. See [BalanceType](https://docs.worldfirst.com/wfdocs/api-sdk/download_report_overview.md#qbJBv).

#### budgetAccountIds (array)

Filter by budget account IDs. When provided, the BUDGET_BALANCE balance type is added automatically.

#### needEsign (boolean)

Whether the report requires an electronic signature. Defaults to false. Effective only when fileFormat is PDF.

#### pdfFields (array)

Extra display columns for the PDF report. Effective only when fileFormat is PDF. Omit to use the default columns.

-   `accountingType`: Include the accounting type column.
-   `description`: Include the description column.

#### fileName (string)

Custom file name. A name is generated automatically when omitted.

## Response Parameters

#### result (Result)

See [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result.md).

#### id (string)

Unique report task identifier. Use this ID to poll Query a Report. Once status is SUCCESS, download the file from the returned downloadUrl.

#### status (string)

Report generation task status. See [ReportStatus](https://docs.worldfirst.com/wfdocs/api-sdk/download_report_overview.md). A newly created task starts as PROCESSING. Transaction-level statuses are defined by [StatementStatus](https://docs.worldfirst.com/wfdocs/api-sdk/statement_report_overview.md#Ihfc3).

-   `PROCESSING`: The report is being generated.
-   `SUCCESS`: The report is ready for download.
-   `FAILED`: Report generation failed.

#### createdAt (datetime)

Creation time, following the ISO 8601 standard. See [Data Types](https://docs.worldfirst.com/wfdocs/api-sdk/data_types.md).

## Error Codes

| resultCode | resultStatus | resultMessage | Troubleshooting |
| --- | --- | --- | --- |
| SUCCESS | S | success | N/A |
| PARAM_ILLEGAL | F | Illegal parameter. Refer to the API documentation to verify the request header and parameters, then retry. | Check `reportType`, `fileFormat`, and the `fromDate`/`toDate` pair (both provided or both omitted, `toDate` ≥ `fromDate`). |
| REPEAT_REQ_INCONSISTENT | F | A request with the same requestId already exists but with different parameters. Use a new requestId or the same parameters. | Reuse the original parameters for the same `requestId`, or generate a new `requestId`. |
| USER_NOT_EXIST | F | The specified user does not exist. Verify the credentials and retry. | Verify your authentication credentials and the `account-id` header. |
| AUTHORIZATION_NOT_EXIST | F | The authorization does not exist. Verify the OAuth token and retry. | Verify the `access-token`; re-authorize if it has expired. |
| PROCESS_FAIL | F | A general business failure occurred. Do not retry. | Do not retry. Contact customer support if the issue persists. |

## 请求头与 Java 集成

本 Skill 沿用官方仍支持的 RSA 模式：Content-Type、Client-Id、Request-Time、Signature 由 [WfApiClient](../../../common/java/client/WfApiClient.java) 生成，响应强制验签。官网 API Key 示例不与本模板 RSA 请求混用。代理账户头使用 WfRequestContext（account-id / access-token 二选一）；未提供则操作凭证所属账户。公共配置与依赖见 [common README](../../../common/README.md)。

复用 `DownloadReportService`；请求 `CreateReportRequest`，响应 `CreateReportResponse`，均位于本模块 `../java/`。Service 是单次请求薄封装，不负责业务校验、自动重试或异步轮询。

```java
// apiClient 由公共配置构建；请求字段按下方 JSON 示例和参数规格填写。
DownloadReportService service = new DownloadReportService(apiClient);
CreateReportRequest request = new CreateReportRequest();
// 填入本接口必填字段后再调用（不要直接发送空请求）。
CreateReportResponse response = service.createReport(request);
// 先检查 response.getResult().getResultStatus()，再按模块流程检查业务状态。
// 不输出完整请求/响应，特别是卡号、CVV、令牌、下载链接和 RFI 材料。
```

## 请求 JSON 示例

```json
{
  "requestId": "f18d0117-095a-4782-a9f1-eeb16c3b0808",
  "reportType": "ACCOUNT_STATEMENTS_REPORT",
  "fileFormat": "CSV",
  "fromDate": "2026-08-01T00:00:00+08:00",
  "toDate": "2026-08-08T23:59:59+08:00"
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
  "id": "RPT202608080000****",
  "status": "PROCESSING",
  "createdAt": "2026-08-08T12:08:56+08:00"
}
```

## 生成后检查

对照上表检查全部字段（包括嵌套对象及列表）；String 保留日期时区和未知枚举，金额用公共 Amount。编译并核对实际包名/import；不能把 HTTP 成功当作业务成功。具体状态、重试和分页规则见模块 README 与本页错误表。
