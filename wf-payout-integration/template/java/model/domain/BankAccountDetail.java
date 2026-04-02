/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

/**
 * 银行账户详情
 *
 * @author Qoder
 * @version BankAccountDetail.java, v 0.1 2026-03-25
 */
public class BankAccountDetail {

    /** 银行账号/卡号 */
    private String bankAccountNo;

    /** 支行名称 */
    private String bankBranchName;

    /** SWIFT/BIC 代码，跨境转账时必填（非 CNY） */
    private String swiftCode;

    /** 银行所在国家/地区代码（ISO-3166，2 位字母） */
    private String bankRegion;

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
     * Getter method for property <tt>bankBranchName</tt>.
     *
     * @return property value of bankBranchName
     */
    public String getBankBranchName() {
        return bankBranchName;
    }

    /**
     * Setter method for property <tt>bankBranchName</tt>.
     *
     * @param bankBranchName value to be assigned to property bankBranchName
     */
    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }

    /**
     * Getter method for property <tt>swiftCode</tt>.
     *
     * @return property value of swiftCode
     */
    public String getSwiftCode() {
        return swiftCode;
    }

    /**
     * Setter method for property <tt>swiftCode</tt>.
     *
     * @param swiftCode value to be assigned to property swiftCode
     */
    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    }

    /**
     * 脱敏银行卡号：仅保留前4后4位
     */
    private static String maskAccountNo(String accountNo) {
        if (accountNo == null || accountNo.length() <= 8) {
            return accountNo;
        }
        return accountNo.substring(0, 4) + "****" + accountNo.substring(accountNo.length() - 4);
    }
}
