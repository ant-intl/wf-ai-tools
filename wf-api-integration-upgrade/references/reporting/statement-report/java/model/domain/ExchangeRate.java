package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 汇率信息对象。
 *
 * <p>用于涉及币种转换的交易场景，记录卖出币种、买入币种和汇率值。
 */
public class ExchangeRate {

    /** 卖出币种 */
    private String sellCurrency;

    /** 买入币种 */
    private String buyCurrency;

    /** 汇率值 */
    private String rate;

    public ExchangeRate() {
    }

    /**
     * Getter method for property <tt>sellCurrency</tt>.
     *
     * @return property value of sellCurrency
     */
    public String getSellCurrency() {
        return sellCurrency;
    }

    /**
     * Setter method for property <tt>sellCurrency</tt>.
     *
     * @param sellCurrency value to be assigned to property sellCurrency
     */
    public void setSellCurrency(String sellCurrency) {
        this.sellCurrency = sellCurrency;
    }

    /**
     * Getter method for property <tt>buyCurrency</tt>.
     *
     * @return property value of buyCurrency
     */
    public String getBuyCurrency() {
        return buyCurrency;
    }

    /**
     * Setter method for property <tt>buyCurrency</tt>.
     *
     * @param buyCurrency value to be assigned to property buyCurrency
     */
    public void setBuyCurrency(String buyCurrency) {
        this.buyCurrency = buyCurrency;
    }

    /**
     * Getter method for property <tt>rate</tt>.
     *
     * @return property value of rate
     */
    public String getRate() {
        return rate;
    }

    /**
     * Setter method for property <tt>rate</tt>.
     *
     * @param rate value to be assigned to property rate
     */
    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
