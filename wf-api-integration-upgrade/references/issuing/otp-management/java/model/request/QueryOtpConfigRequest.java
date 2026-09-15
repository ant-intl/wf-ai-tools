package {basePackage}.wf.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst query_otp_config 请求对象。
 *
 * <p>用于查询账户下卡片的 3DS OTP 通知是否通过 API 投递。本接口作用于账户维度，
 * 无需任何请求参数。
 *
 * <p>本对象无业务字段，序列化后为空对象 {@code {}}；保留用于后续过滤条件扩展。
 */
public class QueryOtpConfigRequest {

    public QueryOtpConfigRequest() {
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
