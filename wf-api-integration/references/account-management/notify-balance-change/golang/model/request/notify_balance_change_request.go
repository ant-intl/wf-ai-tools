package request

// NotifyBalanceChangeRequest represents the callback request from WF notifyBalanceChange API.
// When a transaction occurs in the integrator's WF balance account, WF sends this notification
// with all balance change details.
// NotifySequence is the cumulative sequence number for ordering and deduplication.
type NotifyBalanceChangeRequest struct {
	// NotifySequence is the cumulative sequence number of notifications from WF.
	NotifySequence int `json:"notifySequence"`

	// BalanceChangeLogs contains the list of balance change records.
	BalanceChangeLogs []BalanceChangeLog `json:"balanceChangeLogs"`
}

// BalanceChangeLog represents a single balance change record.
// TransactionAmount can be positive (inflow) or negative (outflow).
type BalanceChangeLog struct {
	// BalanceChangeTime is the time of balance change in ISO 8601 format.
	BalanceChangeTime string `json:"balanceChangeTime"`

	// AccountNo is the unique account ID assigned by WF, max 32 chars.
	AccountNo string `json:"accountNo"`

	// BalanceType indicates the type of balance.
	// Valid values: NORMAL_BALANCE (default), SAME_NAME_TOP_UP_BALANCE, BUDGET_BALANCE.
	BalanceType string `json:"balanceType,omitempty"`

	// AccountingBizNo is the unique ID for the balance change in WF billing.
	AccountingBizNo string `json:"accountingBizNo"`

	// TransactionAmount is the amount of the balance change.
	// Positive for inflow, negative for outflow.
	TransactionAmount *Amount `json:"transactionAmount"`

	// AccountBalance is the real-time account balance after the transaction.
	AccountBalance *Amount `json:"accountBalance"`

	// TransactionType is the type of balance change.
	// Common values: TRANSFER, TRANSFER_REFUND, WITHDRAWAL, WITHDRAWAL_REFUND,
	// COLLECTION, COLLECTION_REFUND, CONVERSION, CONVERSION_DEAL,
	// CHARGE, CHARGE_REFUND, DEDUCTION, FUND_COLLECTION.
	TransactionType string `json:"transactionType"`

	// TransactionId is the unique transaction ID assigned by WF, max 64 chars.
	// Required when transactionType is TRANSFER/TRANSFER_REFUND/WITHDRAWAL/WITHDRAWAL_REFUND.
	TransactionId string `json:"transactionId,omitempty"`

	// ExtTransactionId is the unique transaction ID assigned by the Partner, max 256 chars.
	ExtTransactionId string `json:"extTransactionId,omitempty"`

	// BeneficiaryName is the beneficiary's name (masked), max 128 chars.
	BeneficiaryName string `json:"beneficiaryName,omitempty"`

	// BeneficiaryAccountNo is the beneficiary's WF account number (masked), max 64 chars.
	BeneficiaryAccountNo string `json:"beneficiaryAccountNo,omitempty"`

	// Remarks is additional remarks for the transaction, max 512 chars.
	Remarks string `json:"remarks,omitempty"`
}

// Amount represents a monetary amount with currency.
// Value is in minor units (e.g. cents): 10000 = 100.00 USD.
// Value can be negative for outflow transactions.
type Amount struct {
	Currency string `json:"currency"`
	Value    int64  `json:"value"`
}

// Validate validates the notify balance change request.
// For callback requests, basic validation ensures required fields are present.
func (r *NotifyBalanceChangeRequest) Validate() error {
	// Callback requests are validated by the handler, not the request itself.
	// This method is provided for consistency with other request types.
	return nil
}
