package {basePackage}.wf.model.request;

import {basePackage}.wf.model.domain.RateCondition;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_rates 请求对象。
 *
 * <p>用于批量查询一个或多个货币对的实时汇率。
 */
public class QueryRatesRequest {

    /**
     * 汇率查询条件列表，支持最多 50 个元素。
     */
    private List<RateCondition> rateConditions;

    /**
     * 结算日期（YYYY-MM-DD 格式，UTC），固定 10 字符。
     * <p>可选，用于查询特定结算日的汇率。
     */
    private String settlementDate;

    public QueryRatesRequest() {
    }

    /**
     * Getter method for property <tt>rateConditions</tt>.
     *
     * @return property value of rateConditions
     */
    public List<RateCondition> getRateConditions() {
        return rateConditions;
    }

    /**
     * Setter method for property <tt>rateConditions</tt>.
     *
     * @param rateConditions value to be assigned to property rateConditions
     */
    public void setRateConditions(List<RateCondition> rateConditions) {
        this.rateConditions = rateConditions;
    }

    /**
     * Getter method for property <tt>settlementDate</tt>.
     *
     * @return property value of settlementDate
     */
    public String getSettlementDate() {
        return settlementDate;
    }

    /**
     * Setter method for property <tt>settlementDate</tt>.
     *
     * @param settlementDate value to be assigned to property settlementDate
     */
    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
