package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 钱包账户详情。
 *
 * <p>accountType 为 DIGITAL_WALLET 时必填。
 */
public class WalletDetail {

    /** 钱包账户持有人合法姓名（必填） */
    private UserName accountHolderName;

    /** 钱包品牌：OVO、GOPAY、DANA、ALIPAY、WORLDFIRST 等（必填） */
    private String walletBrandName;

    /** 钱包账号或关联手机号/邮箱 */
    private String accountNumber;

    /** 钱包账户类型：WORLDFIRST、ALIPAY（walletBrandName 为 WorldFirst/Alipay 时必填） */
    private String walletAccountType;

    /** 关联 IBAN */
    private String iban;

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
     * Getter method for property <tt>walletAccountType</tt>.
     *
     * @return property value of walletAccountType
     */
    public String getWalletAccountType() {
        return walletAccountType;
    }

    /**
     * Setter method for property <tt>walletAccountType</tt>.
     *
     * @param walletAccountType value to be assigned to property walletAccountType
     */
    public void setWalletAccountType(String walletAccountType) {
        this.walletAccountType = walletAccountType;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
