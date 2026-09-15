package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 对账单记录对象。
 *
 * <p>描述一笔交易的完整对账信息，包括交易金额、手续费、余额、汇率、资金流详情等。
 * 用于 list_statements 响应的 items 列表。
 */
public class StatementRecord {

    /** 对账单唯一标识 */
    private String id;

    /** 交易类型（TransactionType 枚举） */
    private String transactionType;

    /** 对账单状态（StatementStatus 枚举） */
    private String status;

    /** 交易金额，正数为入账，负数为出账 */
    private Amount transactionAmount;

    /** 手续费扣除前的原始交易金额 */
    private Amount originalTransactionAmount;

    /** WorldFirst 收取的手续费 */
    private Amount feeAmount;

    /** 手续费扣除后的净金额 */
    private Amount netAmount;

    /** 交易后的账户余额 */
    private Amount balanceAmount;

    /** 余额类型（BalanceType 枚举） */
    private String balanceType;

    /**
     * 费用项类型，仅当 transactionType 为 CHARGE 时返回。
     * <p>可选值：OBO_SERVICE_FEE、REMIT_SERVICE_FEE
     */
    private String feeItemType;

    /** 汇率信息，仅涉及币种转换时返回 */
    private ExchangeRate exchangeRate;

    /** 资金流详情 */
    private FundFlowDetail fundFlowDetail;

    /** 交易时间（ISO 8601 格式） */
    private String transactedAt;

    public StatementRecord() {
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
     * Getter method for property <tt>balanceAmount</tt>.
     *
     * @return property value of balanceAmount
     */
    public Amount getBalanceAmount() {
        return balanceAmount;
    }

    /**
     * Setter method for property <tt>balanceAmount</tt>.
     *
     * @param balanceAmount value to be assigned to property balanceAmount
     */
    public void setBalanceAmount(Amount balanceAmount) {
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
    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    /**
     * Setter method for property <tt>exchangeRate</tt>.
     *
     * @param exchangeRate value to be assigned to property exchangeRate
     */
    public void setExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    /**
     * Getter method for property <tt>fundFlowDetail</tt>.
     *
     * @return property value of fundFlowDetail
     */
    public FundFlowDetail getFundFlowDetail() {
        return fundFlowDetail;
    }

    /**
     * Setter method for property <tt>fundFlowDetail</tt>.
     *
     * @param fundFlowDetail value to be assigned to property fundFlowDetail
     */
    public void setFundFlowDetail(FundFlowDetail fundFlowDetail) {
        this.fundFlowDetail = fundFlowDetail;
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
