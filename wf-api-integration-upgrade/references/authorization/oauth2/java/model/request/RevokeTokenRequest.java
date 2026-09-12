package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst revoke_a_token 请求对象。
 *
 * <p>用于撤销已发放的访问令牌。撤销后令牌立即失效，无法再用于 API 调用。
 */
public class RevokeTokenRequest {

    /**
     * 要撤销的访问令牌。
     * <p>最大长度：512 字符。
     */
    private String token;

    public RevokeTokenRequest() {
    }

    /**
     * Getter method for property <tt>token</tt>.
     *
     * @return property value of token
     */
    public String getToken() {
        return token;
    }

    /**
     * Setter method for property <tt>token</tt>.
     *
     * @param token value to be assigned to property token
     */
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
