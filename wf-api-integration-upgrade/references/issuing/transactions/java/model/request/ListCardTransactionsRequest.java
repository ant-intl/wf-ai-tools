package {basePackage}.wf.model.request;

import java.util.List;

/** list_card_transactions request。可选字段保持 null；完整约束见对应 GUIDE。 */
public class ListCardTransactionsRequest {

    /** Required. Maximum number of items per page. Value range 1–100 (10 recommended). */
    private Integer limit;

    /** Required. Query start time. ISO 8601 extended format. The range between fromCreatedAt and toCreatedAt must not exceed 100 days. */
    private String fromCreatedAt;

    /** Required. Query end time. ISO 8601 extended format. The range must not exceed 100 days. */
    private String toCreatedAt;

    /** Pagination cursor taken from the previous response&#x27;s nextCursor or prevCursor. Omit for the first page. Max 256 characters. */
    private String cursor;

    /** Filter by the latest transaction status. Maximum 9 items. - `WAITING_DEDUCT`: Awaiting deduction. - `PROCESSING`: Processing. - `WAITING_CLEARANCE`: Awaiting clearance. - `SUCCESS`: Transaction succeeded. - `FAILED`: Transaction failed. - `PARTIAL_CANCELLED`: Partial authorization cancelled. - `CANCELLED`: Authorization cancelled. - `PARTIAL_REFUNDED`: Partially refunded. - `REFUNDED`: Refunded. */
    private List<String> statuses;

    /** Filter by card number. Pass either 4 digits (the last four) or the 16-digit full card number. */
    private String cardNo;

    /** Filter by merchant name. Max 256 characters. */
    private String merchantName;

    /** Filter by transaction ID. Max 64 characters. */
    private String id;

    /** Filter by card nickname. Max 128 characters. */
    private String cardNickName;

    /** Filter by budget account IDs. Maximum 50 items. */
    private List<String> budgetAccountIds;

    /** Filter by the associated Card resource ID. Max 64 characters. */
    private String cardId;

    public Integer getLimit() { return limit; }

    public void setLimit(Integer limit) { this.limit = limit; }

    public String getFromCreatedAt() { return fromCreatedAt; }

    public void setFromCreatedAt(String fromCreatedAt) { this.fromCreatedAt = fromCreatedAt; }

    public String getToCreatedAt() { return toCreatedAt; }

    public void setToCreatedAt(String toCreatedAt) { this.toCreatedAt = toCreatedAt; }

    public String getCursor() { return cursor; }

    public void setCursor(String cursor) { this.cursor = cursor; }

    public List<String> getStatuses() { return statuses; }

    public void setStatuses(List<String> statuses) { this.statuses = statuses; }

    public String getCardNo() { return cardNo; }

    public void setCardNo(String cardNo) { this.cardNo = cardNo; }

    public String getMerchantName() { return merchantName; }

    public void setMerchantName(String merchantName) { this.merchantName = merchantName; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getCardNickName() { return cardNickName; }

    public void setCardNickName(String cardNickName) { this.cardNickName = cardNickName; }

    public List<String> getBudgetAccountIds() { return budgetAccountIds; }

    public void setBudgetAccountIds(List<String> budgetAccountIds) { this.budgetAccountIds = budgetAccountIds; }

    public String getCardId() { return cardId; }

    public void setCardId(String cardId) { this.cardId = cardId; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "ListCardTransactionsRequest[REDACTED]"; }
}
