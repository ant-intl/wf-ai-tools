package {basePackage}.wf.model.request;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_balance 请求对象。
 *
 * <p>用于查询 WF 账户余额，支持按币种和余额类型过滤。
 */
public class QueryBalanceRequest {

    /**
     * 货币代码列表（ISO-4217），最多 50 个。
     * <p>为空则返回所有币种余额。
     */
    private List<String> currencies;

    /**
     * 余额类型列表：NORMAL_BALANCE（默认）、SAME_NAME_TOP_UP_BALANCE、BUDGET_BALANCE。
     * <p>为空时默认返回 NORMAL_BALANCE。
     */
    private List<String> balanceTypes;

    /**
     * 预算账户 ID，当 balanceTypes 包含 BUDGET_BALANCE 时必填。
     */
    private String budgetAccountId;

    /**
     * Getter method for property <tt>currencies</tt>.
     *
     * @return property value of currencies
     */
    public List<String> getCurrencies() {
        return currencies;
    }

    /**
     * Setter method for property <tt>currencies</tt>.
     *
     * @param currencies value to be assigned to property currencies
     */
    public void setCurrencies(List<String> currencies) {
        this.currencies = currencies;
    }

    /**
     * Getter method for property <tt>balanceTypes</tt>.
     *
     * @return property value of balanceTypes
     */
    public List<String> getBalanceTypes() {
        return balanceTypes;
    }

    /**
     * Setter method for property <tt>balanceTypes</tt>.
     *
     * @param balanceTypes value to be assigned to property balanceTypes
     */
    public void setBalanceTypes(List<String> balanceTypes) {
        this.balanceTypes = balanceTypes;
    }

    /**
     * Getter method for property <tt>budgetAccountId</tt>.
     *
     * @return property value of budgetAccountId
     */
    public String getBudgetAccountId() {
        return budgetAccountId;
    }

    /**
     * Setter method for property <tt>budgetAccountId</tt>.
     *
     * @param budgetAccountId value to be assigned to property budgetAccountId
     */
    public void setBudgetAccountId(String budgetAccountId) {
        this.budgetAccountId = budgetAccountId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
