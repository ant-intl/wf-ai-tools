package {basePackage}.wf.model.request;


/** list_reports request。可选字段保持 null；完整约束见对应 GUIDE。 */
public class ListReportsRequest {

    /** Maximum number of records to return per page. Range: 1–100. Defaults to 20 if not provided. */
    private Integer limit;

    /** Pagination cursor. Omit for the first page; pass the nextCursor or prevCursor from a previous response to fetch the adjacent page. Max length: 2048 characters. */
    private String cursor;

    /** Filter for report tasks created at or after this time, following the ISO 8601 standard. See Data Types. */
    private String fromCreatedAt;

    /** Filter for report tasks created at or before this time, following the ISO 8601 standard. See Data Types. */
    private String toCreatedAt;

    public Integer getLimit() { return limit; }

    public void setLimit(Integer limit) { this.limit = limit; }

    public String getCursor() { return cursor; }

    public void setCursor(String cursor) { this.cursor = cursor; }

    public String getFromCreatedAt() { return fromCreatedAt; }

    public void setFromCreatedAt(String fromCreatedAt) { this.fromCreatedAt = fromCreatedAt; }

    public String getToCreatedAt() { return toCreatedAt; }

    public void setToCreatedAt(String toCreatedAt) { this.toCreatedAt = toCreatedAt; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "ListReportsRequest[REDACTED]"; }
}
