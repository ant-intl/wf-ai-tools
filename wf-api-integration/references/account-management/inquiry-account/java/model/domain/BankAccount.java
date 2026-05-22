package {basePackage}.wf.model.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 银行账户信息
 *
 */
public class BankAccount {

    /** 银行账号 */
    private String bankAccountNo;

    /** 账号户主姓名 */
    private UserName holderName;

    /** 户主账户类型：INDIVIDUAL（个人）、COMPANY（公司） */
    private String holderAccountType;

    /** 户主地址 */
    private Address holderAddress;

    /** VA 对应币种列表（ISO-4217） */
    private List<String> currencyList;

    /** 银行名称 */
    private String bankName;

    /** 汇款路径代码（如 ABA），USD+US 时必填 */
    private String routingNumber;

    /** 银行地址 */
    private Address bankAddress;

    /** 银行所在国家/地区（ISO-3166 二字母） */
    private String bankRegion;

    /** 银行 BIC 代码（8-11 位） */
    private String bankBIC;

    /** IBAN（EUR+EU 或 GBP+GB 时必填） */
    private String bankAccountIBAN;

    /** BSB 号码（AUD+AU 或 NZD+NZ 时必填） */
    private String bankAccountBSB;

    /** 账号创建时间 */
    private String accountCreationDate;

    /** 银行代码 */
    private String bankCode;

    /** 银行分行代码 */
    private String branchCode;

    /** 银行账户类型：checking（活期）、saving（储蓄），JPY/CAD 时必填 */
    private String bankAccountType;

    /** 收款地区：GLOBAL（境外）、LOCAL（境内），AUD/NZD 时必填 */
    private String collectionArea;

    /** Wire 汇款路线号码，USD+US 时必填 */
    private String wireRoutingNumber;

    /** Sort Code（GBP+GB 或 EUR+GB 时必填） */
    private String sortCode;

    /**
     * Getter method for property <tt>bankAccountNo</tt>.
     *
     * @return property value of bankAccountNo
     */
    public String getBankAccountNo() {
        return bankAccountNo;
    }

    /**
     * Setter method for property <tt>bankAccountNo</tt>.
     *
     * @param bankAccountNo value to be assigned to property bankAccountNo
     */
    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    /**
     * Getter method for property <tt>holderName</tt>.
     *
     * @return property value of holderName
     */
    public UserName getHolderName() {
        return holderName;
    }

    /**
     * Setter method for property <tt>holderName</tt>.
     *
     * @param holderName value to be assigned to property holderName
     */
    public void setHolderName(UserName holderName) {
        this.holderName = holderName;
    }

    /**
     * Getter method for property <tt>holderAccountType</tt>.
     *
     * @return property value of holderAccountType
     */
    public String getHolderAccountType() {
        return holderAccountType;
    }

    /**
     * Setter method for property <tt>holderAccountType</tt>.
     *
     * @param holderAccountType value to be assigned to property holderAccountType
     */
    public void setHolderAccountType(String holderAccountType) {
        this.holderAccountType = holderAccountType;
    }

    /**
     * Getter method for property <tt>holderAddress</tt>.
     *
     * @return property value of holderAddress
     */
    public Address getHolderAddress() {
        return holderAddress;
    }

    /**
     * Setter method for property <tt>holderAddress</tt>.
     *
     * @param holderAddress value to be assigned to property holderAddress
     */
    public void setHolderAddress(Address holderAddress) {
        this.holderAddress = holderAddress;
    }

    /**
     * Getter method for property <tt>currencyList</tt>.
     *
     * @return property value of currencyList
     */
    public List<String> getCurrencyList() {
        return currencyList;
    }

