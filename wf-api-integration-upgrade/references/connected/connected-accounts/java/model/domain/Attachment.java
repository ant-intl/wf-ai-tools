package {basePackage}.wf.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 附件信息对象。
 *
 * <p>描述关联账户的附件信息，包括附件类型、文件 key 和原始文件名。
 */
public class Attachment {

    /** 附件类型，AttachmentType 枚举 */
    private String attachmentType;

    /** 上传文件 key，最大64字符 */
    private String attachmentKey;

    /** 原始文件名，最大256字符 */
    private String attachmentName;

    public Attachment() {
    }

    /**
     * Getter method for property <tt>attachmentType</tt>.
     *
     * @return property value of attachmentType
     */
    public String getAttachmentType() {
        return attachmentType;
    }

    /**
     * Setter method for property <tt>attachmentType</tt>.
     *
     * @param attachmentType value to be assigned to property attachmentType
     */
    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    /**
     * Getter method for property <tt>attachmentKey</tt>.
     *
     * @return property value of attachmentKey
     */
    public String getAttachmentKey() {
        return attachmentKey;
    }

    /**
     * Setter method for property <tt>attachmentKey</tt>.
     *
     * @param attachmentKey value to be assigned to property attachmentKey
     */
    public void setAttachmentKey(String attachmentKey) {
        this.attachmentKey = attachmentKey;
    }

    /**
     * Getter method for property <tt>attachmentName</tt>.
     *
     * @return property value of attachmentName
     */
    public String getAttachmentName() {
        return attachmentName;
    }

    /**
     * Setter method for property <tt>attachmentName</tt>.
     *
     * @param attachmentName value to be assigned to property attachmentName
     */
    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
