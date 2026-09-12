package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 收款方式对象。
 *
 * <p>描述存款使用的收款账户类型和账号。
 */
public class ReceiveMethod {

    /**
     * 收款账户类型：VA（Global Account）或 RA（Receiving Account）。
     */
    private String type;

    /**
     * 收款账号（脱敏）。
     */
    private String accountNumber;

    public ReceiveMethod() {
    }

    /**
     * Getter method for property <tt>type</tt>.
     *
     * @return property value of type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter method for property <tt>type</tt>.
     *
     * @param type value to be assigned to property type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter method for property <tt>accountNumber</tt>.
     *
     * @return property value of accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Setter method for property <tt>accountNumber</tt>.
     *
     * @param accountNumber value to be assigned to property accountNumber
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
