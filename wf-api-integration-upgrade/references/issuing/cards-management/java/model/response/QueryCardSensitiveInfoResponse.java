package {basePackage}.wf.model.response;


/** query_card_sensitive_info response。可选字段保持 null；完整约束见对应 GUIDE。 */
public class QueryCardSensitiveInfoResponse {

    /** See Result. */
    private Result result;

    /** Unique card identifier. Max 64 characters. */
    private String id;

    /** User account ID. Max 64 characters. */
    private String accountId;

    /** Full card number. Max 19 characters. */
    private String cardNumber;

    /** CVV security code. Max 4 characters. */
    private String cvv;

    /** Card expiry date. Format MM/YY. */
    private String expiryDate;

    public Result getResult() { return result; }

    public void setResult(Result result) { this.result = result; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getAccountId() { return accountId; }

    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getCardNumber() { return cardNumber; }

    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getCvv() { return cvv; }

    public void setCvv(String cvv) { this.cvv = cvv; }

    public String getExpiryDate() { return expiryDate; }

    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "QueryCardSensitiveInfoResponse[REDACTED]"; }
}
