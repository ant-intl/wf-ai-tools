/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

/**
 * 收款方转账详情（createTransfer 户到户转账场景）
 *
 * @author Qoder
 * @version TransferToDetail.java, v 0.1 2026-04-01
 */
public class TransferToDetail {

    /** 收款金额 */
    private Amount transferToAmount;

    /**
     * Getter method for property <tt>transferToAmount</tt>.
     *
     * @return property value of transferToAmount
     */
    public Amount getTransferToAmount() {
        return transferToAmount;
    }

    /**
     * Setter method for property <tt>transferToAmount</tt>.
     *
     * @param transferToAmount value to be assigned to property transferToAmount
     */
    public void setTransferToAmount(Amount transferToAmount) {
        this.transferToAmount = transferToAmount;
    }

    @Override
    public String toString() {
        return "TransferToDetail{transferToAmount=" + transferToAmount + '}';
    }
}

