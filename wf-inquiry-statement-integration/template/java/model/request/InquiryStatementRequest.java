/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.request;

import java.util.List;

/**
 * WorldFirst inquiryStatementList 请求对象
 *
 * @author Qoder
 * @version InquiryStatementRequest.java, v 0.1 2026-03-24
 */
public class InquiryStatementRequest {

    /** 查询起始时间，ISO 8601 格式，fuzzyName 为空时与 endTime 的间隔不超过 100 天（必填） */
    private String startTime;

    /** 查询结束时间，ISO 8601 格式（必填） */
    private String endTime;

    /** 每页条数，固定为 10，不允许调用方修改 */
    private Integer pageSize;

    /** 页码，1 起始，范围 1-50（必填） */
    private Integer pageNumber;

    /** 交易类型列表（可选），如 TRANSFER、COLLECTION 等，为空返回所有类型 */
    private List<String> transactionTypeList;

    /** 货币列表（ISO-4217，可选），为空返回所有货币 */
    private List<String> currencyList;

    /**
     * 余额类型列表（可选）：
     * NORMAL_BALANCE（默认）、SAME_NAME_TOP_UP_BALANCE、BUDGET_BALANCE
     */
    private List<String> balanceTypes;

    /** 预算账户 ID 列表（可选） */
    private List<String> budgetAccountIds;

    /** 模糊搜索关键字（可选），设置后 100 天时间限制取消 */
    private String fuzzyName;

    /**
     * 私有构造，使用 Builder 创建
     */
    private InquiryStatementRequest() {
    }

    /**
     * 创建 Builder
     *
     * @return Builder 实例
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * InquiryStatementRequest Builder
     */
    public static class Builder {
        private final InquiryStatementRequest request = new InquiryStatementRequest();

        /**
         * 设置请求 ID（兼容测试调用，实际不传入 API）
         *
         * @param requestId 请求 ID
         * @return Builder
         */
        public Builder requestId(String requestId) {
            // requestId 仅用于本地追踪，不传给 WF API
            return this;
        }

        /**
         * 设置查询起始时间
         *
         * @param startTime ISO 8601 时间字符串
         * @return Builder
         */
        public Builder startTime(String startTime) {
            request.startTime = startTime;
            return this;
        }

        /**
         * 设置查询结束时间
         *
         * @param endTime ISO 8601 时间字符串
         * @return Builder
         */
        public Builder endTime(String endTime) {
            request.endTime = endTime;
            return this;
        }

        /**
         * 设置每页条数（将被强制覆盖为 10）
         *
         * @param pageSize 每页条数
         * @return Builder
         */
        public Builder pageSize(Integer pageSize) {
            request.pageSize = pageSize;
            return this;
        }

        /**
         * 设置页码（1 起始，最大 50）
         *
         * @param pageNumber 页码
         * @return Builder
         */
        public Builder pageNumber(Integer pageNumber) {
            request.pageNumber = pageNumber;
            return this;
        }

        /**
         * 设置交易类型列表
         *
         * @param transactionTypeList 交易类型列表
         * @return Builder
         */
        public Builder transactionTypeList(List<String> transactionTypeList) {
            request.transactionTypeList = transactionTypeList;
            return this;
        }

        /**
         * 设置货币列表
         *
         * @param currencyList 货币列表
         * @return Builder
         */
        public Builder currencyList(List<String> currencyList) {
            request.currencyList = currencyList;
            return this;
        }

        /**
         * 设置余额类型列表
         *
         * @param balanceTypes 余额类型列表
         * @return Builder
         */
        public Builder balanceTypes(List<String> balanceTypes) {
            request.balanceTypes = balanceTypes;
            return this;
        }

        /**
         * 设置预算账户 ID 列表
         *
         * @param budgetAccountIds 预算账户 ID 列表
         * @return Builder
         */
        public Builder budgetAccountIds(List<String> budgetAccountIds) {
            request.budgetAccountIds = budgetAccountIds;
            return this;
        }

        /**
         * 设置模糊搜索关键字
         *
         * @param fuzzyName 关键字
         * @return Builder
         */
        public Builder fuzzyName(String fuzzyName) {
            request.fuzzyName = fuzzyName;
            return this;
        }

        /**
         * 构建请求对象
         *
         * @return InquiryStatementRequest
         */
        public InquiryStatementRequest build() {
            return request;
        }
    }

    /**
     * Getter method for property <tt>startTime</tt>.
     *
     * @return property value of startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Setter method for property <tt>startTime</tt>.
     *
     * @param startTime value to be assigned to property startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Getter method for property <tt>endTime</tt>.
     *
     * @return property value of endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Setter method for property <tt>endTime</tt>.
     *
     * @param endTime value to be assigned to property endTime
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

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

    /**
     * Getter method for property <tt>transactionTypeList</tt>.
     *
     * @return property value of transactionTypeList
     */
    public List<String> getTransactionTypeList() {
        return transactionTypeList;
    }

    /**
     * Setter method for property <tt>transactionTypeList</tt>.
     *
     * @param transactionTypeList value to be assigned to property transactionTypeList
     */
    public void setTransactionTypeList(List<String> transactionTypeList) {
        this.transactionTypeList = transactionTypeList;
    }

    /**
     * Getter method for property <tt>currencyList</tt>.
     *
     * @return property value of currencyList
     */
    public List<String> getCurrencyList() {
        return currencyList;
    }

    /**
     * Setter method for property <tt>currencyList</tt>.
     *
     * @param currencyList value to be assigned to property currencyList
     */
    public void setCurrencyList(List<String> currencyList) {
        this.currencyList = currencyList;
    }

    /**
     * Getter method for property <tt>balanceTypes</tt>.
     *
     * @return property value of balanceTypes
     */
    public List<String> getBalanceTypes() {
        return balanceTypes;
    }

    /**
     * Setter method for property <tt>balanceTypes</tt>.
     *
     * @param balanceTypes value to be assigned to property balanceTypes
     */
    public void setBalanceTypes(List<String> balanceTypes) {
        this.balanceTypes = balanceTypes;
    }

    /**
     * Getter method for property <tt>budgetAccountIds</tt>.
     *
     * @return property value of budgetAccountIds
     */
    public List<String> getBudgetAccountIds() {
        return budgetAccountIds;
    }

    /**
     * Setter method for property <tt>budgetAccountIds</tt>.
     *
     * @param budgetAccountIds value to be assigned to property budgetAccountIds
     */
    public void setBudgetAccountIds(List<String> budgetAccountIds) {
        this.budgetAccountIds = budgetAccountIds;
    }

    /**
     * Getter method for property <tt>fuzzyName</tt>.
     *
     * @return property value of fuzzyName
     */
    public String getFuzzyName() {
        return fuzzyName;
    }

    /**
     * Setter method for property <tt>fuzzyName</tt>.
     *
     * @param fuzzyName value to be assigned to property fuzzyName
     */
    public void setFuzzyName(String fuzzyName) {
        this.fuzzyName = fuzzyName;
    }

    @Override
    public String toString() {
        return "InquiryStatementRequest{startTime='" + startTime + "', endTime='" + endTime
            + "', pageSize=" + pageSize + ", pageNumber=" + pageNumber
            + ", transactionTypeList=" + transactionTypeList + ", currencyList=" + currencyList
            + ", fuzzyName='" + fuzzyName + "'}";
    }
}
