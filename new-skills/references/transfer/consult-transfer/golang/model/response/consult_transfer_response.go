package response

import "{moduleName}/wf/model/domain"

// ConsultTransferResponse represents the response from consultTransfer API.
// Contains calculated amounts, fees, and exchange rate information.
type ConsultTransferResponse struct {
	Result             Result                    `json:"result"`
	TransferFromDetail *domain.TransferFromDetail `json:"transferFromDetail"`
	TransferToDetail   *domain.TransferToDetail   `json:"transferToDetail"`
}

// IsSuccess returns true when the consult transfer call is successful
func (r *ConsultTransferResponse) IsSuccess() bool {
	return r.Result.ResultStatus == "S" && r.Result.ResultCode == "SUCCESS"
}
