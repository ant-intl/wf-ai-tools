package response

// Result represents the API call result.
type Result struct {
	ResultCode    string `json:"resultCode"`
	ResultMessage string `json:"resultMessage,omitempty"`
	ResultStatus  string `json:"resultStatus"`
}

// NotifyBalanceChangeResponse represents the response that the integrator returns to WF
// after receiving a notifyBalanceChange callback notification.
// If no successful response is returned, WF will retry up to 7 times.
type NotifyBalanceChangeResponse struct {
	Result Result `json:"result"`
}

// NewSuccessResponse creates a successful NotifyBalanceChangeResponse.
func NewSuccessResponse() *NotifyBalanceChangeResponse {
	return &NotifyBalanceChangeResponse{
		Result: Result{
			ResultCode:    "SUCCESS",
			ResultStatus:  "S",
			ResultMessage: "Success",
		},
	}
}

// NewFailResponse creates a failed NotifyBalanceChangeResponse.
func NewFailResponse(resultCode, resultMessage string) *NotifyBalanceChangeResponse {
	return &NotifyBalanceChangeResponse{
		Result: Result{
			ResultCode:    resultCode,
			ResultStatus:  "U",
			ResultMessage: resultMessage,
		},
	}
}
