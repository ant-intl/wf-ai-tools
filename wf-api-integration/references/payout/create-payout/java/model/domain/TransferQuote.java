package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 汇率报价信息。
 *
 * <p>跨币种代发时，需要先通过 consultPayout 获取 quoteId，
 * 然后在 createPayout 请求中传入 transferToDetail.transferQuote.quoteId。
 */
public class TransferQuote {

    /** 报价 ID */
    private String quoteId;

    /** 汇率货币对，如 USD/CNY */
    private String quoteCurrencyPair;

    /** 汇率价格 */
    private String quotePrice;

    /** 报价开始时间（ISO 8601 格式） */
    private String quoteStartTime;

    /** 报价过期时间（ISO 8601 格式） */
    private String quoteExpiryTime;

    public String getQuoteId() { return quoteId; }
    public void setQuoteId(String quoteId) { this.quoteId = quoteId; }
    public String getQuoteCurrencyPair() { return quoteCurrencyPair; }
    public void setQuoteCurrencyPair(String quoteCurrencyPair) { this.quoteCurrencyPair = quoteCurrencyPair; }
    public String getQuotePrice() { return quotePrice; }
    public void setQuotePrice(String quotePrice) { this.quotePrice = quotePrice; }
    public String getQuoteStartTime() { return quoteStartTime; }
    public void setQuoteStartTime(String quoteStartTime) { this.quoteStartTime = quoteStartTime; }
    public String getQuoteExpiryTime() { return quoteExpiryTime; }
    public void setQuoteExpiryTime(String quoteExpiryTime) { this.quoteExpiryTime = quoteExpiryTime; }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

