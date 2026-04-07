/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

/**
 * WorldFirst 通用金额对象。
 *
 * <p>遵循 <a href="https://developers.worldfirst.com.cn/docs/alipay-worldfirst/worldfirst_enterprise_solution_zh/amount_usage">
 * WF Amount 使用规范</a>，{@code value} 必须以 ISO 4217 定义的<b>最小货币单位</b>表示：
 *
 * <ul>
 *   <li>小数位数 = 2 的币种（USD/CNY/EUR/GBP/HKD/SGD/AUD/CAD 等）：{@code value} = 面额 x 100。
 *       例如 1.00 USD → value = 100</li>
 *   <li>小数位数 = 0 的币种（JPY/KRW/VND/CLP）：{@code value} = 面额 x 1。
 *       例如 1 JPY → value = 1</li>
 * </ul>
 *
 * <p>完整币种精度对照表见 WF 官方文档。
 *
 * @author Qoder
 * @version Amount.java, v 0.1 2026-04-01
 */
public class Amount {

    /** 货币代码（ISO-4217），如 USD、CNY */
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
     * Getter method for property <tt>value</tt>.
     *
     * @return property value of value
     */
    public Long getValue() {
        return value;
    }

    /**
     * Setter method for property <tt>value</tt>.
     *
     * @param value value to be assigned to property value
     */
    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Amount{currency='" + currency + "', value=" + value + "}";
    }
}

