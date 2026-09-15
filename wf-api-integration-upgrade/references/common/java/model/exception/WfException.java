package {basePackage}.wf.model.exception;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * WorldFirst API 业务异常。
 * <p>
 * 所有 WF API 调用过程中的异常统一封装为 {@code WfException}，携带
 * {@link WfErrorCode} 错误码和错误描述信息。通过 {@link #isRetryable()}
 * 可快速判断该异常是否可重试。
 */
public class WfException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** 错误码 */
    private final WfErrorCode errorCode;

    /**
     * 构造函数。
     *
     * @param errorCode 错误码枚举
     * @param message   错误描述
     */
    public WfException(WfErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * 构造函数（带原始异常）。
     *
     * @param errorCode 错误码枚举
     * @param message   错误描述
     * @param cause     原始异常
     */
    public WfException(WfErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Getter method for property <tt>errorCode</tt>.
     *
     * @return 错误码枚举
     */
    public WfErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * 是否可重试。
     * <p>
     * 委托给 {@link WfErrorCode#isRetryable()} 判断。
     *
     * @return 可重试返回 true，不可重试或 errorCode 为 null 时返回 false
     */
    public boolean isRetryable() {
        return errorCode != null && errorCode.isRetryable();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
