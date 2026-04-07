/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

/**
 * 店铺信息。
 *
 * <p>对应 WF submitTradeOrder 接口请求体中的 {@code merchant.store} 字段。
 *
 * @author Qoder
 * @version Store.java, v 0.1 2026-04-03
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
        return "Store{storeShopUrl='" + storeShopUrl + "'}";
    }
}
