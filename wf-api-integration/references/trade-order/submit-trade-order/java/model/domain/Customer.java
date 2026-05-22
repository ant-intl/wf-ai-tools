package {basePackage}.wf.model.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * B2C 卖家信息。
 *
 * <p>对应 WF submitTradeOrder 接口请求体中的 {@code seller} 字段。
 *
 */
public class Customer {

    /** WF 分配的用户 ID，最大 64 字符 */
    private String customerId;

    /** 集成方分配的用户 ID，最大 64 字符 */
    private String referenceCustomerId;

    /** 企业名称，最大 256 字符 */
    private String customerCompanyName;

    /** 证件列表 */
    private List<Certificate> certificateList;

    /**
     * Getter method for property <tt>customerId</tt>.
     *
     * @return property value of customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Setter method for property <tt>customerId</tt>.
     *
     * @param customerId value to be assigned to property customerId
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter method for property <tt>referenceCustomerId</tt>.
     *
     * @return property value of referenceCustomerId
     */
    public String getReferenceCustomerId() {
        return referenceCustomerId;
    }

    /**
     * Setter method for property <tt>referenceCustomerId</tt>.
     *
     * @param referenceCustomerId value to be assigned to property referenceCustomerId
     */
    public void setReferenceCustomerId(String referenceCustomerId) {
        this.referenceCustomerId = referenceCustomerId;
    }

    /**
     * Getter method for property <tt>customerCompanyName</tt>.
     *
     * @return property value of customerCompanyName
     */
    public String getCustomerCompanyName() {
        return customerCompanyName;
    }

    /**
     * Setter method for property <tt>customerCompanyName</tt>.
     *
     * @param customerCompanyName value to be assigned to property customerCompanyName
     */
    public void setCustomerCompanyName(String customerCompanyName) {
        this.customerCompanyName = customerCompanyName;
    }

    /**
     * Getter method for property <tt>certificateList</tt>.
     *
     * @return property value of certificateList
     */
    public List<Certificate> getCertificateList() {
        return certificateList;
    }

    /**
     * Setter method for property <tt>certificateList</tt>.
     *
     * @param certificateList value to be assigned to property certificateList
     */
    public void setCertificateList(List<Certificate> certificateList) {
        this.certificateList = certificateList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    // -------------------------------------------------------------------------
    // 内部类
    // -------------------------------------------------------------------------

    /**
     * 证件信息。
     *
     * <p>用于卖家的营业执照等证件。
     */
    public static class Certificate {

        /** 证件编号，最大 128 字符 */
        private String certificateNo;

        /** 证件类型，如 ENTERPRISE_REGISTRATION */
        private String certificateType;

        /**
         * Getter method for property <tt>certificateNo</tt>.
         *
         * @return property value of certificateNo
         */
        public String getCertificateNo() {
            return certificateNo;
        }

        /**
         * Setter method for property <tt>certificateNo</tt>.
         *
         * @param certificateNo value to be assigned to property certificateNo
         */
        public void setCertificateNo(String certificateNo) {
            this.certificateNo = certificateNo;
        }

        /**
         * Getter method for property <tt>certificateType</tt>.
         *
         * @return property value of certificateType
         */
        public String getCertificateType() {
            return certificateType;
        }

        /**
         * Setter method for property <tt>certificateType</tt>.
         *
         * @param certificateType value to be assigned to property certificateType
         */
        public void setCertificateType(String certificateType) {
            this.certificateType = certificateType;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }
}
