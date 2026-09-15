package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 代发附加业务信息。
 *
 * <p>用于特定业务场景的补充字段。最多 10 个键值对，每个键值最大 255 字符。
 */
public class AdditionalInfo {

    /** 是否迁移 CNY 结汇材料。仅当 businessSceneCode 为 MULTI_ACCOUNT_TRANSFER 时需要，传 true 表示包含结汇材料 */
    private Boolean needMigrateMaterial;

    /**
     * Getter method for property <tt>needMigrateMaterial</tt>.
     *
     * @return property value of needMigrateMaterial
     */
    public Boolean getNeedMigrateMaterial() {
        return needMigrateMaterial;
    }

    /**
     * Setter method for property <tt>needMigrateMaterial</tt>.
     *
     * @param needMigrateMaterial value to be assigned to property needMigrateMaterial
     */
    public void setNeedMigrateMaterial(Boolean needMigrateMaterial) {
        this.needMigrateMaterial = needMigrateMaterial;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
