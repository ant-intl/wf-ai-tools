package request

// NotifyVostroRequest represents the callback request from WF notifyVostro API.
// When the integrator's WF account receives a deposit, WF sends this notification.
// FundingID is the idempotency key — integrators should use it for deduplication.
type NotifyVostroRequest struct {
	// FundingID uniquely identifies a funding request, max 128 chars.
	// Idempotency key for deduplication.
	FundingID string `json:"fundingId"`

	// BalanceResult indicates whether the funding was successful.
	BalanceResult *BalanceResult `json:"balanceResult"`

	// PayerBankAccount contains payer bank account information.
	PayerBankAccount *PayerBankAccount `json:"payerBankAccount"`

	// BeneficiaryAccount contains beneficiary WF VA account information.
	BeneficiaryAccount *VostroBeneficiaryAccount `json:"beneficiaryAccount"`

	// BalanceChangeAmount is the amount of balance change.
	BalanceChangeAmount *Amount `json:"balanceChangeAmount"`

	// BalanceChangeTime is the time of balance change in ISO 8601 format.
	BalanceChangeTime string `json:"balanceChangeTime,omitempty"`

	// RemitInfo is additional information for the funding request, max 530 chars.
	RemitInfo string `json:"remitInfo,omitempty"`
}

// BalanceResult represents the result of the funding operation.
type BalanceResult struct {
	ResultCode    string `json:"resultCode"`
	ResultStatus  string `json:"resultStatus"`
	ResultMessage string `json:"resultMessage"`
}

// PayerBankAccount represents payer bank account information.
type PayerBankAccount struct {
	PayerBankAccountNo string `json:"payerBankAccountNo"`
	PayerBankName      string `json:"payerBankName"`
}

// VostroBeneficiaryAccount represents beneficiary WF VA account information.
type VostroBeneficiaryAccount struct {
	BeneficiaryBankAccountNo string `json:"beneficiaryBankAccountNo"`
}

// Amount represents a monetary amount with currency.
// Value is in minor units (e.g. cents): 10000 = 100.00 USD
type Amount struct {
	Currency string `json:"currency"`
	Value    int64  `json:"value"`
}

// Validate validates the notify vostro request.
// For callback requests, basic validation ensures required fields are present.
func (r *NotifyVostroRequest) Validate() error {
	// Callback requests are validated by the handler, not the request itself.
	// This method is provided for consistency with other request types.
	return nil
}
