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
	pathInquiryAccount        = "/amsin/api/v1/business/account/inquiryAccount"
	pathInquiryBalance        = "/amsin/api/v1/business/account/inquiryBalance"
	pathInquiryAvailableQuota = "/amsin/api/v1/business/account/inquiryAvailableQuota"
	pathInquirySubuser        = "/amsin/api/v1/business/user/inquirySubuser"
)

// InquiryAccountInfoClient handles account inquiry API business logic.
// Includes InquiryAccount, InquiryBalance, InquiryAvailableQuota and InquirySubuser methods.
// HTTP communication is delegated to WfHttpClient.
type InquiryAccountInfoClient struct {
	httpClient *util.WfHttpClient
}

// NewInquiryAccountInfoClient creates a new InquiryAccountInfoClient
func NewInquiryAccountInfoClient(httpClient *util.WfHttpClient) *InquiryAccountInfoClient {
	return &InquiryAccountInfoClient{httpClient: httpClient}
}

// InquiryAccount calls the WF inquiryAccount API.
// Query account information including account type, account number, activation status, currencies, etc.
//
// Supported accountType values:
//   - RECEIVE_ACCOUNT: WF receiving account (requires referenceCustomerId)
//   - VIRTUAL_ACCOUNT: WF virtual account (requires accessToken)
//   - ALIPAY_WALLET: Alipay wallet (requires referenceCustomerId)
//   - ALIPAY_SHADOW_WALLET: affiliated company Alipay wallet (requires accountId)
//   - ALIPAY_ORIGIN_WALLET: enterprise Alipay wallet
func (c *InquiryAccountInfoClient) InquiryAccount(req *request.InquiryAccountRequest) (*response.InquiryAccountResponse, error) {
	if err := req.Validate(); err != nil {
		return nil, exception.NewWfException(exception.ParamIllegal, err.Error())
	}

	// Build request body — only include non-empty fields
	bodyMap := map[string]interface{}{
		"accountType": req.AccountType,
	}
	if req.ReferenceCustomerID != "" {
		bodyMap["referenceCustomerId"] = req.ReferenceCustomerID
	}
	if req.AccountID != "" {
		bodyMap["accountId"] = req.AccountID
	}
	if req.AccessToken != "" {
		bodyMap["accessToken"] = req.AccessToken
	}

	bodyBytes, err := json.Marshal(bodyMap)
	if err != nil {
		return nil, fmt.Errorf("failed to marshal request: %w", err)
	}

	respBody, err := c.httpClient.PostJSON(pathInquiryAccount, bodyBytes)
	if err != nil {
		return nil, err
	}

	var resp response.InquiryAccountResponse
	if err := json.Unmarshal(respBody, &resp); err != nil {
		return nil, fmt.Errorf("failed to unmarshal response: %w", err)
	}

	if resp.Result == nil || resp.Result.ResultStatus == "" {
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

// InquirySubuser calls the WF inquirySubuser API.
// Query primary account and subaccount information with pagination.
// Only the primary account is allowed to call this API.
func (c *InquiryAccountInfoClient) InquirySubuser(req *request.InquirySubuserRequest) (*response.InquirySubuserResponse, error) {
	if err := req.Validate(); err != nil {
		return nil, exception.NewWfException(exception.ParamIllegal, err.Error())
	}

	bodyMap := map[string]interface{}{
		"pageSize":   req.PageSize,
		"pageNumber": req.PageNumber,
	}

	bodyBytes, err := json.Marshal(bodyMap)
	if err != nil {
		return nil, fmt.Errorf("failed to marshal request: %w", err)
	}

	respBody, err := c.httpClient.PostJSON(pathInquirySubuser, bodyBytes)
	if err != nil {
		return nil, err
	}

	var resp response.InquirySubuserResponse
	if err := json.Unmarshal(respBody, &resp); err != nil {
		return nil, fmt.Errorf("failed to unmarshal response: %w", err)
	}

	if resp.Result == nil || resp.Result.ResultStatus == "" {
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

// InquiryBalance calls the WF inquiryBalance API.
// Query account balances with optional currency and balance type filters.
func (c *InquiryAccountInfoClient) InquiryBalance(req *request.InquiryBalanceRequest) (*response.InquiryBalanceResponse, error) {
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

	respBody, err := c.httpClient.PostJSON(pathInquiryBalance, bodyBytes)
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

// InquiryAvailableQuota calls the WF inquiryAvailableQuota API.
// Query available settlement quota with support for four accumulation methods:
//   - USER_ID: by user ID
//   - RECEIVING_ACCOUNT: by receiving account
//   - VIRTUAL_ACCOUNT: by virtual account
//   - BENEFICIARY: by beneficiary (requires tradeType)
func (c *InquiryAccountInfoClient) InquiryAvailableQuota(req *request.InquiryAvailableQuotaRequest) (*response.InquiryAvailableQuotaResponse, error) {
	if err := req.Validate(); err != nil {
		return nil, exception.NewWfException(exception.ParamIllegal, err.Error())
	}

	bodyMap := map[string]interface{}{
		"quotaAccumulationMethod": req.QuotaAccumulationMethod,
		"quotaAccumulationId":     req.QuotaAccumulationId,
		"currency":                req.Currency,
	}
	if req.TradeType != "" {
		bodyMap["tradeType"] = req.TradeType
	}

	bodyBytes, err := json.Marshal(bodyMap)
	if err != nil {
		return nil, fmt.Errorf("failed to marshal request: %w", err)
	}

	respBody, err := c.httpClient.PostJSON(pathInquiryAvailableQuota, bodyBytes)
	if err != nil {
		return nil, err
	}

	var resp response.InquiryAvailableQuotaResponse
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
