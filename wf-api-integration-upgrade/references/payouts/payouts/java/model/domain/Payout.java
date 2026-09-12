package {basePackage}.wf.model.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 代发（Payout）对象。
 *
 * <p>包含代发单的完整信息，用于 consult_a_payout、create_a_payout、query_a_payout
 * 接口的响应。继承此类并添加 {@code result} 字段即可作为响应对象。
 */
public class Payout {

    /** 代发单唯一标识（create/query 响应返回，consult 不返回） */
    private String id;

    /** 代发状态：PROCESSING、SUCCESS、FAIL、RETURN（create/query 响应返回） */
    private String status;

    /** 失败原因码，status 为 FAIL 时返回 */
    private String failureCode;

    /** 失败原因描述，status 为 FAIL 时返回 */
    private String failureMessage;

    /** 支付通道类型：LOCAL（本地清算）或 CROSS（跨境 SWIFT） */
    private String paymentType;

    /** 具体清算网络，如 ACH、SEPA、SWIFT 等 */
    private String paymentNetwork;

    /** 付款方信息 */
    private Payer payer;

    /** 收款方信息 */
    private Beneficiary beneficiary;

    /** 源币种金额（从付款方账户扣款） */
    private Amount sourceAmount;

    /** 目标币种金额（收款方收到） */
    private Amount payoutAmount;

    /** 汇率报价（跨币种代发时返回） */
    private Quote transferQuote;

    /** 总手续费 */
    private Amount feeAmount;

    /** 手续费明细列表 */
    private List<FeeItem> feeItemList;

    /** 原始费率（促销前） */
    private String originalFeeRate;

    /** 实际费率（促销后） */
    private String feeRate;

    /** 原始手续费（促销前） */
    private Amount originalFeeAmount;

    /** 促销减免金额 */
    private Amount discountFeeAmount;

    /** 剩余结汇额度 */
    private Amount availableQuota;

    /** 适用的促销列表 */
    private List<Promotion> promotionList;

    /** 业务场景码 */
    private String businessSceneCode;

    /** 付款用途码 */
    private String purposeCode;

    /** 全额到账偏好：NEED_FVT */
    private String fvtPreference;

    /** 代发偏好：NEED_OBO、NO_OBO */
    private String oboPreference;

    /** 银行转账附言 */
    private String reference;

    /** 内部转账备注 */
    private String transferMemo;

    /** 附加业务信息（特定业务场景的补充字段） */
    private AdditionalInfo additionalInfo;

    /** 退回金额，status 为 RETURN 时返回（query 响应） */
    private Amount returnedAmount;

    /** 退回时间（ISO 8601 格式，如 2024-01-01T00:00:00+08:00），status 为 RETURN 时返回（query 响应） */
    private String returnedAt;

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id value to be assigned to property id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     *
     * @param status value to be assigned to property status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Getter method for property <tt>failureCode</tt>.
     *
     * @return property value of failureCode
     */
    public String getFailureCode() {
        return failureCode;
    }

    /**
     * Setter method for property <tt>failureCode</tt>.
     *
     * @param failureCode value to be assigned to property failureCode
     */
    public void setFailureCode(String failureCode) {
        this.failureCode = failureCode;
    }

    /**
     * Getter method for property <tt>failureMessage</tt>.
     *
     * @return property value of failureMessage
     */
    public String getFailureMessage() {
        return failureMessage;
    }

    /**
     * Setter method for property <tt>failureMessage</tt>.
     *
     * @param failureMessage value to be assigned to property failureMessage
     */
    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    /**
     * Getter method for property <tt>paymentType</tt>.
     *
     * @return property value of paymentType
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * Setter method for property <tt>paymentType</tt>.
     *
     * @param paymentType value to be assigned to property paymentType
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * Getter method for property <tt>paymentNetwork</tt>.
     *
     * @return property value of paymentNetwork
     */
    public String getPaymentNetwork() {
        return paymentNetwork;
    }

    /**
     * Setter method for property <tt>paymentNetwork</tt>.
     *
     * @param paymentNetwork value to be assigned to property paymentNetwork
     */
    public void setPaymentNetwork(String paymentNetwork) {
        this.paymentNetwork = paymentNetwork;
    }

    /**
     * Getter method for property <tt>payer</tt>.
     *
     * @return property value of payer
     */
    public Payer getPayer() {
        return payer;
    }

