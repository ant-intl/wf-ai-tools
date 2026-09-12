package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 收款人字段模板选项（TemplateOption）。
 *
 * <p>用于 OPTIONS_RESTRICTION 类型字段的可选值列表。
 */
public class TemplateOption {

    /** 选项名称 */
    private String optionName;

    /** 选项值 */
    private String optionValue;

    /**
     * Getter method for property <tt>optionName</tt>.
     *
     * @return property value of optionName
     */
    public String getOptionName() {
        return optionName;
    }

    /**
     * Setter method for property <tt>optionName</tt>.
     *
     * @param optionName value to be assigned to property optionName
     */
    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    /**
     * Getter method for property <tt>optionValue</tt>.
     *
     * @return property value of optionValue
     */
    public String getOptionValue() {
        return optionValue;
    }

    /**
     * Setter method for property <tt>optionValue</tt>.
     *
     * @param optionValue value to be assigned to property optionValue
     */
    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
