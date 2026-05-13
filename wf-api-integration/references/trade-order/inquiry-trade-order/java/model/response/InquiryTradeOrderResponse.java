package {basePackage}.wf.model.response;

import java.util.List;

import {basePackage}.wf.model.domain.TradeOrderResult;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst inquiryTradeOrder 响应对象。
 *
 * <p>包含批次级别的处理状态和每笔交易订单的处理结果。
 *
 * <p>batchStatus 取值：
 * <ul>
 *   <li>{@code PROCESSING} — 批次处理中，需继续轮询</li>
 *   <li>{@code FINISHED} — 批次处理完成，可查看各订单结果</li>
 * </ul>
 *
 */
public class InquiryTradeOrderResponse {

    /** 接口调用结果 */
    private Result result;

    /** 请求 ID（回传） */
    private String requestId;

    /** 批次处理状态：PROCESSING 或 FINISHED */
    private String batchStatus;

    /** 交易订单处理结果列表 */
    private List<TradeOrderResult> tradeOrderResults;

    /**
     * 批次是否仍在处理中（需继续轮询）。
     *
     * @return batchStatus 为 PROCESSING 时返回 true
     */
    public boolean isProcessing() {
        return "PROCESSING".equals(batchStatus);
    }

    /**
     * 批次是否已处理完成。
     *
     * @return batchStatus 为 FINISHED 时返回 true
     */
    public boolean isFinished() {
        return "FINISHED".equals(batchStatus);
    }

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
     * Getter method for property <tt>batchStatus</tt>.
     *
     * @return property value of batchStatus
     */
    public String getBatchStatus() {
        return batchStatus;
    }

    /**
     * Setter method for property <tt>batchStatus</tt>.
     *
     * @param batchStatus value to be assigned to property batchStatus
     */
    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
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
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
