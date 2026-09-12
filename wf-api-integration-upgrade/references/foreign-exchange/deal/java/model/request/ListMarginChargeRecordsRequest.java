package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst list_margin_charge_records 请求对象。
 *
 * <p>用于分页查询远期交易的保证金追缴记录，支持按交易 ID、保证金场景、状态和创建时间范围过滤。
 */
public class ListMarginChargeRecordsRequest {

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
     * 按交易 ID 过滤，仅返回指定交易关联的保证金记录。
     */
    private String dealId;

    /**
     * 按保证金场景过滤（MarginScene 枚举）。
     */
    private String scene;

    /**
     * 按保证金追缴状态过滤（MarginChargeStatus 枚举）。
     */
    private String status;

    /**
     * 创建时间范围起始（ISO 8601 格式）。
     * <p>最大查询跨度为 31 天。
     */
    private String fromCreatedAt;

    /**
     * 创建时间范围结束（ISO 8601 格式）。
     */
    private String toCreatedAt;

    public ListMarginChargeRecordsRequest() {
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
     * Getter method for property <tt>scene</tt>.
     *
     * @return property value of scene
     */
    public String getScene() {
        return scene;
    }

    /**
     * Setter method for property <tt>scene</tt>.
     *
     * @param scene value to be assigned to property scene
     */
    public void setScene(String scene) {
        this.scene = scene;
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
