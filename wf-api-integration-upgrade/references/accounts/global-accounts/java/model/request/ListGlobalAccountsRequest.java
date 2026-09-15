package {basePackage}.wf.model.request;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import {basePackage}.wf.model.domain.RequiredFeature;

/**
 * WorldFirst list_global_accounts 请求对象。
 *
 * <p>用于分页查询全局账户列表，支持按地区、状态、收款能力和创建时间范围过滤。
 */
public class ListGlobalAccountsRequest {

    /**
     * 按银行地区过滤（ISO 3166），如 US、GB、HK。
     * <p>详见 WF 官方文档 Data Types。
     */
    private String bankRegion;

    /** 按账户昵称过滤 */
    private String nickName;

    /**
     * 按账户状态过滤。
     * <p>详见 WF 官方文档 GlobalAccountStatus 枚举。
     */
    private String status;

    /**
     * 按已开通收款能力过滤，每个元素指定币种（和可选的支付方式）。
     */
    private List<RequiredFeature> supportedFeatures;

    /**
     * 按创建时间起始过滤（含，ISO 8601 格式，如 2024-01-01T00:00:00+08:00）。
     * <p>详见 WF 官方文档 Data Types。
     */
    private String fromCreatedAt;

    /**
     * 按创建时间截止过滤（含，ISO 8601 格式，如 2024-12-31T23:59:59+08:00）。
     * <p>详见 WF 官方文档 Data Types。
     */
    private String toCreatedAt;

    /**
     * 每页记录数，取值范围 1-20。
     */
    private Integer limit;

    /**
     * 分页游标。
     * <p>首次请求不传，后续请求传入上次响应返回的 nextCursor 或 prevCursor。
     */
    private String cursor;

    /**
     * Getter method for property <tt>bankRegion</tt>.
     *
     * @return property value of bankRegion
     */
    public String getBankRegion() {
        return bankRegion;
    }

    /**
     * Setter method for property <tt>bankRegion</tt>.
     *
     * @param bankRegion value to be assigned to property bankRegion
     */
    public void setBankRegion(String bankRegion) {
        this.bankRegion = bankRegion;
    }

    /**
     * Getter method for property <tt>nickName</tt>.
     *
     * @return property value of nickName
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Setter method for property <tt>nickName</tt>.
     *
     * @param nickName value to be assigned to property nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
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
     * Getter method for property <tt>supportedFeatures</tt>.
     *
     * @return property value of supportedFeatures
     */
    public List<RequiredFeature> getSupportedFeatures() {
        return supportedFeatures;
    }

    /**
     * Setter method for property <tt>supportedFeatures</tt>.
     *
     * @param supportedFeatures value to be assigned to property supportedFeatures
     */
    public void setSupportedFeatures(List<RequiredFeature> supportedFeatures) {
        this.supportedFeatures = supportedFeatures;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
