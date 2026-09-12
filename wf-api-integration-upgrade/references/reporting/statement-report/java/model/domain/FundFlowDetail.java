package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 资金流详情对象。
 *
 * <p>描述对账单中资金流转的详细信息，包括付款方、收款方及付款备注等。
 */
public class FundFlowDetail {

    /** 付款方信息 */
    private PayerInfo payerInfo;

    /** 收款方信息 */
    private BeneficiaryInfo beneficiaryInfo;

    /** 付款方提供的补充备注 */
    private String paymentExplanation;

    /** 付款主体 */
    private String paymentSubject;

    /** 付款凭证号 */
    private String paymentVoucherNo;

    public FundFlowDetail() {
    }

    /**
     * Getter method for property <tt>payerInfo</tt>.
     *
     * @return property value of payerInfo
     */
    public PayerInfo getPayerInfo() {
        return payerInfo;
    }

    /**
     * Setter method for property <tt>payerInfo</tt>.
     *
     * @param payerInfo value to be assigned to property payerInfo
     */
    public void setPayerInfo(PayerInfo payerInfo) {
        this.payerInfo = payerInfo;
    }

    /**
     * Getter method for property <tt>beneficiaryInfo</tt>.
     *
     * @return property value of beneficiaryInfo
     */
    public BeneficiaryInfo getBeneficiaryInfo() {
        return beneficiaryInfo;
    }

    /**
     * Setter method for property <tt>beneficiaryInfo</tt>.
     *
     * @param beneficiaryInfo value to be assigned to property beneficiaryInfo
     */
    public void setBeneficiaryInfo(BeneficiaryInfo beneficiaryInfo) {
        this.beneficiaryInfo = beneficiaryInfo;
    }

    /**
     * Getter method for property <tt>paymentExplanation</tt>.
     *
     * @return property value of paymentExplanation
     */
    public String getPaymentExplanation() {
        return paymentExplanation;
    }

    /**
     * Setter method for property <tt>paymentExplanation</tt>.
     *
     * @param paymentExplanation value to be assigned to property paymentExplanation
     */
    public void setPaymentExplanation(String paymentExplanation) {
        this.paymentExplanation = paymentExplanation;
    }

    /**
     * Getter method for property <tt>paymentSubject</tt>.
     *
     * @return property value of paymentSubject
     */
    public String getPaymentSubject() {
        return paymentSubject;
    }

    /**
     * Setter method for property <tt>paymentSubject</tt>.
     *
     * @param paymentSubject value to be assigned to property paymentSubject
     */
    public void setPaymentSubject(String paymentSubject) {
        this.paymentSubject = paymentSubject;
    }

    /**
     * Getter method for property <tt>paymentVoucherNo</tt>.
     *
     * @return property value of paymentVoucherNo
     */
    public String getPaymentVoucherNo() {
        return paymentVoucherNo;
    }

    /**
     * Setter method for property <tt>paymentVoucherNo</tt>.
     *
     * @param paymentVoucherNo value to be assigned to property paymentVoucherNo
     */
    public void setPaymentVoucherNo(String paymentVoucherNo) {
        this.paymentVoucherNo = paymentVoucherNo;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
