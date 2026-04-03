/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import {basePackage}.wf.model.domain.Amount;
import {basePackage}.wf.model.domain.TransferFromDetail;
import {basePackage}.wf.model.domain.TransferToDetail;

/**
 * WorldFirst consultPayout 响应对象。
 *
 * <p>包含汇率报价信息，跨币种转账时需要从响应中获取 quoteId，
 * 并在 createPayout 请求的 transferToDetail.transferQuote.quoteId 中传入。
 *
 * @author Qoder
 * @version ConsultPayoutResponse.java, v 0.1 2026-04-02
 */
public class ConsultPayoutResponse {

    /** 接口调用结果 */
    private Result result;

    /**
     * 计费模式。
     *
     * <ul>
     *   <li>INNER_DEDUCT：内扣（费用从转账金额中扣除）</li>
     *   <li>OUTER_DEDUCT：外扣（费用额外收取）</li>
     * </ul>
     *
     * <p>只有当 result.resultStatus = S 时返回。
     */
    private String chargeMode;

    /**
     * 支付方转账详情（含计算后的金额）。
     *
     * <p>只有当 result.resultStatus = S 时返回。
     */
    private TransferFromDetail transferFromDetail;

    /**
     * 收款方转账详情（含计算后的金额和汇率报价）。
     *
     * <p>跨币种转账时，transferQuote.quoteId 需要在后续 createPayout 中使用。
     * 只有当 result.resultStatus = S 时返回。
     */
    private TransferToDetail transferToDetail;

    /**
     * 剩余可结汇额度。
     *
     * <p>当 transferToDetail.transferToAmount.currency = CNY 时返回。
     * 只有当 result.resultStatus = S 时返回。
     */
    private Amount availableQuota;

    /**
     * 判断接口调用是否成功。
     *
     * @return result.resultStatus = S 时返回 true
     */
    public boolean isSuccess() {
        return result != null && "S".equals(result.getResultStatus());
    }

    /**
     * 获取汇率报价 ID。
     *
     * <p>跨币种 createPayout 时需要将此 quoteId 传入
     * transferToDetail.transferQuote.quoteId 字段。
     *
     * @return quoteId，若不存在则返回 null
     */
    public String getQuoteId() {
        if (transferToDetail != null && transferToDetail.getTransferQuote() != null) {
            return transferToDetail.getTransferQuote().getQuoteId();
        }
        return null;
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

    /**
     * Getter method for property <tt>availableQuota</tt>.
     *
     * @return property value of availableQuota
     */
    public Amount getAvailableQuota() {
        return availableQuota;
    }

    /**
     * Setter method for property <tt>availableQuota</tt>.
     *
     * @param availableQuota value to be assigned to property availableQuota
     */
    public void setAvailableQuota(Amount availableQuota) {
        this.availableQuota = availableQuota;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
public class ConsultPayoutResponse {
    
}
