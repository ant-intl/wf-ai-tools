package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.Amount;
import {basePackage}.wf.model.domain.CreditSupport;
import {basePackage}.wf.model.domain.DealQuote;
import {basePackage}.wf.model.domain.SettlementPeriod;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst create_a_deal 响应对象。
 *
 * <p>包含接口调用结果及完整的交易详情。
 */
public class CreateDealResponse {

    /** 接口调用结果 */
    private Result result;

    /** 交易唯一标识符 */
    private String id;

    /** 交易类型：SPOT、UNFUNDED_SPOT、FORWARD */
    private String dealType;

    /** 报价信息 */
    private DealQuote quote;

    /** 卖出金额 */
    private Amount sellAmount;

    /** 买入金额 */
    private Amount buyAmount;

    /** 信用支持信息，仅 FORWARD 交易返回 */
    private CreditSupport creditSupport;

    /** 结算周期信息，UNFUNDED_SPOT 和 FORWARD 交易返回 */
    private SettlementPeriod settlementPeriod;

    /** 未结算买入金额，UNFUNDED_SPOT 和 FORWARD 交易在 PROCESSING 或 CANCELED 状态时返回 */
    private Amount unSettleBuyAmount;

    /** 未结算卖出金额，UNFUNDED_SPOT 和 FORWARD 交易在 PROCESSING 或 CANCELED 状态时返回 */
    private Amount unSettleSellAmount;

    /** 取消费用金额，当 status 为 CANCELED 时返回 */
    private Amount cancelFeeAmount;

    /** 当前交易状态 */
    private String status;

    /** 交易创建时间（ISO 8601 格式） */
    private String createdAt;

    /** 交易结算时间（ISO 8601 格式），仅结算后返回 */
    private String settledAt;

    /** 交易取消时间（ISO 8601 格式），仅取消后返回 */
    private String cancelledAt;

    /** 交易失败时间（ISO 8601 格式），仅 status 为 FAILED 时返回 */
    private String failedAt;

    /** 失败代码，仅 status 为 FAILED 时返回 */
    private String failureCode;

    /** 失败描述信息，仅 status 为 FAILED 时返回 */
    private String failureMessage;

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
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id value to be assigned to property id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>dealType</tt>.
     *
     * @return property value of dealType
     */
    public String getDealType() {
        return dealType;
    }

    /**
     * Setter method for property <tt>dealType</tt>.
     *
     * @param dealType value to be assigned to property dealType
     */
    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    /**
     * Getter method for property <tt>quote</tt>.
     *
     * @return property value of quote
     */
    public DealQuote getQuote() {
        return quote;
    }

    /**
     * Setter method for property <tt>quote</tt>.
     *
     * @param quote value to be assigned to property quote
     */
    public void setQuote(DealQuote quote) {
        this.quote = quote;
    }

    /**
     * Getter method for property <tt>sellAmount</tt>.
     *
     * @return property value of sellAmount
     */
    public Amount getSellAmount() {
        return sellAmount;
    }

    /**
     * Setter method for property <tt>sellAmount</tt>.
     *
     * @param sellAmount value to be assigned to property sellAmount
     */
    public void setSellAmount(Amount sellAmount) {
        this.sellAmount = sellAmount;
    }

    /**
     * Getter method for property <tt>buyAmount</tt>.
     *
     * @return property value of buyAmount
     */
    public Amount getBuyAmount() {
        return buyAmount;
    }

    /**
     * Setter method for property <tt>buyAmount</tt>.
     *
     * @param buyAmount value to be assigned to property buyAmount
     */
    public void setBuyAmount(Amount buyAmount) {
        this.buyAmount = buyAmount;
    }

    /**
     * Getter method for property <tt>creditSupport</tt>.
     *
     * @return property value of creditSupport
     */
    public CreditSupport getCreditSupport() {
        return creditSupport;
    }

    /**
     * Setter method for property <tt>creditSupport</tt>.
     *
     * @param creditSupport value to be assigned to property creditSupport
     */
    public void setCreditSupport(CreditSupport creditSupport) {
        this.creditSupport = creditSupport;
    }

