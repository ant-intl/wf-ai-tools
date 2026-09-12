package {basePackage}.wf.model.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst create_a_budget 响应对象。
 *
 * <p>创建成功后 status 为 ACTIVE；预算账户一经创建不可关闭。
 */
public class CreateBudgetResponse {

    /**
     * 接口调用结果（resultStatus S/F/U、resultCode、resultMessage）。
     */
    private Result result;

    /**
     * 预算账户唯一标识，最大 64 字符。
     * <p>用于后续查询、入金、出金。
     */
    private String id;

    /**
     * 预算账户名称，回显请求值；请求未传时为系统自动分配的名称。
     */
    private String name;

    /**
     * 预算账户当前状态（BudgetStatus 枚举）：ACTIVE（已激活可用，非终态）、FAILED
     * （创建失败，终态）。
     */
    private String status;

    /**
     * 预算账户创建时间，ISO 8601 扩展格式（如 2026-08-08T12:46:26Z）。
     */
    private String createdAt;

    public CreateBudgetResponse() {
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
