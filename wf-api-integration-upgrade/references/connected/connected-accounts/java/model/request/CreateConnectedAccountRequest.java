package {basePackage}.wf.model.request;

import {basePackage}.wf.model.domain.Agreements;
import {basePackage}.wf.model.domain.Contact;
import {basePackage}.wf.model.domain.LegalEntityInfo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst create_a_connected_account 请求对象。
 *
 * <p>用于注册并入驻新关联商户到 WorldFirst，提交商户的商业信息、联系方式和法律实体数据用于 KYC/KYB 审核。
 */
public class CreateConnectedAccountRequest {

    /**
     * 集成商侧商户标识符，作为幂等键。
     * <p>最大 64 字符，同一 referenceAccountId 重复提交将返回已有账户信息而非创建新账户。
     */
    private String referenceAccountId;

    /**
     * 注册国家/地区。
     * <p>ISO 3166-1 alpha-2 代码，如 CN、GB、US。
     */
    private String registrationRegion;

    /**
     * 注册法律实体名称。
     * <p>最大 128 字符，企业填写公司全称，个人填写真实姓名。
     */
    private String registrationLegalName;

    /** 联系信息（邮箱或电话至少提供一个） */
    private Contact contact;

    /** 法律实体信息（包含实体类型、公司/个人详情、商业档案、关联方） */
    private LegalEntityInfo legalEntityInfo;

    /** 协议记录（必须同意平台条款和数据使用授权） */
    private Agreements agreements;

    public CreateConnectedAccountRequest() {
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
