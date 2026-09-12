package {basePackage}.wf.model.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 物流发货详情对象。
 *
 * <p>描述贸易订单的物流信息，当 tradeCategory 为 GOODS 时必填。
 */
public class Shipping {

    /** 运单详情列表，最多 50 条 */
    private List<WayBillInfo> wayBillInfos;

    public Shipping() {
    }

    public List<WayBillInfo> getWayBillInfos() {
        return wayBillInfos;
    }

    public void setWayBillInfos(List<WayBillInfo> wayBillInfos) {
        this.wayBillInfos = wayBillInfos;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
