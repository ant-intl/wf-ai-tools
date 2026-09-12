package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 账户验证函（Account Verification Letter）参数。
 *
 * <p>当 {@code confirmationLetterType} 为 {@code ACCOUNT_VERIFICATION_LETTER} 时使用。
 */
public class AccountVerificationLetterParams {

    /**
     * 全球账户 ID。
     * <p>指定要验证的全球账户。
     * <p>最大 64 字符。
     */
    private String globalAccountId;

    public AccountVerificationLetterParams() {
    }

    /**
     * Getter method for property <tt>globalAccountId</tt>.
     *
     * @return property value of globalAccountId
     */
    public String getGlobalAccountId() {
        return globalAccountId;
    }

    /**
     * Setter method for property <tt>globalAccountId</tt>.
     *
     * @param globalAccountId value to be assigned to property globalAccountId
     */
    public void setGlobalAccountId(String globalAccountId) {
        this.globalAccountId = globalAccountId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
