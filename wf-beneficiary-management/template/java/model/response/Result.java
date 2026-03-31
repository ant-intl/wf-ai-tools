package {basePackage}.wf.model.response;

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
}
