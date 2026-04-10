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
	submitTradeOrderPath  = "/amsin/api/v1/business/account/submitTradeOrder"
	inquiryTradeOrderPath = "/amsin/api/v1/business/account/inquiryTradeOrder"
)

// TradeOrderManagementClient handles submitTradeOrder and inquiryTradeOrder API business logic.
// HTTP communication is delegated to WfHttpClient.
//
// Trade order flow:
//  1. Call SubmitTradeOrder to upload trade orders
//  2. If PAY_INTO_CHINA: poll with InquiryTradeOrder until batchStatus = FINISHED
//  3. Or wait for async callback at notifyUrl
type TradeOrderManagementClient struct {
	httpClient *util.WfHttpClient
}

// NewTradeOrderManagementClient creates a new TradeOrderManagementClient
func NewTradeOrderManagementClient(httpClient *util.WfHttpClient) *TradeOrderManagementClient {
	return &TradeOrderManagementClient{httpClient: httpClient}
}

// SubmitTradeOrder calls the WF submitTradeOrder API.
// For PAY_INTO_CHINA: response contains per-order TradeOrderResult.
// For CREATE_B2B_ORDERS: response contains AcceptOrderID.
// When result is PROCESSING, poll with InquiryTradeOrder or wait for notifyUrl callback.
func (c *TradeOrderManagementClient) SubmitTradeOrder(req *request.SubmitTradeOrderRequest) (*response.SubmitTradeOrderResponse, error) {
	if err := req.Validate(); err != nil {
		return nil, exception.NewWfException(exception.ParamIllegal, err.Error())
	}

	bodyBytes, err := json.Marshal(req)
	if err != nil {
		return nil, fmt.Errorf("failed to marshal request: %w", err)
	}

	respBody, err := c.httpClient.PostJSON(submitTradeOrderPath, bodyBytes)
	if err != nil {
		return nil, err
	}

	var resp response.SubmitTradeOrderResponse
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

// InquiryTradeOrder calls the WF inquiryTradeOrder API.
// Only applicable for PAY_INTO_CHINA scene.
// Caller should check resp.IsProcessing() / resp.IsFinished():
//   - IsProcessing() → batch still in progress, continue polling
//   - IsFinished()   → inspect resp.TradeOrderResults for per-order status
func (c *TradeOrderManagementClient) InquiryTradeOrder(req *request.InquiryTradeOrderRequest) (*response.InquiryTradeOrderResponse, error) {
	if err := req.Validate(); err != nil {
		return nil, exception.NewWfException(exception.ParamIllegal, err.Error())
	}

	bodyBytes, err := json.Marshal(req)
	if err != nil {
		return nil, fmt.Errorf("failed to marshal request: %w", err)
	}

	respBody, err := c.httpClient.PostJSON(inquiryTradeOrderPath, bodyBytes)
	if err != nil {
		return nil, err
	}

	var resp response.InquiryTradeOrderResponse
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
