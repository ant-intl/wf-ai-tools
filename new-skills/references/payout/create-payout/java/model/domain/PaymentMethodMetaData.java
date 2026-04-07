/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 收款方银行账户元数据（嵌套在 {@link TransferToMethod} 中）。
 *
 * <p>对应 WF createPayout 接口 {@code transferToDetail.transferToMethod.paymentMethodMetaData}。
 * 字段名遵循 inquiryBeneficiaryTemplate 接口返回的卡信息要素列表。
 *
 * @author Qoder
 * @version PaymentMethodMetaData.java, v 0.1 2026-03-26
 */
public class PaymentMethodMetaData {

    /** 账户名称（英文） */
    private String bankAccountName;

    /** 银行账号/卡号 */
    private String bankAccountNo;

    /** 银行名称（英文） */
    private String bankName;

    /** 银行 BIC/SWIFT 代码 */
    private String bankBIC;

    /** IBAN（国际银行账号，部分欧洲国家需要） */
    private String bankAccountIBAN;

    /** 路由号码（美国等部分国家需要） */
    private String routingNumber;

    /** 受益人地址 */
    private String beneficiaryAddress;

    /** 受益人国家代码（ISO-3166，2 位字母） */
    private String beneficiaryCountryCode;

    /** 受益人电话 */
    private String beneficiaryPhone;

    /** 银行分支代码 */
    private String bankBranchCode;

    /** 银行本地名称（当地文字） */
    private String bankLocalName;

    /** 账户名称（当地文字） */
    private String bankAccountLocalName;

    /**
     * 收款方类型。
     * <ul>
     *   <li>{@code THIRD_PARTY_PERSONAL_BANK_ACCOUNT} — 第三方个人银行账户</li>
     *   <li>{@code THIRD_PARTY_COMPANY_BANK_ACCOUNT} — 第三方企业银行账户</li>
     *   <li>{@code SAME_NAME_BANK_ACCOUNT} — 同名银行账户</li>
     * </ul>
     */
    private String beneficiaryType;

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankBIC() {
        return bankBIC;
    }

    public void setBankBIC(String bankBIC) {
        this.bankBIC = bankBIC;
    }

    public String getBankAccountIBAN() {
        return bankAccountIBAN;
    }

    public void setBankAccountIBAN(String bankAccountIBAN) {
        this.bankAccountIBAN = bankAccountIBAN;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getBeneficiaryAddress() {
        return beneficiaryAddress;
    }

    public void setBeneficiaryAddress(String beneficiaryAddress) {
        this.beneficiaryAddress = beneficiaryAddress;
    }

    public String getBeneficiaryCountryCode() {
        return beneficiaryCountryCode;
    }

    public void setBeneficiaryCountryCode(String beneficiaryCountryCode) {
        this.beneficiaryCountryCode = beneficiaryCountryCode;
    }

    public String getBeneficiaryPhone() {
        return beneficiaryPhone;
    }

    public void setBeneficiaryPhone(String beneficiaryPhone) {
        this.beneficiaryPhone = beneficiaryPhone;
    }

    public String getBankBranchCode() {
        return bankBranchCode;
    }

    public void setBankBranchCode(String bankBranchCode) {
        this.bankBranchCode = bankBranchCode;
    }

    public String getBankLocalName() {
        return bankLocalName;
    }

    public void setBankLocalName(String bankLocalName) {
        this.bankLocalName = bankLocalName;
    }

    public String getBankAccountLocalName() {
        return bankAccountLocalName;
    }

    public void setBankAccountLocalName(String bankAccountLocalName) {
        this.bankAccountLocalName = bankAccountLocalName;
    }

    public String getBeneficiaryType() {
        return beneficiaryType;
    }

    public void setBeneficiaryType(String beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
    }

    // -------------------------------------------------------------------------
    // Convenience methods - 向后兼容
    // -------------------------------------------------------------------------

    /**
     * 获取 SWIFT 代码（{@link #getBankBIC()} 的别名）。
     *
     * @return bankBIC
     */
    public String getSwiftCode() {
        return bankBIC;
    }

    /**
     * 设置 SWIFT 代码（{@link #setBankBIC(String)} 的别名）。
     *
     * @param swiftCode SWIFT/BIC 代码
     */
    public void setSwiftCode(String swiftCode) {
        this.bankBIC = swiftCode;
    }

    /**
     * 获取支行名称（{@link #getBankBranchCode()} 的别名）。
     *
     * @return bankBranchCode
     */
    public String getBankBranchName() {
        return bankBranchCode;
    }

    /**
     * 设置支行名称（{@link #setBankBranchCode(String)} 的别名）。
     *
     * @param bankBranchName 支行名称
     */
    public void setBankBranchName(String bankBranchName) {
        this.bankBranchCode = bankBranchName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    private static String maskAccountNo(String accountNo) {
        if (accountNo == null || accountNo.length() <= 8) {
            return accountNo;
        }
        return accountNo.substring(0, 4) + "****" + accountNo.substring(accountNo.length() - 4);
    }
}
