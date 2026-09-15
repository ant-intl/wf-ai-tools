package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 联系信息对象。
 *
 * <p>描述关联账户的联系人信息，包括邮箱和电话。
 */
public class Contact {

    /** 联系邮箱地址 */
    private String contactEmail;

    /** 联系电话号码 */
    private String contactPhone;

    public Contact() {
    }

    /**
     * Getter method for property <tt>contactEmail</tt>.
     *
     * @return property value of contactEmail
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Setter method for property <tt>contactEmail</tt>.
     *
     * @param contactEmail value to be assigned to property contactEmail
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * Getter method for property <tt>contactPhone</tt>.
     *
     * @return property value of contactPhone
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * Setter method for property <tt>contactPhone</tt>.
     *
     * @param contactPhone value to be assigned to property contactPhone
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
