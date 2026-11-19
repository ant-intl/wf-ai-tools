package {basePackage}.wf.model.domain;

import java.util.List;

/** BatchPayoutItem 领域结构。可选字段保持 null；完整约束见对应 GUIDE。 */
public class BatchPayoutItem {

    /** WorldFirst-assigned unique payout ID. Use this ID for subsequent query calls. */
    private String itemId;

    /** Current payout status. See PayoutStatus. */
    private String status;

    /** Failure reason code. Returned when status is FAILED. Inspect this field to determine the cause and whether to retry. */
    private String failureCode;

    /** A plain-text description of the failure reason. Returned when status is FAILED. */
    private String failureMessage;

    /** Business scene code for this payout. See BusinessSceneCode. */
    private String businessSceneCode;

    /** Payment purpose code applied. See PurposeCode. */
    private String purposeCode;

    /** Source currency amount debited from the payer&#x27;s account. See Data Types. */
    private Amount sourceAmount;

    /** Target currency amount received (or to be received) by the beneficiary. See Data Types. */
    private Amount payoutAmount;

    /** Exchange rate applied to this payout. Returned for cross-currency payouts. */
    private BatchPayoutQuote transferQuote;

    /** Payer information recorded for this payout. */
    private BatchPayoutPayer payer;

    /** Beneficiary information recorded for this payout. */
    private BatchPayoutBeneficiary beneficiary;

    /** Total service fee charged for this payout. See Data Types. */
    private Amount feeAmount;

    /** Itemized fee breakdown. Returned when multiple fee components apply. */
    private List<BatchPayoutFeeItem> feeItemList;

    /** Original fee rate before any promotional discounts. */
    private String originalFeeRate;

    /** Actual fee rate after promotional discounts are applied. */
    private String feeRate;

    /** Original service fee before promotional discounts. See Data Types. */
    private Amount originalFeeAmount;

    /** Promotional fee reduction amount. See Data Types. */
    private Amount discountFeeAmount;

    /** Amount returned to the payer. Returned when status is RETURNED. See Data Types. */
    private Amount returnedAmount;

    /** Timestamp when the funds were returned to the payer. Returned when status is RETURNED. ISO 8601 extended format. See Data Types. */
    private String returnedAt;

    /** Active promotions applied to this payout. */
    private List<BatchPayoutPromotion> promotionList;

    /** Full Value Transfer preference of the payout. - `NEED_FVT`: Request Full Value Transfer. */
    private String fvtPreference;

    /** On-Behalf-Of routing preference of the payout. - `NEED_OBO`: Route the payout on behalf of the specified payer. - `NO_OBO`: Do not use On-Behalf-Of routing. */
    private String oboPreference;

    /** Payment channel type used for this payout. - `LOCAL`: Local clearing. - `CROSS`: Cross-border wire transfer via SWIFT. */
    private String paymentType;

    /** Specific clearing network used. See PaymentNetwork. */
    private String paymentNetwork;

    /** Bank transfer reference text. */
    private String reference;

    /** Internal transfer memo. */
    private String transferMemo;

    /** Supplementary business fields for this payout. */
    private BatchPayoutAdditionalInfo additionalInfo;

    /** Idempotency key supplied for the payout item. */
    private String requestId;

    /** CNY quota consumption preference for this item. */
    private BatchPayoutQuotaPreference quotaConsumptionPreference;

    public String getItemId() { return itemId; }

