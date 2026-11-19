package {basePackage}.wf.model.domain;


/** CardTransactionLimit 领域结构。可选字段保持 null；完整约束见对应 GUIDE。 */
public class CardTransactionLimit {

    /** Limit period. Supported values: PER_TRANSACTION, DAILY, MONTHLY, and ALL_TIME. */
    private String interval;

    /** Limit ceiling, in the smallest currency unit. */
    private Long rangeAmount;

    /** Amount used in the current period. Returned in query responses only. */
    private Long usedAmount;

    /** Remaining available amount in the current period. Returned in query responses only. */
    private Long remainAmount;

    public String getInterval() { return interval; }

    public void setInterval(String interval) { this.interval = interval; }

    public Long getRangeAmount() { return rangeAmount; }

    public void setRangeAmount(Long rangeAmount) { this.rangeAmount = rangeAmount; }

    public Long getUsedAmount() { return usedAmount; }

    public void setUsedAmount(Long usedAmount) { this.usedAmount = usedAmount; }

    public Long getRemainAmount() { return remainAmount; }

    public void setRemainAmount(Long remainAmount) { this.remainAmount = remainAmount; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "CardTransactionLimit[REDACTED]"; }
}
