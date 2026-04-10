package request

// InquiryBeneficiaryTemplateRequest queries the card template for a specific
// country/currency/beneficiary type combination.
// At least one of CountryCode, Currency, or BeneficiaryType should be provided.
type InquiryBeneficiaryTemplateRequest struct {
	// CountryCode is the ISO-3166 2-letter country code (国家代码，条件必填)
	CountryCode string `json:"countryCode,omitempty"`

	// Currency is the ISO-4217 3-letter currency code (币种代码，条件必填)
	Currency string `json:"currency,omitempty"`

	// BeneficiaryType is the beneficiary account type (账户类型，条件必填)
	// e.g., THIRD_PARTY_PERSONAL_BANK_ACCOUNT, THIRD_PARTY_COMPANY_BANK_ACCOUNT, etc.
	BeneficiaryType string `json:"beneficiaryType,omitempty"`
}
