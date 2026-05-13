package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 资金流动详情
 *
 * <p>对应 statementList 中的 fundMoveDetail 字段，包含付款方和收款方信息。
 *
 */
public class FundMoveDetail {

    /** 付款人姓名（脱敏） */
    private String payerName;

    /** 付款方账户号（脱敏） */
    private String payerAccountNo;

    /** 付款方账户类型：WORLDFIRST、BANK_CARD、VIRTUAL_ACCOUNT、ALIPAY_CN、MINI_ACCOUNT、INST_ACCOUNT、INNER_ACCOUNT */
    private String payerAccountType;

    /** 付款人开户行名称 */
    private String payerBankName;

    /** 万里汇识别付款方的唯一ID（VENDOR_COLLECTION_PAYMENT 场景） */
    private String payerUserId;

    /** 收款方名称（脱敏） */
    private String beneficiaryName;

    /** 收款方账户号（脱敏） */
    private String beneficiaryAccountNo;

    /** 收款方账户类型：WORLDFIRST、BANK_CARD、VIRTUAL_ACCOUNT、ALIPAY_CN、MINI_ACCOUNT、INST_ACCOUNT、INNER_ACCOUNT、OVO */
    private String beneficiaryAccountType;

    /** 收款方账户所在国家/地区（ISO-3166） */
    private String beneficiaryBankCountry;

    /** 收款方账户所属银行名称（脱敏） */
    private String beneficiaryBankName;

    /** 收款人店铺名（COLLECTION 场景） */
    private String beneficiaryStoreName;

    /** 集成商在 Marketplace 上的注册名（COLLECTION 场景） */
    private String beneficiaryMarketplaceName;

    /** 收款人的 RA 或 VA 账号（COLLECTION 场景） */
    private String receiveAccount;

    /** 转账附言 */
    private String remarks;

    /** 由用户提供的交易描述信息 */
    private String description;

    /** 付款补充说明，由付款方填写（VENDOR_COLLECTION_PAYMENT 场景） */
    private String paymentExplanation;

    /** 付款主体（付款人）在服务商注册的姓名（VENDOR_COLLECTION_PAYMENT 场景） */
    private String paymentSubject;

    /** 付款凭证号，由付款人填写（VENDOR_COLLECTION_PAYMENT 场景） */
    private String paymentVoucherNo;

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayerAccountNo() {
        return payerAccountNo;
    }

    public void setPayerAccountNo(String payerAccountNo) {
        this.payerAccountNo = payerAccountNo;
    }

    public String getPayerAccountType() {
        return payerAccountType;
    }

    public void setPayerAccountType(String payerAccountType) {
        this.payerAccountType = payerAccountType;
    }

    public String getPayerBankName() {
        return payerBankName;
    }

    public void setPayerBankName(String payerBankName) {
        this.payerBankName = payerBankName;
    }

    public String getPayerUserId() {
        return payerUserId;
    }

    public void setPayerUserId(String payerUserId) {
        this.payerUserId = payerUserId;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getBeneficiaryAccountNo() {
        return beneficiaryAccountNo;
    }

    public void setBeneficiaryAccountNo(String beneficiaryAccountNo) {
        this.beneficiaryAccountNo = beneficiaryAccountNo;
    }

    public String getBeneficiaryAccountType() {
        return beneficiaryAccountType;
    }

    public void setBeneficiaryAccountType(String beneficiaryAccountType) {
        this.beneficiaryAccountType = beneficiaryAccountType;
    }

    public String getBeneficiaryBankCountry() {
        return beneficiaryBankCountry;
    }

    public void setBeneficiaryBankCountry(String beneficiaryBankCountry) {
        this.beneficiaryBankCountry = beneficiaryBankCountry;
    }

    public String getBeneficiaryBankName() {
        return beneficiaryBankName;
    }

    public void setBeneficiaryBankName(String beneficiaryBankName) {
        this.beneficiaryBankName = beneficiaryBankName;
    }

    public String getBeneficiaryStoreName() {
        return beneficiaryStoreName;
    }

    public void setBeneficiaryStoreName(String beneficiaryStoreName) {
        this.beneficiaryStoreName = beneficiaryStoreName;
    }

    public String getBeneficiaryMarketplaceName() {
        return beneficiaryMarketplaceName;
    }

    public void setBeneficiaryMarketplaceName(String beneficiaryMarketplaceName) {
        this.beneficiaryMarketplaceName = beneficiaryMarketplaceName;
    }

    public String getReceiveAccount() {
        return receiveAccount;
    }

    public void setReceiveAccount(String receiveAccount) {
        this.receiveAccount = receiveAccount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentExplanation() {
        return paymentExplanation;
    }

    public void setPaymentExplanation(String paymentExplanation) {
        this.paymentExplanation = paymentExplanation;
    }

    public String getPaymentSubject() {
        return paymentSubject;
    }

    public void setPaymentSubject(String paymentSubject) {
        this.paymentSubject = paymentSubject;
    }

    public String getPaymentVoucherNo() {
        return paymentVoucherNo;
    }

    public void setPaymentVoucherNo(String paymentVoucherNo) {
        this.paymentVoucherNo = paymentVoucherNo;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
