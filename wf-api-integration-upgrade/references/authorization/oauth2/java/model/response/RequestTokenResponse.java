package {basePackage}.wf.model.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst request_a_token 响应对象。
 *
 * <p>包含接口调用结果及访问令牌、刷新令牌等信息。
 */
public class RequestTokenResponse {

    /** 接口调用结果 */
    private Result result;

    /** 访问令牌，用于后续 API 调用的身份认证 */
    private String accessToken;

    /** 访问令牌过期时间（ISO 8601 格式，如 2026-06-24T07:13:29Z） */
    private String expiresAt;

    /** 刷新令牌，用于获取新的访问令牌（支持刷新时返回） */
    private String refreshToken;

    /** 刷新令牌过期时间（ISO 8601 格式，支持刷新时返回） */
    private String refreshTokenExpiresAt;

    /** 令牌有权访问的 WorldFirst 账户 ID，最大长度 64 字符 */
    private String accountId;

    /** 已授权的权限范围，空格分隔 */
    private String scope;

    /**
     * Getter method for property <tt>result</tt>.
     *
     * @return property value of result
     */
    public Result getResult() {
        return result;
    }

    /**
     * Setter method for property <tt>result</tt>.
     *
     * @param result value to be assigned to property result
     */
    public void setResult(Result result) {
        this.result = result;
    }

    /**
     * Getter method for property <tt>accessToken</tt>.
     *
     * @return property value of accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Setter method for property <tt>accessToken</tt>.
     *
     * @param accessToken value to be assigned to property accessToken
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Getter method for property <tt>expiresAt</tt>.
     *
     * @return property value of expiresAt
     */
    public String getExpiresAt() {
        return expiresAt;
    }

    /**
     * Setter method for property <tt>expiresAt</tt>.
     *
     * @param expiresAt value to be assigned to property expiresAt
     */
    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    /**
     * Getter method for property <tt>refreshToken</tt>.
     *
     * @return property value of refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Setter method for property <tt>refreshToken</tt>.
     *
     * @param refreshToken value to be assigned to property refreshToken
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Getter method for property <tt>refreshTokenExpiresAt</tt>.
     *
     * @return property value of refreshTokenExpiresAt
     */
    public String getRefreshTokenExpiresAt() {
        return refreshTokenExpiresAt;
    }

    /**
     * Setter method for property <tt>refreshTokenExpiresAt</tt>.
     *
     * @param refreshTokenExpiresAt value to be assigned to property refreshTokenExpiresAt
     */
    public void setRefreshTokenExpiresAt(String refreshTokenExpiresAt) {
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
    }

    /**
     * Getter method for property <tt>accountId</tt>.
     *
     * @return property value of accountId
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Setter method for property <tt>accountId</tt>.
     *
     * @param accountId value to be assigned to property accountId
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * Getter method for property <tt>scope</tt>.
     *
     * @return property value of scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * Setter method for property <tt>scope</tt>.
     *
     * @param scope value to be assigned to property scope
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
