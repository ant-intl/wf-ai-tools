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
 * <p>支持五种收款方式（互斥）：
 * <ul>
 *   <li><b>卡详情模式</b>：{@code paymentMethodType=BANK_ACCOUNT_DETAIL}，设置 {@code paymentMethodMetaData} 传递银行卡详情信息</li>
 *   <li><b>卡 token 模式</b>：{@code paymentMethodType=BENEFICIARY_TOKEN}，将 {@code beneficiaryToken} 作为 {@code paymentMethodId} 传入</li>
 *   <li><b>支付宝账户详情模式</b>：{@code paymentMethodType=ALIPAY_CN_DETAIL}，设置 {@code paymentMethodMetaData} 传递支付宝账户信息</li>
 *   <li><b>关联支付宝钱包模式</b>：{@code paymentMethodType=REFERENCE_ALIPAY_CN}，将 {@code referenceCustomerId} 作为 {@code paymentMethodId} 传入</li>
 *   <li><b>钱包账户模式</b>：{@code paymentMethodType=WALLET_ACCOUNT_DETAIL}，设置 {@code paymentMethodMetaData} 传递钱包账户信息（JSON 字符串，含 walletFullName/walletAccountNo/walletBrandName/walletCountryCode）
 * </ul>
 *
 * @author Qoder
 * @version TransferToMethod.java, v 0.1 2026-03-27
 */
public class TransferToMethod {

    /**
     * 支付方式类型。
     * <ul>
     *   <li>{@code BANK_ACCOUNT_DETAIL} — 代发到银行卡（卡详情模式）</li>
     *   <li>{@code BENEFICIARY_TOKEN} — 代发到银行卡（卡 token 模式）</li>
     *   <li>{@code ALIPAY_CN_DETAIL} — 代发到支付宝账户（支付宝账户详情模式）</li>
     *   <li>{@code REFERENCE_ALIPAY_CN} — 代发到关联的支付宝钱包</li>
     *   <li>{@code WALLET_ACCOUNT_DETAIL} — 代发到钱包账户（钱包账户模式）</li>
     * </ul>
     */
    private String paymentMethodType;

    /** 支付方式元数据（JSON 字符串，银行账户详情或钱包账户详情） */
    private String paymentMethodMetaData;

    /**
     * 支付方式 ID。
     * <ul>
     *   <li><b>卡 token 模式</b>（{@code BENEFICIARY_TOKEN}）：请求中传入 {@code beneficiaryToken} 作为 paymentMethodId</li>
     *   <li><b>关联支付宝钱包模式</b>（{@code REFERENCE_ALIPAY_CN}）：请求中传入 {@code referenceCustomerId} 作为 paymentMethodId</li>
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
     * @return paymentMethodMetaData JSON 字符串
     */
    public String getPaymentMethodMetaData() {
        return paymentMethodMetaData;
    }

    /**
     * 设置支付方式元数据。
     *
     * @param paymentMethodMetaData JSON 字符串（银行账户详情或钱包账户详情）
     */
    public void setPaymentMethodMetaData(String paymentMethodMetaData) {
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
