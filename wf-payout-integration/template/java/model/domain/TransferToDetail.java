/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

/**
 * 收款方转账详情（代发到三方卡场景）
 *
 * @author Qoder
 * @version TransferToDetail.java, v 0.1 2026-03-25
 */
public class TransferToDetail {

    /** 收款金额 */
    private Amount transferToAmount;

    /** 转账方式，固定为 BANK_ACCOUNT_DETAIL */
    private String transferToMethod;

    /** 银行账户详情 */
    private BankAccountDetail bankAccountDetail;

    /** 收款人信息 */
    private BeneficiaryInfo beneficiaryInfo;

    /** 转账用途/备注 */
    private String purpose;

    /**
     * Getter method for property <tt>transferToAmount</tt>.
     *
     * @return property value of transferToAmount
     */
    public Amount getTransferToAmount() {
        return transferToAmount;
    }

    /**
     * Setter method for property <tt>transferToAmount</tt>.
     *
     * @param transferToAmount value to be assigned to property transferToAmount
     */
    public void setTransferToAmount(Amount transferToAmount) {
        this.transferToAmount = transferToAmount;
    }

    /**
     * Getter method for property <tt>transferToMethod</tt>.
     *
     * @return property value of transferToMethod
     */
    public String getTransferToMethod() {
        return transferToMethod;
    }

    /**
     * Setter method for property <tt>transferToMethod</tt>.
     *
     * @param transferToMethod value to be assigned to property transferToMethod
     */
    public void setTransferToMethod(String transferToMethod) {
        this.transferToMethod = transferToMethod;
    }

    /**
     * Getter method for property <tt>bankAccountDetail</tt>.
     *
     * @return property value of bankAccountDetail
     */
    public BankAccountDetail getBankAccountDetail() {
        return bankAccountDetail;
    }

    /**
     * Setter method for property <tt>bankAccountDetail</tt>.
     *
     * @param bankAccountDetail value to be assigned to property bankAccountDetail
     */
    public void setBankAccountDetail(BankAccountDetail bankAccountDetail) {
        this.bankAccountDetail = bankAccountDetail;
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
     * Getter method for property <tt>purpose</tt>.
     *
     * @return property value of purpose
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Setter method for property <tt>purpose</tt>.
     *
     * @param purpose value to be assigned to property purpose
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    }
}
