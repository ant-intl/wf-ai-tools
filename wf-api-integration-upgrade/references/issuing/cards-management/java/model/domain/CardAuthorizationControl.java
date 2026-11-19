package {basePackage}.wf.model.domain;

import java.util.List;

/** CardAuthorizationControl 领域结构。可选字段保持 null；完整约束见对应 GUIDE。 */
public class CardAuthorizationControl {

    /** Card activation time. Transactions before this time are declined. ISO 8601 format; must be later than the current time. */
    private String cardActiveAt;

    /** Card expiry time. Transactions after this time are declined. ISO 8601 format; must be later than cardActiveAt. */
    private String cardCancelAt;

    /** MCC allowlist. Only transactions within these 4-digit MCCs are permitted. Mutually exclusive with blockedMerchantCategories. */
    private List<String> allowedMerchantCategories;

    /** MCC blocklist. Transactions within these 4-digit MCCs are declined. Mutually exclusive with allowedMerchantCategories. */
    private List<String> blockedMerchantCategories;

    /** Maximum number of authorizations allowed for the card. Further authorizations are declined once the limit is reached. Minimum 1. */
    private Integer allowedAuthTimes;

    /** Allowed transaction currency allowlist. Each item is a 3-letter ISO 4217 code. Omit to allow all currencies. */
    private List<String> allowedCurrencies;

    public String getCardActiveAt() { return cardActiveAt; }

    public void setCardActiveAt(String cardActiveAt) { this.cardActiveAt = cardActiveAt; }

    public String getCardCancelAt() { return cardCancelAt; }

    public void setCardCancelAt(String cardCancelAt) { this.cardCancelAt = cardCancelAt; }

    public List<String> getAllowedMerchantCategories() { return allowedMerchantCategories; }

    public void setAllowedMerchantCategories(List<String> allowedMerchantCategories) { this.allowedMerchantCategories = allowedMerchantCategories; }

    public List<String> getBlockedMerchantCategories() { return blockedMerchantCategories; }

    public void setBlockedMerchantCategories(List<String> blockedMerchantCategories) { this.blockedMerchantCategories = blockedMerchantCategories; }

    public Integer getAllowedAuthTimes() { return allowedAuthTimes; }

    public void setAllowedAuthTimes(Integer allowedAuthTimes) { this.allowedAuthTimes = allowedAuthTimes; }

    public List<String> getAllowedCurrencies() { return allowedCurrencies; }

    public void setAllowedCurrencies(List<String> allowedCurrencies) { this.allowedCurrencies = allowedCurrencies; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "CardAuthorizationControl[REDACTED]"; }
}
