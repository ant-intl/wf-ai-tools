package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.RateHistoryDetail;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_rate_history 响应对象。
 *
 * <p>包含接口调用结果、当前汇率及历史汇率列表。
 */
public class QueryRateHistoryResponse {

    /** 接口调用结果 */
    private Result result;

    /** 卖出币种（回显请求值） */
    private String sellCurrency;

    /** 买入币种（回显请求值） */
    private String buyCurrency;

    /** 最新汇率快照，当查询时间范围包含当前时间且汇率服务可提供实时报价时返回 */
    private RateHistoryDetail currentRate;

    /** 历史汇率快照列表（按小时间隔），最多 720 个元素 */
    private List<RateHistoryDetail> historicalRates;

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
     * Getter method for property <tt>currentRate</tt>.
     *
     * @return property value of currentRate
     */
    public RateHistoryDetail getCurrentRate() {
        return currentRate;
    }

    /**
     * Setter method for property <tt>currentRate</tt>.
     *
     * @param currentRate value to be assigned to property currentRate
     */
    public void setCurrentRate(RateHistoryDetail currentRate) {
        this.currentRate = currentRate;
    }

    /**
     * Getter method for property <tt>historicalRates</tt>.
     *
     * @return property value of historicalRates
     */
    public List<RateHistoryDetail> getHistoricalRates() {
        return historicalRates;
    }

    /**
     * Setter method for property <tt>historicalRates</tt>.
     *
     * @param historicalRates value to be assigned to property historicalRates
     */
    public void setHistoricalRates(List<RateHistoryDetail> historicalRates) {
        this.historicalRates = historicalRates;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
