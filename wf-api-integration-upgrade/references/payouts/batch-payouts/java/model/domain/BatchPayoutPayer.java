package {basePackage}.wf.model.domain;


/** BatchPayoutPayer 领域结构。可选字段保持 null；完整约束见对应 GUIDE。 */
public class BatchPayoutPayer {

    /** Payer account type. Default: BALANCE. - `BALANCE`: Standard balance account. - `SUBLEDGER`: Sub-ledger account. */
    private String balanceType;

    /** Payer account number. Required when balanceType is SUBLEDGER. */
    private String accountNumber;

    /** Payer&#x27;s country/region. ISO 3166 two-letter code. */
    private String region;

    /** Payer&#x27;s phone number. */
    private String phone;

    /** Payer&#x27;s email address. */
    private String email;

    /** Payer&#x27;s legal name. See Data Types. */
    private UserName name;

    /** Payer&#x27;s mailing address. See Data Types. */
    private Address address;

    public String getBalanceType() { return balanceType; }

    public void setBalanceType(String balanceType) { this.balanceType = balanceType; }

    public String getAccountNumber() { return accountNumber; }

    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getRegion() { return region; }

    public void setRegion(String region) { this.region = region; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public UserName getName() { return name; }

    public void setName(UserName name) { this.name = name; }

    public Address getAddress() { return address; }

    public void setAddress(Address address) { this.address = address; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "BatchPayoutPayer[REDACTED]"; }
}
