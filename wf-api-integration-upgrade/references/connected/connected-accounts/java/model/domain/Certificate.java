package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 证件信息对象。
 *
 * <p>描述关联账户的证件信息，包括证件类型、号码、文件、持有人姓名、有效期等。
 */
public class Certificate {

    /** 证件类型，CertificateType 枚举 */
    private String certificateType;

    /** 证件号码，最大64字符 */
    private String certificateNo;

    /** 上传文件 key 列表，最多10个 */
    private java.util.List<String> files;

    /** 证件持有人姓名，最大128字符 */
    private String holderName;

    /** 有效期类型：TIME_RANGE / LONG_TERM */
    private String effectivePeriodType;

    /** 生效日期，ISO 8601 */
    private String effectiveDate;

    /** 到期日期，ISO 8601 */
    private String expiresDate;

    /** 发证机关，最大128字符 */
    private String certificateAuthority;

    public Certificate() {
    }

    /**
     * Getter method for property <tt>certificateType</tt>.
     *
     * @return property value of certificateType
     */
    public String getCertificateType() {
        return certificateType;
    }

    /**
     * Setter method for property <tt>certificateType</tt>.
     *
     * @param certificateType value to be assigned to property certificateType
     */
    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    /**
     * Getter method for property <tt>certificateNo</tt>.
     *
     * @return property value of certificateNo
     */
    public String getCertificateNo() {
        return certificateNo;
    }

    /**
     * Setter method for property <tt>certificateNo</tt>.
     *
     * @param certificateNo value to be assigned to property certificateNo
     */
    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    /**
     * Getter method for property <tt>files</tt>.
     *
     * @return property value of files
     */
    public java.util.List<String> getFiles() {
        return files;
    }

    /**
     * Setter method for property <tt>files</tt>.
     *
     * @param files value to be assigned to property files
     */
    public void setFiles(java.util.List<String> files) {
        this.files = files;
    }

    /**
     * Getter method for property <tt>holderName</tt>.
     *
     * @return property value of holderName
     */
    public String getHolderName() {
        return holderName;
    }

    /**
     * Setter method for property <tt>holderName</tt>.
     *
     * @param holderName value to be assigned to property holderName
     */
    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    /**
     * Getter method for property <tt>effectivePeriodType</tt>.
     *
     * @return property value of effectivePeriodType
     */
    public String getEffectivePeriodType() {
        return effectivePeriodType;
    }

    /**
     * Setter method for property <tt>effectivePeriodType</tt>.
     *
     * @param effectivePeriodType value to be assigned to property effectivePeriodType
     */
    public void setEffectivePeriodType(String effectivePeriodType) {
        this.effectivePeriodType = effectivePeriodType;
    }

    /**
     * Getter method for property <tt>effectiveDate</tt>.
     *
     * @return property value of effectiveDate
     */
    public String getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Setter method for property <tt>effectiveDate</tt>.
     *
     * @param effectiveDate value to be assigned to property effectiveDate
     */
    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Getter method for property <tt>expiresDate</tt>.
     *
     * @return property value of expiresDate
     */
    public String getExpiresDate() {
        return expiresDate;
    }

    /**
     * Setter method for property <tt>expiresDate</tt>.
     *
     * @param expiresDate value to be assigned to property expiresDate
     */
    public void setExpiresDate(String expiresDate) {
        this.expiresDate = expiresDate;
    }

    /**
     * Getter method for property <tt>certificateAuthority</tt>.
     *
     * @return property value of certificateAuthority
     */
    public String getCertificateAuthority() {
        return certificateAuthority;
    }

    /**
     * Setter method for property <tt>certificateAuthority</tt>.
     *
     * @param certificateAuthority value to be assigned to property certificateAuthority
     */
    public void setCertificateAuthority(String certificateAuthority) {
        this.certificateAuthority = certificateAuthority;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
