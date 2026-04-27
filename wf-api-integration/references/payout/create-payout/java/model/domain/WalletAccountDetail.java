/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 钱包账户详情（代发到钱包账户场景）。
 *
 * <p>对应 WF createPayout 接口 {@code transferToDetail.transferToMethod.paymentMethodMetaData}
 * 当 {@code paymentMethodType=WALLET_ACCOUNT_DETAIL} 时的对象结构。
 *
 * <p>使用方式：
 * <ul>
 *   <li>设置 {@code paymentMethodType} = {@code WALLET_ACCOUNT_DETAIL}</li>
 *   <li>将本对象序列化为 JSON 字符串，设置到 {@code paymentMethodMetaData}</li>
 *   <li>可选：同时通过 {@code paymentMethodId} 传入 {@code walletAccountId}</li>
 * </ul>
 *
 * @author 逸游
 * @version WalletAccountDetail.java, v 0.1 2026-04-27
 */
public class WalletAccountDetail {

    /** 钱包账户持有人姓名 */
    private String walletFullName;

    /** 钱包账号 */
    private String walletAccountNo;

    /** 钱包品牌名称（如 GCASH） */
    private String walletBrandName;

    /** 钱包国家代码（ISO-3166，2 位字母，如 PH） */
    private String walletCountryCode;

    public String getWalletFullName() {
        return walletFullName;
    }

    public void setWalletFullName(String walletFullName) {
        this.walletFullName = walletFullName;
    }

    public String getWalletAccountNo() {
        return walletAccountNo;
    }

    public void setWalletAccountNo(String walletAccountNo) {
        this.walletAccountNo = walletAccountNo;
    }

    public String getWalletBrandName() {
        return walletBrandName;
    }

    public void setWalletBrandName(String walletBrandName) {
        this.walletBrandName = walletBrandName;
    }

    public String getWalletCountryCode() {
        return walletCountryCode;
    }

    public void setWalletCountryCode(String walletCountryCode) {
        this.walletCountryCode = walletCountryCode;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
