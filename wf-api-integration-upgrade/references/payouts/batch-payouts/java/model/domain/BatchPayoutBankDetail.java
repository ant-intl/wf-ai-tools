package {basePackage}.wf.model.domain;


/** BatchPayoutBankDetail 领域结构。可选字段保持 null；完整约束见对应 GUIDE。 */
public class BatchPayoutBankDetail {

    /** Account holder&#x27;s legal name. See Data Types. */
    private UserName accountHolderName;

    /** Bank account number. */
    private String accountNumber;

    /** International Bank Account Number (IBAN). Used for SEPA and IBAN-based transfers. */
    private String iban;

    /** SWIFT/BIC code of the receiving bank. Required for cross-border wire transfers. */
    private String bankBIC;

    /** Local clearing routing number (e.g., ABA routing number for ACH transfers). */
    private String routingNumber;

    /** Receiving bank name. */
    private String bankName;

    /** Bank name in local language. */
    private String bankLocalName;

    /** Branch bank name. */
    private String branchBankName;

    /** Bank branch code. */
    private String bankBranchCode;

    /** Account holder&#x27;s name in local language. */
    private String bankAccountLocalName;

    /** Bank&#x27;s address. */
    private String bankAddress;

    /** Beneficiary deposit category. - `CC`: Current account (demand deposit). - `CP`: Fixed deposit. */
    private String beneficiaryDepositType;

    /** Beneficiary ID document type. */
    private String cardClientCode;

    /** Beneficiary ID document number. */
    private String cardClientId;

    /** Beneficiary Tax Identification Number (TIN). */
    private String beneficiaryTIN;

    /** Beneficiary account number type. */
    private String beneficiaryAccountNumberType;

    /** ID number (for PERSONAL) or business registration number (for COMPANY). Required only for beneficiary information declaration. */
    private String certificateNo;

    /** Bank city. */
    private String bankCity;

    /** Bank state or province. */
    private String bankState;

    /** Bank postal code. */
    private String bankPostalCode;

    public UserName getAccountHolderName() { return accountHolderName; }

    public void setAccountHolderName(UserName accountHolderName) { this.accountHolderName = accountHolderName; }

    public String getAccountNumber() { return accountNumber; }

    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getIban() { return iban; }

    public void setIban(String iban) { this.iban = iban; }

    public String getBankBIC() { return bankBIC; }

    public void setBankBIC(String bankBIC) { this.bankBIC = bankBIC; }

    public String getRoutingNumber() { return routingNumber; }

    public void setRoutingNumber(String routingNumber) { this.routingNumber = routingNumber; }

    public String getBankName() { return bankName; }

    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getBankLocalName() { return bankLocalName; }

    public void setBankLocalName(String bankLocalName) { this.bankLocalName = bankLocalName; }

    public String getBranchBankName() { return branchBankName; }

    public void setBranchBankName(String branchBankName) { this.branchBankName = branchBankName; }

    public String getBankBranchCode() { return bankBranchCode; }

    public void setBankBranchCode(String bankBranchCode) { this.bankBranchCode = bankBranchCode; }

    public String getBankAccountLocalName() { return bankAccountLocalName; }

    public void setBankAccountLocalName(String bankAccountLocalName) { this.bankAccountLocalName = bankAccountLocalName; }

    public String getBankAddress() { return bankAddress; }

    public void setBankAddress(String bankAddress) { this.bankAddress = bankAddress; }

    public String getBeneficiaryDepositType() { return beneficiaryDepositType; }

    public void setBeneficiaryDepositType(String beneficiaryDepositType) { this.beneficiaryDepositType = beneficiaryDepositType; }

    public String getCardClientCode() { return cardClientCode; }

    public void setCardClientCode(String cardClientCode) { this.cardClientCode = cardClientCode; }

    public String getCardClientId() { return cardClientId; }

    public void setCardClientId(String cardClientId) { this.cardClientId = cardClientId; }

    public String getBeneficiaryTIN() { return beneficiaryTIN; }

    public void setBeneficiaryTIN(String beneficiaryTIN) { this.beneficiaryTIN = beneficiaryTIN; }

    public String getBeneficiaryAccountNumberType() { return beneficiaryAccountNumberType; }

    public void setBeneficiaryAccountNumberType(String beneficiaryAccountNumberType) { this.beneficiaryAccountNumberType = beneficiaryAccountNumberType; }

    public String getCertificateNo() { return certificateNo; }

    public void setCertificateNo(String certificateNo) { this.certificateNo = certificateNo; }

    public String getBankCity() { return bankCity; }

    public void setBankCity(String bankCity) { this.bankCity = bankCity; }

    public String getBankState() { return bankState; }

    public void setBankState(String bankState) { this.bankState = bankState; }

    public String getBankPostalCode() { return bankPostalCode; }

    public void setBankPostalCode(String bankPostalCode) { this.bankPostalCode = bankPostalCode; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "BatchPayoutBankDetail[REDACTED]"; }
}
