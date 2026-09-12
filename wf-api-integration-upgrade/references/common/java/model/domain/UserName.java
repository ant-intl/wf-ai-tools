package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 通用姓名对象。
 *
 * <p>表示个人姓名信息，支持拆分姓名组件（名、中间名、姓）和组合全名两种方式。
 * 各业务模块（Payout、Beneficiary、Account 等）均可复用此姓名结构。
 *
 * @see <a href="https://docs.worldfirst.com/wfdocs/api-sdk/data_types">Data Types</a>
 */
public class UserName {

    /**
     * 名（given name）。
     * <p>例如 "San"。
     */
    private String firstName;

    /**
     * 中间名。
     * <p>例如 "Ming"。
     */
    private String middleName;

    /**
     * 姓（family name）。
     * <p>例如 "Zhang"。
     */
    private String lastName;

    /**
     * 全名。
     * <p>例如 "Zhang San"。
     */
    private String fullName;

    public UserName() {
    }

    /**
     * Getter method for property <tt>firstName</tt>.
     *
     * @return 名
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter method for property <tt>firstName</tt>.
     *
     * @param firstName 名
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter method for property <tt>middleName</tt>.
     *
     * @return 中间名
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Setter method for property <tt>middleName</tt>.
     *
     * @param middleName 中间名
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Getter method for property <tt>lastName</tt>.
     *
     * @return 姓
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter method for property <tt>lastName</tt>.
     *
     * @param lastName 姓
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter method for property <tt>fullName</tt>.
     *
     * @return 全名
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Setter method for property <tt>fullName</tt>.
     *
     * @param fullName 全名
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
