package {basePackage}.wf.model.response;

import java.util.List;

import {basePackage}.wf.model.domain.TradeOrderResult;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst submit_trade_orders 响应对象。
 *
 * <p>包含接口调用结果、批次标识及每笔订单的同步处理结果。
 */
public class SubmitTradeOrdersResponse {

    /** 接口调用结果 */
    private Result result;

    /** 系统生成的批次标识，用于后续查询处理状态 */
    private String id;

    /** 请求中提供的幂等键回显 */
    private String batchRequestId;

    /** 批次状态，同步阶段始终为 ACCEPTED */
    private String status;

    /** 每笔订单的处理结果 */
    private List<TradeOrderResult> tradeOrders;

    /** 批次创建时间（ISO 8601 扩展格式） */
    private String createdAt;

    public SubmitTradeOrdersResponse() {
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatchRequestId() {
        return batchRequestId;
    }

    public void setBatchRequestId(String batchRequestId) {
        this.batchRequestId = batchRequestId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TradeOrderResult> getTradeOrders() {
        return tradeOrders;
    }

    public void setTradeOrders(List<TradeOrderResult> tradeOrders) {
        this.tradeOrders = tradeOrders;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
