package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 收款方信息对象。
 *
 * <p>描述对账单中收款方的基本信息，所有字段均已脱敏。
 */
public class BeneficiaryInfo {

    /** 收款方名称（脱敏） */
    private String name;

    /** 收款方账号（脱敏） */
    private String accountNo;

    /** 收款方账户类型 */
    private String accountType;

    /** 收款方账户所在地区 */
    private String bankRegion;

    /** 收款方银行名称 */
    private String bankName;

    /** 收款方店铺名称 */
    private String storeName;

    /** 集成方平台注册名称 */
    private String marketplaceName;

    /** 收款方 RA 或 VA 账号 */
    private String receiveAccount;

    public BeneficiaryInfo() {
    }

    /**
     * Getter method for property <tt>name</tt>.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     *
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property <tt>accountNo</tt>.
     *
     * @return property value of accountNo
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * Setter method for property <tt>accountNo</tt>.
     *
     * @param accountNo value to be assigned to property accountNo
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    /**
     * Getter method for property <tt>accountType</tt>.
     *
     * @return property value of accountType
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * Setter method for property <tt>accountType</tt>.
     *
     * @param accountType value to be assigned to property accountType
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
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
     * Getter method for property <tt>storeName</tt>.
     *
     * @return property value of storeName
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * Setter method for property <tt>storeName</tt>.
     *
     * @param storeName value to be assigned to property storeName
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * Getter method for property <tt>marketplaceName</tt>.
     *
     * @return property value of marketplaceName
     */
    public String getMarketplaceName() {
        return marketplaceName;
    }

    /**
     * Setter method for property <tt>marketplaceName</tt>.
     *
     * @param marketplaceName value to be assigned to property marketplaceName
     */
    public void setMarketplaceName(String marketplaceName) {
        this.marketplaceName = marketplaceName;
    }

    /**
     * Getter method for property <tt>receiveAccount</tt>.
     *
     * @return property value of receiveAccount
     */
    public String getReceiveAccount() {
        return receiveAccount;
    }

    /**
     * Setter method for property <tt>receiveAccount</tt>.
     *
     * @param receiveAccount value to be assigned to property receiveAccount
     */
    public void setReceiveAccount(String receiveAccount) {
        this.receiveAccount = receiveAccount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
