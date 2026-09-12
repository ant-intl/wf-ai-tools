package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.DepositRecord;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_deposit 响应对象。
 *
 * <p>包含接口调用结果及完整的存款记录详情。
 */
public class QueryDepositResponse {

    /** 接口调用结果 */
    private Result result;

    /** 存款唯一标识 */
    private String id;

    /** 收款商户的 WorldFirst 账户 ID */
    private String accountId;

    /** 存款业务类型 */
    private String type;

    /** 存款生命周期状态 */
    private String status;

    /** 付款方发送的原始金额 */
    private Amount sourceAmount;

    /** 实际入账金额 */
    private Amount amount;

    /** 累计退款金额 */
    private Amount refundedAmount;

    /** 手续费金额 */
    private Amount feeAmount;

    /** 跨币种汇率详情 */
    private Quote quote;

    /** 付款方支付方式详情 */
    private InitiatingPaymentMethod initiatingPaymentMethod;

    /** 收款方式 */
    private ReceiveMethod receiveMethod;

    /** 付款方转账备注 */
    private String reference;

    /** 存款记录创建时间 */
    private String createdAt;

    /** 资金入账时间 */
    private String succeededAt;

    /** 全额退款时间 */
    private String refundedAllAt;

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
     * Getter method for property <tt>accountId</tt>.
     *
     * @return property value of accountId
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Setter method for property <tt>accountId</tt>.
     *
     * @param accountId value to be assigned to property accountId
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * Getter method for property <tt>type</tt>.
     *
     * @return property value of type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter method for property <tt>type</tt>.
     *
     * @param type value to be assigned to property type
     */
    public void setType(String type) {
        this.type = type;
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
     * Getter method for property <tt>sourceAmount</tt>.
     *
     * @return property value of sourceAmount
     */
    public {basePackage}.wf.model.domain.Amount getSourceAmount() {
        return sourceAmount;
    }

    /**
     * Setter method for property <tt>sourceAmount</tt>.
     *
     * @param sourceAmount value to be assigned to property sourceAmount
     */
    public void setSourceAmount({basePackage}.wf.model.domain.Amount sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    /**
     * Getter method for property <tt>amount</tt>.
     *
     * @return property value of amount
     */
    public {basePackage}.wf.model.domain.Amount getAmount() {
        return amount;
    }

    /**
     * Setter method for property <tt>amount</tt>.
     *
     * @param amount value to be assigned to property amount
     */
    public void setAmount({basePackage}.wf.model.domain.Amount amount) {
        this.amount = amount;
    }

    /**
     * Getter method for property <tt>refundedAmount</tt>.
     *
     * @return property value of refundedAmount
     */
    public {basePackage}.wf.model.domain.Amount getRefundedAmount() {
        return refundedAmount;
    }

    /**
     * Setter method for property <tt>refundedAmount</tt>.
     *
     * @param refundedAmount value to be assigned to property refundedAmount
     */
    public void setRefundedAmount({basePackage}.wf.model.domain.Amount refundedAmount) {
        this.refundedAmount = refundedAmount;
    }

    /**
     * Getter method for property <tt>feeAmount</tt>.
     *
     * @return property value of feeAmount
     */
    public {basePackage}.wf.model.domain.Amount getFeeAmount() {
        return feeAmount;
    }

    /**
     * Setter method for property <tt>feeAmount</tt>.
     *
     * @param feeAmount value to be assigned to property feeAmount
     */
    public void setFeeAmount({basePackage}.wf.model.domain.Amount feeAmount) {
        this.feeAmount = feeAmount;
    }

    /**
     * Getter method for property <tt>quote</tt>.
     *
     * @return property value of quote
     */
    public {basePackage}.wf.model.domain.Quote getQuote() {
        return quote;
    }

    /**
     * Setter method for property <tt>quote</tt>.
     *
     * @param quote value to be assigned to property quote
     */
    public void setQuote({basePackage}.wf.model.domain.Quote quote) {
        this.quote = quote;
    }

    /**
     * Getter method for property <tt>initiatingPaymentMethod</tt>.
     *
     * @return property value of initiatingPaymentMethod
     */
    public {basePackage}.wf.model.domain.InitiatingPaymentMethod getInitiatingPaymentMethod() {
        return initiatingPaymentMethod;
    }

    /**
     * Setter method for property <tt>initiatingPaymentMethod</tt>.
     *
     * @param initiatingPaymentMethod value to be assigned to property initiatingPaymentMethod
     */
    public void setInitiatingPaymentMethod({basePackage}.wf.model.domain.InitiatingPaymentMethod initiatingPaymentMethod) {
        this.initiatingPaymentMethod = initiatingPaymentMethod;
    }

    /**
     * Getter method for property <tt>receiveMethod</tt>.
     *
     * @return property value of receiveMethod
     */
    public {basePackage}.wf.model.domain.ReceiveMethod getReceiveMethod() {
        return receiveMethod;
    }

    /**
     * Setter method for property <tt>receiveMethod</tt>.
     *
     * @param receiveMethod value to be assigned to property receiveMethod
     */
    public void setReceiveMethod({basePackage}.wf.model.domain.ReceiveMethod receiveMethod) {
        this.receiveMethod = receiveMethod;
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
     * Getter method for property <tt>succeededAt</tt>.
     *
     * @return property value of succeededAt
     */
    public String getSucceededAt() {
        return succeededAt;
    }

    /**
     * Setter method for property <tt>succeededAt</tt>.
     *
     * @param succeededAt value to be assigned to property succeededAt
     */
    public void setSucceededAt(String succeededAt) {
        this.succeededAt = succeededAt;
    }

    /**
     * Getter method for property <tt>refundedAllAt</tt>.
     *
     * @return property value of refundedAllAt
     */
    public String getRefundedAllAt() {
        return refundedAllAt;
    }

    /**
     * Setter method for property <tt>refundedAllAt</tt>.
     *
     * @param refundedAllAt value to be assigned to property refundedAllAt
     */
    public void setRefundedAllAt(String refundedAllAt) {
        this.refundedAllAt = refundedAllAt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
