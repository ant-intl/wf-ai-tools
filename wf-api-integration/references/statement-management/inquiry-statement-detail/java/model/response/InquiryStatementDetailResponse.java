package {basePackage}.wf.model.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import {basePackage}.wf.model.domain.Amount;
import {basePackage}.wf.model.domain.ForeignExchangeQuote;
import {basePackage}.wf.model.domain.FundMoveDetail;
import {basePackage}.wf.model.domain.OperatorInfo;
import {basePackage}.wf.model.domain.RelatedStatement;

import java.util.List;

/**
 * WorldFirst inquiryStatementDetail 响应对象
 *
 */
public class InquiryStatementDetailResponse {

    /** 接口调用结果 */
    private Result result;

    /** 响应唯一 ID，最大 32 位 */
    private String responseId;

    /**
     * 交易 ID，TRANSFER/TRANSFER_REFUND/WITHDRAWAL/WITHDRAWAL_REFUND/
     * CONVERSION/CONVERSION_DEAL/CHARGE/CHARGE_REFUND/DEDUCTION/FUND_COLLECTION 类型必返回
     */
    private String transactionId;

    /** 调用方传入的外部交易 ID，最大 256 位 */
    private String extTransactionId;

    /** 交易状态：INIT / PROCESSING / PENDING / SUCCESS / FAIL / REFUNDED */
    private String transactionStatus;

    /** 余额变动时间，ISO 8601 格式，如 2019-11-27T12:01:01+08:00 */
    private String transactionTime;

    /** 交易类型，如 TRANSFER、COLLECTION 等 */
    private String transactionType;

    /** 本次余额变动金额 */
    private Amount transactionAmount;

    /** 原始提交的交易金额 */
    private Amount originalTransactionAmount;

    /**
     * 手续费金额，TRANSFER/TRANSFER_REFUND/WITHDRAWAL/WITHDRAWAL_REFUND/
     * CONVERSION/CONVERSION_DEAL/CHARGE/CHARGE_REFUND/DEDUCTION/FUND_COLLECTION 类型条件必返回
     */
    private Amount feeAmount;

    /** 手续费类型：OBO_SERVICE_FEE / REMIT_SERVICE_FEE */
    private String feeItemType;

    /** 扣除手续费后的净额，TRANSFER/CHARGE/PAYMENT/CASH_BACK 类型必返回 */
    private Amount netAmount;

    /** 换汇后实际到账金额，TRANSFER/CHARGE/PAYMENT/CASH_BACK 类型必返回 */
    private Amount receiveAmount;

    /** 交易后账户实时余额 */
    private Amount accountBalance;

    /** 资金流动详情（付款方/收款方信息） */
    private FundMoveDetail fundMoveDetail;

    /** 汇率报价信息（TRANSFER/WITHDRAWAL/CONVERSION/CONVERSION_DEAL/CHARGE 类型可选） */
    private ForeignExchangeQuote foreignExchangeQuote;

    /** 退款汇率报价（TRANSFER_REFUND/WITHDRAWAL_REFUND/CHARGE_REFUND 类型可选） */
    private ForeignExchangeQuote refundForeignExchangeQuote;

    /**
     * 余额类型：NORMAL_BALANCE（默认）/ SAME_NAME_TOP_UP_BALANCE / BUDGET_BALANCE
     */
    private String balanceType;

    /** 账单流水唯一 ID */
    private String accountingBizNo;

    /** 失败原因，transactionStatus=FAIL 时返回 */
    private Result failReason;

    /** 关联交易列表 */
    private List<RelatedStatement> combinedTransactionList;

    /** 操作员信息，仅通过 WF 门户操作时返回 */
    private OperatorInfo operatorInfo;

    /** 商品名称 */
    private String goodsName;

    /** 商品金额 */
    private Amount goodsAmount;

    /** 优惠前手续费金额 */
    private Amount originalFeeAmount;

    /** 手续费优惠金额 */
    private Amount discountFeeAmount;

    /**
     * Getter method for property <tt>result</tt>.
     *
     * @return property value of result
     */
    public Result getResult() {
        return result;
    }

    /**
     * Setter method for property <tt>result</tt>.
     *
     * @param result value to be assigned to property result
     */
    public void setResult(Result result) {
        this.result = result;
    }

    /**
     * Getter method for property <tt>responseId</tt>.
     *
     * @return property value of responseId
     */
    public String getResponseId() {
        return responseId;
    }

    /**
     * Setter method for property <tt>responseId</tt>.
     *
     * @param responseId value to be assigned to property responseId
     */
    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    /**
     * Getter method for property <tt>transactionId</tt>.
     *
     * @return property value of transactionId
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Setter method for property <tt>transactionId</tt>.
     *
     * @param transactionId value to be assigned to property transactionId
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Getter method for property <tt>extTransactionId</tt>.
     *
     * @return property value of extTransactionId
     */
    public String getExtTransactionId() {
        return extTransactionId;
    }

