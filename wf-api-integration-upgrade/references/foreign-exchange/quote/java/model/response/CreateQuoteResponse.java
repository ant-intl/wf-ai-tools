package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.Amount;
import {basePackage}.wf.model.domain.CreditSupport;
import {basePackage}.wf.model.domain.QuoteDetail;
import {basePackage}.wf.model.domain.SettlementPeriod;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst create_a_quote 响应对象。
 *
 * <p>包含接口调用结果及完整的报价详情。
 */
public class CreateQuoteResponse {

    /** 接口调用结果 */
    private Result result;

    /** 报价详情 */
    private QuoteDetail quote;

    /** 卖出金额 */
    private Amount sellAmount;

    /** 买入金额 */
    private Amount buyAmount;

    /** 交易类型 */
    private String dealType;

    /** 信用支持信息，当 dealType 为 FORWARD 时返回 */
    private CreditSupport creditSupport;

    /** 结算周期信息 */
    private SettlementPeriod settlementPeriod;

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
     * Getter method for property <tt>quote</tt>.
     *
     * @return property value of quote
     */
    public QuoteDetail getQuote() {
        return quote;
    }

    /**
     * Setter method for property <tt>quote</tt>.
     *
     * @param quote value to be assigned to property quote
     */
    public void setQuote(QuoteDetail quote) {
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
