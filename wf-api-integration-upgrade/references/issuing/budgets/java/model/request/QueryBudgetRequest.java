package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_a_budget 请求对象。
 *
 * <p>用于按预算账户 ID 查询其详情与多币种余额。
 */
public class QueryBudgetRequest {

    /**
     * 预算账户唯一标识，最大 64 字符。
     * <p>取自 create_a_budget 返回的 id。
     */
    private String id;

    public QueryBudgetRequest() {
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
