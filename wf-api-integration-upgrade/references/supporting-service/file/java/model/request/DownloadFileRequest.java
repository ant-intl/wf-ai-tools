package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst download_a_file 请求对象。
 *
 * <p>根据文件 ID 获取已上传文件的临时下载链接。
 */
public class DownloadFileRequest {

    /**
     * 唯一文件标识符。
     * <p>由 Upload a File 接口返回。
     */
    private String id;

    public DownloadFileRequest() {
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
