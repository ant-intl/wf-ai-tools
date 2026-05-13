package {basePackage}.wf.model.request;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst editBeneficiary 请求对象。
 *
 * <p>用于修改收款人昵称。
 *
 */
public class EditBeneficiaryRequest {

    /** 收款人令牌（Base64 加密），最大 128 字符 */
    private String beneficiaryToken;

    /** 新收款人昵称，最大 70 字符 */
    private String beneficiaryNick;

    public String getBeneficiaryToken() {
        return beneficiaryToken;
    }

    public void setBeneficiaryToken(String beneficiaryToken) {
        this.beneficiaryToken = beneficiaryToken;
    }

    public String getBeneficiaryNick() {
        return beneficiaryNick;
    }

    public void setBeneficiaryNick(String beneficiaryNick) {
        this.beneficiaryNick = beneficiaryNick;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
