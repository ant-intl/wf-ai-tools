/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.AccountInfo;
import {basePackage}.wf.model.domain.AlipayCustomer;
import {basePackage}.wf.model.domain.Customer;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst inquiryAccount 响应对象
 *
 * @author Qoder
 * @version InquiryAccountResponse.java, v 0.1 2026-04-16
 */
public class InquiryAccountResponse {

    /** 接口调用结果 */
    private Result result;

    /** 响应唯一 ID，最大 32 位 */
    private String responseId;

    /** 万里汇账户唯一标识（VIRTUAL_ACCOUNT/RECEIVE_ACCOUNT/ALIPAY_SHADOW_WALLET 时返回） */
    private String accountId;

    /** 账号信息列表（VIRTUAL_ACCOUNT/RECEIVE_ACCOUNT/ALIPAY_SHADOW_WALLET 时返回） */
    private List<AccountInfo> accountInfos;

    /** 客户信息 */
    private Customer customer;

    /** 企业支付宝用户信息（ALIPAY_ORIGIN_WALLET 时返回） */
    private AlipayCustomer alipayCustomer;

    /** 关联公司信息（ALIPAY_SHADOW_WALLET 时返回） */
    private AlipayCustomer affiliatedCustomer;

    /**
     * 判断接口调用是否成功
     *
     * @return result.resultStatus = S 时返回 true
     */
    public boolean isSuccess() {
        return result != null && "S".equals(result.getResultStatus());
    }

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
     * Getter method for property <tt>accountInfos</tt>.
     *
     * @return property value of accountInfos
     */
    public List<AccountInfo> getAccountInfos() {
        return accountInfos;
    }

    /**
     * Setter method for property <tt>accountInfos</tt>.
     *
     * @param accountInfos value to be assigned to property accountInfos
     */
    public void setAccountInfos(List<AccountInfo> accountInfos) {
        this.accountInfos = accountInfos;
    }

    /**
     * Getter method for property <tt>customer</tt>.
     *
     * @return property value of customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Setter method for property <tt>customer</tt>.
     *
     * @param customer value to be assigned to property customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Getter method for property <tt>alipayCustomer</tt>.
     *
     * @return property value of alipayCustomer
     */
    public AlipayCustomer getAlipayCustomer() {
        return alipayCustomer;
    }

    /**
     * Setter method for property <tt>alipayCustomer</tt>.
     *
     * @param alipayCustomer value to be assigned to property alipayCustomer
     */
    public void setAlipayCustomer(AlipayCustomer alipayCustomer) {
        this.alipayCustomer = alipayCustomer;
    }

    /**
     * Getter method for property <tt>affiliatedCustomer</tt>.
     *
     * @return property value of affiliatedCustomer
     */
    public AlipayCustomer getAffiliatedCustomer() {
        return affiliatedCustomer;
    }

    /**
     * Setter method for property <tt>affiliatedCustomer</tt>.
     *
     * @param affiliatedCustomer value to be assigned to property affiliatedCustomer
     */
    public void setAffiliatedCustomer(AlipayCustomer affiliatedCustomer) {
        this.affiliatedCustomer = affiliatedCustomer;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
