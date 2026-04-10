/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 转账汇率。
 *
 * @author Qoder
 * @version Quote.java, v 0.1 2026-04-01
 */
public class Quote {

    /**
     * 唯一汇率标识ID。
     *
     * <p>最大长度：64 字符
     */
    private String quoteId;

    /**
     * 汇率货币对，由2个符合 ISO-4217 格式的三字母货币代码组成。
     *
     * <p>格式：基础货币/目标货币，例如：USD/GBP。
     *
     * <p>最大长度：16 字符
     */
    private String quoteCurrencyPair;

    /**
     * 汇率价格。
     *
     * <p>最大长度：20 字符
     */
    private String quotePrice;

    /**
     * quotePrice 开始生效的时间。
     *
     * <p>值遵循 ISO 8601 标准格式。例如，"2019-11-27T12:01:01+08:00"。
     */
    private String quoteStartTime;

    /**
     * 汇率过期时间。
     *
     * <p>公式：quoteExpiryTime = quoteStartTime + 30秒。
     *
     * <p>值遵循 ISO 8601 标准格式。例如，"2019-11-27T12:01:01+08:00"。
     */
    private String quoteExpiryTime;

    /**
     * 汇率是否为保证汇率。
     *
     * <p>可取值：
     * <ul>
     *   <li>true：为保证汇率</li>
     *   <li>false：非保证汇率</li>
     * </ul>
     */
    private Boolean guaranteed;

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

    /**
     * Getter method for property <tt>guaranteed</tt>.
     *
     * @return property value of guaranteed
     */
    public Boolean getGuaranteed() {
        return guaranteed;
    }

    /**
     * Setter method for property <tt>guaranteed</tt>.
     *
     * @param guaranteed value to be assigned to property guaranteed
     */
    public void setGuaranteed(Boolean guaranteed) {
        this.guaranteed = guaranteed;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
