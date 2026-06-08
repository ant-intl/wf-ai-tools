package client

import (
	"encoding/json"
	"fmt"

	"{moduleName}/wf/model/exception"
	"{moduleName}/wf/model/request"
	"{moduleName}/wf/model/response"
	"{moduleName}/wf/util"
)

const (
	pathInquiryStatementList   = "/amsin/api/v1/business/account/inquiryStatementList"
	pathInquiryStatementDetail = "/amsin/api/v1/business/account/inquiryStatementDetail"
)

// StatementClient handles statement management API business logic.
// Includes InquiryStatementList and InquiryStatementDetail methods.
// HTTP communication is delegated to WfHttpClient.
type StatementClient struct {
	httpClient *util.WfHttpClient
}

// NewStatementClient creates a new StatementClient
func NewStatementClient(httpClient *util.WfHttpClient) *StatementClient {
	return &StatementClient{httpClient: httpClient}
}

// InquiryStatementList calls the WF inquiryStatementList API.
// Query account statement list with pagination support.
func (c *StatementClient) InquiryStatementList(req *request.InquiryStatementRequest) (*response.InquiryStatementResponse, error) {
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

	respBody, err := c.httpClient.PostJSON(pathInquiryStatementList, bodyBytes)
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

// InquiryStatementDetail calls the WF inquiryStatementDetail API.
// Query detailed information of a specific statement record.
// accountingBizNo is obtained from the InquiryStatementList API response.
func (c *StatementClient) InquiryStatementDetail(
	req *request.InquiryStatementDetailRequest,
) (*response.InquiryStatementDetailResponse, error) {
	if err := req.Validate(); err != nil {
		return nil, exception.NewWfException(exception.ParamIllegal, err.Error())
	}

	// Build request body
	bodyMap := map[string]interface{}{
		"accountingBizNo": req.AccountingBizNo,
	}

	bodyBytes, err := json.Marshal(bodyMap)
	if err != nil {
		return nil, fmt.Errorf("failed to marshal request: %w", err)
	}

	respBody, err := c.httpClient.PostJSON(pathInquiryStatementDetail, bodyBytes)
	if err != nil {
		return nil, err
	}

	var resp response.InquiryStatementDetailResponse
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
