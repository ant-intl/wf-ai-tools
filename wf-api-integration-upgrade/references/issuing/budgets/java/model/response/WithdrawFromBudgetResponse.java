package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.Amount;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst withdraw_from_a_budget 响应对象。
 *
 * <p>回显本次出金的金额与操作状态；出金结果不确定时须用同一 {@code requestId}
 * 重试，或查询预算账户余额确认。
 */
public class WithdrawFromBudgetResponse {

    /**
     * 接口调用结果（resultStatus S/F/U、resultCode、resultMessage）。
     */
    private Result result;

    /**
     * 预算账户唯一标识，最大 64 字符，回显请求值。
     */
    private String id;

    /**
     * 出金金额，回显请求值。
     */
    private Amount amount;

    /**
     * 出金操作状态，如 SUCCESS。
     */
    private String status;

    public WithdrawFromBudgetResponse() {
    }

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

    /**
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     *
     * @param status value to be assigned to property status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
