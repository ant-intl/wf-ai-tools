package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst list_connected_accounts 请求对象。
 *
 * <p>用于分页查询关联账户列表，支持按状态和创建时间范围过滤。
 */
public class ListConnectedAccountsRequest {

    /**
     * 每页记录数，取值范围 1-20。
     */
    private Integer limit;

    /**
     * 分页游标。
     * <p>首次请求不传，后续请求传入上一次响应返回的 nextCursor 或 prevCursor。
     */
    private String cursor;

    /**
     * 按账户状态过滤。
     * <p>可选值：REGISTERED、PROCESSING、SUCCESS、FAILED、REJECT
     * <p>如果省略，返回所有状态的账户。
     */
    private String status;

    /**
     * 创建时间范围起始（ISO 8601 格式，包含）。
     * <p>必须与 toCreatedAt 配合使用。
     */
    private String fromCreatedAt;

    /**
     * 创建时间范围结束（ISO 8601 格式，包含）。
     * <p>必须与 fromCreatedAt 配合使用。
     */
    private String toCreatedAt;

    public ListConnectedAccountsRequest() {
    }

    /**
     * Getter method for property <tt>limit</tt>.
     *
     * @return property value of limit
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * Setter method for property <tt>limit</tt>.
     *
     * @param limit value to be assigned to property limit
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * Getter method for property <tt>cursor</tt>.
     *
     * @return property value of cursor
     */
    public String getCursor() {
        return cursor;
    }

    /**
     * Setter method for property <tt>cursor</tt>.
     *
     * @param cursor value to be assigned to property cursor
     */
    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    /**
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     *
     * @param status value to be assigned to property status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Getter method for property <tt>fromCreatedAt</tt>.
     *
     * @return property value of fromCreatedAt
     */
    public String getFromCreatedAt() {
        return fromCreatedAt;
    }

    /**
     * Setter method for property <tt>fromCreatedAt</tt>.
     *
     * @param fromCreatedAt value to be assigned to property fromCreatedAt
     */
    public void setFromCreatedAt(String fromCreatedAt) {
        this.fromCreatedAt = fromCreatedAt;
    }

    /**
     * Getter method for property <tt>toCreatedAt</tt>.
     *
     * @return property value of toCreatedAt
     */
    public String getToCreatedAt() {
        return toCreatedAt;
    }

    /**
     * Setter method for property <tt>toCreatedAt</tt>.
     *
     * @param toCreatedAt value to be assigned to property toCreatedAt
     */
    public void setToCreatedAt(String toCreatedAt) {
        this.toCreatedAt = toCreatedAt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
