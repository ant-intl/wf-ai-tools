package {basePackage}.wf.model.request;


/** submit_a_batch_payout request。可选字段保持 null；完整约束见对应 GUIDE。 */
public class SubmitBatchPayoutRequest {

    /** Required. Unique identifier of the batch payout to submit. The batch must be in INIT status, contain at least one item, and have every item pass validation before submission. */
    private String id;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "SubmitBatchPayoutRequest[REDACTED]"; }
}
