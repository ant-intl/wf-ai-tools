package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst create_a_confirmation_letter 请求对象。
 *
 * <p>创建确认函下载任务。任务异步处理，需通过 query 接口轮询获取结果。
 * 支持两种确认函类型：
 * <ul>
 *   <li>{@code STATEMENT_DETAIL_LETTER} — 基于对账单记录的交易证明函，需提供 {@link StatementDetailLetterParams}</li>
 *   <li>{@code ACCOUNT_VERIFICATION_LETTER} — 全球账户验证信息，需提供 {@link AccountVerificationLetterParams}</li>
 * </ul>
 */
public class CreateConfirmationLetterRequest {

    /**
     * 幂等键。
     * <p>每次请求应使用唯一值（如 UUID），避免因重试导致重复创建任务。
     * <p>最大 64 字符。
     */
    private String requestId;

    /**
     * 确认函类型。
     * <p>可选值：
     * <ul>
     *   <li>{@code STATEMENT_DETAIL_LETTER} — 交易证明函</li>
     *   <li>{@code ACCOUNT_VERIFICATION_LETTER} — 账户验证函</li>
     * </ul>
     */
    private String confirmationLetterType;

    /**
     * 交易证明函参数。
     * <p>当 {@code confirmationLetterType} 为 {@code STATEMENT_DETAIL_LETTER} 时必填。
     */
    private StatementDetailLetterParams statementDetailLetterParams;

    /**
     * 账户验证函参数。
     * <p>当 {@code confirmationLetterType} 为 {@code ACCOUNT_VERIFICATION_LETTER} 时必填。
     */
    private AccountVerificationLetterParams accountVerificationLetterParams;

    public CreateConfirmationLetterRequest() {
    }

    /**
     * Getter method for property <tt>requestId</tt>.
     *
     * @return property value of requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Setter method for property <tt>requestId</tt>.
     *
     * @param requestId value to be assigned to property requestId
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * Getter method for property <tt>confirmationLetterType</tt>.
     *
     * @return property value of confirmationLetterType
     */
    public String getConfirmationLetterType() {
        return confirmationLetterType;
    }

    /**
     * Setter method for property <tt>confirmationLetterType</tt>.
     *
     * @param confirmationLetterType value to be assigned to property confirmationLetterType
     */
    public void setConfirmationLetterType(String confirmationLetterType) {
        this.confirmationLetterType = confirmationLetterType;
    }

    /**
     * Getter method for property <tt>statementDetailLetterParams</tt>.
     *
     * @return property value of statementDetailLetterParams
     */
    public StatementDetailLetterParams getStatementDetailLetterParams() {
        return statementDetailLetterParams;
    }

    /**
     * Setter method for property <tt>statementDetailLetterParams</tt>.
     *
     * @param statementDetailLetterParams value to be assigned to property statementDetailLetterParams
     */
    public void setStatementDetailLetterParams(StatementDetailLetterParams statementDetailLetterParams) {
        this.statementDetailLetterParams = statementDetailLetterParams;
    }

    /**
     * Getter method for property <tt>accountVerificationLetterParams</tt>.
     *
     * @return property value of accountVerificationLetterParams
     */
    public AccountVerificationLetterParams getAccountVerificationLetterParams() {
        return accountVerificationLetterParams;
    }

    /**
     * Setter method for property <tt>accountVerificationLetterParams</tt>.
     *
     * @param accountVerificationLetterParams value to be assigned to property accountVerificationLetterParams
     */
    public void setAccountVerificationLetterParams(AccountVerificationLetterParams accountVerificationLetterParams) {
        this.accountVerificationLetterParams = accountVerificationLetterParams;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
