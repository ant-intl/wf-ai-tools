package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst list_settlements 请求对象。
 *
 * <p>用于分页查询 FX 结算记录列表，支持按交易 ID、卖出币种、买入币种、结算状态和创建时间范围过滤。
 */
public class ListSettlementsRequest {

    /**
     * 每页记录数，取值范围 1-100。
     * <p>不传时默认 20。
     */
    private Integer limit;

    /**
     * 分页游标。
     * <p>首次请求不传，后续请求传入上一次响应返回的 nextCursor 或 prevCursor。
     */
    private String cursor;

    /**
     * 按关联交易 ID 过滤。
     */
    private String dealId;

    /**
     * 按卖出币种过滤（ISO 4217 三字母代码）。
     */
    private String sellCurrency;

    /**
     * 按买入币种过滤（ISO 4217 三字母代码）。
     */
    private String buyCurrency;

    /**
     * 按结算状态过滤（SettlementStatus 枚举）。
     */
    private String status;

    /**
     * 创建时间范围起始（ISO 8601 格式）。
     * <p>最大查询跨度为 30 天。
     */
    private String fromCreatedAt;

    /**
     * 创建时间范围结束（ISO 8601 格式）。
     */
    private String toCreatedAt;

    public ListSettlementsRequest() {
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
     * Getter method for property <tt>dealId</tt>.
     *
     * @return property value of dealId
     */
    public String getDealId() {
        return dealId;
    }

    /**
     * Setter method for property <tt>dealId</tt>.
     *
     * @param dealId value to be assigned to property dealId
     */
    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    /**
     * Getter method for property <tt>sellCurrency</tt>.
     *
     * @return property value of sellCurrency
     */
    public String getSellCurrency() {
        return sellCurrency;
    }

    /**
     * Setter method for property <tt>sellCurrency</tt>.
     *
     * @param sellCurrency value to be assigned to property sellCurrency
     */
    public void setSellCurrency(String sellCurrency) {
        this.sellCurrency = sellCurrency;
    }

    /**
     * Getter method for property <tt>buyCurrency</tt>.
     *
     * @return property value of buyCurrency
     */
    public String getBuyCurrency() {
        return buyCurrency;
    }

    /**
     * Setter method for property <tt>buyCurrency</tt>.
     *
     * @param buyCurrency value to be assigned to property buyCurrency
     */
    public void setBuyCurrency(String buyCurrency) {
        this.buyCurrency = buyCurrency;
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

    /**
     * Getter method for property <tt>fromCreatedAt</tt>.
     *
     * @return property value of fromCreatedAt
     */
    public String getFromCreatedAt() {
        return fromCreatedAt;
    }

    /**
     * Setter method for property <tt>fromCreatedAt</tt>.
     *
     * @param fromCreatedAt value to be assigned to property fromCreatedAt
     */
    public void setFromCreatedAt(String fromCreatedAt) {
        this.fromCreatedAt = fromCreatedAt;
    }

    /**
     * Getter method for property <tt>toCreatedAt</tt>.
     *
     * @return property value of toCreatedAt
     */
    public String getToCreatedAt() {
        return toCreatedAt;
    }

    /**
     * Setter method for property <tt>toCreatedAt</tt>.
     *
     * @param toCreatedAt value to be assigned to property toCreatedAt
     */
    public void setToCreatedAt(String toCreatedAt) {
        this.toCreatedAt = toCreatedAt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
