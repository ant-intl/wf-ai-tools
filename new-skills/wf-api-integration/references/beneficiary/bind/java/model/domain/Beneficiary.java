/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

/**
 * 收款人信息对象。
 *
 * <p>绑定收款人成功后返回，或在查询收款人列表中返回。
 *
 * @author Qoder
 * @version Beneficiary.java, v 0.1 2026-03-26
 */
public class Beneficiary {

    /** 收款人令牌（Base64 加密的银行账户信息） */
    private String beneficiaryToken;

    /** 收款人昵称 */
    private String beneficiaryNick;

    /** 账户类型 */
    private String beneficiaryType;

    /** 状态 */
    private String status;

    /** 集成商自定义唯一ID */
    private String referenceBeneficiaryId;

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

    public String getBeneficiaryType() {
        return beneficiaryType;
    }

    public void setBeneficiaryType(String beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReferenceBeneficiaryId() {
        return referenceBeneficiaryId;
    }

    public void setReferenceBeneficiaryId(String referenceBeneficiaryId) {
        this.referenceBeneficiaryId = referenceBeneficiaryId;
    }

    @Override
    public String toString() {
        return "Beneficiary{beneficiaryToken='" + beneficiaryToken
            + "', beneficiaryNick='" + beneficiaryNick
            + "', beneficiaryType='" + beneficiaryType
            + "', status='" + status
            + "', referenceBeneficiaryId='" + referenceBeneficiaryId + "'}";
    }
}
