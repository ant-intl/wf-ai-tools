package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 结算周期信息对象。
 *
 * <p>用于无资金即期（UNFUNDED_SPOT）和远期（FORWARD）交易，定义结算日期范围。
 */
public class SettlementPeriod {

    /**
     * 结算开始日期（YYYY-MM-DD 格式，UTC）。
     * <p>UNFUNDED_SPOT 禁止使用；FORWARD 且 mode 为 WINDOWED 时必填。
     */
    private String startDate;

    /**
     * 结算结束日期（YYYY-MM-DD 格式，UTC）。
     * <p>UNFUNDED_SPOT 和 FORWARD 必填。
     */
    private String endDate;

    /**
     * 结算日期类型。
     * <p>可选值：FIXED（固定结算日期）、FLEXIBLE（灵活结算日期）、WINDOWED（窗口结算周期）
     */
    private String mode;

    public SettlementPeriod() {
    }

    /**
     * Getter method for property <tt>startDate</tt>.
     *
     * @return property value of startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Setter method for property <tt>startDate</tt>.
     *
     * @param startDate value to be assigned to property startDate
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Getter method for property <tt>endDate</tt>.
     *
     * @return property value of endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Setter method for property <tt>endDate</tt>.
     *
     * @param endDate value to be assigned to property endDate
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Getter method for property <tt>mode</tt>.
     *
     * @return property value of mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * Setter method for property <tt>mode</tt>.
     *
     * @param mode value to be assigned to property mode
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
