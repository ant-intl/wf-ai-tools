package {basePackage}.wf.model.request;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst list_statements 请求对象。
 *
 * <p>用于分页查询对账单记录列表，支持按交易类型、币种、余额类型和交易时间范围过滤。
 */
public class ListStatementsRequest {

    /**
     * 每页记录数，取值范围 1-100，默认 20。
     */
    private Integer limit;

    /**
     * 分页游标。
     * <p>首次请求不传，后续请求传入上一次响应返回的 nextCursor 或 prevCursor。
     */
    private String cursor;

    /**
     * 交易时间范围起始（ISO 8601 格式，包含）。
     * <p>必须与 toTransactAt 配合使用，最大时间跨度 100 天。
     */
    private String fromTransactAt;

    /**
     * 交易时间范围结束（ISO 8601 格式，不包含）。
     * <p>必须与 fromTransactAt 配合使用。
     */
    private String toTransactAt;

    /**
     * 按交易类型过滤（OR 逻辑）。
     * <p>省略返回所有类型。
     * <p>可选值：TRANSFER、TRANSFER_REFUND、WITHDRAWAL、WITHDRAWAL_REFUND、
     * CONVERSION、CONVERSION_DEAL、CHARGE、CHARGE_REFUND、DEDUCTION、
     * FUND_COLLECTION、COLLECTION、COLLECTION_REFUND、PAYMENT、CASH_BACK、TOP_UP
     */
    private List<String> transactionTypes;

    /**
     * 按币种过滤（ISO-4217 三字母代码）。
     * <p>省略返回所有币种。
     */
    private List<String> currencies;

    /**
     * 按余额类型过滤。
     * <p>省略默认为 NORMAL_BALANCE。
     * <p>可选值：NORMAL_BALANCE、SAME_NAME_TOP_UP_BALANCE、BUDGET_BALANCE
     */
    private List<String> balanceTypes;

    /**
     * 预算账户 ID 列表。
     * <p>当 balanceTypes 包含 BUDGET_BALANCE 时必填。
     */
    private List<String> budgetAccountIds;

    /**
     * 模糊搜索关键词，匹配对方名称和备注。
     */
    private String keyword;

    public ListStatementsRequest() {
    }

    /**
     * Getter method for property <tt>limit</tt>.
     *
     * @return property value of limit
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * Setter method for property <tt>limit</tt>.
     *
     * @param limit value to be assigned to property limit
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * Getter method for property <tt>cursor</tt>.
     *
     * @return property value of cursor
     */
    public String getCursor() {
        return cursor;
    }

    /**
     * Setter method for property <tt>cursor</tt>.
     *
     * @param cursor value to be assigned to property cursor
     */
    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    /**
     * Getter method for property <tt>fromTransactAt</tt>.
     *
     * @return property value of fromTransactAt
     */
    public String getFromTransactAt() {
        return fromTransactAt;
    }

    /**
     * Setter method for property <tt>fromTransactAt</tt>.
     *
     * @param fromTransactAt value to be assigned to property fromTransactAt
     */
    public void setFromTransactAt(String fromTransactAt) {
        this.fromTransactAt = fromTransactAt;
    }

    /**
     * Getter method for property <tt>toTransactAt</tt>.
     *
     * @return property value of toTransactAt
     */
    public String getToTransactAt() {
        return toTransactAt;
    }

    /**
     * Setter method for property <tt>toTransactAt</tt>.
     *
     * @param toTransactAt value to be assigned to property toTransactAt
     */
    public void setToTransactAt(String toTransactAt) {
        this.toTransactAt = toTransactAt;
    }

    /**
     * Getter method for property <tt>transactionTypes</tt>.
     *
     * @return property value of transactionTypes
     */
    public List<String> getTransactionTypes() {
        return transactionTypes;
    }

    /**
     * Setter method for property <tt>transactionTypes</tt>.
     *
     * @param transactionTypes value to be assigned to property transactionTypes
     */
    public void setTransactionTypes(List<String> transactionTypes) {
        this.transactionTypes = transactionTypes;
    }

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
     * Getter method for property <tt>budgetAccountIds</tt>.
     *
     * @return property value of budgetAccountIds
     */
    public List<String> getBudgetAccountIds() {
        return budgetAccountIds;
    }

    /**
     * Setter method for property <tt>budgetAccountIds</tt>.
     *
     * @param budgetAccountIds value to be assigned to property budgetAccountIds
     */
    public void setBudgetAccountIds(List<String> budgetAccountIds) {
        this.budgetAccountIds = budgetAccountIds;
    }

    /**
     * Getter method for property <tt>keyword</tt>.
     *
     * @return property value of keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * Setter method for property <tt>keyword</tt>.
     *
     * @param keyword value to be assigned to property keyword
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
