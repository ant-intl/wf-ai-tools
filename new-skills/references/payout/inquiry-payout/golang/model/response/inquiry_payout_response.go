package response

import "{moduleName}/wf/model/domain"

// InquiryPayoutResponse represents the response from inquiryPayout API
type InquiryPayoutResponse struct {
	Result             Result                    `json:"result"`
	TransferResult     *domain.TransferResult    `json:"transferResult"`
	TransferRequestID  string                    `json:"transferRequestId"`
	TransferID         string                    `json:"transferId"`
	TransferFinishTime string                    `json:"transferFinishTime"`
	ChargeMode         string                    `json:"chargeMode"`
	TransferFromDetail *domain.TransferFromDetail `json:"transferFromDetail"`
	TransferToDetail   *domain.TransferToDetail   `json:"transferToDetail"`
}
