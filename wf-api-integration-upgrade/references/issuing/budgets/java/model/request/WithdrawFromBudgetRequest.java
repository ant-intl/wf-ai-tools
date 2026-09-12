package {basePackage}.wf.model.request;

import {basePackage}.wf.model.domain.Amount;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst withdraw_from_a_budget 请求对象。
 *
 * <p>用于将预算账户资金退回余额账户（出金）。仅 ACTIVE 状态的预算账户可出金。
 * <p>属于资金变动操作，{@code requestId} 必填且需在 partnerId 内唯一。
 */
public class WithdrawFromBudgetRequest {

    /**
     * 幂等键，最大 64 字符，需在 partnerId 内唯一。
     * <p>每次出金请求需唯一（建议 UUID）；网络重试须复用同一取值，避免重复出金。
     */
    private String requestId;

    /**
     * 预算账户唯一标识，最大 64 字符。
     * <p>取自 create_a_budget 返回的 id。
     */
    private String id;

    /**
     * 出金金额。{@code value} 须大于 0，以最小货币单位表示。
     */
    private Amount amount;

    public WithdrawFromBudgetRequest() {
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
