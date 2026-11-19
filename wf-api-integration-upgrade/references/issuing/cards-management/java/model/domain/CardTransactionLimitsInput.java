package {basePackage}.wf.model.domain;

import java.util.List;

/** CardTransactionLimitsInput 领域结构。可选字段保持 null；完整约束见对应 GUIDE。 */
public class CardTransactionLimitsInput {

    /** Required. Limit currency. 3-letter ISO 4217 code. Supported value: USD. */
    private String currency;

    /** Required. Limit rules for this currency. Maximum 4 entries (one per interval). */
    private List<CardTransactionLimitInput> limits;

    public String getCurrency() { return currency; }

    public void setCurrency(String currency) { this.currency = currency; }

    public List<CardTransactionLimitInput> getLimits() { return limits; }

    public void setLimits(List<CardTransactionLimitInput> limits) { this.limits = limits; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "CardTransactionLimitsInput[REDACTED]"; }
}
