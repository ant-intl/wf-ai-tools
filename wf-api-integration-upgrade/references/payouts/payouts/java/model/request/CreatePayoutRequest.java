package {basePackage}.wf.model.request;

import {basePackage}.wf.model.domain.AdditionalInfo;
import {basePackage}.wf.model.domain.Amount;
import {basePackage}.wf.model.domain.Beneficiary;
import {basePackage}.wf.model.domain.Payer;
import {basePackage}.wf.model.domain.Quote;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst create_a_payout 请求对象。
 *
 * <p>用于发起代发，将资金发送到收款方银行账户或电子钱包。
 */
public class CreatePayoutRequest {

    /** 幂等键，唯一请求标识（如 UUID） */
    private String requestId;

    /** 支付通道类型：LOCAL 或 CROSS */
    private String paymentType;

    /** 具体清算网络，如 ACH、SEPA、SWIFT */
    private String paymentNetwork;

    /** 收款方信息（提供 beneficiaryId、bankDetails 或 walletDetails 之一） */
    private Beneficiary beneficiary;

    /** 付款方信息（OBO 场景必填） */
    private Payer payer;

    /** 源币种金额（扣款金额），与 payoutAmount 二选一指定 value */
    private Amount sourceAmount;

    /** 目标币种金额（收款金额），与 sourceAmount 二选一指定 value */
    private Amount payoutAmount;

    /** 汇率报价（跨币种代发时传入 consult_a_payout 返回的 quoteId） */
    private Quote transferQuote;

    /** 业务场景码 */
    private String businessSceneCode;

    /** 付款用途码（必填）：GDS、TXS、ACM、GST、COM、TOA、SAL */
    private String purposeCode;

    /** 全额到账偏好：NEED_FVT */
    private String fvtPreference;

    /** 代发偏好：NEED_OBO、NO_OBO */
    private String oboPreference;

    /** 银行转账附言 */
    private String reference;

    /** 内部转账备注 */
    private String transferMemo;

    /** 附加业务信息（特定业务场景的补充字段，最多 10 个键值对） */
    private AdditionalInfo additionalInfo;

    /**
     * Getter method for property <tt>requestId</tt>.
     *
     * @return property value of requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Setter method for property <tt>requestId</tt>.
     *
     * @param requestId value to be assigned to property requestId
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
