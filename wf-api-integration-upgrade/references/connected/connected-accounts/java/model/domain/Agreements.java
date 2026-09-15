package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 协议记录对象。
 *
 * <p>描述商户在注册时同意的协议条款。
 */
public class Agreements {

    /** 是否同意平台服务协议 */
    private Boolean agreedToTermsAndConditions;

    /** 是否同意数据使用授权 */
    private Boolean agreedToDataUsage;

    /** 是否同意 2C2P 条款，TH 地区必填 */
    private Boolean agreedTo2C2PAnd2C2BPlusTermsAndConditions;

    /** 平台操作权限：FULL_ACCESS / SCOPED */
    private String accessType;

    /** 授权范围，空格分隔，accessType 为 SCOPED 时必填 */
    private String accessScope;

    public Agreements() {
    }

    /**
     * Getter method for property <tt>agreedToTermsAndConditions</tt>.
     *
     * @return property value of agreedToTermsAndConditions
     */
    public Boolean getAgreedToTermsAndConditions() {
        return agreedToTermsAndConditions;
    }

    /**
     * Setter method for property <tt>agreedToTermsAndConditions</tt>.
     *
     * @param agreedToTermsAndConditions value to be assigned to property agreedToTermsAndConditions
     */
    public void setAgreedToTermsAndConditions(Boolean agreedToTermsAndConditions) {
        this.agreedToTermsAndConditions = agreedToTermsAndConditions;
    }

    /**
     * Getter method for property <tt>agreedToDataUsage</tt>.
     *
     * @return property value of agreedToDataUsage
     */
    public Boolean getAgreedToDataUsage() {
        return agreedToDataUsage;
    }

    /**
     * Setter method for property <tt>agreedToDataUsage</tt>.
     *
     * @param agreedToDataUsage value to be assigned to property agreedToDataUsage
     */
    public void setAgreedToDataUsage(Boolean agreedToDataUsage) {
        this.agreedToDataUsage = agreedToDataUsage;
    }

    /**
     * Getter method for property <tt>agreedTo2C2PAnd2C2BPlusTermsAndConditions</tt>.
     *
     * @return property value of agreedTo2C2PAnd2C2BPlusTermsAndConditions
     */
    public Boolean getAgreedTo2C2PAnd2C2BPlusTermsAndConditions() {
        return agreedTo2C2PAnd2C2BPlusTermsAndConditions;
    }

    /**
     * Setter method for property <tt>agreedTo2C2PAnd2C2BPlusTermsAndConditions</tt>.
     *
     * @param agreedTo2C2PAnd2C2BPlusTermsAndConditions value to be assigned to property agreedTo2C2PAnd2C2BPlusTermsAndConditions
     */
    public void setAgreedTo2C2PAnd2C2BPlusTermsAndConditions(Boolean agreedTo2C2PAnd2C2BPlusTermsAndConditions) {
        this.agreedTo2C2PAnd2C2BPlusTermsAndConditions = agreedTo2C2PAnd2C2BPlusTermsAndConditions;
    }

    /**
     * Getter method for property <tt>accessType</tt>.
     *
     * @return property value of accessType
     */
    public String getAccessType() {
        return accessType;
    }

    /**
     * Setter method for property <tt>accessType</tt>.
     *
     * @param accessType value to be assigned to property accessType
     */
    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    /**
     * Getter method for property <tt>accessScope</tt>.
     *
     * @return property value of accessScope
     */
    public String getAccessScope() {
        return accessScope;
    }

    /**
     * Setter method for property <tt>accessScope</tt>.
     *
     * @param accessScope value to be assigned to property accessScope
     */
    public void setAccessScope(String accessScope) {
        this.accessScope = accessScope;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
