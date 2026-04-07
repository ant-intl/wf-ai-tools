/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.request;

import java.util.List;

import {basePackage}.wf.model.domain.TradeOrderResult;

/**
 * WorldFirst notifyTradeOrder 回调请求对象（仅 PAY_INTO_CHINA 场景）。
 *
 * <p>WF 在交易订单处理完成后，主动 POST 到集成商提供的 notifyUrl，
 * 携带订单处理结果列表。
 *
 * @author Qoder
 * @version NotifyTradeOrderRequest.java, v 0.1 2026-04-03
 */
public class NotifyTradeOrderRequest {

    /** 对应 submitTradeOrder 的请求 ID */
    private String requestId;

    /** 交易订单处理结果列表 */
    private List<TradeOrderResult> tradeOrderResults;

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
     * Getter method for property <tt>tradeOrderResults</tt>.
     *
     * @return property value of tradeOrderResults
     */
    public List<TradeOrderResult> getTradeOrderResults() {
        return tradeOrderResults;
    }

    /**
     * Setter method for property <tt>tradeOrderResults</tt>.
     *
     * @param tradeOrderResults value to be assigned to property tradeOrderResults
     */
    public void setTradeOrderResults(List<TradeOrderResult> tradeOrderResults) {
        this.tradeOrderResults = tradeOrderResults;
    }

    @Override
    public String toString() {
        return "NotifyTradeOrderRequest{requestId='" + requestId
            + "', tradeOrderResults=" + tradeOrderResults + '}';
    }
}