    /**
     * Setter method for property <tt>payer</tt>.
     *
     * @param payer value to be assigned to property payer
     */
    public void setPayer(Payer payer) {
        this.payer = payer;
    }

    /**
     * Getter method for property <tt>beneficiary</tt>.
     *
     * @return property value of beneficiary
     */
    public Beneficiary getBeneficiary() {
        return beneficiary;
    }

    /**
     * Setter method for property <tt>beneficiary</tt>.
     *
     * @param beneficiary value to be assigned to property beneficiary
     */
    public void setBeneficiary(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }

    /**
     * Getter method for property <tt>sourceAmount</tt>.
     *
     * @return property value of sourceAmount
     */
    public Amount getSourceAmount() {
        return sourceAmount;
    }

    /**
     * Setter method for property <tt>sourceAmount</tt>.
     *
     * @param sourceAmount value to be assigned to property sourceAmount
     */
    public void setSourceAmount(Amount sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    /**
     * Getter method for property <tt>payoutAmount</tt>.
     *
     * @return property value of payoutAmount
     */
    public Amount getPayoutAmount() {
        return payoutAmount;
    }

    /**
     * Setter method for property <tt>payoutAmount</tt>.
     *
     * @param payoutAmount value to be assigned to property payoutAmount
     */
    public void setPayoutAmount(Amount payoutAmount) {
        this.payoutAmount = payoutAmount;
    }

    /**
     * Getter method for property <tt>transferQuote</tt>.
     *
     * @return property value of transferQuote
     */
    public Quote getTransferQuote() {
        return transferQuote;
    }

    /**
     * Setter method for property <tt>transferQuote</tt>.
     *
     * @param transferQuote value to be assigned to property transferQuote
     */
    public void setTransferQuote(Quote transferQuote) {
        this.transferQuote = transferQuote;
    }

    /**
     * Getter method for property <tt>feeAmount</tt>.
     *
     * @return property value of feeAmount
     */
    public Amount getFeeAmount() {
        return feeAmount;
    }

    /**
     * Setter method for property <tt>feeAmount</tt>.
     *
     * @param feeAmount value to be assigned to property feeAmount
     */
    public void setFeeAmount(Amount feeAmount) {
        this.feeAmount = feeAmount;
    }

    /**
     * Getter method for property <tt>feeItemList</tt>.
     *
     * @return property value of feeItemList
     */
    public List<FeeItem> getFeeItemList() {
        return feeItemList;
    }

    /**
     * Setter method for property <tt>feeItemList</tt>.
     *
     * @param feeItemList value to be assigned to property feeItemList
     */
    public void setFeeItemList(List<FeeItem> feeItemList) {
        this.feeItemList = feeItemList;
    }

    /**
     * Getter method for property <tt>originalFeeRate</tt>.
     *
     * @return property value of originalFeeRate
     */
    public String getOriginalFeeRate() {
        return originalFeeRate;
    }

    /**
     * Setter method for property <tt>originalFeeRate</tt>.
     *
     * @param originalFeeRate value to be assigned to property originalFeeRate
     */
    public void setOriginalFeeRate(String originalFeeRate) {
        this.originalFeeRate = originalFeeRate;
    }

    /**
     * Getter method for property <tt>feeRate</tt>.
     *
     * @return property value of feeRate
     */
    public String getFeeRate() {
        return feeRate;
    }

    /**
     * Setter method for property <tt>feeRate</tt>.
     *
     * @param feeRate value to be assigned to property feeRate
     */
    public void setFeeRate(String feeRate) {
        this.feeRate = feeRate;
    }

    /**
     * Getter method for property <tt>originalFeeAmount</tt>.
     *
     * @return property value of originalFeeAmount
     */
    public Amount getOriginalFeeAmount() {
        return originalFeeAmount;
    }

    /**
     * Setter method for property <tt>originalFeeAmount</tt>.
     *
     * @param originalFeeAmount value to be assigned to property originalFeeAmount
     */
    public void setOriginalFeeAmount(Amount originalFeeAmount) {
        this.originalFeeAmount = originalFeeAmount;
    }

    /**
     * Getter method for property <tt>discountFeeAmount</tt>.
     *
     * @return property value of discountFeeAmount
     */
    public Amount getDiscountFeeAmount() {
        return discountFeeAmount;
    }

    /**
     * Setter method for property <tt>discountFeeAmount</tt>.
     *
     * @param discountFeeAmount value to be assigned to property discountFeeAmount
     */
    public void setDiscountFeeAmount(Amount discountFeeAmount) {
        this.discountFeeAmount = discountFeeAmount;
    }

    /**
     * Getter method for property <tt>availableQuota</tt>.
     *
     * @return property value of availableQuota
     */
    public Amount getAvailableQuota() {
        return availableQuota;
    }

    /**
     * Setter method for property <tt>availableQuota</tt>.
     *
     * @param availableQuota value to be assigned to property availableQuota
     */
    public void setAvailableQuota(Amount availableQuota) {
        this.availableQuota = availableQuota;
    }

    /**
     * Getter method for property <tt>promotionList</tt>.
     *
     * @return property value of promotionList
     */
    public List<Promotion> getPromotionList() {
        return promotionList;
    }

    /**
     * Setter method for property <tt>promotionList</tt>.
     *
     * @param promotionList value to be assigned to property promotionList
     */
    public void setPromotionList(List<Promotion> promotionList) {
        this.promotionList = promotionList;
    }

    /**
     * Getter method for property <tt>businessSceneCode</tt>.
     *
     * @return property value of businessSceneCode
     */
    public String getBusinessSceneCode() {
        return businessSceneCode;
    }

    /**
     * Setter method for property <tt>businessSceneCode</tt>.
     *
     * @param businessSceneCode value to be assigned to property businessSceneCode
     */
    public void setBusinessSceneCode(String businessSceneCode) {
        this.businessSceneCode = businessSceneCode;
    }

    /**
     * Getter method for property <tt>purposeCode</tt>.
     *
     * @return property value of purposeCode
     */
    public String getPurposeCode() {
        return purposeCode;
    }

    /**
     * Setter method for property <tt>purposeCode</tt>.
     *
     * @param purposeCode value to be assigned to property purposeCode
     */
    public void setPurposeCode(String purposeCode) {
        this.purposeCode = purposeCode;
    }

    /**
     * Getter method for property <tt>fvtPreference</tt>.
     *
     * @return property value of fvtPreference
     */
    public String getFvtPreference() {
        return fvtPreference;
    }

    /**
     * Setter method for property <tt>fvtPreference</tt>.
     *
     * @param fvtPreference value to be assigned to property fvtPreference
     */
    public void setFvtPreference(String fvtPreference) {
        this.fvtPreference = fvtPreference;
    }

    /**
     * Getter method for property <tt>oboPreference</tt>.
     *
     * @return property value of oboPreference
     */
    public String getOboPreference() {
        return oboPreference;
    }

    /**
     * Setter method for property <tt>oboPreference</tt>.
     *
     * @param oboPreference value to be assigned to property oboPreference
     */
    public void setOboPreference(String oboPreference) {
        this.oboPreference = oboPreference;
    }

    /**
     * Getter method for property <tt>reference</tt>.
     *
     * @return property value of reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * Setter method for property <tt>reference</tt>.
     *
     * @param reference value to be assigned to property reference
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * Getter method for property <tt>transferMemo</tt>.
     *
     * @return property value of transferMemo
     */
    public String getTransferMemo() {
        return transferMemo;
    }

    /**
     * Setter method for property <tt>transferMemo</tt>.
     *
     * @param transferMemo value to be assigned to property transferMemo
     */
    public void setTransferMemo(String transferMemo) {
        this.transferMemo = transferMemo;
    }

    /**
     * Getter method for property <tt>additionalInfo</tt>.
     *
     * @return property value of additionalInfo
     */
    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * Setter method for property <tt>additionalInfo</tt>.
     *
     * @param additionalInfo value to be assigned to property additionalInfo
     */
    public void setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    /**
     * Getter method for property <tt>returnedAmount</tt>.
     *
     * @return property value of returnedAmount
     */
    public Amount getReturnedAmount() {
        return returnedAmount;
    }

    /**
     * Setter method for property <tt>returnedAmount</tt>.
     *
     * @param returnedAmount value to be assigned to property returnedAmount
     */
    public void setReturnedAmount(Amount returnedAmount) {
        this.returnedAmount = returnedAmount;
    }

    /**
     * Getter method for property <tt>returnedAt</tt>.
     *
     * @return property value of returnedAt
     */
    public String getReturnedAt() {
        return returnedAt;
    }

    /**
     * Setter method for property <tt>returnedAt</tt>.
     *
     * @param returnedAt value to be assigned to property returnedAt
     */
    public void setReturnedAt(String returnedAt) {
        this.returnedAt = returnedAt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
