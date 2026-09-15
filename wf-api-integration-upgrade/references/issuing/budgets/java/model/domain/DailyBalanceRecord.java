package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 预算账户每日余额记录对象。
 *
 * <p>用于 list_daily_balances 响应的 dailyBalances 列表，表示某个预算账户在某一日的
 * 日终余额与当日资金流入/流出汇总。
 *
 * <p>{@code endOfDayBalance}、{@code dailyFundInAmount}、{@code dailyFundOutAmount}
 * 均为通用 {@link Amount} 对象，金额以最小货币单位表示。
 */
public class DailyBalanceRecord {

    /**
     * 预算账户唯一标识，最大 64 字符。
     */
    private String budgetId;

    /**
     * 余额日期，格式 yyyy-MM-dd（客户本地时区）。
     */
    private String date;

    /**
     * 日终余额合计。
     */
    private Amount endOfDayBalance;

    /**
     * 当日资金流入合计。
     */
    private Amount dailyFundInAmount;

    /**
     * 当日资金流出合计。
     */
    private Amount dailyFundOutAmount;

    public DailyBalanceRecord() {
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
     * Getter method for property <tt>date</tt>.
     *
     * @return property value of date
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter method for property <tt>date</tt>.
     *
     * @param date value to be assigned to property date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Getter method for property <tt>endOfDayBalance</tt>.
     *
     * @return property value of endOfDayBalance
     */
    public Amount getEndOfDayBalance() {
        return endOfDayBalance;
    }

    /**
     * Setter method for property <tt>endOfDayBalance</tt>.
     *
     * @param endOfDayBalance value to be assigned to property endOfDayBalance
     */
    public void setEndOfDayBalance(Amount endOfDayBalance) {
        this.endOfDayBalance = endOfDayBalance;
    }

    /**
     * Getter method for property <tt>dailyFundInAmount</tt>.
     *
     * @return property value of dailyFundInAmount
     */
    public Amount getDailyFundInAmount() {
        return dailyFundInAmount;
    }

    /**
     * Setter method for property <tt>dailyFundInAmount</tt>.
     *
     * @param dailyFundInAmount value to be assigned to property dailyFundInAmount
     */
    public void setDailyFundInAmount(Amount dailyFundInAmount) {
        this.dailyFundInAmount = dailyFundInAmount;
    }

    /**
     * Getter method for property <tt>dailyFundOutAmount</tt>.
     *
     * @return property value of dailyFundOutAmount
     */
    public Amount getDailyFundOutAmount() {
        return dailyFundOutAmount;
    }

    /**
     * Setter method for property <tt>dailyFundOutAmount</tt>.
     *
     * @param dailyFundOutAmount value to be assigned to property dailyFundOutAmount
     */
    public void setDailyFundOutAmount(Amount dailyFundOutAmount) {
        this.dailyFundOutAmount = dailyFundOutAmount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
