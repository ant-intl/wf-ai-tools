/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst inquiryAvailableQuota 请求对象
 *
 * <p>用于查询可申报的结汇额度
 *
 * @author Qoder
 * @version InquiryAvailableQuotaRequest.java, v 0.1 2026-04-08
 */
public class InquiryAvailableQuotaRequest {

    /** 结汇额度累计方式：USER_ID/RECEIVING_ACCOUNT/VIRTUAL_ACCOUNT/BENEFICIARY */
    private String quotaAccumulationMethod;

    /** 累计方式标识（用户ID/RA号/VA号/外部平台用户ID） */
    private String quotaAccumulationId;

    /** 币种（ISO 4217标准，如"USD"） */
    private String currency;

    /** 贸易类型：GOODS（货物）/SERVICE（服务）；BENEFICIARY时必传 */
    private String tradeType;

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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
