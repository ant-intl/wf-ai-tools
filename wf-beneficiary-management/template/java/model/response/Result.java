package {basePackage}.wf.model.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import lombok.Data;

/**
 * Common API result structure for all WF API responses.
 */
@Data
public class Result {

    /**
     * Result status: S (Success), F (Failure), U (Unknown/Retry)
     */
    private String resultStatus;

    /**
     * Result code
     */
    private String resultCode;

    /**
     * Result message
     */
    private String resultMessage;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
