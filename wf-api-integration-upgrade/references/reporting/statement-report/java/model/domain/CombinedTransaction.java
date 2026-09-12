package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 关联交易对象。
 *
 * <p>描述与主交易关联的其他交易记录（如手续费扣款等）。
 */
public class CombinedTransaction {

    /** 关联交易金额 */
    private Amount transactionAmount;

    /** 关联交易时间（ISO 8601 格式） */
    private String transactedAt;

    /** 关联交易唯一 ID */
    private String transactionId;

    /** 关联交易类型 */
    private String transactionType;

    /** 关联交易状态 */
    private String status;

    public CombinedTransaction() {
    }

    /**
     * Getter method for property <tt>transactionAmount</tt>.
     *
     * @return property value of transactionAmount
     */
    public Amount getTransactionAmount() {
        return transactionAmount;
    }

    /**
     * Setter method for property <tt>transactionAmount</tt>.
     *
     * @param transactionAmount value to be assigned to property transactionAmount
     */
    public void setTransactionAmount(Amount transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    /**
     * Getter method for property <tt>transactedAt</tt>.
     *
     * @return property value of transactedAt
     */
    public String getTransactedAt() {
        return transactedAt;
    }

    /**
     * Setter method for property <tt>transactedAt</tt>.
     *
     * @param transactedAt value to be assigned to property transactedAt
     */
    public void setTransactedAt(String transactedAt) {
        this.transactedAt = transactedAt;
    }

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

    /**
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     *
     * @param status value to be assigned to property status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
