package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 数字钱包详情对象。
 *
 * <p>用于描述付款方的数字钱包信息，当 paymentAccountType 为 DIGITAL_WALLET 时返回。
 */
public class WalletDetail {

    /** 账户持有人姓名 */
    private UserName accountHolderName;

    /** 数字钱包品牌（如 OVO、GOPAY、DANA 等） */
    private String walletBrandName;

    /** 钱包账号（脱敏） */
    private String accountNumber;

    public WalletDetail() {
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
