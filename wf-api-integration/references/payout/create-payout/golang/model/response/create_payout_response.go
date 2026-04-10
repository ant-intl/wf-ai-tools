package response

import "{moduleName}/wf/model/domain"

// CreatePayoutResponse represents the response from createPayout API
type CreatePayoutResponse struct {
	Result             Result                    `json:"result"`
	TransferRequestID  string                    `json:"transferRequestId"`
	TransferID         string                    `json:"transferId"`
	ChargeMode         string                    `json:"chargeMode"`
	TransferFromDetail *domain.TransferFromDetail `json:"transferFromDetail"`
	TransferToDetail   *domain.TransferToDetail   `json:"transferToDetail"`
}

// IsProcessing returns true when the transfer is still in progress
func (r *CreatePayoutResponse) IsProcessing() bool {
	return r.Result.ResultStatus == "S" && r.Result.ResultCode == "PROCESSING"
}
