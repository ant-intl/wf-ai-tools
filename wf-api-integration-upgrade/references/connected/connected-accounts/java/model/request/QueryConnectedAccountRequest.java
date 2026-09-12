package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_a_connected_account 请求对象。
 *
 * <p>用于查询指定关联账户的完整详情。
 */
public class QueryConnectedAccountRequest {

    /**
     * WorldFirst 分配的唯一商户标识符（账户 ID）。
     * <p>从 Create a Connected Account 响应或 Webhook 通知中获取。
     */
    private String id;

    public QueryConnectedAccountRequest() {
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
