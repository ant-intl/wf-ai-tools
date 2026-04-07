/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

/**
 * 运单信息。
 *
 * <p>对应 WF submitTradeOrder 接口请求体中的 {@code shipping.wayBillInfos[]} 元素。
 *
 * @author Qoder
 * @version WayBillInfo.java, v 0.1 2026-04-03
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
        return "WayBillInfo{shippingOrderReferenceNo='" + shippingOrderReferenceNo + "'}";
    }
}
