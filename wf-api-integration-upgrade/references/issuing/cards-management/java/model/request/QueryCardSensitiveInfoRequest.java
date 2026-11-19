package {basePackage}.wf.model.request;


/** query_card_sensitive_info request。可选字段保持 null；完整约束见对应 GUIDE。 */
public class QueryCardSensitiveInfoRequest {

    /** Required. Unique card identifier. Max 64 characters. */
    private String id;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "QueryCardSensitiveInfoRequest[REDACTED]"; }
}
