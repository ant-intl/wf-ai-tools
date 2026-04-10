package request

import "{moduleName}/wf/model/domain"

// NotifyTradeOrderRequest represents the inbound callback request from WF
// when trade order processing is complete (PAY_INTO_CHINA scene only).
//
// WF POSTs this to the integrator's notifyUrl. The integrator must:
//  1. Verify the request signature
//  2. Process tradeOrderResults (preferably async)
//  3. Return NotifyTradeOrderResponse with resultCode = SUCCESS
type NotifyTradeOrderRequest struct {
	// RequestID matches the requestId from the original submitTradeOrder call
	RequestID string `json:"requestId"`

	// TradeOrderResults contains per-order processing results
	TradeOrderResults []domain.TradeOrderResult `json:"tradeOrderResults"`
}
