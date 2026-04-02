/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.List;

/**
 * WorldFirst inquiryBeneficiaryList 请求对象。
 *
 * <p>用于分页查询已绑定的收款人列表。
 *
 * @author Qoder
 * @version InquiryBeneficiaryListRequest.java, v 0.1 2026-03-26
 */
public class InquiryBeneficiaryListRequest {

    /** 每页条数，最大 50 */
    private Integer pageSize;

    /** 页码（从 1 开始） */
    private Integer pageNumber;

    /** OAuth access token，最大 64 字符 */
    private String accessToken;

    /** 收款人令牌（精确匹配） */
    private String beneficiaryToken;

    /** 收款人昵称（模糊匹配） */
    private String beneficiaryNick;

    /** 集成商自定义唯一ID */
    private String referenceBeneficiaryId;

    /** 银行账号（模糊匹配） */
    private String bankAccountNo;

    /** IBAN */
    private String bankAccountIBAN;

    /** 币种过滤列表 */
    private List<String> currencyList;

    /** 账户名称（模糊匹配） */
    private String accountName;

    /** 资产类型：BANK_ACCOUNT 或 ALIPAY_ACCOUNT */
    private String assetType;

    /** 关系过滤：SAME_NAME 或 THIRD_PARTY */
    private List<String> relationFilter;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getBeneficiaryToken() {
        return beneficiaryToken;
    }

    public void setBeneficiaryToken(String beneficiaryToken) {
        this.beneficiaryToken = beneficiaryToken;
    }

    public String getBeneficiaryNick() {
        return beneficiaryNick;
    }

    public void setBeneficiaryNick(String beneficiaryNick) {
        this.beneficiaryNick = beneficiaryNick;
    }

    public String getReferenceBeneficiaryId() {
        return referenceBeneficiaryId;
    }

    public void setReferenceBeneficiaryId(String referenceBeneficiaryId) {
        this.referenceBeneficiaryId = referenceBeneficiaryId;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankAccountIBAN() {
        return bankAccountIBAN;
    }

    public void setBankAccountIBAN(String bankAccountIBAN) {
        this.bankAccountIBAN = bankAccountIBAN;
    }

    public List<String> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<String> currencyList) {
        this.currencyList = currencyList;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public List<String> getRelationFilter() {
        return relationFilter;
    }

    public void setRelationFilter(List<String> relationFilter) {
        this.relationFilter = relationFilter;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    }
}
