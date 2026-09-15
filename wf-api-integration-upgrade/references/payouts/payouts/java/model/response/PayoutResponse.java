package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.Payout;

/**
 * WorldFirst 代发（Payout）响应对象。
 *
 * <p>用于 consult_a_payout、create_a_payout、query_a_payout 接口的响应。
 * 继承 {@link Payout} 的所有代发字段，并增加 {@code result} 调用结果字段。
 *
 * <p>consult 响应中 id/status/failureCode/failureMessage/returnedAmount/returnedAt 为 null；
 * create 响应中 returnedAmount/returnedAt 为 null；query 响应包含所有字段。
 */
public class PayoutResponse extends Payout {

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
        return "PayoutResponse{result=" + result
            + ", id='" + getId() + '\''
            + ", status='" + getStatus() + '\''
            + ", paymentType='" + getPaymentType() + '\''
            + '}';
    }
}
