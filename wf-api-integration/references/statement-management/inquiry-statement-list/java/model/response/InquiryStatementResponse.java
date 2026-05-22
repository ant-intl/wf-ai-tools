package {basePackage}.wf.model.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import {basePackage}.wf.model.domain.StatementRecord;

import java.util.List;

/**
 * WorldFirst inquiryStatementList 响应对象
 *
 */
public class InquiryStatementResponse {

    /** 接口调用结果 */
    private Result result;

    /** 响应唯一 ID，最大 32 位 */
    private String responseId;

    /** 费项类型：OBO_SERVICE_FEE、REMIT_SERVICE_FEE */
    private String feeItemType;

    /** 账单流水列表 */
    private List<StatementRecord> statementList;

    /** 总记录数 */
    private Integer totalCount;

    /** 总页数 */
    private Integer totalPageNumber;

    /** 当前页码 */
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
     * Getter method for property <tt>responseId</tt>.
     *
     * @return property value of responseId
     */
    public String getResponseId() {
        return responseId;
    }

    /**
     * Setter method for property <tt>responseId</tt>.
     *
     * @param responseId value to be assigned to property responseId
     */
    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    /**
     * Getter method for property <tt>feeItemType</tt>.
     *
     * @return property value of feeItemType
     */
    public String getFeeItemType() {
        return feeItemType;
    }

    /**
     * Setter method for property <tt>feeItemType</tt>.
     *
     * @param feeItemType value to be assigned to property feeItemType
     */
    public void setFeeItemType(String feeItemType) {
        this.feeItemType = feeItemType;
    }

    /**
     * Getter method for property <tt>statementList</tt>.
     *
     * @return property value of statementList
     */
    public List<StatementRecord> getStatementList() {
        return statementList;
    }

    /**
     * Setter method for property <tt>statementList</tt>.
     *
     * @param statementList value to be assigned to property statementList
     */
    public void setStatementList(List<StatementRecord> statementList) {
        this.statementList = statementList;
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
}
