package {basePackage}.wf.model.domain;

import java.util.List;

/** RfiAnswer 领域结构。可选字段保持 null；完整约束见对应 GUIDE。 */
public class RfiAnswer {

    /** Required. Identifier of the question being answered, matching questions[].id. */
    private String questionId;

    /** List of uploaded file objects. Required when the corresponding question&#x27;s answerType is ATTACHMENT. Provide up to 10 files, each up to 7 MB. Upload files first via Upload a File to obtain each fileId. */
    private List<RfiFile> files;

    /** Free-text answer content. Required when the corresponding question&#x27;s answerType is TEXT. */
    private String text;

    /** Text-list answer content. Required when the corresponding question&#x27;s answerType is TEXTLIST. Provide up to 20 elements. */
    private List<String> textList;

    public String getQuestionId() { return questionId; }

    public void setQuestionId(String questionId) { this.questionId = questionId; }

    public List<RfiFile> getFiles() { return files; }

    public void setFiles(List<RfiFile> files) { this.files = files; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public List<String> getTextList() { return textList; }

    public void setTextList(List<String> textList) { this.textList = textList; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "RfiAnswer[REDACTED]"; }
}
