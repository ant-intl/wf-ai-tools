package {basePackage}.wf.model.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_a_confirmation_letter 响应对象。
 *
 * <p>包含接口调用结果、任务状态及下载链接或失败信息。
 * <ul>
 *   <li>当 {@code status} 为 {@code SUCCESS} 时，返回 {@code downloadUrl} 和 {@code downloadUrlExpiresAt}</li>
 *   <li>当 {@code status} 为 {@code FAIL} 时，返回 {@code failureCode} 和 {@code failureMessage}</li>
 * </ul>
 */
public class QueryConfirmationLetterResponse {

    /** 接口调用结果 */
    private Result result;

    /** 下载任务标识符（回显） */
    private String id;

    /**
     * 任务状态。
     * <p>可选值：
     * <ul>
     *   <li>{@code PROCESSING} — 任务处理中</li>
     *   <li>{@code SUCCESS} — 任务成功，可获取 downloadUrl</li>
     *   <li>{@code FAIL} — 任务失败，可查看 failureCode 和 failureMessage</li>
     * </ul>
     */
    private String status;

    /**
     * 预签名下载链接。
     * <p>仅在 {@code status} 为 {@code SUCCESS} 时返回。
     * <p>最大 2,048 字符。
     */
    private String downloadUrl;

    /**
     * 下载链接过期时间。
     * <p>仅在 {@code status} 为 {@code SUCCESS} 时返回。
     * <p>ISO 8601 扩展格式。
     */
    private String downloadUrlExpiresAt;

    /**
     * 业务失败原因码。
     * <p>仅在 {@code status} 为 {@code FAIL} 时返回。
     */
    private String failureCode;

    /**
     * 失败原因的纯文本描述。
     * <p>仅在 {@code status} 为 {@code FAIL} 时返回。
     */
    private String failureMessage;

    /** 任务创建时间，ISO 8601 扩展格式 */
    private String createdAt;

    /**
     * Getter method for property <tt>result</tt>.
     *
     * @return property value of result
     */
    public Result getResult() {
        return result;
    }

    /**
     * Setter method for property <tt>result</tt>.
     *
     * @param result value to be assigned to property result
     */
    public void setResult(Result result) {
        this.result = result;
    }

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id value to be assigned to property id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     *
     * @param status value to be assigned to property status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Getter method for property <tt>downloadUrl</tt>.
     *
     * @return property value of downloadUrl
     */
    public String getDownloadUrl() {
        return downloadUrl;
    }

    /**
     * Setter method for property <tt>downloadUrl</tt>.
     *
     * @param downloadUrl value to be assigned to property downloadUrl
     */
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    /**
     * Getter method for property <tt>downloadUrlExpiresAt</tt>.
     *
     * @return property value of downloadUrlExpiresAt
     */
    public String getDownloadUrlExpiresAt() {
        return downloadUrlExpiresAt;
    }

    /**
     * Setter method for property <tt>downloadUrlExpiresAt</tt>.
     *
     * @param downloadUrlExpiresAt value to be assigned to property downloadUrlExpiresAt
     */
    public void setDownloadUrlExpiresAt(String downloadUrlExpiresAt) {
        this.downloadUrlExpiresAt = downloadUrlExpiresAt;
    }

    /**
     * Getter method for property <tt>failureCode</tt>.
     *
     * @return property value of failureCode
     */
    public String getFailureCode() {
        return failureCode;
    }

    /**
     * Setter method for property <tt>failureCode</tt>.
     *
     * @param failureCode value to be assigned to property failureCode
     */
    public void setFailureCode(String failureCode) {
        this.failureCode = failureCode;
    }

    /**
     * Getter method for property <tt>failureMessage</tt>.
     *
     * @return property value of failureMessage
     */
    public String getFailureMessage() {
        return failureMessage;
    }

    /**
     * Setter method for property <tt>failureMessage</tt>.
     *
     * @param failureMessage value to be assigned to property failureMessage
     */
    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    /**
     * Getter method for property <tt>createdAt</tt>.
     *
     * @return property value of createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Setter method for property <tt>createdAt</tt>.
     *
     * @param createdAt value to be assigned to property createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
