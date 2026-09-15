package {basePackage}.wf.model.response;

import java.util.List;

import {basePackage}.wf.model.domain.Beneficiary;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 收款人列表查询响应对象。
 *
 * <p>用于 list_beneficiaries 接口响应，包含分页收款人列表。
 */
public class ListBeneficiariesResponse {

    /** API 调用结果 */
    private Result result;

    /** 收款人列表 */
    private List<Beneficiary> items;

    /** 下一页游标（无更多数据时为 null） */
    private String nextCursor;

    /** 上一页游标（无更多数据时为 null） */
    private String prevCursor;

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
    public List<Beneficiary> getItems() {
        return items;
    }

    /**
     * Setter method for property <tt>items</tt>.
     *
     * @param items value to be assigned to property items
     */
    public void setItems(List<Beneficiary> items) {
        this.items = items;
    }

    /**
     * Getter method for property <tt>nextCursor</tt>.
     *
     * @return property value of nextCursor
     */
    public String getNextCursor() {
        return nextCursor;
    }

    /**
     * Setter method for property <tt>nextCursor</tt>.
     *
     * @param nextCursor value to be assigned to property nextCursor
     */
    public void setNextCursor(String nextCursor) {
        this.nextCursor = nextCursor;
    }

    /**
     * Getter method for property <tt>prevCursor</tt>.
     *
     * @return property value of prevCursor
     */
    public String getPrevCursor() {
        return prevCursor;
    }

    /**
     * Setter method for property <tt>prevCursor</tt>.
     *
     * @param prevCursor value to be assigned to property prevCursor
     */
    public void setPrevCursor(String prevCursor) {
        this.prevCursor = prevCursor;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
