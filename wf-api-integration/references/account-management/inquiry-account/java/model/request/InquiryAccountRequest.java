package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst inquiryAccount 请求对象
 *
 * <p>用于查询账户信息，包括账户类型、账号、激活状态、币种等。
 *
 */
public class InquiryAccountRequest {

    /**
     * 查询账号类型（必填）。
     * 可取值：RECEIVE_ACCOUNT、VIRTUAL_ACCOUNT、ALIPAY_WALLET、ALIPAY_SHADOW_WALLET、ALIPAY_ORIGIN_WALLET
     */
    private String accountType;

    /**
     * 集成商分配给注册用户的唯一用户ID。
     * accountType 为 RECEIVE_ACCOUNT 或 ALIPAY_WALLET 时必填。
     * 最大长度：64
     */
    private String referenceCustomerId;

    /**
     * 万里汇账户唯一标识。
     * accountType 为 ALIPAY_SHADOW_WALLET 时必填。
     * 最大长度：64
     */
    private String accountId;

    /**
     * OAuth 访问令牌。
     * accountType 为 VIRTUAL_ACCOUNT 时必填。
     * 最大长度：64
     */
    private String accessToken;

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
     * Getter method for property <tt>referenceCustomerId</tt>.
     *
     * @return property value of referenceCustomerId
     */
    public String getReferenceCustomerId() {
        return referenceCustomerId;
    }

    /**
     * Setter method for property <tt>referenceCustomerId</tt>.
     *
     * @param referenceCustomerId value to be assigned to property referenceCustomerId
     */
    public void setReferenceCustomerId(String referenceCustomerId) {
        this.referenceCustomerId = referenceCustomerId;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
