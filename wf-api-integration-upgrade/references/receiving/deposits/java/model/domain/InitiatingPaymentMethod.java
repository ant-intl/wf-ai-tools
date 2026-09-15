package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 付款方支付方式对象。
 *
 * <p>描述付款方如何发送资金，包括账户类型和对应的银行或钱包详情。
 */
public class InitiatingPaymentMethod {

    /**
     * 付款方账户类型：BANK_ACCOUNT（银行账户）或 DIGITAL_WALLET（数字钱包）。
     */
    private String paymentAccountType;

    /**
     * 银行账户详情，当 paymentAccountType 为 BANK_ACCOUNT 时返回。
     */
    private BankDetail bankDetails;

    /**
     * 数字钱包详情，当 paymentAccountType 为 DIGITAL_WALLET 时返回。
     */
    private WalletDetail walletDetails;

    public InitiatingPaymentMethod() {
    }

    /**
     * Getter method for property <tt>paymentAccountType</tt>.
     *
     * @return property value of paymentAccountType
     */
    public String getPaymentAccountType() {
        return paymentAccountType;
    }

    /**
     * Setter method for property <tt>paymentAccountType</tt>.
     *
     * @param paymentAccountType value to be assigned to property paymentAccountType
     */
    public void setPaymentAccountType(String paymentAccountType) {
        this.paymentAccountType = paymentAccountType;
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
