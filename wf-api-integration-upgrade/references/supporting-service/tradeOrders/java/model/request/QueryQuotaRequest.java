package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_available_settlement_quota 请求对象。
 *
 * <p>用于查询指定币种和累积方式下的剩余 CNY 结算额度。
 */
public class QueryQuotaRequest {

    /** 查询币种，ISO 4217 三字母代码（如 USD、EUR） */
    private String currency;

    /** 与 quotaAccumulationMethod 配对的累积标识 */
    private String quotaAccumulationId;

    /** 额度累积方式（QuotaAccumulationMethod 枚举），传入 USER_ID 时可不传 */
    private String quotaAccumulationMethod;

    /** 贸易类别过滤，仅在查询受益人级别额度时必填：GOODS 或 SERVICE */
    private String tradeCategory;

    public QueryQuotaRequest() {
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getQuotaAccumulationId() {
        return quotaAccumulationId;
    }

    public void setQuotaAccumulationId(String quotaAccumulationId) {
        this.quotaAccumulationId = quotaAccumulationId;
    }

    public String getQuotaAccumulationMethod() {
        return quotaAccumulationMethod;
    }

    public void setQuotaAccumulationMethod(String quotaAccumulationMethod) {
        this.quotaAccumulationMethod = quotaAccumulationMethod;
    }

    public String getTradeCategory() {
        return tradeCategory;
    }

    public void setTradeCategory(String tradeCategory) {
        this.tradeCategory = tradeCategory;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
