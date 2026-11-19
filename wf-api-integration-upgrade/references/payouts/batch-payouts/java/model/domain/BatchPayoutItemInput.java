package {basePackage}.wf.model.domain;


/** BatchPayoutItemInput 领域结构。可选字段保持 null；完整约束见对应 GUIDE。 */
public class BatchPayoutItemInput {

    /** Required. Idempotency key for this payout item. Assign a unique value to each item and reuse the same value only when retrying an identical item. */
    private String requestId;

    /** Business scene code that identifies the payout use case. If specified, it overrides the businessSceneCode set on the batch for this item. See BusinessSceneCode. */
    private String businessSceneCode;

    /** Required. Payment purpose code used for compliance and routing. Supported values depend on the payout scenario. See PurposeCode. */
    private String purposeCode;

    /** Funds debited from the payer. Provide value on either sourceAmount or payoutAmount, but not both; include currency on both objects. See Data Types. */
    private Amount sourceAmount;

    /** Funds received by the beneficiary. Provide value on either sourceAmount or payoutAmount, but not both; include currency on both objects. See Data Types. */
    private Amount payoutAmount;

    /** Required. Beneficiary information. Required fields depend on the payout scenario; use the beneficiary definition of the matching scenario in Create a Payout. */
    private BatchPayoutBeneficiary beneficiary;

    /** Sender information. Required when the batch oboPreference is NEED_OBO. Omit it to debit the default balance account. Same structure as in Create a Payout. */
    private BatchPayoutPayer payer;

    /** How the CNY quota is consumed for this payout. Same structure as in Create a Payout. */
    private BatchPayoutQuotaPreference quotaConsumptionPreference;

    /** Bank transfer reference that may appear on the beneficiary&#x27;s bank statement. */
    private String reference;

    /** Internal memo for your records. It is not visible to the beneficiary. */
    private String transferMemo;

    /** Additional fields for WorldFirst-defined business scenarios. Same structure as in Create a Payout. */
    private BatchPayoutAdditionalInfo additionalInfo;

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

    public String getBusinessSceneCode() { return businessSceneCode; }

    public void setBusinessSceneCode(String businessSceneCode) { this.businessSceneCode = businessSceneCode; }

    public String getPurposeCode() { return purposeCode; }

    public void setPurposeCode(String purposeCode) { this.purposeCode = purposeCode; }

    public Amount getSourceAmount() { return sourceAmount; }

    public void setSourceAmount(Amount sourceAmount) { this.sourceAmount = sourceAmount; }

    public Amount getPayoutAmount() { return payoutAmount; }

    public void setPayoutAmount(Amount payoutAmount) { this.payoutAmount = payoutAmount; }

    public BatchPayoutBeneficiary getBeneficiary() { return beneficiary; }

    public void setBeneficiary(BatchPayoutBeneficiary beneficiary) { this.beneficiary = beneficiary; }

    public BatchPayoutPayer getPayer() { return payer; }

    public void setPayer(BatchPayoutPayer payer) { this.payer = payer; }

    public BatchPayoutQuotaPreference getQuotaConsumptionPreference() { return quotaConsumptionPreference; }

    public void setQuotaConsumptionPreference(BatchPayoutQuotaPreference quotaConsumptionPreference) { this.quotaConsumptionPreference = quotaConsumptionPreference; }

    public String getReference() { return reference; }

    public void setReference(String reference) { this.reference = reference; }

    public String getTransferMemo() { return transferMemo; }

    public void setTransferMemo(String transferMemo) { this.transferMemo = transferMemo; }

    public BatchPayoutAdditionalInfo getAdditionalInfo() { return additionalInfo; }

    public void setAdditionalInfo(BatchPayoutAdditionalInfo additionalInfo) { this.additionalInfo = additionalInfo; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "BatchPayoutItemInput[REDACTED]"; }
}
