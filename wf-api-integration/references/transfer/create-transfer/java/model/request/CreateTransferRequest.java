package {basePackage}.wf.model.request;

import {basePackage}.wf.model.domain.TransferFromDetail;
import {basePackage}.wf.model.domain.TransferToDetail;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst createTransfer 请求对象（户到户转账场景）
 *
 * <p>集成商可调用此接口进行资金的转账。资金会转入到另一个万里汇账户，
 * 即资金在万里汇账户之间流通。
 *
 */
public class CreateTransferRequest {

    /** 集成商定义的唯一转账识别 ID（幂等键），最大 64 字符 */
    private String transferRequestId;

    /**
     * 转账业务类型码。
     * <ul>
     *   <li>{@code MULTI_ACCOUNT_TRANSFER} — 万里汇主/子账号余额互转</li>
     * </ul>
     * 在主账号转至子账号或子账号转至子账号时必填。
     */
    private String businessSceneCode;

    /** 支付方转账详情 */
    private TransferFromDetail transferFromDetail;

    /** 收款方转账详情 */
    private TransferToDetail transferToDetail;

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
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

