/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst createPayout 转账方式对象。
 *
 * <p>对应 WF 接口 {@code transferToDetail.transferToMethod} 字段，
 * 为嵌套对象（非字符串），包含 {@code paymentMethodType} 和 {@code paymentMethodMetaData}。
 *
 * <p>支持两种收款方式（互斥）：
 * <ul>
 *   <li><b>卡详情模式</b>：设置 {@code paymentMethodMetaData} 传递银行卡详情信息</li>
 *   <li><b>卡 token 模式</b>：将 {@code beneficiaryToken} 作为 {@code paymentMethodId} 传入</li>
 * </ul>
 *
 * @author Qoder
 * @version TransferToMethod.java, v 0.1 2026-03-27
 */
public class TransferToMethod {

    /**
     * 支付方式类型。
     * 代发到银行卡固定传 {@code BANK_ACCOUNT_DETAIL}。
     */
    private String paymentMethodType;

    /** 支付方式元数据（银行账户详情） */
    private PaymentMethodMetaData paymentMethodMetaData;

    /**
     * 支付方式 ID。
     * <ul>
     *   <li><b>卡详情模式</b>：响应中返回</li>
     *   <li><b>卡 token 模式</b>：请求中传入 {@code beneficiaryToken} 作为 paymentMethodId</li>
     * </ul>
     */
    private String paymentMethodId;

    /**
     * 获取支付方式类型。
     *
     * @return paymentMethodType
     */
    public String getPaymentMethodType() {
        return paymentMethodType;
    }

    /**
     * 设置支付方式类型。
     *
     * @param paymentMethodType 如 {@code BANK_ACCOUNT_DETAIL}
     */
    public void setPaymentMethodType(String paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    /**
     * 获取支付方式元数据。
     *
     * @return paymentMethodMetaData
     */
    public PaymentMethodMetaData getPaymentMethodMetaData() {
        return paymentMethodMetaData;
    }

    /**
     * 设置支付方式元数据。
     *
     * @param paymentMethodMetaData 银行账户详情
     */
    public void setPaymentMethodMetaData(PaymentMethodMetaData paymentMethodMetaData) {
        this.paymentMethodMetaData = paymentMethodMetaData;
    }

    /**
     * 获取支付方式 ID。
     *
     * @return paymentMethodId
     */
    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    /**
     * 设置支付方式 ID。
     *
     * @param paymentMethodId 支付方式 ID（明文模式）或 beneficiaryToken（token 模式）
     */
    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    private static String maskId(String id) {
        if (id == null || id.length() <= 8) {
            return id;
        }
        return id.substring(0, 4) + "****" + id.substring(id.length() - 4);
    }
}
