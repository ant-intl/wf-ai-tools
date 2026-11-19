package {basePackage}.wf.model.domain;


/** BatchPayoutWalletDetail 领域结构。可选字段保持 null；完整约束见对应 GUIDE。 */
public class BatchPayoutWalletDetail {

    /** Required. Wallet holder&#x27;s legal name. See Data Types. */
    private UserName accountHolderName;

    /** Required. Wallet brand. See WalletBrandName. */
    private String walletBrandName;

    /** Wallet account number. */
    private String accountNumber;

    /** IBAN for the wallet account, if applicable. */
    private String iban;

    /** Required. Wallet account type. - `BALANCE`: Standard WorldFirst balance account. - `WORLDFIRST_RECEIVE_ACCOUNT`: WorldFirst receiving account (RA). */
    private String walletAccountType;

    /** Identification number of the ALIPAY account holder. Required only for beneficiary information declaration. */
    private String certificateNo;

    public UserName getAccountHolderName() { return accountHolderName; }

    public void setAccountHolderName(UserName accountHolderName) { this.accountHolderName = accountHolderName; }

    public String getWalletBrandName() { return walletBrandName; }

    public void setWalletBrandName(String walletBrandName) { this.walletBrandName = walletBrandName; }

    public String getAccountNumber() { return accountNumber; }

    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getIban() { return iban; }

    public void setIban(String iban) { this.iban = iban; }

    public String getWalletAccountType() { return walletAccountType; }

    public void setWalletAccountType(String walletAccountType) { this.walletAccountType = walletAccountType; }

    public String getCertificateNo() { return certificateNo; }

    public void setCertificateNo(String certificateNo) { this.certificateNo = certificateNo; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "BatchPayoutWalletDetail[REDACTED]"; }
}
