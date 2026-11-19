package {basePackage}.wf.model.domain;


/** CardTransactionLimitInput 领域结构。可选字段保持 null；完整约束见对应 GUIDE。 */
public class CardTransactionLimitInput {

    /** Required. Limit period. Supported values: PER_TRANSACTION, DAILY, MONTHLY, and ALL_TIME. */
    private String interval;

    /** Required. Limit ceiling, in the smallest currency unit. Minimum 1. */
    private Long rangeAmount;

    public String getInterval() { return interval; }

    public void setInterval(String interval) { this.interval = interval; }

    public Long getRangeAmount() { return rangeAmount; }

    public void setRangeAmount(Long rangeAmount) { this.rangeAmount = rangeAmount; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "CardTransactionLimitInput[REDACTED]"; }
}
