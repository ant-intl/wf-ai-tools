/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 转账订单附加信息。
 *
 * <p>在 notifyTransfer 回调中，万里汇可能携带此对象，
 * 包含集成商定义的关联订单 ID 等附加信息。
 *
 * @author Qoder
 * @version TransferOrderAddition.java, v 0.1 2026-04-22
 */
public class TransferOrderAddition {

    /** 集成商定义的关联订单 ID */
    private String referenceOrderId;

    /**
     * Getter method for property <tt>referenceOrderId</tt>.
     *
     * @return property value of referenceOrderId
     */
    public String getReferenceOrderId() {
        return referenceOrderId;
    }

    /**
     * Setter method for property <tt>referenceOrderId</tt>.
     *
     * @param referenceOrderId value to be assigned to property referenceOrderId
     */
    public void setReferenceOrderId(String referenceOrderId) {
        this.referenceOrderId = referenceOrderId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
