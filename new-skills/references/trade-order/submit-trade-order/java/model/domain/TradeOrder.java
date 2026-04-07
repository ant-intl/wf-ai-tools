/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 交易订单主对象，适用于 B2C 和 B2B 场景。
 *
 * <p>对应 WF submitTradeOrder 接口请求体中的交易订单信息。
 * B2C 场景需要传入 merchant/seller/buyer 等字段，
 * B2B 场景需要传入 tradeTerms/bizContractInfo 等字段。
 *
 * @author Qoder
 * @version TradeOrder.java, v 0.1 2026-04-03
 */
public class TradeOrder {

    // -------------------------------------------------------------------------
    // 通用字段（B2C + B2B）
    // -------------------------------------------------------------------------

    /** 交易订单编号，最大 64 字符 */
    private String referenceOrderNo;

    /** 付款时间（ISO8601），不能晚于接口调用时间 */
    private String paymentTime;

    /** 交易金额（B2C: 买家支付金额; B2B: 预期关联金额） */
    private Amount transAmount;

    /** 贸易金额（B2C: 卖家收到金额; B2B: 批次总金额） */
    private Amount tradeAmount;

    /** 贸易类型：GOODS 或 SERVICE */
    private String tradeType;

    /** 商品列表，tradeType=GOODS 时必填 */
    private List<Goods> goods;

    /** 物流信息，tradeType=GOODS 时必填 */
    private Shipping shipping;

    /** 市场标识，CHN 或 USA */
    private String market;

    /** 分润比例 */
    private String revenueShare;

    // -------------------------------------------------------------------------
    // B2C 专属字段
    // -------------------------------------------------------------------------

    /** 下单时间（ISO8601），不能晚于接口调用时间 */
    private String orderTime;

    /** 订单类型：LOAN（付款）或 REFUND（退款） */
    private String orderType;

    /** 商户信息 */
    private Merchant merchant;

    /** 卖家信息 */
    private Customer seller;

    /** 买家信息 */
    private Buyer buyer;

    // -------------------------------------------------------------------------
    // B2B 专属字段
    // -------------------------------------------------------------------------

    /** 贸易条款 */
    private String tradeTerms;

    /** 是否用于结汇：Y 或 N */
    private String isUsedForExchange;

    /** 贸易合同信息 */
    private BizContractInfo bizContractInfo;

    /** 物流模式：DROPSHIPPING 或 REGULAR_MODE */
    private String logisticsMode;

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    /**
     * Getter method for property <tt>referenceOrderNo</tt>.
     *
     * @return property value of referenceOrderNo
     */
    public String getReferenceOrderNo() {
        return referenceOrderNo;
    }

    /**
     * Setter method for property <tt>referenceOrderNo</tt>.
     *
     * @param referenceOrderNo value to be assigned to property referenceOrderNo
     */
    public void setReferenceOrderNo(String referenceOrderNo) {
        this.referenceOrderNo = referenceOrderNo;
    }

    /**
     * Getter method for property <tt>paymentTime</tt>.
     *
     * @return property value of paymentTime
     */
    public String getPaymentTime() {
        return paymentTime;
    }

    /**
     * Setter method for property <tt>paymentTime</tt>.
     *
     * @param paymentTime value to be assigned to property paymentTime
     */
    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    /**
     * Getter method for property <tt>transAmount</tt>.
     *
     * @return property value of transAmount
     */
    public Amount getTransAmount() {
        return transAmount;
    }

    /**
     * Setter method for property <tt>transAmount</tt>.
     *
     * @param transAmount value to be assigned to property transAmount
     */
    public void setTransAmount(Amount transAmount) {
        this.transAmount = transAmount;
    }

    /**
     * Getter method for property <tt>tradeAmount</tt>.
     *
     * @return property value of tradeAmount
     */
    public Amount getTradeAmount() {
        return tradeAmount;
    }

    /**
     * Setter method for property <tt>tradeAmount</tt>.
     *
     * @param tradeAmount value to be assigned to property tradeAmount
     */
    public void setTradeAmount(Amount tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    /**
     * Getter method for property <tt>tradeType</tt>.
     *
     * @return property value of tradeType
     */
    public String getTradeType() {
        return tradeType;
    }

    /**
     * Setter method for property <tt>tradeType</tt>.
     *
     * @param tradeType value to be assigned to property tradeType
     */
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * Getter method for property <tt>goods</tt>.
     *
     * @return property value of goods
     */
    public List<Goods> getGoods() {
        return goods;
    }

    /**
     * Setter method for property <tt>goods</tt>.
     *
     * @param goods value to be assigned to property goods
     */
    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }

