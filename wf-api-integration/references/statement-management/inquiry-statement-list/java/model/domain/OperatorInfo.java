package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 操作员信息
 *
 * <p>只有在操作万里汇门户而产生交易单时才会返回。
 *
 */
public class OperatorInfo {

    /** 操作员名称 */
    private String operatorName;

    /** 操作员邮箱 */
    private String operatorEmail;

    /**
     * Getter method for property <tt>operatorName</tt>.
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * Setter method for property <tt>operatorName</tt>.
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    /**
     * Getter method for property <tt>operatorEmail</tt>.
     */
    public String getOperatorEmail() {
        return operatorEmail;
    }

    /**
     * Setter method for property <tt>operatorEmail</tt>.
     */
    public void setOperatorEmail(String operatorEmail) {
        this.operatorEmail = operatorEmail;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
