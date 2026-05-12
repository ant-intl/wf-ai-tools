/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 营业执照信息
 *
 * @author Qoder
 * @version Certificate.java, v 0.1 2026-04-16
 */
public class Certificate {

    /** 公司营业执照编号 */
    private String certificateNo;

    /** 证书类型：ENTERPRISE_REGISTRATION（营业执照） */
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
