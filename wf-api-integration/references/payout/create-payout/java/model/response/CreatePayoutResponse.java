/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.TransferFromDetail;
import {basePackage}.wf.model.domain.TransferToDetail;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst createPayout 响应对象
 *
 * @author Qoder
 * @version CreatePayoutResponse.java, v 0.1 2026-03-25
 */
public class CreatePayoutResponse {

    /** 接口调用结果 */
    private Result result;

    /** 集成商定义的转账请求 ID（回传） */
    private String transferRequestId;

    /** WF 生成的转账 ID，最大 64 字符 */
    private String transferId;

    /** 计费模式：INNER_DEDUCT（内扣）/ OUTER_DEDUCT（外扣） */
    private String chargeMode;

    /** 支付方实际扣款详情 */
    private TransferFromDetail transferFromDetail;

    /** 收款方实际到账详情 */
    private TransferToDetail transferToDetail;

    /**
     * 是否处理中（异步状态，需调用 inquiryPayout 查询最终结果）
     *
     * @return resultCode 为 PROCESSING 时返回 true
     */
    public boolean isProcessing() {
        return result != null && "S".equals(result.getResultStatus())
            && "PROCESSING".equals(result.getResultCode());
    }

    /**
     * 是否最终成功
     *
     * @return resultCode 为 SUCCESS 时返回 true
     */
    public boolean isSuccess() {
        return result != null && "S".equals(result.getResultStatus())
            && "SUCCESS".equals(result.getResultCode());
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