    /**
     * Getter method for property <tt>shipping</tt>.
     *
     * @return property value of shipping
     */
    public Shipping getShipping() {
        return shipping;
    }

    /**
     * Setter method for property <tt>shipping</tt>.
     *
     * @param shipping value to be assigned to property shipping
     */
    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    /**
     * Getter method for property <tt>market</tt>.
     *
     * @return property value of market
     */
    public String getMarket() {
        return market;
    }

    /**
     * Setter method for property <tt>market</tt>.
     *
     * @param market value to be assigned to property market
     */
    public void setMarket(String market) {
        this.market = market;
    }

    /**
     * Getter method for property <tt>revenueShare</tt>.
     *
     * @return property value of revenueShare
     */
    public String getRevenueShare() {
        return revenueShare;
    }

    /**
     * Setter method for property <tt>revenueShare</tt>.
     *
     * @param revenueShare value to be assigned to property revenueShare
     */
    public void setRevenueShare(String revenueShare) {
        this.revenueShare = revenueShare;
    }

    /**
     * Getter method for property <tt>orderTime</tt>.
     *
     * @return property value of orderTime
     */
    public String getOrderTime() {
        return orderTime;
    }

    /**
     * Setter method for property <tt>orderTime</tt>.
     *
     * @param orderTime value to be assigned to property orderTime
     */
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * Getter method for property <tt>orderType</tt>.
     *
     * @return property value of orderType
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * Setter method for property <tt>orderType</tt>.
     *
     * @param orderType value to be assigned to property orderType
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * Getter method for property <tt>merchant</tt>.
     *
     * @return property value of merchant
     */
    public Merchant getMerchant() {
        return merchant;
    }

    /**
     * Setter method for property <tt>merchant</tt>.
     *
     * @param merchant value to be assigned to property merchant
     */
    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    /**
     * Getter method for property <tt>seller</tt>.
     *
     * @return property value of seller
     */
    public Customer getSeller() {
        return seller;
    }

    /**
     * Setter method for property <tt>seller</tt>.
     *
     * @param seller value to be assigned to property seller
     */
    public void setSeller(Customer seller) {
        this.seller = seller;
    }

    /**
     * Getter method for property <tt>buyer</tt>.
     *
     * @return property value of buyer
     */
    public Buyer getBuyer() {
        return buyer;
    }

    /**
     * Setter method for property <tt>buyer</tt>.
     *
     * @param buyer value to be assigned to property buyer
     */
    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    /**
     * Getter method for property <tt>tradeTerms</tt>.
     *
     * @return property value of tradeTerms
     */
    public String getTradeTerms() {
        return tradeTerms;
    }

    /**
     * Setter method for property <tt>tradeTerms</tt>.
     *
     * @param tradeTerms value to be assigned to property tradeTerms
     */
    public void setTradeTerms(String tradeTerms) {
        this.tradeTerms = tradeTerms;
    }

    /**
     * Getter method for property <tt>isUsedForExchange</tt>.
     *
     * @return property value of isUsedForExchange
     */
    public String getIsUsedForExchange() {
        return isUsedForExchange;
    }

    /**
     * Setter method for property <tt>isUsedForExchange</tt>.
     *
     * @param isUsedForExchange value to be assigned to property isUsedForExchange
     */
    public void setIsUsedForExchange(String isUsedForExchange) {
        this.isUsedForExchange = isUsedForExchange;
    }

    /**
     * Getter method for property <tt>bizContractInfo</tt>.
     *
     * @return property value of bizContractInfo
     */
    public BizContractInfo getBizContractInfo() {
        return bizContractInfo;
    }

    /**
     * Setter method for property <tt>bizContractInfo</tt>.
     *
     * @param bizContractInfo value to be assigned to property bizContractInfo
     */
    public void setBizContractInfo(BizContractInfo bizContractInfo) {
        this.bizContractInfo = bizContractInfo;
    }

    /**
     * Getter method for property <tt>logisticsMode</tt>.
     *
     * @return property value of logisticsMode
     */
    public String getLogisticsMode() {
        return logisticsMode;
    }

    /**
     * Setter method for property <tt>logisticsMode</tt>.
     *
     * @param logisticsMode value to be assigned to property logisticsMode
     */
    public void setLogisticsMode(String logisticsMode) {
        this.logisticsMode = logisticsMode;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
