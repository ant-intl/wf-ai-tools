package {basePackage}.wf.model.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 可用额度条目对象。
 *
 * <p>描述单个累积标识下的可用结算额度，包含汇总额度和按币种拆分的明细。
 */
public class AvailableQuotaEntry {

    /** 该额度条目的累积标识 */
    private String quotaAccumulationId;

    /** 以请求币种汇总的总可用额度 */
    private Amount availableQuota;

    /** 按币种拆分的可用额度明细 */
    private List<QuotaByCurrency> availableQuotaByCurrency;

    public AvailableQuotaEntry() {
    }

    public String getQuotaAccumulationId() {
        return quotaAccumulationId;
    }

    public void setQuotaAccumulationId(String quotaAccumulationId) {
        this.quotaAccumulationId = quotaAccumulationId;
    }

    public {basePackage}.wf.model.domain.Amount getAvailableQuota() {
        return availableQuota;
    }

    public void setAvailableQuota({basePackage}.wf.model.domain.Amount availableQuota) {
        this.availableQuota = availableQuota;
    }

    public List<QuotaByCurrency> getAvailableQuotaByCurrency() {
        return availableQuotaByCurrency;
    }

    public void setAvailableQuotaByCurrency(List<QuotaByCurrency> availableQuotaByCurrency) {
        this.availableQuotaByCurrency = availableQuotaByCurrency;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
