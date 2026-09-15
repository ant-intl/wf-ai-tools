package {basePackage}.wf.model.response;

import java.util.List;

import {basePackage}.wf.model.domain.AvailableQuotaEntry;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_available_settlement_quota 响应对象。
 *
 * <p>包含接口调用结果、可用额度快照及快照时间戳。
 */
public class QueryQuotaResponse {

    /** 接口调用结果 */
    private Result result;

    /** 可用额度明细，按累积标识拆分，最多 100 条 */
    private List<AvailableQuotaEntry> availableQuotas;

    /** 额度快照时间戳（ISO 8601 扩展格式） */
    private String createdAt;

    public QueryQuotaResponse() {
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public List<AvailableQuotaEntry> getAvailableQuotas() {
        return availableQuotas;
    }

    public void setAvailableQuotas(List<AvailableQuotaEntry> availableQuotas) {
        this.availableQuotas = availableQuotas;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
