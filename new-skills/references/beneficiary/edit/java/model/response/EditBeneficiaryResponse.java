/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.response;

/**
 * WorldFirst editBeneficiary 响应对象。
 *
 * @author Qoder
 * @version EditBeneficiaryResponse.java, v 0.1 2026-03-26
 */
public class EditBeneficiaryResponse {

    /** API 调用结果 */
    private Result result;

    /** 收款人令牌 */
    private String beneficiaryToken;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getBeneficiaryToken() {
        return beneficiaryToken;
    }

    public void setBeneficiaryToken(String beneficiaryToken) {
        this.beneficiaryToken = beneficiaryToken;
    }

    public boolean isSuccess() {
        return result != null && "S".equals(result.getResultStatus());
    }

    @Override
    public String toString() {
        return "EditBeneficiaryResponse{result=" + result
            + ", beneficiaryToken='" + beneficiaryToken + "'}";
    }
}
