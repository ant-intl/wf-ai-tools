/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 收款人信息
 *
 * @author Qoder
 * @version BeneficiaryInfo.java, v 0.1 2026-03-25
 */
public class BeneficiaryInfo {

    /** 收款人姓名（须与银行开户名一致） */
    private String beneficiaryName;

    /**
     * Getter method for property <tt>beneficiaryName</tt>.
     *
     * @return property value of beneficiaryName
     */
    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    /**
     * Setter method for property <tt>beneficiaryName</tt>.
     *
     * @param beneficiaryName value to be assigned to property beneficiaryName
     */
    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
