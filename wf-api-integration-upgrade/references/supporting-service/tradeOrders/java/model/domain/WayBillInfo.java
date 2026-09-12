package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 物流运单信息对象。
 *
 * <p>描述单笔物流发货的运单详情。
 */
public class WayBillInfo {

    /** 物流公司名称，最大长度 128 字符 */
    private String logisticsCompanyName;

    /** 出站物流追踪号，最大长度 64 字符 */
    private String shippingOrderReferenceNo;

    /** 物流配送物理地址 */
    private Address shippingAddress;

    public WayBillInfo() {
    }

    public String getLogisticsCompanyName() {
        return logisticsCompanyName;
    }

    public void setLogisticsCompanyName(String logisticsCompanyName) {
        this.logisticsCompanyName = logisticsCompanyName;
    }

    public String getShippingOrderReferenceNo() {
        return shippingOrderReferenceNo;
    }

    public void setShippingOrderReferenceNo(String shippingOrderReferenceNo) {
        this.shippingOrderReferenceNo = shippingOrderReferenceNo;
    }

    public {basePackage}.wf.model.domain.Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress({basePackage}.wf.model.domain.Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
