/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.response;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst API 通用响应结果对象
 *
 * @author Qoder
 * @version Result.java, v 0.1 2026-04-01
 */
public class Result {

    /** 结果状态：S=成功，F=失败，U=未知（可重试） */
    private String resultStatus;

    /** 结果码 */
    private String resultCode;

    /** 结果描述 */
    private String resultMessage;

    /**
     * Getter method for property <tt>resultStatus</tt>.
     *
     * @return property value of resultStatus
     */
    public String getResultStatus() {
        return resultStatus;
    }

    /**
     * Setter method for property <tt>resultStatus</tt>.
     *
     * @param resultStatus value to be assigned to property resultStatus
     */
    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    /**
     * Getter method for property <tt>resultCode</tt>.
     *
     * @return property value of resultCode
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * Setter method for property <tt>resultCode</tt>.
     *
     * @param resultCode value to be assigned to property resultCode
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * Getter method for property <tt>resultMessage</tt>.
     *
     * @return property value of resultMessage
     */
    public String getResultMessage() {
        return resultMessage;
    }

    /**
     * Setter method for property <tt>resultMessage</tt>.
     *
     * @param resultMessage value to be assigned to property resultMessage
     */
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

