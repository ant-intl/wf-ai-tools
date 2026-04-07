package request

import "fmt"

const (
	maxPageNumber = 50
	fixedPageSize = 10
)

// InquiryStatementRequest represents the request for inquiryStatementList API.
// pageSize is always fixed to 10 regardless of what caller sets.
type InquiryStatementRequest struct {
	StartTime           string   `json:"startTime"`
	EndTime             string   `json:"endTime"`
	PageSize            int      `json:"pageSize"`
	PageNumber          int      `json:"pageNumber"`
	TransactionTypeList []string `json:"transactionTypeList,omitempty"`
	CurrencyList        []string `json:"currencyList,omitempty"`
	BalanceTypes        []string `json:"balanceTypes,omitempty"`
	BudgetAccountIds    []string `json:"budgetAccountIds,omitempty"`
	FuzzyName           string   `json:"fuzzyName,omitempty"`
}

// Validate validates the request parameters
func (r *InquiryStatementRequest) Validate() error {
	if r.StartTime == "" {
		return fmt.Errorf("startTime is required")
	}
	if r.EndTime == "" {
		return fmt.Errorf("endTime is required")
	}
	if r.PageNumber < 1 || r.PageNumber > maxPageNumber {
		return fmt.Errorf("pageNumber must be between 1 and %d", maxPageNumber)
	}
	// Always override pageSize to fixed value
	r.PageSize = fixedPageSize
	return nil
}
