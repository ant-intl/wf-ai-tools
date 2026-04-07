package response

import "{moduleName}/wf/model/domain"

// ConsultPayoutResponse represents the response from consultPayout API.
// Contains exchange rate quote information for cross-currency payout.
type ConsultPayoutResponse struct {
	// Result represents the API call result
	Result Result `json:"result"`

	// ChargeMode indicates the fee charge mode.
	// Values: INNER_DEDUCT (deduct from transfer amount), OUTER_DEDUCT (charge separately)
	// Only returned when result.resultStatus = S
	ChargeMode string `json:"chargeMode,omitempty"`

	// TransferFromDetail contains the calculated payer amount.
	// Only returned when result.resultStatus = S
	TransferFromDetail *domain.TransferFromDetail `json:"transferFromDetail,omitempty"`

	// TransferToDetail contains the calculated payee amount and exchange rate quote.
	// The quoteId in transferQuote should be used in subsequent createPayout request.
	// Only returned when result.resultStatus = S
	TransferToDetail *domain.TransferToDetail `json:"transferToDetail,omitempty"`

	// AvailableQuota represents the remaining settlement quota.
	// Only returned when transferToAmount.currency = CNY and result.resultStatus = S
	AvailableQuota *domain.Amount `json:"availableQuota,omitempty"`
}

// IsSuccess returns true if the API call was successful
func (r *ConsultPayoutResponse) IsSuccess() bool {
	return r.Result.ResultStatus == "S"
}

// GetQuoteID returns the exchange rate quote ID from the response.
// This quoteId should be passed to createPayout's transferToDetail.transferQuote.quoteId
// for cross-currency payouts.
func (r *ConsultPayoutResponse) GetQuoteID() string {
	if r.TransferToDetail != nil && r.TransferToDetail.TransferQuote != nil {
		return r.TransferToDetail.TransferQuote.QuoteID
	}
	return ""
}

// GetQuotePrice returns the exchange rate from the response
func (r *ConsultPayoutResponse) GetQuotePrice() string {
	if r.TransferToDetail != nil && r.TransferToDetail.TransferQuote != nil {
		return r.TransferToDetail.TransferQuote.QuotePrice
	}
	return ""
}

// GetQuoteCurrencyPair returns the currency pair (e.g., "USD/CNY") from the response
func (r *ConsultPayoutResponse) GetQuoteCurrencyPair() string {
	if r.TransferToDetail != nil && r.TransferToDetail.TransferQuote != nil {
		return r.TransferToDetail.TransferQuote.QuoteCurrencyPair
	}
	return ""
}

// GetQuoteExpiryTime returns the quote expiry time from the response
func (r *ConsultPayoutResponse) GetQuoteExpiryTime() string {
	if r.TransferToDetail != nil && r.TransferToDetail.TransferQuote != nil {
		return r.TransferToDetail.TransferQuote.QuoteExpiryTime
	}
	return ""
}

