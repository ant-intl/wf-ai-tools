package {basePackage}.wf.model.response;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst notifyTradeOrder 回调响应对象。
 *
 * <p>集成商收到 WF 回调后，需返回包含 result 的响应体。
 * result.resultStatus = S 且 result.resultCode = SUCCESS 表示处理成功，WF 不再重试。
 *
 */
public class NotifyTradeOrderResponse {

    /** 接口调用结果 */
    private Result result;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