    /**
     * Getter method for property <tt>settlementPeriod</tt>.
     *
     * @return property value of settlementPeriod
     */
    public SettlementPeriod getSettlementPeriod() {
        return settlementPeriod;
    }

    /**
     * Setter method for property <tt>settlementPeriod</tt>.
     *
     * @param settlementPeriod value to be assigned to property settlementPeriod
     */
    public void setSettlementPeriod(SettlementPeriod settlementPeriod) {
        this.settlementPeriod = settlementPeriod;
    }

    /**
     * Getter method for property <tt>unSettleBuyAmount</tt>.
     *
     * @return property value of unSettleBuyAmount
     */
    public Amount getUnSettleBuyAmount() {
        return unSettleBuyAmount;
    }

    /**
     * Setter method for property <tt>unSettleBuyAmount</tt>.
     *
     * @param unSettleBuyAmount value to be assigned to property unSettleBuyAmount
     */
    public void setUnSettleBuyAmount(Amount unSettleBuyAmount) {
        this.unSettleBuyAmount = unSettleBuyAmount;
    }

    /**
     * Getter method for property <tt>unSettleSellAmount</tt>.
     *
     * @return property value of unSettleSellAmount
     */
    public Amount getUnSettleSellAmount() {
        return unSettleSellAmount;
    }

    /**
     * Setter method for property <tt>unSettleSellAmount</tt>.
     *
     * @param unSettleSellAmount value to be assigned to property unSettleSellAmount
     */
    public void setUnSettleSellAmount(Amount unSettleSellAmount) {
        this.unSettleSellAmount = unSettleSellAmount;
    }

    /**
     * Getter method for property <tt>cancelFeeAmount</tt>.
     *
     * @return property value of cancelFeeAmount
     */
    public Amount getCancelFeeAmount() {
        return cancelFeeAmount;
    }

    /**
     * Setter method for property <tt>cancelFeeAmount</tt>.
     *
     * @param cancelFeeAmount value to be assigned to property cancelFeeAmount
     */
    public void setCancelFeeAmount(Amount cancelFeeAmount) {
        this.cancelFeeAmount = cancelFeeAmount;
    }

    /**
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     *
     * @param status value to be assigned to property status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Getter method for property <tt>createdAt</tt>.
     *
     * @return property value of createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Setter method for property <tt>createdAt</tt>.
     *
     * @param createdAt value to be assigned to property createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Getter method for property <tt>settledAt</tt>.
     *
     * @return property value of settledAt
     */
    public String getSettledAt() {
        return settledAt;
    }

    /**
     * Setter method for property <tt>settledAt</tt>.
     *
     * @param settledAt value to be assigned to property settledAt
     */
    public void setSettledAt(String settledAt) {
        this.settledAt = settledAt;
    }

    /**
     * Getter method for property <tt>cancelledAt</tt>.
     *
     * @return property value of cancelledAt
     */
    public String getCancelledAt() {
        return cancelledAt;
    }

    /**
     * Setter method for property <tt>cancelledAt</tt>.
     *
     * @param cancelledAt value to be assigned to property cancelledAt
     */
    public void setCancelledAt(String cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    /**
     * Getter method for property <tt>failedAt</tt>.
     *
     * @return property value of failedAt
     */
    public String getFailedAt() {
        return failedAt;
    }

    /**
     * Setter method for property <tt>failedAt</tt>.
     *
     * @param failedAt value to be assigned to property failedAt
     */
    public void setFailedAt(String failedAt) {
        this.failedAt = failedAt;
    }

    /**
     * Getter method for property <tt>failureCode</tt>.
     *
     * @return property value of failureCode
     */
    public String getFailureCode() {
        return failureCode;
    }

    /**
     * Setter method for property <tt>failureCode</tt>.
     *
     * @param failureCode value to be assigned to property failureCode
     */
    public void setFailureCode(String failureCode) {
        this.failureCode = failureCode;
    }

    /**
     * Getter method for property <tt>failureMessage</tt>.
     *
     * @return property value of failureMessage
     */
    public String getFailureMessage() {
        return failureMessage;
    }

    /**
     * Setter method for property <tt>failureMessage</tt>.
     *
     * @param failureMessage value to be assigned to property failureMessage
     */
    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
