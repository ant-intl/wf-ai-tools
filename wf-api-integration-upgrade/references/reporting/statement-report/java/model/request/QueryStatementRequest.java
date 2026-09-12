package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_a_statement 请求对象。
 *
 * <p>用于查询指定对账单记录的完整详情。
 */
public class QueryStatementRequest {

    /**
     * 对账单唯一标识。
     * <p>使用 List Statements 返回的 ID。
     */
    private String id;

    public QueryStatementRequest() {
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
