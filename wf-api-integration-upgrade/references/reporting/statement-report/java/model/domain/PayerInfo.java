package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 付款方信息对象。
 *
 * <p>描述对账单中付款方的基本信息，所有字段均已脱敏。
 */
public class PayerInfo {

    /** 付款方 WorldFirst 唯一 ID */
    private String payerUserId;

    /** 付款方名称（脱敏） */
    private String name;

    /** 付款方账号（脱敏） */
    private String accountNo;

    /** 付款方账户类型 */
    private String accountType;

    /** 付款方银行名称 */
    private String bankName;

    public PayerInfo() {
    }

    /**
     * Getter method for property <tt>payerUserId</tt>.
     *
     * @return property value of payerUserId
     */
    public String getPayerUserId() {
        return payerUserId;
    }

    /**
     * Setter method for property <tt>payerUserId</tt>.
     *
     * @param payerUserId value to be assigned to property payerUserId
     */
    public void setPayerUserId(String payerUserId) {
        this.payerUserId = payerUserId;
    }

    /**
     * Getter method for property <tt>name</tt>.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     *
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property <tt>accountNo</tt>.
     *
     * @return property value of accountNo
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * Setter method for property <tt>accountNo</tt>.
     *
     * @param accountNo value to be assigned to property accountNo
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
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
     * Getter method for property <tt>bankName</tt>.
     *
     * @return property value of bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * Setter method for property <tt>bankName</tt>.
     *
     * @param bankName value to be assigned to property bankName
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
