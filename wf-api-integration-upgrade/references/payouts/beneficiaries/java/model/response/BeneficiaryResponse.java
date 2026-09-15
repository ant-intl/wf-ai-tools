package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.Beneficiary;

/**
 * WorldFirst 收款人单条响应对象。
 *
 * <p>用于 create_a_beneficiary、query_a_beneficiary、update_a_beneficiary、
 * delete_a_beneficiary、validate_a_beneficiary 接口的响应。
 * 继承 {@link Beneficiary} 的所有字段，并增加 {@code result} 调用结果字段。
 */
public class BeneficiaryResponse extends Beneficiary {

    /** API 调用结果 */
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
        return "BeneficiaryResponse{result=" + result
            + ", id='" + getId() + '\''
            + ", status='" + getStatus() + '\''
            + '}';
    }
}
