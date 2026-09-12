package {basePackage}.wf.model.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 收款人字段模板（BeneficiaryTemplate）。
 *
 * <p>包含一组字段定义，用于描述创建收款人时需要提供的字段集合。
 * 用于 query_beneficiary_template 接口响应。
 */
public class BeneficiaryTemplate {

    /** 字段列表 */
    private List<TemplateField> fields;

    /** 钱包品牌名称（accountType 为 DIGITAL_WALLET 时返回） */
    private String walletBrandName;

    /**
     * Getter method for property <tt>fields</tt>.
     *
     * @return property value of fields
     */
    public List<TemplateField> getFields() {
        return fields;
    }

    /**
     * Setter method for property <tt>fields</tt>.
     *
     * @param fields value to be assigned to property fields
     */
    public void setFields(List<TemplateField> fields) {
        this.fields = fields;
    }

    /**
     * Getter method for property <tt>walletBrandName</tt>.
     *
     * @return property value of walletBrandName
     */
    public String getWalletBrandName() {
        return walletBrandName;
    }

    /**
     * Setter method for property <tt>walletBrandName</tt>.
     *
     * @param walletBrandName value to be assigned to property walletBrandName
     */
    public void setWalletBrandName(String walletBrandName) {
        this.walletBrandName = walletBrandName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
