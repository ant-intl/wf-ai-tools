package {basePackage}.wf.model.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 地址信息对象。
 *
 * <p>用于 thirdPartyIdentity 中的地址字段。
 *
 */
public class Address {

    /** 国家/地区代码，符合 ISO-3166 标准 */
    private String region;

    /** 省/州，最大 8 字符 */
    private String state;

    /** 城市，最大 32 字符 */
    private String city;

    /** 地址行1，最大 128 字符 */
    private String address1;

    /** 地址行2，最大 128 字符 */
    private String address2;

    /** 邮政编码，最大 32 字符 */
    private String zipCode;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
