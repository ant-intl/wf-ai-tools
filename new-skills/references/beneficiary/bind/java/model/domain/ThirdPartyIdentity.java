/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 三方身份信息。
 *
 * <p>当 beneficiaryType 为 THIRD_PARTY_PERSONAL_BANK_ACCOUNT 或 
 * THIRD_PARTY_COMPANY_BANK_ACCOUNT 且 countryCode=CN、currency=CNY 时必填。
 *
 * @author Qoder
 * @version ThirdPartyIdentity.java, v 0.1 2026-03-26
 */
public class ThirdPartyIdentity {

    /**
     * 证件号码。
     * <ul>
     *   <li>个人：身份证号</li>
     *   <li>企业：营业执照号</li>
     * </ul>
     */
    private String certificateNo;

    /** 地址信息 */
    private Address address;

    /** 电话号码 */
    private String phoneNumber;

    /** 邮箱地址 */
    private String email;

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
