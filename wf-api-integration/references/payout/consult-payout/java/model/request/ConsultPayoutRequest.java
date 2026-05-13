package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import {basePackage}.wf.model.domain.TransferFromDetail;
import {basePackage}.wf.model.domain.TransferToDetail;

/**
 * WorldFirst consultPayout 请求对象。
 *
 * <p>在调用 createPayout API 进行跨币种转账之前，集成商可调用此接口获取汇率报价（quoteId）。
 *
 * <p>接口约束：
 * <ul>
 *   <li>transferFromDetail 和 transferToDetail 均为必填</li>
 *   <li>transferFromAmount.value 与 transferToAmount.value 不能同时传入，二选一</li>
 *   <li>当 transferToAmount.currency = CNY 时，businessSceneCode 必填</li>
 * </ul>
 */
public class ConsultPayoutRequest {

    /**
     * 支付方转账详情。
     *
     * <p>必须指定 transferFromAmount.currency，告知 WF 从哪个币种扣款。
     */
    private TransferFromDetail transferFromDetail;

    /**
     * 收款方转账详情。
     *
     * <p>包含 transferToAmount（收款金额）和 transferToMethod（转账方式）。
     */
    private TransferToDetail transferToDetail;

    /**
     * 转账业务类型码。
     *
     * <p>当 transferToDetail.transferToAmount.currency = CNY 时，此字段为必填。
     * 可取值：
     * <ul>
     *   <li>THIRD_PARTY_PAYOUT：转账到第三方卡（企业卡或个人卡）</li>
     *   <li>SAME_NAME_PAYOUT：提现到同名卡</li>
     * </ul>
     */
    private String businessSceneCode;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

