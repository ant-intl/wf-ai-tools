package {basePackage}.wf.model.request;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst list_daily_balances 请求对象。
 *
 * <p>用于按日期区间查询各预算账户的日终余额与当日资金流入/流出，采用游标分页。
 * <p>{@code startDate} 与 {@code endDate} 的区间跨度不得超过 31 天。
 */
public class ListDailyBalancesRequest {

    /**
     * 查询起始日期（客户本地时区），格式 {@code yyyy-MM-dd}。
     * <p>须早于或等于 endDate。
     */
    private String startDate;

    /**
     * 查询结束日期（客户本地时区），格式 {@code yyyy-MM-dd}。
     * <p>与 startDate 的区间跨度不得超过 31 天。
     */
    private String endDate;

    /**
     * 预算账户 ID，最大 64 字符。
     * <p>不传时返回当前账户下全部预算账户的每日余额。
     */
    private String budgetId;

    /**
     * 币种过滤，每项为 ISO 4217 三字母货币代码，最多 10 项。
     * <p>不传时返回全部币种。
     */
    private List<String> currencies;

    /**
     * 分页游标。
     * <p>首次请求不传，后续传入上一次响应返回的 nextCursor 或 prevCursor。
     */
    private String cursor;

    /**
     * 每页记录数，取值范围 1-100，不传时默认 20。
     */
    private Integer limit;

    public ListDailyBalancesRequest() {
    }

    /**
     * Getter method for property <tt>startDate</tt>.
     *
     * @return property value of startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Setter method for property <tt>startDate</tt>.
     *
     * @param startDate value to be assigned to property startDate
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Getter method for property <tt>endDate</tt>.
     *
     * @return property value of endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Setter method for property <tt>endDate</tt>.
     *
     * @param endDate value to be assigned to property endDate
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Getter method for property <tt>budgetId</tt>.
     *
     * @return property value of budgetId
     */
    public String getBudgetId() {
        return budgetId;
    }

    /**
     * Setter method for property <tt>budgetId</tt>.
     *
     * @param budgetId value to be assigned to property budgetId
     */
    public void setBudgetId(String budgetId) {
        this.budgetId = budgetId;
    }

    /**
     * Getter method for property <tt>currencies</tt>.
     *
     * @return property value of currencies
     */
    public List<String> getCurrencies() {
        return currencies;
    }

    /**
     * Setter method for property <tt>currencies</tt>.
     *
     * @param currencies value to be assigned to property currencies
     */
    public void setCurrencies(List<String> currencies) {
        this.currencies = currencies;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
