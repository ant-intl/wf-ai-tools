/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 客户信息
 *
 * @author Qoder
 * @version Customer.java, v 0.1 2026-04-16
 */
public class Customer {

    /** 万里汇账户标识（VIRTUAL_ACCOUNT/RECEIVE_ACCOUNT 时返回） */
    private String accountId;

    /** 集成商用户 ID（ALIPAY_WALLET 时返回） */
    private String referenceCustomerId;

    /** 注册公司名 */
    private String customerCompanyName;

    /** 营业执照信息（VIRTUAL_ACCOUNT/RECEIVE_ACCOUNT 时返回） */
    private List<Certificate> certificateList;

    /** 法律实体类型：INDIVIDUAL（个人）、COMPANY（企业），ALIPAY_WALLET 时返回 */
    private String legalEntityType;

    /** 登录账号 */
    private String logonId;

    /** 身份认证等级：NOT_ALLOW_COLLECTION、ALLOW_COLLECTION */
    private String verificationLevel;

    /**
     * Getter method for property <tt>accountId</tt>.
     *
     * @return property value of accountId
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Setter method for property <tt>accountId</tt>.
     *
     * @param accountId value to be assigned to property accountId
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * Getter method for property <tt>referenceCustomerId</tt>.
     *
     * @return property value of referenceCustomerId
     */
    public String getReferenceCustomerId() {
        return referenceCustomerId;
    }

    /**
     * Setter method for property <tt>referenceCustomerId</tt>.
     *
     * @param referenceCustomerId value to be assigned to property referenceCustomerId
     */
    public void setReferenceCustomerId(String referenceCustomerId) {
        this.referenceCustomerId = referenceCustomerId;
    }

    /**
     * Getter method for property <tt>customerCompanyName</tt>.
     *
     * @return property value of customerCompanyName
     */
    public String getCustomerCompanyName() {
        return customerCompanyName;
    }

    /**
     * Setter method for property <tt>customerCompanyName</tt>.
     *
     * @param customerCompanyName value to be assigned to property customerCompanyName
     */
    public void setCustomerCompanyName(String customerCompanyName) {
        this.customerCompanyName = customerCompanyName;
    }

    /**
     * Getter method for property <tt>certificateList</tt>.
     *
     * @return property value of certificateList
     */
    public List<Certificate> getCertificateList() {
        return certificateList;
    }

    /**
     * Setter method for property <tt>certificateList</tt>.
     *
     * @param certificateList value to be assigned to property certificateList
     */
    public void setCertificateList(List<Certificate> certificateList) {
        this.certificateList = certificateList;
    }

    /**
     * Getter method for property <tt>legalEntityType</tt>.
     *
     * @return property value of legalEntityType
     */
    public String getLegalEntityType() {
        return legalEntityType;
    }

    /**
     * Setter method for property <tt>legalEntityType</tt>.
     *
     * @param legalEntityType value to be assigned to property legalEntityType
     */
    public void setLegalEntityType(String legalEntityType) {
        this.legalEntityType = legalEntityType;
    }

    /**
     * Getter method for property <tt>logonId</tt>.
     *
     * @return property value of logonId
     */
    public String getLogonId() {
        return logonId;
    }

    /**
     * Setter method for property <tt>logonId</tt>.
     *
     * @param logonId value to be assigned to property logonId
     */
    public void setLogonId(String logonId) {
        this.logonId = logonId;
    }

    /**
     * Getter method for property <tt>verificationLevel</tt>.
     *
     * @return property value of verificationLevel
     */
    public String getVerificationLevel() {
        return verificationLevel;
    }

    /**
     * Setter method for property <tt>verificationLevel</tt>.
     *
     * @param verificationLevel value to be assigned to property verificationLevel
     */
    public void setVerificationLevel(String verificationLevel) {
        this.verificationLevel = verificationLevel;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
