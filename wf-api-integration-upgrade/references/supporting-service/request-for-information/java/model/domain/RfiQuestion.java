package {basePackage}.wf.model.domain;


/** RfiQuestion 领域结构。可选字段保持 null；完整约束见对应 GUIDE。 */
public class RfiQuestion {

    /** Unique identifier of the question. Echo it back in answers[].questionId when responding. */
    private String id;

    /** Machine anchor that locates the corresponding required form field (for example, basicInformation.companyName.value). A free-form string, not an enum; its value varies by business scenario. */
    private String auditKey;

    /** Returned only when the review provides a specific description. */
    private String description;

    /** Type of answer expected, which determines whether to provide files, text, or textList when responding. - `ATTACHMENT`: File-upload question; provide files. - `TEXT`: Free-text question; provide text. - `TEXTLIST`: Text-list question; provide textList. */
    private String answerType;

    /** Whether an answer to this question is mandatory. */
    private Boolean required;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getAuditKey() { return auditKey; }

    public void setAuditKey(String auditKey) { this.auditKey = auditKey; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getAnswerType() { return answerType; }

    public void setAnswerType(String answerType) { this.answerType = answerType; }

    public Boolean getRequired() { return required; }

    public void setRequired(Boolean required) { this.required = required; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "RfiQuestion[REDACTED]"; }
}
