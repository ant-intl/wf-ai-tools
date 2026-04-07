/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

/**
 * 物流公司信息。
 *
 * <p>对应 WF submitTradeOrder 接口请求体中的 {@code shipping.logisticsCompany} 字段。
 *
 * @author Qoder
 * @version LogisticsCompany.java, v 0.1 2026-04-03
 */
public class LogisticsCompany {

    /** 物流公司代码，最大 8 字符 */
    private String providerKey;

    /** 物流公司名称，最大 128 字符 */
    private String providerValue;

    /**
     * Getter method for property <tt>providerKey</tt>.
     *
     * @return property value of providerKey
     */
    public String getProviderKey() {
        return providerKey;
    }

    /**
     * Setter method for property <tt>providerKey</tt>.
     *
     * @param providerKey value to be assigned to property providerKey
     */
    public void setProviderKey(String providerKey) {
        this.providerKey = providerKey;
    }

    /**
     * Getter method for property <tt>providerValue</tt>.
     *
     * @return property value of providerValue
     */
    public String getProviderValue() {
        return providerValue;
    }

    /**
     * Setter method for property <tt>providerValue</tt>.
     *
     * @param providerValue value to be assigned to property providerValue
     */
    public void setProviderValue(String providerValue) {
        this.providerValue = providerValue;
    }

    @Override
    public String toString() {
        return "LogisticsCompany{providerKey='" + providerKey
            + "', providerValue='" + providerValue + "'}";
    }
}
