package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst create_a_deal 请求对象。
 *
 * <p>使用从 Quote API 获取的有效报价执行 FX 交易。
 * 交易类型（SPOT、UNFUNDED_SPOT、FORWARD）由所使用的报价决定。
 */
public class CreateDealRequest {

    /**
     * 幂等 ID，用于防止重复提交。
     * <p>每次创建交易请求必须唯一。
     */
    private String requestId;

    /**
     * 报价 ID，从 Quote API 获取。
     * <p>报价在创建交易时必须有效且未过期。
     */
    private String quoteId;

    public CreateDealRequest() {
    }

    /**
     * Getter method for property <tt>requestId</tt>.
     *
     * @return property value of requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Setter method for property <tt>requestId</tt>.
     *
     * @param requestId value to be assigned to property requestId
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * Getter method for property <tt>quoteId</tt>.
     *
     * @return property value of quoteId
     */
    public String getQuoteId() {
        return quoteId;
    }

    /**
     * Setter method for property <tt>quoteId</tt>.
     *
     * @param quoteId value to be assigned to property quoteId
     */
    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
