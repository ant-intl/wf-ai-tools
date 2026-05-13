package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * B2C 买家信息。
 *
 * <p>对应 WF submitTradeOrder 接口请求体中的 {@code buyer} 字段。
 * {@code referenceBuyerId}、{@code buyerName}、{@code buyerEmail} 三者至少传一个。
 *
 */
public class Buyer {

    /** 集成方分配的买家 ID，最大 64 字符 */
    private String referenceBuyerId;

    /** 买家姓名 */
    private BuyerName buyerName;

    /** 买家邮箱，最大 128 字符 */
    private String buyerEmail;

    /** 买家国家代码（ISO-3166，2 位字母） */
    private String buyerCountry;

    /** 买家电话号码，最大 24 字符 */
    private String buyerPhoneNo;

    /**
     * Getter method for property <tt>referenceBuyerId</tt>.
     *
     * @return property value of referenceBuyerId
     */
    public String getReferenceBuyerId() {
        return referenceBuyerId;
    }

    /**
     * Setter method for property <tt>referenceBuyerId</tt>.
     *
     * @param referenceBuyerId value to be assigned to property referenceBuyerId
     */
    public void setReferenceBuyerId(String referenceBuyerId) {
        this.referenceBuyerId = referenceBuyerId;
    }

    /**
     * Getter method for property <tt>buyerName</tt>.
     *
     * @return property value of buyerName
     */
    public BuyerName getBuyerName() {
        return buyerName;
    }

    /**
     * Setter method for property <tt>buyerName</tt>.
     *
     * @param buyerName value to be assigned to property buyerName
     */
    public void setBuyerName(BuyerName buyerName) {
        this.buyerName = buyerName;
    }

    /**
     * Getter method for property <tt>buyerEmail</tt>.
     *
     * @return property value of buyerEmail
     */
    public String getBuyerEmail() {
        return buyerEmail;
    }

    /**
     * Setter method for property <tt>buyerEmail</tt>.
     *
     * @param buyerEmail value to be assigned to property buyerEmail
     */
    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    /**
     * Getter method for property <tt>buyerCountry</tt>.
     *
     * @return property value of buyerCountry
     */
    public String getBuyerCountry() {
        return buyerCountry;
    }

    /**
     * Setter method for property <tt>buyerCountry</tt>.
     *
     * @param buyerCountry value to be assigned to property buyerCountry
     */
    public void setBuyerCountry(String buyerCountry) {
        this.buyerCountry = buyerCountry;
    }

    /**
     * Getter method for property <tt>buyerPhoneNo</tt>.
     *
     * @return property value of buyerPhoneNo
     */
    public String getBuyerPhoneNo() {
        return buyerPhoneNo;
    }

    /**
     * Setter method for property <tt>buyerPhoneNo</tt>.
     *
     * @param buyerPhoneNo value to be assigned to property buyerPhoneNo
     */
    public void setBuyerPhoneNo(String buyerPhoneNo) {
        this.buyerPhoneNo = buyerPhoneNo;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
