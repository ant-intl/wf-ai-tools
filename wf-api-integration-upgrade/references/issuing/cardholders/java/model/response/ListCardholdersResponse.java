package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.CardholderRecord;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst list_cardholders 响应对象。
 *
 * <p>包含接口调用结果、持卡人记录列表及分页游标。
 */
public class ListCardholdersResponse {

    /** 接口调用结果 */
    private Result result;

    /** 当前页持卡人记录列表，无匹配时返回空列表 */
    private List<CardholderRecord> items;

    /** 下一页游标；未返回表示无更多数据 */
    private String nextCursor;

    /** 上一页游标 */
    private String prevCursor;

    /** 匹配的持卡人总数；仅在查询条件允许时返回，不应用于分页控制 */
    private Integer totalCount;

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
    public List<CardholderRecord> getItems() {
        return items;
    }

    /**
     * Setter method for property <tt>items</tt>.
     *
     * @param items value to be assigned to property items
     */
    public void setItems(List<CardholderRecord> items) {
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

    /**
     * Getter method for property <tt>totalCount</tt>.
     *
     * @return property value of totalCount
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * Setter method for property <tt>totalCount</tt>.
     *
     * @param totalCount value to be assigned to property totalCount
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
