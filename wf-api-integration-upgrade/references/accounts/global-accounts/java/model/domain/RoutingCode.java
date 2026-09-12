package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 全局账户路由码对象。
 *
 * <p>表示银行账户的路由/识别码，如 ACH routing number、SWIFT/BIC code 等。
 * 类型与支持区域参见 WF 官方文档 RoutingCodeType 枚举。
 */
public class RoutingCode {

    /**
     * 路由码类型，如 ACH、SWIFT、SORT_CODE、BSB、BANK_CODE 等。
     * <p>详见 WF 官方文档 RoutingCodeType 枚举。
     */
    private String type;

    /** 路由码值 */
    private String value;

    public RoutingCode() {
    }

    /**
     * 构造路由码对象。
     *
     * @param type  路由码类型
     * @param value 路由码值
     */
    public RoutingCode(String type, String value) {
        this.type = type;
        this.value = value;
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
     * Getter method for property <tt>value</tt>.
     *
     * @return property value of value
     */
    public String getValue() {
        return value;
    }

    /**
     * Setter method for property <tt>value</tt>.
     *
     * @param value value to be assigned to property value
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
