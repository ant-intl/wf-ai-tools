/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

/**
 * WorldFirst 账单流水记录
 *
 * @author Qoder
 * @version StatementRecord.java, v 0.1 2026-03-24
 */
public class StatementRecord {

    /** 交易 ID */
    private String transactionId;

    /** 交易时间，ISO 8601 格式 */
    private String transactionTime;

    /** 交易类型，如 TRANSFER、COLLECTION 等 */
    private String transactionType;

    /** 货币代码（ISO-4217） */
    private String currency;

    /** 交易金额，正数为收入，负数为支出 */
    private String amount;

    /** 交易后余额 */
    private String balance;

    /** 余额类型：NORMAL_BALANCE 等 */
    private String balanceType;

    /** 对手方名称 */
    private String counterpartyName;

    /** 对手方账号 */
    private String counterpartyAccount;

    /** 交易摘要 */
    private String remark;

    /** 交易状态 */
    private String status;

    /** 手续费金额 */
    private String feeAmount;

    /** 实际到账金额 */
    private String actualAmount;

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
     * Getter method for property <tt>currency</tt>.
     *
     * @return property value of currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Setter method for property <tt>currency</tt>.
     *
     * @param currency value to be assigned to property currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Getter method for property <tt>amount</tt>.
     *
     * @return property value of amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Setter method for property <tt>amount</tt>.
     *
     * @param amount value to be assigned to property amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * Getter method for property <tt>balance</tt>.
     *
     * @return property value of balance
     */
    public String getBalance() {
        return balance;
    }

    /**
     * Setter method for property <tt>balance</tt>.
     *
     * @param balance value to be assigned to property balance
     */
    public void setBalance(String balance) {
        this.balance = balance;
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
     * Getter method for property <tt>counterpartyName</tt>.
     *
     * @return property value of counterpartyName
     */
    public String getCounterpartyName() {
        return counterpartyName;
    }

    /**
     * Setter method for property <tt>counterpartyName</tt>.
     *
     * @param counterpartyName value to be assigned to property counterpartyName
     */
    public void setCounterpartyName(String counterpartyName) {
        this.counterpartyName = counterpartyName;
    }

    /**
     * Getter method for property <tt>counterpartyAccount</tt>.
     *
     * @return property value of counterpartyAccount
     */
    public String getCounterpartyAccount() {
        return counterpartyAccount;
    }

    /**
     * Setter method for property <tt>counterpartyAccount</tt>.
     *
     * @param counterpartyAccount value to be assigned to property counterpartyAccount
     */
    public void setCounterpartyAccount(String counterpartyAccount) {
        this.counterpartyAccount = counterpartyAccount;
    }

    /**
     * Getter method for property <tt>remark</tt>.
     *
     * @return property value of remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Setter method for property <tt>remark</tt>.
     *
     * @param remark value to be assigned to property remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
     * Getter method for property <tt>feeAmount</tt>.
     *
     * @return property value of feeAmount
     */
    public String getFeeAmount() {
        return feeAmount;
    }

    /**
     * Setter method for property <tt>feeAmount</tt>.
     *
     * @param feeAmount value to be assigned to property feeAmount
     */
    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    /**
     * Getter method for property <tt>actualAmount</tt>.
     *
     * @return property value of actualAmount
     */
    public String getActualAmount() {
        return actualAmount;
    }

    /**
     * Setter method for property <tt>actualAmount</tt>.
     *
     * @param actualAmount value to be assigned to property actualAmount
     */
    public void setActualAmount(String actualAmount) {
        this.actualAmount = actualAmount;
    }

    @Override
    public String toString() {
        return "StatementRecord{transactionId='" + transactionId + "', transactionTime='" + transactionTime
            + "', transactionType='" + transactionType + "', currency='" + currency
            + "', amount='" + amount + "', status='" + status + "'}";
    }
}
