package {basePackage}.wf.model.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 支付方转账详情（createTransfer 户到户转账场景）
 *
 */
public class TransferFromDetail {

    /** 转账付款的方法 */
    private PaymentMethod transferFromMethod;

    /** 支付方需要支付的金额（计算手续费之前的金额） */
    private Amount transferFromAmount;

    /** 实际转账金额（计算手续费之后的金额） */
    private Amount actualTransferFromAmount;

    /** 付款方支付的转账手续费 */
    private Amount feeAmount;

    /**
     * Getter method for property <tt>transferFromMethod</tt>.
     *
     * @return property value of transferFromMethod
     */
    public PaymentMethod getTransferFromMethod() {
        return transferFromMethod;
    }

    /**
     * Setter method for property <tt>transferFromMethod</tt>.
     *
     * @param transferFromMethod value to be assigned to property transferFromMethod
     */
    public void setTransferFromMethod(PaymentMethod transferFromMethod) {
        this.transferFromMethod = transferFromMethod;
    }

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

    /**
     * Getter method for property <tt>actualTransferFromAmount</tt>.
     *
     * @return property value of actualTransferFromAmount
     */
    public Amount getActualTransferFromAmount() {
        return actualTransferFromAmount;
    }

    /**
     * Setter method for property <tt>actualTransferFromAmount</tt>.
     *
     * @param actualTransferFromAmount value to be assigned to property actualTransferFromAmount
     */
    public void setActualTransferFromAmount(Amount actualTransferFromAmount) {
        this.actualTransferFromAmount = actualTransferFromAmount;
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

