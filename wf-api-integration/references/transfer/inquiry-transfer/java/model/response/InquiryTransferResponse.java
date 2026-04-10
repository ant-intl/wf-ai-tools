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
 * WorldFirst inquiryTransfer 响应对象
 *
 * <p>响应包含两层结果码：
 * <ul>
 *   <li>{@code result} — API 调用结果</li>
 *   <li>{@code transferResult} — 转账业务结果</li>
 * </ul>
 *
 * @author Qoder
 * @version InquiryTransferResponse.java, v 0.1 2026-04-07
 */
public class InquiryTransferResponse {

    /** 接口调用结果 */
    private Result result;

    /** 集成商定义的转账请求 ID（回传），最大 64 字符 */
    private String transferRequestId;

    /** WF 生成的转账 ID，最大 64 字符 */
    private String transferId;

    /**
     * 转账业务类型码。
     * <ul>
     *   <li>{@code MULTI_ACCOUNT_TRANSFER} — 万里汇主/子账号余额互转</li>
     *   <li>{@code ATOMIC_TRANSFER} — 万里汇户到户转账</li>
     * </ul>
     */
    private String businessSceneCode;

    /** 转账结果，包含 resultStatus/resultCode/resultMessage */
    private Result transferResult;

    /** 转账结束时间，ISO 8601 格式。仅在重复请求时返回 */
    private String transferFinishTime;

    /** 支付方转账详情 */
    private TransferFromDetail transferFromDetail;

    /** 收款方转账详情 */
    private TransferToDetail transferToDetail;

    /**
     * 转账是否仍在处理中（需继续轮询）
     *
     * @return transferResult.resultCode 为 PROCESSING 时返回 true
     */
    public boolean isTransferProcessing() {
        return transferResult != null && "S".equals(transferResult.getResultStatus())
            && "PROCESSING".equals(transferResult.getResultCode());
    }

    /**
     * 转账是否最终成功
     *
     * @return transferResult.resultCode 为 SUCCESS 时返回 true
     */
    public boolean isTransferSuccess() {
        return transferResult != null && "S".equals(transferResult.getResultStatus())
            && "SUCCESS".equals(transferResult.getResultCode());
    }

    /**
     * 转账是否最终失败
     *
     * @return transferResult.resultStatus 为 F 时返回 true
     */
    public boolean isTransferFailed() {
        return transferResult != null && "F".equals(transferResult.getResultStatus());
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
     * Getter method for property <tt>businessSceneCode</tt>.
     *
     * @return property value of businessSceneCode
     */
    public String getBusinessSceneCode() {
        return businessSceneCode;
    }

    /**
     * Setter method for property <tt>businessSceneCode</tt>.
     *
     * @param businessSceneCode value to be assigned to property businessSceneCode
     */
    public void setBusinessSceneCode(String businessSceneCode) {
        this.businessSceneCode = businessSceneCode;
    }

    /**
     * Getter method for property <tt>transferResult</tt>.
     *
     * @return property value of transferResult
     */
    public Result getTransferResult() {
        return transferResult;
    }

    /**
     * Setter method for property <tt>transferResult</tt>.
     *
     * @param transferResult value to be assigned to property transferResult
     */
    public void setTransferResult(Result transferResult) {
        this.transferResult = transferResult;
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
