package {basePackage}.wf.model.response;

import java.util.List;

import {basePackage}.wf.model.domain.DailyBalanceRecord;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst list_daily_balances 响应对象。
 *
 * <p>按日期区间返回各预算账户的日终余额与当日资金流入/流出，采用游标分页；
 * 响应未返回 {@code nextCursor} 即表示已无更多数据。
 */
public class ListDailyBalancesResponse {

    /**
     * 接口调用结果（resultStatus S/F/U、resultCode、resultMessage）。
     */
    private Result result;

    /**
     * 每日余额列表，无数据时返回空列表。
     * <p>注意本接口列表字段名为 dailyBalances，而非 items。
     */
    private List<DailyBalanceRecord> dailyBalances;

    /**
     * 下一页游标；未返回表示已到最后一页。
     */
    private String nextCursor;

    /**
     * 上一页游标；未返回表示当前为第一页。
     */
    private String prevCursor;

    public ListDailyBalancesResponse() {
    }

    /**
     * Getter method for property <tt>result</tt>.
     *
     * @return property value of result
     */
    public Result getResult() {
        return result;
    }

    /**
     * Setter method for property <tt>result</tt>.
     *
     * @param result value to be assigned to property result
     */
    public void setResult(Result result) {
        this.result = result;
    }

    /**
     * Getter method for property <tt>dailyBalances</tt>.
     *
     * @return property value of dailyBalances
     */
    public List<DailyBalanceRecord> getDailyBalances() {
        return dailyBalances;
    }

    /**
     * Setter method for property <tt>dailyBalances</tt>.
     *
     * @param dailyBalances value to be assigned to property dailyBalances
     */
    public void setDailyBalances(List<DailyBalanceRecord> dailyBalances) {
        this.dailyBalances = dailyBalances;
    }

    /**
     * Getter method for property <tt>nextCursor</tt>.
     *
     * @return property value of nextCursor
     */
    public String getNextCursor() {
        return nextCursor;
    }

    /**
     * Setter method for property <tt>nextCursor</tt>.
     *
     * @param nextCursor value to be assigned to property nextCursor
     */
    public void setNextCursor(String nextCursor) {
        this.nextCursor = nextCursor;
    }

    /**
     * Getter method for property <tt>prevCursor</tt>.
     *
     * @return property value of prevCursor
     */
    public String getPrevCursor() {
        return prevCursor;
    }

    /**
     * Setter method for property <tt>prevCursor</tt>.
     *
     * @param prevCursor value to be assigned to property prevCursor
     */
    public void setPrevCursor(String prevCursor) {
        this.prevCursor = prevCursor;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
