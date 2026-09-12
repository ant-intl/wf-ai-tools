package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 汇率报价对象。
 *
 * <p>用于跨币种代发时锁定汇率。通过 consult_a_payout 获取 quoteId 后，
 * 传入 create_a_payout 的 transferQuote 字段以锁定汇率。
 */
public class Quote {

    /** 报价 ID，由 consult_a_payout 返回 */
    private String quoteId;

    /** 货币对，如 HKD/USD */
    private String currencyPair;

    /** 客户端汇率 */
    private String clientRate;

    /** 报价生效时间（ISO 8601 格式，如 2024-01-01T00:00:00+08:00） */
    private String effectiveAt;

    /** 报价过期时间（ISO 8601 格式，如 2024-01-01T00:30:00+08:00） */
    private String expiresAt;

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
