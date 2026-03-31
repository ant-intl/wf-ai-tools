package {basePackage}.wf.model.exception;

import lombok.Getter;

/**
 * WorldFirst API exception.
 */
@Getter
public class WfException extends RuntimeException {

    private final WfErrorCode errorCode;

    public WfException(WfErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public WfException(WfErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
