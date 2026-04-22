package request

import "{moduleName}/wf/model/domain"

// NotifyPayoutRequest represents the callback request from WF notifyPayout API.
// After a transfer is completed, WF sends this notification with the transfer result.
// TransferRequestId is the idempotent field for deduplication.
type NotifyPayoutRequest struct {
	// TransferResult represents the transfer result.
	TransferResult *domain.TransferResult `json:"transferResult"`

	// TransferRequestId is the idempotent request ID defined by the integrator, max 64 chars.
	TransferRequestId string `json:"transferRequestId"`

	// TransferId is the unique transfer ID defined by WF, max 64 chars.
	TransferId string `json:"transferId"`

	// TransferFinishTime is the transfer completion time in ISO 8601 format.
	TransferFinishTime string `json:"transferFinishTime"`

	// ChargeMode indicates the fee charge mode.
	// Values: INNER_DEDUCT (deduct from transfer amount), OUTER_DEDUCT (charge separately).
	ChargeMode string `json:"chargeMode,omitempty"`

	// TransferFromDetail contains the payer's transfer details.
	TransferFromDetail *domain.TransferFromDetail `json:"transferFromDetail"`

	// TransferToDetail contains the payee's transfer details.
	TransferToDetail *domain.TransferToDetail `json:"transferToDetail"`
}

// Validate validates the notify payout request.
// For callback requests, basic validation ensures required fields are present.
func (r *NotifyPayoutRequest) Validate() error {
	// Callback requests are validated by the handler, not the request itself.
	return nil
}
