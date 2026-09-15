package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 身份证明材料文件对象。
 *
 * <p>用于 identification.fileList，表示一个已通过文件上传接口取得 fileKey 的证明材料文件。
 */
public class IdentificationFile {

    /**
     * 文件上传接口返回的文件 key。
     * <p>需先上传各证明材料文件，再将返回的 key 传入此字段。
     */
    private String fileKey;

    /**
     * 原始文件名（含扩展名），如 passport_front.jpg。
     */
    private String fileName;

    public IdentificationFile() {
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
