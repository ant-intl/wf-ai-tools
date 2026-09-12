package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_beneficiary_template 请求对象。
 *
 * <p>查询创建收款人所需的字段模板，根据筛选条件返回对应的字段定义和校验规则。
 */
public class QueryBeneficiaryTemplateRequest {

    /** 收款人所在地区（ISO 3166 两位字母代码，如 US、GB） */
    private String region;

    /** 账户币种（ISO 4217 三字母代码，如 USD、EUR） */
    private String currency;

    /** 账户类型：BANK_ACCOUNT、DIGITAL_WALLET */
    private String accountType;

    /** 实体类型：COMPANY、PERSONAL */
    private String entityType;

    /** 关系类型：SAME_NAME、THIRD_PARTY、RELATED_MERCHANT */
    private String relationType;

    /** 支付方式：LOCAL、CROSS */
    private String paymentType;

    /** 钱包品牌名称（如 OVO、GOPAY、DANA、ALIPAY 等） */
    private String walletBrandName;

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
     * Getter method for property <tt>walletBrandName</tt>.
     *
     * @return property value of walletBrandName
     */
    public String getWalletBrandName() {
        return walletBrandName;
    }

    /**
     * Setter method for property <tt>walletBrandName</tt>.
     *
     * @param walletBrandName value to be assigned to property walletBrandName
     */
    public void setWalletBrandName(String walletBrandName) {
        this.walletBrandName = walletBrandName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
