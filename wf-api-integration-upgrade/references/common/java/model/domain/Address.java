package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 通用地址对象。
 *
 * <p>表示物理地址或邮寄地址。所有字段均为可选，根据实际需要提供。
 * 各业务模块（Payout、Beneficiary、Account 等）均可复用此地址结构。
 *
 * @see <a href="https://docs.worldfirst.com/wfdocs/api-sdk/data_types">Data Types</a>
 */
public class Address {

    /**
     * 国家/地区代码（ISO 3166-1），两字母格式。
     * <p>例如 "HK"。
     */
    private String region;

    /**
     * 州/省。
     * <p>例如 "California"。
     */
    private String state;

    /**
     * 城市。
     * <p>例如 "Hong Kong"。
     */
    private String city;

    /**
     * 地址行 1。
     * <p>例如 "Central, Hong Kong"。
     */
    private String address1;

    /**
     * 地址行 2。
     * <p>例如 "Suite 1234"。
     */
    private String address2;

    /**
     * 邮政编码。
     * <p>例如 "000000"。
     */
    private String zipCode;

    public Address() {
    }

    /**
     * Getter method for property <tt>region</tt>.
     *
     * @return 国家/地区代码
     */
    public String getRegion() {
        return region;
    }

    /**
     * Setter method for property <tt>region</tt>.
     *
     * @param region 国家/地区代码
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Getter method for property <tt>state</tt>.
     *
     * @return 州/省
     */
    public String getState() {
        return state;
    }

    /**
     * Setter method for property <tt>state</tt>.
     *
     * @param state 州/省
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Getter method for property <tt>city</tt>.
     *
     * @return 城市
     */
    public String getCity() {
        return city;
    }

    /**
     * Setter method for property <tt>city</tt>.
     *
     * @param city 城市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Getter method for property <tt>address1</tt>.
     *
     * @return 地址行 1
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Setter method for property <tt>address1</tt>.
     *
     * @param address1 地址行 1
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * Getter method for property <tt>address2</tt>.
     *
     * @return 地址行 2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Setter method for property <tt>address2</tt>.
     *
     * @param address2 地址行 2
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * Getter method for property <tt>zipCode</tt>.
     *
     * @return 邮政编码
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Setter method for property <tt>zipCode</tt>.
     *
     * @param zipCode 邮政编码
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
