package {basePackage}.wf.model.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 支付/收款方式。
 *
 */
public class PaymentMethod {

    /**
     * 支付/收款方式。
     *
     * <p>可取值：
     * <ul>
     *   <li>BALANCE：使用万里汇账户余额做为支付/收款方法</li>
     *   <li>WORLDFIRST_RECEIVE_ACCOUNT：使用万里汇RA账户进行收款</li>
     * </ul>
     */
    private String paymentMethodType;

    /**
     * 收款方式ID。
     *
     * <p>当 paymentMethodType 字段取值为 WORLDFIRST_RECEIVE_ACCOUNT 时，此字段为必传。
     * 其取值可从集成商门户获取。
     *
     * <p>最大长度：128 字符
     */
    private String paymentMethodId;

    /**
     * 由万里汇定义、用于唯一标识某万里汇账户。
     *
     * <p>当 paymentMethodType 字段取值为 BALANCE 时，此字段为必传。
     *
     * <p>最大长度：64 字符
     */
    private String accountId;

    /**
     * Getter method for property <tt>paymentMethodType</tt>.
     *
     * @return property value of paymentMethodType
     */
    public String getPaymentMethodType() {
        return paymentMethodType;
    }

    /**
     * Setter method for property <tt>paymentMethodType</tt>.
     *
     * @param paymentMethodType value to be assigned to property paymentMethodType
     */
    public void setPaymentMethodType(String paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    /**
     * Getter method for property <tt>paymentMethodId</tt>.
     *
     * @return property value of paymentMethodId
     */
    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    /**
     * Setter method for property <tt>paymentMethodId</tt>.
     *
     * @param paymentMethodId value to be assigned to property paymentMethodId
     */
    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    /**
     * Getter method for property <tt>accountId</tt>.
     *
     * @return property value of accountId
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Setter method for property <tt>accountId</tt>.
     *
     * @param accountId value to be assigned to property accountId
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
