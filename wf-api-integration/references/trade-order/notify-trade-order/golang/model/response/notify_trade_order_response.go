package response

// Result represents the API call result.
type Result struct {
	ResultCode    string `json:"resultCode"`
	ResultMessage string `json:"resultMessage,omitempty"`
	ResultStatus  string `json:"resultStatus"`
}

// NotifyTradeOrderResponse represents the outbound response from the integrator
// back to WF after receiving a notifyTradeOrder callback.
//
// The response body must be signed before returning to WF.
// Result.ResultCode should be:
//   - SUCCESS: callback processed successfully
//   - UNKNOWN_EXCEPTION: temporary failure, WF will retry
//   - PROCESS_FAIL: permanent failure, WF will not retry
type NotifyTradeOrderResponse struct {
	Result Result `json:"result"`
}

// NewSuccessResponse creates a successful NotifyTradeOrderResponse.
func NewSuccessResponse() *NotifyTradeOrderResponse {
	return &NotifyTradeOrderResponse{
		Result: Result{
			ResultCode:    "SUCCESS",
			ResultStatus:  "S",
			ResultMessage: "success.",
		},
	}
}

// NewFailResponse creates a failed NotifyTradeOrderResponse.
func NewFailResponse(resultCode, resultMessage string) *NotifyTradeOrderResponse {
	return &NotifyTradeOrderResponse{
		Result: Result{
			ResultCode:    resultCode,
			ResultStatus:  "U",
			ResultMessage: resultMessage,
		},
	}
}
