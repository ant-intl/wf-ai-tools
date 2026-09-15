package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_balance 响应中的余额项。
 *
 * <p>每条记录对应一个币种 + 余额类型的组合，包含可用余额与冻结余额。
 */
public class BalanceItem {

    /** 货币代码（ISO-4217），如 USD、CNY */
    private String currency;

    /**
     * 余额类型：NORMAL_BALANCE（普通余额）、SAME_NAME_TOP_UP_BALANCE（同名充值余额）、BUDGET_BALANCE（预算余额）
     */
    private String balanceType;

    /** 可用余额 */
    private Amount availableAmount;

    /** 冻结余额 */
    private Amount frozenAmount;

    /**
     * Getter method for property <tt>currency</tt>.
     *
     * @return property value of currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Setter method for property <tt>currency</tt>.
     *
     * @param currency value to be assigned to property currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Getter method for property <tt>balanceType</tt>.
     *
     * @return property value of balanceType
     */
    public String getBalanceType() {
        return balanceType;
    }

    /**
     * Setter method for property <tt>balanceType</tt>.
     *
     * @param balanceType value to be assigned to property balanceType
     */
    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }

    /**
     * Getter method for property <tt>availableAmount</tt>.
     *
     * @return property value of availableAmount
     */
    public Amount getAvailableAmount() {
        return availableAmount;
    }

    /**
     * Setter method for property <tt>availableAmount</tt>.
     *
     * @param availableAmount value to be assigned to property availableAmount
     */
    public void setAvailableAmount(Amount availableAmount) {
        this.availableAmount = availableAmount;
    }

    /**
     * Getter method for property <tt>frozenAmount</tt>.
     *
     * @return property value of frozenAmount
     */
    public Amount getFrozenAmount() {
        return frozenAmount;
    }

    /**
     * Setter method for property <tt>frozenAmount</tt>.
     *
     * @param frozenAmount value to be assigned to property frozenAmount
     */
    public void setFrozenAmount(Amount frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
