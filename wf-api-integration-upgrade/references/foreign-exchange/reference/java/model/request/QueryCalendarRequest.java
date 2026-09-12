package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_trading_calendar 请求对象。
 *
 * <p>查询指定货币对和交易类型在给定日期范围内的可用交易日或结算日。
 */
public class QueryCalendarRequest {

    /**
     * 交易类型。
     * <p>有效值：SPOT、UNFUNDED_SPOT、FORWARD。
     */
    private String dealType;

    /**
     * 查询起始日期。
     * <p>格式：YYYY-MM-DD（UTC）。
     */
    private String startDate;

    /**
     * 查询结束日期。
     * <p>格式：YYYY-MM-DD（UTC）。
     */
    private String endDate;

    /**
     * 买入货币。
     * <p>ISO 4217 三字母代码，如 USD。
     */
    private String buyCurrency;

    /**
     * 卖出货币。
     * <p>ISO 4217 三字母代码，如 HKD。
     */
    private String sellCurrency;

    /**
     * 日历类型（可选）。
     * <p>有效值：DEAL_CALENDAR（默认）、SETTLEMENT_CALENDAR。
     */
    private String calendarType;

    public QueryCalendarRequest() {
    }

    /**
     * Getter method for property <tt>dealType</tt>.
     *
     * @return property value of dealType
     */
    public String getDealType() {
        return dealType;
    }

    /**
     * Setter method for property <tt>dealType</tt>.
     *
     * @param dealType value to be assigned to property dealType
     */
    public void setDealType(String dealType) {
        this.dealType = dealType;
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
     * Getter method for property <tt>buyCurrency</tt>.
     *
     * @return property value of buyCurrency
     */
    public String getBuyCurrency() {
        return buyCurrency;
    }

    /**
     * Setter method for property <tt>buyCurrency</tt>.
     *
     * @param buyCurrency value to be assigned to property buyCurrency
     */
    public void setBuyCurrency(String buyCurrency) {
        this.buyCurrency = buyCurrency;
    }

    /**
     * Getter method for property <tt>sellCurrency</tt>.
     *
     * @return property value of sellCurrency
     */
    public String getSellCurrency() {
        return sellCurrency;
    }

    /**
     * Setter method for property <tt>sellCurrency</tt>.
     *
     * @param sellCurrency value to be assigned to property sellCurrency
     */
    public void setSellCurrency(String sellCurrency) {
        this.sellCurrency = sellCurrency;
    }

    /**
     * Getter method for property <tt>calendarType</tt>.
     *
     * @return property value of calendarType
     */
    public String getCalendarType() {
        return calendarType;
    }

    /**
     * Setter method for property <tt>calendarType</tt>.
     *
     * @param calendarType value to be assigned to property calendarType
     */
    public void setCalendarType(String calendarType) {
        this.calendarType = calendarType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
