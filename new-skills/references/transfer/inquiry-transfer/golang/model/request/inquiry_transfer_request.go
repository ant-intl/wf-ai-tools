package request

import (
	"fmt"
)

// InquiryTransferRequest represents the request for inquiryTransfer API.
// Used to query the fund transfer result.
type InquiryTransferRequest struct {
	TransferRequestID string `json:"transferRequestId"`
}

// Validate validates the request parameters
func (r *InquiryTransferRequest) Validate() error {
	if r.TransferRequestID == "" {
		return fmt.Errorf("transferRequestId is required")
	}
	if len(r.TransferRequestID) > 64 {
		return fmt.Errorf("transferRequestId max length is 64")
	}
	return nil
}
