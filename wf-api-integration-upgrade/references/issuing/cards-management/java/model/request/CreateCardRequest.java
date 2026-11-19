package {basePackage}.wf.model.request;

import java.util.List;
import java.util.Map;
import {basePackage}.wf.model.domain.Address;
import {basePackage}.wf.model.domain.CardAuthorizationControl;
import {basePackage}.wf.model.domain.CardTransactionLimitsInput;

/** create_a_card request。可选字段保持 null；完整约束见对应 GUIDE。 */
public class CreateCardRequest {

    /** Required. Idempotency key for this card-creation request. Assign a unique value per request to safely retry without issuing duplicate cards. Must be unique within your partnerId. Max 64 characters. */
    private String requestId;

    /** Required. Card network brand. - `MASTERCARD`: Mastercard network. */
    private String cardBrand;

    /** Associated cardholder ID. Optional — a card can be created without a cardholder. Max 64 characters. */
    private String cardholderId;

    /** Card media type. Defaults to VIRTUAL. - `VIRTUAL`: Virtual card. */
    private String mediaType;

    /** Associated budget account ID. Can only be set at creation and cannot be changed later. Max 64 characters. */
    private String budgetId;

    /** Merchant-defined friendly name for the card. Max 120 characters. */
    private String cardNickName;

    /** Free-text note. Max 512 characters. */
    private String note;

    /** Card purpose description. Max 64 characters. */
    private String purpose;

    /** Required. Cardholder&#x27;s email address, used for 3DS authentication. Must be a valid email format. Max 256 characters. */
    private String email;

    /** Required. Cardholder&#x27;s mobile phone number, used for 3DS authentication. Include the international dialing code (for example, +6512345678). Max 32 characters. */
    private String mobilePhone;

    /** Required. Billing address. See Data Types. */
    private Address billingAddress;

    /** Transaction limit configuration, aggregated by currency. Maximum 5 entries. At creation, each limit only needs interval and rangeAmount. */
    private List<CardTransactionLimitsInput> transactionLimits;

    /** Authorization control rules governing when and where the card can transact. */
    private CardAuthorizationControl authorizationControl;

    /** Merchant-defined metadata. Up to 50 keys. */
    private Map<String, Object> metadata;

    /** Whether to activate the card synchronously. Defaults to asynchronous issuance. */
    private Boolean enableSyncActivation;

    /** Whether to waive the 3DS challenge (true = transactions require no additional 3DS verification). */
    private Boolean threeDsFrictionless;

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

    public String getCardBrand() { return cardBrand; }

    public void setCardBrand(String cardBrand) { this.cardBrand = cardBrand; }

    public String getCardholderId() { return cardholderId; }

    public void setCardholderId(String cardholderId) { this.cardholderId = cardholderId; }

    public String getMediaType() { return mediaType; }

    public void setMediaType(String mediaType) { this.mediaType = mediaType; }

    public String getBudgetId() { return budgetId; }

    public void setBudgetId(String budgetId) { this.budgetId = budgetId; }

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

    public Boolean getEnableSyncActivation() { return enableSyncActivation; }

    public void setEnableSyncActivation(Boolean enableSyncActivation) { this.enableSyncActivation = enableSyncActivation; }

    public Boolean getThreeDsFrictionless() { return threeDsFrictionless; }

    public void setThreeDsFrictionless(Boolean threeDsFrictionless) { this.threeDsFrictionless = threeDsFrictionless; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "CreateCardRequest[REDACTED]"; }
}
