/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.Beneficiary;

/**
 * WorldFirst bindBeneficiary 响应对象。
 *
 * @author Qoder
 * @version BindBeneficiaryResponse.java, v 0.1 2026-03-26
 */
public class BindBeneficiaryResponse {

    /** API 调用结果 */
    private Result result;

    /** 绑定的收款人信息 */
    private Beneficiary beneficiary;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Beneficiary getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }

    public boolean isSuccess() {
        return result != null && "S".equals(result.getResultStatus());
    }

    @Override
    public String toString() {
        return "BindBeneficiaryResponse{result=" + result
            + ", beneficiary=" + beneficiary + "}";
    }
}
