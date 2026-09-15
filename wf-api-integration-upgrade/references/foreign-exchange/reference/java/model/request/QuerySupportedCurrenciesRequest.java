package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_supported_currencies 请求对象。
 *
 * <p>用于查询 FX 交易支持的货币对列表，可按交易类型过滤。
 */
public class QuerySupportedCurrenciesRequest {

    /**
     * 按交易类型过滤，仅返回该交易类型支持的货币对。
     * <p>有效值：SPOT、UNFUNDED_SPOT、FORWARD。
     */
    private String dealType;

    public QuerySupportedCurrenciesRequest() {
    }

    /**
     * Getter method for property <tt>dealType</tt>.
     *
     * @return property value of dealType
     */
    public String getDealType() {
        return dealType;
    }

    /**
     * Setter method for property <tt>dealType</tt>.
     *
     * @param dealType value to be assigned to property dealType
     */
    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
