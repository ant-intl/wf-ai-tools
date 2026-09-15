package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_trade_orders 请求对象。
 *
 * <p>用于查询已提交贸易订单批次的处理状态和结果。
 */
public class QueryTradeOrdersRequest {

    /** 批次标识，使用 submit 接口返回的 id */
    private String id;

    public QueryTradeOrdersRequest() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
