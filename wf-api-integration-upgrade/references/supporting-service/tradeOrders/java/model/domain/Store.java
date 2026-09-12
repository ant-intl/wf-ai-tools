package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 商户店铺信息对象。
 *
 * <p>描述贸易订单中商户的店铺详情。
 */
public class Store {

    /** 店铺 URL，最大长度 256 字符 */
    private String storeShopUrl;

    /** 外部店铺 ID，最大长度 64 字符 */
    private String referenceStoreId;

    /** 店铺名称，最大长度 128 字符 */
    private String storeName;

    /** 店铺商品类别码（如 5734） */
    private String storeMCC;

    /** 店铺在平台上的展示名称，最大长度 128 字符 */
    private String storeDisplayName;

    /** 店铺地址 */
    private Address storeAddress;

    public Store() {
    }

    public String getStoreShopUrl() {
        return storeShopUrl;
    }

    public void setStoreShopUrl(String storeShopUrl) {
        this.storeShopUrl = storeShopUrl;
    }

    public String getReferenceStoreId() {
        return referenceStoreId;
    }

    public void setReferenceStoreId(String referenceStoreId) {
        this.referenceStoreId = referenceStoreId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreMCC() {
        return storeMCC;
    }

    public void setStoreMCC(String storeMCC) {
        this.storeMCC = storeMCC;
    }

    public String getStoreDisplayName() {
        return storeDisplayName;
    }

    public void setStoreDisplayName(String storeDisplayName) {
        this.storeDisplayName = storeDisplayName;
    }

    public {basePackage}.wf.model.domain.Address getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress({basePackage}.wf.model.domain.Address storeAddress) {
        this.storeAddress = storeAddress;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
