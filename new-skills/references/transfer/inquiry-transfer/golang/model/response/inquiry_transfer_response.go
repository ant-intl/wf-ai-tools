package response

import "{moduleName}/wf/model/domain"

// InquiryTransferResponse represents the response from inquiryTransfer API.
//
// The response contains two result layers:
//   - Result: the API call result
//   - TransferResult: the transfer business result
type InquiryTransferResponse struct {
	Result             Result                    `json:"result"`
	TransferRequestID  string                    `json:"transferRequestId"`
	TransferID         string                    `json:"transferId"`
	BusinessSceneCode  string                    `json:"businessSceneCode"`
	TransferResult     Result                    `json:"transferResult"`
	TransferFinishTime string                    `json:"transferFinishTime,omitempty"`
	TransferFromDetail *domain.TransferFromDetail `json:"transferFromDetail"`
	TransferToDetail   *domain.TransferToDetail   `json:"transferToDetail"`
}

// IsTransferProcessing returns true when the transfer is still in progress
func (r *InquiryTransferResponse) IsTransferProcessing() bool {
	return r.TransferResult.ResultStatus == "S" && r.TransferResult.ResultCode == "PROCESSING"
}

// IsTransferSuccess returns true when the transfer is completed successfully
func (r *InquiryTransferResponse) IsTransferSuccess() bool {
	return r.TransferResult.ResultStatus == "S" && r.TransferResult.ResultCode == "SUCCESS"
}

// IsTransferFailed returns true when the transfer has failed
func (r *InquiryTransferResponse) IsTransferFailed() bool {
	return r.TransferResult.ResultStatus == "F"
}
