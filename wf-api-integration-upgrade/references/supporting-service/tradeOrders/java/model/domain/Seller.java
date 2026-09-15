package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 卖家（收款方）信息对象。
 *
 * <p>描述贸易订单中卖家的账户和注册信息。
 */
public class Seller {

    /** WorldFirst 账户 ID */
    private String accountId;

    /** 第三方系统中的外部账户标识 */
    private String referenceAccountId;

    /** 注册公司类型：PERSONAL（个人）或 ENTERPRISE（企业） */
    private String customerType;

    /** 注册法人实体名称（customerType 为 ENTERPRISE 时） */
    private String customerCompanyName;

    /** 注册个人法人姓名（customerType 为 PERSONAL 时） */
    private UserNameInfo customerName;

    /** 联系邮箱地址 */
    private String customerEmail;

    /** 卖家国籍，ISO 3166 两字母代码 */
    private String nationality;

    public Seller() {
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getReferenceAccountId() {
        return referenceAccountId;
    }

    public void setReferenceAccountId(String referenceAccountId) {
        this.referenceAccountId = referenceAccountId;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerCompanyName() {
        return customerCompanyName;
    }

    public void setCustomerCompanyName(String customerCompanyName) {
        this.customerCompanyName = customerCompanyName;
    }

    public UserNameInfo getCustomerName() {
        return customerName;
    }

    public void setCustomerName(UserNameInfo customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
