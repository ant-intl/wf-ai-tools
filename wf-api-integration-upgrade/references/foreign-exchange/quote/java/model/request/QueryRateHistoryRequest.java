package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_rate_history 请求对象。
 *
 * <p>用于查询指定货币对在时间范围内的历史汇率数据（按小时粒度）。
 */
public class QueryRateHistoryRequest {

    /**
     * 卖出币种（ISO 4217 三字母代码）。
     */
    private String sellCurrency;

    /**
     * 买入币种（ISO 4217 三字母代码）。
     */
    private String buyCurrency;

    /**
     * 查询时间范围起始（ISO 8601 格式）。
     */
    private String startTime;

    /**
     * 查询时间范围结束（ISO 8601 格式）。
     * <p>按小时间隔返回汇率数据，直到此时间。
     */
    private String endTime;

    public QueryRateHistoryRequest() {
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
     * Getter method for property <tt>startTime</tt>.
     *
     * @return property value of startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Setter method for property <tt>startTime</tt>.
     *
     * @param startTime value to be assigned to property startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Getter method for property <tt>endTime</tt>.
     *
     * @return property value of endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Setter method for property <tt>endTime</tt>.
     *
     * @param endTime value to be assigned to property endTime
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
