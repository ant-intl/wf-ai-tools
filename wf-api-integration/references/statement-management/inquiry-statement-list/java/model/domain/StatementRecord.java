/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 账单流水记录
 *
 * @author Qoder
 * @version StatementRecord.java, v 0.1 2026-03-24
 */
public class StatementRecord {

    /** 交易 ID */
    private String transactionId;

    /** 交易时间，ISO 8601 格式，如 2024-01-15T10:30:00Z */
    private String transactionTime;

    /** 交易类型，如 TRANSFER、CHARGE、TRANSFER_REFUND、CHARGE_REFUND 等 */
    private String transactionType;

    /** 交易状态，如 SUCCESS、PROCESSING、FAIL、REFUNDED 等 */
    private String transactionStatus;

    /** 余额类型，如 NORMAL_BALANCE、BUDGET_BALANCE 等 */
    private String balanceType;

    /** 账户余额（交易后） */
    private Amount accountBalance;

    /** 手续费金额 */
    private Amount feeAmount;

    /** 净额（扣除手续费后实际转账金额） */
    private Amount netAmount;

    /** 原始交易金额 */
    private Amount originalTransactionAmount;

    /** 收款方实际到账金额 */
    private Amount receiveAmount;

    /** 本次交易变动金额（正为收入，负为支出） */
    private Amount transactionAmount;

    /** 外部交易流水号（调用方传入的幂等号） */
    private String extTransactionId;

    /** 核算业务流水号 */
    private String accountingBizNo;

    /** 汇率报价信息（正向交易使用） */
    private ForeignExchangeQuote foreignExchangeQuote;

    /** 退款汇率报价信息（退款类型交易使用，如 CHARGE_REFUND、TRANSFER_REFUND） */
    private ForeignExchangeQuote refundForeignExchangeQuote;

    /** 资金流动详情 */
    private FundMoveDetail fundMoveDetail;

    /** 发起交易单的操作员信息（仅通过万里汇门户操作时返回） */
    private OperatorInfo operatorInfo;

    /** 商品名称 */
    private String goodsName;

    /** 商品金额 */
    private Amount goodsAmount;

    /** 第三方平台服务费 */
    private Amount platformFeeAmount;

    /** 优惠前服务费金额 */
    private Amount originalFeeAmount;

    /** 服务费优惠金额 */
    private Amount discountFeeAmount;

    /**
     * Getter method for property <tt>transactionId</tt>.
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Setter method for property <tt>transactionId</tt>.
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Getter method for property <tt>transactionTime</tt>.
     */
    public String getTransactionTime() {
        return transactionTime;
    }

    /**
     * Setter method for property <tt>transactionTime</tt>.
     */
    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    /**
     * Getter method for property <tt>transactionType</tt>.
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * Setter method for property <tt>transactionType</tt>.
     */
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * Getter method for property <tt>transactionStatus</tt>.
     */
    public String getTransactionStatus() {
        return transactionStatus;
    }

    /**
     * Setter method for property <tt>transactionStatus</tt>.
     */
    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    /**
     * Getter method for property <tt>balanceType</tt>.
     */
    public String getBalanceType() {
        return balanceType;
    }

