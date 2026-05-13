package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 关联交易信息
 *
 * <p>对应 inquiryStatementDetail 响应中的 combinedTransactionList 字段，
 * 包含与当前账单流水关联的其他交易信息。
 */
public class RelatedStatement {

    /** 关联交易 ID */
    private String transactionId;

    /** 关联账单流水唯一 ID */
    private String accountingBizNo;

    /** 关联交易类型，如 TRANSFER、CHARGE 等 */
    private String transactionType;

    /**
     * Getter method for property <tt>transactionId</tt>.
     *
     * @return property value of transactionId
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Setter method for property <tt>transactionId</tt>.
     *
     * @param transactionId value to be assigned to property transactionId
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    /**
     * Getter method for property <tt>transactionType</tt>.
     *
     * @return property value of transactionType
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * Setter method for property <tt>transactionType</tt>.
     *
     * @param transactionType value to be assigned to property transactionType
     */
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
