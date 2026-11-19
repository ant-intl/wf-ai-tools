package {basePackage}.wf.model.request;


/** calculate_a_batch_payout request。可选字段保持 null；完整约束见对应 GUIDE。 */
public class CalculateBatchPayoutRequest {

    /** Required. Unique identifier of the target batch payout. */
    private String id;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "CalculateBatchPayoutRequest[REDACTED]"; }
}
