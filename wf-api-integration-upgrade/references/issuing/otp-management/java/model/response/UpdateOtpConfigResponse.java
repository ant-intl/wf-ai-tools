package {basePackage}.wf.model.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst update_otp_config 响应对象。
 *
 * <p>响应仅含调用结果，不回显配置值；{@code resultStatus=S} 表示配置已更新，
 * 需再次调用 query_otp_config 确认当前生效配置。
 */
public class UpdateOtpConfigResponse {

    /** 接口调用结果，resultStatus 为 S 表示配置更新成功 */
    private Result result;

    public UpdateOtpConfigResponse() {
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
