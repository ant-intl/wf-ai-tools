package {basePackage}.wf.model.request;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst inquiryBalance 请求对象
 *
 */
public class InquiryBalanceRequest {

    /** 货币列表（ISO-4217），为空则返回所有货币 */
    private List<String> currencyList;

    /**
     * 余额类型列表：NORMAL_BALANCE（默认）、SAME_NAME_TOP_UP_BALANCE、BUDGET_BALANCE
     * 为空时默认返回 NORMAL_BALANCE
     */
    private List<String> balanceTypes;

    /**
     * 预算账户 ID，当 balanceTypes 包含 BUDGET_BALANCE 时必填
     */
    private String budgetAccountId;

    /**
     * Getter method for property <tt>currencyList</tt>.
     *
     * @return property value of currencyList
     */
    public List<String> getCurrencyList() {
        return currencyList;
    }

    /**
     * Setter method for property <tt>currencyList</tt>.
     *
     * @param currencyList value to be assigned to property currencyList
     */
    public void setCurrencyList(List<String> currencyList) {
        this.currencyList = currencyList;
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
