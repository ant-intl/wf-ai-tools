/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 账户信息
 *
 * @author Qoder
 * @version AccountInfo.java, v 0.1 2026-04-16
 */
public class AccountInfo {

    /** 账户号码（VIRTUAL_ACCOUNT/RECEIVE_ACCOUNT 时返回） */
    private String accountNo;

    /** 账户币种列表（ISO-4217） */
    private List<String> currencyList;

    /** 账号类型：RECEIVE_ACCOUNT、VIRTUAL_ACCOUNT、ALIPAY_WALLET、ALIPAY_SHADOW_WALLET、ALIPAY_ORIGIN_WALLET */
    private String accountType;

    /** 账户状态：ACTIVE（已激活）、ABNORMAL（异常） */
    private String accountStatus;

    /** 银行账户信息（VIRTUAL_ACCOUNT 时返回） */
    private List<BankAccount> bankAccountList;

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
     * Getter method for property <tt>currencyList</tt>.
     *
     * @return property value of currencyList
     */
    public List<String> getCurrencyList() {
        return currencyList;
    }

    /**
     * Setter method for property <tt>currencyList</tt>.
     *
     * @param currencyList value to be assigned to property currencyList
     */
    public void setCurrencyList(List<String> currencyList) {
        this.currencyList = currencyList;
    }

    /**
     * Getter method for property <tt>accountType</tt>.
     *
     * @return property value of accountType
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * Setter method for property <tt>accountType</tt>.
     *
     * @param accountType value to be assigned to property accountType
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    /**
     * Getter method for property <tt>accountStatus</tt>.
     *
     * @return property value of accountStatus
     */
    public String getAccountStatus() {
        return accountStatus;
    }

    /**
     * Setter method for property <tt>accountStatus</tt>.
     *
     * @param accountStatus value to be assigned to property accountStatus
     */
    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    /**
     * Getter method for property <tt>bankAccountList</tt>.
     *
     * @return property value of bankAccountList
     */
    public List<BankAccount> getBankAccountList() {
        return bankAccountList;
    }

    /**
     * Setter method for property <tt>bankAccountList</tt>.
     *
     * @param bankAccountList value to be assigned to property bankAccountList
     */
    public void setBankAccountList(List<BankAccount> bankAccountList) {
        this.bankAccountList = bankAccountList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
