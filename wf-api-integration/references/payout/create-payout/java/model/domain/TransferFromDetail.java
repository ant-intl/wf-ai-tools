package {basePackage}.wf.model.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 支付方转账详情
 */
public class TransferFromDetail {

    /** 支付方金额 */
    private Amount transferFromAmount;

    /**
     * Getter method for property <tt>transferFromAmount</tt>.
     *
     * @return property value of transferFromAmount
     */
    public Amount getTransferFromAmount() {
        return transferFromAmount;
    }

    /**
     * Setter method for property <tt>transferFromAmount</tt>.
     *
     * @param transferFromAmount value to be assigned to property transferFromAmount
     */
    public void setTransferFromAmount(Amount transferFromAmount) {
        this.transferFromAmount = transferFromAmount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
