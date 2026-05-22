package {basePackage}.wf.model.request;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst removeBeneficiary 请求对象。
 *
 * <p>用于删除已绑定的收款人。
 *
 */
public class RemoveBeneficiaryRequest {

    /** 幂等请求ID，最大 64 字符 */
    private String removeBeneficiaryRequestId;

    /** OAuth access token，最大 64 字符 */
    private String accessToken;

    /** 收款人令牌（Base64 加密），最大 128 字符 */
    private String beneficiaryToken;

    public String getRemoveBeneficiaryRequestId() {
        return removeBeneficiaryRequestId;
    }

    public void setRemoveBeneficiaryRequestId(String removeBeneficiaryRequestId) {
        this.removeBeneficiaryRequestId = removeBeneficiaryRequestId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getBeneficiaryToken() {
        return beneficiaryToken;
    }

    public void setBeneficiaryToken(String beneficiaryToken) {
        this.beneficiaryToken = beneficiaryToken;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
