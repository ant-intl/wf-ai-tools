package {basePackage}.wf.model.exception;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
