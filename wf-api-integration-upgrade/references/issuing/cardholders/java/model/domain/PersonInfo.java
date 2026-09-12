package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 持卡人个人信息对象。
 *
 * <p>用于持卡人创建、查询与列表响应中的持卡人个人身份详情，提交 KYC 审核使用。
 */
public class PersonInfo {

    /**
     * 持卡人姓名。
     */
    private UserName userName;

    /**
     * 出生日期，格式 yyyy-MM-dd。
     */
    private String dateOfBirth;

    public PersonInfo() {
    }

    /**
     * Getter method for property <tt>userName</tt>.
     *
     * @return property value of userName
     */
    public UserName getUserName() {
        return userName;
    }

    /**
     * Setter method for property <tt>userName</tt>.
     *
     * @param userName value to be assigned to property userName
     */
    public void setUserName(UserName userName) {
        this.userName = userName;
    }

    /**
     * Getter method for property <tt>dateOfBirth</tt>.
     *
     * @return property value of dateOfBirth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Setter method for property <tt>dateOfBirth</tt>.
     *
     * @param dateOfBirth value to be assigned to property dateOfBirth
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
