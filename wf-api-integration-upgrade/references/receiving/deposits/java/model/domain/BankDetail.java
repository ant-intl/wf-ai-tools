package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 银行账户详情对象。
 *
 * <p>用于描述付款方的银行账户信息，当 paymentAccountType 为 BANK_ACCOUNT 时返回。
 */
public class BankDetail {

    /** 账户持有人姓名 */
    private UserName accountHolderName;

    /** 银行账号（脱敏） */
    private String accountNumber;

    /** SWIFT/BIC 代码 */
    private String bankBIC;

    /** 银行名称 */
    private String bankName;

    public BankDetail() {
    }

    /**
     * Getter method for property <tt>accountHolderName</tt>.
     *
     * @return property value of accountHolderName
     */
    public UserName getAccountHolderName() {
        return accountHolderName;
    }

    /**
     * Setter method for property <tt>accountHolderName</tt>.
     *
     * @param accountHolderName value to be assigned to property accountHolderName
     */
    public void setAccountHolderName(UserName accountHolderName) {
        this.accountHolderName = accountHolderName;
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
     * Getter method for property <tt>bankBIC</tt>.
     *
     * @return property value of bankBIC
     */
    public String getBankBIC() {
        return bankBIC;
    }

    /**
     * Setter method for property <tt>bankBIC</tt>.
     *
     * @param bankBIC value to be assigned to property bankBIC
     */
    public void setBankBIC(String bankBIC) {
        this.bankBIC = bankBIC;
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
