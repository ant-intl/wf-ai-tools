package {basePackage}.wf.model.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * B2C 商户信息。
 *
 * <p>对应 WF submitTradeOrder 接口请求体中的 {@code merchant} 字段。
 *
 */
public class Merchant {

    /** 店铺信息 */
    private Store store;

    /** WF 分配的商户 ID */
    private String merchantId;

    /**
     * Getter method for property <tt>store</tt>.
     *
     * @return property value of store
     */
    public Store getStore() {
        return store;
    }

    /**
     * Setter method for property <tt>store</tt>.
     *
     * @param store value to be assigned to property store
     */
    public void setStore(Store store) {
        this.store = store;
    }

    /**
     * Getter method for property <tt>merchantId</tt>.
     *
     * @return property value of merchantId
     */
    public String getMerchantId() {
        return merchantId;
    }

    /**
     * Setter method for property <tt>merchantId</tt>.
     *
     * @param merchantId value to be assigned to property merchantId
     */
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
