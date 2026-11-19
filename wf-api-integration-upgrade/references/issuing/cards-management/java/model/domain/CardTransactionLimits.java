package {basePackage}.wf.model.domain;

import java.util.List;

/** CardTransactionLimits 领域结构。可选字段保持 null；完整约束见对应 GUIDE。 */
public class CardTransactionLimits {

    /** Limit currency. Currently, USD is the only supported value. */
    private String currency;

    /** Limit rules for this currency. */
    private List<CardTransactionLimit> limits;

    public String getCurrency() { return currency; }

    public void setCurrency(String currency) { this.currency = currency; }

    public List<CardTransactionLimit> getLimits() { return limits; }

    public void setLimits(List<CardTransactionLimit> limits) { this.limits = limits; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "CardTransactionLimits[REDACTED]"; }
}
