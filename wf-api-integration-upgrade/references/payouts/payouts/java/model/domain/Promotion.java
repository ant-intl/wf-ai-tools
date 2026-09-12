package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 促销信息。
 */
public class Promotion {

    /** 促销名称 */
    private String promotionName;

    /** 促销类型。可选值：`FEE_DISCOUNT`（手续费折扣） */
    private String promotionType;

    /** 促销生效时间（ISO 8601 格式，如 2024-01-01T00:00:00+08:00） */
    private String activeTime;

    /** 促销过期时间（ISO 8601 格式，如 2024-12-31T23:59:59+08:00） */
    private String expiredTime;

    /**
     * Getter method for property <tt>promotionName</tt>.
     *
     * @return property value of promotionName
     */
    public String getPromotionName() {
        return promotionName;
    }

    /**
     * Setter method for property <tt>promotionName</tt>.
     *
     * @param promotionName value to be assigned to property promotionName
     */
    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    /**
     * Getter method for property <tt>promotionType</tt>.
     *
     * @return property value of promotionType
     */
    public String getPromotionType() {
        return promotionType;
    }

    /**
     * Setter method for property <tt>promotionType</tt>.
     *
     * @param promotionType value to be assigned to property promotionType
     */
    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    /**
     * Getter method for property <tt>activeTime</tt>.
     *
     * @return property value of activeTime
     */
    public String getActiveTime() {
        return activeTime;
    }

    /**
     * Setter method for property <tt>activeTime</tt>.
     *
     * @param activeTime value to be assigned to property activeTime
     */
    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    /**
     * Getter method for property <tt>expiredTime</tt>.
     *
     * @return property value of expiredTime
     */
    public String getExpiredTime() {
        return expiredTime;
    }

    /**
     * Setter method for property <tt>expiredTime</tt>.
     *
     * @param expiredTime value to be assigned to property expiredTime
     */
    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
