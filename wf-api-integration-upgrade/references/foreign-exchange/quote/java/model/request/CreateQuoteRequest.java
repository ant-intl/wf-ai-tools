package {basePackage}.wf.model.request;

import {basePackage}.wf.model.domain.Amount;
import {basePackage}.wf.model.domain.CreditSupport;
import {basePackage}.wf.model.domain.SettlementPeriod;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst create_a_quote 请求对象。
 *
 * <p>用于生成具有约束力的 FX 报价，锁定汇率用于后续交易创建。
 */
public class CreateQuoteRequest {

    /**
     * 卖出金额。
     * <p>与 buyAmount 至少提供一个，且只能有一侧包含 value。
     */
    private Amount sellAmount;

    /**
     * 买入金额。
     * <p>与 sellAmount 至少提供一个，且只能有一侧包含 value。
     */
    private Amount buyAmount;

    /**
     * 交易类型。
     * <p>可选值：SPOT（标准即期）、UNFUNDED_SPOT（无资金即期）、FORWARD（远期）
     */
    private String dealType;

    /**
     * 信用支持信息。
     * <p>当 dealType 为 FORWARD 时必填。
     */
    private CreditSupport creditSupport;

    /**
     * 结算周期。
     * <p>当 dealType 为 SPOT 时禁止使用；当 dealType 为 UNFUNDED_SPOT 或 FORWARD 时必填。
     */
    private SettlementPeriod settlementPeriod;

    public CreateQuoteRequest() {
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
