package {basePackage}.wf.model.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst create_a_connected_account 响应对象。
 *
 * <p>包含接口调用结果及新创建的关联账户基本信息。
 */
public class CreateConnectedAccountResponse {

    /** API 调用结果 */
    private Result result;

    /**
     * 账户注册状态。
     * <p>可选值：REGISTERED、PROCESSING、SUCCESS、FAILED、REJECT
     */
    private String status;

    /** WorldFirst 分配的唯一商户标识符（Account ID） */
    private String id;

    /** 集成商侧的商户标识符（与请求中的 referenceAccountId 一致） */
    private String referenceAccountId;

    /** 账户创建时间（ISO 8601 格式） */
    private String createdAt;

    public CreateConnectedAccountResponse() {
    }

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

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id value to be assigned to property id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>referenceAccountId</tt>.
     *
     * @return property value of referenceAccountId
     */
    public String getReferenceAccountId() {
        return referenceAccountId;
    }

    /**
     * Setter method for property <tt>referenceAccountId</tt>.
     *
     * @param referenceAccountId value to be assigned to property referenceAccountId
     */
    public void setReferenceAccountId(String referenceAccountId) {
        this.referenceAccountId = referenceAccountId;
    }

    /**
     * Getter method for property <tt>createdAt</tt>.
     *
     * @return property value of createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Setter method for property <tt>createdAt</tt>.
     *
     * @param createdAt value to be assigned to property createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
