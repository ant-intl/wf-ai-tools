/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.request;

import {basePackage}.wf.model.domain.TransferFromDetail;
import {basePackage}.wf.model.domain.TransferToDetail;
import {basePackage}.wf.model.domain.TransferResult;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst notifyPayout 回调请求对象。
 *
 * <p>万里汇在转账完成后，主动调用此接口将转账结果通知给集成商。
 * 集成商需在 createPayout 时通过 {@code transferToDetail.transferNotifyUrl} 配置回调地址。
 *
 * <p>{@code transferRequestId} 为幂等字段，集成商可用于去重。
 *
 * @author Qoder
 * @version NotifyPayoutRequest.java, v 0.1 2026-04-22
 */
public class NotifyPayoutRequest {

    /** 交易请求结果 */
    private TransferResult transferResult;

    /** 集成商定义的幂等请求 ID，最大 64 字符 */
    private String transferRequestId;

    /** 万里汇定义的交易唯一标识，最大 64 字符 */
    private String transferId;

    /** 交易结束时间，ISO 8601 格式，如 "2019-11-27T12:01:01+08:00" */
    private String transferFinishTime;

    /**
     * 计收费模式。
     * <ul>
     *   <li>INNER_DEDUCT：内部扣除</li>
     *   <li>OUTER_DEDUCT：外部扣除</li>
     * </ul>
     */
    private String chargeMode;

    /** 支付方转账详情 */
    private TransferFromDetail transferFromDetail;

    /** 收款方转账详情 */
    private TransferToDetail transferToDetail;

    /**
     * Getter method for property <tt>transferResult</tt>.
     *
     * @return property value of transferResult
     */
    public TransferResult getTransferResult() {
        return transferResult;
    }

    /**
     * Setter method for property <tt>transferResult</tt>.
     *
     * @param transferResult value to be assigned to property transferResult
     */
    public void setTransferResult(TransferResult transferResult) {
        this.transferResult = transferResult;
    }

    /**
     * Getter method for property <tt>transferRequestId</tt>.
     *
     * @return property value of transferRequestId
     */
    public String getTransferRequestId() {
        return transferRequestId;
    }

    /**
     * Setter method for property <tt>transferRequestId</tt>.
     *
     * @param transferRequestId value to be assigned to property transferRequestId
     */
    public void setTransferRequestId(String transferRequestId) {
        this.transferRequestId = transferRequestId;
    }

    /**
     * Getter method for property <tt>transferId</tt>.
     *
     * @return property value of transferId
     */
    public String getTransferId() {
        return transferId;
    }

    /**
     * Setter method for property <tt>transferId</tt>.
     *
     * @param transferId value to be assigned to property transferId
     */
    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    /**
     * Getter method for property <tt>transferFinishTime</tt>.
     *
     * @return property value of transferFinishTime
     */
    public String getTransferFinishTime() {
        return transferFinishTime;
    }

    /**
     * Setter method for property <tt>transferFinishTime</tt>.
     *
     * @param transferFinishTime value to be assigned to property transferFinishTime
     */
    public void setTransferFinishTime(String transferFinishTime) {
        this.transferFinishTime = transferFinishTime;
    }

    /**
     * Getter method for property <tt>chargeMode</tt>.
     *
     * @return property value of chargeMode
     */
    public String getChargeMode() {
        return chargeMode;
    }

    /**
     * Setter method for property <tt>chargeMode</tt>.
     *
     * @param chargeMode value to be assigned to property chargeMode
     */
    public void setChargeMode(String chargeMode) {
        this.chargeMode = chargeMode;
    }

    /**
     * Getter method for property <tt>transferFromDetail</tt>.
     *
     * @return property value of transferFromDetail
     */
    public TransferFromDetail getTransferFromDetail() {
        return transferFromDetail;
    }

    /**
     * Setter method for property <tt>transferFromDetail</tt>.
     *
     * @param transferFromDetail value to be assigned to property transferFromDetail
     */
    public void setTransferFromDetail(TransferFromDetail transferFromDetail) {
        this.transferFromDetail = transferFromDetail;
    }

    /**
     * Getter method for property <tt>transferToDetail</tt>.
     *
     * @return property value of transferToDetail
     */
    public TransferToDetail getTransferToDetail() {
        return transferToDetail;
    }

    /**
     * Setter method for property <tt>transferToDetail</tt>.
     *
     * @param transferToDetail value to be assigned to property transferToDetail
     */
    public void setTransferToDetail(TransferToDetail transferToDetail) {
        this.transferToDetail = transferToDetail;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
