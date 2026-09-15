package {basePackage}.wf.model.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 收款人字段模板（TemplateField）。
 *
 * <p>表示创建收款人时需要提供的单个字段及其校验规则。
 * 用于 query_beneficiary_template 接口响应。
 */
public class TemplateField {

    /** 字段名称 */
    private String fieldName;

    /** 字段描述 */
    private String fieldDescription;

    /** 是否必填：Y=必填，N=选填 */
    private String required;

    /** 限制类型：PATTERN_RESTRICTION（正则）、OPTIONS_RESTRICTION（枚举） */
    private String restrictionType;

    /** 限制提示信息 */
    private String restrictionMsg;

    /** 正则表达式（restrictionType 为 PATTERN_RESTRICTION 时返回） */
    private String restrictionRegex;

    /** 可选项列表（restrictionType 为 OPTIONS_RESTRICTION 时返回） */
    private List<TemplateOption> restrictionOptions;

    /**
     * Getter method for property <tt>fieldName</tt>.
     *
     * @return property value of fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Setter method for property <tt>fieldName</tt>.
     *
     * @param fieldName value to be assigned to property fieldName
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Getter method for property <tt>fieldDescription</tt>.
     *
     * @return property value of fieldDescription
     */
    public String getFieldDescription() {
        return fieldDescription;
    }

    /**
     * Setter method for property <tt>fieldDescription</tt>.
     *
     * @param fieldDescription value to be assigned to property fieldDescription
     */
    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    /**
     * Getter method for property <tt>required</tt>.
     *
     * @return property value of required
     */
    public String getRequired() {
        return required;
    }

    /**
     * Setter method for property <tt>required</tt>.
     *
     * @param required value to be assigned to property required
     */
    public void setRequired(String required) {
        this.required = required;
    }

    /**
     * Getter method for property <tt>restrictionType</tt>.
     *
     * @return property value of restrictionType
     */
    public String getRestrictionType() {
        return restrictionType;
    }

    /**
     * Setter method for property <tt>restrictionType</tt>.
     *
     * @param restrictionType value to be assigned to property restrictionType
     */
    public void setRestrictionType(String restrictionType) {
        this.restrictionType = restrictionType;
    }

    /**
     * Getter method for property <tt>restrictionMsg</tt>.
     *
     * @return property value of restrictionMsg
     */
    public String getRestrictionMsg() {
        return restrictionMsg;
    }

    /**
     * Setter method for property <tt>restrictionMsg</tt>.
     *
     * @param restrictionMsg value to be assigned to property restrictionMsg
     */
    public void setRestrictionMsg(String restrictionMsg) {
        this.restrictionMsg = restrictionMsg;
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
