package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 审核拒绝详情对象。
 *
 * <p>当关联账户审核被拒绝时，返回具体的拒绝原因和补充材料要求。
 * 仅在 status 为 REJECT 时返回，最多 50 条。
 */
public class AuditDetail {

    /** 拒绝原因代码 */
    private String reasonCode;

    /** 拒绝原因描述 */
    private String reasonMessage;

    /** 需要补充的材料说明 */
    private String supplementaryMaterial;

    public AuditDetail() {
    }

    /**
     * Getter method for property <tt>reasonCode</tt>.
     *
     * @return property value of reasonCode
     */
    public String getReasonCode() {
        return reasonCode;
    }

    /**
     * Setter method for property <tt>reasonCode</tt>.
     *
     * @param reasonCode value to be assigned to property reasonCode
     */
    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    /**
     * Getter method for property <tt>reasonMessage</tt>.
     *
     * @return property value of reasonMessage
     */
    public String getReasonMessage() {
        return reasonMessage;
    }

    /**
     * Setter method for property <tt>reasonMessage</tt>.
     *
     * @param reasonMessage value to be assigned to property reasonMessage
     */
    public void setReasonMessage(String reasonMessage) {
        this.reasonMessage = reasonMessage;
    }

    /**
     * Getter method for property <tt>supplementaryMaterial</tt>.
     *
     * @return property value of supplementaryMaterial
     */
    public String getSupplementaryMaterial() {
        return supplementaryMaterial;
    }

    /**
     * Setter method for property <tt>supplementaryMaterial</tt>.
     *
     * @param supplementaryMaterial value to be assigned to property supplementaryMaterial
     */
    public void setSupplementaryMaterial(String supplementaryMaterial) {
        this.supplementaryMaterial = supplementaryMaterial;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
