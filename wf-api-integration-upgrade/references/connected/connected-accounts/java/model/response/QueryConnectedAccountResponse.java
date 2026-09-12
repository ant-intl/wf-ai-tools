package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.Agreements;
import {basePackage}.wf.model.domain.AuditDetail;
import {basePackage}.wf.model.domain.Contact;
import {basePackage}.wf.model.domain.LegalEntityInfo;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_a_connected_account 响应对象。
 *
 * <p>包含接口调用结果及完整的关联账户详情。
 */
public class QueryConnectedAccountResponse {

    /** 接口调用结果 */
    private Result result;

    /** WorldFirst 分配的唯一商户标识符（账户 ID） */
    private String id;

    /** 集成商侧的商户标识符 */
    private String referenceAccountId;

    /**
     * 账户注册状态。
     * <p>可选值：REGISTERED、PROCESSING、SUCCESS、FAILED、REJECT
     */
    private String status;

    /** 注册国家/地区（ISO 3166-1 alpha-2 代码） */
    private String registrationRegion;

    /** 注册的法律实体名称 */
    private String registrationLegalName;

    /** 法律实体信息，SDK 注册但尚未提交 KYC 的账户可能为 null */
    private LegalEntityInfo legalEntityInfo;

    /** 联系信息 */
    private Contact contact;

    /** 协议记录 */
    private Agreements agreements;

    /**
     * 审核原因代码。
     * <p>仅在 status 为 REJECT 时返回，用于确定需要补充的材料。
     */
    private String reasonCode;

    /**
     * 审核拒绝详情列表。
     * <p>仅在 status 为 REJECT 时返回，最多 50 条。
     */
    private List<AuditDetail> auditDetails;

    /** 账户创建时间（ISO 8601 格式） */
    private String createdAt;

    /** 账户最后更新时间（ISO 8601 格式） */
    private String updatedAt;

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
     * Getter method for property <tt>legalEntityInfo</tt>.
     *
     * @return property value of legalEntityInfo
     */
    public LegalEntityInfo getLegalEntityInfo() {
        return legalEntityInfo;
    }

    /**
     * Setter method for property <tt>legalEntityInfo</tt>.
     *
     * @param legalEntityInfo value to be assigned to property legalEntityInfo
     */
    public void setLegalEntityInfo(LegalEntityInfo legalEntityInfo) {
        this.legalEntityInfo = legalEntityInfo;
    }

    /**
     * Getter method for property <tt>contact</tt>.
     *
     * @return property value of contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Setter method for property <tt>contact</tt>.
     *
     * @param contact value to be assigned to property contact
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * Getter method for property <tt>agreements</tt>.
     *
     * @return property value of agreements
     */
    public Agreements getAgreements() {
        return agreements;
    }

    /**
     * Setter method for property <tt>agreements</tt>.
     *
     * @param agreements value to be assigned to property agreements
     */
    public void setAgreements(Agreements agreements) {
        this.agreements = agreements;
    }

    /**
     * Getter method for property <tt>reasonCode</tt>.
     *
     * @return property value of reasonCode
     */
    public String getReasonCode() {
        return reasonCode;
    }

    /**
     * Setter method for property <tt>reasonCode</tt>.
     *
     * @param reasonCode value to be assigned to property reasonCode
     */
    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    /**
     * Getter method for property <tt>auditDetails</tt>.
     *
     * @return property value of auditDetails
     */
    public List<AuditDetail> getAuditDetails() {
        return auditDetails;
    }

    /**
     * Setter method for property <tt>auditDetails</tt>.
     *
     * @param auditDetails value to be assigned to property auditDetails
     */
    public void setAuditDetails(List<AuditDetail> auditDetails) {
        this.auditDetails = auditDetails;
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
