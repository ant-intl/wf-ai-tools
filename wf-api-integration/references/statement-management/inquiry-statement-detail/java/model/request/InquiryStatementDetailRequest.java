package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst inquiryStatementDetail 请求对象
*
 */
public class InquiryStatementDetailRequest {

    /** 账单流水唯一 ID，通过 inquiryStatementList 接口获取（必填） */
    private String accountingBizNo;

    /**
     * 私有构造，使用 Builder 创建
     */
    private InquiryStatementDetailRequest() {
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
     * InquiryStatementDetailRequest Builder
     */
    public static class Builder {
        private final InquiryStatementDetailRequest request = new InquiryStatementDetailRequest();

        /**
         * 设置账单流水唯一 ID
         *
         * @param accountingBizNo 账单流水唯一 ID
         * @return Builder
         */
        public Builder accountingBizNo(String accountingBizNo) {
            request.accountingBizNo = accountingBizNo;
            return this;
        }

        /**
         * 构建请求对象
         *
         * @return InquiryStatementDetailRequest
         */
        public InquiryStatementDetailRequest build() {
            return request;
        }
    }

    /**
     * Getter method for property <tt>accountingBizNo</tt>.
     *
     * @return property value of accountingBizNo
     */
    public String getAccountingBizNo() {
        return accountingBizNo;
    }

    /**
     * Setter method for property <tt>accountingBizNo</tt>.
     *
     * @param accountingBizNo value to be assigned to property accountingBizNo
     */
    public void setAccountingBizNo(String accountingBizNo) {
        this.accountingBizNo = accountingBizNo;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}