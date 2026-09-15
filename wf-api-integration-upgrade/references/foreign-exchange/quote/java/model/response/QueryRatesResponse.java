package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.RateDetail;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_rates 响应对象。
 *
 * <p>包含接口调用结果及汇率详情列表。
 */
public class QueryRatesResponse {

    /** 接口调用结果 */
    private Result result;

    /** 结算日期（YYYY-MM-DD 格式，UTC），当请求中提供 settlementDate 时返回 */
    private String settlementDate;

    /** 汇率查询结果列表，最多 100 个元素 */
    private List<RateDetail> rateDetails;

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

    /**
     * Getter method for property <tt>rateDetails</tt>.
     *
     * @return property value of rateDetails
     */
    public List<RateDetail> getRateDetails() {
        return rateDetails;
    }

    /**
     * Setter method for property <tt>rateDetails</tt>.
     *
     * @param rateDetails value to be assigned to property rateDetails
     */
    public void setRateDetails(List<RateDetail> rateDetails) {
        this.rateDetails = rateDetails;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
