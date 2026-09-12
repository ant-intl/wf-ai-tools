package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 汇率信息对象。
 *
 * <p>用于跨币种存款场景，记录汇率货币对和客户端汇率。
 */
public class Quote {

    /**
     * 汇率货币对，由两个 ISO 4217 三字母代码以斜杠分隔（如 EUR/USD）。
     */
    private String currencyPair;

    /**
     * 应用于此存款的汇率。
     */
    private String clientRate;

    public Quote() {
    }

    /**
     * Getter method for property <tt>currencyPair</tt>.
     *
     * @return property value of currencyPair
     */
    public String getCurrencyPair() {
        return currencyPair;
    }

    /**
     * Setter method for property <tt>currencyPair</tt>.
     *
     * @param currencyPair value to be assigned to property currencyPair
     */
    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    /**
     * Getter method for property <tt>clientRate</tt>.
     *
     * @return property value of clientRate
     */
    public String getClientRate() {
        return clientRate;
    }

    /**
     * Setter method for property <tt>clientRate</tt>.
     *
     * @param clientRate value to be assigned to property clientRate
     */
    public void setClientRate(String clientRate) {
        this.clientRate = clientRate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
