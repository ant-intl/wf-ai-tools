package {basePackage}.wf.model.domain;

import java.util.Map;

/** CardSummary 领域结构。可选字段保持 null；完整约束见对应 GUIDE。 */
public class CardSummary {

    /** Unique card identifier. Max 64 characters. */
    private String id;

    /** Current card status. */
    private String status;

    /** Creation timestamp. ISO 8601 extended format. */
    private String createdAt;

    /** Associated cardholder ID. Max 64 characters. */
    private String cardholderId;

    /** Card media type (VIRTUAL or PHYSICAL). */
    private String mediaType;

    /** Card network brand (VISA or MASTERCARD). */
    private String cardBrand;

    /** Masked card number. Max 19 characters. */
    private String cardNumber;

    /** Last 4 digits of the card number. Max 4 characters. */
    private String last4Digits;

    /** Merchant-defined friendly name. Max 120 characters. */
    private String cardNickName;

    /** Merchant-defined metadata. Up to 50 keys. */
    private Map<String, Object> metadata;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getCreatedAt() { return createdAt; }

    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getCardholderId() { return cardholderId; }

    public void setCardholderId(String cardholderId) { this.cardholderId = cardholderId; }

    public String getMediaType() { return mediaType; }

    public void setMediaType(String mediaType) { this.mediaType = mediaType; }

    public String getCardBrand() { return cardBrand; }

    public void setCardBrand(String cardBrand) { this.cardBrand = cardBrand; }

    public String getCardNumber() { return cardNumber; }

    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getLast4Digits() { return last4Digits; }

    public void setLast4Digits(String last4Digits) { this.last4Digits = last4Digits; }

    public String getCardNickName() { return cardNickName; }

    public void setCardNickName(String cardNickName) { this.cardNickName = cardNickName; }

    public Map<String, Object> getMetadata() { return metadata; }

    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "CardSummary[REDACTED]"; }
}
