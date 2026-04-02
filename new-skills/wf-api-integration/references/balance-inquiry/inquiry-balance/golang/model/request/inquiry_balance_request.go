package request

import "fmt"

// InquiryBalanceRequest represents the request for inquiryBalance API
type InquiryBalanceRequest struct {
	CurrencyList    []string `json:"currencyList,omitempty"`
	BalanceTypes    []string `json:"balanceTypes,omitempty"`
	BudgetAccountID string   `json:"budgetAccountId,omitempty"`
}

// Validate validates the request parameters
func (r *InquiryBalanceRequest) Validate() error {
	if contains(r.BalanceTypes, "BUDGET_BALANCE") && r.BudgetAccountID == "" {
		return fmt.Errorf("budgetAccountId is required when balanceTypes includes BUDGET_BALANCE")
	}
	return nil
}

func contains(slice []string, item string) bool {
	for _, s := range slice {
		if s == item {
			return true
		}
	}
	return false
}
