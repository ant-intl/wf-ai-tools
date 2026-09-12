package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst list_balance_history 响应中的余额变动记录。
 *
 * <p>每条记录描述一次余额变动事件，包含变动金额、变动后余额及交易时间。
 */
public class BalanceHistoryRecord {

    /** 记录唯一标识 */
    private String id;

    /** 货币代码（ISO-4217），如 USD、CNY */
    private String currency;

    /** 变动金额（正数为入账，负数为出账） */
    private Amount changeAmount;

    /** 变动后的账户余额 */
    private Amount balanceAmount;

    /** 变动描述/备注 */
    private String description;

    /** 交易时间（ISO 8601 格式，如 2024-01-01T00:00:00+08:00） */
    private String transactedAt;

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id value to be assigned to property id
     */
    public void setId(String id) {
        this.id = id;
    }

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
     * Getter method for property <tt>changeAmount</tt>.
     *
     * @return property value of changeAmount
     */
    public Amount getChangeAmount() {
        return changeAmount;
    }

    /**
     * Setter method for property <tt>changeAmount</tt>.
     *
     * @param changeAmount value to be assigned to property changeAmount
     */
    public void setChangeAmount(Amount changeAmount) {
        this.changeAmount = changeAmount;
    }

    /**
     * Getter method for property <tt>balanceAmount</tt>.
     *
     * @return property value of balanceAmount
     */
    public Amount getBalanceAmount() {
        return balanceAmount;
    }

    /**
     * Setter method for property <tt>balanceAmount</tt>.
     *
     * @param balanceAmount value to be assigned to property balanceAmount
     */
    public void setBalanceAmount(Amount balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    /**
     * Getter method for property <tt>description</tt>.
     *
     * @return property value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter method for property <tt>description</tt>.
     *
     * @param description value to be assigned to property description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter method for property <tt>transactedAt</tt>.
     *
     * @return property value of transactedAt
     */
    public String getTransactedAt() {
        return transactedAt;
    }

    /**
     * Setter method for property <tt>transactedAt</tt>.
     *
     * @param transactedAt value to be assigned to property transactedAt
     */
    public void setTransactedAt(String transactedAt) {
        this.transactedAt = transactedAt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
