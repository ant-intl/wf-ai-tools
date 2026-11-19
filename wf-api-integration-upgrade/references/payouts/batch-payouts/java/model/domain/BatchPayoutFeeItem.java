package {basePackage}.wf.model.domain;


/** BatchPayoutFeeItem 领域结构。可选字段保持 null；完整约束见对应 GUIDE。 */
public class BatchPayoutFeeItem {

    /** Fee item type. See FeeItemType. */
    private String feeItemType;

    /** Fee amount for this item. See Data Types. */
    private Amount feeItemAmount;

    public String getFeeItemType() { return feeItemType; }

    public void setFeeItemType(String feeItemType) { this.feeItemType = feeItemType; }

    public Amount getFeeItemAmount() { return feeItemAmount; }

    public void setFeeItemAmount(Amount feeItemAmount) { this.feeItemAmount = feeItemAmount; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "BatchPayoutFeeItem[REDACTED]"; }
}
