package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 公司实体详情对象。
 *
 * <p>描述公司的注册信息，包括公司类型、注册号码、注册日期等。
 */
public class Company {

    /**
     * 公司类型。
     * <p>可选值：SOLE_PROPRIETORSHIP_ENTERPRISE、PUBLIC_LIMITED、PUBLIC_LIMITED_SHARES、
     * PARTNERSHIP、PUBLIC_LIMITED_FOREIGN、PUBLIC_LIMITED_STATE、GOVERNMENT、
     * PRIVATE_LIMITED、PUBLIC_LIMITED_PUBLIC、PUBLIC_LIMITED_LISTED、
     * PUBLIC_LIMITED_CONTROL、PUBLIC_LIMITED_GROUP
     */
    private String companyType;

    /** 公司注册日期（ISO 8601 格式） */
    private String registrationDate;

    /** 公司官方注册名称，最大128字符 */
    private String legalName;

    /** 公司注册类型，RegistrationType 枚举 */
    private String registrationType;

    /** 公司注册号码，最大64字符 */
    private String registrationNo;

    /** 公司注册地址 */
    private Address registrationAddress;

    /** 英文注册地址 */
    private Address englishRegistrationAddress;

    /** 证件信息列表 */
    private java.util.List<Certificate> certificates;

    /** 附件列表 */
    private java.util.List<Attachment> attachments;

    /** 税务识别号，最大64字符 */
    private String taxNo;

    /** VAT 号码，最大64字符 */
    private String vatNo;

    /** 是否上市：IPO / UNIPO */
    private String enterpriseType;

    /** 股票代码，最大32字符 */
    private String stockCode;

    /** 股票交易所，ISO 3166-1 alpha-2 */
    private String stockMarket;

    /** 员工数量 */
    private Integer staffNumber;

    /** 公司财富来源列表 */
    private java.util.List<String> wealthSources;

    /** 扩展信息 JSON 字符串，最大4096字符 */
    private String additionalInfo;

    public Company() {
    }

    /**
     * Getter method for property <tt>companyType</tt>.
     *
     * @return property value of companyType
     */
    public String getCompanyType() {
        return companyType;
    }

    /**
     * Setter method for property <tt>companyType</tt>.
     *
     * @param companyType value to be assigned to property companyType
     */
    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    /**
     * Getter method for property <tt>registrationDate</tt>.
     *
     * @return property value of registrationDate
     */
    public String getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Setter method for property <tt>registrationDate</tt>.
     *
     * @param registrationDate value to be assigned to property registrationDate
     */
    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * Getter method for property <tt>legalName</tt>.
     *
     * @return property value of legalName
     */
    public String getLegalName() {
        return legalName;
    }

    /**
     * Setter method for property <tt>legalName</tt>.
     *
     * @param legalName value to be assigned to property legalName
     */
    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    /**
     * Getter method for property <tt>registrationType</tt>.
     *
     * @return property value of registrationType
     */
    public String getRegistrationType() {
        return registrationType;
    }

    /**
     * Setter method for property <tt>registrationType</tt>.
     *
     * @param registrationType value to be assigned to property registrationType
     */
    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    /**
     * Getter method for property <tt>registrationNo</tt>.
     *
     * @return property value of registrationNo
     */
    public String getRegistrationNo() {
        return registrationNo;
    }

    /**
     * Setter method for property <tt>registrationNo</tt>.
     *
     * @param registrationNo value to be assigned to property registrationNo
     */
    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    /**
     * Getter method for property <tt>registrationAddress</tt>.
     *
     * @return property value of registrationAddress
     */
    public Address getRegistrationAddress() {
        return registrationAddress;
    }

    /**
     * Setter method for property <tt>registrationAddress</tt>.
     *
     * @param registrationAddress value to be assigned to property registrationAddress
     */
    public void setRegistrationAddress(Address registrationAddress) {
        this.registrationAddress = registrationAddress;
    }

