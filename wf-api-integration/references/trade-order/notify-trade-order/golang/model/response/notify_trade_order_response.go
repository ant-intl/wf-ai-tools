package response

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
