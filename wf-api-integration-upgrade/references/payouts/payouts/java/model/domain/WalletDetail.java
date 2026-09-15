package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 代发收款方电子钱包账户详情。
 *
 * <p>当不使用 beneficiaryId 而是直接传钱包详情时使用。
 * 与 {@link BankDetail} 互斥。
 */
public class WalletDetail {

    /** 钱包持有人法定姓名（必填） */
    private UserName accountHolderName;

    /** 钱包品牌（必填），参见 WalletBrandName 枚举：WORLDFIRST、ALIPAY、OVO、GOPAY、DANA 等 */
    private String walletBrandName;

    /** 钱包账户类型，参见 WalletAccountType 枚举：BALANCE、SUBLEDGER、WORLDFIRST_RECEIVE_ACCOUNT 等 */
    private String walletAccountType;

    /** 钱包账号 */
    private String accountNumber;

    /** 钱包账户的 IBAN（如适用） */
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
