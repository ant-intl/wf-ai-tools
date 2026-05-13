package {basePackage}.wf.model.request;

import {basePackage}.wf.model.domain.TransferFromDetail;
import {basePackage}.wf.model.domain.TransferToDetail;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst consultTransfer 请求对象（转账咨询场景）
 *
 * <p>在调用 createTransfer API 进行转账之前，集成商可调用此接口获取转账相关信息，
 * 如跨币种汇率、手续费等。
 *
 */
public class ConsultTransferRequest {

    /** 支付方转账详情 */
    private TransferFromDetail transferFromDetail;

    /** 收款方转账详情 */
    private TransferToDetail transferToDetail;

    public TransferFromDetail getTransferFromDetail() {
        return transferFromDetail;
    }

    public void setTransferFromDetail(TransferFromDetail transferFromDetail) {
        this.transferFromDetail = transferFromDetail;
    }

    public TransferToDetail getTransferToDetail() {
        return transferToDetail;
    }

    public void setTransferToDetail(TransferToDetail transferToDetail) {
        this.transferToDetail = transferToDetail;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
