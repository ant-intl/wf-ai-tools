package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.ConnectedAccountRecord;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst list_connected_accounts 响应对象。
 *
 * <p>包含接口调用结果、关联账户记录列表及分页游标。
 */
public class ListConnectedAccountsResponse {

    /** 接口调用结果 */
    private Result result;

    /** 关联账户记录列表（基本信息），按 createdAt 降序排列 */
    private List<ConnectedAccountRecord> items;

    /** 下一页游标，为空表示无更多数据 */
    private String nextCursor;

    /** 上一页游标，第一页为 null */
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
    public List<ConnectedAccountRecord> getItems() {
        return items;
    }

    /**
     * Setter method for property <tt>items</tt>.
     *
     * @param items value to be assigned to property items
     */
    public void setItems(List<ConnectedAccountRecord> items) {
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
