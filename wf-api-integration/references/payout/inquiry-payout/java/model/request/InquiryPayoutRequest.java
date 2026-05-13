package {basePackage}.wf.model.request;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst inquiryPayout 请求对象。
 *
 * <p>{@code transferId} 与 {@code transferRequestId} 不能同时为空，二选一传入即可。
 *
 */
public class InquiryPayoutRequest {

    /** WF 生成的代发单 ID（与 transferRequestId 二选一） */
    private String transferId;

    /** 集成商定义的代发请求 ID（与 transferId 二选一），最大 64 字符 */
    private String transferRequestId;

    public String getTransferId() { return transferId; }
    public void setTransferId(String transferId) { this.transferId = transferId; }
    public String getTransferRequestId() { return transferRequestId; }
    public void setTransferRequestId(String transferRequestId) { this.transferRequestId = transferRequestId; }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
