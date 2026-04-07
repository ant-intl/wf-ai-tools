/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.TransferFromDetail;
import {basePackage}.wf.model.domain.TransferResult;
import {basePackage}.wf.model.domain.TransferToDetail;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst inquiryPayout 响应对象。
 *
 * <p>包含两层结果：
 * <ul>
 *   <li>{@code result} — API 调用级别结果，resultStatus=S 表示查询本身成功</li>
 *   <li>{@code transferResult} — 代发单级别结果，表示该笔代发的实际处理状态</li>
 * </ul>
 *
 * @author Qoder
 * @version InquiryPayoutResponse.java, v 0.1 2026-03-27
 */
public class InquiryPayoutResponse {

    /** API 调用级别结果 */
    private Result result;

    /** 代发单级别结果 */
    private TransferResult transferResult;

    /** 集成商定义的代发请求 ID */
    private String transferRequestId;

    /** WF 生成的代发单 ID */
    private String transferId;

    /** 代发完成时间，ISO 8601 */
    private String transferFinishTime;

    /** 计费模式：INNER_DEDUCT / OUTER_DEDUCT */
    private String chargeMode;

    /** 付款方实际扣款详情 */
    private TransferFromDetail transferFromDetail;

    /** 收款方实际到账详情 */
    private TransferToDetail transferToDetail;

    public Result getResult() { return result; }
    public void setResult(Result result) { this.result = result; }
    public TransferResult getTransferResult() { return transferResult; }
    public void setTransferResult(TransferResult transferResult) { this.transferResult = transferResult; }
    public String getTransferRequestId() { return transferRequestId; }
    public void setTransferRequestId(String transferRequestId) { this.transferRequestId = transferRequestId; }
    public String getTransferId() { return transferId; }
    public void setTransferId(String transferId) { this.transferId = transferId; }
    public String getTransferFinishTime() { return transferFinishTime; }
    public void setTransferFinishTime(String transferFinishTime) { this.transferFinishTime = transferFinishTime; }
    public String getChargeMode() { return chargeMode; }
    public void setChargeMode(String chargeMode) { this.chargeMode = chargeMode; }
    public TransferFromDetail getTransferFromDetail() { return transferFromDetail; }
    public void setTransferFromDetail(TransferFromDetail transferFromDetail) { this.transferFromDetail = transferFromDetail; }
    public TransferToDetail getTransferToDetail() { return transferToDetail; }
    public void setTransferToDetail(TransferToDetail transferToDetail) { this.transferToDetail = transferToDetail; }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
