package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 法律实体信息对象。
 *
 * <p>描述关联账户注册时提交的法律实体信息，包括实体类型、公司详情、商业档案和关联方等。
 * 对于 SDK 注册但尚未提交 KYC 的账户，此对象可能为 null。
 */
public class LegalEntityInfo {

    /**
     * 法律实体类型。
     * <p>可选值：INDIVIDUAL（个人）、COMPANY（企业/公司）
     */
    private String legalEntityType;

    /** 公司法律实体详情，当 legalEntityType 为 COMPANY 时返回 */
    private Company company;

    /** 个人法律实体详情，当 legalEntityType 为 INDIVIDUAL 时 */
    private Individual individual;

    /** 商业档案，包括主要业务活动和经营地址 */
    private BusinessProfile businessProfile;

    /** 关联方列表（UBO、董事、法定代表人等） */
    private java.util.List<RelatedParty> relatedParties;

    public LegalEntityInfo() {
    }

    /**
     * Getter method for property <tt>legalEntityType</tt>.
     *
     * @return property value of legalEntityType
     */
    public String getLegalEntityType() {
        return legalEntityType;
    }

    /**
     * Setter method for property <tt>legalEntityType</tt>.
     *
     * @param legalEntityType value to be assigned to property legalEntityType
     */
    public void setLegalEntityType(String legalEntityType) {
        this.legalEntityType = legalEntityType;
    }

    /**
     * Getter method for property <tt>company</tt>.
     *
     * @return property value of company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Setter method for property <tt>company</tt>.
     *
     * @param company value to be assigned to property company
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Getter method for property <tt>individual</tt>.
     *
     * @return property value of individual
     */
    public Individual getIndividual() {
        return individual;
    }

    /**
     * Setter method for property <tt>individual</tt>.
     *
     * @param individual value to be assigned to property individual
     */
    public void setIndividual(Individual individual) {
        this.individual = individual;
    }

    /**
     * Getter method for property <tt>businessProfile</tt>.
     *
     * @return property value of businessProfile
     */
    public BusinessProfile getBusinessProfile() {
        return businessProfile;
    }

    /**
     * Setter method for property <tt>businessProfile</tt>.
     *
     * @param businessProfile value to be assigned to property businessProfile
     */
    public void setBusinessProfile(BusinessProfile businessProfile) {
        this.businessProfile = businessProfile;
    }

    /**
     * Getter method for property <tt>relatedParties</tt>.
     *
     * @return property value of relatedParties
     */
    public java.util.List<RelatedParty> getRelatedParties() {
        return relatedParties;
    }

    /**
     * Setter method for property <tt>relatedParties</tt>.
     *
     * @param relatedParties value to be assigned to property relatedParties
     */
    public void setRelatedParties(java.util.List<RelatedParty> relatedParties) {
        this.relatedParties = relatedParties;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
