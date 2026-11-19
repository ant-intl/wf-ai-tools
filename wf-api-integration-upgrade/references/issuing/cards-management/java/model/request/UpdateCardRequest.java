package {basePackage}.wf.model.request;

import java.util.List;
import java.util.Map;
import {basePackage}.wf.model.domain.Address;
import {basePackage}.wf.model.domain.CardAuthorizationControl;
import {basePackage}.wf.model.domain.CardTransactionLimitsInput;

/** update_a_card request。可选字段保持 null；完整约束见对应 GUIDE。 */
public class UpdateCardRequest {

    /** Required. Idempotency key for this card-creation request. */
    private String requestId;

    /** Required. Unique card identifier. Max 64 characters. */
    private String id;

    /** Target card status. Pass only to change the card state. - `FROZEN`: Freeze the card for risk or security reasons. - `CANCELLED`: Cancel the card. This is terminal and cannot be undone. - `ACTIVE`: Unfreeze the card and resume transactions. */
    private String status;

    /** Card nickname. Max 120 characters. */
    private String cardNickName;

    /** Free-text note. Max 512 characters. */
    private String note;

    /** Card purpose description. Max 64 characters. */
    private String purpose;

    /** Email associated with the card. Max 256 characters. */
    private String email;

    /** Mobile phone associated with the card, including the international dialing code. Max 32 characters. */
    private String mobilePhone;

    /** Billing address. See Data Types. */
    private Address billingAddress;

    /** Transaction limit configuration, aggregated by currency. Maximum 5 entries. Replaced as a whole — pass the complete configuration. On update, each limit only needs interval and rangeAmount. */
    private List<CardTransactionLimitsInput> transactionLimits;

    /** Authorization control rules. Replaced as a whole — pass the complete configuration. */
    private CardAuthorizationControl authorizationControl;

    /** Merchant-defined metadata. Up to 50 keys. */
    private Map<String, Object> metadata;

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

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

    public List<CardTransactionLimitsInput> getTransactionLimits() { return transactionLimits; }

    public void setTransactionLimits(List<CardTransactionLimitsInput> transactionLimits) { this.transactionLimits = transactionLimits; }

    public CardAuthorizationControl getAuthorizationControl() { return authorizationControl; }

    public void setAuthorizationControl(CardAuthorizationControl authorizationControl) { this.authorizationControl = authorizationControl; }

    public Map<String, Object> getMetadata() { return metadata; }

    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "UpdateCardRequest[REDACTED]"; }
}
