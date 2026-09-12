package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst create_a_budget 请求对象。
 *
 * <p>用于创建预算账户，划拨并持有用于发卡消费的资金。
 * 单个商户账户最多持有 3 个预算账户，预算账户一经创建不可关闭。
 */
public class CreateBudgetRequest {

    /**
     * 幂等键，最大 64 字符。
     * <p>每次创建请求需唯一（建议 UUID）；网络重试须复用同一取值，避免重复创建预算账户。
     */
    private String requestId;

    /**
     * 预算账户名称，最大 50 字符。
     * <p>不传时由系统自动分配名称。
     */
    private String name;

    public CreateBudgetRequest() {
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
     * Getter method for property <tt>name</tt>.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     *
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
