package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 关联方对象。
 *
 * <p>描述关联账户的关联方信息，包括 UBO（最终受益人）、董事、法定代表人等。
 */
public class RelatedParty {

    /**
     * 关联方关系类型。
     * <p>可选值：SHAREHOLDER、BOARD_MEMBER、BENEFICIAL_OWNER、LEGAL_REPRESENTATIVE、
     * AUTHORIZED_SIGNATORY、DIRECTOR、COMMON、AFFILIATION_COMPANY、ENTITY_SHAREHOLDER
     */
    private String relationship;

    /** 实体类型：INDIVIDUAL / COMPANY */
    private String legalEntityType;

    /** 个人详情，当 legalEntityType 为 INDIVIDUAL 时 */
    private Individual individual;

    /** 公司详情，当 legalEntityType 为 COMPANY 时 */
    private Company company;

    /** 持股比例，小数格式如 0.25 = 25% */
    private String shareHoldingRatio;

    /** 资金来源列表 */
    private java.util.List<String> fundsSources;

    /** 资金去向列表 */
    private java.util.List<String> fundsDestinations;

    /** 扩展信息，最大4096字符 */
    private String additionalInfo;

    public RelatedParty() {
    }

    /**
     * Getter method for property <tt>relationship</tt>.
     *
     * @return property value of relationship
     */
    public String getRelationship() {
        return relationship;
    }

    /**
     * Setter method for property <tt>relationship</tt>.
     *
     * @param relationship value to be assigned to property relationship
     */
    public void setRelationship(String relationship) {
        this.relationship = relationship;
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
     * Getter method for property <tt>shareHoldingRatio</tt>.
     *
     * @return property value of shareHoldingRatio
     */
    public String getShareHoldingRatio() {
        return shareHoldingRatio;
    }

    /**
     * Setter method for property <tt>shareHoldingRatio</tt>.
     *
     * @param shareHoldingRatio value to be assigned to property shareHoldingRatio
     */
    public void setShareHoldingRatio(String shareHoldingRatio) {
        this.shareHoldingRatio = shareHoldingRatio;
    }

    /**
     * Getter method for property <tt>fundsSources</tt>.
     *
     * @return property value of fundsSources
     */
    public java.util.List<String> getFundsSources() {
        return fundsSources;
    }

    /**
     * Setter method for property <tt>fundsSources</tt>.
     *
     * @param fundsSources value to be assigned to property fundsSources
     */
    public void setFundsSources(java.util.List<String> fundsSources) {
        this.fundsSources = fundsSources;
    }

    /**
     * Getter method for property <tt>fundsDestinations</tt>.
     *
     * @return property value of fundsDestinations
     */
    public java.util.List<String> getFundsDestinations() {
        return fundsDestinations;
    }

    /**
     * Setter method for property <tt>fundsDestinations</tt>.
     *
     * @param fundsDestinations value to be assigned to property fundsDestinations
     */
    public void setFundsDestinations(java.util.List<String> fundsDestinations) {
        this.fundsDestinations = fundsDestinations;
    }

    /**
     * Getter method for property <tt>additionalInfo</tt>.
     *
     * @return property value of additionalInfo
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * Setter method for property <tt>additionalInfo</tt>.
     *
     * @param additionalInfo value to be assigned to property additionalInfo
     */
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
