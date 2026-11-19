package {basePackage}.wf.model.request;


/** list_rfis request。可选字段保持 null；完整约束见对应 GUIDE。 */
public class ListRfisRequest {

    /** Number of records to return per page. Value range: 1–100. Defaults to 20 when omitted. */
    private Integer limit;

    /** Pagination token for turning pages. Omit it on the first request; for subsequent pages, pass the nextCursor or prevCursor returned by the previous response. */
    private String cursor;

    /** Start of the creation-time range to filter by, inclusive. ISO 8601 UTC format (ending with Z). See Data Types. */
    private String fromCreatedAt;

    /** End of the creation-time range to filter by, inclusive. ISO 8601 UTC format (ending with Z). See Data Types. */
    private String toCreatedAt;

    /** Filters results by RFI status. When omitted, RFIs of all statuses are returned. See RfiStatus. */
    private String status;

    /** Filters results by RFI type. When omitted, RFIs of all types are returned. - `KYC_RFI`: Information request raised during the KYC process. - `KYC_ONGOING_CDD_RFI`: Information request raised during ongoing customer due diligence. */
    private String rfiType;

    /** Filters results by account ID. When omitted, returns all RFIs for the authenticated account, or for the proxied account specified in the account-id header. */
    private String accountId;

    public Integer getLimit() { return limit; }

    public void setLimit(Integer limit) { this.limit = limit; }

    public String getCursor() { return cursor; }

    public void setCursor(String cursor) { this.cursor = cursor; }

    public String getFromCreatedAt() { return fromCreatedAt; }

    public void setFromCreatedAt(String fromCreatedAt) { this.fromCreatedAt = fromCreatedAt; }

    public String getToCreatedAt() { return toCreatedAt; }

    public void setToCreatedAt(String toCreatedAt) { this.toCreatedAt = toCreatedAt; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getRfiType() { return rfiType; }

    public void setRfiType(String rfiType) { this.rfiType = rfiType; }

    public String getAccountId() { return accountId; }

    public void setAccountId(String accountId) { this.accountId = accountId; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "ListRfisRequest[REDACTED]"; }
}