    /**
     * Getter method for property <tt>englishRegistrationAddress</tt>.
     *
     * @return property value of englishRegistrationAddress
     */
    public Address getEnglishRegistrationAddress() {
        return englishRegistrationAddress;
    }

    /**
     * Setter method for property <tt>englishRegistrationAddress</tt>.
     *
     * @param englishRegistrationAddress value to be assigned to property englishRegistrationAddress
     */
    public void setEnglishRegistrationAddress(Address englishRegistrationAddress) {
        this.englishRegistrationAddress = englishRegistrationAddress;
    }

    /**
     * Getter method for property <tt>certificates</tt>.
     *
     * @return property value of certificates
     */
    public java.util.List<Certificate> getCertificates() {
        return certificates;
    }

    /**
     * Setter method for property <tt>certificates</tt>.
     *
     * @param certificates value to be assigned to property certificates
     */
    public void setCertificates(java.util.List<Certificate> certificates) {
        this.certificates = certificates;
    }

    /**
     * Getter method for property <tt>attachments</tt>.
     *
     * @return property value of attachments
     */
    public java.util.List<Attachment> getAttachments() {
        return attachments;
    }

    /**
     * Setter method for property <tt>attachments</tt>.
     *
     * @param attachments value to be assigned to property attachments
     */
    public void setAttachments(java.util.List<Attachment> attachments) {
        this.attachments = attachments;
    }

    /**
     * Getter method for property <tt>taxNo</tt>.
     *
     * @return property value of taxNo
     */
    public String getTaxNo() {
        return taxNo;
    }

    /**
     * Setter method for property <tt>taxNo</tt>.
     *
     * @param taxNo value to be assigned to property taxNo
     */
    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    /**
     * Getter method for property <tt>vatNo</tt>.
     *
     * @return property value of vatNo
     */
    public String getVatNo() {
        return vatNo;
    }

    /**
     * Setter method for property <tt>vatNo</tt>.
     *
     * @param vatNo value to be assigned to property vatNo
     */
    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    /**
     * Getter method for property <tt>enterpriseType</tt>.
     *
     * @return property value of enterpriseType
     */
    public String getEnterpriseType() {
        return enterpriseType;
    }

    /**
     * Setter method for property <tt>enterpriseType</tt>.
     *
     * @param enterpriseType value to be assigned to property enterpriseType
     */
    public void setEnterpriseType(String enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    /**
     * Getter method for property <tt>stockCode</tt>.
     *
     * @return property value of stockCode
     */
    public String getStockCode() {
        return stockCode;
    }

    /**
     * Setter method for property <tt>stockCode</tt>.
     *
     * @param stockCode value to be assigned to property stockCode
     */
    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    /**
     * Getter method for property <tt>stockMarket</tt>.
     *
     * @return property value of stockMarket
     */
    public String getStockMarket() {
        return stockMarket;
    }

    /**
     * Setter method for property <tt>stockMarket</tt>.
     *
     * @param stockMarket value to be assigned to property stockMarket
     */
    public void setStockMarket(String stockMarket) {
        this.stockMarket = stockMarket;
    }

    /**
     * Getter method for property <tt>staffNumber</tt>.
     *
     * @return property value of staffNumber
     */
    public Integer getStaffNumber() {
        return staffNumber;
    }

    /**
     * Setter method for property <tt>staffNumber</tt>.
     *
     * @param staffNumber value to be assigned to property staffNumber
     */
    public void setStaffNumber(Integer staffNumber) {
        this.staffNumber = staffNumber;
    }

    /**
     * Getter method for property <tt>wealthSources</tt>.
     *
     * @return property value of wealthSources
     */
    public java.util.List<String> getWealthSources() {
        return wealthSources;
    }

    /**
     * Setter method for property <tt>wealthSources</tt>.
     *
     * @param wealthSources value to be assigned to property wealthSources
     */
    public void setWealthSources(java.util.List<String> wealthSources) {
        this.wealthSources = wealthSources;
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
