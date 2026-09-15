package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 代发收款方信息。
 *
 * <p>提供 beneficiaryId、bankDetails 或 walletDetails 之一。
 */
public class Beneficiary {

    /** 已注册收款人 ID（使用 Beneficiaries API 创建后获得） */
    private String beneficiaryId;

    /** 外部收款人引用 ID（商户系统分配），用于 CNY 结汇时声明收款人信息/额度 */
    private String referenceBeneficiaryId;

    /** 收款人账户类型（不使用 beneficiaryId 时提供）：`COMPANY`（企业）、`PERSONAL`（个人） */
    private String accountType;

    /** 收款人实体类型（不使用 beneficiaryId 时提供）：`COMPANY`（企业）、`PERSONAL`（个人） */
    private String entityType;

    /** 付款方与收款方关系类型（不使用 beneficiaryId 时提供）：`THIRD_PARTY`、`SAME_NAME`、`RELATED_MERCHANT` */
    private String relationType;

     /** 收款方地区（ISO 3166） */
    private String region;

    /** 收款方联系电话 */
    private String phone;

    /** 收款方邮箱 */
    private String email;

    /** 收款人城市 */
    private String city;

    /** 收款人街道地址 */
    private String address;

    /** 银行账户详情（直接传卡详情模式时使用） */
    private BankDetail bankDetails;

    /** 钱包账户详情（代发到数字钱包时使用） */
    private WalletDetail walletDetails;

    /**
     * Getter method for property <tt>region</tt>.
     *
     * @return property value of region
     */
    public String getRegion() {
        return region;
    }

    /**
     * Setter method for property <tt>region</tt>.
     *
     * @param region value to be assigned to property region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Getter method for property <tt>phone</tt>.
     *
     * @return property value of phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter method for property <tt>phone</tt>.
     *
     * @param phone value to be assigned to property phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter method for property <tt>email</tt>.
     *
     * @return property value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method for property <tt>email</tt>.
     *
     * @param email value to be assigned to property email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter method for property <tt>beneficiaryId</tt>.
     *
     * @return property value of beneficiaryId
     */
    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    /**
     * Setter method for property <tt>beneficiaryId</tt>.
     *
     * @param beneficiaryId value to be assigned to property beneficiaryId
     */
    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    /**
     * Getter method for property <tt>referenceBeneficiaryId</tt>.
     *
     * @return property value of referenceBeneficiaryId
     */
    public String getReferenceBeneficiaryId() {
        return referenceBeneficiaryId;
    }

    /**
     * Setter method for property <tt>referenceBeneficiaryId</tt>.
     *
     * @param referenceBeneficiaryId value to be assigned to property referenceBeneficiaryId
     */
    public void setReferenceBeneficiaryId(String referenceBeneficiaryId) {
        this.referenceBeneficiaryId = referenceBeneficiaryId;
    }

    /**
     * Getter method for property <tt>accountType</tt>.
     *
     * @return property value of accountType
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * Setter method for property <tt>accountType</tt>.
     *
     * @param accountType value to be assigned to property accountType
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    /**
     * Getter method for property <tt>entityType</tt>.
     *
     * @return property value of entityType
     */
    public String getEntityType() {
        return entityType;
    }

    /**
     * Setter method for property <tt>entityType</tt>.
     *
     * @param entityType value to be assigned to property entityType
     */
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    /**
     * Getter method for property <tt>relationType</tt>.
     *
     * @return property value of relationType
     */
    public String getRelationType() {
        return relationType;
    }

    /**
     * Setter method for property <tt>relationType</tt>.
     *
     * @param relationType value to be assigned to property relationType
     */
    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    /**
     * Getter method for property <tt>city</tt>.
     *
     * @return property value of city
     */
    public String getCity() {
        return city;
    }

    /**
     * Setter method for property <tt>city</tt>.
     *
     * @param city value to be assigned to property city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Getter method for property <tt>address</tt>.
     *
     * @return property value of address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter method for property <tt>address</tt>.
     *
     * @param address value to be assigned to property address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter method for property <tt>bankDetails</tt>.
     *
     * @return property value of bankDetails
     */
    public BankDetail getBankDetails() {
        return bankDetails;
    }

    /**
     * Setter method for property <tt>bankDetails</tt>.
     *
     * @param bankDetails value to be assigned to property bankDetails
     */
    public void setBankDetails(BankDetail bankDetails) {
        this.bankDetails = bankDetails;
    }

    /**
     * Getter method for property <tt>walletDetails</tt>.
     *
     * @return property value of walletDetails
     */
    public WalletDetail getWalletDetails() {
        return walletDetails;
    }

    /**
     * Setter method for property <tt>walletDetails</tt>.
     *
     * @param walletDetails value to be assigned to property walletDetails
     */
    public void setWalletDetails(WalletDetail walletDetails) {
        this.walletDetails = walletDetails;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
