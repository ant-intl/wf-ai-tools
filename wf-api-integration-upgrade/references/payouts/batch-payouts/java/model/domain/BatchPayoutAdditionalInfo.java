package {basePackage}.wf.model.domain;


/** BatchPayoutAdditionalInfo 领域结构。可选字段保持 null；完整约束见对应 GUIDE。 */
public class BatchPayoutAdditionalInfo {

    /** Whether to migrate CNY settlement materials. Required only when businessSceneCode is MULTI_ACCOUNT_TRANSFER. Pass true to include settlement materials. */
    private Boolean needMigrateMaterial;

    public Boolean getNeedMigrateMaterial() { return needMigrateMaterial; }

    public void setNeedMigrateMaterial(Boolean needMigrateMaterial) { this.needMigrateMaterial = needMigrateMaterial; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "BatchPayoutAdditionalInfo[REDACTED]"; }
}
