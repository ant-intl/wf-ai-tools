package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 地址信息
 *
 */
public class Address {

    /** 国家/地区（ISO-3166 二字母） */
    private String region;

    /** 省/州 */
    private String state;

    /** 城市 */
    private String city;

    /** 地址第一行 */
    private String address1;

    /** 地址第二行 */
    private String address2;

    /** 邮编 */
    private String zipCode;

    /**
     * Getter method for property <tt>region</tt>.
     *
     * @return property value of region
     */
    public String getRegion() {
        return region;
    }

    /**
     * Setter method for property <tt>region</tt>.
     *
     * @param region value to be assigned to property region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Getter method for property <tt>state</tt>.
     *
     * @return property value of state
     */
    public String getState() {
        return state;
    }

    /**
     * Setter method for property <tt>state</tt>.
     *
     * @param state value to be assigned to property state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Getter method for property <tt>city</tt>.
     *
     * @return property value of city
     */
    public String getCity() {
        return city;
    }

    /**
     * Setter method for property <tt>city</tt>.
     *
     * @param city value to be assigned to property city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Getter method for property <tt>address1</tt>.
     *
     * @return property value of address1
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Setter method for property <tt>address1</tt>.
     *
     * @param address1 value to be assigned to property address1
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * Getter method for property <tt>address2</tt>.
     *
     * @return property value of address2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Setter method for property <tt>address2</tt>.
     *
     * @param address2 value to be assigned to property address2
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * Getter method for property <tt>zipCode</tt>.
     *
     * @return property value of zipCode
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Setter method for property <tt>zipCode</tt>.
     *
     * @param zipCode value to be assigned to property zipCode
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
