package {basePackage}.wf.model.exception;

import lombok.Getter;

/**
 * WorldFirst API error codes.
 */
@Getter
public enum WfErrorCode {

    // System codes
    SUCCESS("SUCCESS", "Success"),
    PROCESS_FAIL("PROCESS_FAIL", "Process failed"),
    PARAM_ILLEGAL("PARAM_ILLEGAL", "Illegal parameters"),
    UNKNOWN_EXCEPTION("UNKNOWN_EXCEPTION", "Unknown exception, retryable"),
    REQUEST_TRAFFIC_EXCEED_LIMIT("REQUEST_TRAFFIC_EXCEED_LIMIT", "Request traffic exceed limit, retryable"),
    OAUTH_FAIL("OAUTH_FAIL", "OAuth failed"),
    INVALID_API("INVALID_API", "Invalid API"),
    INVALID_CLIENT("INVALID_CLIENT", "Invalid client"),
    INVALID_SIGNATURE("INVALID_SIGNATURE", "Invalid signature"),
    METHOD_NOT_SUPPORTED("METHOD_NOT_SUPPORTED", "Method not supported"),

    // Business codes - bindBeneficiary
    CARD_TEMPLATE_NOT_EXIST("CARD_TEMPLATE_NOT_EXIST", "Card template not exist"),
    BENEFICIARY_ALREADY_EXISTED("BENEFICIARY_ALREADY_EXISTED", "Beneficiary already existed"),
    REPEAT_BIND_BENEFICIARY_REQUEST("REPEAT_BIND_BENEFICIARY_REQUEST", "Duplicate bind beneficiary request"),
    EXCEED_MAX_COUNT_LIMIT("EXCEED_MAX_COUNT_LIMIT", "Exceeded max beneficiary count limit"),
    USER_NO_PERMISSION("USER_NO_PERMISSION", "User has no permission"),
    REFERENCE_BENEFICIARY_ID_EXIST("REFERENCE_BENEFICIARY_ID_EXIST", "Reference beneficiary ID already exists"),
    RISK_REJECT("RISK_REJECT", "Risk rejection"),

    // Business codes - removeBeneficiary / editBeneficiary
    BENEFICIARY_NOT_EXIST("BENEFICIARY_NOT_EXIST", "Beneficiary not found"),
    REPEAT_REQ_INCONSISTENT("REPEAT_REQ_INCONSISTENT", "Inconsistent repeat request"),

    // Generic
    UN_SUPPORT_BUSINESS("UN_SUPPORT_BUSINESS", "Unsupported business"),
    USER_NOT_EXIST("USER_NOT_EXIST", "User not found"),

    // Response parsing
    INVALID_RESPONSE_FORMAT("INVALID_RESPONSE_FORMAT", "Invalid response format");

    private final String code;
    private final String description;

    WfErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Check if this error code is retryable.
     */
    public boolean isRetryable() {
        return this == UNKNOWN_EXCEPTION
                || this == REQUEST_TRAFFIC_EXCEED_LIMIT
                || this == REPEAT_BIND_BENEFICIARY_REQUEST;
    }

    /**
     * Get enum from code string.
     */
    public static WfErrorCode fromCode(String code) {
        if (code == null) {
            return UNKNOWN_EXCEPTION;
        }
        for (WfErrorCode value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return UNKNOWN_EXCEPTION;
    }
}
