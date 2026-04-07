/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 账户余额信息
 *
 * @author Qoder
 * @version AccountBalance.java, v 0.1 2026-03-24
 */
public class AccountBalance {

    /** 账户标识 */
    private String accountId;

    /** 账户别名 */
    private String accountAlias;

    /** 货币代码（ISO-4217） */
    private String currency;

    /** 当前余额 */
    private String balance;

    /** 可用余额 */
    private String availableBalance;

    /** 余额类型：NORMAL_BALANCE、SAME_NAME_TOP_UP_BALANCE、BUDGET_BALANCE */
    private String balanceType;

    /** 预算账户 ID（balanceType=BUDGET_BALANCE 时存在） */
    private String budgetAccountId;

    /**
     * Getter method for property <tt>accountId</tt>.
     *
     * @return property value of accountId
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Setter method for property <tt>accountId</tt>.
     *
     * @param accountId value to be assigned to property accountId
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * Getter method for property <tt>accountAlias</tt>.
     *
     * @return property value of accountAlias
     */
    public String getAccountAlias() {
        return accountAlias;
    }

    /**
     * Setter method for property <tt>accountAlias</tt>.
     *
     * @param accountAlias value to be assigned to property accountAlias
     */
    public void setAccountAlias(String accountAlias) {
        this.accountAlias = accountAlias;
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
     * Getter method for property <tt>availableBalance</tt>.
     *
     * @return property value of availableBalance
     */
    public String getAvailableBalance() {
        return availableBalance;
    }

    /**
     * Setter method for property <tt>availableBalance</tt>.
     *
     * @param availableBalance value to be assigned to property availableBalance
     */
    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
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
     * Getter method for property <tt>budgetAccountId</tt>.
     *
     * @return property value of budgetAccountId
     */
    public String getBudgetAccountId() {
        return budgetAccountId;
    }

    /**
     * Setter method for property <tt>budgetAccountId</tt>.
     *
     * @param budgetAccountId value to be assigned to property budgetAccountId
     */
    public void setBudgetAccountId(String budgetAccountId) {
        this.budgetAccountId = budgetAccountId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
