package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 交易订单提交响应结果。
 *
 * <p>对应 WF submitTradeOrder 接口的响应体（PAY_INTO_CHINA 场景）。
 *
 */
public class TradeOrderResult {

    /** 交易订单编号，最大 64 字符 */
    private String referenceOrderNo;

    /**
     * 交易订单状态。
     *
     * <ul>
     *   <li>{@code REJECT} — 拒绝</li>
     *   <li>{@code AVAILABLE} — 可用</li>
     *   <li>{@code ACCEPT} — 接受</li>
     *   <li>{@code PARTIAL_DECLARED} — 部分报关</li>
     * </ul>
     */
    private String orderStatus;

    /** 订单类型：LOAN（付款）或 REFUND（退款），PAY_INTO_CHINA 时返回 */
    private String orderType;

    /** 交易订单状态详情 */
    private String statusMessage;

    /** 交易金额 */
    private Amount transAmount;

    /** 贸易金额 */
    private Amount tradeAmount;

    /** 通过上传本笔交易单积累的可用结汇额度 */
    private Amount remainAmount;

    /** 错误码（错误场景下返回） */
    private String errorCode;

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    /**
     * Getter method for property <tt>referenceOrderNo</tt>.
     *
     * @return property value of referenceOrderNo
     */
    public String getReferenceOrderNo() {
        return referenceOrderNo;
    }

    /**
     * Setter method for property <tt>referenceOrderNo</tt>.
     *
     * @param referenceOrderNo value to be assigned to property referenceOrderNo
     */
    public void setReferenceOrderNo(String referenceOrderNo) {
        this.referenceOrderNo = referenceOrderNo;
    }

    /**
     * Getter method for property <tt>orderStatus</tt>.
     *
     * @return property value of orderStatus
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * Setter method for property <tt>orderStatus</tt>.
     *
     * @param orderStatus value to be assigned to property orderStatus
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * Getter method for property <tt>orderType</tt>.
     *
     * @return property value of orderType
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * Setter method for property <tt>orderType</tt>.
     *
     * @param orderType value to be assigned to property orderType
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * Getter method for property <tt>statusMessage</tt>.
     *
     * @return property value of statusMessage
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * Setter method for property <tt>statusMessage</tt>.
     *
     * @param statusMessage value to be assigned to property statusMessage
     */
    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    /**
     * Getter method for property <tt>transAmount</tt>.
     *
     * @return property value of transAmount
     */
    public Amount getTransAmount() {
        return transAmount;
    }

    /**
     * Setter method for property <tt>transAmount</tt>.
     *
     * @param transAmount value to be assigned to property transAmount
     */
    public void setTransAmount(Amount transAmount) {
        this.transAmount = transAmount;
    }

    /**
     * Getter method for property <tt>tradeAmount</tt>.
     *
     * @return property value of tradeAmount
     */
    public Amount getTradeAmount() {
        return tradeAmount;
    }

    /**
     * Setter method for property <tt>tradeAmount</tt>.
     *
     * @param tradeAmount value to be assigned to property tradeAmount
     */
    public void setTradeAmount(Amount tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    /**
     * Getter method for property <tt>remainAmount</tt>.
     *
     * @return property value of remainAmount
     */
    public Amount getRemainAmount() {
        return remainAmount;
    }

    /**
     * Setter method for property <tt>remainAmount</tt>.
     *
     * @param remainAmount value to be assigned to property remainAmount
     */
    public void setRemainAmount(Amount remainAmount) {
        this.remainAmount = remainAmount;
    }

    /**
     * Getter method for property <tt>errorCode</tt>.
     *
     * @return property value of errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Setter method for property <tt>errorCode</tt>.
     *
     * @param errorCode value to be assigned to property errorCode
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
