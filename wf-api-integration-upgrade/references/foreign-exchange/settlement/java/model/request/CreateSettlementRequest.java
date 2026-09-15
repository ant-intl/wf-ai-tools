package {basePackage}.wf.model.request;

import {basePackage}.wf.model.domain.Amount;
import {basePackage}.wf.model.domain.Beneficiary;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst create_a_settlement 请求对象。
 *
 * <p>发起 FX 结算以执行货币兑换。SPOT 结算需提供 quoteId；
 * FORWARD 和 UNFUNDED_SPOT 结算需提供 dealId 并指定卖出或买入金额。
 */
public class CreateSettlementRequest {

    /**
     * 幂等 ID，用于防止重复提交。
     * <p>每次创建结算请求必须唯一。
     */
    private String requestId;

    /**
     * 交易 ID。
     * <p>FORWARD 和 UNFUNDED_SPOT 结算必填；SPOT 结算不得提供。
     */
    private String dealId;

    /**
     * 报价 ID。
     * <p>SPOT 结算必填；FORWARD 和 UNFUNDED_SPOT 结算不得提供。
     */
    private String quoteId;

    /**
     * 卖出金额。
     * <p>SPOT 结算不得提供；FORWARD/UNFUNDED_SPOT 结算时与 buyAmount 二必填。
     */
    private Amount sellAmount;

    /**
     * 买入金额。
     * <p>SPOT 结算不得提供；FORWARD/UNFUNDED_SPOT 结算时与 sellAmount 二必填。
     */
    private Amount buyAmount;

    /**
     * 期望结算日期，YYYY-MM-DD 格式（UTC）。
     * <p>适用于 FORWARD 和 UNFUNDED_SPOT 结算。
     */
    private String settlementDate;

    /**
     * 收款人信息。
     * <p>涉及银行账户出金时提供。
     */
    private Beneficiary beneficiary;

    /**
     * 业务参考号，用于对账追踪。
     */
    private String reference;

    /**
     * 交易备注。
     */
    private String memo;

    public CreateSettlementRequest() {
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
     * Getter method for property <tt>quoteId</tt>.
     *
     * @return property value of quoteId
     */
    public String getQuoteId() {
        return quoteId;
    }

    /**
     * Setter method for property <tt>quoteId</tt>.
     *
     * @param quoteId value to be assigned to property quoteId
     */
    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
