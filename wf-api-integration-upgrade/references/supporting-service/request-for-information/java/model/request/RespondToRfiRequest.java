package {basePackage}.wf.model.request;

import java.util.List;
import {basePackage}.wf.model.domain.RfiAnswer;

/** response_to_an_rfi request。可选字段保持 null；完整约束见对应 GUIDE。 */
public class RespondToRfiRequest {

    /** Required. Unique identifier of the RFI to respond to. */
    private String id;

    /** Required. List of answers; each element corresponds to one question in the RFI&#x27;s questions array. Provide 1–50 elements. */
    private List<RfiAnswer> answers;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public List<RfiAnswer> getAnswers() { return answers; }

    public void setAnswers(List<RfiAnswer> answers) { this.answers = answers; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "RespondToRfiRequest[REDACTED]"; }
}
