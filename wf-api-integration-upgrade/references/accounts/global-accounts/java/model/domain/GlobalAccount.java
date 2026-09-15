package {basePackage}.wf.model.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 全局账户对象。
 *
 * <p>表示一个全球收款账户，包含账户基本信息、银行信息、申请和已开通的收款能力。
 * 用于 create_a_global_account、query_a_global_account、list_global_accounts、
 * update_a_global_account、close_a_global_account 接口的响应。
 */
public class GlobalAccount {

    /** 账户唯一标识 */
    private String id;

    /** 账户持有人合法名称（银行登记名称） */
    private String accountName;

    /** 用户自定义账户标签，不显示在银行对账单上 */
    private String nickName;

    /**
     * 账户类型，目前仅支持 CHECKING。
     * <p>详见 WF 官方文档 AccountType 枚举。
     */
    private String accountType;

    /**
     * 账户生命周期状态：PROCESSING（创建中）、ACTIVE（正常）、FAILED（创建失败）、CLOSED（已关闭）。
     * <p>详见 WF 官方文档 GlobalAccountStatus 枚举。
     */
    private String status;

    /** 关户原因，当 status 为 CLOSED 时返回 */
    private String closeReason;

    /** 开户失败原因，当 status 为 FAILED 时返回 */
    private String failureReason;

    /** 银行账号（用于本地收款），当 status 为 ACTIVE 时返回 */
    private String accountNumber;

    /** 国际银行账号（IBAN），适用于支持 IBAN 的地区（如欧盟） */
    private String iban;

    /** SWIFT/BIC 代码（用于跨境汇款），适用于支持 SWIFT 的地区 */
    private String swiftCode;

    /** 银行机构信息 */
    private Institution institution;

    /** 申请的收款能力列表（创建账户时请求的能力） */
    private List<RequiredFeature> requiredFeatures;

    /** 已开通的收款能力列表（银行实际分配的能力），当 status 为 ACTIVE 时返回 */
    private List<SupportedFeature> supportedFeatures;

    /** 账户创建时间（ISO 8601 格式，如 2024-01-01T00:00:00+08:00） */
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
     * Getter method for property <tt>accountName</tt>.
     *
     * @return property value of accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Setter method for property <tt>accountName</tt>.
     *
     * @param accountName value to be assigned to property accountName
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * Getter method for property <tt>nickName</tt>.
     *
     * @return property value of nickName
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Setter method for property <tt>nickName</tt>.
     *
     * @param nickName value to be assigned to property nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
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
     * Getter method for property <tt>closeReason</tt>.
     *
     * @return property value of closeReason
     */
    public String getCloseReason() {
        return closeReason;
    }

    /**
     * Setter method for property <tt>closeReason</tt>.
     *
     * @param closeReason value to be assigned to property closeReason
     */
    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }

    /**
     * Getter method for property <tt>failureReason</tt>.
     *
     * @return property value of failureReason
     */
    public String getFailureReason() {
        return failureReason;
    }

    /**
     * Setter method for property <tt>failureReason</tt>.
     *
     * @param failureReason value to be assigned to property failureReason
     */
    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    /**
     * Getter method for property <tt>accountNumber</tt>.
     *
     * @return property value of accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Setter method for property <tt>accountNumber</tt>.
     *
     * @param accountNumber value to be assigned to property accountNumber
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Getter method for property <tt>iban</tt>.
     *
     * @return property value of iban
     */
    public String getIban() {
        return iban;
    }

    /**
     * Setter method for property <tt>iban</tt>.
     *
     * @param iban value to be assigned to property iban
     */
    public void setIban(String iban) {
        this.iban = iban;
    }

    /**
     * Getter method for property <tt>swiftCode</tt>.
     *
     * @return property value of swiftCode
     */
    public String getSwiftCode() {
        return swiftCode;
    }

    /**
     * Setter method for property <tt>swiftCode</tt>.
     *
     * @param swiftCode value to be assigned to property swiftCode
     */
    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    /**
     * Getter method for property <tt>institution</tt>.
     *
     * @return property value of institution
     */
    public Institution getInstitution() {
        return institution;
    }

    /**
     * Setter method for property <tt>institution</tt>.
     *
     * @param institution value to be assigned to property institution
     */
    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    /**
     * Getter method for property <tt>requiredFeatures</tt>.
     *
     * @return property value of requiredFeatures
     */
    public List<RequiredFeature> getRequiredFeatures() {
        return requiredFeatures;
    }

    /**
     * Setter method for property <tt>requiredFeatures</tt>.
     *
     * @param requiredFeatures value to be assigned to property requiredFeatures
     */
    public void setRequiredFeatures(List<RequiredFeature> requiredFeatures) {
        this.requiredFeatures = requiredFeatures;
    }

    /**
     * Getter method for property <tt>supportedFeatures</tt>.
     *
     * @return property value of supportedFeatures
     */
    public List<SupportedFeature> getSupportedFeatures() {
        return supportedFeatures;
    }

    /**
     * Setter method for property <tt>supportedFeatures</tt>.
     *
     * @param supportedFeatures value to be assigned to property supportedFeatures
     */
    public void setSupportedFeatures(List<SupportedFeature> supportedFeatures) {
        this.supportedFeatures = supportedFeatures;
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
