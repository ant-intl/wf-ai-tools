package {basePackage}.wf.model.response;


/** update_a_card response。可选字段保持 null；完整约束见对应 GUIDE。 */
public class UpdateCardResponse {

    /** See Result. */
    private Result result;

    /** Unique card identifier. Max 64 characters. */
    private String id;

    /** Current card status after the update (PENDING, ACTIVE, FROZEN, FAILED, or CANCELLED). */
    private String status;

    public Result getResult() { return result; }

    public void setResult(Result result) { this.result = result; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "UpdateCardResponse[REDACTED]"; }
}
