package request

import (
	"fmt"

	"{moduleName}/wf/model/domain"
)

// SubmitTradeOrderRequest represents the request for submitTradeOrder API.
// Supports two scenes:
//   - PAY_INTO_CHINA (B2C): upload trade orders for foreign-exchange settlement
//   - CREATE_B2B_ORDERS (B2B): upload B2B trade orders for association
type SubmitTradeOrderRequest struct {
	// RequestID is the idempotency key, max 64 chars
	RequestID string `json:"requestId"`

	// SceneCode identifies the business scene: PAY_INTO_CHINA or CREATE_B2B_ORDERS
	SceneCode string `json:"sceneCode"`

	// QuotaAccumulationMethod specifies how to accumulate settlement quota.
	// Values: USER_ID / RECEIVING_ACCOUNT / VIRTUAL_ACCOUNT / BENEFICIARY / TRANSFER_ID / COLLECTION_ID
	QuotaAccumulationMethod string `json:"quotaAccumulationMethod"`

	// QuotaAccumulationID is the identifier corresponding to the accumulation method
	QuotaAccumulationID string `json:"quotaAccumulationId"`

	// TradeOrders is the list of trade orders. B2C max 100, B2B max 10
	TradeOrders []domain.TradeOrder `json:"tradeOrders"`

	// NotifyURL is the async callback URL, max 256 chars
	NotifyURL string `json:"notifyUrl,omitempty"`

	// Platform is required when sceneCode = PAY_INTO_CHINA
	Platform string `json:"platform,omitempty"`

	// ExtendInfo is additional information for B2B, max 2048 chars
	ExtendInfo string `json:"extendInfo,omitempty"`
}

// Validate validates the request parameters
func (r *SubmitTradeOrderRequest) Validate() error {
	if r.RequestID == "" {
		return fmt.Errorf("requestId is required")
	}
	if len(r.RequestID) > 64 {
		return fmt.Errorf("requestId max length is 64")
	}
	if r.SceneCode == "" {
		return fmt.Errorf("sceneCode is required")
	}
	if len(r.TradeOrders) == 0 {
		return fmt.Errorf("tradeOrders must not be empty")
	}
	// B2C max 100, B2B max 10
	switch r.SceneCode {
	case "PAY_INTO_CHINA":
		if len(r.TradeOrders) > 100 {
			return fmt.Errorf("tradeOrders max count is 100 for PAY_INTO_CHINA")
		}
		if r.Platform == "" {
			return fmt.Errorf("platform is required when sceneCode is PAY_INTO_CHINA")
		}
	case "CREATE_B2B_ORDERS":
		if len(r.TradeOrders) > 10 {
			return fmt.Errorf("tradeOrders max count is 10 for CREATE_B2B_ORDERS")
		}
	default:
		return fmt.Errorf("unsupported sceneCode: %s", r.SceneCode)
	}
	return nil
}
