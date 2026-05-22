package {basePackage}.wf.model.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 附件信息。
 *
 * <p>用于物流凭证、合同、报关单据等附件的统一模型。
 * {@code fileKey} 通过文件上传接口获取。
 *
 */
public class AttachmentInfo {

    /** 原始文件名，如 file.pdf，最大 64 字符 */
    private String fileName;

    /** 文件上传后返回的 fileKey，最大 128 字符 */
    private String fileKey;

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
     * Getter method for property <tt>fileKey</tt>.
     *
     * @return property value of fileKey
     */
    public String getFileKey() {
        return fileKey;
    }

    /**
     * Setter method for property <tt>fileKey</tt>.
     *
     * @param fileKey value to be assigned to property fileKey
     */
    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
