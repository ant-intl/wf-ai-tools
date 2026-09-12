package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.BalanceItem;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_balance 响应对象。
 *
 * <p>包含接口调用结果及余额列表。
 */
public class QueryBalanceResponse {

    /** 接口调用结果 */
    private Result result;

    /** 余额列表 */
    private List<BalanceItem> balances;

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
     * Getter method for property <tt>balances</tt>.
     *
     * @return property value of balances
     */
    public List<BalanceItem> getBalances() {
        return balances;
    }

    /**
     * Setter method for property <tt>balances</tt>.
     *
     * @param balances value to be assigned to property balances
     */
    public void setBalances(List<BalanceItem> balances) {
        this.balances = balances;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
