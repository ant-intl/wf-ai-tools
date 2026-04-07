/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst inquiryPayout 代发单级别结果。
 *
 * <p>与 {@code result}（API 调用级别）区分：
 * <ul>
 *   <li>{@code result} — 本次 HTTP 请求是否成功</li>
 *   <li>{@code transferResult} — 代发单本身的处理状态</li>
 * </ul>
 *
 * <p>{@code resultStatus} 取值：
 * <ul>
 *   <li>{@code S} — 代发成功（resultCode=SUCCESS）或仍处理中（resultCode=PROCESSING）</li>
 *   <li>{@code F} — 代发失败，不可重试</li>
 *   <li>{@code U} — 处理异常，可重试</li>
 * </ul>
 *
 * @author Qoder
 * @version TransferResult.java, v 0.1 2026-03-27
 */
public class TransferResult {

    /** 代发单处理状态：S / F / U */
    private String resultStatus;

    /** 代发单结果码，如 SUCCESS / PROCESSING / PROCESS_FAIL 等 */
    private String resultCode;

    /** 结果描述 */
    private String resultMessage;

    /**
     * 是否最终成功。
     *
     * @return resultStatus=S 且 resultCode=SUCCESS 时返回 true
     */
    public boolean isSuccess() {
        return "S".equals(resultStatus) && "SUCCESS".equals(resultCode);
    }

    /**
     * 是否仍在处理中（需继续轮询）。
     *
     * @return resultStatus=S 且 resultCode=PROCESSING 时返回 true
     */
    public boolean isProcessing() {
        return "S".equals(resultStatus) && "PROCESSING".equals(resultCode);
    }

    public String getResultStatus() { return resultStatus; }
    public void setResultStatus(String resultStatus) { this.resultStatus = resultStatus; }
    public String getResultCode() { return resultCode; }
    public void setResultCode(String resultCode) { this.resultCode = resultCode; }
    public String getResultMessage() { return resultMessage; }
    public void setResultMessage(String resultMessage) { this.resultMessage = resultMessage; }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
