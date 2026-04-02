/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 汇率报价信息。
 *
 * <p>跨币种代发时，需要先通过 consultPayout 获取 quoteId，
 * 然后在 createPayout 请求中传入 transferToDetail.transferQuote.quoteId。
 *
 * @author Qoder
 * @version TransferQuote.java, v 0.1 2026-04-02
 */
public class TransferQuote {

    /**
     * 报价 ID。
     *
     * <p>从 consultPayout 响应中获取，跨币种 createPayout 时传入此字段。
     */
    private String quoteId;

    /**
     * 汇率货币对，如 USD/CNY。
     *
     * <p>响应中返回，表示汇率计算的货币对。
     */
    private String quoteCurrencyPair;

    /**
     * 汇率价格。
     *
     * <p>响应中返回，表示当前使用的汇率。
     */
    private String quotePrice;

    /**
     * 报价开始时间（ISO 8601 格式）。
     *
     * <p>响应中返回，表示报价生效时间。
     */
    private String quoteStartTime;

    /**
     * 报价过期时间（ISO 8601 格式）。
     *
     * <p>响应中返回，表示报价过期时间。超过此时间，quoteId 将失效，需要重新调用 consultPayout 获取新报价。
     */
    private String quoteExpiryTime;

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
     * Getter method for property <tt>quoteCurrencyPair</tt>.
     *
     * @return property value of quoteCurrencyPair
     */
    public String getQuoteCurrencyPair() {
        return quoteCurrencyPair;
    }

    /**
     * Setter method for property <tt>quoteCurrencyPair</tt>.
     *
     * @param quoteCurrencyPair value to be assigned to property quoteCurrencyPair
     */
    public void setQuoteCurrencyPair(String quoteCurrencyPair) {
        this.quoteCurrencyPair = quoteCurrencyPair;
    }

    /**
     * Getter method for property <tt>quotePrice</tt>.
     *
     * @return property value of quotePrice
     */
    public String getQuotePrice() {
        return quotePrice;
    }

    /**
     * Setter method for property <tt>quotePrice</tt>.
     *
     * @param quotePrice value to be assigned to property quotePrice
     */
    public void setQuotePrice(String quotePrice) {
        this.quotePrice = quotePrice;
    }

    /**
     * Getter method for property <tt>quoteStartTime</tt>.
     *
     * @return property value of quoteStartTime
     */
    public String getQuoteStartTime() {
        return quoteStartTime;
    }

    /**
     * Setter method for property <tt>quoteStartTime</tt>.
     *
     * @param quoteStartTime value to be assigned to property quoteStartTime
     */
    public void setQuoteStartTime(String quoteStartTime) {
        this.quoteStartTime = quoteStartTime;
    }

    /**
     * Getter method for property <tt>quoteExpiryTime</tt>.
     *
     * @return property value of quoteExpiryTime
     */
    public String getQuoteExpiryTime() {
        return quoteExpiryTime;
    }

    /**
     * Setter method for property <tt>quoteExpiryTime</tt>.
     *
     * @param quoteExpiryTime value to be assigned to property quoteExpiryTime
     */
    public void setQuoteExpiryTime(String quoteExpiryTime) {
        this.quoteExpiryTime = quoteExpiryTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
