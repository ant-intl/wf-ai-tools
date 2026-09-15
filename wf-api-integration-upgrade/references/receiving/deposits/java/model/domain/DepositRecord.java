package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 存款记录对象。
 *
 * <p>描述一笔入账款项的完整信息，包括来源金额、入账金额、手续费、汇率、付款方信息和收款方式等。
 * 用于 query_deposit 响应和 list_deposits 响应的 items 列表。
 */
public class DepositRecord {

    /** 存款唯一标识 */
    private String id;

    /** 收款商户的 WorldFirst 账户 ID */
    private String accountId;

    /**
     * 存款业务类型，表示资金来源：
     * THIRD_PARTY、MARKETPLACE、PSP、SAME_NAME_TOPUP、CARD_SAME_NAME_TOPUP、
     * DEVELOPER、FREELANCER、TAX_REFUND、UNKNOWN
     */
    private String type;

    /**
     * 存款生命周期状态：
     * PROCESSING、WAITING_CLAIM、UNDER_CLAIM、SUCCESS、PARTIAL_REFUNDED、REFUNDED、CANCELLED
     */
    private String status;

    /** 付款方发送的原始金额（货币转换和手续费扣除前） */
    private Amount sourceAmount;

    /** 实际入账金额（货币转换和手续费扣除后），status 为 SUCCESS/PARTIAL_REFUNDED/REFUNDED 时返回 */
    private Amount amount;

    /** 累计退款金额，status 为 PARTIAL_REFUNDED/REFUNDED 时返回 */
    private Amount refundedAmount;

    /** 手续费金额，币种与 sourceAmount.currency 一致，status 为 SUCCESS/PARTIAL_REFUNDED/REFUNDED 时返回 */
    private Amount feeAmount;

    /** 跨币种汇率详情，仅跨币种转换完成后返回，同币种存款不返回 */
    private Quote quote;

    /** 付款方支付方式详情，当付款方信息可识别时返回 */
    private InitiatingPaymentMethod initiatingPaymentMethod;

    /** 收款方式，表示资金如何接收 */
    private ReceiveMethod receiveMethod;

    /** 付款方转账备注/附言，当付款方提供了备注时返回 */
    private String reference;

    /** 存款记录创建时间（ISO 8601 格式） */
    private String createdAt;

    /** 资金入账时间（ISO 8601 格式），status 为 SUCCESS/PARTIAL_REFUNDED 时返回 */
    private String succeededAt;

    /** 全额退款时间（ISO 8601 格式），status 为 REFUNDED 时返回 */
    private String refundedAllAt;

    public DepositRecord() {
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
    public Amount getSourceAmount() {
        return sourceAmount;
    }

    /**
     * Setter method for property <tt>sourceAmount</tt>.
     *
     * @param sourceAmount value to be assigned to property sourceAmount
     */
    public void setSourceAmount(Amount sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    /**
     * Getter method for property <tt>amount</tt>.
     *
     * @return property value of amount
     */
    public Amount getAmount() {
        return amount;
    }

    /**
     * Setter method for property <tt>amount</tt>.
     *
     * @param amount value to be assigned to property amount
     */
    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    /**
     * Getter method for property <tt>refundedAmount</tt>.
     *
     * @return property value of refundedAmount
     */
    public Amount getRefundedAmount() {
        return refundedAmount;
    }

    /**
     * Setter method for property <tt>refundedAmount</tt>.
     *
     * @param refundedAmount value to be assigned to property refundedAmount
     */
    public void setRefundedAmount(Amount refundedAmount) {
        this.refundedAmount = refundedAmount;
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
     * Getter method for property <tt>quote</tt>.
     *
     * @return property value of quote
     */
    public Quote getQuote() {
        return quote;
    }

    /**
     * Setter method for property <tt>quote</tt>.
     *
     * @param quote value to be assigned to property quote
     */
    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    /**
     * Getter method for property <tt>initiatingPaymentMethod</tt>.
     *
     * @return property value of initiatingPaymentMethod
     */
    public InitiatingPaymentMethod getInitiatingPaymentMethod() {
        return initiatingPaymentMethod;
    }

    /**
     * Setter method for property <tt>initiatingPaymentMethod</tt>.
     *
     * @param initiatingPaymentMethod value to be assigned to property initiatingPaymentMethod
     */
    public void setInitiatingPaymentMethod(InitiatingPaymentMethod initiatingPaymentMethod) {
        this.initiatingPaymentMethod = initiatingPaymentMethod;
    }

    /**
     * Getter method for property <tt>receiveMethod</tt>.
     *
     * @return property value of receiveMethod
     */
    public ReceiveMethod getReceiveMethod() {
        return receiveMethod;
    }

    /**
     * Setter method for property <tt>receiveMethod</tt>.
     *
     * @param receiveMethod value to be assigned to property receiveMethod
     */
    public void setReceiveMethod(ReceiveMethod receiveMethod) {
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
