/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.Beneficiary;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst inquiryBeneficiaryList 响应对象。
 *
 * @author Qoder
 * @version InquiryBeneficiaryListResponse.java, v 0.1 2026-03-26
 */
public class InquiryBeneficiaryListResponse {

    /** API 调用结果 */
    private Result result;

    /** 响应唯一标识 */
    private String responseId;

    /** 收款人列表 */
    private List<Beneficiary> beneficiaries;

    /** 总条数 */
    private Integer totalCount;

    /** 总页数 */
    private Integer totalPageNumber;

    /** 当前页码 */
    private Integer currentPageNumber;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public List<Beneficiary> getBeneficiaries() {
        return beneficiaries;
    }

    public void setBeneficiaries(List<Beneficiary> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPageNumber() {
        return totalPageNumber;
    }

    public void setTotalPageNumber(Integer totalPageNumber) {
        this.totalPageNumber = totalPageNumber;
    }

    public Integer getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(Integer currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public boolean isSuccess() {
        return result != null && "S".equals(result.getResultStatus());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
