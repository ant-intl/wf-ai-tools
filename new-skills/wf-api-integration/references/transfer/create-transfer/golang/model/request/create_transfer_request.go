package request

import (
	"fmt"

	"{moduleName}/wf/model/domain"
)

// CreateTransferRequest represents the request for createTransfer API.
// Used for account-to-account fund transfer within WorldFirst.
type CreateTransferRequest struct {
	TransferRequestID  string                    `json:"transferRequestId"`
	BusinessSceneCode  string                    `json:"businessSceneCode,omitempty"`
	TransferFromDetail *domain.TransferFromDetail `json:"transferFromDetail"`
	TransferToDetail   *domain.TransferToDetail   `json:"transferToDetail"`
}

// Validate validates the request parameters
func (r *CreateTransferRequest) Validate() error {
	if r.TransferRequestID == "" {
		return fmt.Errorf("transferRequestId is required")
	}
	if len(r.TransferRequestID) > 64 {
		return fmt.Errorf("transferRequestId max length is 64")
	}
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

