package {basePackage}.wf.model.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 支付宝账户信息。
 *
 * <p>当 beneficiaryType 为 RELATED_MERCHANT_ALIPAY_COMPANY_ACCOUNT 时必填。
 *
 */
public class BeneficiaryAlipayAccount {

    /** 支付宝账户名称 */
    private String alipayAccountName;

    /** 支付宝账户ID */
    private String alipayAccountId;

    public String getAlipayAccountName() {
        return alipayAccountName;
    }

    public void setAlipayAccountName(String alipayAccountName) {
        this.alipayAccountName = alipayAccountName;
    }

    public String getAlipayAccountId() {
        return alipayAccountId;
    }

    public void setAlipayAccountId(String alipayAccountId) {
        this.alipayAccountId = alipayAccountId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
