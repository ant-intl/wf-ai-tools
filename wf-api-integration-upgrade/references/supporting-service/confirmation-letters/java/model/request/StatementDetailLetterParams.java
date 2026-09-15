package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 交易证明函（Statement Detail Letter）参数。
 *
 * <p>当 {@code confirmationLetterType} 为 {@code STATEMENT_DETAIL_LETTER} 时使用。
 */
public class StatementDetailLetterParams {

    /**
     * 对账单 ID。
     * <p>指定要生成确认函的对账单。
     * <p>最大 64 字符。
     */
    private String statementId;

    /**
     * 是否包含电子签名。
     * <p>省略时默认为 {@code false}（无签名的 PDF）。
     */
    private Boolean electronicallySigned;

    /**
     * 生成确认函的语言。
     * <p>省略时默认为 {@code EN_GB}。
     * <p>最大 10 字符。
     */
    private String language;

    public StatementDetailLetterParams() {
    }

    /**
     * Getter method for property <tt>statementId</tt>.
     *
     * @return property value of statementId
     */
    public String getStatementId() {
        return statementId;
    }

    /**
     * Setter method for property <tt>statementId</tt>.
     *
     * @param statementId value to be assigned to property statementId
     */
    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    /**
     * Getter method for property <tt>electronicallySigned</tt>.
     *
     * @return property value of electronicallySigned
     */
    public Boolean getElectronicallySigned() {
        return electronicallySigned;
    }

    /**
     * Setter method for property <tt>electronicallySigned</tt>.
     *
     * @param electronicallySigned value to be assigned to property electronicallySigned
     */
    public void setElectronicallySigned(Boolean electronicallySigned) {
        this.electronicallySigned = electronicallySigned;
    }

    /**
     * Getter method for property <tt>language</tt>.
     *
     * @return property value of language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Setter method for property <tt>language</tt>.
     *
     * @param language value to be assigned to property language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
