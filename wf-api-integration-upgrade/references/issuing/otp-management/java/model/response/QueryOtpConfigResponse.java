package {basePackage}.wf.model.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_otp_config 响应对象。
 *
 * <p>返回账户维度的 3DS OTP 通知投递配置。
 */
public class QueryOtpConfigResponse {

    /** 接口调用结果（resultStatus S/F/U、resultCode、resultMessage） */
    private Result result;

    /**
     * 3DS OTP 通知是否通过 API 投递（OtpApiNotifyPreference 枚举）：
     * ON（通过 API 接收）、OFF（不通过 API 接收）。
     */
    private String otpApiNotifyPreference;

    public QueryOtpConfigResponse() {
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

    /**
     * Getter method for property <tt>otpApiNotifyPreference</tt>.
     *
     * @return property value of otpApiNotifyPreference
     */
    public String getOtpApiNotifyPreference() {
        return otpApiNotifyPreference;
    }

    /**
     * Setter method for property <tt>otpApiNotifyPreference</tt>.
     *
     * @param otpApiNotifyPreference value to be assigned to property otpApiNotifyPreference
     */
    public void setOtpApiNotifyPreference(String otpApiNotifyPreference) {
        this.otpApiNotifyPreference = otpApiNotifyPreference;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
