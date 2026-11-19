package {basePackage}.wf.model.request;


/** list_cards request。可选字段保持 null；完整约束见对应 GUIDE。 */
public class ListCardsRequest {

    /** Number of records per page. Value range 1–100. Defaults to 20. */
    private Integer limit;

    /** Pagination cursor token taken from the previous response&#x27;s nextCursor or prevCursor. Omit for the first page. */
    private String cursor;

    /** Filter by card status. Omit to include all statuses (including CANCELLED). - `PENDING`: Card application submitted, awaiting review or production. - `ACTIVE`: Card is activated and ready for transactions. - `FROZEN`: Card is frozen; transactions are paused and can be resumed. - `FAILED`: Card creation failed (terminal). - `CANCELLED`: Card cancelled (terminal). */
    private String status;

    /** Filter by the last 4 digits of the card number (exact match). Max 4 characters. */
    private String last4Digits;

    /** Filter by card nickname (fuzzy match). Max 120 characters. */
    private String cardNickName;

    /** The unique ID assigned by WorldFirst to identify a budget account. When specified, only cards under this budget account are returned; if omitted, cards under the primary budget account are returned by default. */
    private String budgetId;

    public Integer getLimit() { return limit; }

    public void setLimit(Integer limit) { this.limit = limit; }

    public String getCursor() { return cursor; }

    public void setCursor(String cursor) { this.cursor = cursor; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getLast4Digits() { return last4Digits; }

    public void setLast4Digits(String last4Digits) { this.last4Digits = last4Digits; }

    public String getCardNickName() { return cardNickName; }

    public void setCardNickName(String cardNickName) { this.cardNickName = cardNickName; }

    public String getBudgetId() { return budgetId; }

    public void setBudgetId(String budgetId) { this.budgetId = budgetId; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "ListCardsRequest[REDACTED]"; }
}
