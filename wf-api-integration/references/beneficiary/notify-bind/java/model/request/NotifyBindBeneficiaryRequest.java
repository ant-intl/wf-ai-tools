/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.request;

import {basePackage}.wf.model.domain.NotifyBeneficiary;
import {basePackage}.wf.model.response.Result;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst notifyBindBeneficiary 回调请求对象。
 *
 * <p>收款人绑定成功后，万里汇会主动调用此接口将收款人绑定结果通知给集成商。
 * {@code bindBeneficiaryRequestId} 为幂等字段，集成商可用于去重。
 *
 * @author Qoder
 * @version NotifyBindBeneficiaryRequest.java, v 0.1 2026-04-21
 */
public class NotifyBindBeneficiaryRequest {

    /** 集成商定义的幂等请求 ID，最大 64 字符 */
    private String bindBeneficiaryRequestId;

    /** 收款人绑定结果 */
    private Result result;

    /** 收款人信息（仅 result.resultStatus=S 时有值） */
    private NotifyBeneficiary beneficiary;

    /**
     * Getter method for property <tt>bindBeneficiaryRequestId</tt>.
     *
     * @return property value of bindBeneficiaryRequestId
     */
    public String getBindBeneficiaryRequestId() {
        return bindBeneficiaryRequestId;
    }

    /**
     * Setter method for property <tt>bindBeneficiaryRequestId</tt>.
     *
     * @param bindBeneficiaryRequestId value to be assigned to property bindBeneficiaryRequestId
     */
    public void setBindBeneficiaryRequestId(String bindBeneficiaryRequestId) {
        this.bindBeneficiaryRequestId = bindBeneficiaryRequestId;
    }

    /**
     * Getter method for property <tt>result</tt>.
     *
     * @return property value of result
     */
    public Result getResult() {
        return result;
    }

    /**
     * Setter method for property <tt>result</tt>.
     *
     * @param result value to be assigned to property result
     */
    public void setResult(Result result) {
        this.result = result;
    }

    /**
     * Getter method for property <tt>beneficiary</tt>.
     *
     * @return property value of beneficiary
     */
    public NotifyBeneficiary getBeneficiary() {
        return beneficiary;
    }

    /**
     * Setter method for property <tt>beneficiary</tt>.
     *
     * @param beneficiary value to be assigned to property beneficiary
     */
    public void setBeneficiary(NotifyBeneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
