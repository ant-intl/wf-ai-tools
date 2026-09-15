package {basePackage}.wf.model.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 全局账户已开通的收款能力对象。
 *
 * <p>表示账户实际开通的收款能力，包含支付网络和路由码信息。
 * 与 {@link RequiredFeature} 不同，此对象包含银行实际分配的支付网络和路由码。
 */
public class SupportedFeature {

    /**
     * 能力类型，如 RECEIVING（收款能力）。
     * <p>详见 WF 官方文档 SupportedFeatureType 枚举。
     */
    private String type;

    /** 货币代码（ISO-4217），如 USD、EUR、GBP */
    private String currency;

    /**
     * 支付网络，如 ACH、SEPA、SWIFT、FASTER_PAYMENTS、BACS 等。
     * <p>详见 WF 官方文档 PaymentNetwork 枚举。
     */
    private String paymentNetwork;

    /** 路由码列表 */
    private List<RoutingCode> routingCodes;

    /**
     * 支付方式：LOCAL（本地清算）或 CROSS（跨境汇款）。
     * <p>详见 WF 官方文档 PaymentType 枚举。
     */
    private String paymentType;

    /**
     * Getter method for property <tt>type</tt>.
     *
     * @return property value of type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter method for property <tt>type</tt>.
     *
     * @param type value to be assigned to property type
     */
    public void setType(String type) {
        this.type = type;
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
     * Getter method for property <tt>paymentNetwork</tt>.
     *
     * @return property value of paymentNetwork
     */
    public String getPaymentNetwork() {
        return paymentNetwork;
    }

    /**
     * Setter method for property <tt>paymentNetwork</tt>.
     *
     * @param paymentNetwork value to be assigned to property paymentNetwork
     */
    public void setPaymentNetwork(String paymentNetwork) {
        this.paymentNetwork = paymentNetwork;
    }

    /**
     * Getter method for property <tt>routingCodes</tt>.
     *
     * @return property value of routingCodes
     */
    public List<RoutingCode> getRoutingCodes() {
        return routingCodes;
    }

    /**
     * Setter method for property <tt>routingCodes</tt>.
     *
     * @param routingCodes value to be assigned to property routingCodes
     */
    public void setRoutingCodes(List<RoutingCode> routingCodes) {
        this.routingCodes = routingCodes;
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
