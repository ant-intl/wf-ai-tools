package {basePackage}.wf.model.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 字段校验错误信息。
 *
 * <p>用于 validate_a_beneficiary 接口响应，表示单个字段的校验失败详情。
 */
public class ValidationError {

    /** 校验失败的字段路径（点号分隔，如 bankDetails.sortCode）。最多 256 字符 */
    private String field;

    /** 校验错误类型：FIELD_OPTION_NOT_MATCH（值不在允许选项中）、FIELD_PATTERN_NOT_MATCH（值不匹配正则） */
    private String type;

    /** 纯文本错误描述。最多 512 字符 */
    private String desc;

    /** 字段需匹配的正则表达式（type 为 FIELD_PATTERN_NOT_MATCH 时返回） */
    private String restrictionRegex;

    /** 允许的选项值列表（type 为 FIELD_OPTION_NOT_MATCH 时返回） */
    private List<TemplateOption> restrictionOptions;

    /**
     * Getter method for property <tt>field</tt>.
     *
     * @return property value of field
     */
    public String getField() {
        return field;
    }

    /**
     * Setter method for property <tt>field</tt>.
     *
     * @param field value to be assigned to property field
     */
    public void setField(String field) {
        this.field = field;
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
     * Getter method for property <tt>desc</tt>.
     *
     * @return property value of desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Setter method for property <tt>desc</tt>.
     *
     * @param desc value to be assigned to property desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Getter method for property <tt>restrictionRegex</tt>.
     *
     * @return property value of restrictionRegex
     */
    public String getRestrictionRegex() {
        return restrictionRegex;
    }

    /**
     * Setter method for property <tt>restrictionRegex</tt>.
     *
     * @param restrictionRegex value to be assigned to property restrictionRegex
     */
    public void setRestrictionRegex(String restrictionRegex) {
        this.restrictionRegex = restrictionRegex;
    }

    /**
     * Getter method for property <tt>restrictionOptions</tt>.
     *
     * @return property value of restrictionOptions
     */
    public List<TemplateOption> getRestrictionOptions() {
        return restrictionOptions;
    }

    /**
     * Setter method for property <tt>restrictionOptions</tt>.
     *
     * @param restrictionOptions value to be assigned to property restrictionOptions
     */
    public void setRestrictionOptions(List<TemplateOption> restrictionOptions) {
        this.restrictionOptions = restrictionOptions;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
