package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 收款人信息对象。
 *
 * <p>用于 Settlement 中涉及银行账户出金时的收款人详情。
 */
public class Beneficiary {

    /**
     * 收款人唯一标识符。
     * <p>当 accountType 为 BANK_ACCOUNT 时必填。
     */
    private String beneficiaryId;

    /**
     * 收款人账户类型。
     * <p>支持值：BANK_ACCOUNT — 银行账户出金。
     */
    private String accountType;

    public Beneficiary() {
    }

    /**
     * Getter method for property <tt>beneficiaryId</tt>.
     *
     * @return property value of beneficiaryId
     */
    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    /**
     * Setter method for property <tt>beneficiaryId</tt>.
     *
     * @param beneficiaryId value to be assigned to property beneficiaryId
     */
    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    /**
     * Getter method for property <tt>accountType</tt>.
     *
     * @return property value of accountType
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * Setter method for property <tt>accountType</tt>.
     *
     * @param accountType value to be assigned to property accountType
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
