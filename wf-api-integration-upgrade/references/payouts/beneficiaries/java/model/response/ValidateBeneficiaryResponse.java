package {basePackage}.wf.model.response;

import java.util.List;

import {basePackage}.wf.model.domain.ValidationError;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst validate_a_beneficiary 响应对象。
 *
 * <p>用于 validate_a_beneficiary 接口响应，包含校验结果和字段级错误信息。
 * result 为 SUCCESS 且 validationErrors 为空数组时表示校验通过。
 */
public class ValidateBeneficiaryResponse {

    /** API 调用结果 */
    private Result result;

    /** 字段级校验错误列表（全部通过时为空数组，最多 50 条） */
    private List<ValidationError> validationErrors;

    /**
     * Getter method for property <tt>result</tt>.
     *
     * @return property value of result
     */
    public Result getResult() {
        return result;
    }

    /**
     * Setter method for property <tt>result</tt>.
     *
     * @param result value to be assigned to property result
     */
    public void setResult(Result result) {
        this.result = result;
    }

    /**
     * Getter method for property <tt>validationErrors</tt>.
     *
     * @return property value of validationErrors
     */
    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }

    /**
     * Setter method for property <tt>validationErrors</tt>.
     *
     * @param validationErrors value to be assigned to property validationErrors
     */
    public void setValidationErrors(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
