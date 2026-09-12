package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst upload_a_file 请求对象。
 *
 * <p>上传 base64 编码的文件至 WorldFirst，用于开户、KYC/KYB 验证等场景。
 * 支持格式：PDF、JPG、JPEG、PNG、ZIP，原始文件最大 7 MB。
 */
public class UploadFileRequest {

    /**
     * Base64 编码的文件内容。
     * <p>原始文件大小不超过 7 MB。
     */
    private String fileContent;

    /**
     * 原始文件名（含扩展名）。
     * <p>允许的扩展名：pdf、jpg、jpeg、png、zip。
     * <p>不允许的字符：{ ，/ : * ? " < > |}。
     * <p>长度不超过 256 字符。
     */
    private String fileName;

    public UploadFileRequest() {
    }

    /**
     * Getter method for property <tt>fileContent</tt>.
     *
     * @return property value of fileContent
     */
    public String getFileContent() {
        return fileContent;
    }

    /**
     * Setter method for property <tt>fileContent</tt>.
     *
     * @param fileContent value to be assigned to property fileContent
     */
    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
