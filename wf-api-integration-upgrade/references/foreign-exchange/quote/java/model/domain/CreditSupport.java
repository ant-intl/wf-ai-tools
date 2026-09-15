package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 信用支持信息对象。
 *
 * <p>用于远期交易（FORWARD）场景，包含保证金和信用额度信息。
 */
public class CreditSupport {

    /**
     * 保证金币种（ISO 4217 标准，固定 3 字符）。
     */
    private String marginCurrency;

    /**
     * 保证金金额。
     */
    private Amount marginAmount;

    /**
     * 信用额度。
     */
    private Amount creditLineAmount;

    /**
     * 信用额度基准金额。
     */
    private Amount creditLineBaseAmount;

    public CreditSupport() {
    }

    /**
     * Getter method for property <tt>marginCurrency</tt>.
     *
     * @return property value of marginCurrency
     */
    public String getMarginCurrency() {
        return marginCurrency;
    }

    /**
     * Setter method for property <tt>marginCurrency</tt>.
     *
     * @param marginCurrency value to be assigned to property marginCurrency
     */
    public void setMarginCurrency(String marginCurrency) {
        this.marginCurrency = marginCurrency;
    }

    /**
     * Getter method for property <tt>marginAmount</tt>.
     *
     * @return property value of marginAmount
     */
    public Amount getMarginAmount() {
        return marginAmount;
    }

    /**
     * Setter method for property <tt>marginAmount</tt>.
     *
     * @param marginAmount value to be assigned to property marginAmount
     */
    public void setMarginAmount(Amount marginAmount) {
        this.marginAmount = marginAmount;
    }

    /**
     * Getter method for property <tt>creditLineAmount</tt>.
     *
     * @return property value of creditLineAmount
     */
    public Amount getCreditLineAmount() {
        return creditLineAmount;
    }

    /**
     * Setter method for property <tt>creditLineAmount</tt>.
     *
     * @param creditLineAmount value to be assigned to property creditLineAmount
     */
    public void setCreditLineAmount(Amount creditLineAmount) {
        this.creditLineAmount = creditLineAmount;
    }

    /**
     * Getter method for property <tt>creditLineBaseAmount</tt>.
     *
     * @return property value of creditLineBaseAmount
     */
    public Amount getCreditLineBaseAmount() {
        return creditLineBaseAmount;
    }

    /**
     * Setter method for property <tt>creditLineBaseAmount</tt>.
     *
     * @param creditLineBaseAmount value to be assigned to property creditLineBaseAmount
     */
    public void setCreditLineBaseAmount(Amount creditLineBaseAmount) {
        this.creditLineBaseAmount = creditLineBaseAmount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
