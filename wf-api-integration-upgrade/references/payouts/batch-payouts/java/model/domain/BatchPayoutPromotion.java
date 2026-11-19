package {basePackage}.wf.model.domain;


/** BatchPayoutPromotion 领域结构。可选字段保持 null；完整约束见对应 GUIDE。 */
public class BatchPayoutPromotion {

    /** Promotion name. */
    private String promotionName;

    /** Promotion type. - `FEE_DISCOUNT`: Service fee discount. */
    private String promotionType;

    /** Promotion activation time. ISO 8601 extended format. */
    private String activeTime;

    /** Promotion expiration time. ISO 8601 extended format. */
    private String expiredTime;

    public String getPromotionName() { return promotionName; }

    public void setPromotionName(String promotionName) { this.promotionName = promotionName; }

    public String getPromotionType() { return promotionType; }

    public void setPromotionType(String promotionType) { this.promotionType = promotionType; }

    public String getActiveTime() { return activeTime; }

    public void setActiveTime(String activeTime) { this.activeTime = activeTime; }

    public String getExpiredTime() { return expiredTime; }

    public void setExpiredTime(String expiredTime) { this.expiredTime = expiredTime; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "BatchPayoutPromotion[REDACTED]"; }
}
