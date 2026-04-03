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
	consultPayoutPath = "/amsin/api/v1/business/fund/consultPayout"
	createPayoutPath  = "/amsin/api/v1/business/fund/createPayout"
	inquiryPayoutPath = "/amsin/api/v1/business/fund/inquiryPayout"
)

// PayoutClient handles consultPayout, createPayout and inquiryPayout API business logic.
// HTTP communication is delegated to WfHttpClient.
//
// Cross-currency payout flow:
//  1. Call ConsultPayout to get exchange rate quote (quoteId)
//  2. Pass quoteId to CreatePayout's transferToDetail.transferQuote.quoteId
//  3. If response is PROCESSING, poll with InquiryPayout
type PayoutClient struct {
	httpClient *util.WfHttpClient
}

// NewPayoutClient creates a new PayoutClient
func NewPayoutClient(httpClient *util.WfHttpClient) *PayoutClient {
	return &PayoutClient{httpClient: httpClient}
}

// ConsultPayout calls the WF consultPayout API to get exchange rate quote.
// Used before cross-currency createPayout to obtain quoteId.
// The returned quoteId should be passed to CreatePayout's transferToDetail.transferQuote.quoteId.
func (c *PayoutClient) ConsultPayout(req *request.ConsultPayoutRequest) (*response.ConsultPayoutResponse, error) {
	if err := req.Validate(); err != nil {
		return nil, exception.NewWfException(exception.ParamIllegal, err.Error())
	}

	bodyBytes, err := json.Marshal(req)
	if err != nil {
		return nil, fmt.Errorf("failed to marshal request: %w", err)
	}

	respBody, err := c.httpClient.PostJSON(consultPayoutPath, bodyBytes)
	if err != nil {
		return nil, err
	}

	var resp response.ConsultPayoutResponse
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

// CreatePayout calls the WF createPayout API.
// When response.IsProcessing() is true, caller must poll with InquiryPayout.
func (c *PayoutClient) CreatePayout(req *request.CreatePayoutRequest) (*response.CreatePayoutResponse, error) {
	if err := req.Validate(); err != nil {
		return nil, exception.NewWfException(exception.ParamIllegal, err.Error())
	}

	bodyBytes, err := json.Marshal(req)
	if err != nil {
		return nil, fmt.Errorf("failed to marshal request: %w", err)
	}

	respBody, err := c.httpClient.PostJSON(createPayoutPath, bodyBytes)
	if err != nil {
		return nil, err
	}

	var resp response.CreatePayoutResponse
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

// InquiryPayout calls the WF inquiryPayout API.
// Caller should check resp.TransferResult for the transfer-level status:
//   - IsSuccess()    → transfer completed
//   - IsProcessing() → still in progress, continue polling
//   - otherwise      → transfer failed
func (c *PayoutClient) InquiryPayout(req *request.InquiryPayoutRequest) (*response.InquiryPayoutResponse, error) {
	if err := req.Validate(); err != nil {
		return nil, exception.NewWfException(exception.ParamIllegal, err.Error())
	}

	bodyBytes, err := json.Marshal(req)
	if err != nil {
		return nil, fmt.Errorf("failed to marshal request: %w", err)
	}

	respBody, err := c.httpClient.PostJSON(inquiryPayoutPath, bodyBytes)
	if err != nil {
		return nil, err
	}

	var resp response.InquiryPayoutResponse
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
