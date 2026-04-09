package response

import "{moduleName}/wf/model/domain"

// InquiryStatementDetailResponse represents the response from inquiryStatementDetail API
type InquiryStatementDetailResponse struct {
	Result Result `json:"result"`

	// ResponseID is the unique ID assigned by WorldFirst to identify a response. Max 32 chars.
	ResponseID string `json:"responseId"`

	// TransactionID is the unique ID assigned by WorldFirst to identify a transaction.
	// Required when transactionType is TRANSFER/TRANSFER_REFUND/WITHDRAWAL/WITHDRAWAL_REFUND/
	// CONVERSION/CONVERSION_DEAL/CHARGE/CHARGE_REFUND/DEDUCTION/FUND_COLLECTION.
	TransactionID string `json:"transactionId,omitempty"`

	// ExtTransactionID is the unique ID assigned by the Partner to identify a transaction. Max 256 chars.
	ExtTransactionID string `json:"extTransactionId,omitempty"`

	// TransactionStatus: INIT / PROCESSING / PENDING / SUCCESS / FAIL / REFUNDED
	TransactionStatus string `json:"transactionStatus"`

	// TransactionTime is the time when the WorldFirst balance change occurs. ISO 8601 format.
	TransactionTime string `json:"transactionTime"`

	// TransactionType is the type of the balance change, e.g. TRANSFER, COLLECTION.
	TransactionType string `json:"transactionType"`

	// TransactionAmount is the amount of the balance change.
	TransactionAmount *domain.Amount `json:"transactionAmount,omitempty"`

	// OriginalTransactionAmount is the transaction amount originally submitted.
	OriginalTransactionAmount *domain.Amount `json:"originalTransactionAmount,omitempty"`

	// FeeAmount is the amount of the service fee.
	// Conditionally required for TRANSFER/WITHDRAWAL/CONVERSION/CHARGE etc.
	FeeAmount *domain.Amount `json:"feeAmount,omitempty"`

	// FeeItemType: OBO_SERVICE_FEE / REMIT_SERVICE_FEE
	FeeItemType string `json:"feeItemType,omitempty"`

	// NetAmount = originalTransactionAmount - feeAmount.
	// Required when transactionType is TRANSFER/CHARGE/PAYMENT/CASH_BACK.
	NetAmount *domain.Amount `json:"netAmount,omitempty"`

	// ReceiveAmount = netAmount * foreignExchangeQuote.quotePrice.
	// Required when transactionType is TRANSFER/CHARGE/PAYMENT/CASH_BACK.
	ReceiveAmount *domain.Amount `json:"receiveAmount,omitempty"`

	// AccountBalance is the account balance in real time after the transaction.
	AccountBalance *domain.Amount `json:"accountBalance,omitempty"`

	// FundMoveDetail contains fund flow details such as payer and beneficiary information.
	FundMoveDetail *FundMoveDetail `json:"fundMoveDetail,omitempty"`

	// ForeignExchangeQuote contains FX quote information for the transaction.
	// Optional for TRANSFER/WITHDRAWAL/CONVERSION/CONVERSION_DEAL/CHARGE.
	ForeignExchangeQuote *ForeignExchangeQuote `json:"foreignExchangeQuote,omitempty"`

	// RefundForeignExchangeQuote contains FX quote information for refund transactions.
	// Optional for TRANSFER_REFUND/WITHDRAWAL_REFUND/CHARGE_REFUND.
	RefundForeignExchangeQuote *ForeignExchangeQuote `json:"refundForeignExchangeQuote,omitempty"`

	// BalanceType: NORMAL_BALANCE (default) / SAME_NAME_TOP_UP_BALANCE / BUDGET_BALANCE
	BalanceType string `json:"balanceType,omitempty"`

	// AccountingBizNo is the unique ID used to identify a change in WorldFirst balance.
	AccountingBizNo string `json:"accountingBizNo"`

	// FailReason is returned when transactionStatus is FAIL.
	FailReason *Result `json:"failReason,omitempty"`

	// CombinedTransactionList contains related transaction information.
	CombinedTransactionList []RelatedStatement `json:"combinedTransactionList,omitempty"`

	// OperatorInfo is returned only when the transaction is initiated on the WF portal.
	OperatorInfo *OperatorInfo `json:"operatorInfo,omitempty"`

	// GoodsName is the name of the goods.
	GoodsName string `json:"goodsName,omitempty"`

	// GoodsAmount is the amount of the goods.
	GoodsAmount *domain.Amount `json:"goodsAmount,omitempty"`

	// OriginalFeeAmount is the amount of service fee before discount.
	OriginalFeeAmount *domain.Amount `json:"originalFeeAmount,omitempty"`

	// DiscountFeeAmount is the discount amount of service fee.
	DiscountFeeAmount *domain.Amount `json:"discountFeeAmount,omitempty"`
}

// Result represents the API call result
type Result struct {
	ResultStatus  string `json:"resultStatus"`
	ResultCode    string `json:"resultCode"`
	ResultMessage string `json:"resultMessage"`
}

// RelatedStatement represents a related transaction entry in combinedTransactionList
type RelatedStatement struct {
	TransactionID   string `json:"transactionId,omitempty"`
	AccountingBizNo string `json:"accountingBizNo,omitempty"`
	TransactionType string `json:"transactionType,omitempty"`
}

// ForeignExchangeQuote represents FX rate information.
// Used in both foreignExchangeQuote (normal transactions) and
// refundForeignExchangeQuote (refund transactions like CHARGE_REFUND, TRANSFER_REFUND).
type ForeignExchangeQuote struct {
	QuotePrice           string `json:"quotePrice"`
	TransferFromCurrency string `json:"transferFromCurrency"`
	TransferToCurrency   string `json:"transferToCurrency"`
}

// FundMoveDetail represents fund movement details including payer and beneficiary information
type FundMoveDetail struct {
	// 付款方信息
	PayerName        string `json:"payerName,omitempty"`
	PayerAccountNo   string `json:"payerAccountNo,omitempty"`
	PayerAccountType string `json:"payerAccountType,omitempty"`
	PayerBankName    string `json:"payerBankName,omitempty"`
	PayerUserId      string `json:"payerUserId,omitempty"`

	// 收款方信息
	BeneficiaryName            string `json:"beneficiaryName,omitempty"`
	BeneficiaryAccountNo       string `json:"beneficiaryAccountNo,omitempty"`
	BeneficiaryAccountType     string `json:"beneficiaryAccountType,omitempty"`
	BeneficiaryBankCountry     string `json:"beneficiaryBankCountry,omitempty"`
	BeneficiaryBankName        string `json:"beneficiaryBankName,omitempty"`
	BeneficiaryStoreName       string `json:"beneficiaryStoreName,omitempty"`
	BeneficiaryMarketplaceName string `json:"beneficiaryMarketplaceName,omitempty"`
	ReceiveAccount             string `json:"receiveAccount,omitempty"`

	// 交易说明
	Remarks            string `json:"remarks,omitempty"`
	Description        string `json:"description,omitempty"`
	PaymentExplanation string `json:"paymentExplanation,omitempty"`
	PaymentSubject     string `json:"paymentSubject,omitempty"`
	PaymentVoucherNo   string `json:"paymentVoucherNo,omitempty"`
}

// OperatorInfo represents the operator who initiated the transaction via WF portal
type OperatorInfo struct {
	OperatorName  string `json:"operatorName,omitempty"`
	OperatorEmail string `json:"operatorEmail,omitempty"`
}
