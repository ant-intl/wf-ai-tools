/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 收款方转账详情（createTransfer 户到户转账场景）
 *
 * @author Qoder
 * @version TransferToDetail.java, v 0.1 2026-04-01
 */
public class TransferToDetail {

    /** 收款人使用的收款方式 */
    private PaymentMethod transferToMethod;

    /** 转账收款金额（计算费用前） */
    private Amount transferToAmount;

    /** 实际转账金额（计算手续费之后的金额） */
    private Amount actualTransferToAmount;

    /** 付款方支付的转账手续费 */
    private Amount feeAmount;

    /** 转账汇率 */
    private Quote transferQuote;

    /**
     * 是否迁移交易材料。迁移交易材料可能会影响账号的结汇额度。
     *
     * <p>当 businessSceneCode 字段取值为 MULTI_ACCOUNT_TRANSFER 时，此字段为必传。
     */
    private Boolean needMigrateMaterial;

    /** 用于接收转账通知的地址，万里汇会使用此地址将转账结果通过 notifyTransfer 接口发送给集成商 */
    private String transferNotifyUrl;

    /**
     * 用于银行结算账单使用的信息。
     *
     * <p>最大长度：1024 字符
     */
    private String transferRemark;

    /**
     * 付款人留的简短附言。
     *
     * <p>最大长度：256 字符
     */
    private String transferMemo;

    /**
     * 转账额外信息。
     *
     * <p>最大长度：2048 字符
     */
    private String extendInfo;

    /**
     * 转账目的代码。
     *
     * <p>可取值：
     * <ul>
     *   <li>GDS：商品买卖</li>
     *   <li>TXS：缴税</li>
     *   <li>ACM：代理佣金</li>
     *   <li>GST：服务贸易</li>
     *   <li>COM：佣金</li>
     * </ul>
     *
     * <p>本字段默认使用 GDS，如需使用其他取值，联系万里汇技术支持。
     */
    private String purposeCode;

    /**
     * Getter method for property <tt>transferToMethod</tt>.
     *
     * @return property value of transferToMethod
     */
    public PaymentMethod getTransferToMethod() {
        return transferToMethod;
    }

    /**
     * Setter method for property <tt>transferToMethod</tt>.
     *
     * @param transferToMethod value to be assigned to property transferToMethod
     */
    public void setTransferToMethod(PaymentMethod transferToMethod) {
        this.transferToMethod = transferToMethod;
    }

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
     * Getter method for property <tt>actualTransferToAmount</tt>.
     *
     * @return property value of actualTransferToAmount
     */
    public Amount getActualTransferToAmount() {
        return actualTransferToAmount;
    }

    /**
     * Setter method for property <tt>actualTransferToAmount</tt>.
     *
     * @param actualTransferToAmount value to be assigned to property actualTransferToAmount
     */
    public void setActualTransferToAmount(Amount actualTransferToAmount) {
        this.actualTransferToAmount = actualTransferToAmount;
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
     * Getter method for property <tt>transferQuote</tt>.
     *
     * @return property value of transferQuote
     */
    public Quote getTransferQuote() {
        return transferQuote;
    }

    /**
     * Setter method for property <tt>transferQuote</tt>.
     *
     * @param transferQuote value to be assigned to property transferQuote
     */
    public void setTransferQuote(Quote transferQuote) {
        this.transferQuote = transferQuote;
    }

    /**
     * Getter method for property <tt>needMigrateMaterial</tt>.
     *
     * @return property value of needMigrateMaterial
     */
    public Boolean getNeedMigrateMaterial() {
        return needMigrateMaterial;
    }

    /**
     * Setter method for property <tt>needMigrateMaterial</tt>.
     *
     * @param needMigrateMaterial value to be assigned to property needMigrateMaterial
     */
    public void setNeedMigrateMaterial(Boolean needMigrateMaterial) {
        this.needMigrateMaterial = needMigrateMaterial;
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
     * Getter method for property <tt>transferRemark</tt>.
     *
     * @return property value of transferRemark
     */
    public String getTransferRemark() {
        return transferRemark;
    }

    /**
     * Setter method for property <tt>transferRemark</tt>.
     *
     * @param transferRemark value to be assigned to property transferRemark
     */
    public void setTransferRemark(String transferRemark) {
        this.transferRemark = transferRemark;
    }

    /**
     * Getter method for property <tt>transferMemo</tt>.
     *
     * @return property value of transferMemo
     */
    public String getTransferMemo() {
        return transferMemo;
    }

    /**
     * Setter method for property <tt>transferMemo</tt>.
     *
     * @param transferMemo value to be assigned to property transferMemo
     */
    public void setTransferMemo(String transferMemo) {
        this.transferMemo = transferMemo;
    }

    /**
     * Getter method for property <tt>extendInfo</tt>.
     *
     * @return property value of extendInfo
     */
    public String getExtendInfo() {
        return extendInfo;
    }

    /**
     * Setter method for property <tt>extendInfo</tt>.
     *
     * @param extendInfo value to be assigned to property extendInfo
     */
    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

