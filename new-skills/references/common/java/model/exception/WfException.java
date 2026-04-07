/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf.model.exception;

/**
 * WorldFirst API 业务异常
 *
 * @author Qoder
 * @version WfException.java, v 0.1 2026-04-01
 */
public class WfException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** 错误码 */
    private final WfErrorCode errorCode;

    /**
     * 构造函数
     *
     * @param errorCode 错误码枚举
     * @param message   错误描述
     */
    public WfException(WfErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * 构造函数（带原始异常）
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
     * @return property value of errorCode
     */
    public WfErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * 是否可重试
     *
     * @return 可重试返回 true
     */
    public boolean isRetryable() {
        return errorCode != null && errorCode.isRetryable();
    }

    @Override
    public String toString() {
        return "WfException{errorCode=" + errorCode + ", message=" + getMessage() + '}';
    }
}

