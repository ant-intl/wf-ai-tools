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
 * WorldFirst consultTransfer 响应对象
 *
 * <p>返回转账咨询结果，包含计算后的金额、手续费、汇率等信息。
 *
 * @author Qoder
 * @version ConsultTransferResponse.java, v 0.1 2026-04-07
 */
public class ConsultTransferResponse {

    /** 接口调用结果 */
    private Result result;

    /** 支付方转账详情（含计算后的金额、手续费等） */
    private TransferFromDetail transferFromDetail;

    /** 收款方转账详情（含汇率、实际到账金额等） */
    private TransferToDetail transferToDetail;

    /**
     * 是否调用成功
     *
     * @return resultCode 为 SUCCESS 时返回 true
     */
    public boolean isSuccess() {
        return result != null && "S".equals(result.getResultStatus())
            && "SUCCESS".equals(result.getResultCode());
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

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
