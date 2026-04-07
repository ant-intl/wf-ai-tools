package response

import "{moduleName}/wf/model/domain"

// SubmitTradeOrderResponse represents the response from submitTradeOrder API.
// PAY_INTO_CHINA returns RequestID + TradeOrderResult per order.
// CREATE_B2B_ORDERS returns AcceptOrderID.
type SubmitTradeOrderResponse struct {
	// Result represents the API call result
	Result Result `json:"result"`

	// RequestID echoes the request ID (PAY_INTO_CHINA)
	RequestID string `json:"requestId,omitempty"`

	// TradeOrderResult contains per-order results (PAY_INTO_CHINA)
	TradeOrderResult []domain.TradeOrderResult `json:"tradeOrderResult,omitempty"`

	// AcceptOrderID is the WF acceptance order ID (CREATE_B2B_ORDERS), max 128 chars
	AcceptOrderID string `json:"acceptOrderId,omitempty"`
}

// IsProcessing returns true when the result indicates async processing
func (r *SubmitTradeOrderResponse) IsProcessing() bool {
	return r.Result.ResultStatus == "S" && r.Result.ResultCode == "PROCESSING"
}
