/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 报关信息。
 *
 * <p>对应 WF submitTradeOrder 接口请求体中的 {@code shipping.declarationInfos[]} 元素。
 * 在 B2B + isUsedForExchange=Y + isShipped=Y + isDeclared=Y 时必填。
 *
 * @author Qoder
 * @version DeclarationInfo.java, v 0.1 2026-04-03
 */
public class DeclarationInfo {

    /** 报关单号，最大 100 字符 */
    private String declarationOrderReferenceNo;

    /** 监管方式：9810 / 9710 / 0110 */
    private String supervisionMethod;

    /** 报关单附件列表 */
    private List<AttachmentInfo> customsDeclarationAttachmentList;

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    /**
     * Getter method for property <tt>declarationOrderReferenceNo</tt>.
     *
     * @return property value of declarationOrderReferenceNo
     */
    public String getDeclarationOrderReferenceNo() {
        return declarationOrderReferenceNo;
    }

    /**
     * Setter method for property <tt>declarationOrderReferenceNo</tt>.
     *
     * @param declarationOrderReferenceNo value to be assigned to property declarationOrderReferenceNo
     */
    public void setDeclarationOrderReferenceNo(String declarationOrderReferenceNo) {
        this.declarationOrderReferenceNo = declarationOrderReferenceNo;
    }

    /**
     * Getter method for property <tt>supervisionMethod</tt>.
     *
     * @return property value of supervisionMethod
     */
    public String getSupervisionMethod() {
        return supervisionMethod;
    }

    /**
     * Setter method for property <tt>supervisionMethod</tt>.
     *
     * @param supervisionMethod value to be assigned to property supervisionMethod
     */
    public void setSupervisionMethod(String supervisionMethod) {
        this.supervisionMethod = supervisionMethod;
    }

    /**
     * Getter method for property <tt>customsDeclarationAttachmentList</tt>.
     *
     * @return property value of customsDeclarationAttachmentList
     */
    public List<AttachmentInfo> getCustomsDeclarationAttachmentList() {
        return customsDeclarationAttachmentList;
    }

    /**
     * Setter method for property <tt>customsDeclarationAttachmentList</tt>.
     *
     * @param customsDeclarationAttachmentList value to be assigned to property customsDeclarationAttachmentList
     */
    public void setCustomsDeclarationAttachmentList(List<AttachmentInfo> customsDeclarationAttachmentList) {
        this.customsDeclarationAttachmentList = customsDeclarationAttachmentList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
