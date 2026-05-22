package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.Amount;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst inquiryAvailableQuota 响应对象
 *
 * <p>包含可申报的结汇额度信息
 */
public class InquiryAvailableQuotaResponse {

    /** 接口调用结果 */
    private Result result;

    /** 结汇额度累计方式 */
    private String quotaAccumulationMethod;

    /** 累计方式标识 */
    private String quotaAccumulationId;

    /** 可申报的结汇额度 */
    private Amount availableQuota;

    /** 贸易类型（BENEFICIARY时返回） */
    private String tradeType;

    /**
     * 判断接口调用是否成功
     *
     * @return result.resultStatus = S 时返回 true
     */
    public boolean isSuccess() {
        return result != null && "S".equals(result.getResultStatus());
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getQuotaAccumulationMethod() {
        return quotaAccumulationMethod;
    }

    public void setQuotaAccumulationMethod(String quotaAccumulationMethod) {
        this.quotaAccumulationMethod = quotaAccumulationMethod;
    }

    public String getQuotaAccumulationId() {
        return quotaAccumulationId;
    }

    public void setQuotaAccumulationId(String quotaAccumulationId) {
        this.quotaAccumulationId = quotaAccumulationId;
    }

    public Amount getAvailableQuota() {
        return availableQuota;
    }

    public void setAvailableQuota(Amount availableQuota) {
        this.availableQuota = availableQuota;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
