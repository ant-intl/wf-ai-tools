/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

/**
 * WorldFirst 汇率报价信息
 *
 * <p>对应 statementList 中的 foreignExchangeQuote 和 refundForeignExchangeQuote 字段。
 *
 * @author Qoder
 * @version ForeignExchangeQuote.java, v 0.1 2026-03-24
 */
public class ForeignExchangeQuote {

    /** 汇率价格，如 7.24 表示 1 源币种 = 7.24 目标币种 */
    private String quotePrice;

    /** 源币种（ISO-4217） */
    private String transferFromCurrency;

    /** 目标币种（ISO-4217） */
    private String transferToCurrency;

    /**
     * Getter method for property <tt>quotePrice</tt>.
     */
    public String getQuotePrice() {
        return quotePrice;
    }

    /**
     * Setter method for property <tt>quotePrice</tt>.
     */
    public void setQuotePrice(String quotePrice) {
        this.quotePrice = quotePrice;
    }

    /**
     * Getter method for property <tt>transferFromCurrency</tt>.
     */
    public String getTransferFromCurrency() {
        return transferFromCurrency;
    }

    /**
     * Setter method for property <tt>transferFromCurrency</tt>.
     */
    public void setTransferFromCurrency(String transferFromCurrency) {
        this.transferFromCurrency = transferFromCurrency;
    }

    /**
     * Getter method for property <tt>transferToCurrency</tt>.
     */
    public String getTransferToCurrency() {
        return transferToCurrency;
    }

    /**
     * Setter method for property <tt>transferToCurrency</tt>.
     */
    public void setTransferToCurrency(String transferToCurrency) {
        this.transferToCurrency = transferToCurrency;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    }
}
