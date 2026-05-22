package {basePackage}.wf.model.response;

import java.util.List;

import {basePackage}.wf.model.domain.TradeOrderResult;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst submitTradeOrder 响应对象。
 *
 * <p>根据 sceneCode 不同，返回内容有所区别：
 * <ul>
 *   <li>{@code PAY_INTO_CHINA} — 返回 requestId 和 tradeOrderResult 列表</li>
 *   <li>{@code CREATE_B2B_ORDERS} — 返回 acceptOrderId（受理单号）</li>
 * </ul>
 *
 */
public class SubmitTradeOrderResponse {

    /** 接口调用结果 */
    private Result result;

    /** PAY_INTO_CHINA 场景回传的请求 ID */
    private String requestId;

    /** PAY_INTO_CHINA 场景的交易订单处理结果列表 */
    private List<TradeOrderResult> tradeOrderResult;

    /** CREATE_B2B_ORDERS 场景的受理单号 */
    private String acceptOrderId;

    /**
     * Getter method for property <tt>result</tt>.
     *
     * @return property value of result
     */
    public Result getResult() {
        return result;
    }

    /**
     * Setter method for property <tt>result</tt>.
     *
     * @param result value to be assigned to property result
     */
    public void setResult(Result result) {
        this.result = result;
    }

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
     * Getter method for property <tt>tradeOrderResult</tt>.
     *
     * @return property value of tradeOrderResult
     */
    public List<TradeOrderResult> getTradeOrderResult() {
        return tradeOrderResult;
    }

    /**
     * Setter method for property <tt>tradeOrderResult</tt>.
     *
     * @param tradeOrderResult value to be assigned to property tradeOrderResult
     */
    public void setTradeOrderResult(List<TradeOrderResult> tradeOrderResult) {
        this.tradeOrderResult = tradeOrderResult;
    }

    /**
     * Getter method for property <tt>acceptOrderId</tt>.
     *
     * @return property value of acceptOrderId
     */
    public String getAcceptOrderId() {
        return acceptOrderId;
    }

    /**
     * Setter method for property <tt>acceptOrderId</tt>.
     *
     * @param acceptOrderId value to be assigned to property acceptOrderId
     */
    public void setAcceptOrderId(String acceptOrderId) {
        this.acceptOrderId = acceptOrderId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
