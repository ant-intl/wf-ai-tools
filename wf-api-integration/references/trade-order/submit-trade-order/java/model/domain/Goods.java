package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 商品信息，适用于 B2C 和 B2B 场景。
 *
 * <p>对应 WF submitTradeOrder 接口请求体中的 {@code goods[]} 元素。
 * B2C 场景需要传入 goodsCategory，B2B 场景需要传入 goodsUnit/goodsCnName/storeUrl。
 *
 */
public class Goods {

    // -------------------------------------------------------------------------
    // 通用字段（B2C + B2B）
    // -------------------------------------------------------------------------

    /** 商品名称，最大 256 字符 */
    private String goodsName;

    /** 商品数量 */
    private String goodsQuantity;

    // -------------------------------------------------------------------------
    // B2C 专属字段
    // -------------------------------------------------------------------------

    /** 商品类目，最大 256 字符（B2C 必填） */
    private String goodsCategory;

    // -------------------------------------------------------------------------
    // B2B 专属字段
    // -------------------------------------------------------------------------

    /** 计量单位（B2B 必填） */
    private String goodsUnit;

    /** 商品中文名称（B2B 必填） */
    private String goodsCnName;

    /** 商品链接（B2B 必填） */
    private String storeUrl;

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    /**
     * Getter method for property <tt>goodsName</tt>.
     *
     * @return property value of goodsName
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * Setter method for property <tt>goodsName</tt>.
     *
     * @param goodsName value to be assigned to property goodsName
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * Getter method for property <tt>goodsQuantity</tt>.
     *
     * @return property value of goodsQuantity
     */
    public String getGoodsQuantity() {
        return goodsQuantity;
    }

    /**
     * Setter method for property <tt>goodsQuantity</tt>.
     *
     * @param goodsQuantity value to be assigned to property goodsQuantity
     */
    public void setGoodsQuantity(String goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
    }

    /**
     * Getter method for property <tt>goodsCategory</tt>.
     *
     * @return property value of goodsCategory
     */
    public String getGoodsCategory() {
        return goodsCategory;
    }

    /**
     * Setter method for property <tt>goodsCategory</tt>.
     *
     * @param goodsCategory value to be assigned to property goodsCategory
     */
    public void setGoodsCategory(String goodsCategory) {
        this.goodsCategory = goodsCategory;
    }

    /**
     * Getter method for property <tt>goodsUnit</tt>.
     *
     * @return property value of goodsUnit
     */
    public String getGoodsUnit() {
        return goodsUnit;
    }

    /**
     * Setter method for property <tt>goodsUnit</tt>.
     *
     * @param goodsUnit value to be assigned to property goodsUnit
     */
    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    /**
     * Getter method for property <tt>goodsCnName</tt>.
     *
     * @return property value of goodsCnName
     */
    public String getGoodsCnName() {
        return goodsCnName;
    }

    /**
     * Setter method for property <tt>goodsCnName</tt>.
     *
     * @param goodsCnName value to be assigned to property goodsCnName
     */
    public void setGoodsCnName(String goodsCnName) {
        this.goodsCnName = goodsCnName;
    }

    /**
     * Getter method for property <tt>storeUrl</tt>.
     *
     * @return property value of storeUrl
     */
    public String getStoreUrl() {
        return storeUrl;
    }

    /**
     * Setter method for property <tt>storeUrl</tt>.
     *
     * @param storeUrl value to be assigned to property storeUrl
     */
    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
