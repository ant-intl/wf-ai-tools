package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.SettlementRecord;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst list_settlements 响应对象。
 *
 * <p>包含接口调用结果、结算记录列表及分页游标。
 */
public class ListSettlementsResponse {

    /** 接口调用结果 */
    private Result result;

    /** 结算记录列表 */
    private List<SettlementRecord> items;

    /** 下一页游标，有更多结果时返回 */
    private String nextCursor;

    /** 上一页游标 */
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
    public List<SettlementRecord> getItems() {
        return items;
    }

    /**
     * Setter method for property <tt>items</tt>.
     *
     * @param items value to be assigned to property items
     */
    public void setItems(List<SettlementRecord> items) {
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
