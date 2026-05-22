package {basePackage}.wf.model.response;

import {basePackage}.wf.model.domain.Beneficiary;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst bindBeneficiary 响应对象。
 *
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
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
