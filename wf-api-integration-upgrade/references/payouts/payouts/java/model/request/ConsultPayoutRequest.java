package {basePackage}.wf.model.request;

import {basePackage}.wf.model.domain.Amount;
import {basePackage}.wf.model.domain.Beneficiary;
import {basePackage}.wf.model.domain.Payer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst consult_a_payout 请求对象。
 *
 * <p>用于预校验代发并获取手续费和汇率估算，不实际移动资金。
 */
public class ConsultPayoutRequest {

    /** 支付通道类型，需与 paymentNetwork 配合使用。可选值：`LOCAL`（本地清算，如 ACH、SEPA、Faster Payments）、`CROSS`（跨境 SWIFT 电汇） */
    private String paymentType;

    /** 具体清算网络，需与 paymentType 配合使用。参见 PaymentNetwork */
    private String paymentNetwork;

    /** 收款方（受益人）信息（必填） */
    private Beneficiary beneficiary;

    /** 付款方（汇款人）信息。默认使用已入驻账户信息。当指定 On-Behalf-Of 代发偏好时必填 */
    private Payer payer;

    /** 从付款方账户扣款的源币种金额。sourceAmount 和 payoutAmount 均需指定 currency，但仅可在一个金额对象中指定 value */
    private Amount sourceAmount;

    /** 收款方收到的目标币种金额。sourceAmount 和 payoutAmount 均需指定 currency，但仅可在一个金额对象中指定 value */
    private Amount payoutAmount;

    /** 标识代发类型的业务场景码。参见 BusinessSceneCode */
    private String businessSceneCode;

    /** 付款用途码（必填）。参见 PurposeCode */
    private String purposeCode;

    /** 全额到账偏好。指定后请求保证收款方收到全额（中间行手续费由付款方承担）。可选值：`NEED_FVT`（请求全额到账） */
    private String fvtPreference;

    /** On-Behalf-Of 代发路由偏好。设为 `NEED_OBO` 时，WorldFirst 将指定的付款方信息转发给收款银行。可选值：`NEED_OBO`（以指定付款方名义代发，仅适用于支持 OBO 的渠道）、`NO_OBO`（无 On-Behalf-Of 路由偏好） */
    private String oboPreference;

    /** 银行转账附言。可能出现在收款方银行对账单上，具体取决于当地清算系统和收款银行 */
    private String reference;

    /** 内部转账备注，仅供商户自己记录使用，收款方不可见 */
    private String transferMemo;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
