package response

import "{moduleName}/wf/model/domain"

// InquiryTradeOrderResponse represents the response from inquiryTradeOrder API.
// When BatchStatus is PROCESSING, caller should poll again after an interval.
// When BatchStatus is FINISHED, TradeOrderResults contains per-order results.
type InquiryTradeOrderResponse struct {
	// Result represents the API call result
	Result Result `json:"result"`

	// RequestID echoes the request ID
	RequestID string `json:"requestId"`

	// BatchStatus indicates the batch processing status: PROCESSING or FINISHED
	BatchStatus string `json:"batchStatus"`

	// TradeOrderResults contains per-order results when batchStatus = FINISHED
	TradeOrderResults []domain.TradeOrderResult `json:"tradeOrderResults,omitempty"`
}

// IsProcessing returns true when the batch is still being processed.
// Caller should wait and poll again.
func (r *InquiryTradeOrderResponse) IsProcessing() bool {
	return r.BatchStatus == "PROCESSING"
}

// IsFinished returns true when the batch processing is complete.
// Caller should inspect TradeOrderResults for per-order status.
func (r *InquiryTradeOrderResponse) IsFinished() bool {
	return r.BatchStatus == "FINISHED"
}
