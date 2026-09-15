package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 全局账户申请的收款能力对象。
 *
 * <p>用于 create_a_global_account 请求中指定账户所需的收款能力，
 * 每个对象描述一种币种 + 支付方式的组合。
 */
public class RequiredFeature {

    /** 货币代码（ISO-4217），如 USD、EUR、GBP */
    private String currency;

    /**
     * 支付方式：LOCAL（本地清算，如 ACH、SEPA、Faster Payments）或 CROSS（跨境汇款，如 SWIFT）。
     * <p>详见 WF 官方文档 PaymentType 枚举。
     */
    private String paymentType;

    public RequiredFeature() {
    }

    /**
     * 构造申请收款能力对象。
     *
     * @param currency    货币代码
     * @param paymentType 支付方式
     */
    public RequiredFeature(String currency, String paymentType) {
        this.currency = currency;
        this.paymentType = paymentType;
    }

    /**
     * Getter method for property <tt>currency</tt>.
     *
     * @return property value of currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Setter method for property <tt>currency</tt>.
     *
     * @param currency value to be assigned to property currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Getter method for property <tt>paymentType</tt>.
     *
     * @return property value of paymentType
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * Setter method for property <tt>paymentType</tt>.
     *
     * @param paymentType value to be assigned to property paymentType
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
