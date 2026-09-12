package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 商品信息对象。
 *
 * <p>描述对账单中涉及报关交易的商品信息。
 */
public class GoodsInfo {

    /** 报关商品名称 */
    private String goodsName;

    /** 报关商品金额 */
    private Amount goodsAmount;

    public GoodsInfo() {
    }

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
     * Getter method for property <tt>goodsAmount</tt>.
     *
     * @return property value of goodsAmount
     */
    public Amount getGoodsAmount() {
        return goodsAmount;
    }

    /**
     * Setter method for property <tt>goodsAmount</tt>.
     *
     * @param goodsAmount value to be assigned to property goodsAmount
     */
    public void setGoodsAmount(Amount goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
