# 报表下载（Download Report）模块

官方规格：[概览](https://docs.worldfirst.com/wfdocs/api-sdk/download_report_overview)；核对日期：2026-11-18。

## 接口列表

| 接口 | GUIDE 路径 | Endpoint |
|---|---|---|
| `create_a_report` | [create-a-report/GUIDE.md](create-a-report/GUIDE.md) | `POST /api/open/v1/reports/create` |
| `query_a_report` | [query-a-report/GUIDE.md](query-a-report/GUIDE.md) | `POST /api/open/v1/reports/query` |
| `list_reports` | [list-reports/GUIDE.md](list-reports/GUIDE.md) | `POST /api/open/v1/reports/list` |

## 集成流程与边界

- 本模块创建可下载报表任务，不是 Statement 明细查询或通用 File 下载。
- 支持 ACCOUNT_STATEMENTS_REPORT，格式 CSV/XLSX/PDF；fromDate/toDate 同时提供或同时省略，日期起止含边界，结束不得早于开始。
- 明确需要的余额类型请显式传 balanceTypes；预算筛选带 budgetAccountIds 会自动加入 BUDGET_BALANCE。PDF 才处理 needEsign/pdfFields。
- 创建返回任务 ID 和 PROCESSING；轮询查询直到 report.status=SUCCESS 或 FAILED（设置间隔和总时限）。注意状态、downloadUrl 位于 report 对象内，不能按顶层读取。
- 列表只有任务摘要，不含下载地址。SUCCESS 后对 report.downloadUrl 直接 HTTP GET，不加 WF 的签名/令牌请求头，也不通过 WfApiClient 拼接 API 基础地址；链接过期重新 query。
- Service 覆盖官方 3 个任务 API，二进制下载由客户 HTTP 客户端处理；下载 URL 不记日志。failureCode 是开放值，不限定枚举。
- resultStatus=S 不等于文件生成成功；PROCESS_FAIL 按接口错误表不可自动重试。

## Java 参考实现

入口 [java/service/DownloadReportService.java](java/service/DownloadReportService.java)，提供 `WfClientConfig` 和 `WfApiClient` 两种构造器。所有接口通过公共 WfApiClient 加签/验签；不在 Service 内校验参数或自动重试。

| 方法 | 请求 / 响应类型 |
|---|---|
| `createReport` | `CreateReportRequest` / `CreateReportResponse` |
| `queryReport` | `QueryReportRequest` / `QueryReportResponse` |
| `listReports` | `ListReportsRequest` / `ListReportsResponse` |

每个方法均有 `(request)` 与 `(request, WfRequestContext context)` 两种重载。复用 common 的 WfRequestContext、WfApiClient、配置、Result、Amount、Address、UserName；上下文只作用于单次调用。RSA 仍受官网支持，本次不改为 API Key。GUIDE 保留完整字段与 JSON 示例，不需要在生成时临时补查接口规格。

- 模型使用 Java 8 POJO、显式 getter/setter，日期和状态保留 String，decimal 用 BigDecimal，扩展 metadata 用 Map。
- 模型的 toString 不输出金融/个人资料；适配 Lombok 后也必须保留脱敏。
- 只追加新错误码；同一 resultCode 在不同接口的重试规则可能不同，以当前 GUIDE 为准，不只依赖全局 retryable 标志。

## 枚举参考


### ReportType

|  |  |
| --- | --- |
| **Value** | **Description** |
| `ACCOUNT_STATEMENTS_REPORT` | Account statement report. Provides an official account statement with transaction history and key financial information for month-end close, audit, and third-party verification. |

### FileFormat

|  |  |
| --- | --- |
| **Value** | **Description** |
| `CSV` | Comma-separated values format. |
| `XLSX` | Microsoft Excel (XLSX) format. |
| `PDF` | PDF format. Supports optional e-signature and extra display columns. |

### ReportStatus

Status of the report generation task, used by Create a Report, Query a Report, and List Reports. A task reaching `SUCCESS` means the report file was generated successfully. For the statuses of transactions recorded in statements, see [StatementStatus](https://docs.worldfirst.com/wfdocs/api-sdk/statement_report_overview.md#Ihfc3).

|  |  |
| --- | --- |
| **Value** | **Description** |
| `PROCESSING` | The report is being generated. |
| `SUCCESS` | The report was generated successfully and can be downloaded via `downloadUrl`. |
| `FAILED` | Report generation failed. See `failureCode` and `failureMessage`. |

### TransactionType

|  |  |
| --- | --- |
| **Value** | **Description** |
| `TRANSFER` | Transfer |
| `TRANSFER_REFUND` | Transfer refund |
| `WITHDRAWAL` | Withdrawal |
| `WITHDRAWAL_REFUND` | Withdrawal refund |
| `CONVERSION` | Currency conversion |
| `CONVERSION_DEAL` | Currency conversion (locked rate) |
| `CHARGE` | Service fee |
| `CHARGE_REFUND` | Service fee refund |
| `DEDUCTION` | Deduction |
| `FUND_COLLECTION` | Fund collection |
| `COLLECTION` | Collection |
| `COLLECTION_REFUND` | Collection refund |
| `PAYMENT` | Payment |
| `CASH_BACK` | Cash back |
| `TOP_UP` | Top-up |

### BalanceType

|  |  |
| --- | --- |
| **Value** | **Description** |
| `NORMAL_BALANCE` | Standard e-commerce balance (default when `balanceTypes` is omitted). |
| `SAME_NAME_TOP_UP_BALANCE` | Same-name top-up balance, funded via a same-name bank account. |
| `BUDGET_BALANCE` | Budget account balance. A user may have multiple budget accounts. Use with `budgetAccountIds` to target specific budgets. |
| `OVERFLOW_BALANCE` | Overflow balance, funds exceeding the budget account limit. |
