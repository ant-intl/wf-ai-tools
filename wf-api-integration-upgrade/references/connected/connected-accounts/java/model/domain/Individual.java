package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 个人用户信息对象。
 *
 * <p>描述关联账户的个人用户信息，包括姓名、证件、地址、税务信息等。
 */
public class Individual {

    /** 官方证件上的姓名，最大128字符 */
    private String legalName;

    /** 本地语言姓名 */
    private UserName userName;

    /** 英文姓名 */
    private UserName userEnglishName;

    /** 性别：MALE / FEMALE */
    private String gender;

    /** 证件信息列表，最多10个 */
    private java.util.List<Certificate> certificates;

    /** 附件列表，最多10个 */
    private java.util.List<Attachment> attachments;

    /** 税务识别号，最大64字符 */
    private String taxNo;

    /** VAT 号码，最大64字符 */
    private String vatNo;

    /** 国籍，ISO 3166-1 alpha-2 */
    private String nationality;

    /** 出生日期，ISO 8601 */
    private String birthDate;

    /** 出生地 */
    private Address birthPlace;

    /** 居住地址 */
    private Address residentAddress;

    /** 英文居住地址 */
    private Address englishResidentAddress;

    /** 通讯地址 */
    private Address contactAddress;

    /** 部门，最大64字符 */
    private String department;

    /** 职位，最大32字符 */
    private String position;

    /** 财富来源列表 */
    private java.util.List<String> wealthSources;

    /** 扩展信息 JSON 字符串，最大4096字符 */
    private String additionalInfo;

    public Individual() {
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
     * Getter method for property <tt>userName</tt>.
     *
     * @return property value of userName
     */
    public UserName getUserName() {
        return userName;
    }

    /**
     * Setter method for property <tt>userName</tt>.
     *
     * @param userName value to be assigned to property userName
     */
    public void setUserName(UserName userName) {
        this.userName = userName;
    }

    /**
     * Getter method for property <tt>userEnglishName</tt>.
     *
     * @return property value of userEnglishName
     */
    public UserName getUserEnglishName() {
        return userEnglishName;
    }

    /**
     * Setter method for property <tt>userEnglishName</tt>.
     *
     * @param userEnglishName value to be assigned to property userEnglishName
     */
    public void setUserEnglishName(UserName userEnglishName) {
        this.userEnglishName = userEnglishName;
    }

    /**
     * Getter method for property <tt>gender</tt>.
     *
     * @return property value of gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Setter method for property <tt>gender</tt>.
     *
     * @param gender value to be assigned to property gender
     */
    public void setGender(String gender) {
        this.gender = gender;
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
     * Getter method for property <tt>nationality</tt>.
     *
     * @return property value of nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Setter method for property <tt>nationality</tt>.
     *
     * @param nationality value to be assigned to property nationality
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * Getter method for property <tt>birthDate</tt>.
     *
     * @return property value of birthDate
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * Setter method for property <tt>birthDate</tt>.
     *
     * @param birthDate value to be assigned to property birthDate
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Getter method for property <tt>birthPlace</tt>.
     *
     * @return property value of birthPlace
     */
    public Address getBirthPlace() {
        return birthPlace;
    }

    /**
     * Setter method for property <tt>birthPlace</tt>.
     *
     * @param birthPlace value to be assigned to property birthPlace
     */
    public void setBirthPlace(Address birthPlace) {
        this.birthPlace = birthPlace;
    }

    /**
     * Getter method for property <tt>residentAddress</tt>.
     *
     * @return property value of residentAddress
     */
    public Address getResidentAddress() {
        return residentAddress;
    }

    /**
     * Setter method for property <tt>residentAddress</tt>.
     *
     * @param residentAddress value to be assigned to property residentAddress
     */
    public void setResidentAddress(Address residentAddress) {
        this.residentAddress = residentAddress;
    }

    /**
     * Getter method for property <tt>englishResidentAddress</tt>.
     *
     * @return property value of englishResidentAddress
     */
    public Address getEnglishResidentAddress() {
        return englishResidentAddress;
    }

    /**
     * Setter method for property <tt>englishResidentAddress</tt>.
     *
     * @param englishResidentAddress value to be assigned to property englishResidentAddress
     */
    public void setEnglishResidentAddress(Address englishResidentAddress) {
        this.englishResidentAddress = englishResidentAddress;
    }

    /**
     * Getter method for property <tt>contactAddress</tt>.
     *
     * @return property value of contactAddress
     */
    public Address getContactAddress() {
        return contactAddress;
    }

    /**
     * Setter method for property <tt>contactAddress</tt>.
     *
     * @param contactAddress value to be assigned to property contactAddress
     */
    public void setContactAddress(Address contactAddress) {
        this.contactAddress = contactAddress;
    }

    /**
     * Getter method for property <tt>department</tt>.
     *
     * @return property value of department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Setter method for property <tt>department</tt>.
     *
     * @param department value to be assigned to property department
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Getter method for property <tt>position</tt>.
     *
     * @return property value of position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Setter method for property <tt>position</tt>.
     *
     * @param position value to be assigned to property position
     */
    public void setPosition(String position) {
        this.position = position;
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
