/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

/**
 * 买家姓名信息。
 *
 * <p>对应 WF submitTradeOrder 接口请求体中的 {@code buyer.buyerName} 字段。
 * 当传入 buyerName 时，{@code fullName} 为必填项。
 *
 * @author Qoder
 * @version BuyerName.java, v 0.1 2026-04-03
 */
public class BuyerName {

    /** 名，最大 32 字符 */
    private String firstName;

    /** 中间名，最大 32 字符 */
    private String middleName;

    /** 姓，最大 32 字符 */
    private String lastName;

    /** 全名（必填），最大 96 字符 */
    private String fullName;

    /**
     * Getter method for property <tt>firstName</tt>.
     *
     * @return property value of firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter method for property <tt>firstName</tt>.
     *
     * @param firstName value to be assigned to property firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter method for property <tt>middleName</tt>.
     *
     * @return property value of middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Setter method for property <tt>middleName</tt>.
     *
     * @param middleName value to be assigned to property middleName
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Getter method for property <tt>lastName</tt>.
     *
     * @return property value of lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter method for property <tt>lastName</tt>.
     *
     * @param lastName value to be assigned to property lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter method for property <tt>fullName</tt>.
     *
     * @return property value of fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Setter method for property <tt>fullName</tt>.
     *
     * @param fullName value to be assigned to property fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "BuyerName{firstName='" + firstName
            + "', middleName='" + middleName
            + "', lastName='" + lastName
            + "', fullName='" + fullName + "'}";
    }
}
