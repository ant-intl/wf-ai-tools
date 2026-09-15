package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 通用金额对象。
 *
 * <p>表示货币金额，值以最小货币单位（minor units）存储以避免浮点精度问题。
 *
 * <p>遵循 WF Amount 使用规范，{@code value} 必须以 ISO 4217 定义的<b>最小货币单位</b>表示：
 * <ul>
 *   <li>小数位数 = 2 的币种（USD/CNY/EUR/GBP/HKD/SGD/AUD/CAD 等）：{@code value} = 面额 x 100。
 *       例如 10.00 USD → value = 1000</li>
 *   <li>小数位数 = 0 的币种（JPY/KRW/VND/CLP）：{@code value} = 面额 x 1。
 *       例如 1000 JPY → value = 1000</li>
 * </ul>
 *
 * <p>完整币种精度对照表见 WF 官方文档。
 *
 * @see <a href="https://docs.worldfirst.com/wfdocs/api-sdk/data_types">Data Types</a>
 */
public class Amount {

    /**
     * 货币代码（ISO-4217），三字母格式。
     * <p>使用 CNH 表示离岸人民币。例如 "USD"。
     */
    private String currency;

    /**
     * 金额值，以最小货币单位表示的整数。
     *
     * <p>示例：100.50 USD → value = 10050；200 JPY → value = 200
     */
    private Long value;

    public Amount() {
    }

    /**
     * 构造金额对象。
     *
     * @param currency ISO-4217 货币代码
     * @param value    以最小货币单位表示的整数金额
     */
    public Amount(String currency, Long value) {
        this.currency = currency;
        this.value = value;
    }

    /**
     * Getter method for property <tt>currency</tt>.
     *
     * @return 货币代码
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Setter method for property <tt>currency</tt>.
     *
     * @param currency 货币代码
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Getter method for property <tt>value</tt>.
     *
     * @return 以最小货币单位表示的整数金额
     */
    public Long getValue() {
        return value;
    }

    /**
     * Setter method for property <tt>value</tt>.
     *
     * @param value 以最小货币单位表示的整数金额
     */
    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
