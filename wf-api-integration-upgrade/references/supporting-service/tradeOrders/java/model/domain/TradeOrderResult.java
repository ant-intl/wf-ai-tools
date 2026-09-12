package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 贸易订单处理结果对象。
 *
 * <p>描述单笔贸易订单的处理结果，用于 submit 和 query 响应中的 tradeOrders 列表。
 */
public class TradeOrderResult {

    /** 商户分配的订单号回显 */
    private String referenceOrderNo;

    /** 单笔订单处理状态（OrderStatus 枚举） */
    private String orderStatus;

    /** 订单状态描述 */
    private String statusMessage;

    /** 订单级错误码，orderStatus 为 REJECTED 时返回 */
    private String failureCode;

    /** 订单级错误描述，orderStatus 为 REJECTED 时返回 */
    private String failureMessage;

    /** 申报结算金额回显 */
    private Amount tradeAmount;

    /** 实际交易金额回显 */
    private Amount transAmount;

    /** 该订单剩余可申报额度，仅在 query 响应中返回 */
    private Amount remainingAmount;

    /** 资金方向回显：CREDIT（收款）或 DEBIT（退款） */
    private String orderDirection;

    public TradeOrderResult() {
    }

    public String getReferenceOrderNo() {
        return referenceOrderNo;
    }

    public void setReferenceOrderNo(String referenceOrderNo) {
        this.referenceOrderNo = referenceOrderNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getFailureCode() {
        return failureCode;
    }

    public void setFailureCode(String failureCode) {
        this.failureCode = failureCode;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public {basePackage}.wf.model.domain.Amount getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount({basePackage}.wf.model.domain.Amount tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public {basePackage}.wf.model.domain.Amount getTransAmount() {
        return transAmount;
    }

    public void setTransAmount({basePackage}.wf.model.domain.Amount transAmount) {
        this.transAmount = transAmount;
    }

    public {basePackage}.wf.model.domain.Amount getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount({basePackage}.wf.model.domain.Amount remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
