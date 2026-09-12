package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst list_balance_history 请求对象。
 *
 * <p>用于查询 WF 账户余额变动历史记录，支持游标分页和按币种、时间范围过滤。
 */
public class ListBalanceHistoryRequest {

    /**
     * 每页记录数，取值范围 1-100，默认 20。
     */
    private Integer limit;

    /**
     * 分页游标。
     * <p>首次请求不传，后续请求传入上一次响应返回的 nextCursor。
     */
    private String cursor;

    /**
     * 币种过滤（ISO-4217），为空则返回所有币种的变动记录。
     */
    private String currency;

    /**
     * 起始时间（ISO 8601 格式，如 2024-01-01T00:00:00+08:00），筛选交易时间 >= 此值的记录。
     */
    private String fromTransactAt;

    /**
     * 结束时间（ISO 8601 格式，如 2024-03-31T23:59:59+08:00），筛选交易时间 <= 此值的记录。
     */
    private String toTransactAt;

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
     * Getter method for property <tt>currency</tt>.
     *
     * @return property value of currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Setter method for property <tt>currency</tt>.
     *
     * @param currency value to be assigned to property currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Getter method for property <tt>fromTransactAt</tt>.
     *
     * @return property value of fromTransactAt
     */
    public String getFromTransactAt() {
        return fromTransactAt;
    }

    /**
     * Setter method for property <tt>fromTransactAt</tt>.
     *
     * @param fromTransactAt value to be assigned to property fromTransactAt
     */
    public void setFromTransactAt(String fromTransactAt) {
        this.fromTransactAt = fromTransactAt;
    }

    /**
     * Getter method for property <tt>toTransactAt</tt>.
     *
     * @return property value of toTransactAt
     */
    public String getToTransactAt() {
        return toTransactAt;
    }

    /**
     * Setter method for property <tt>toTransactAt</tt>.
     *
     * @param toTransactAt value to be assigned to property toTransactAt
     */
    public void setToTransactAt(String toTransactAt) {
        this.toTransactAt = toTransactAt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
