package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 汇率查询条件对象。
 *
 * <p>用于 query_rates 请求，定义单个货币对的查询条件。
 */
public class RateCondition {

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

    public RateCondition() {
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
