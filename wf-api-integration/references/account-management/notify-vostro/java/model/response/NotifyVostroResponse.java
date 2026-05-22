package {basePackage}.wf.model.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst notifyVostro 回调响应对象。
 *
 * <p>集成商在收到 notifyVostro 通知后，需返回本响应以确认收到通知。
 * 若不返回响应，万里汇将按重试策略重新发送通知（最多 7 次）。
 */
public class NotifyVostroResponse {

    /** 接口调用结果 */
    private Result result;

    public NotifyVostroResponse() {
    }

    /**
     * 构造成功响应。
     *
     * @return 成功的 NotifyVostroResponse
     */
    public static NotifyVostroResponse success() {
        NotifyVostroResponse response = new NotifyVostroResponse();
        Result result = new Result();
        result.setResultCode("SUCCESS");
        result.setResultStatus("S");
        result.setResultMessage("Success");
        response.setResult(result);
        return response;
    }

    /**
     * 构造失败响应。
     *
     * @param resultCode    结果码
     * @param resultMessage 结果描述
     * @return 失败的 NotifyVostroResponse
     */
    public static NotifyVostroResponse fail(String resultCode, String resultMessage) {
        NotifyVostroResponse response = new NotifyVostroResponse();
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setResultStatus("U");
        result.setResultMessage(resultMessage);
        response.setResult(result);
        return response;
    }

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
