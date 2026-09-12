package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.Amount;
import {basePackage}.wf.model.domain.Beneficiary;
import {basePackage}.wf.model.domain.SettlementQuote;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst create_a_settlement 响应对象。
 *
 * <p>包含接口调用结果及完整的结算详情。
 */
public class CreateSettlementResponse {

    /** 接口调用结果 */
    private Result result;

    /** 结算唯一标识符 */
    private String id;

    /** 卖出金额 */
    private Amount sellAmount;

    /** 买入金额 */
    private Amount buyAmount;

    /** 报价信息 */
    private SettlementQuote quote;

    /** 交易 ID，FORWARD 和 UNFUNDED_SPOT 结算返回 */
    private String dealId;

    /** 期望结算日期，YYYY-MM-DD 格式（UTC） */
    private String settlementDate;

    /** 收款人信息（如涉及） */
    private Beneficiary beneficiary;

    /** 请求中提供的业务参考号 */
    private String reference;

    /** 请求中提供的交易备注 */
    private String memo;

    /** 结算手续费 */
    private Amount feeAmount;

    /** 当前结算状态：PROCESSING、SUCCESS、FAILED */
    private String status;

    /** 结算创建时间（ISO 8601 格式） */
    private String createdAt;

    /** 结算完成时间（ISO 8601 格式），仅结算成功后返回 */
    private String settledAt;

    /** 结算失败时间（ISO 8601 格式），仅 status 为 FAILED 时返回 */
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
     * Getter method for property <tt>quote</tt>.
     *
     * @return property value of quote
     */
    public SettlementQuote getQuote() {
        return quote;
    }

    /**
     * Setter method for property <tt>quote</tt>.
     *
     * @param quote value to be assigned to property quote
     */
    public void setQuote(SettlementQuote quote) {
        this.quote = quote;
    }

    /**
     * Getter method for property <tt>dealId</tt>.
     *
     * @return property value of dealId
     */
    public String getDealId() {
        return dealId;
    }

    /**
     * Setter method for property <tt>dealId</tt>.
     *
     * @param dealId value to be assigned to property dealId
     */
    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    /**
     * Getter method for property <tt>settlementDate</tt>.
     *
     * @return property value of settlementDate
     */
    public String getSettlementDate() {
        return settlementDate;
    }

    /**
     * Setter method for property <tt>settlementDate</tt>.
     *
     * @param settlementDate value to be assigned to property settlementDate
     */
    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }

    /**
     * Getter method for property <tt>beneficiary</tt>.
     *
     * @return property value of beneficiary
     */
    public Beneficiary getBeneficiary() {
        return beneficiary;
    }

    /**
     * Setter method for property <tt>beneficiary</tt>.
     *
     * @param beneficiary value to be assigned to property beneficiary
     */
    public void setBeneficiary(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }

    /**
     * Getter method for property <tt>reference</tt>.
     *
     * @return property value of reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * Setter method for property <tt>reference</tt>.
     *
     * @param reference value to be assigned to property reference
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * Getter method for property <tt>memo</tt>.
     *
     * @return property value of memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * Setter method for property <tt>memo</tt>.
     *
     * @param memo value to be assigned to property memo
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * Getter method for property <tt>feeAmount</tt>.
     *
     * @return property value of feeAmount
     */
    public Amount getFeeAmount() {
        return feeAmount;
    }

    /**
     * Setter method for property <tt>feeAmount</tt>.
     *
     * @param feeAmount value to be assigned to property feeAmount
     */
    public void setFeeAmount(Amount feeAmount) {
        this.feeAmount = feeAmount;
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
