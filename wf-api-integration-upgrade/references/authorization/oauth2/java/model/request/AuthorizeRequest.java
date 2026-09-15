package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst authorize 请求参数对象。
 *
 * <p>用于构造授权 URL，将用户浏览器重定向到 WorldFirst 授权页面。
 * 此对象不用于 HTTP API 调用，仅用于 {@code OAuth2Service.buildAuthorizationUrl()}。
 */
public class AuthorizeRequest {

    /**
     * 应用的 Client ID。
     * <p>最大长度：64 字符。
     */
    private String clientId;

    /**
     * 授权回调地址（HTTPS 格式）。
     * <p>必须与 OAuth 应用注册时填写的 URI 完全匹配。
     * <p>最大长度：2,048 字符。
     */
    private String redirectUri;

    /**
     * 请求的权限范围，多个权限以空格分隔。
     * <p>格式：{@code resource:action}，如 {@code "payouts:write accounts:read"}。
     */
    private String scope;

    /**
     * 防 CSRF 攻击的随机字符串。
     * <p>建议最少 32 字符，最大 64 字符。
     * <p>此值将在重定向回调中原样返回，用于验证请求合法性。
     */
    private String state;

    /**
     * 外部商户标识。
     * <p>同一个 WorldFirst 账户可以授权多个平台店铺。
     * <p>最大长度：64 字符。
     */
    private String referenceAccountId;

    public AuthorizeRequest() {
    }

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
     * Getter method for property <tt>redirectUri</tt>.
     *
     * @return property value of redirectUri
     */
    public String getRedirectUri() {
        return redirectUri;
    }

    /**
     * Setter method for property <tt>redirectUri</tt>.
     *
     * @param redirectUri value to be assigned to property redirectUri
     */
    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
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

    /**
     * Getter method for property <tt>state</tt>.
     *
     * @return property value of state
     */
    public String getState() {
        return state;
    }

    /**
     * Setter method for property <tt>state</tt>.
     *
     * @param state value to be assigned to property state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Getter method for property <tt>referenceAccountId</tt>.
     *
     * @return property value of referenceAccountId
     */
    public String getReferenceAccountId() {
        return referenceAccountId;
    }

    /**
     * Setter method for property <tt>referenceAccountId</tt>.
     *
     * @param referenceAccountId value to be assigned to property referenceAccountId
     */
    public void setReferenceAccountId(String referenceAccountId) {
        this.referenceAccountId = referenceAccountId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
