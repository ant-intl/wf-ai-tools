/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst inquiryStore 请求对象
 *
 * <p>用于查询店铺信息及店铺关联账号信息，支持分页
 *
 * @author Qoder
 * @version InquiryStoreRequest.java, v 0.1 2026-04-17
 */
public class InquiryStoreRequest {

    /** 每页包含的条目数（必填） */
    private Integer pageSize;

    /** 当前页码，从 1 开始（必填） */
    private Integer pageNumber;

    /**
     * Getter method for property <tt>pageSize</tt>.
     *
     * @return property value of pageSize
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * Setter method for property <tt>pageSize</tt>.
     *
     * @param pageSize value to be assigned to property pageSize
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Getter method for property <tt>pageNumber</tt>.
     *
     * @return property value of pageNumber
     */
    public Integer getPageNumber() {
        return pageNumber;
    }

    /**
     * Setter method for property <tt>pageNumber</tt>.
     *
     * @param pageNumber value to be assigned to property pageNumber
     */
    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
