package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.GlobalAccount;

/**
 * WorldFirst 全局账户单账户响应对象。
 *
 * <p>用于 create_a_global_account、query_a_global_account、update_a_global_account、
 * close_a_global_account 接口的响应。继承 {@link GlobalAccount} 的所有账户字段，
 * 并增加 {@code result} 调用结果字段。
 *
 * <p>响应 JSON 中 {@code result} 与账户字段平铺在同一层级，fastjson2 反序列化时
 * 会自动将父类字段和本类字段映射到同一 JSON 对象。
 */
public class GlobalAccountResponse extends GlobalAccount {

    /** 接口调用结果 */
    private Result result;

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

    @Override
    public String toString() {
        return "GlobalAccountResponse{result=" + result
            + ", id='" + getId() + '\''
            + ", status='" + getStatus() + '\''
            + ", accountNumber='" + getAccountNumber() + '\''
            + '}';
    }
}
