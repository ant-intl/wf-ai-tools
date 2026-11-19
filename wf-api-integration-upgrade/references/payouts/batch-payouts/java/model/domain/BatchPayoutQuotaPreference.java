package {basePackage}.wf.model.domain;


/** BatchPayoutQuotaPreference 领域结构。可选字段保持 null；完整约束见对应 GUIDE。 */
public class BatchPayoutQuotaPreference {

    /** Entity whose quota is consumed. Defaults to the entity associated with your account profile. Contact WorldFirst for special requirements. - `PAYER`: consumes CNY quota accumulated by payer. - `BENEFICIARY`: consumes CNY quota accumulated by the beneficiary. */
    private String party;

    /** Type of trade materials supporting the quota usage. - `B2C`: Backed by B2C trade orders. - `B2B`: Backed by B2B trade orders. */
    private String tradeOrderType;

    public String getParty() { return party; }

    public void setParty(String party) { this.party = party; }

    public String getTradeOrderType() { return tradeOrderType; }

    public void setTradeOrderType(String tradeOrderType) { this.tradeOrderType = tradeOrderType; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "BatchPayoutQuotaPreference[REDACTED]"; }
}
