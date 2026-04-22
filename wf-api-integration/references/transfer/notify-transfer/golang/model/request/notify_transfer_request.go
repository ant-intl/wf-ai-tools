package request

import "{moduleName}/wf/model/domain"

// NotifyTransferRequest represents the callback request from WF notifyTransfer API.
// After a transfer is completed, WF sends this notification with the transfer result.
// TransferRequestId is the idempotent field for deduplication.
type NotifyTransferRequest struct {
	// TransferResult represents the transfer result.
	TransferResult *Result `json:"transferResult"`

	// TransferRequestId is the unique transfer request ID defined by the integrator, max 64 chars.
	TransferRequestId string `json:"transferRequestId"`

	// TransferId is the unique transfer ID assigned by WorldFirst, max 64 chars.
	TransferId string `json:"transferId"`

	// TransferFinishTime is the transfer completion time in ISO 8601 format.
	TransferFinishTime string `json:"transferFinishTime,omitempty"`

	// TransferFromDetail contains the payer's transfer details.
	TransferFromDetail *domain.TransferFromDetail `json:"transferFromDetail,omitempty"`

	// TransferToDetail contains the payee's transfer details.
	TransferToDetail *domain.TransferToDetail `json:"transferToDetail,omitempty"`

	// TransferOrderAddition contains additional transfer order information.
	TransferOrderAddition *TransferOrderAddition `json:"transferOrderAddition,omitempty"`
}

// Result represents the API call result.
type Result struct {
	ResultCode    string `json:"resultCode"`
	ResultMessage string `json:"resultMessage,omitempty"`
	ResultStatus  string `json:"resultStatus"`
}

// TransferOrderAddition represents additional information for the transfer order.
type TransferOrderAddition struct {
	// ReferenceOrderId is the associated order ID defined by the integrator.
	ReferenceOrderId string `json:"referenceOrderId,omitempty"`
}

// IsTransferSuccess returns true when the transfer result indicates success.
func (r *NotifyTransferRequest) IsTransferSuccess() bool {
	return r.TransferResult != nil &&
		r.TransferResult.ResultStatus == "S" &&
		r.TransferResult.ResultCode == "SUCCESS"
}

// IsTransferFailed returns true when the transfer result indicates failure.
func (r *NotifyTransferRequest) IsTransferFailed() bool {
	return r.TransferResult != nil && r.TransferResult.ResultStatus == "F"
}

// Validate validates the notify transfer request.
// For callback requests, basic validation ensures required fields are present.
func (r *NotifyTransferRequest) Validate() error {
	// Callback requests are validated by the handler, not the request itself.
	return nil
}
