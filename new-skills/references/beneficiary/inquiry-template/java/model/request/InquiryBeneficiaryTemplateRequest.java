package {basePackage}.wf.model.request;

import lombok.Data;

/**
 * Request for inquiryBeneficiaryTemplate API.
 * Queries the card template for a specific country/currency/beneficiary type combination.
 */
@Data
public class InquiryBeneficiaryTemplateRequest {

    /**
     * ISO-3166 2-letter country code (conditional)
     */
    private String countryCode;

    /**
     * ISO-4217 3-letter currency code (conditional)
     */
    private String currency;

    /**
     * Beneficiary account type (conditional)
     * e.g., THIRD_PARTY_PERSONAL_BANK_ACCOUNT, THIRD_PARTY_COMPANY_BANK_ACCOUNT, etc.
     */
    private String beneficiaryType;
}