    public void setItemId(String itemId) { this.itemId = itemId; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getFailureCode() { return failureCode; }

    public void setFailureCode(String failureCode) { this.failureCode = failureCode; }

    public String getFailureMessage() { return failureMessage; }

    public void setFailureMessage(String failureMessage) { this.failureMessage = failureMessage; }

    public String getBusinessSceneCode() { return businessSceneCode; }

    public void setBusinessSceneCode(String businessSceneCode) { this.businessSceneCode = businessSceneCode; }

    public String getPurposeCode() { return purposeCode; }

    public void setPurposeCode(String purposeCode) { this.purposeCode = purposeCode; }

    public Amount getSourceAmount() { return sourceAmount; }

    public void setSourceAmount(Amount sourceAmount) { this.sourceAmount = sourceAmount; }

    public Amount getPayoutAmount() { return payoutAmount; }

    public void setPayoutAmount(Amount payoutAmount) { this.payoutAmount = payoutAmount; }

    public BatchPayoutQuote getTransferQuote() { return transferQuote; }

    public void setTransferQuote(BatchPayoutQuote transferQuote) { this.transferQuote = transferQuote; }

    public BatchPayoutPayer getPayer() { return payer; }

    public void setPayer(BatchPayoutPayer payer) { this.payer = payer; }

    public BatchPayoutBeneficiary getBeneficiary() { return beneficiary; }

    public void setBeneficiary(BatchPayoutBeneficiary beneficiary) { this.beneficiary = beneficiary; }

    public Amount getFeeAmount() { return feeAmount; }

    public void setFeeAmount(Amount feeAmount) { this.feeAmount = feeAmount; }

    public List<BatchPayoutFeeItem> getFeeItemList() { return feeItemList; }

    public void setFeeItemList(List<BatchPayoutFeeItem> feeItemList) { this.feeItemList = feeItemList; }

    public String getOriginalFeeRate() { return originalFeeRate; }

    public void setOriginalFeeRate(String originalFeeRate) { this.originalFeeRate = originalFeeRate; }

    public String getFeeRate() { return feeRate; }

    public void setFeeRate(String feeRate) { this.feeRate = feeRate; }

    public Amount getOriginalFeeAmount() { return originalFeeAmount; }

    public void setOriginalFeeAmount(Amount originalFeeAmount) { this.originalFeeAmount = originalFeeAmount; }

    public Amount getDiscountFeeAmount() { return discountFeeAmount; }

    public void setDiscountFeeAmount(Amount discountFeeAmount) { this.discountFeeAmount = discountFeeAmount; }

    public Amount getReturnedAmount() { return returnedAmount; }

    public void setReturnedAmount(Amount returnedAmount) { this.returnedAmount = returnedAmount; }

    public String getReturnedAt() { return returnedAt; }

    public void setReturnedAt(String returnedAt) { this.returnedAt = returnedAt; }

    public List<BatchPayoutPromotion> getPromotionList() { return promotionList; }

    public void setPromotionList(List<BatchPayoutPromotion> promotionList) { this.promotionList = promotionList; }

    public String getFvtPreference() { return fvtPreference; }

    public void setFvtPreference(String fvtPreference) { this.fvtPreference = fvtPreference; }

    public String getOboPreference() { return oboPreference; }

    public void setOboPreference(String oboPreference) { this.oboPreference = oboPreference; }

    public String getPaymentType() { return paymentType; }

    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }

    public String getPaymentNetwork() { return paymentNetwork; }

    public void setPaymentNetwork(String paymentNetwork) { this.paymentNetwork = paymentNetwork; }

    public String getReference() { return reference; }

    public void setReference(String reference) { this.reference = reference; }

    public String getTransferMemo() { return transferMemo; }

    public void setTransferMemo(String transferMemo) { this.transferMemo = transferMemo; }

    public BatchPayoutAdditionalInfo getAdditionalInfo() { return additionalInfo; }

    public void setAdditionalInfo(BatchPayoutAdditionalInfo additionalInfo) { this.additionalInfo = additionalInfo; }

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

    public BatchPayoutQuotaPreference getQuotaConsumptionPreference() { return quotaConsumptionPreference; }

    public void setQuotaConsumptionPreference(BatchPayoutQuotaPreference quotaConsumptionPreference) { this.quotaConsumptionPreference = quotaConsumptionPreference; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "BatchPayoutItem[REDACTED]"; }
}
