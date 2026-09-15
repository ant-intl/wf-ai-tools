package {basePackage}.wf.model.request;

import {basePackage}.wf.model.domain.Amount;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst deposit_to_a_budget 请求对象。
 *
 * <p>用于从源余额账户向预算账户划拨资金（入金）。仅 ACTIVE 状态的预算账户可入金。
 * <p>属于资金变动操作，{@code requestId} 必填且需在 partnerId 内唯一。
 */
public class DepositToBudgetRequest {

    /**
     * 幂等键，最大 64 字符，需在 partnerId 内唯一。
     * <p>每次入金请求需唯一（建议 UUID）；网络重试须复用同一取值，避免重复扣款。
     */
    private String requestId;

    /**
     * 预算账户唯一标识，最大 64 字符。
     * <p>取自 create_a_budget 返回的 id。
     */
    private String id;

    /**
     * 入金来源余额账户类型（BalanceType 枚举）。
     * <p>有效值：NORMAL_BALANCE（普通余额，即电商余额，默认）、SAME_NAME_TOP_UP_BALANCE
     * （同名充值余额）。
     */
    private String balanceType;

    /**
     * 入金金额。{@code value} 须大于 0，以最小货币单位表示。
     */
    private Amount amount;

    public DepositToBudgetRequest() {
    }

    /**
     * Getter method for property <tt>requestId</tt>.
     *
     * @return property value of requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Setter method for property <tt>requestId</tt>.
     *
     * @param requestId value to be assigned to property requestId
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

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
     * Getter method for property <tt>amount</tt>.
     *
     * @return property value of amount
     */
    public Amount getAmount() {
        return amount;
    }

    /**
     * Setter method for property <tt>amount</tt>.
     *
     * @param amount value to be assigned to property amount
     */
    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
