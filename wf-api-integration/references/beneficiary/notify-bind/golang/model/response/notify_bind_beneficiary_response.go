package response

// Result represents the API call result.
type Result struct {
	ResultCode    string `json:"resultCode"`
	ResultMessage string `json:"resultMessage,omitempty"`
	ResultStatus  string `json:"resultStatus"`
}

// NotifyBindBeneficiaryResponse represents the response that the integrator returns to WF
// after receiving a notifyBindBeneficiary callback notification.
// If no successful response is returned, WF will retry up to 7 times.
// Retry intervals: 2min, 10min, 10min, 1h, 2h, 6h, 15h.
type NotifyBindBeneficiaryResponse struct {
	Result Result `json:"result"`
}

// NewSuccessResponse creates a successful NotifyBindBeneficiaryResponse.
func NewSuccessResponse() *NotifyBindBeneficiaryResponse {
	return &NotifyBindBeneficiaryResponse{
		Result: Result{
			ResultCode:    "SUCCESS",
			ResultStatus:  "S",
			ResultMessage: "success.",
		},
	}
}

// NewFailResponse creates a failed NotifyBindBeneficiaryResponse.
func NewFailResponse(resultCode, resultMessage string) *NotifyBindBeneficiaryResponse {
	return &NotifyBindBeneficiaryResponse{
		Result: Result{
			ResultCode:    resultCode,
			ResultStatus:  "U",
			ResultMessage: resultMessage,
		},
	}
}
