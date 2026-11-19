package {basePackage}.wf.model.response;

import java.util.List;
import java.util.Map;
import {basePackage}.wf.model.domain.Address;
import {basePackage}.wf.model.domain.CardAuthorizationControl;
import {basePackage}.wf.model.domain.CardTransactionLimits;

/** query_a_card response。可选字段保持 null；完整约束见对应 GUIDE。 */
public class QueryCardResponse {

    /** See Result. */
    private Result result;

    /** Unique card identifier. Max 64 characters. */
    private String id;

    /** Current card status. - `PENDING`: Card application submitted, awaiting review or production. - `ACTIVE`: Card is activated and ready for transactions. - `FROZEN`: Card is frozen; transactions are paused and can be resumed. - `FAILED`: Card creation failed (terminal). - `CANCELLED`: Card cancelled (terminal). */
    private String status;

    /** Associated cardholder ID. Max 64 characters. */
    private String cardholderId;

    /** Card media type. - `VIRTUAL`: Virtual card. - `PHYSICAL`: Physical card. */
    private String mediaType;

    /** Creation timestamp. ISO 8601 extended format. */
    private String createdAt;

    /** Card network brand. - `VISA`: Visa network. - `MASTERCARD`: Mastercard network. */
    private String cardBrand;

    /** Associated budget account ID. Max 64 characters. */
    private String budgetId;

    /** Masked card number. Returned only when status is ACTIVE. Max 19 characters. */
    private String cardNumber;

    /** Card expiry date. Returned only when status is ACTIVE. Format MM/YY. */
    private String expiryDate;

    /** Last 4 digits of the card number. Max 4 characters. */
    private String last4Digits;

    /** Merchant-defined friendly name. Max 120 characters. */
    private String cardNickName;

    /** Free-text note. Max 512 characters. */
    private String note;

    /** Card purpose description. Max 64 characters. */
    private String purpose;

    /** Email associated with the card. Max 256 characters. */
    private String email;

    /** Mobile phone associated with the card. Max 32 characters. */
    private String mobilePhone;

    /** Billing address. See Data Types. */
    private Address billingAddress;

    /** Transaction limit configuration, aggregated by currency. Maximum 5 entries. At creation, each limit only needs interval and rangeAmount. */
    private List<CardTransactionLimits> transactionLimits;

    /** Authorization control rules governing when and where the card can transact. */
    private CardAuthorizationControl authorizationControl;

    /** Merchant-defined metadata. Up to 50 keys. */
    private Map<String, Object> metadata;

    public Result getResult() { return result; }

    public void setResult(Result result) { this.result = result; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getCardholderId() { return cardholderId; }

    public void setCardholderId(String cardholderId) { this.cardholderId = cardholderId; }

    public String getMediaType() { return mediaType; }

    public void setMediaType(String mediaType) { this.mediaType = mediaType; }

    public String getCreatedAt() { return createdAt; }

    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getCardBrand() { return cardBrand; }

    public void setCardBrand(String cardBrand) { this.cardBrand = cardBrand; }

    public String getBudgetId() { return budgetId; }

    public void setBudgetId(String budgetId) { this.budgetId = budgetId; }

    public String getCardNumber() { return cardNumber; }

    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getExpiryDate() { return expiryDate; }

    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }

    public String getLast4Digits() { return last4Digits; }

    public void setLast4Digits(String last4Digits) { this.last4Digits = last4Digits; }

    public String getCardNickName() { return cardNickName; }

    public void setCardNickName(String cardNickName) { this.cardNickName = cardNickName; }

    public String getNote() { return note; }

    public void setNote(String note) { this.note = note; }

    public String getPurpose() { return purpose; }

    public void setPurpose(String purpose) { this.purpose = purpose; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getMobilePhone() { return mobilePhone; }

    public void setMobilePhone(String mobilePhone) { this.mobilePhone = mobilePhone; }

    public Address getBillingAddress() { return billingAddress; }

    public void setBillingAddress(Address billingAddress) { this.billingAddress = billingAddress; }

    public List<CardTransactionLimits> getTransactionLimits() { return transactionLimits; }

    public void setTransactionLimits(List<CardTransactionLimits> transactionLimits) { this.transactionLimits = transactionLimits; }

    public CardAuthorizationControl getAuthorizationControl() { return authorizationControl; }

    public void setAuthorizationControl(CardAuthorizationControl authorizationControl) { this.authorizationControl = authorizationControl; }

    public Map<String, Object> getMetadata() { return metadata; }

    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "QueryCardResponse[REDACTED]"; }
}
