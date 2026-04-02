package request

import (
	"fmt"

	"{moduleName}/wf/model/domain"
)

// ConsultPayoutRequest represents the request for consultPayout API.
// Used to get exchange rate quote (quoteId) before cross-currency createPayout.
type ConsultPayoutRequest struct {
	// TransferFromDetail specifies the payer's transfer details.
	// Must include transferFromAmount.currency to indicate the deduction currency.
	TransferFromDetail *domain.TransferFromDetail `json:"transferFromDetail"`

	// TransferToDetail specifies the payee's transfer details.
	// Contains transferToAmount and transferToMethod.
	TransferToDetail *domain.TransferToDetail `json:"transferToDetail"`

	// BusinessSceneCode is required when transferToAmount.currency = CNY.
	// Values: THIRD_PARTY_PAYOUT (transfer to third-party card), SAME_NAME_PAYOUT (withdraw to same-name card)
	BusinessSceneCode string `json:"businessSceneCode,omitempty"`
}

// Validate validates the request parameters for consultPayout
func (r *ConsultPayoutRequest) Validate() error {
	if r.TransferFromDetail == nil || r.TransferFromDetail.TransferFromAmount == nil {
		return fmt.Errorf("transferFromDetail.transferFromAmount is required")
	}
	if r.TransferFromDetail.TransferFromAmount.Currency == "" {
		return fmt.Errorf("transferFromDetail.transferFromAmount.currency is required")
	}

	if r.TransferToDetail == nil || r.TransferToDetail.TransferToAmount == nil {
		return fmt.Errorf("transferToDetail.transferToAmount is required")
	}
	if r.TransferToDetail.TransferToAmount.Currency == "" {
		return fmt.Errorf("transferToDetail.transferToAmount.currency is required")
	}

	// CNY requires businessSceneCode
	if r.TransferToDetail.TransferToAmount.Currency == "CNY" && r.BusinessSceneCode == "" {
		return fmt.Errorf("businessSceneCode is required when transferToAmount.currency is CNY")
	}

	return nil
}
