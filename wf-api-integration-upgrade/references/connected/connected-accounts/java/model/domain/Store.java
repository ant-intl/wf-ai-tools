package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 店铺信息对象。
 *
 * <p>描述关联账户的店铺信息，包括店铺名称和地址。
 */
public class Store {

    /** 店铺名称 */
    private String storeName;

    /** 店铺地址 */
    private Address storeAddress;

    public Store() {
    }

    /**
     * Getter method for property <tt>storeName</tt>.
     *
     * @return property value of storeName
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * Setter method for property <tt>storeName</tt>.
     *
     * @param storeName value to be assigned to property storeName
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * Getter method for property <tt>storeAddress</tt>.
     *
     * @return property value of storeAddress
     */
    public Address getStoreAddress() {
        return storeAddress;
    }

    /**
     * Setter method for property <tt>storeAddress</tt>.
     *
     * @param storeAddress value to be assigned to property storeAddress
     */
    public void setStoreAddress(Address storeAddress) {
        this.storeAddress = storeAddress;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
