package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_deposit 请求对象。
 *
 * <p>用于查询指定存款记录的完整详情。
 */
public class QueryDepositRequest {

    /**
     * 存款唯一标识。
     * <p>使用 List Deposits 返回的 ID 或存款 webhook 通知中的 ID。
     */
    private String id;

    public QueryDepositRequest() {
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
