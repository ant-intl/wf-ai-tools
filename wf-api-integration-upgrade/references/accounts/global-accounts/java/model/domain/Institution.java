package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 全局账户银行机构信息。
 *
 * <p>描述账户所在的银行信息，包括银行名称、地址、支行代码和所在地区。
 */
public class Institution {

    /** 银行地址 */
    private String bankAddress;

    /** 支行代码 */
    private String branchCode;

    /** 银行名称 */
    private String bankName;

    /**
     * 银行所在地区（ISO 3166），如 US、GB、HK。
     * <p>详见 WF 官方文档 Data Types。
     */
    private String bankRegion;

    /**
     * Getter method for property <tt>bankAddress</tt>.
     *
     * @return property value of bankAddress
     */
    public String getBankAddress() {
        return bankAddress;
    }

    /**
     * Setter method for property <tt>bankAddress</tt>.
     *
     * @param bankAddress value to be assigned to property bankAddress
     */
    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    /**
     * Getter method for property <tt>branchCode</tt>.
     *
     * @return property value of branchCode
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * Setter method for property <tt>branchCode</tt>.
     *
     * @param branchCode value to be assigned to property branchCode
     */
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    /**
     * Getter method for property <tt>bankName</tt>.
     *
     * @return property value of bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * Setter method for property <tt>bankName</tt>.
     *
     * @param bankName value to be assigned to property bankName
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * Getter method for property <tt>bankRegion</tt>.
     *
     * @return property value of bankRegion
     */
    public String getBankRegion() {
        return bankRegion;
    }

    /**
     * Setter method for property <tt>bankRegion</tt>.
     *
     * @param bankRegion value to be assigned to property bankRegion
     */
    public void setBankRegion(String bankRegion) {
        this.bankRegion = bankRegion;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
