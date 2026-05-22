package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst inquiryTransfer 请求对象（查询转账结果）
 *
 * <p>集成商可调用此接口查询转账结果。
 * 若调用 createTransfer 接口后 2 小时没有接收到万里汇通知，
 * 且调用 inquiryTransfer 接口后 transferResult.resultCode 返回 UNKNOWN，
 * 联系万里汇技术支持。
 *
 */
public class InquiryTransferRequest {

    /** 集成商定义的唯一转账识别 ID（幂等键），最大 64 字符 */
    private String transferRequestId;

    /**
     * Getter method for property <tt>transferRequestId</tt>.
     *
     * @return property value of transferRequestId
     */
    public String getTransferRequestId() {
        return transferRequestId;
    }

    /**
     * Setter method for property <tt>transferRequestId</tt>.
     *
     * @param transferRequestId value to be assigned to property transferRequestId
     */
    public void setTransferRequestId(String transferRequestId) {
        this.transferRequestId = transferRequestId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
