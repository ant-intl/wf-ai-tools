/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 支付宝客户信息
 *
 * @author Qoder
 * @version AlipayCustomer.java, v 0.1 2026-04-16
 */
public class AlipayCustomer {

    /** 支付宝账号 */
    private String alipayNo;

    /** 企业名称 */
    private String companyName;

    /** 所在国家/地区（ISO-3166 二字母） */
    private String region;

    /**
     * Getter method for property <tt>alipayNo</tt>.
     *
     * @return property value of alipayNo
     */
    public String getAlipayNo() {
        return alipayNo;
    }

    /**
     * Setter method for property <tt>alipayNo</tt>.
     *
     * @param alipayNo value to be assigned to property alipayNo
     */
    public void setAlipayNo(String alipayNo) {
        this.alipayNo = alipayNo;
    }

    /**
     * Getter method for property <tt>companyName</tt>.
     *
     * @return property value of companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Setter method for property <tt>companyName</tt>.
     *
     * @param companyName value to be assigned to property companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Getter method for property <tt>region</tt>.
     *
     * @return property value of region
     */
    public String getRegion() {
        return region;
    }

    /**
     * Setter method for property <tt>region</tt>.
     *
     * @param region value to be assigned to property region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
