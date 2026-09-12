package {basePackage}.wf.model.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst API 通用响应结果对象。
 * <p>
 * 所有 WF API 响应均包含此结果对象，用于标识请求的处理状态、结果码和描述信息。
 * <ul>
 *   <li>{@code resultStatus}：结果状态，S=成功，F=失败，U=未知（可重试）</li>
 *   <li>{@code resultCode}：结果码，对应 {@link {basePackage}.wf.model.exception.WfErrorCode}</li>
 *   <li>{@code resultMessage}：人类可读的结果描述</li>
 * </ul>
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
     * @return 结果状态
     */
    public String getResultStatus() {
        return resultStatus;
    }

    /**
     * Setter method for property <tt>resultStatus</tt>.
     *
     * @param resultStatus 结果状态
     */
    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    /**
     * Getter method for property <tt>resultCode</tt>.
     *
     * @return 结果码
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * Setter method for property <tt>resultCode</tt>.
     *
     * @param resultCode 结果码
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * Getter method for property <tt>resultMessage</tt>.
     *
     * @return 结果描述
     */
    public String getResultMessage() {
        return resultMessage;
    }

    /**
     * Setter method for property <tt>resultMessage</tt>.
     *
     * @param resultMessage 结果描述
     */
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
