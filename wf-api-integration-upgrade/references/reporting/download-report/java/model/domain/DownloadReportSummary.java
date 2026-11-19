package {basePackage}.wf.model.domain;


/** DownloadReportSummary 领域结构。可选字段保持 null；完整约束见对应 GUIDE。 */
public class DownloadReportSummary {

    /** Unique report task identifier. Use this ID to call Query a Report. */
    private String id;

    /** Report generation task status. See ReportStatus. Transaction-level statuses are defined by StatementStatus. - `PROCESSING`: The report is being generated. - `SUCCESS`: The report is ready; call Query a Report to obtain the download link. - `FAILED`: Report generation failed; see failureCode and failureMessage. */
    private String status;

    /** Business failure reason code. Returned when status is FAILED. This is an open value passed through from the downstream system, not a fixed enum; handle unknown values gracefully. */
    private String failureCode;

    /** Business failure reason description. Returned when status is FAILED. Use it together with failureCode to determine the root cause. */
    private String failureMessage;

    /** Report type. - `ACCOUNT_STATEMENTS_REPORT`: Account statement report. */
    private String reportType;

    /** Output file format. - `CSV`: Comma-separated values format. - `XLSX`: Microsoft Excel (XLSX) format. - `PDF`: PDF format. */
    private String fileFormat;

    /** File name. Returned once the file name has been determined. */
    private String fileName;

    /** Start date of the report data range (inclusive), following the ISO 8601 standard. See Data Types. */
    private String fromDate;

    /** End date of the report data range (inclusive), following the ISO 8601 standard. See Data Types. */
    private String toDate;

    /** Creation time, following the ISO 8601 standard. See Data Types. */
    private String createdAt;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getFailureCode() { return failureCode; }

    public void setFailureCode(String failureCode) { this.failureCode = failureCode; }

    public String getFailureMessage() { return failureMessage; }

    public void setFailureMessage(String failureMessage) { this.failureMessage = failureMessage; }

    public String getReportType() { return reportType; }

    public void setReportType(String reportType) { this.reportType = reportType; }

    public String getFileFormat() { return fileFormat; }

    public void setFileFormat(String fileFormat) { this.fileFormat = fileFormat; }

    public String getFileName() { return fileName; }

    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFromDate() { return fromDate; }

    public void setFromDate(String fromDate) { this.fromDate = fromDate; }

    public String getToDate() { return toDate; }

    public void setToDate(String toDate) { this.toDate = toDate; }

    public String getCreatedAt() { return createdAt; }

    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "DownloadReportSummary[REDACTED]"; }
}
