package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * WorldFirst 交易关联的报价信息对象。
 *
 * <p>用于 Deal 响应中嵌套的报价详情，包含报价 ID、货币对、汇率及有效期。
 */
public class DealQuote {

    /**
     * 报价唯一标识符。
     */
    private String quoteId;

    /**
     * 货币对，格式为 BASE/QUOTE（如 GBP/EUR）。
     */
    private String currencyPair;

    /**
     * 客户端汇率，8 位小数。
     */
    private BigDecimal clientRate;

    /**
     * 报价生效时间（ISO 8601 格式）。
     */
    private String effectiveAt;

    /**
     * 报价过期时间（ISO 8601 格式）。
     */
    private String expiresAt;

    public DealQuote() {
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
