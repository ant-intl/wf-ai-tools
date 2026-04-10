package request

import "fmt"

// InquiryTradeOrderRequest represents the request for inquiryTradeOrder API.
// Used to query the processing result of a previously submitted trade order batch.
// Only applicable for PAY_INTO_CHINA scene.
type InquiryTradeOrderRequest struct {
	// RequestID must match the requestId used in the corresponding submitTradeOrder call, max 64 chars
	RequestID string `json:"requestId"`

	// SceneCode must be PAY_INTO_CHINA
	SceneCode string `json:"sceneCode"`

	// QuotaAccumulationMethod specifies how to accumulate settlement quota.
	// Values: USER_ID / RECEIVING_ACCOUNT / VIRTUAL_ACCOUNT / BENEFICIARY
	QuotaAccumulationMethod string `json:"quotaAccumulationMethod"`

	// QuotaAccumulationID is the identifier corresponding to the accumulation method
	QuotaAccumulationID string `json:"quotaAccumulationId"`

	// TradeType is the trade type: GOODS or SERVICE
	TradeType string `json:"tradeType"`
}

// Validate validates the request parameters
func (r *InquiryTradeOrderRequest) Validate() error {
	if r.RequestID == "" {
		return fmt.Errorf("requestId is required")
	}
	if r.SceneCode != "PAY_INTO_CHINA" {
		return fmt.Errorf("sceneCode must be PAY_INTO_CHINA for inquiryTradeOrder")
	}
	if r.TradeType == "" {
		return fmt.Errorf("tradeType is required")
	}
	return nil
}
