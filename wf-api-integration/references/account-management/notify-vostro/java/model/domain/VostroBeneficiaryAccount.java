/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 收款人万里汇 VA 账户信息（notifyVostro 回调中使用）
 *
 * @author Qoder
 * @version VostroBeneficiaryAccount.java, v 0.1 2026-04-21
 */
public class VostroBeneficiaryAccount {

    /** 收款人万里汇 VA 账号 */
    private String beneficiaryBankAccountNo;

    /**
     * Getter method for property <tt>beneficiaryBankAccountNo</tt>.
     *
     * @return property value of beneficiaryBankAccountNo
     */
    public String getBeneficiaryBankAccountNo() {
        return beneficiaryBankAccountNo;
    }

    /**
     * Setter method for property <tt>beneficiaryBankAccountNo</tt>.
     *
     * @param beneficiaryBankAccountNo value to be assigned to property beneficiaryBankAccountNo
     */
    public void setBeneficiaryBankAccountNo(String beneficiaryBankAccountNo) {
        this.beneficiaryBankAccountNo = beneficiaryBankAccountNo;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
