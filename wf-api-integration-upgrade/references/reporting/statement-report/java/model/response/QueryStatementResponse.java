package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.CombinedTransaction;
import {basePackage}.wf.model.domain.ExchangeRate;
import {basePackage}.wf.model.domain.FundFlowDetail;
import {basePackage}.wf.model.domain.GoodsInfo;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_a_statement 响应对象。
 *
 * <p>包含接口调用结果及完整的对账单记录详情。
 */
public class QueryStatementResponse {

    /** 接口调用结果 */
    private Result result;

    /** 对账单唯一标识 */
    private String id;

    /** WorldFirst 定义的交易唯一 ID */
    private String transactionId;

    /** 集成方定义的交易唯一 ID */
    private String externalTransactionId;

    /** 交易类型 */
    private String transactionType;

    /** 对账单状态 */
    private String status;

    /** 失败原因码，仅当 status 为 FAIL 时返回 */
    private String failureCode;

    /** 失败原因描述，仅当 status 为 FAIL 时返回 */
    private String failureMessage;

    /** 交易金额，正数为入账，负数为出账 */
    private Amount transactionAmount;

    /** 手续费扣除前的原始交易金额 */
    private Amount originalTransactionAmount;

    /** WorldFirst 收取的手续费 */
    private Amount feeAmount;

    /** 手续费扣除后的净金额 */
    private Amount netAmount;

    /** 服务费折扣金额 */
    private Amount discountFeeAmount;

    /** 折扣前的原始服务费 */
    private Amount originalFeeAmount;

    /** 第三方平台服务费 */
    private Amount platformFeeAmount;

    /** 币种转换后的到账金额 */
    private Amount receiveAmount;

    /** 交易后的账户余额 */
    private Amount balanceAmount;

    /** 余额类型 */
    private String balanceType;

    /** 费用项类型，仅当 transactionType 为 CHARGE 时返回 */
    private String feeItemType;

    /** 汇率信息，仅涉及币种转换时返回 */
    private ExchangeRate exchangeRate;

    /** 资金流详情 */
    private FundFlowDetail fundFlowDetail;

    /** 商品信息，仅涉及海关申报的交易返回 */
    private GoodsInfo goodsInfo;

    /** 关联交易列表 */
    private List<CombinedTransaction> combinedTransactions;

    /** 渠道参考信息，传递给收款方 */
    private String reference;

    /** 用户提供的交易描述 */
    private String description;

