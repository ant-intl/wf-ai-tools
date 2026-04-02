/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.TransferFromDetail;
import {basePackage}.wf.model.domain.TransferToDetail;

/**
 * WorldFirst createTransfer 响应对象
 *
 * @author Qoder
 * @version CreateTransferResponse.java, v 0.1 2026-04-01
 */
public class CreateTransferResponse {

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

    /** 支付方转账详情 */
    private TransferFromDetail transferFromDetail;

    /** 收款方转账详情 */
    private TransferToDetail transferToDetail;

    /**
     * 是否处理中（异步状态，需调用 inquiryTransfer 查询最终结果）
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
        return "CreateTransferResponse{result=" + result
            + ", transferRequestId='" + transferRequestId
            + "', transferId='" + transferId
            + "', businessSceneCode='" + businessSceneCode
            + "', transferFromDetail=" + transferFromDetail
            + ", transferToDetail=" + transferToDetail + '}';
    }
}

