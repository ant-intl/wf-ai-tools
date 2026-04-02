/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 收款方转账详情（代发到三方卡场景）
 *
 * @author Qoder
 * @version TransferToDetail.java, v 0.1 2026-03-25
 */
public class TransferToDetail {

    /** 收款金额 */
    private Amount transferToAmount;

    /** 转账方式（嵌套对象） */
    private TransferToMethod transferToMethod;

    /**
     * 汇率报价信息。
     *
     * <p>跨币种代发时，需要先通过 consultPayout 获取 quoteId，
     * 然后在 createPayout 请求中传入 transferQuote.quoteId。
     */
    private TransferQuote transferQuote;

    /** 转账用途代码，默认 GDS */
    private String purposeCode;

    /** 异步通知回调 URL */
    private String transferNotifyUrl;

    /** 手续费金额（响应中返回） */
    private Amount feeAmount;

    /**
     * Getter method for property <tt>transferToAmount</tt>.
     *
     * @return property value of transferToAmount
     */
    public Amount getTransferToAmount() {
        return transferToAmount;
    }

    /**
     * Setter method for property <tt>transferToAmount</tt>.
     *
     * @param transferToAmount value to be assigned to property transferToAmount
     */
    public void setTransferToAmount(Amount transferToAmount) {
        this.transferToAmount = transferToAmount;
    }

    /**
     * Getter method for property <tt>transferToMethod</tt>.
     *
     * @return property value of transferToMethod
     */
    public TransferToMethod getTransferToMethod() {
        return transferToMethod;
    }

    /**
     * Setter method for property <tt>transferToMethod</tt>.
     *
     * @param transferToMethod value to be assigned to property transferToMethod
     */
    public void setTransferToMethod(TransferToMethod transferToMethod) {
        this.transferToMethod = transferToMethod;
    }

    /**
     * Getter method for property <tt>transferQuote</tt>.
     *
     * @return property value of transferQuote
     */
    public TransferQuote getTransferQuote() {
        return transferQuote;
    }

    /**
     * Setter method for property <tt>transferQuote</tt>.
     *
     * @param transferQuote value to be assigned to property transferQuote
     */
    public void setTransferQuote(TransferQuote transferQuote) {
        this.transferQuote = transferQuote;
    }

    /**
     * Getter method for property <tt>purposeCode</tt>.
     *
     * @return property value of purposeCode
     */
    public String getPurposeCode() {
        return purposeCode;
    }

    /**
     * Setter method for property <tt>purposeCode</tt>.
     *
     * @param purposeCode value to be assigned to property purposeCode
     */
    public void setPurposeCode(String purposeCode) {
        this.purposeCode = purposeCode;
    }

    /**
     * Getter method for property <tt>transferNotifyUrl</tt>.
     *
     * @return property value of transferNotifyUrl
     */
    public String getTransferNotifyUrl() {
        return transferNotifyUrl;
    }

    /**
     * Setter method for property <tt>transferNotifyUrl</tt>.
     *
     * @param transferNotifyUrl value to be assigned to property transferNotifyUrl
     */
    public void setTransferNotifyUrl(String transferNotifyUrl) {
        this.transferNotifyUrl = transferNotifyUrl;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