    /**
     * Setter method for property <tt>extTransactionId</tt>.
     *
     * @param extTransactionId value to be assigned to property extTransactionId
     */
    public void setExtTransactionId(String extTransactionId) {
        this.extTransactionId = extTransactionId;
    }

    /**
     * Getter method for property <tt>transactionStatus</tt>.
     *
     * @return property value of transactionStatus
     */
    public String getTransactionStatus() {
        return transactionStatus;
    }

    /**
     * Setter method for property <tt>transactionStatus</tt>.
     *
     * @param transactionStatus value to be assigned to property transactionStatus
     */
    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    /**
     * Getter method for property <tt>transactionTime</tt>.
     *
     * @return property value of transactionTime
     */
    public String getTransactionTime() {
        return transactionTime;
    }

    /**
     * Setter method for property <tt>transactionTime</tt>.
     *
     * @param transactionTime value to be assigned to property transactionTime
     */
    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    /**
     * Getter method for property <tt>transactionType</tt>.
     *
     * @return property value of transactionType
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * Setter method for property <tt>transactionType</tt>.
     *
     * @param transactionType value to be assigned to property transactionType
     */
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * Getter method for property <tt>transactionAmount</tt>.
     *
     * @return property value of transactionAmount
     */
    public Amount getTransactionAmount() {
        return transactionAmount;
    }