    /** 交易时间 */
    private String transactedAt;

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
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id value to be assigned to property id
     */
    public void setId(String id) {
        this.id = id;
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
     * Getter method for property <tt>externalTransactionId</tt>.
     *
     * @return property value of externalTransactionId
     */
    public String getExternalTransactionId() {
        return externalTransactionId;
    }

    /**
     * Setter method for property <tt>externalTransactionId</tt>.
     *
     * @param externalTransactionId value to be assigned to property externalTransactionId
     */
    public void setExternalTransactionId(String externalTransactionId) {
        this.externalTransactionId = externalTransactionId;
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
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     *
     * @param status value to be assigned to property status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Getter method for property <tt>failureCode</tt>.
     *
     * @return property value of failureCode
     */
    public String getFailureCode() {
        return failureCode;
    }

    /**
     * Setter method for property <tt>failureCode</tt>.
     *
     * @param failureCode value to be assigned to property failureCode
     */
    public void setFailureCode(String failureCode) {
        this.failureCode = failureCode;
    }

    /**
     * Getter method for property <tt>failureMessage</tt>.
     *
     * @return property value of failureMessage
     */
    public String getFailureMessage() {
        return failureMessage;
    }

    /**
     * Setter method for property <tt>failureMessage</tt>.
     *
     * @param failureMessage value to be assigned to property failureMessage
     */
    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    /**
     * Getter method for property <tt>transactionAmount</tt>.
     *
     * @return property value of transactionAmount
     */
    public {basePackage}.wf.model.domain.Amount getTransactionAmount() {
        return transactionAmount;
    }

    /**
     * Setter method for property <tt>transactionAmount</tt>.
     *
     * @param transactionAmount value to be assigned to property transactionAmount
     */
    public void setTransactionAmount({basePackage}.wf.model.domain.Amount transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    /**
     * Getter method for property <tt>originalTransactionAmount</tt>.
     *
     * @return property value of originalTransactionAmount
     */
    public {basePackage}.wf.model.domain.Amount getOriginalTransactionAmount() {
        return originalTransactionAmount;
    }

    /**
     * Setter method for property <tt>originalTransactionAmount</tt>.
     *
     * @param originalTransactionAmount value to be assigned to property originalTransactionAmount
     */
    public void setOriginalTransactionAmount({basePackage}.wf.model.domain.Amount originalTransactionAmount) {
        this.originalTransactionAmount = originalTransactionAmount;
    }

    /**
     * Getter method for property <tt>feeAmount</tt>.
     *
     * @return property value of feeAmount
     */
    public {basePackage}.wf.model.domain.Amount getFeeAmount() {
        return feeAmount;
    }

    /**
     * Setter method for property <tt>feeAmount</tt>.
     *
     * @param feeAmount value to be assigned to property feeAmount
     */
    public void setFeeAmount({basePackage}.wf.model.domain.Amount feeAmount) {
        this.feeAmount = feeAmount;
    }

    /**
     * Getter method for property <tt>netAmount</tt>.
     *
     * @return property value of netAmount
     */
    public {basePackage}.wf.model.domain.Amount getNetAmount() {
        return netAmount;
    }

    /**
     * Setter method for property <tt>netAmount</tt>.
     *
     * @param netAmount value to be assigned to property netAmount
     */
    public void setNetAmount({basePackage}.wf.model.domain.Amount netAmount) {
        this.netAmount = netAmount;
    }

    /**
     * Getter method for property <tt>discountFeeAmount</tt>.
     *
     * @return property value of discountFeeAmount
     */
    public {basePackage}.wf.model.domain.Amount getDiscountFeeAmount() {
        return discountFeeAmount;
    }

    /**
     * Setter method for property <tt>discountFeeAmount</tt>.
     *
     * @param discountFeeAmount value to be assigned to property discountFeeAmount
     */
    public void setDiscountFeeAmount({basePackage}.wf.model.domain.Amount discountFeeAmount) {
        this.discountFeeAmount = discountFeeAmount;
    }

    /**
     * Getter method for property <tt>originalFeeAmount</tt>.
     *
     * @return property value of originalFeeAmount
     */
    public {basePackage}.wf.model.domain.Amount getOriginalFeeAmount() {
        return originalFeeAmount;
    }

    /**
     * Setter method for property <tt>originalFeeAmount</tt>.
     *
     * @param originalFeeAmount value to be assigned to property originalFeeAmount
     */
    public void setOriginalFeeAmount({basePackage}.wf.model.domain.Amount originalFeeAmount) {
        this.originalFeeAmount = originalFeeAmount;
    }

    /**
     * Getter method for property <tt>platformFeeAmount</tt>.
     *
     * @return property value of platformFeeAmount
     */
    public {basePackage}.wf.model.domain.Amount getPlatformFeeAmount() {
        return platformFeeAmount;
    }

    /**
     * Setter method for property <tt>platformFeeAmount</tt>.
     *
     * @param platformFeeAmount value to be assigned to property platformFeeAmount
     */
    public void setPlatformFeeAmount({basePackage}.wf.model.domain.Amount platformFeeAmount) {
        this.platformFeeAmount = platformFeeAmount;
    }

    /**
     * Getter method for property <tt>receiveAmount</tt>.
     *
     * @return property value of receiveAmount
     */
    public {basePackage}.wf.model.domain.Amount getReceiveAmount() {
        return receiveAmount;
    }

    /**
     * Setter method for property <tt>receiveAmount</tt>.
     *
     * @param receiveAmount value to be assigned to property receiveAmount
     */
    public void setReceiveAmount({basePackage}.wf.model.domain.Amount receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    /**
     * Getter method for property <tt>balanceAmount</tt>.
     *
     * @return property value of balanceAmount
     */
    public {basePackage}.wf.model.domain.Amount getBalanceAmount() {
        return balanceAmount;
    }

    /**
     * Setter method for property <tt>balanceAmount</tt>.
     *
     * @param balanceAmount value to be assigned to property balanceAmount
     */
    public void setBalanceAmount({basePackage}.wf.model.domain.Amount balanceAmount) {
        this.balanceAmount = balanceAmount;
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
     * Getter method for property <tt>exchangeRate</tt>.
     *
     * @return property value of exchangeRate
     */
    public {basePackage}.wf.model.domain.ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    /**
     * Setter method for property <tt>exchangeRate</tt>.
     *
     * @param exchangeRate value to be assigned to property exchangeRate
     */
    public void setExchangeRate({basePackage}.wf.model.domain.ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    /**
     * Getter method for property <tt>fundFlowDetail</tt>.
     *
     * @return property value of fundFlowDetail
     */
    public {basePackage}.wf.model.domain.FundFlowDetail getFundFlowDetail() {
        return fundFlowDetail;
    }

    /**
     * Setter method for property <tt>fundFlowDetail</tt>.
     *
     * @param fundFlowDetail value to be assigned to property fundFlowDetail
     */
    public void setFundFlowDetail({basePackage}.wf.model.domain.FundFlowDetail fundFlowDetail) {
        this.fundFlowDetail = fundFlowDetail;
    }

    /**
     * Getter method for property <tt>goodsInfo</tt>.
     *
     * @return property value of goodsInfo
     */
    public {basePackage}.wf.model.domain.GoodsInfo getGoodsInfo() {
        return goodsInfo;
    }

    /**
     * Setter method for property <tt>goodsInfo</tt>.
     *
     * @param goodsInfo value to be assigned to property goodsInfo
     */
    public void setGoodsInfo({basePackage}.wf.model.domain.GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    /**
     * Getter method for property <tt>combinedTransactions</tt>.
     *
     * @return property value of combinedTransactions
     */
    public List<{basePackage}.wf.model.domain.CombinedTransaction> getCombinedTransactions() {
        return combinedTransactions;
    }

    /**
     * Setter method for property <tt>combinedTransactions</tt>.
     *
     * @param combinedTransactions value to be assigned to property combinedTransactions
     */
    public void setCombinedTransactions(List<{basePackage}.wf.model.domain.CombinedTransaction> combinedTransactions) {
        this.combinedTransactions = combinedTransactions;
    }

    /**
     * Getter method for property <tt>reference</tt>.
     *
     * @return property value of reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * Setter method for property <tt>reference</tt>.
     *
     * @param reference value to be assigned to property reference
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * Getter method for property <tt>description</tt>.
     *
     * @return property value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter method for property <tt>description</tt>.
     *
     * @param description value to be assigned to property description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter method for property <tt>transactedAt</tt>.
     *
     * @return property value of transactedAt
     */
    public String getTransactedAt() {
        return transactedAt;
    }

    /**
     * Setter method for property <tt>transactedAt</tt>.
     *
     * @param transactedAt value to be assigned to property transactedAt
     */
    public void setTransactedAt(String transactedAt) {
        this.transactedAt = transactedAt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
