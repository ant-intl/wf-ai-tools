/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 余额变动记录（notifyBalanceChange 回调中使用）。
 *
 * <p>每条记录代表一次账户余额变动，包含动账金额、实时余额、交易类型等信息。
 * {@code transactionAmount} 的正负代表资金流向：正数为入账，负数为出账。
 *
 * @author Qoder
 * @version BalanceChangeLog.java, v 0.1 2026-04-21
 */
public class BalanceChangeLog {

    /** 账户余额变动的时间，ISO 8601 格式 */
    private String balanceChangeTime;

    /** 万里汇定义的唯一账户 ID，最大 32 字符 */
    private String accountNo;

    /**
     * 余额类型。
     * <ul>
     *   <li>NORMAL_BALANCE: 普通余额类型（即电商余额类型），默认值</li>
     *   <li>SAME_NAME_TOP_UP_BALANCE: 同名充值余额类型</li>
     *   <li>BUDGET_BALANCE: 预算账户余额类型</li>
     * </ul>
     */
    private String balanceType;

    /** 万里汇账单动账流水单号 */
    private String accountingBizNo;

    /** 动账金额（正数为入账，负数为出账） */
    private Amount transactionAmount;

    /** 转账后的实时账户余额 */
    private Amount accountBalance;

    /**
     * 交易类型。
     * <ul>
     *   <li>TRANSFER: 转账</li>
     *   <li>TRANSFER_REFUND: 转账退款</li>
     *   <li>WITHDRAWAL: 提款</li>
     *   <li>WITHDRAWAL_REFUND: 提款退款</li>
     *   <li>COLLECTION: 收款</li>
     *   <li>COLLECTION_REFUND: 收款退款</li>
     *   <li>CONVERSION: 换汇</li>
     *   <li>CONVERSION_DEAL: 换汇交割</li>
     *   <li>CHARGE: 扣费</li>
     *   <li>CHARGE_REFUND: 扣费退款</li>
     *   <li>DEDUCTION: 扣款</li>
     *   <li>FUND_COLLECTION: 资金归集</li>
     * </ul>
     */
    private String transactionType;

    /**
     * 万里汇定义的交易唯一 ID，最大 64 字符。
     * 当 transactionType 为 TRANSFER/TRANSFER_REFUND/WITHDRAWAL/WITHDRAWAL_REFUND 时必填。
     * 一笔交易中包含多条交易数据时，所有交易数据共用同一 ID。
     */
    private String transactionId;

    /** 集成商定义的交易唯一 ID，最大 256 字符 */
    private String extTransactionId;

    /** 收款人姓名（脱敏），最大 128 字符 */
    private String beneficiaryName;

    /** 收款人万里汇账号（脱敏），最大 64 字符 */
    private String beneficiaryAccountNo;

    /** 转账附言，最大 512 字符 */
    private String remarks;

    /**
     * Getter method for property <tt>balanceChangeTime</tt>.
     *
     * @return property value of balanceChangeTime
     */
    public String getBalanceChangeTime() {
        return balanceChangeTime;
    }

    /**
     * Setter method for property <tt>balanceChangeTime</tt>.
     *
     * @param balanceChangeTime value to be assigned to property balanceChangeTime
     */
    public void setBalanceChangeTime(String balanceChangeTime) {
        this.balanceChangeTime = balanceChangeTime;
    }

    /**
     * Getter method for property <tt>accountNo</tt>.
     *
     * @return property value of accountNo
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * Setter method for property <tt>accountNo</tt>.
     *
     * @param accountNo value to be assigned to property accountNo
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
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
     * Getter method for property <tt>beneficiaryName</tt>.
     *
     * @return property value of beneficiaryName
     */
    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    /**
     * Setter method for property <tt>beneficiaryName</tt>.
     *
     * @param beneficiaryName value to be assigned to property beneficiaryName
     */
    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    /**
     * Getter method for property <tt>beneficiaryAccountNo</tt>.
     *
     * @return property value of beneficiaryAccountNo
     */
    public String getBeneficiaryAccountNo() {
        return beneficiaryAccountNo;
    }

    /**
     * Setter method for property <tt>beneficiaryAccountNo</tt>.
     *
     * @param beneficiaryAccountNo value to be assigned to property beneficiaryAccountNo
     */
    public void setBeneficiaryAccountNo(String beneficiaryAccountNo) {
        this.beneficiaryAccountNo = beneficiaryAccountNo;
    }

    /**
     * Getter method for property <tt>remarks</tt>.
     *
     * @return property value of remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Setter method for property <tt>remarks</tt>.
     *
     * @param remarks value to be assigned to property remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
