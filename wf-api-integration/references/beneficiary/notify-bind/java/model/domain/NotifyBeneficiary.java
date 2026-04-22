/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * notifyBindBeneficiary 回调中的收款人信息。
 *
 * <p>当 result.resultStatus=S 时，万里汇会在回调中携带本对象，
 * 包含收款人令牌、账户类型、状态等信息。
 *
 * @author Qoder
 * @version NotifyBeneficiary.java, v 0.1 2026-04-21
 */
public class NotifyBeneficiary {

    /** 收款人令牌（Base64 加密的银行账户信息） */
    private String beneficiaryToken;

    /** 集成商定义的幂等请求 ID */
    private String bindBeneficiaryRequestId;

    /** 收款人银行账户信息（按 inquiryBeneficiaryTemplate 返回的字段） */
    private Object beneficiaryBankAccount;

    /** 收款行所在国家/地区，ISO-3166 2 位字母，最大 2 字符 */
    private String countryCode;

    /** 币种，ISO-4217 3 位字母（USD, EUR, GBP, NZD, CAD, AUD, JPY, SGD, HKD, CNH, CNY） */
    private String currency;

    /**
     * 收款人账户类型。
     * <ul>
     *   <li>THIRD_PARTY_PERSONAL_BANK_ACCOUNT: 第三方收款人的私人银行账户</li>
     *   <li>THIRD_PARTY_COMPANY_BANK_ACCOUNT: 第三方收款人的企业银行账户</li>
     *   <li>PERSONAL_BANK_ACCOUNT: 同名收款人的私人银行账户</li>
     *   <li>COMPANY_BANK_ACCOUNT: 同名收款人的企业银行账户</li>
     *   <li>RELATED_MERCHANT_COMPANY_BANK_ACCOUNT: 关联公司企业银行账户</li>
     *   <li>RELATED_MERCHANT_ALIPAY_COMPANY_ACCOUNT: 关联公司企业支付宝账户</li>
     * </ul>
     */
    private String beneficiaryType;

    /** 收款人昵称 */
    private String beneficiaryNick;

    /**
     * 收款人状态。
     * <ul>
     *   <li>SUCCESS: 可用</li>
     *   <li>FAIL: 不可用</li>
     * </ul>
     */
    private String status;

    /** 集成商定义的收款人唯一 ID，最大 64 字符 */
    private String referenceBeneficiaryId;

    /**
     * Getter method for property <tt>beneficiaryToken</tt>.
     *
     * @return property value of beneficiaryToken
     */
    public String getBeneficiaryToken() {
        return beneficiaryToken;
    }

    /**
     * Setter method for property <tt>beneficiaryToken</tt>.
     *
     * @param beneficiaryToken value to be assigned to property beneficiaryToken
     */
    public void setBeneficiaryToken(String beneficiaryToken) {
        this.beneficiaryToken = beneficiaryToken;
    }

    /**
     * Getter method for property <tt>bindBeneficiaryRequestId</tt>.
     *
     * @return property value of bindBeneficiaryRequestId
     */
    public String getBindBeneficiaryRequestId() {
        return bindBeneficiaryRequestId;
    }

    /**
     * Setter method for property <tt>bindBeneficiaryRequestId</tt>.
     *
     * @param bindBeneficiaryRequestId value to be assigned to property bindBeneficiaryRequestId
     */
    public void setBindBeneficiaryRequestId(String bindBeneficiaryRequestId) {
        this.bindBeneficiaryRequestId = bindBeneficiaryRequestId;
    }

    /**
     * Getter method for property <tt>beneficiaryBankAccount</tt>.
     *
     * @return property value of beneficiaryBankAccount
     */
    public Object getBeneficiaryBankAccount() {
        return beneficiaryBankAccount;
    }

    /**
     * Setter method for property <tt>beneficiaryBankAccount</tt>.
     *
     * @param beneficiaryBankAccount value to be assigned to property beneficiaryBankAccount
     */
    public void setBeneficiaryBankAccount(Object beneficiaryBankAccount) {
        this.beneficiaryBankAccount = beneficiaryBankAccount;
    }

    /**
     * Getter method for property <tt>countryCode</tt>.
     *
     * @return property value of countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Setter method for property <tt>countryCode</tt>.
     *
     * @param countryCode value to be assigned to property countryCode
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * Getter method for property <tt>currency</tt>.
     *
     * @return property value of currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Setter method for property <tt>currency</tt>.
     *
     * @param currency value to be assigned to property currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Getter method for property <tt>beneficiaryType</tt>.
     *
     * @return property value of beneficiaryType
     */
    public String getBeneficiaryType() {
        return beneficiaryType;
    }

    /**
     * Setter method for property <tt>beneficiaryType</tt>.
     *
     * @param beneficiaryType value to be assigned to property beneficiaryType
     */
    public void setBeneficiaryType(String beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
    }

    /**
     * Getter method for property <tt>beneficiaryNick</tt>.
     *
     * @return property value of beneficiaryNick
     */
    public String getBeneficiaryNick() {
        return beneficiaryNick;
    }

    /**
     * Setter method for property <tt>beneficiaryNick</tt>.
     *
     * @param beneficiaryNick value to be assigned to property beneficiaryNick
     */
    public void setBeneficiaryNick(String beneficiaryNick) {
        this.beneficiaryNick = beneficiaryNick;
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
     * Getter method for property <tt>referenceBeneficiaryId</tt>.
     *
     * @return property value of referenceBeneficiaryId
     */
    public String getReferenceBeneficiaryId() {
        return referenceBeneficiaryId;
    }

    /**
     * Setter method for property <tt>referenceBeneficiaryId</tt>.
     *
     * @param referenceBeneficiaryId value to be assigned to property referenceBeneficiaryId
     */
    public void setReferenceBeneficiaryId(String referenceBeneficiaryId) {
        this.referenceBeneficiaryId = referenceBeneficiaryId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
