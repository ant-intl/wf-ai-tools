package request

// NotifyBindBeneficiaryRequest represents the callback request from WF notifyBindBeneficiary API.
// After a beneficiary is bound, WF sends this notification with the binding result.
// BindBeneficiaryRequestId is the idempotent field for deduplication.
type NotifyBindBeneficiaryRequest struct {
	// BindBeneficiaryRequestId is the idempotent request ID defined by the integrator, max 64 chars.
	BindBeneficiaryRequestId string `json:"bindBeneficiaryRequestId"`

	// Result represents the beneficiary binding result.
	Result *Result `json:"result"`

	// Beneficiary contains the beneficiary information (only present when result.resultStatus=S).
	Beneficiary *NotifyBeneficiary `json:"beneficiary"`
}

// Result represents the API call result.
type Result struct {
	ResultCode    string `json:"resultCode"`
	ResultMessage string `json:"resultMessage,omitempty"`
	ResultStatus  string `json:"resultStatus"`
}

// NotifyBeneficiary represents the beneficiary information in the notifyBindBeneficiary callback.
type NotifyBeneficiary struct {
	// BeneficiaryToken is the Base64-encoded encrypted string containing beneficiary bank account info.
	BeneficiaryToken string `json:"beneficiaryToken"`

	// BindBeneficiaryRequestId is the idempotent request ID defined by the integrator.
	BindBeneficiaryRequestId string `json:"bindBeneficiaryRequestId"`

	// BeneficiaryBankAccount contains the beneficiary's bank account details.
	// Fields are determined by the card template returned by inquiryBeneficiaryTemplate.
	BeneficiaryBankAccount interface{} `json:"beneficiaryBankAccount,omitempty"`

	// CountryCode is the ISO-3166 2-letter country code where the bank is located.
	CountryCode string `json:"countryCode,omitempty"`

	// Currency is the ISO-4217 3-letter currency code.
	// Supported: USD, EUR, GBP, NZD, CAD, AUD, JPY, SGD, HKD, CNH, CNY.
	Currency string `json:"currency,omitempty"`

	// BeneficiaryType defines the type of beneficiary's account.
	// Valid values:
	//   - THIRD_PARTY_PERSONAL_BANK_ACCOUNT
	//   - THIRD_PARTY_COMPANY_BANK_ACCOUNT
	//   - PERSONAL_BANK_ACCOUNT
	//   - COMPANY_BANK_ACCOUNT
	//   - RELATED_MERCHANT_COMPANY_BANK_ACCOUNT
	//   - RELATED_MERCHANT_ALIPAY_COMPANY_ACCOUNT
	BeneficiaryType string `json:"beneficiaryType,omitempty"`

	// BeneficiaryNick is the user-defined nickname for the beneficiary.
	BeneficiaryNick string `json:"beneficiaryNick,omitempty"`

	// Status indicates whether the beneficiary is available.
	// Valid values: SUCCESS, FAIL.
	Status string `json:"status"`

	// ReferenceBeneficiaryId is the unique beneficiary ID defined by the integrator, max 64 chars.
	ReferenceBeneficiaryId string `json:"referenceBeneficiaryId,omitempty"`
}

// Validate validates the notify bind beneficiary request.
// For callback requests, basic validation ensures required fields are present.
func (r *NotifyBindBeneficiaryRequest) Validate() error {
	// Callback requests are validated by the handler, not the request itself.
	return nil
}
