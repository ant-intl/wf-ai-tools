package {basePackage}.wf.model.request;


/** list_batch_payout_items request。可选字段保持 null；完整约束见对应 GUIDE。 */
public class ListBatchPayoutItemsRequest {

    /** Required. Unique identifier of the target existing batch payout. */
    private String id;

    /** Number of results per page. Value range: 1–100. Defaults to 20 if omitted. */
    private Integer limit;

    /** Pagination cursor. Omit on the first call; pass the nextCursor (or prevCursor) returned by a previous call to move to the next (or previous) page. Max length: 256 characters. */
    private String cursor;

    /** Filter items by latest status. See BatchPayoutItemStatus. */
    private String status;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public Integer getLimit() { return limit; }

    public void setLimit(Integer limit) { this.limit = limit; }

    public String getCursor() { return cursor; }

    public void setCursor(String cursor) { this.cursor = cursor; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "ListBatchPayoutItemsRequest[REDACTED]"; }
}
