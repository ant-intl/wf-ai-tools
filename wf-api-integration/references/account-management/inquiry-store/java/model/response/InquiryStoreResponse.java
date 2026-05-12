/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.StoreInfo;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst inquiryStore 响应对象
 *
 * @author Qoder
 * @version InquiryStoreResponse.java, v 0.1 2026-04-17
 */
public class InquiryStoreResponse {

    /** 接口调用结果 */
    private Result result;

    /** 店铺信息列表，resultStatus=S 时按需返回 */
    private List<StoreInfo> storeInformation;

    /** 查询结果总条目数，最大 8 字符，resultStatus=S 时返回 */
    private Integer totalCount;

    /** 查询结果总页数，最大 8 字符，resultStatus=S 时返回 */
    private Integer totalPageNumber;

    /** 当前页码，最大 8 字符，resultStatus=S 时返回 */
    private Integer currentPageNumber;

    /**
     * Getter method for property <tt>result</tt>.
     *
     * @return property value of result
     */
    public Result getResult() {
        return result;
    }

    /**
     * Setter method for property <tt>result</tt>.
     *
     * @param result value to be assigned to property result
     */
    public void setResult(Result result) {
        this.result = result;
    }

    /**
     * Getter method for property <tt>storeInformation</tt>.
     *
     * @return property value of storeInformation
     */
    public List<StoreInfo> getStoreInformation() {
        return storeInformation;
    }

    /**
     * Setter method for property <tt>storeInformation</tt>.
     *
     * @param storeInformation value to be assigned to property storeInformation
     */
    public void setStoreInformation(List<StoreInfo> storeInformation) {
        this.storeInformation = storeInformation;
    }

    /**
     * Getter method for property <tt>totalCount</tt>.
     *
     * @return property value of totalCount
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * Setter method for property <tt>totalCount</tt>.
     *
     * @param totalCount value to be assigned to property totalCount
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * Getter method for property <tt>totalPageNumber</tt>.
     *
     * @return property value of totalPageNumber
     */
    public Integer getTotalPageNumber() {
        return totalPageNumber;
    }

    /**
     * Setter method for property <tt>totalPageNumber</tt>.
     *
     * @param totalPageNumber value to be assigned to property totalPageNumber
     */
    public void setTotalPageNumber(Integer totalPageNumber) {
        this.totalPageNumber = totalPageNumber;
    }

    /**
     * Getter method for property <tt>currentPageNumber</tt>.
     *
     * @return property value of currentPageNumber
     */
    public Integer getCurrentPageNumber() {
        return currentPageNumber;
    }

    /**
     * Setter method for property <tt>currentPageNumber</tt>.
     *
     * @param currentPageNumber value to be assigned to property currentPageNumber
     */
    public void setCurrentPageNumber(Integer currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
