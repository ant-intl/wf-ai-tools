package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 买家（付款方）信息对象。
 *
 * <p>描述贸易订单中买家的基本信息。
 */
public class Buyer {

    /** 第三方平台中的外部买家用户 ID */
    private String referenceBuyerId;

    /** 买家邮箱地址 */
    private String buyerEmail;

    /** 买家电话号码 */
    private String buyerPhone;

    /** 买家国家/地区，ISO 3166 两字母代码 */
    private String buyerRegion;

    /** 买家姓名 */
    private UserNameInfo buyerName;

    public Buyer() {
    }

    public String getReferenceBuyerId() {
        return referenceBuyerId;
    }

    public void setReferenceBuyerId(String referenceBuyerId) {
        this.referenceBuyerId = referenceBuyerId;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getBuyerRegion() {
        return buyerRegion;
    }

    public void setBuyerRegion(String buyerRegion) {
        this.buyerRegion = buyerRegion;
    }

    public UserNameInfo getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(UserNameInfo buyerName) {
        this.buyerName = buyerName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
