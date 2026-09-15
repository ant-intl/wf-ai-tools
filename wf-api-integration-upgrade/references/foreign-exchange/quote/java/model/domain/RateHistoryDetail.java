package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * WorldFirst 历史汇率详情对象。
 *
 * <p>用于 query_rate_history 响应，包含单个时间点的汇率信息。
 */
public class RateHistoryDetail {

    /**
     * 客户端汇率，8 位小数。
     */
    private BigDecimal clientRate;

    /**
     * 市场参考汇率，8 位小数。
     * <p>仅当汇率服务提供市场汇率时返回。
     */
    private BigDecimal marketRate;

    /**
     * 汇率快照时间戳（ISO 8601 格式）。
     */
    private String time;

    public RateHistoryDetail() {
    }

    /**
     * Getter method for property <tt>clientRate</tt>.
     *
     * @return property value of clientRate
     */
    public BigDecimal getClientRate() {
        return clientRate;
    }

    /**
     * Setter method for property <tt>clientRate</tt>.
     *
     * @param clientRate value to be assigned to property clientRate
     */
    public void setClientRate(BigDecimal clientRate) {
        this.clientRate = clientRate;
    }

    /**
     * Getter method for property <tt>marketRate</tt>.
     *
     * @return property value of marketRate
     */
    public BigDecimal getMarketRate() {
        return marketRate;
    }

    /**
     * Setter method for property <tt>marketRate</tt>.
     *
     * @param marketRate value to be assigned to property marketRate
     */
    public void setMarketRate(BigDecimal marketRate) {
        this.marketRate = marketRate;
    }

    /**
     * Getter method for property <tt>time</tt>.
     *
     * @return property value of time
     */
    public String getTime() {
        return time;
    }

    /**
     * Setter method for property <tt>time</tt>.
     *
     * @param time value to be assigned to property time
     */
    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
