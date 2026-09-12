package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * WorldFirst 报价详情对象。
 *
 * <p>用于 create_a_quote 响应，包含报价 ID、汇率、有效期等信息。
 */
public class QuoteDetail {

    /**
     * 报价唯一标识符。
     * <p>使用此 ID 进行后续交易创建。
     */
    private String quoteId;

    /**
     * 货币对，格式为 BASE/QUOTE（如 USD/HKD），固定 7 字符。
     */
    private String currencyPair;

    /**
     * 报价汇率，8 位小数。
     */
    private BigDecimal clientRate;

    /**
     * 报价生效时间（ISO 8601 格式）。
     */
    private String effectiveAt;

    /**
     * 报价过期时间（ISO 8601 格式）。
     * <p>必须在此时间前接受报价。
     */
    private String expiresAt;

    public QuoteDetail() {
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
    public BigDecimal getClientRate() {
        return clientRate;
    }

    /**
     * Setter method for property <tt>clientRate</tt>.
     *
     * @param clientRate value to be assigned to property clientRate
     */
    public void setClientRate(BigDecimal clientRate) {
        this.clientRate = clientRate;
    }

    /**
     * Getter method for property <tt>effectiveAt</tt>.
     *
     * @return property value of effectiveAt
     */
    public String getEffectiveAt() {
        return effectiveAt;
    }

    /**
     * Setter method for property <tt>effectiveAt</tt>.
     *
     * @param effectiveAt value to be assigned to property effectiveAt
     */
    public void setEffectiveAt(String effectiveAt) {
        this.effectiveAt = effectiveAt;
    }

    /**
     * Getter method for property <tt>expiresAt</tt>.
     *
     * @return property value of expiresAt
     */
    public String getExpiresAt() {
        return expiresAt;
    }

    /**
     * Setter method for property <tt>expiresAt</tt>.
     *
     * @param expiresAt value to be assigned to property expiresAt
     */
    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
