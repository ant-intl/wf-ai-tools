/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.AccountBalance;

import java.util.List;

/**
 * WorldFirst inquiryBalance 响应对象
 *
 * @author Qoder
 * @version InquiryBalanceResponse.java, v 0.1 2026-03-24
 */
public class InquiryBalanceResponse {

    /** 接口调用结果 */
    private Result result;

    /** 响应唯一 ID，最大 32 位 */
    private String responseId;

    /** 账户余额列表 */
    private List<AccountBalance> accountBalances;

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
     * Getter method for property <tt>accountBalances</tt>.
     *
     * @return property value of accountBalances
     */
    public List<AccountBalance> getAccountBalances() {
        return accountBalances;
    }

    /**
     * Setter method for property <tt>accountBalances</tt>.
     *
     * @param accountBalances value to be assigned to property accountBalances
     */
    public void setAccountBalances(List<AccountBalance> accountBalances) {
        this.accountBalances = accountBalances;
    }

    @Override
    public String toString() {
        return "InquiryBalanceResponse{result=" + result
            + ", responseId='" + responseId + "', accountBalances=" + accountBalances + '}';
    }
}
