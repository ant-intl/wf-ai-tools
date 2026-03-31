package response

// InquiryStatementResponse represents the response from inquiryStatementList API
type InquiryStatementResponse struct {
	Result            Result            `json:"result"`
	ResponseID        string            `json:"responseId"`
	FeeItemType       string            `json:"feeItemType"`
	StatementList     []StatementRecord `json:"statementList"`
	TotalCount        int               `json:"totalCount"`
	TotalPageNumber   int               `json:"totalPageNumber"`
	CurrentPageNumber int               `json:"currentPageNumber"`
}

// StatementRecord represents a single statement record
type StatementRecord struct {
	TransactionID      string `json:"transactionId"`
	TransactionTime    string `json:"transactionTime"`
	TransactionType    string `json:"transactionType"`
	Currency           string `json:"currency"`
	Amount             string `json:"amount"`
	Balance            string `json:"balance"`
	BalanceType        string `json:"balanceType"`
	CounterpartyName   string `json:"counterpartyName"`
	CounterpartyAccount string `json:"counterpartyAccount"`
	Remark             string `json:"remark"`
	Status             string `json:"status"`
	FeeAmount          string `json:"feeAmount"`
	ActualAmount       string `json:"actualAmount"`
}