    /**
     * Setter method for property <tt>transactionAmount</tt>.
     *
     * @param transactionAmount value to be assigned to property transactionAmount
     */
    public void setTransactionAmount(Amount transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    /**
     * Getter method for property <tt>originalTransactionAmount</tt>.
     *
     * @return property value of originalTransactionAmount
     */
    public Amount getOriginalTransactionAmount() {
        return originalTransactionAmount;
    }

    /**
     * Setter method for property <tt>originalTransactionAmount</tt>.
     *
     * @param originalTransactionAmount value to be assigned to property originalTransactionAmount
     */
    public void setOriginalTransactionAmount(Amount originalTransactionAmount) {
        this.originalTransactionAmount = originalTransactionAmount;
    }

    /**
     * Getter method for property <tt>feeAmount</tt>.
     *
     * @return property value of feeAmount
     */
    public Amount getFeeAmount() {
        return feeAmount;
    }

    /**
     * Setter method for property <tt>feeAmount</tt>.
     *
     * @param feeAmount value to be assigned to property feeAmount
     */
    public void setFeeAmount(Amount feeAmount) {
        this.feeAmount = feeAmount;
    }

    /**
     * Getter method for property <tt>feeItemType</tt>.
     *
     * @return property value of feeItemType
     */
    public String getFeeItemType() {
        return feeItemType;
    }

    /**
     * Setter method for property <tt>feeItemType</tt>.
     *
     * @param feeItemType value to be assigned to property feeItemType
     */
    public void setFeeItemType(String feeItemType) {
        this.feeItemType = feeItemType;
    }

    /**
     * Getter method for property <tt>netAmount</tt>.
     *
     * @return property value of netAmount
     */
    public Amount getNetAmount() {
        return netAmount;
    }

    /**
     * Setter method for property <tt>netAmount</tt>.
     *
     * @param netAmount value to be assigned to property netAmount
     */
    public void setNetAmount(Amount netAmount) {
        this.netAmount = netAmount;
    }

    /**
     * Getter method for property <tt>receiveAmount</tt>.
     *
     * @return property value of receiveAmount
     */
    public Amount getReceiveAmount() {
        return receiveAmount;
    }

    /**
     * Setter method for property <tt>receiveAmount</tt>.
     *
     * @param receiveAmount value to be assigned to property receiveAmount
     */
    public void setReceiveAmount(Amount receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    /**
     * Getter method for property <tt>accountBalance</tt>.
     *
     * @return property value of accountBalance
     */
    public Amount getAccountBalance() {
        return accountBalance;
    }

    /**
     * Setter method for property <tt>accountBalance</tt>.
     *
     * @param accountBalance value to be assigned to property accountBalance
     */
    public void setAccountBalance(Amount accountBalance) {
        this.accountBalance = accountBalance;
    }

    /**
     * Getter method for property <tt>fundMoveDetail</tt>.
     *
     * @return property value of fundMoveDetail
     */
    public FundMoveDetail getFundMoveDetail() {
        return fundMoveDetail;
    }

    /**
     * Setter method for property <tt>fundMoveDetail</tt>.
     *
     * @param fundMoveDetail value to be assigned to property fundMoveDetail
     */
    public void setFundMoveDetail(FundMoveDetail fundMoveDetail) {
        this.fundMoveDetail = fundMoveDetail;
    }

    /**
     * Getter method for property <tt>foreignExchangeQuote</tt>.
     *
     * @return property value of foreignExchangeQuote
     */
    public ForeignExchangeQuote getForeignExchangeQuote() {
        return foreignExchangeQuote;
    }

    /**
     * Setter method for property <tt>foreignExchangeQuote</tt>.
     *
     * @param foreignExchangeQuote value to be assigned to property foreignExchangeQuote
     */
    public void setForeignExchangeQuote(ForeignExchangeQuote foreignExchangeQuote) {
        this.foreignExchangeQuote = foreignExchangeQuote;
    }

    /**
     * Getter method for property <tt>refundForeignExchangeQuote</tt>.
     *
     * @return property value of refundForeignExchangeQuote
     */
    public ForeignExchangeQuote getRefundForeignExchangeQuote() {
        return refundForeignExchangeQuote;
    }

    /**
     * Setter method for property <tt>refundForeignExchangeQuote</tt>.
     *
     * @param refundForeignExchangeQuote value to be assigned to property refundForeignExchangeQuote
     */
    public void setRefundForeignExchangeQuote(ForeignExchangeQuote refundForeignExchangeQuote) {
        this.refundForeignExchangeQuote = refundForeignExchangeQuote;
    }

    /**
     * Getter method for property <tt>balanceType</tt>.
     *
     * @return property value of balanceType
     */
    public String getBalanceType() {
        return balanceType;
    }

    /**
     * Setter method for property <tt>balanceType</tt>.
     *
     * @param balanceType value to be assigned to property balanceType
     */
    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }

    /**
     * Getter method for property <tt>accountingBizNo</tt>.
     *
     * @return property value of accountingBizNo
     */
    public String getAccountingBizNo() {
        return accountingBizNo;
    }

    /**
     * Setter method for property <tt>accountingBizNo</tt>.
     *
     * @param accountingBizNo value to be assigned to property accountingBizNo
     */
    public void setAccountingBizNo(String accountingBizNo) {
        this.accountingBizNo = accountingBizNo;
    }

    /**
     * Getter method for property <tt>failReason</tt>.
     *
     * @return property value of failReason
     */
    public Result getFailReason() {
        return failReason;
    }

    /**
     * Setter method for property <tt>failReason</tt>.
     *
     * @param failReason value to be assigned to property failReason
     */
    public void setFailReason(Result failReason) {
        this.failReason = failReason;
    }

    /**
     * Getter method for property <tt>combinedTransactionList</tt>.
     *
     * @return property value of combinedTransactionList
     */
    public List<RelatedStatement> getCombinedTransactionList() {
        return combinedTransactionList;
    }

    /**
     * Setter method for property <tt>combinedTransactionList</tt>.
     *
     * @param combinedTransactionList value to be assigned to property combinedTransactionList
     */
    public void setCombinedTransactionList(List<RelatedStatement> combinedTransactionList) {
        this.combinedTransactionList = combinedTransactionList;
    }

    /**
     * Getter method for property <tt>operatorInfo</tt>.
     *
     * @return property value of operatorInfo
     */
    public OperatorInfo getOperatorInfo() {
        return operatorInfo;
    }

    /**
     * Setter method for property <tt>operatorInfo</tt>.
     *
     * @param operatorInfo value to be assigned to property operatorInfo
     */
    public void setOperatorInfo(OperatorInfo operatorInfo) {
        this.operatorInfo = operatorInfo;
    }

    /**
     * Getter method for property <tt>goodsName</tt>.
     *
     * @return property value of goodsName
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * Setter method for property <tt>goodsName</tt>.
     *
     * @param goodsName value to be assigned to property goodsName
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * Getter method for property <tt>goodsAmount</tt>.
     *
     * @return property value of goodsAmount
     */
    public Amount getGoodsAmount() {
        return goodsAmount;
    }

    /**
     * Setter method for property <tt>goodsAmount</tt>.
     *
     * @param goodsAmount value to be assigned to property goodsAmount
     */
    public void setGoodsAmount(Amount goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    /**
     * Getter method for property <tt>originalFeeAmount</tt>.
     *
     * @return property value of originalFeeAmount
     */
    public Amount getOriginalFeeAmount() {
        return originalFeeAmount;
    }

    /**
     * Setter method for property <tt>originalFeeAmount</tt>.
     *
     * @param originalFeeAmount value to be assigned to property originalFeeAmount
     */
    public void setOriginalFeeAmount(Amount originalFeeAmount) {
        this.originalFeeAmount = originalFeeAmount;
    }

    /**
     * Getter method for property <tt>discountFeeAmount</tt>.
     *
     * @return property value of discountFeeAmount
     */
    public Amount getDiscountFeeAmount() {
        return discountFeeAmount;
    }

    /**
     * Setter method for property <tt>discountFeeAmount</tt>.
     *
     * @param discountFeeAmount value to be assigned to property discountFeeAmount
     */
    public void setDiscountFeeAmount(Amount discountFeeAmount) {
        this.discountFeeAmount = discountFeeAmount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
