package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 手续费明细项。
 */
public class FeeItem {

    /** 手续费类型，如 REMIT_SERVICE_FEE、PAYOUT_SERVICE_FEE、OBO_SERVICE_FEE、FVT_SERVICE_FEE */
    private String feeItemType;

    /** 手续费金额 */
    private Amount feeAmount;

    /**
     * Getter method for property <tt>feeItemType</tt>.
     *
     * @return property value of feeItemType
     */
    public String getFeeItemType() {
        return feeItemType;
    }

    /**
     * Setter method for property <tt>feeItemType</tt>.
     *
     * @param feeItemType value to be assigned to property feeItemType
     */
    public void setFeeItemType(String feeItemType) {
        this.feeItemType = feeItemType;
    }

    /**
     * Getter method for property <tt>feeAmount</tt>.
     *
     * @return property value of feeAmount
     */
    public Amount getFeeAmount() {
        return feeAmount;
    }

    /**
     * Setter method for property <tt>feeAmount</tt>.
     *
     * @param feeAmount value to be assigned to property feeAmount
     */
    public void setFeeAmount(Amount feeAmount) {
        this.feeAmount = feeAmount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
