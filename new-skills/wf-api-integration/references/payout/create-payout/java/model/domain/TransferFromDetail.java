/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

/**
 * 支付方转账详情
 *
 * @author Qoder
 * @version TransferFromDetail.java, v 0.1 2026-03-25
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
        return "TransferFromDetail{transferFromAmount=" + transferFromAmount + '}';
    }
}
