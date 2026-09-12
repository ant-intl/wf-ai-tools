package {basePackage}.wf.model.request;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst list_beneficiaries 请求对象。
 *
 * <p>分页查询收款人列表，支持多维度筛选。
 * 使用 cursor 游标分页，nextCursor/prevCursor 双向翻页。
 */
public class ListBeneficiariesRequest {

    /** 每页返回数量 */
    private Integer limit;

    /** 分页游标（首次请求不传，后续传入上页返回的 nextCursor 或 prevCursor） */
    private String cursor;

    /** 账户类型筛选：BANK_ACCOUNT、DIGITAL_WALLET */
    private String accountType;

    /** 实体类型筛选：COMPANY、PERSONAL */
    private String entityType;

    /** 关系类型筛选：SAME_NAME、THIRD_PARTY、RELATED_MERCHANT */
    private String relationType;

    /** 支付方式筛选：LOCAL、CROSS */
    private String paymentType;

    /** 显示名称筛选 */
    private String nickname;

    /** 账户持有人名称筛选 */
    private String accountName;

    /** 银行账号筛选 */
    private String accountNumber;

    /** 国际银行账号（IBAN）筛选 */
    private String iban;

    /** 证件号或企业注册号筛选 */
    private String certificateNo;

    /** 币种列表筛选（ISO 4217 三字母代码） */
    private List<String> currencyList;

    /**
     * Getter method for property <tt>limit</tt>.
     *
     * @return property value of limit
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * Setter method for property <tt>limit</tt>.
     *
     * @param limit value to be assigned to property limit
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * Getter method for property <tt>cursor</tt>.
     *
     * @return property value of cursor
     */
    public String getCursor() {
        return cursor;
    }

    /**
     * Setter method for property <tt>cursor</tt>.
     *
     * @param cursor value to be assigned to property cursor
     */
    public void setCursor(String cursor) {
        this.cursor = cursor;
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
     * Getter method for property <tt>currencyList</tt>.
     *
     * @return property value of currencyList
     */
    public List<String> getCurrencyList() {
        return currencyList;
    }

    /**
     * Setter method for property <tt>currencyList</tt>.
     *
     * @param currencyList value to be assigned to property currencyList
     */
    public void setCurrencyList(List<String> currencyList) {
        this.currencyList = currencyList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
