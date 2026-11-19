package {basePackage}.wf.model.response;

import java.util.List;
import {basePackage}.wf.model.domain.RfiQuestion;

/** retrieve_an_rfi response。可选字段保持 null；完整约束见对应 GUIDE。 */
public class RetrieveRfiResponse {

    /** See Result. */
    private Result result;

    /** Unique identifier of the RFI. */
    private String id;

    /** ID of the account the RFI belongs to. */
    private String accountId;

    /** Type of the RFI, indicating the business scenario it originates from. See RfiType. */
    private String rfiType;

    /** Current status of the RFI. See RfiStatus. */
    private String status;

    /** Human-readable, top-level instruction describing what the merchant needs to provide. Returned only when the RFI has a top-level instruction. */
    private String instruction;

    /** Deadline for answering the RFI; the RFI is automatically marked EXPIRED once passed. ISO 8601 UTC format (ending with Z). Returned only when the RFI has a deadline. */
    private String expiresAt;

    /** List of questions (the requested information) the merchant must answer. Returned only when the RFI has question details. */
    private List<RfiQuestion> questions;

    /** Time the RFI was created. ISO 8601 UTC format (ending with Z). */
    private String createdAt;

    /** Time the RFI was last updated. ISO 8601 UTC format (ending with Z). */
    private String updatedAt;

    public Result getResult() { return result; }

    public void setResult(Result result) { this.result = result; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getAccountId() { return accountId; }

    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getRfiType() { return rfiType; }

    public void setRfiType(String rfiType) { this.rfiType = rfiType; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getInstruction() { return instruction; }

    public void setInstruction(String instruction) { this.instruction = instruction; }

    public String getExpiresAt() { return expiresAt; }

    public void setExpiresAt(String expiresAt) { this.expiresAt = expiresAt; }

    public List<RfiQuestion> getQuestions() { return questions; }

    public void setQuestions(List<RfiQuestion> questions) { this.questions = questions; }

    public String getCreatedAt() { return createdAt; }

    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "RetrieveRfiResponse[REDACTED]"; }
}
