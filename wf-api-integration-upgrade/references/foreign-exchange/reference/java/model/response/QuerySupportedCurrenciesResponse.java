package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.CurrencyPair;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_supported_currencies 响应对象。
 *
 * <p>包含接口调用结果及支持的货币对列表。
 */
public class QuerySupportedCurrenciesResponse {

    /** 接口调用结果 */
    private Result result;

    /** 支持的货币对列表 */
    private List<CurrencyPair> currencyPairs;

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
     * Getter method for property <tt>currencyPairs</tt>.
     *
     * @return property value of currencyPairs
     */
    public List<CurrencyPair> getCurrencyPairs() {
        return currencyPairs;
    }

    /**
     * Setter method for property <tt>currencyPairs</tt>.
     *
     * @param currencyPairs value to be assigned to property currencyPairs
     */
    public void setCurrencyPairs(List<CurrencyPair> currencyPairs) {
        this.currencyPairs = currencyPairs;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
