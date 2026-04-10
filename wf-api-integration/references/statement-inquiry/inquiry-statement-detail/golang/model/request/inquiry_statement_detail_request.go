package request

import "fmt"

// InquiryStatementDetailRequest represents the request for inquiryStatementDetail API.
// accountingBizNo is obtained from the inquiryStatementList API response.
type InquiryStatementDetailRequest struct {
	AccountingBizNo string `json:"accountingBizNo"`
}

// Validate validates the request parameters
func (r *InquiryStatementDetailRequest) Validate() error {
	if r.AccountingBizNo == "" {
		return fmt.Errorf("accountingBizNo is required")
	}
	return nil
}
