package {basePackage}.wf.model.request;

import {basePackage}.wf.model.domain.BankDetail;
import {basePackage}.wf.model.domain.WalletDetail;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst create_a_beneficiary 请求对象。
 *
 * <p>注册新的付款收款人（银行账户或数字钱包），创建后生成唯一收款人 ID。
 */
public class CreateBeneficiaryRequest {

    /** 账户类型：BANK_ACCOUNT（银行账户）、DIGITAL_WALLET（数字钱包）（必填） */
    private String accountType;

    /** 实体类型：COMPANY（企业）、PERSONAL（个人）（必填） */
    private String entityType;

    /** 关系类型：SAME_NAME（同名）、THIRD_PARTY（第三方）、RELATED_MERCHANT（关联商户）（必填） */
    private String relationType;

    /** 收款人所在地区（ISO 3166 两位字母代码，如 US、GB）（必填） */
    private String region;

    /** 账户币种（ISO 4217 三字母代码，如 USD、EUR）（必填） */
    private String currency;

    /** 支付方式：LOCAL（本地清算）、CROSS（跨境 SWIFT）。省略时两者均支持 */
    private String paymentType;

    /** 显示名称（最多 70 字符） */
    private String nickname;

    /** 联系电话（最多 64 字符） */
    private String phone;

    /** 联系邮箱（最多 64 字符） */
    private String email;

    /** 街道地址（最多 256 字符） */
    private String address;

    /** 城市（最多 64 字符） */
    private String city;

    /** 内部参考 ID（最多 64 字符） */
    private String referenceBeneficiaryId;

    /** 业务类别，需遵循收款人模板要求 */
    private String serviceCategory;

    /** 银行账户详情（accountType 为 BANK_ACCOUNT 时必填） */
    private BankDetail bankDetails;

    /** 钱包账户详情（accountType 为 DIGITAL_WALLET 时必填） */
    private WalletDetail walletDetails;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
