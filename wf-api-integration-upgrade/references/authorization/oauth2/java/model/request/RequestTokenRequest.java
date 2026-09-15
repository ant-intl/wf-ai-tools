package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst request_a_token 请求对象。
 *
 * <p>用于将授权码或刷新令牌换取访问令牌。
 */
public class RequestTokenRequest {

    /**
     * 授权类型。
     * <p>支持值：
     * <ul>
     *   <li>{@code AUTHORIZATION_CODE} — 使用授权码换取令牌</li>
     *   <li>{@code REFRESH_TOKEN} — 使用刷新令牌刷新访问令牌</li>
     * </ul>
     */
    private String grantType;

    /**
     * 授权码。
     * <p>从 authorize 重定向回调中获取。
     * <p>当 {@code grantType} 为 {@code AUTHORIZATION_CODE} 时必填。
     * <p>最大长度：256 字符。有效期 10 分钟，单次使用。
     */
    private String code;

    /**
     * 刷新令牌。
     * <p>当 {@code grantType} 为 {@code REFRESH_TOKEN} 时必填。
     * <p>最大长度：256 字符。在有效期内可重复使用。
     */
    private String refreshToken;

    public RequestTokenRequest() {
    }

    /**
     * Getter method for property <tt>grantType</tt>.
     *
     * @return property value of grantType
     */
    public String getGrantType() {
        return grantType;
    }

    /**
     * Setter method for property <tt>grantType</tt>.
     *
     * @param grantType value to be assigned to property grantType
     */
    public void setGrantType(String grantType) {
        this.grantType = grantType;
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
     * Setter method for property <tt>code</tt>.
     *
     * @param code value to be assigned to property code
     */
    public void setCode(String code) {
        this.code = code;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
