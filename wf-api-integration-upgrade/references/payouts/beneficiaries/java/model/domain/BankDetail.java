package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 银行账户详情。
 *
 * <p>用于 beneficiary 创建/更新和 payout 收款方信息。
 * accountType 为 BANK_ACCOUNT 时必填。
 */
public class BankDetail {

    /** 账户持有人合法姓名 */
    private UserName accountHolderName;

    /** 银行账号 */
    private String accountNumber;

    /** 国际银行账号（IBAN） */
    private String iban;

    /** SWIFT/BIC 代码 */
    private String bankBIC;

    /** 本地清算路由号（如 ACH routing number） */
    private String routingNumber;

    /** 银行名称 */
    private String bankName;

    /** 银行本地语言名称 */
    private String bankLocalName;

    /** 支行名称 */
    private String branchBankName;

    /** 支行清算代码 */
    private String bankBranchCode;

    /** 银行地址 */
    private String bankAddress;

    /** 银行所在城市 */
    private String bankCity;

    /** 收款方所在国家/地区代码 */
    private String beneficiaryRegion;

    /** 收款方账户本地语言名称 */
    private String bankAccountLocalName;

    /** 收款方本地语言地址 */
    private String beneficiaryLocalAddress;

    /** 收款方州/省 */
    private String beneficiaryState;

    /** 收款方邮编 */
    private String beneficiaryPostalCode;

    /** 存款类型：CC（活期）、CP（定期） */
    private String beneficiaryDepositType;

    /** 证件号或企业注册号（CNY 结算必填） */
    private String certificateNo;

    /** 税号 */
    private String beneficiaryTIN;

    /** 证件类型代码 */
    private String cardClientCode;

    /** 证件号码 */
    private String cardClientId;

    /** 账号类型 */
    private String beneficiaryAccountNumberType;

    /** 持卡人出生日期 */
    private String cardHolderBirthDate;

    /** 持卡人居住状态 */
    private String cardResidential;

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
     * Getter method for property <tt>bankCity</tt>.
     *
     * @return property value of bankCity
     */
    public String getBankCity() {
        return bankCity;
    }

    /**
     * Setter method for property <tt>bankCity</tt>.
     *
     * @param bankCity value to be assigned to property bankCity
     */
    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }

    /**
     * Getter method for property <tt>beneficiaryRegion</tt>.
     *
     * @return property value of beneficiaryRegion
     */
    public String getBeneficiaryRegion() {
        return beneficiaryRegion;
    }

    /**
     * Setter method for property <tt>beneficiaryRegion</tt>.
     *
     * @param beneficiaryRegion value to be assigned to property beneficiaryRegion
     */
    public void setBeneficiaryRegion(String beneficiaryRegion) {
        this.beneficiaryRegion = beneficiaryRegion;
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
     * Getter method for property <tt>beneficiaryLocalAddress</tt>.
     *
     * @return property value of beneficiaryLocalAddress
     */
    public String getBeneficiaryLocalAddress() {
        return beneficiaryLocalAddress;
    }

    /**
     * Setter method for property <tt>beneficiaryLocalAddress</tt>.
     *
     * @param beneficiaryLocalAddress value to be assigned to property beneficiaryLocalAddress
     */
    public void setBeneficiaryLocalAddress(String beneficiaryLocalAddress) {
        this.beneficiaryLocalAddress = beneficiaryLocalAddress;
    }

    /**
     * Getter method for property <tt>beneficiaryState</tt>.
     *
     * @return property value of beneficiaryState
     */
    public String getBeneficiaryState() {
        return beneficiaryState;
    }

    /**
     * Setter method for property <tt>beneficiaryState</tt>.
     *
     * @param beneficiaryState value to be assigned to property beneficiaryState
     */
    public void setBeneficiaryState(String beneficiaryState) {
        this.beneficiaryState = beneficiaryState;
    }

    /**
     * Getter method for property <tt>beneficiaryPostalCode</tt>.
     *
     * @return property value of beneficiaryPostalCode
     */
    public String getBeneficiaryPostalCode() {
        return beneficiaryPostalCode;
    }

    /**
     * Setter method for property <tt>beneficiaryPostalCode</tt>.
     *
     * @param beneficiaryPostalCode value to be assigned to property beneficiaryPostalCode
     */
    public void setBeneficiaryPostalCode(String beneficiaryPostalCode) {
        this.beneficiaryPostalCode = beneficiaryPostalCode;
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
     * Getter method for property <tt>certificateNo</tt>.
     *
     * @return property value of certificateNo
     */
    public String getCertificateNo() {
        return certificateNo;
    }

    /**
     * Setter method for property <tt>certificateNo</tt>.
     *
     * @param certificateNo value to be assigned to property certificateNo
     */
    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
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

    /**
     * Getter method for property <tt>cardHolderBirthDate</tt>.
     *
     * @return property value of cardHolderBirthDate
     */
    public String getCardHolderBirthDate() {
        return cardHolderBirthDate;
    }

    /**
     * Setter method for property <tt>cardHolderBirthDate</tt>.
     *
     * @param cardHolderBirthDate value to be assigned to property cardHolderBirthDate
     */
    public void setCardHolderBirthDate(String cardHolderBirthDate) {
        this.cardHolderBirthDate = cardHolderBirthDate;
    }

    /**
     * Getter method for property <tt>cardResidential</tt>.
     *
     * @return property value of cardResidential
     */
    public String getCardResidential() {
        return cardResidential;
    }

    /**
     * Setter method for property <tt>cardResidential</tt>.
     *
     * @param cardResidential value to be assigned to property cardResidential
     */
    public void setCardResidential(String cardResidential) {
        this.cardResidential = cardResidential;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
