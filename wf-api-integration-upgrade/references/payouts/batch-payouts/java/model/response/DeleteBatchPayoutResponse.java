package {basePackage}.wf.model.response;


/** delete_a_batch_payout response。可选字段保持 null；完整约束见对应 GUIDE。 */
public class DeleteBatchPayoutResponse {

    /** See Result. */
    private Result result;

    public Result getResult() { return result; }

    public void setResult(Result result) { this.result = result; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "DeleteBatchPayoutResponse[REDACTED]"; }
}
