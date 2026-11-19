package {basePackage}.wf.model.request;


/** cancel_a_batch_payout request。可选字段保持 null；完整约束见对应 GUIDE。 */
public class CancelBatchPayoutRequest {

    /** Required. Unique identifier of the batch payout to cancel. The batch must be in INIT or PENDING_APPROVAL status. */
    private String id;

    /** Optional note describing why the batch is being cancelled. Max length: 256 characters. */
    private String memo;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getMemo() { return memo; }

    public void setMemo(String memo) { this.memo = memo; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "CancelBatchPayoutRequest[REDACTED]"; }
}
