package {basePackage}.wf.model.domain;


/** RfiFile 领域结构。可选字段保持 null；完整约束见对应 GUIDE。 */
public class RfiFile {

    /** Required. Unique file identifier. Use the id returned by Upload a File. */
    private String fileId;

    /** Required. Original file name including extension. Use the fileName returned by Upload a File. */
    private String fileName;

    public String getFileId() { return fileId; }

    public void setFileId(String fileId) { this.fileId = fileId; }

    public String getFileName() { return fileName; }

    public void setFileName(String fileName) { this.fileName = fileName; }

    /** 不将敏感字段写入日志。 */
    @Override
    public String toString() { return "RfiFile[REDACTED]"; }
}
