package {basePackage}.wf.model.response;


/** create_a_report response。可选字段保持 null；完整约束见对应 GUIDE。 */
public class CreateReportResponse {

    /** See Result. */
    private Result result;

    /** Unique report task identifier. Use this ID to poll Query a Report. Once status is SUCCESS, download the file from the returned downloadUrl. */
    private String id;

    /** Report generation task status. See ReportStatus. A newly created task starts as PROCESSING. Transaction-level statuses are defined by StatementStatus. - `PROCESSING`: The report is being generated. - `SUCCESS`: The report is ready for download. - `FAILED`: Report generation failed. */
    private String status;

    /** Creation time, following the ISO 8601 standard. See Data Types. */
    private String createdAt;

    public Result getResult() { return result; }

    public void setResult(Result result) { this.result = result; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getCreatedAt() { return createdAt; }

    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "CreateReportResponse[REDACTED]"; }
}
