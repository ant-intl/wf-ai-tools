package {basePackage}.wf.model.response;


/** response_to_an_rfi response。可选字段保持 null；完整约束见对应 GUIDE。 */
public class RespondToRfiResponse {

    /** See Result. */
    private Result result;

    /** Unique identifier of the RFI. */
    private String id;

    /** Status of the RFI after the operation. Fixed to ANSWERED on a successful response. See RfiStatus. */
    private String status;

    /** Time the RFI was created. ISO 8601 UTC format (ending with Z). */
    private String createdAt;

    /** Time the RFI was updated by this operation. ISO 8601 UTC format (ending with Z). */
    private String updatedAt;

    public Result getResult() { return result; }

    public void setResult(Result result) { this.result = result; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getCreatedAt() { return createdAt; }

    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "RespondToRfiResponse[REDACTED]"; }
}
