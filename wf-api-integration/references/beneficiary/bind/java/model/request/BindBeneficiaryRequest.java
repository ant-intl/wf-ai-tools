/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.request;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst bindBeneficiary 请求对象。
 *
 * <p>用于绑定收款人到当前 WF 账户。
 *
 * @author Qoder
 * @version BindBeneficiaryRequest.java, v 0.1 2026-03-26
 */
public class BindBeneficiaryRequest {

    /** 幂等请求ID，最大 64 字符 */
    private String bindBeneficiaryRequestId;

    /** OAuth access token，最大 64 字符 */
    private String accessToken;

    /** 账户类型 */
    private String beneficiaryType;

    /** 银行账户信息（按卡模版字段填写） */
    private Object beneficiaryBankAccount;

    /** 支付宝账户信息 */
    private Object beneficiaryAlipayAccount;

    /** 三方身份信息（CN/CNY 三方场景必填） */
    private Object thirdPartyIdentity;

    /** 银行国家代码，ISO-3166 2 位字母 */
    private String countryCode;

    /** 币种代码，ISO-4217 3 位字母 */
    private String currency;

    /** 收款人昵称，最大 70 字符 */
    private String beneficiaryNick;

    /** 模版类型：GENERAL_TEMPLATE(默认)、LOCAL_TEMPLATE、CROSS_BORDER_TEMPLATE */
    private String templateCategory;

    /** 集成商自定义唯一ID，最大 64 字符 */
    private String referenceBeneficiaryId;

    public String getBindBeneficiaryRequestId() {
        return bindBeneficiaryRequestId;
    }

    public void setBindBeneficiaryRequestId(String bindBeneficiaryRequestId) {
        this.bindBeneficiaryRequestId = bindBeneficiaryRequestId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getBeneficiaryType() {
        return beneficiaryType;
    }

    public void setBeneficiaryType(String beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
    }

    public Object getBeneficiaryBankAccount() {
        return beneficiaryBankAccount;
    }

    public void setBeneficiaryBankAccount(Object beneficiaryBankAccount) {
        this.beneficiaryBankAccount = beneficiaryBankAccount;
    }

    public Object getBeneficiaryAlipayAccount() {
        return beneficiaryAlipayAccount;
    }

    public void setBeneficiaryAlipayAccount(Object beneficiaryAlipayAccount) {
        this.beneficiaryAlipayAccount = beneficiaryAlipayAccount;
    }

    public Object getThirdPartyIdentity() {
        return thirdPartyIdentity;
    }

    public void setThirdPartyIdentity(Object thirdPartyIdentity) {
        this.thirdPartyIdentity = thirdPartyIdentity;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBeneficiaryNick() {
        return beneficiaryNick;
    }

    public void setBeneficiaryNick(String beneficiaryNick) {
        this.beneficiaryNick = beneficiaryNick;
    }

    public String getTemplateCategory() {
        return templateCategory;
    }

    public void setTemplateCategory(String templateCategory) {
        this.templateCategory = templateCategory;
    }

    public String getReferenceBeneficiaryId() {
        return referenceBeneficiaryId;
    }

    public void setReferenceBeneficiaryId(String referenceBeneficiaryId) {
        this.referenceBeneficiaryId = referenceBeneficiaryId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
