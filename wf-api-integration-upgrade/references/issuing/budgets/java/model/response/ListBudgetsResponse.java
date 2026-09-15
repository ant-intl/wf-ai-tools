package {basePackage}.wf.model.response;

import java.util.List;

import {basePackage}.wf.model.domain.BudgetRecord;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst list_budgets 响应对象。
 *
 * <p>列出当前账户下全部预算账户，不分页（响应无游标字段），最多 3 条。
 */
public class ListBudgetsResponse {

    /**
     * 接口调用结果（resultStatus S/F/U、resultCode、resultMessage）。
     */
    private Result result;

    /**
     * 预算账户列表，每项字段与 query_a_budget 响应一致（不含余额）；无数据时返回空列表。
     */
    private List<BudgetRecord> items;

    public ListBudgetsResponse() {
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
     * Getter method for property <tt>items</tt>.
     *
     * @return property value of items
     */
    public List<BudgetRecord> getItems() {
        return items;
    }

    /**
     * Setter method for property <tt>items</tt>.
     *
     * @param items value to be assigned to property items
     */
    public void setItems(List<BudgetRecord> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
