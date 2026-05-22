package {basePackage}.wf.model.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 运单信息。
 *
 * <p>对应 WF submitTradeOrder 接口请求体中的 {@code shipping.wayBillInfos[]} 元素。
 *
 */
public class WayBillInfo {

    /** 物流运单号 */
    private String shippingOrderReferenceNo;

    /**
     * Getter method for property <tt>shippingOrderReferenceNo</tt>.
     *
     * @return property value of shippingOrderReferenceNo
     */
    public String getShippingOrderReferenceNo() {
        return shippingOrderReferenceNo;
    }

    /**
     * Setter method for property <tt>shippingOrderReferenceNo</tt>.
     *
     * @param shippingOrderReferenceNo value to be assigned to property shippingOrderReferenceNo
     */
    public void setShippingOrderReferenceNo(String shippingOrderReferenceNo) {
        this.shippingOrderReferenceNo = shippingOrderReferenceNo;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
