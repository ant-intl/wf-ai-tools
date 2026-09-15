package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 关联账户记录对象。
 *
 * <p>描述关联账户的基本信息，用于 list_connected_accounts 响应的 items 列表。
 * 完整详情需调用 query_a_connected_account 接口获取。
 */
public class ConnectedAccountRecord {

    /** WorldFirst 分配的唯一商户标识符（账户 ID） */
    private String id;

    /** 集成商侧的商户标识符 */
    private String referenceAccountId;

    /** 注册的法律实体名称 */
    private String registrationLegalName;

    /** 注册国家/地区（ISO 3166-1 alpha-2 代码） */
    private String registrationRegion;

    /**
     * 账户注册状态。
     * <p>可选值：REGISTERED、PROCESSING、SUCCESS、FAILED、REJECT
     */
    private String status;

    /** 账户创建时间（ISO 8601 格式） */
    private String createdAt;

    /** 账户最后更新时间（ISO 8601 格式） */
    private String updatedAt;

    public ConnectedAccountRecord() {
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
     * Getter method for property <tt>referenceAccountId</tt>.
     *
     * @return property value of referenceAccountId
     */
    public String getReferenceAccountId() {
        return referenceAccountId;
    }

    /**
     * Setter method for property <tt>referenceAccountId</tt>.
     *
     * @param referenceAccountId value to be assigned to property referenceAccountId
     */
    public void setReferenceAccountId(String referenceAccountId) {
        this.referenceAccountId = referenceAccountId;
    }

    /**
     * Getter method for property <tt>registrationLegalName</tt>.
     *
     * @return property value of registrationLegalName
     */
    public String getRegistrationLegalName() {
        return registrationLegalName;
    }

    /**
     * Setter method for property <tt>registrationLegalName</tt>.
     *
     * @param registrationLegalName value to be assigned to property registrationLegalName
     */
    public void setRegistrationLegalName(String registrationLegalName) {
        this.registrationLegalName = registrationLegalName;
    }

    /**
     * Getter method for property <tt>registrationRegion</tt>.
     *
     * @return property value of registrationRegion
     */
    public String getRegistrationRegion() {
        return registrationRegion;
    }

    /**
     * Setter method for property <tt>registrationRegion</tt>.
     *
     * @param registrationRegion value to be assigned to property registrationRegion
     */
    public void setRegistrationRegion(String registrationRegion) {
        this.registrationRegion = registrationRegion;
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

    /**
     * Getter method for property <tt>updatedAt</tt>.
     *
     * @return property value of updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Setter method for property <tt>updatedAt</tt>.
     *
     * @param updatedAt value to be assigned to property updatedAt
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
