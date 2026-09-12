package {basePackage}.wf.model.response;

import java.util.List;

import {basePackage}.wf.model.domain.BeneficiaryTemplate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 收款人字段模板查询响应对象。
 *
 * <p>用于 query_beneficiary_template 接口响应，包含字段定义和校验规则。
 */
public class QueryBeneficiaryTemplateResponse {

    /** API 调用结果 */
    private Result result;

    /** 收款人字段模板列表 */
    private List<BeneficiaryTemplate> templates;

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
     * Getter method for property <tt>templates</tt>.
     *
     * @return property value of templates
     */
    public List<BeneficiaryTemplate> getTemplates() {
        return templates;
    }

    /**
     * Setter method for property <tt>templates</tt>.
     *
     * @param templates value to be assigned to property templates
     */
    public void setTemplates(List<BeneficiaryTemplate> templates) {
        this.templates = templates;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
