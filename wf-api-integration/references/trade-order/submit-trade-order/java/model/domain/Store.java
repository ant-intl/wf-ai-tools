package {basePackage}.wf.model.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 店铺信息。
 *
 * <p>对应 WF submitTradeOrder 接口请求体中的 {@code merchant.store} 字段。
 *
 */
public class Store {

    /** 店铺链接，最大 64 字符 */
    private String storeShopUrl;

    /**
     * Getter method for property <tt>storeShopUrl</tt>.
     *
     * @return property value of storeShopUrl
     */
    public String getStoreShopUrl() {
        return storeShopUrl;
    }

    /**
     * Setter method for property <tt>storeShopUrl</tt>.
     *
     * @param storeShopUrl value to be assigned to property storeShopUrl
     */
    public void setStoreShopUrl(String storeShopUrl) {
        this.storeShopUrl = storeShopUrl;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
