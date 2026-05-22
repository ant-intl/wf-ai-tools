package {basePackage}.wf.model.request;

import {basePackage}.wf.model.domain.TransferFromDetail;
import {basePackage}.wf.model.domain.TransferToDetail;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst createPayout 请求对象（同币种代发到三方卡场景）
 *
 * <p>接口约束：transferFromAmount 与 transferToAmount 不能同时传入，二选一。
 * 当前场景传 transferToAmount，WF 自动计算付款方扣款金额。
 */
public class CreatePayoutRequest {

    /** 集成商定义的唯一转账识别 ID（幂等键），最大 64 字符 */
    private String transferRequestId;

    /** 付款方转账详情（与 transferToDetail.transferToAmount 互斥） */
    private TransferFromDetail transferFromDetail;

    /** 收款方转账详情（含 transferToAmount） */
    private TransferToDetail transferToDetail;

    /** 转账业务类型码：
     * THIRD_PARTY_PAYOUT — 代发到第三方卡
     * SAME_NAME_PAYOUT — 提现到同名卡
     * 当 transferToDetail.transferToAmount.currency = CNY 时必填
     */
    private String businessSceneCode;

    /** 转账订单附加信息（可选） */
    private Object transferOrderAddition;

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
     * Getter method for property <tt>transferOrderAddition</tt>.
     *
     * @return property value of transferOrderAddition
     */
    public Object getTransferOrderAddition() {
        return transferOrderAddition;
    }

    /**
     * Setter method for property <tt>transferOrderAddition</tt>.
     *
     * @param transferOrderAddition value to be assigned to property transferOrderAddition
     */
    public void setTransferOrderAddition(Object transferOrderAddition) {
        this.transferOrderAddition = transferOrderAddition;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
