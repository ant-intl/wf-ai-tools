package {basePackage}.wf.model.domain;

import lombok.Data;

/**
 * Card template field definition returned by inquiryBeneficiaryTemplate API.
 * Indicates which fields are required when binding a beneficiary.
 */
@Data
public class CardTemplateField {

    /**
     * Field name (e.g., bankAccountName, bankBIC, bankAccountNo)
     */
    private String fieldName;

    /**
     * Field description
     */
    private String fieldDescription;

    /**
     * Whether the field is required: "Y" or "N"
     */
    private String required;

    /**
     * Field restriction rules
     */
    private FieldRestriction restriction;

    /**
     * Nested restriction object
     */
    @Data
    public static class FieldRestriction {
        /**
         * Restriction message for validation failure
         */
        private String restrictionMsg;

        /**
         * Regex pattern for validation
         */
        private String restrictionRegex;

        /**
         * Restriction type (e.g., PATTERN_RESTRICTION)
         */
        private String restrictionType;
    }
}
