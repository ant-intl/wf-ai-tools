/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.request;

import java.util.List;

import {basePackage}.wf.model.domain.TradeOrder;

/**
 * WorldFirst submitTradeOrder 请求对象。
 *
 * <p>支持两种场景：
 * <ul>
 *   <li>{@code PAY_INTO_CHINA} — B2C 跨境电商收款入境（最多 100 笔订单）</li>
 *   <li>{@code CREATE_B2B_ORDERS} — B2B 外贸订单创建（最多 10 笔订单）</li>
 * </ul>
 *
 * @author Qoder
 * @version SubmitTradeOrderRequest.java, v 0.1 2026-04-03
 */
public class SubmitTradeOrderRequest {

    /** 幂等键，集成商定义的唯一请求 ID，最大 64 字符 */
    private String requestId;

    /**
     * 场景码。
     *
     * <ul>
     *   <li>PAY_INTO_CHINA — B2C 跨境电商收款入境</li>
     *   <li>CREATE_B2B_ORDERS — B2B 外贸订单创建</li>
     * </ul>
     */
    private String sceneCode;

    /** 额度累计方式 */
    private String quotaAccumulationMethod;

    /** 额度累计 ID */
    private String quotaAccumulationId;

    /** 交易订单列表 */
    private List<TradeOrder> tradeOrders;

    /** 回调通知地址（可选），仅 PAY_INTO_CHINA 场景支持 */
    private String notifyUrl;

    /** 平台标识，PAY_INTO_CHINA（B2C）场景必填 */
    private String platform;

    /** 扩展信息（可选），JSON 格式字符串 */
    private String extendInfo;

    /**
     * Getter method for property <tt>requestId</tt>.
     *
     * @return property value of requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Setter method for property <tt>requestId</tt>.
     *
     * @param requestId value to be assigned to property requestId
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * Getter method for property <tt>sceneCode</tt>.
     *
     * @return property value of sceneCode
     */
    public String getSceneCode() {
        return sceneCode;
    }

    /**
     * Setter method for property <tt>sceneCode</tt>.
     *
     * @param sceneCode value to be assigned to property sceneCode
     */
    public void setSceneCode(String sceneCode) {
        this.sceneCode = sceneCode;
    }

    /**
     * Getter method for property <tt>quotaAccumulationMethod</tt>.
     *
     * @return property value of quotaAccumulationMethod
     */
    public String getQuotaAccumulationMethod() {
        return quotaAccumulationMethod;
    }

    /**
     * Setter method for property <tt>quotaAccumulationMethod</tt>.
     *
     * @param quotaAccumulationMethod value to be assigned to property quotaAccumulationMethod
     */
    public void setQuotaAccumulationMethod(String quotaAccumulationMethod) {
        this.quotaAccumulationMethod = quotaAccumulationMethod;
    }

    /**
     * Getter method for property <tt>quotaAccumulationId</tt>.
     *
     * @return property value of quotaAccumulationId
     */
    public String getQuotaAccumulationId() {
        return quotaAccumulationId;
    }

    /**
     * Setter method for property <tt>quotaAccumulationId</tt>.
     *
     * @param quotaAccumulationId value to be assigned to property quotaAccumulationId
     */
    public void setQuotaAccumulationId(String quotaAccumulationId) {
        this.quotaAccumulationId = quotaAccumulationId;
    }

    /**
     * Getter method for property <tt>tradeOrders</tt>.
     *
     * @return property value of tradeOrders
     */
    public List<TradeOrder> getTradeOrders() {
        return tradeOrders;
    }

    /**
     * Setter method for property <tt>tradeOrders</tt>.
     *
     * @param tradeOrders value to be assigned to property tradeOrders
     */
    public void setTradeOrders(List<TradeOrder> tradeOrders) {
        this.tradeOrders = tradeOrders;
    }

    /**
     * Getter method for property <tt>notifyUrl</tt>.
     *
     * @return property value of notifyUrl
     */
    public String getNotifyUrl() {
        return notifyUrl;
    }

    /**
     * Setter method for property <tt>notifyUrl</tt>.
     *
     * @param notifyUrl value to be assigned to property notifyUrl
     */
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    /**
     * Getter method for property <tt>platform</tt>.
     *
     * @return property value of platform
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * Setter method for property <tt>platform</tt>.
     *
     * @param platform value to be assigned to property platform
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * Getter method for property <tt>extendInfo</tt>.
     *
     * @return property value of extendInfo
     */
    public String getExtendInfo() {
        return extendInfo;
    }

    /**
     * Setter method for property <tt>extendInfo</tt>.
     *
     * @param extendInfo value to be assigned to property extendInfo
     */
    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }

    @Override
    public String toString() {
        return "SubmitTradeOrderRequest{requestId='" + requestId
            + "', sceneCode='" + sceneCode
            + "', quotaAccumulationMethod='" + quotaAccumulationMethod
            + "', quotaAccumulationId='" + quotaAccumulationId
            + "', tradeOrders=" + tradeOrders
            + ", notifyUrl='" + notifyUrl
            + "', platform='" + platform
            + "', extendInfo='" + extendInfo + "'}";
    }
}
