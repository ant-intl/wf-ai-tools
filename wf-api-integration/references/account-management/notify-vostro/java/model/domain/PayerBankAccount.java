package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 付款人银行账户信息（notifyVostro 回调中使用）
 */
public class PayerBankAccount {

    /** 付款人银行账号 */
    private String payerBankAccountNo;

    /** 付款人银行名称 */
    private String payerBankName;

    /**
     * Getter method for property <tt>payerBankAccountNo</tt>.
     *
     * @return property value of payerBankAccountNo
     */
    public String getPayerBankAccountNo() {
        return payerBankAccountNo;
    }

    /**
     * Setter method for property <tt>payerBankAccountNo</tt>.
     *
     * @param payerBankAccountNo value to be assigned to property payerBankAccountNo
     */
    public void setPayerBankAccountNo(String payerBankAccountNo) {
        this.payerBankAccountNo = payerBankAccountNo;
    }

    /**
     * Getter method for property <tt>payerBankName</tt>.
     *
     * @return property value of payerBankName
     */
    public String getPayerBankName() {
        return payerBankName;
    }

    /**
     * Setter method for property <tt>payerBankName</tt>.
     *
     * @param payerBankName value to be assigned to property payerBankName
     */
    public void setPayerBankName(String payerBankName) {
        this.payerBankName = payerBankName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
