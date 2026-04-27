package domain

// Amount represents a monetary amount in WF API.
// Value is in minor units (e.g. cents): USD 10.00 = value 1000.
// 2-decimal currencies (USD/EUR/GBP/CNY etc): value = face_amount × 100
// 0-decimal currencies (JPY/KRW): value = face_amount × 1
type Amount struct {
	Currency string `json:"currency"`
	Value    *int64 `json:"value,omitempty"`
}

// TransferFromDetail represents the payer detail in payout
type TransferFromDetail struct {
	TransferFromAmount *Amount             `json:"transferFromAmount,omitempty"`
	TransferFromMethod *TransferFromMethod `json:"transferFromMethod,omitempty"`
}

// TransferFromMethod represents the payer transfer method
type TransferFromMethod struct {
	CustomerID        string `json:"customerId,omitempty"`
	PaymentMethodType string `json:"paymentMethodType,omitempty"`
}

// TransferToDetail represents the payee detail in payout
type TransferToDetail struct {
	TransferToAmount  *Amount            `json:"transferToAmount,omitempty"`
	TransferToMethod  *TransferToMethod  `json:"transferToMethod,omitempty"`
	TransferQuote     *TransferQuote     `json:"transferQuote,omitempty"`
	PurposeCode       string             `json:"purposeCode,omitempty"`
	TransferNotifyURL string             `json:"transferNotifyUrl,omitempty"`
	FeeAmount         *Amount            `json:"feeAmount,omitempty"`
}

// TransferToMethod represents the payee transfer method.
// Supports five mutually exclusive modes:
//   - BANK_ACCOUNT_DETAIL: payout to bank card, set PaymentMethodMetaData with bank details (JSON string)
//   - BENEFICIARY_TOKEN: payout to bank card via token, set PaymentMethodID with beneficiaryToken
//   - ALIPAY_CN_DETAIL: payout to Alipay CN account, PaymentMethodMetaData can be empty
//   - REFERENCE_ALIPAY_CN: payout to linked Alipay wallet, set PaymentMethodID with referenceCustomerId
//   - WALLET_ACCOUNT_DETAIL: payout to wallet account, set PaymentMethodMetaData with wallet details (JSON string), PaymentMethodID with walletAccountId
type TransferToMethod struct {
	PaymentMethodType     string `json:"paymentMethodType"`
	PaymentMethodMetaData string `json:"paymentMethodMetaData,omitempty"`
	PaymentMethodID       string `json:"paymentMethodId,omitempty"`
}

// PaymentMethodMetaData contains payment method details for bank account mode.
// For BANK_ACCOUNT_DETAIL mode, bank fields are used.
// For WALLET_ACCOUNT_DETAIL mode, use WalletAccountDetail struct instead.
type PaymentMethodMetaData struct {
	BankAccountName      string `json:"bankAccountName,omitempty"`
	BankAccountNo        string `json:"bankAccountNo,omitempty"`
	BankName             string `json:"bankName,omitempty"`
	BankBIC              string `json:"bankBIC,omitempty"`
	BankAccountIBAN      string `json:"bankAccountIBAN,omitempty"`
	RoutingNumber        string `json:"routingNumber,omitempty"`
	BeneficiaryAddress   string `json:"beneficiaryAddress,omitempty"`
	BankCountryCode      string `json:"bankCountryCode,omitempty"`
	BeneficiaryPhone     string `json:"beneficiaryPhone,omitempty"`
	BankBranchCode       string `json:"bankBranchCode,omitempty"`
	BankLocalName        string `json:"bankLocalName,omitempty"`
	BankAccountLocalName string `json:"bankAccountLocalName,omitempty"`
	BeneficiaryType      string `json:"beneficiaryType,omitempty"`
}

// WalletAccountDetail contains wallet account details for WALLET_ACCOUNT_DETAIL mode.
type WalletAccountDetail struct {
	WalletFullName   string `json:"walletFullName,omitempty"`
	WalletAccountNo  string `json:"walletAccountNo,omitempty"`
	WalletBrandName  string `json:"walletBrandName,omitempty"`
	WalletCountryCode string `json:"walletCountryCode,omitempty"`
}

// TransferQuote contains quote information for cross-currency payout
type TransferQuote struct {
	QuoteID           string `json:"quoteId,omitempty"`
	QuoteCurrencyPair string `json:"quoteCurrencyPair,omitempty"`
	QuotePrice        string `json:"quotePrice,omitempty"`
	QuoteStartTime    string `json:"quoteStartTime,omitempty"`
	QuoteExpiryTime   string `json:"quoteExpiryTime,omitempty"`
}

// TransferResult represents the transfer-level result in inquiryPayout response
type TransferResult struct {
	ResultStatus  string `json:"resultStatus"`
	ResultCode    string `json:"resultCode"`
	ResultMessage string `json:"resultMessage"`
}

func (r *TransferResult) IsSuccess() bool    { return r.ResultCode == "SUCCESS" }
func (r *TransferResult) IsProcessing() bool { return r.ResultCode == "PROCESSING" }
