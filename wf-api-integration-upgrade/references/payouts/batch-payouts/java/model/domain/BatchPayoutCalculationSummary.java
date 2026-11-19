package {basePackage}.wf.model.domain;

import java.util.List;

/** BatchPayoutCalculationSummary 领域结构。可选字段保持 null；完整约束见对应 GUIDE。 */
public class BatchPayoutCalculationSummary {

    /** Time the batch was most recently priced, in ISO 8601 format. Indicates how recent the reference preview is. */
    private String lastCalculatedAt;

    /** Aggregated reference pricing per currency pair covered by the batch. Up to 10 entries. */
    private List<BatchPayoutCurrencyPair> currencyPairs;

    public String getLastCalculatedAt() { return lastCalculatedAt; }

    public void setLastCalculatedAt(String lastCalculatedAt) { this.lastCalculatedAt = lastCalculatedAt; }

    public List<BatchPayoutCurrencyPair> getCurrencyPairs() { return currencyPairs; }

    public void setCurrencyPairs(List<BatchPayoutCurrencyPair> currencyPairs) { this.currencyPairs = currencyPairs; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "BatchPayoutCalculationSummary[REDACTED]"; }
}
