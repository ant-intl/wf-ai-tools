package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 收款人（Beneficiary）对象。
 *
 * <p>表示一个已注册的付款收款方，包含银行账户或数字钱包信息。
 * 用于 create_a_beneficiary、query_a_beneficiary、list_beneficiaries、
 * update_a_beneficiary、delete_a_beneficiary、validate_a_beneficiary 接口。
 */
public class Beneficiary {

    /** 收款人唯一标识 */
    private String id;

    /** 收款人状态：PROCESSING、ACTIVE、REJECTED、DELETED */
    private String status;

    /** 收款人所在地区（ISO 3166） */
    private String region;

    /** 账户类型：BANK_ACCOUNT、DIGITAL_WALLET */
    private String accountType;

    /** 实体类型：COMPANY、PERSONAL */
    private String entityType;

    /** 关系类型：SAME_NAME、THIRD_PARTY、RELATED_MERCHANT */
    private String relationType;

    /** 账户币种（ISO 4217） */
    private String currency;

    /** 支付方式：LOCAL、CROSS */
    private String paymentType;

    /** 显示名称 */
    private String nickname;

    /** 联系电话 */
    private String phone;

    /** 联系邮箱 */
    private String email;

    /** 街道地址 */
    private String address;

    /** 城市 */
    private String city;

    /** 内部参考 ID（最多 64 字符） */
    private String referenceBeneficiaryId;

    /** 业务类别，需遵循收款人模板要求 */
    private String serviceCategory;

    /** 银行账户详情（accountType 为 BANK_ACCOUNT 时返回） */
    private BankDetail bankDetails;

    /** 钱包账户详情（accountType 为 DIGITAL_WALLET 时返回） */
    private WalletDetail walletDetails;

    /** 创建时间（ISO 8601 格式，如 2024-01-01T00:00:00+08:00） */
    private String createdAt;

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
     * Getter method for property <tt>currency</tt>.
     *
     * @return property value of currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Setter method for property <tt>currency</tt>.
     *
     * @param currency value to be assigned to property currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Getter method for property <tt>paymentType</tt>.
     *
     * @return property value of paymentType
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * Setter method for property <tt>paymentType</tt>.
     *
     * @param paymentType value to be assigned to property paymentType
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * Getter method for property <tt>nickname</tt>.
     *
     * @return property value of nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Setter method for property <tt>nickname</tt>.
     *
     * @param nickname value to be assigned to property nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
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
     * Getter method for property <tt>serviceCategory</tt>.
     *
     * @return property value of serviceCategory
     */
    public String getServiceCategory() {
        return serviceCategory;
    }

    /**
     * Setter method for property <tt>serviceCategory</tt>.
     *
     * @param serviceCategory value to be assigned to property serviceCategory
     */
    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
