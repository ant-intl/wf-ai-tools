/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.request;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst inquiryTradeOrder 请求对象。
 *
 * <p>仅适用于 PAY_INTO_CHINA 场景，用于查询 submitTradeOrder 提交的交易订单处理结果。
 *
 * @author Qoder
 * @version InquiryTradeOrderRequest.java, v 0.1 2026-04-03
 */
public class InquiryTradeOrderRequest {

    /** 与 submitTradeOrder 相同的幂等键，最大 64 字符 */
    private String requestId;

    /** 场景码，固定为 PAY_INTO_CHINA */
    private String sceneCode;

    /** 额度累计方式 */
    private String quotaAccumulationMethod;

    /** 额度累计 ID */
    private String quotaAccumulationId;

    /** 交易类型：GOODS（货物贸易）或 SERVICE（服务贸易） */
    private String tradeType;

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
     * Getter method for property <tt>tradeType</tt>.
     *
     * @return property value of tradeType
     */
    public String getTradeType() {
        return tradeType;
    }

    /**
     * Setter method for property <tt>tradeType</tt>.
     *
     * @param tradeType value to be assigned to property tradeType
     */
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
