/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.exception;

/**
 * WorldFirst API 错误码枚举
 *
 * @author Qoder
 * @version WfErrorCode.java, v 0.1 2026-03-24
 */
public enum WfErrorCode {

    // ---------------------- 通用错误码 ----------------------

    /** 参数非法 */
    PARAM_ILLEGAL("PARAM_ILLEGAL", false),

    /** OAuth 鉴权失败 */
    OAUTH_FAIL("OAUTH_FAIL", false),

    /** API 无效或未激活 */
    INVALID_API("INVALID_API", false),

    /** Client ID 无效 */
    INVALID_CLIENT("INVALID_CLIENT", false),

    /** 签名无效 */
    INVALID_SIGNATURE("INVALID_SIGNATURE", false),

    /** HTTP 方法不支持 */
    METHOD_NOT_SUPPORTED("METHOD_NOT_SUPPORTED", false),

    /** 用户不存在 */
    USER_NOT_EXIST("USER_NOT_EXIST", false),

    /** 账户不存在 */
    ACCOUNT_NOT_EXIST("ACCOUNT_NOT_EXIST", false),

    /** 系统错误，不可重试 */
    SYSTEM_ERROR("SYSTEM_ERROR", false),

    /** 服务不允许 */
    SERVICE_NOT_ALLOWED("SERVICE_NOT_ALLOWED", false),

    /** 货币不支持 */
    CURRENCY_NOT_SUPPORT("CURRENCY_NOT_SUPPORT", false),

    /** 合约校验失败 */
    CONTRACT_CHECK_FAIL("CONTRACT_CHECK_FAIL", false),

    /** Access Token 过期 */
    ACCESS_TOKEN_EXPIRED("ACCESS_TOKEN_EXPIRED", false),

    /** 授权不存在 */
    AUTHORIZATION_NOT_EXIST("AUTHORIZATION_NOT_EXIST", false),

    /** 处理失败 */
    PROCESS_FAIL("PROCESS_FAIL", false),

    // ---------------------- 可重试错误码 (resultStatus=U) ----------------------

    /** 未知异常，可重试 */
    UNKNOWN_EXCEPTION("UNKNOWN_EXCEPTION", true),

    /** 请求流量超限，可重试 */
    REQUEST_TRAFFIC_EXCEED_LIMIT("REQUEST_TRAFFIC_EXCEED_LIMIT", true),

    // ---------------------- 客户端内部错误码 ----------------------

    /** HTTP 请求失败 */
    HTTP_REQUEST_FAILED("HTTP_REQUEST_FAILED", false),

    /** 响应格式无效 */
    INVALID_RESPONSE_FORMAT("INVALID_RESPONSE_FORMAT", false),

    /** 签名生成失败 */
    SIGNATURE_GENERATION_FAILED("SIGNATURE_GENERATION_FAILED", false),

    /** 未知错误码（兜底） */
    UNKNOWN("UNKNOWN", false);

    /** 错误码字符串 */
    private final String code;

    /** 是否可重试 */
    private final boolean retryable;

    WfErrorCode(String code, boolean retryable) {
        this.code = code;
        this.retryable = retryable;
    }

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Getter method for property <tt>retryable</tt>.
     *
     * @return property value of retryable
     */
    public boolean isRetryable() {
        return retryable;
    }

    /**
     * 根据错误码字符串查找对应枚举，找不到时返回 UNKNOWN
     *
     * @param code 错误码字符串
     * @return 对应的 WfErrorCode 枚举值
     */
    public static WfErrorCode fromCode(String code) {
        if (code == null) {
            return UNKNOWN;
        }
        for (WfErrorCode errorCode : values()) {
            if (errorCode.code.equals(code)) {
                return errorCode;
            }
        }
        return UNKNOWN;
    }
}
