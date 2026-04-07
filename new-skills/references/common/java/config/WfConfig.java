/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.ipay.iissuecard.common.service.integration.wf.config;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst API 配置类
 *
 * @author Qoder
 * @version WfConfig.java, v 0.1 2026-03-24
 */
public class WfConfig {

    /** WF client identifier（由 WF 分配，如 "3K5Y966G2Y5G5309739"） */
    private String clientId = "{clientId}";

    /** WF user identifier（即登录 WF 的 userId） */
    private String userId = "{userId}";

    /** WF API base URL（沙箱: https://iopengw-sggz95m.alipay.com, 生产: https://iopengw.alipay.com） */
    private String baseUrl = "{baseUrl}";

    /** RSA private key file path (PKCS#8 PEM)（客户私钥文件路径） */
    private String privateKeyPath = "{privateKeyPath}";

    /** WF RSA public key file path（万里汇公钥文件路径） */
    private String publicKeyPath = "{publicKeyPath}";

    /** HTTP connect timeout in milliseconds */
    private int connectTimeout = 10000;

    /** HTTP read timeout in milliseconds */
    private int readTimeout = 30000;

    /**
     * Getter method for property <tt>clientId</tt>.
     *
     * @return property value of clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Setter method for property <tt>clientId</tt>.
     *
     * @param clientId value to be assigned to property clientId
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

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
     * Getter method for property <tt>baseUrl</tt>.
     *
     * @return property value of baseUrl
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Setter method for property <tt>baseUrl</tt>.
     *
     * @param baseUrl value to be assigned to property baseUrl
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Getter method for property <tt>privateKeyPath</tt>.
     *
     * @return property value of privateKeyPath
     */
    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    /**
     * Setter method for property <tt>privateKeyPath</tt>.
     *
     * @param privateKeyPath value to be assigned to property privateKeyPath
     */
    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    /**
     * Getter method for property <tt>publicKeyPath</tt>.
     *
     * @return property value of publicKeyPath
     */
    public String getPublicKeyPath() {
        return publicKeyPath;
    }

    /**
     * Setter method for property <tt>publicKeyPath</tt>.
     *
     * @param publicKeyPath value to be assigned to property publicKeyPath
     */
    public void setPublicKeyPath(String publicKeyPath) {
        this.publicKeyPath = publicKeyPath;
    }

    /**
     * Getter method for property <tt>connectTimeout</tt>.
     *
     * @return property value of connectTimeout
     */
    public int getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * Setter method for property <tt>connectTimeout</tt>.
     *
     * @param connectTimeout value to be assigned to property connectTimeout
     */
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * Getter method for property <tt>readTimeout</tt>.
     *
     * @return property value of readTimeout
     */
    public int getReadTimeout() {
        return readTimeout;
    }

    /**
     * Setter method for property <tt>readTimeout</tt>.
     *
     * @param readTimeout value to be assigned to property readTimeout
     */
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
