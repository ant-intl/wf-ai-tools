package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst update_otp_config 请求对象。
 *
 * <p>用于开启或关闭通过 API 接收账户下卡片的 3DS OTP 通知。配置作用于账户维度。
 */
public class UpdateOtpConfigRequest {

    /**
     * 是否通过 API 接收 3DS OTP 通知（OtpApiNotifyPreference 枚举）。
     * <p>有效值：ON（通过 API 接收 OTP 通知）、OFF（不通过 API 接收 OTP 通知）。
     */
    private String otpApiNotifyPreference;

    public UpdateOtpConfigRequest() {
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
