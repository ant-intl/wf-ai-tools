package domain

// Amount represents a monetary amount in WF API.
// Value is in minor units (e.g. cents): USD 10.00 = value 1000.
// 2-decimal currencies (USD/EUR/GBP/CNY etc): value = face_amount × 100
// 0-decimal currencies (JPY/KRW): value = face_amount × 1
type Amount struct {
	Currency string `json:"currency"`
	Value    *int64 `json:"value,omitempty"`
}

// PaymentMethod represents a payment/collection method.
//
// PaymentMethodType values:
//   - BALANCE: use WorldFirst account balance
//   - WORLDFIRST_RECEIVE_ACCOUNT: use WorldFirst RA account (payee only)
type PaymentMethod struct {
	// PaymentMethodType is the payment/collection method type.
	PaymentMethodType string `json:"paymentMethodType"`
	// PaymentMethodId is required when PaymentMethodType is WORLDFIRST_RECEIVE_ACCOUNT. Max 128 chars.
	PaymentMethodId string `json:"paymentMethodId,omitempty"`
	// AccountId uniquely identifies a WorldFirst account. Required when PaymentMethodType is BALANCE. Max 64 chars.
	AccountId string `json:"accountId,omitempty"`
}

// Quote represents the transfer exchange rate.
type Quote struct {
	// QuoteId is the unique quote identifier. Max 64 chars.
	QuoteId string `json:"quoteId"`
	// QuoteCurrencyPair is the currency pair, e.g. "USD/GBP". Max 16 chars.
	QuoteCurrencyPair string `json:"quoteCurrencyPair,omitempty"`
	// QuotePrice is the exchange rate price. Max 20 chars.
	QuotePrice string `json:"quotePrice,omitempty"`
	// QuoteStartTime is when the quote becomes effective. ISO 8601 format.
	QuoteStartTime string `json:"quoteStartTime,omitempty"`
	// QuoteExpiryTime is when the quote expires (quoteStartTime + 30s). ISO 8601 format.
	QuoteExpiryTime string `json:"quoteExpiryTime,omitempty"`
	// Guaranteed indicates whether the quote is a guaranteed rate.
	Guaranteed *bool `json:"guaranteed,omitempty"`
}

// TransferFromDetail represents the payer detail in transfer.
type TransferFromDetail struct {
	// TransferFromMethod is the payer's payment method.
	// Required when BusinessSceneCode is MULTI_ACCOUNT_TRANSFER.
	TransferFromMethod *PaymentMethod `json:"transferFromMethod,omitempty"`
	// TransferFromAmount is the amount the payer needs to pay (before fees).
	TransferFromAmount *Amount `json:"transferFromAmount,omitempty"`
	// ActualTransferFromAmount is the actual transfer amount (after fees). Response only.
	ActualTransferFromAmount *Amount `json:"actualTransferFromAmount,omitempty"`
	// FeeAmount is the transfer fee paid by the payer. Response only.
	FeeAmount *Amount `json:"feeAmount,omitempty"`
}

// TransferToDetail represents the payee detail in transfer.
type TransferToDetail struct {
	// TransferToMethod is the payee's collection method.
	TransferToMethod *PaymentMethod `json:"transferToMethod,omitempty"`
	// TransferToAmount is the transfer collection amount (before fees).
	TransferToAmount *Amount `json:"transferToAmount,omitempty"`
	// ActualTransferToAmount is the actual transfer amount (after fees). Response only.
	ActualTransferToAmount *Amount `json:"actualTransferToAmount,omitempty"`
	// FeeAmount is the transfer fee. Response only.
	FeeAmount *Amount `json:"feeAmount,omitempty"`
	// TransferQuote is the transfer exchange rate.
	TransferQuote *Quote `json:"transferQuote,omitempty"`
	// NeedMigrateMaterial indicates whether to migrate transaction materials.
	// Required when BusinessSceneCode is MULTI_ACCOUNT_TRANSFER.
	NeedMigrateMaterial *bool `json:"needMigrateMaterial,omitempty"`
	// TransferNotifyUrl is the URL for receiving transfer notifications.
	TransferNotifyUrl string `json:"transferNotifyUrl,omitempty"`
	// TransferRemark is used for bank settlement bills. Max 1024 chars.
	TransferRemark string `json:"transferRemark,omitempty"`
	// TransferMemo is a short note from the payer. Max 256 chars.
	TransferMemo string `json:"transferMemo,omitempty"`
	// ExtendInfo is extra transfer information. Max 2048 chars.
	ExtendInfo string `json:"extendInfo,omitempty"`
	// PurposeCode is the transfer purpose code.
	// Values: GDS (goods), TXS (tax), ACM (agency commission), GST (service trade), COM (commission).
	// Defaults to GDS.
	PurposeCode string `json:"purposeCode,omitempty"`
}

