package {basePackage}.wf.model.request;

import {basePackage}.wf.model.domain.TransferFromDetail;
import {basePackage}.wf.model.domain.TransferToDetail;
import {basePackage}.wf.model.domain.TransferOrderAddition;
import {basePackage}.wf.model.response.Result;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst notifyTransfer 回调请求对象。
 *
 * <p>转账完成后，万里汇会主动调用此接口将转账结果通知给集成商。
 * {@code transferRequestId} 为幂等字段，集成商可用于去重。
 *
 */
public class NotifyTransferRequest {

    /** 转账结果，包含 resultStatus/resultCode/resultMessage */
    private Result transferResult;

    /** 集成商定义的唯一转账请求 ID（幂等键），最大 64 字符 */
    private String transferRequestId;

    /** 万里汇生成的转账 ID，最大 64 字符 */
    private String transferId;

    /** 转账完成时间，ISO 8601 格式 */
    private String transferFinishTime;

    /** 支付方转账详情 */
    private TransferFromDetail transferFromDetail;

    /** 收款方转账详情 */
    private TransferToDetail transferToDetail;

    /** 转账订单附加信息 */
    private TransferOrderAddition transferOrderAddition;

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

    /**
     * Getter method for property <tt>transferOrderAddition</tt>.
     *
     * @return property value of transferOrderAddition
     */
    public TransferOrderAddition getTransferOrderAddition() {
        return transferOrderAddition;
    }

    /**
     * Setter method for property <tt>transferOrderAddition</tt>.
     *
     * @param transferOrderAddition value to be assigned to property transferOrderAddition
     */
    public void setTransferOrderAddition(TransferOrderAddition transferOrderAddition) {
        this.transferOrderAddition = transferOrderAddition;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
