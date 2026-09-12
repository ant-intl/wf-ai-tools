package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 商户信息对象。
 *
 * <p>描述贸易订单中商户的基本信息，包括商户 ID、类别码、名称和店铺信息。
 */
public class Merchant {

    /** 第三方平台分配的商户标识（非 WorldFirst 账户 ID） */
    private String merchantId;

    /** 商户类别码（如 5734） */
    private String merchantMCC;

    /** 商户展示名称 */
    private String merchantName;

    /** 商户店铺信息 */
    private Store store;

    public Merchant() {
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantMCC() {
        return merchantMCC;
    }

    public void setMerchantMCC(String merchantMCC) {
        this.merchantMCC = merchantMCC;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
