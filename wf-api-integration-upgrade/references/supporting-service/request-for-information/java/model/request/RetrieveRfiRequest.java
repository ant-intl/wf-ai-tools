package {basePackage}.wf.model.request;


/** retrieve_an_rfi request。可选字段保持 null；完整约束见对应 GUIDE。 */
public class RetrieveRfiRequest {

    /** Required. Unique identifier of the RFI to retrieve. This is the only accepted field in the request body. */
    private String id;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "RetrieveRfiRequest[REDACTED]"; }
}
