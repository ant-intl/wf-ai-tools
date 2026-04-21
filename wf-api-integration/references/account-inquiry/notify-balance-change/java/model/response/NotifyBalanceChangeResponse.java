/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst notifyBalanceChange 回调响应对象。
 *
 * <p>集成商在收到 notifyBalanceChange 通知后，需返回本响应以确认收到通知。
 * 若不返回响应，万里汇将按重试策略重新发送通知（最多 7 次）。
 *
 * @author Qoder
 * @version NotifyBalanceChangeResponse.java, v 0.1 2026-04-21
 */
public class NotifyBalanceChangeResponse {

    /** 接口调用结果 */
    private Result result;

    public NotifyBalanceChangeResponse() {
    }

    /**
     * 构造成功响应。
     *
     * @return 成功的 NotifyBalanceChangeResponse
     */
    public static NotifyBalanceChangeResponse success() {
        NotifyBalanceChangeResponse response = new NotifyBalanceChangeResponse();
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
     * @return 失败的 NotifyBalanceChangeResponse
     */
    public static NotifyBalanceChangeResponse fail(String resultCode, String resultMessage) {
        NotifyBalanceChangeResponse response = new NotifyBalanceChangeResponse();
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
