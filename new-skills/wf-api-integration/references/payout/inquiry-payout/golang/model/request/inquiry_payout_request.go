package request

import "fmt"

// InquiryPayoutRequest represents the request for inquiryPayout API.
// Either transferId or transferRequestId must be provided.
type InquiryPayoutRequest struct {
	TransferID        string `json:"transferId,omitempty"`
	TransferRequestID string `json:"transferRequestId,omitempty"`
}

// Validate validates the request parameters
func (r *InquiryPayoutRequest) Validate() error {
	if r.TransferID == "" && r.TransferRequestID == "" {
		return fmt.Errorf("either transferId or transferRequestId must be provided")
	}
	return nil
}
