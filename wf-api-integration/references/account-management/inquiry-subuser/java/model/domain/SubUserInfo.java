/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 子账号/主账号用户信息
 *
 * <p>用于 inquirySubuser 接口，描述主账号（primaryUserInformation）
 * 和子账号列表（userInformations）中每条记录的结构。
 *
 * @author Qoder
 * @version SubUserInfo.java, v 0.1 2026-04-16
 */
public class SubUserInfo {

    /** 万里汇用户 ID，最大 32 字符 */
    private String userId;

    /** 用户姓名 */
    private UserName userName;

    /** 用户登录账号，最大 128 字符 */
    private String logonId;

    /** 子账号昵称（子账号专用） */
    private UserName userNickName;

    /** 用户地址 */
    private Address userAddress;

    /** 用户邮箱，最大 67 字符 */
    private String userEmail;

    /** 用户手机号，最大 32 字符 */
    private String userMobile;

    /**
     * Getter method for property <tt>userId</tt>.
     *
     * @return property value of userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method for property <tt>userId</tt>.
     *
     * @param userId value to be assigned to property userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter method for property <tt>userName</tt>.
     *
     * @return property value of userName
     */
    public UserName getUserName() {
        return userName;
    }

    /**
     * Setter method for property <tt>userName</tt>.
     *
     * @param userName value to be assigned to property userName
     */
    public void setUserName(UserName userName) {
        this.userName = userName;
    }

    /**
     * Getter method for property <tt>logonId</tt>.
     *
     * @return property value of logonId
     */
    public String getLogonId() {
        return logonId;
    }

    /**
     * Setter method for property <tt>logonId</tt>.
     *
     * @param logonId value to be assigned to property logonId
     */
    public void setLogonId(String logonId) {
        this.logonId = logonId;
    }

    /**
     * Getter method for property <tt>userNickName</tt>.
     *
     * @return property value of userNickName
     */
    public UserName getUserNickName() {
        return userNickName;
    }

    /**
     * Setter method for property <tt>userNickName</tt>.
     *
     * @param userNickName value to be assigned to property userNickName
     */
    public void setUserNickName(UserName userNickName) {
        this.userNickName = userNickName;
    }

    /**
     * Getter method for property <tt>userAddress</tt>.
     *
     * @return property value of userAddress
     */
    public Address getUserAddress() {
        return userAddress;
    }

    /**
     * Setter method for property <tt>userAddress</tt>.
     *
     * @param userAddress value to be assigned to property userAddress
     */
    public void setUserAddress(Address userAddress) {
        this.userAddress = userAddress;
    }

    /**
     * Getter method for property <tt>userEmail</tt>.
     *
     * @return property value of userEmail
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Setter method for property <tt>userEmail</tt>.
     *
     * @param userEmail value to be assigned to property userEmail
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * Getter method for property <tt>userMobile</tt>.
     *
     * @return property value of userMobile
     */
    public String getUserMobile() {
        return userMobile;
    }

    /**
     * Setter method for property <tt>userMobile</tt>.
     *
     * @param userMobile value to be assigned to property userMobile
     */
    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
