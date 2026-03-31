package response

// InquiryBalanceResponse represents the response from inquiryBalance API
type InquiryBalanceResponse struct {
	Result          Result           `json:"result"`
	ResponseID      string           `json:"responseId"`
	AccountBalances []AccountBalance `json:"accountBalances"`
}

// Money represents an amount with currency.
// Value is in minor units (e.g. cents): 999450809995 = 9,994,508.09995 USD
type Money struct {
	Currency string `json:"currency"`
	Value    int64  `json:"value"`
}

// AccountBalance represents a single account balance entry.
// Note: availableBalance / totalBalance / frozenBalance are objects, NOT strings.
type AccountBalance struct {
	AccountNo        string `json:"accountNo"`
	Currency         string `json:"currency"`
	BalanceType      string `json:"balanceType"`
	TotalBalance     Money  `json:"totalBalance"`
	AvailableBalance Money  `json:"availableBalance"`
	FrozenBalance    Money  `json:"frozenBalance"`
	BudgetAccountID  string `json:"budgetAccountId"`
}
