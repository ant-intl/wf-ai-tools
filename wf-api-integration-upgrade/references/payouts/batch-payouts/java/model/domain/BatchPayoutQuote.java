package {basePackage}.wf.model.domain;

import java.math.BigDecimal;

/** BatchPayoutQuote 领域结构。可选字段保持 null；完整约束见对应 GUIDE。 */
public class BatchPayoutQuote {

    /** Quote ID used for this payout. */
    private String quoteId;

    /** Currency pair (e.g., HKD/USD). Two ISO 4217 three-letter codes joined by /. */
    private String currencyPair;

    /** Applied exchange rate. Up to 6 decimal places. */
    private BigDecimal clientRate;

    /** Time when the rate became effective. ISO 8601 extended format. */
    private String effectiveAt;

    /** Time when the quote expires. ISO 8601 extended format. */
    private String expiresAt;

    public String getQuoteId() { return quoteId; }

    public void setQuoteId(String quoteId) { this.quoteId = quoteId; }

    public String getCurrencyPair() { return currencyPair; }

    public void setCurrencyPair(String currencyPair) { this.currencyPair = currencyPair; }

    public BigDecimal getClientRate() { return clientRate; }

    public void setClientRate(BigDecimal clientRate) { this.clientRate = clientRate; }

    public String getEffectiveAt() { return effectiveAt; }

    public void setEffectiveAt(String effectiveAt) { this.effectiveAt = effectiveAt; }

    public String getExpiresAt() { return expiresAt; }

    public void setExpiresAt(String expiresAt) { this.expiresAt = expiresAt; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "BatchPayoutQuote[REDACTED]"; }
}