    /**
     * Setter method for property <tt>currencyList</tt>.
     *
     * @param currencyList value to be assigned to property currencyList
     */
    public void setCurrencyList(List<String> currencyList) {
        this.currencyList = currencyList;
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
     * Getter method for property <tt>bankAddress</tt>.
     *
     * @return property value of bankAddress
     */
    public Address getBankAddress() {
        return bankAddress;
    }

    /**
     * Setter method for property <tt>bankAddress</tt>.
     *
     * @param bankAddress value to be assigned to property bankAddress
     */
    public void setBankAddress(Address bankAddress) {
        this.bankAddress = bankAddress;
    }

    /**
     * Getter method for property <tt>bankRegion</tt>.
     *
     * @return property value of bankRegion
     */
    public String getBankRegion() {
        return bankRegion;
    }

    /**
     * Setter method for property <tt>bankRegion</tt>.
     *
     * @param bankRegion value to be assigned to property bankRegion
     */
    public void setBankRegion(String bankRegion) {
        this.bankRegion = bankRegion;
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
     * Getter method for property <tt>bankAccountIBAN</tt>.
     *
     * @return property value of bankAccountIBAN
     */
    public String getBankAccountIBAN() {
        return bankAccountIBAN;
    }

    /**
     * Setter method for property <tt>bankAccountIBAN</tt>.
     *
     * @param bankAccountIBAN value to be assigned to property bankAccountIBAN
     */
    public void setBankAccountIBAN(String bankAccountIBAN) {
        this.bankAccountIBAN = bankAccountIBAN;
    }

    /**
     * Getter method for property <tt>bankAccountBSB</tt>.
     *
     * @return property value of bankAccountBSB
     */
    public String getBankAccountBSB() {
        return bankAccountBSB;
    }

    /**
     * Setter method for property <tt>bankAccountBSB</tt>.
     *
     * @param bankAccountBSB value to be assigned to property bankAccountBSB
     */
    public void setBankAccountBSB(String bankAccountBSB) {
        this.bankAccountBSB = bankAccountBSB;
    }

    /**
     * Getter method for property <tt>accountCreationDate</tt>.
     *
     * @return property value of accountCreationDate
     */
    public String getAccountCreationDate() {
        return accountCreationDate;
    }

    /**
     * Setter method for property <tt>accountCreationDate</tt>.
     *
     * @param accountCreationDate value to be assigned to property accountCreationDate
     */
    public void setAccountCreationDate(String accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }

    /**
     * Getter method for property <tt>bankCode</tt>.
     *
     * @return property value of bankCode
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * Setter method for property <tt>bankCode</tt>.
     *
     * @param bankCode value to be assigned to property bankCode
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * Getter method for property <tt>branchCode</tt>.
     *
     * @return property value of branchCode
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * Setter method for property <tt>branchCode</tt>.
     *
     * @param branchCode value to be assigned to property branchCode
     */
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    /**
     * Getter method for property <tt>bankAccountType</tt>.
     *
     * @return property value of bankAccountType
     */
    public String getBankAccountType() {
        return bankAccountType;
    }

    /**
     * Setter method for property <tt>bankAccountType</tt>.
     *
     * @param bankAccountType value to be assigned to property bankAccountType
     */
    public void setBankAccountType(String bankAccountType) {
        this.bankAccountType = bankAccountType;
    }

    /**
     * Getter method for property <tt>collectionArea</tt>.
     *
     * @return property value of collectionArea
     */
    public String getCollectionArea() {
        return collectionArea;
    }

    /**
     * Setter method for property <tt>collectionArea</tt>.
     *
     * @param collectionArea value to be assigned to property collectionArea
     */
    public void setCollectionArea(String collectionArea) {
        this.collectionArea = collectionArea;
    }

    /**
     * Getter method for property <tt>wireRoutingNumber</tt>.
     *
     * @return property value of wireRoutingNumber
     */
    public String getWireRoutingNumber() {
        return wireRoutingNumber;
    }

    /**
     * Setter method for property <tt>wireRoutingNumber</tt>.
     *
     * @param wireRoutingNumber value to be assigned to property wireRoutingNumber
     */
    public void setWireRoutingNumber(String wireRoutingNumber) {
        this.wireRoutingNumber = wireRoutingNumber;
    }

    /**
     * Getter method for property <tt>sortCode</tt>.
     *
     * @return property value of sortCode
     */
    public String getSortCode() {
        return sortCode;
    }

    /**
     * Setter method for property <tt>sortCode</tt>.
     *
     * @param sortCode value to be assigned to property sortCode
     */
    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