    /**
     * Setter method for property <tt>balanceType</tt>.
     */
    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }

    /**
     * Getter method for property <tt>accountBalance</tt>.
     */
    public Amount getAccountBalance() {
        return accountBalance;
    }

    /**
     * Setter method for property <tt>accountBalance</tt>.
     */
    public void setAccountBalance(Amount accountBalance) {
        this.accountBalance = accountBalance;
    }

    /**
     * Getter method for property <tt>feeAmount</tt>.
     */
    public Amount getFeeAmount() {
        return feeAmount;
    }

    /**
     * Setter method for property <tt>feeAmount</tt>.
     */
    public void setFeeAmount(Amount feeAmount) {
        this.feeAmount = feeAmount;
    }

    /**
     * Getter method for property <tt>netAmount</tt>.
     */
    public Amount getNetAmount() {
        return netAmount;
    }

    /**
     * Setter method for property <tt>netAmount</tt>.
     */
    public void setNetAmount(Amount netAmount) {
        this.netAmount = netAmount;
    }

    /**
     * Getter method for property <tt>originalTransactionAmount</tt>.
     */
    public Amount getOriginalTransactionAmount() {
        return originalTransactionAmount;
    }

    /**
     * Setter method for property <tt>originalTransactionAmount</tt>.
     */
    public void setOriginalTransactionAmount(Amount originalTransactionAmount) {
        this.originalTransactionAmount = originalTransactionAmount;
    }

    /**
     * Getter method for property <tt>receiveAmount</tt>.
     */
    public Amount getReceiveAmount() {
        return receiveAmount;
    }

    /**
     * Setter method for property <tt>receiveAmount</tt>.
     */
    public void setReceiveAmount(Amount receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    /**
     * Getter method for property <tt>transactionAmount</tt>.
     */
    public Amount getTransactionAmount() {
        return transactionAmount;
    }

    /**
     * Setter method for property <tt>transactionAmount</tt>.
     */
    public void setTransactionAmount(Amount transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    /**
     * Getter method for property <tt>extTransactionId</tt>.
     */
    public String getExtTransactionId() {
        return extTransactionId;
    }

    /**
     * Setter method for property <tt>extTransactionId</tt>.
     */
    public void setExtTransactionId(String extTransactionId) {
        this.extTransactionId = extTransactionId;
    }

    /**
     * Getter method for property <tt>accountingBizNo</tt>.
     */
    public String getAccountingBizNo() {
        return accountingBizNo;
    }

    /**
     * Setter method for property <tt>accountingBizNo</tt>.
     */
    public void setAccountingBizNo(String accountingBizNo) {
        this.accountingBizNo = accountingBizNo;
    }

    /**
     * Getter method for property <tt>foreignExchangeQuote</tt>.
     */
    public ForeignExchangeQuote getForeignExchangeQuote() {
        return foreignExchangeQuote;
    }

    /**
     * Setter method for property <tt>foreignExchangeQuote</tt>.
     */
    public void setForeignExchangeQuote(ForeignExchangeQuote foreignExchangeQuote) {
        this.foreignExchangeQuote = foreignExchangeQuote;
    }

    /**
     * Getter method for property <tt>refundForeignExchangeQuote</tt>.
     */
    public ForeignExchangeQuote getRefundForeignExchangeQuote() {
        return refundForeignExchangeQuote;
    }

    /**
     * Setter method for property <tt>refundForeignExchangeQuote</tt>.
     */
    public void setRefundForeignExchangeQuote(ForeignExchangeQuote refundForeignExchangeQuote) {
        this.refundForeignExchangeQuote = refundForeignExchangeQuote;
    }

    /**
     * Getter method for property <tt>fundMoveDetail</tt>.
     */
    public FundMoveDetail getFundMoveDetail() {
        return fundMoveDetail;
    }

    /**
     * Setter method for property <tt>fundMoveDetail</tt>.
     */
    public void setFundMoveDetail(FundMoveDetail fundMoveDetail) {
        this.fundMoveDetail = fundMoveDetail;
    }

    /**
     * Getter method for property <tt>operatorInfo</tt>.
     */
    public OperatorInfo getOperatorInfo() {
        return operatorInfo;
    }

    /**
     * Setter method for property <tt>operatorInfo</tt>.
     */
    public void setOperatorInfo(OperatorInfo operatorInfo) {
        this.operatorInfo = operatorInfo;
    }

    /**
     * Getter method for property <tt>goodsName</tt>.
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * Setter method for property <tt>goodsName</tt>.
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * Getter method for property <tt>goodsAmount</tt>.
     */
    public Amount getGoodsAmount() {
        return goodsAmount;
    }

    /**
     * Setter method for property <tt>goodsAmount</tt>.
     */
    public void setGoodsAmount(Amount goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    /**
     * Getter method for property <tt>platformFeeAmount</tt>.
     */
    public Amount getPlatformFeeAmount() {
        return platformFeeAmount;
    }

    /**
     * Setter method for property <tt>platformFeeAmount</tt>.
     */
    public void setPlatformFeeAmount(Amount platformFeeAmount) {
        this.platformFeeAmount = platformFeeAmount;
    }

    /**
     * Getter method for property <tt>originalFeeAmount</tt>.
     */
    public Amount getOriginalFeeAmount() {
        return originalFeeAmount;
    }

    /**
     * Setter method for property <tt>originalFeeAmount</tt>.
     */
    public void setOriginalFeeAmount(Amount originalFeeAmount) {
        this.originalFeeAmount = originalFeeAmount;
    }

    /**
     * Getter method for property <tt>discountFeeAmount</tt>.
     */
    public Amount getDiscountFeeAmount() {
        return discountFeeAmount;
    }

    /**
     * Setter method for property <tt>discountFeeAmount</tt>.
     */
    public void setDiscountFeeAmount(Amount discountFeeAmount) {
        this.discountFeeAmount = discountFeeAmount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    }
}
