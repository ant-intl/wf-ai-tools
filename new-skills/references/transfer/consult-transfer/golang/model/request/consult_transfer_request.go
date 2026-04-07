package request

import (
	"fmt"

	"{moduleName}/wf/model/domain"
)

// ConsultTransferRequest represents the request for consultTransfer API.
// Used to inquire about transfer information (exchange rate, fees, etc.) before calling createTransfer.
type ConsultTransferRequest struct {
	TransferFromDetail *domain.TransferFromDetail `json:"transferFromDetail"`
	TransferToDetail   *domain.TransferToDetail   `json:"transferToDetail"`
}

// Validate validates the request parameters
func (r *ConsultTransferRequest) Validate() error {
	if r.TransferFromDetail == nil || r.TransferFromDetail.TransferFromAmount == nil ||
		r.TransferFromDetail.TransferFromAmount.Currency == "" {
		return fmt.Errorf("transferFromDetail.transferFromAmount.currency is required")
	}
	if r.TransferToDetail == nil || r.TransferToDetail.TransferToAmount == nil ||
		r.TransferToDetail.TransferToAmount.Currency == "" {
		return fmt.Errorf("transferToDetail.transferToAmount.currency is required")
	}
	// at least one value must be specified
	fromHasValue := r.TransferFromDetail.TransferFromAmount.Value != nil
	toHasValue := r.TransferToDetail.TransferToAmount.Value != nil
	if !fromHasValue && !toHasValue {
		return fmt.Errorf("either transferFromAmount.value or transferToAmount.value must be specified")
	}
	return nil
}
