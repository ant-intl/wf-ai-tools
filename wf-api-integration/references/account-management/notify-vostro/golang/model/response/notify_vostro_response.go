package response

// Result represents the API call result
type Result struct {
	ResultCode    string `json:"resultCode"`
	ResultMessage string `json:"resultMessage,omitempty"`
	ResultStatus  string `json:"resultStatus"`
}

// NotifyVostroResponse represents the response that the integrator returns to WF
// after receiving a notifyVostro callback notification.
// If no successful response is returned, WF will retry up to 7 times.
type NotifyVostroResponse struct {
	Result Result `json:"result"`
}

// NewSuccessResponse creates a successful NotifyVostroResponse.
func NewSuccessResponse() *NotifyVostroResponse {
	return &NotifyVostroResponse{
		Result: Result{
			ResultCode:    "SUCCESS",
			ResultStatus:  "S",
			ResultMessage: "Success",
		},
	}
}

// NewFailResponse creates a failed NotifyVostroResponse.
func NewFailResponse(resultCode, resultMessage string) *NotifyVostroResponse {
	return &NotifyVostroResponse{
		Result: Result{
			ResultCode:    resultCode,
			ResultStatus:  "U",
			ResultMessage: resultMessage,
		},
	}
}
