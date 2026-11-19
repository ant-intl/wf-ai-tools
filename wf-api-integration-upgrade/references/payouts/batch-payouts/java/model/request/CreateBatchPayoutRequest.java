package {basePackage}.wf.model.request;


/** create_a_batch_payout request。可选字段保持 null；完整约束见对应 GUIDE。 */
public class CreateBatchPayoutRequest {

    /** Required. Idempotency key. Assign a unique value (e.g., UUID) per batch to safely retry without creating duplicates. Max length: 64 characters. */
    private String requestId;

    /** Human-readable name for the batch (e.g., Payroll 2026-08). Not visible to beneficiaries. Max length: 128 characters. */
    private String batchName;

    /** Business scene code that applies to every item in the batch. Individual items may override this value when added. See BusinessSceneCode. */
    private String businessSceneCode;

    /** The full value transfer preference for the batch. If specified, it applies to all items in the batch. Set NEED_FVT to request guaranteed full-amount delivery (payer covers intermediary fees). See FvtPreference. */
    private String fvtPreference;

    /** The On-Behalf-Of routing preference for the batch. If specified, it applies to all items in the batch. See OboPreference. */
    private String oboPreference;

    /** The payment channel type for the batch. If specified, it applies to all items in the batch. Required if paymentNetwork is specified. See PaymentType. */
    private String paymentType;

    /** The payment clearing network for the batch. If specified, it applies to all items in the batch. Required ifpaymentType is specified. See PaymentNetwork. */
    private String paymentNetwork;

    /** Internal memo attached to the batch for your own records. Not visible to beneficiaries. Max length: 256 characters. */
    private String transferMemo;

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

    public String getBatchName() { return batchName; }

    public void setBatchName(String batchName) { this.batchName = batchName; }

    public String getBusinessSceneCode() { return businessSceneCode; }

    public void setBusinessSceneCode(String businessSceneCode) { this.businessSceneCode = businessSceneCode; }

    public String getFvtPreference() { return fvtPreference; }

    public void setFvtPreference(String fvtPreference) { this.fvtPreference = fvtPreference; }

    public String getOboPreference() { return oboPreference; }

    public void setOboPreference(String oboPreference) { this.oboPreference = oboPreference; }

    public String getPaymentType() { return paymentType; }

    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }

    public String getPaymentNetwork() { return paymentNetwork; }

    public void setPaymentNetwork(String paymentNetwork) { this.paymentNetwork = paymentNetwork; }

    public String getTransferMemo() { return transferMemo; }

    public void setTransferMemo(String transferMemo) { this.transferMemo = transferMemo; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "CreateBatchPayoutRequest[REDACTED]"; }
}
