package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_a_cardholder 请求对象。
 *
 * <p>通过持卡人 ID 查询持卡人详情与审核状态。
 */
public class QueryCardholderRequest {

    /**
     * 持卡人唯一标识，由 create_a_cardholder 返回，最大 64 字符。
     */
    private String id;

    public QueryCardholderRequest() {
    }

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id value to be assigned to property id
     */
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
