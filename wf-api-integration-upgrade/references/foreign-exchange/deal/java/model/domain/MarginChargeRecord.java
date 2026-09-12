package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 保证金追缴记录对象。
 *
 * <p>用于 list_margin_charge_records 响应的 items 列表，表示远期交易的一次保证金冻结、追加或释放记录。
 * <p>部分字段根据保证金场景与状态有条件返回。
 */
public class MarginChargeRecord {

    /**
     * 保证金追缴记录唯一标识符。
     */
    private String id;

    /**
     * 关联交易 ID。
     */
    private String dealId;

    /**
     * 保证金场景（MarginScene 枚举）。
     */
    private String scene;

    /**
     * 保证金动作类型（MarginAction 枚举）。
     */
    private String action;

    /**
     * 保证金追缴状态（MarginChargeStatus 枚举）。
     */
    private String status;

    /**
     * 取消原因（MarginCancelReason 枚举），当 status 为 CANCELLED 时返回。
     */
    private String cancelReason;

    /**
     * 保证金金额。
     */
    private Amount amount;

    /**
     * 保证金缴纳截止时间，适用时返回。
     */
    private String dueAt;

    /**
     * 强制平仓触发时间（ISO 8601 格式），触发强平时返回。
     */
    private String closeOutAt;

    /**
     * 完成时间（ISO 8601 格式），当 status 为 SUCCESS 时返回。
     */
    private String succeededAt;

    /**
     * 保证金追缴失败时间（ISO 8601 格式），追缴失败时返回。
     */
    private String failedAt;

    /**
     * 取消时间（ISO 8601 格式），当 status 为 CANCELLED 时返回。
     */
    private String cancelledAt;

    /**
     * 强制平仓完成时间（ISO 8601 格式），当 status 为 CLOSED_OUT 时返回。
     */
    private String closedOutAt;

    /**
     * 记录创建时间（ISO 8601 格式）。
     */
    private String createdAt;

    public MarginChargeRecord() {
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
     * Getter method for property <tt>action</tt>.
     *
     * @return property value of action
     */
    public String getAction() {
        return action;
    }

    /**
     * Setter method for property <tt>action</tt>.
     *
     * @param action value to be assigned to property action
     */
    public void setAction(String action) {
        this.action = action;
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
     * Getter method for property <tt>cancelReason</tt>.
     *
     * @return property value of cancelReason
     */
    public String getCancelReason() {
        return cancelReason;
    }

    /**
     * Setter method for property <tt>cancelReason</tt>.
     *
     * @param cancelReason value to be assigned to property cancelReason
     */
    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
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
     * Getter method for property <tt>dueAt</tt>.
     *
     * @return property value of dueAt
     */
    public String getDueAt() {
        return dueAt;
    }

    /**
     * Setter method for property <tt>dueAt</tt>.
     *
     * @param dueAt value to be assigned to property dueAt
     */
    public void setDueAt(String dueAt) {
        this.dueAt = dueAt;
    }

    /**
     * Getter method for property <tt>closeOutAt</tt>.
     *
     * @return property value of closeOutAt
     */
    public String getCloseOutAt() {
        return closeOutAt;
    }

    /**
     * Setter method for property <tt>closeOutAt</tt>.
     *
     * @param closeOutAt value to be assigned to property closeOutAt
     */
    public void setCloseOutAt(String closeOutAt) {
        this.closeOutAt = closeOutAt;
    }

    /**
     * Getter method for property <tt>succeededAt</tt>.
     *
     * @return property value of succeededAt
     */
    public String getSucceededAt() {
        return succeededAt;
    }

    /**
     * Setter method for property <tt>succeededAt</tt>.
     *
     * @param succeededAt value to be assigned to property succeededAt
     */
    public void setSucceededAt(String succeededAt) {
        this.succeededAt = succeededAt;
    }

    /**
     * Getter method for property <tt>failedAt</tt>.
     *
     * @return property value of failedAt
     */
    public String getFailedAt() {
        return failedAt;
    }

    /**
     * Setter method for property <tt>failedAt</tt>.
     *
     * @param failedAt value to be assigned to property failedAt
     */
    public void setFailedAt(String failedAt) {
        this.failedAt = failedAt;
    }

    /**
     * Getter method for property <tt>cancelledAt</tt>.
     *
     * @return property value of cancelledAt
     */
    public String getCancelledAt() {
        return cancelledAt;
    }

    /**
     * Setter method for property <tt>cancelledAt</tt>.
     *
     * @param cancelledAt value to be assigned to property cancelledAt
     */
    public void setCancelledAt(String cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    /**
     * Getter method for property <tt>closedOutAt</tt>.
     *
     * @return property value of closedOutAt
     */
    public String getClosedOutAt() {
        return closedOutAt;
    }

    /**
     * Setter method for property <tt>closedOutAt</tt>.
     *
     * @param closedOutAt value to be assigned to property closedOutAt
     */
    public void setClosedOutAt(String closedOutAt) {
        this.closedOutAt = closedOutAt;
    }

    /**
     * Getter method for property <tt>createdAt</tt>.
     *
     * @return property value of createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Setter method for property <tt>createdAt</tt>.
     *
     * @param createdAt value to be assigned to property createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
