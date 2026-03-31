package client

import (
	"encoding/json"
	"fmt"

	"{moduleName}/wf/model/exception"
	"{moduleName}/wf/model/request"
	"{moduleName}/wf/model/response"
	"{moduleName}/wf/util"
)

const inquiryStatementPath = "/amsin/api/v1/business/account/inquiryStatementList"

// InquiryStatementClient handles inquiryStatementList API business logic.
// HTTP communication is delegated to WfHttpClient.
type InquiryStatementClient struct {
	httpClient *util.WfHttpClient
}

// NewInquiryStatementClient creates a new InquiryStatementClient
func NewInquiryStatementClient(httpClient *util.WfHttpClient) *InquiryStatementClient {
	return &InquiryStatementClient{httpClient: httpClient}
}

// InquiryStatementList calls the WF inquiryStatementList API
func (c *InquiryStatementClient) InquiryStatementList(req *request.InquiryStatementRequest) (*response.InquiryStatementResponse, error) {
	if err := req.Validate(); err != nil {
		return nil, exception.NewWfException(exception.ParamIllegal, err.Error())
	}

	// Build request body — only include non-empty optional fields
	bodyMap := map[string]interface{}{
		"startTime":  req.StartTime,
		"endTime":    req.EndTime,
		"pageSize":   req.PageSize,
		"pageNumber": req.PageNumber,
	}
	if len(req.TransactionTypeList) > 0 {
		bodyMap["transactionTypeList"] = req.TransactionTypeList
	}
	if len(req.CurrencyList) > 0 {
		bodyMap["currencyList"] = req.CurrencyList
	}
	if len(req.BalanceTypes) > 0 {
		bodyMap["balanceTypes"] = req.BalanceTypes
	}
	if len(req.BudgetAccountIds) > 0 {
		bodyMap["budgetAccountIds"] = req.BudgetAccountIds
	}
	if req.FuzzyName != "" {
		bodyMap["fuzzyName"] = req.FuzzyName
	}

	bodyBytes, err := json.Marshal(bodyMap)
	if err != nil {
		return nil, fmt.Errorf("failed to marshal request: %w", err)
	}

	respBody, err := c.httpClient.PostJSON(inquiryStatementPath, bodyBytes)
	if err != nil {
		return nil, err
	}

	var resp response.InquiryStatementResponse
	if err := json.Unmarshal(respBody, &resp); err != nil {
		return nil, fmt.Errorf("failed to unmarshal response: %w", err)
	}

	if resp.Result.ResultStatus == "" {
		return nil, exception.NewWfException(exception.InvalidResponseFormat, "resultStatus is missing")
	}

	switch resp.Result.ResultStatus {
	case "S":
		return &resp, nil
	case "F":
		return nil, exception.NewWfException(exception.FromCode(resp.Result.ResultCode), resp.Result.ResultMessage)
	case "U":
		return nil, exception.NewWfException(exception.FromCode(resp.Result.ResultCode), resp.Result.ResultMessage)
	default:
		return nil, exception.NewWfException(exception.InvalidResponseFormat,
			fmt.Sprintf("unknown resultStatus: %s", resp.Result.ResultStatus))
	}
}
