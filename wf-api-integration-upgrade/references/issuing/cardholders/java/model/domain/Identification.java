package {basePackage}.wf.model.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst 持卡人身份证明材料对象。
 *
 * <p>用于 create_a_cardholder 请求，提交 KYC 审核所需的身份证明文件列表。
 */
public class Identification {

    /**
     * 已上传的证明文件列表。
     * <p>EMPLOYEE 与 SHAREHOLDER 必须包含身份证明文件；关系证明可选但建议提供。
     * <p>万里汇按列表顺序与业务规则识别各文件。
     */
    private List<IdentificationFile> fileList;

    public Identification() {
    }

    /**
     * Getter method for property <tt>fileList</tt>.
     *
     * @return property value of fileList
     */
    public List<IdentificationFile> getFileList() {
        return fileList;
    }

    /**
     * Setter method for property <tt>fileList</tt>.
     *
     * @param fileList value to be assigned to property fileList
     */
    public void setFileList(List<IdentificationFile> fileList) {
        this.fileList = fileList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
