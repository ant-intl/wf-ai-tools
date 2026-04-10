package client

import (
	"encoding/json"
	"fmt"

	"{moduleName}/wf/model/exception"
	"{moduleName}/wf/model/request"
	"{moduleName}/wf/model/response"
	"{moduleName}/wf/util"
)

const inquiryBalancePath = "/amsin/api/v1/business/account/inquiryBalance"

// InquiryBalanceClient handles inquiryBalance API business logic.
// HTTP communication is delegated to WfHttpClient.
type InquiryBalanceClient struct {
	httpClient *util.WfHttpClient
}

// NewInquiryBalanceClient creates a new InquiryBalanceClient
func NewInquiryBalanceClient(httpClient *util.WfHttpClient) *InquiryBalanceClient {
	return &InquiryBalanceClient{httpClient: httpClient}
}

// InquiryBalance calls the WF inquiryBalance API
func (c *InquiryBalanceClient) InquiryBalance(req *request.InquiryBalanceRequest) (*response.InquiryBalanceResponse, error) {
	if err := req.Validate(); err != nil {
		return nil, exception.NewWfException(exception.ParamIllegal, err.Error())
	}

	// Build request body — only include non-empty fields
	bodyMap := make(map[string]interface{})
	if len(req.CurrencyList) > 0 {
		bodyMap["currencyList"] = req.CurrencyList
	}
	if len(req.BalanceTypes) > 0 {
		bodyMap["balanceTypes"] = req.BalanceTypes
	}
	if req.BudgetAccountID != "" {
		bodyMap["budgetAccountId"] = req.BudgetAccountID
	}

	bodyBytes, err := json.Marshal(bodyMap)
	if err != nil {
		return nil, fmt.Errorf("failed to marshal request: %w", err)
	}

	respBody, err := c.httpClient.PostJSON(inquiryBalancePath, bodyBytes)
	if err != nil {
		return nil, err
	}

	var resp response.InquiryBalanceResponse
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
