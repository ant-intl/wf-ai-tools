package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.BatchPayoutCalculationSummary;

/** cancel_a_batch_payout response。可选字段保持 null；完整约束见对应 GUIDE。 */
public class CancelBatchPayoutResponse {

    /** See Result. */
    private Result result;

    /** Unique identifier of the batch payout. */
    private String id;

    /** Batch status after cancellation. Always CANCELLED. See BatchPayoutStatus. */
    private String status;

    /** Time the batch was created, in ISO 8601 extended format. */
    private String createdAt;

    /** Time the batch was most recently updated, in ISO 8601 extended format. */
    private String updatedAt;

    /** Idempotency key supplied when the batch was created. */
    private String requestId;

    /** Human-readable batch name, if provided at creation. */
    private String batchName;

    /** Payment channel type set on the batch, if provided at creation. See PaymentType. */
    private String paymentType;

    /** Time the cancellation was recorded, in ISO 8601 extended format. */
    private String cancelledAt;

    /** Note describing why the batch is being cancelled if provided. */
    private String memo;

    /** Total number of items in the batch at cancellation. Range: 0 - 100. */
    private Integer totalItemCount;

    /** Number of items that had passed validation at cancellation. Range: 0 - 100. */
    private Integer validItemCount;

    /** Latest reference pricing summary. Returned once calculate (or submit) has completed pricing. All totals and rates are indicative — final values are determined at payout processing. */
    private BatchPayoutCalculationSummary calculationSummary;

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

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

    public String getBatchName() { return batchName; }

    public void setBatchName(String batchName) { this.batchName = batchName; }

    public String getPaymentType() { return paymentType; }

    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }

    public String getCancelledAt() { return cancelledAt; }

    public void setCancelledAt(String cancelledAt) { this.cancelledAt = cancelledAt; }

    public String getMemo() { return memo; }

    public void setMemo(String memo) { this.memo = memo; }

    public Integer getTotalItemCount() { return totalItemCount; }

    public void setTotalItemCount(Integer totalItemCount) { this.totalItemCount = totalItemCount; }

    public Integer getValidItemCount() { return validItemCount; }

    public void setValidItemCount(Integer validItemCount) { this.validItemCount = validItemCount; }

    public BatchPayoutCalculationSummary getCalculationSummary() { return calculationSummary; }

    public void setCalculationSummary(BatchPayoutCalculationSummary calculationSummary) { this.calculationSummary = calculationSummary; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "CancelBatchPayoutResponse[REDACTED]"; }
}
