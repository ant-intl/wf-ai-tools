package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 代发收款方银行账户详情。
 *
 * <p>当不使用 beneficiaryId 而是直接传银行卡详情时使用。
 * 与 {@link WalletDetail} 互斥。
 */
public class BankDetail {

    /** 账户持有人法定姓名 */
    private UserName accountHolderName;

    /** 银行账号 */
    private String accountNumber;

    /** 国际银行账号（IBAN），用于 SEPA 和基于 IBAN 的转账 */
    private String iban;

    /** 收款银行 SWIFT/BIC 代码，跨境电汇时必填 */
    private String bankBIC;

    /** 本地清算路由号码（如 ACH 转账的 ABA routing number） */
    private String routingNumber;

    /** 收款银行名称 */
    private String bankName;

    /** 银行本地语言名称 */
    private String bankLocalName;

    /** 支行名称 */
    private String branchBankName;

    /** 银行支行代码 */
    private String bankBranchCode;

    /** 账户持有人本地语言姓名 */
    private String bankAccountLocalName;

    /** 银行地址 */
    private String bankAddress;

    /** 收款人存款类别：`CC`（活期账户）、`CP`（定期存款） */
    private String beneficiaryDepositType;

    /** 收款人身份证件类型 */
    private String cardClientCode;

    /** 收款人身份证件号码 */
    private String cardClientId;

    /** 收款人税号（TIN） */
    private String beneficiaryTIN;

    /** 收款人账号类型 */
    private String beneficiaryAccountNumberType;

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
     * Getter method for property <tt>routingNumber</tt>.
     *
     * @return property value of routingNumber
     */
    public String getRoutingNumber() {
        return routingNumber;
    }

    /**
     * Setter method for property <tt>routingNumber</tt>.
     *
     * @param routingNumber value to be assigned to property routingNumber
     */
    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
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

    /**
     * Getter method for property <tt>bankLocalName</tt>.
     *
     * @return property value of bankLocalName
     */
    public String getBankLocalName() {
        return bankLocalName;
    }

    /**
     * Setter method for property <tt>bankLocalName</tt>.
     *
     * @param bankLocalName value to be assigned to property bankLocalName
     */
    public void setBankLocalName(String bankLocalName) {
        this.bankLocalName = bankLocalName;
    }

    /**
     * Getter method for property <tt>branchBankName</tt>.
     *
     * @return property value of branchBankName
     */
    public String getBranchBankName() {
        return branchBankName;
    }

    /**
     * Setter method for property <tt>branchBankName</tt>.
     *
     * @param branchBankName value to be assigned to property branchBankName
     */
    public void setBranchBankName(String branchBankName) {
        this.branchBankName = branchBankName;
    }

    /**
     * Getter method for property <tt>bankBranchCode</tt>.
     *
     * @return property value of bankBranchCode
     */
    public String getBankBranchCode() {
        return bankBranchCode;
    }

    /**
     * Setter method for property <tt>bankBranchCode</tt>.
     *
     * @param bankBranchCode value to be assigned to property bankBranchCode
     */
    public void setBankBranchCode(String bankBranchCode) {
        this.bankBranchCode = bankBranchCode;
    }

    /**
     * Getter method for property <tt>bankAccountLocalName</tt>.
     *
     * @return property value of bankAccountLocalName
     */
    public String getBankAccountLocalName() {
        return bankAccountLocalName;
    }

    /**
     * Setter method for property <tt>bankAccountLocalName</tt>.
     *
     * @param bankAccountLocalName value to be assigned to property bankAccountLocalName
     */
    public void setBankAccountLocalName(String bankAccountLocalName) {
        this.bankAccountLocalName = bankAccountLocalName;
    }

    /**
     * Getter method for property <tt>bankAddress</tt>.
     *
     * @return property value of bankAddress
     */
    public String getBankAddress() {
        return bankAddress;
    }

    /**
     * Setter method for property <tt>bankAddress</tt>.
     *
     * @param bankAddress value to be assigned to property bankAddress
     */
    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    /**
     * Getter method for property <tt>beneficiaryDepositType</tt>.
     *
     * @return property value of beneficiaryDepositType
     */
    public String getBeneficiaryDepositType() {
        return beneficiaryDepositType;
    }

    /**
     * Setter method for property <tt>beneficiaryDepositType</tt>.
     *
     * @param beneficiaryDepositType value to be assigned to property beneficiaryDepositType
     */
    public void setBeneficiaryDepositType(String beneficiaryDepositType) {
        this.beneficiaryDepositType = beneficiaryDepositType;
    }

    /**
     * Getter method for property <tt>cardClientCode</tt>.
     *
     * @return property value of cardClientCode
     */
    public String getCardClientCode() {
        return cardClientCode;
    }

    /**
     * Setter method for property <tt>cardClientCode</tt>.
     *
     * @param cardClientCode value to be assigned to property cardClientCode
     */
    public void setCardClientCode(String cardClientCode) {
        this.cardClientCode = cardClientCode;
    }

    /**
     * Getter method for property <tt>cardClientId</tt>.
     *
     * @return property value of cardClientId
     */
    public String getCardClientId() {
        return cardClientId;
    }

    /**
     * Setter method for property <tt>cardClientId</tt>.
     *
     * @param cardClientId value to be assigned to property cardClientId
     */
    public void setCardClientId(String cardClientId) {
        this.cardClientId = cardClientId;
    }

    /**
     * Getter method for property <tt>beneficiaryTIN</tt>.
     *
     * @return property value of beneficiaryTIN
     */
    public String getBeneficiaryTIN() {
        return beneficiaryTIN;
    }

    /**
     * Setter method for property <tt>beneficiaryTIN</tt>.
     *
     * @param beneficiaryTIN value to be assigned to property beneficiaryTIN
     */
    public void setBeneficiaryTIN(String beneficiaryTIN) {
        this.beneficiaryTIN = beneficiaryTIN;
    }

    /**
     * Getter method for property <tt>beneficiaryAccountNumberType</tt>.
     *
     * @return property value of beneficiaryAccountNumberType
     */
    public String getBeneficiaryAccountNumberType() {
        return beneficiaryAccountNumberType;
    }

    /**
     * Setter method for property <tt>beneficiaryAccountNumberType</tt>.
     *
     * @param beneficiaryAccountNumberType value to be assigned to property beneficiaryAccountNumberType
     */
    public void setBeneficiaryAccountNumberType(String beneficiaryAccountNumberType) {
        this.beneficiaryAccountNumberType = beneficiaryAccountNumberType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
