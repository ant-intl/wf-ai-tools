package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 商品信息对象。
 *
 * <p>描述贸易订单中单个商品的详情。
 */
public class Goods {

    /** 商品名称（如 "Electronics"） */
    private String goodsName;

    /** 外部商品 ID */
    private String referenceGoodsId;

    /** 商品中文名称 */
    private String goodsCnName;

    /** 订购数量，最小 1 */
    private Integer goodsQuantity;

    /** 商品或店铺 URL */
    private String storeUrl;

    /** 商品类别（如 ELECTRONICS） */
    private String goodsCategory;

    /** 出发城市，适用于 OTA 场景的 CNY 结算 */
    private String departureCity;

    /** 到达城市，适用于 OTA 场景的 CNY 结算 */
    private String arrivalCity;

    /** 机票 PNR 号，适用于 OTA 场景的 CNY 结算 */
    private String pnrNo;

    public Goods() {
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getReferenceGoodsId() {
        return referenceGoodsId;
    }

    public void setReferenceGoodsId(String referenceGoodsId) {
        this.referenceGoodsId = referenceGoodsId;
    }

    public String getGoodsCnName() {
        return goodsCnName;
    }

    public void setGoodsCnName(String goodsCnName) {
        this.goodsCnName = goodsCnName;
    }

    public Integer getGoodsQuantity() {
        return goodsQuantity;
    }

    public void setGoodsQuantity(Integer goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
    }

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public String getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(String goodsCategory) {
        this.goodsCategory = goodsCategory;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public String getPnrNo() {
        return pnrNo;
    }

    public void setPnrNo(String pnrNo) {
        this.pnrNo = pnrNo;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
