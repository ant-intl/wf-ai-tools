package response

import "{moduleName}/wf/model/domain"

// InquiryStatementResponse represents the response from inquiryStatementList API
type InquiryStatementResponse struct {
	Result            Result            `json:"result"`
	ResponseID        string            `json:"responseId"`
	StatementList     []StatementRecord `json:"statementList"`
	TotalCount        int               `json:"totalCount"`
	TotalPageNumber   int               `json:"totalPageNumber"`
	CurrentPageNumber int               `json:"currentPageNumber"`
}

// StatementRecord represents a single statement record
type StatementRecord struct {
	TransactionID              string                `json:"transactionId"`
	TransactionTime            string                `json:"transactionTime"`
	TransactionType            string                `json:"transactionType"`
	TransactionStatus          string                `json:"transactionStatus"`
	BalanceType                string                `json:"balanceType"`
	AccountBalance             *domain.Amount        `json:"accountBalance,omitempty"`
	FeeAmount                  *domain.Amount        `json:"feeAmount,omitempty"`
	NetAmount                  *domain.Amount        `json:"netAmount,omitempty"`
	OriginalTransactionAmount  *domain.Amount        `json:"originalTransactionAmount,omitempty"`
	ReceiveAmount              *domain.Amount        `json:"receiveAmount,omitempty"`
	TransactionAmount          *domain.Amount        `json:"transactionAmount,omitempty"`
	GoodsAmount                *domain.Amount        `json:"goodsAmount,omitempty"`
	PlatformFeeAmount          *domain.Amount        `json:"platformFeeAmount,omitempty"`
	OriginalFeeAmount          *domain.Amount        `json:"originalFeeAmount,omitempty"`
	DiscountFeeAmount          *domain.Amount        `json:"discountFeeAmount,omitempty"`
	ExtTransactionID           string                `json:"extTransactionId"`
	AccountingBizNo            string                `json:"accountingBizNo"`
	GoodsName                  string                `json:"goodsName,omitempty"`
	ForeignExchangeQuote       *ForeignExchangeQuote `json:"foreignExchangeQuote,omitempty"`
	RefundForeignExchangeQuote *ForeignExchangeQuote `json:"refundForeignExchangeQuote,omitempty"`
	FundMoveDetail             *FundMoveDetail       `json:"fundMoveDetail,omitempty"`
	OperatorInfo               *OperatorInfo         `json:"operatorInfo,omitempty"`
}

// ForeignExchangeQuote represents FX rate information.
// Used in both foreignExchangeQuote (normal transactions) and
// refundForeignExchangeQuote (refund transactions like CHARGE_REFUND, TRANSFER_REFUND).
type ForeignExchangeQuote struct {
	QuotePrice           string `json:"quotePrice"`
	TransferFromCurrency string `json:"transferFromCurrency"`
	TransferToCurrency   string `json:"transferToCurrency"`
}

// FundMoveDetail represents fund movement details
type FundMoveDetail struct {
	// 付款方信息
	PayerName        string `json:"payerName,omitempty"`
	PayerAccountNo   string `json:"payerAccountNo,omitempty"`
	PayerAccountType string `json:"payerAccountType,omitempty"`
	PayerBankName    string `json:"payerBankName,omitempty"`
	PayerUserId      string `json:"payerUserId,omitempty"`

	// 收款方信息
	BeneficiaryName           string `json:"beneficiaryName,omitempty"`
	BeneficiaryAccountNo      string `json:"beneficiaryAccountNo,omitempty"`
	BeneficiaryAccountType    string `json:"beneficiaryAccountType,omitempty"`
	BeneficiaryBankCountry    string `json:"beneficiaryBankCountry,omitempty"`
	BeneficiaryBankName       string `json:"beneficiaryBankName,omitempty"`
	BeneficiaryStoreName      string `json:"beneficiaryStoreName,omitempty"`
	BeneficiaryMarketplaceName string `json:"beneficiaryMarketplaceName,omitempty"`
	ReceiveAccount            string `json:"receiveAccount,omitempty"`

	// 交易说明
	Remarks           string `json:"remarks,omitempty"`
	Description       string `json:"description,omitempty"`
	PaymentExplanation string `json:"paymentExplanation,omitempty"`
	PaymentSubject    string `json:"paymentSubject,omitempty"`
	PaymentVoucherNo  string `json:"paymentVoucherNo,omitempty"`
}

// OperatorInfo represents the operator who initiated the transaction via WF portal
type OperatorInfo struct {
	OperatorName  string `json:"operatorName,omitempty"`
	OperatorEmail string `json:"operatorEmail,omitempty"`
}
