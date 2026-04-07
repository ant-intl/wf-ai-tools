package response

import "{moduleName}/wf/model/domain"

// CreateTransferResponse represents the response from createTransfer API
type CreateTransferResponse struct {
	Result             Result                    `json:"result"`
	TransferRequestID  string                    `json:"transferRequestId"`
	TransferID         string                    `json:"transferId"`
	BusinessSceneCode  string                    `json:"businessSceneCode"`
	TransferFromDetail *domain.TransferFromDetail `json:"transferFromDetail"`
	TransferToDetail   *domain.TransferToDetail   `json:"transferToDetail"`
}

// IsProcessing returns true when the transfer is still in progress
func (r *CreateTransferResponse) IsProcessing() bool {
	return r.Result.ResultStatus == "S" && r.Result.ResultCode == "PROCESSING"
}

// IsSuccess returns true when the transfer is completed successfully
func (r *CreateTransferResponse) IsSuccess() bool {
	return r.Result.ResultStatus == "S" && r.Result.ResultCode == "SUCCESS"
}

