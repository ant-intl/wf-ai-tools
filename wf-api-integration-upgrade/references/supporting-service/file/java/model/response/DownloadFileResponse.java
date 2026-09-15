package {basePackage}.wf.model.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst download_a_file 响应对象。
 *
 * <p>包含接口调用结果、文件基本信息及临时下载链接。
 * 下载链接有效期 1 小时，仅在 {@code resultStatus} 为 {@code S} 时返回。
 */
public class DownloadFileResponse {

    /** 接口调用结果 */
    private Result result;

    /** 唯一文件标识符 */
    private String id;

    /** 原始文件名 */
    private String fileName;

    /** 文件原始上传时间，ISO 8601 格式 */
    private String createdAt;

    /**
     * 临时下载链接。
     * <p>有效期 1 小时，仅在 resultStatus 为 S 时返回。
     */
    private String downloadUrl;

    /**
     * 下载链接过期时间。
     * <p>ISO 8601 格式，仅在 resultStatus 为 S 时返回。
     */
    private String downloadUrlExpiresAt;

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
     * Getter method for property <tt>fileName</tt>.
     *
     * @return property value of fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Setter method for property <tt>fileName</tt>.
     *
     * @param fileName value to be assigned to property fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
