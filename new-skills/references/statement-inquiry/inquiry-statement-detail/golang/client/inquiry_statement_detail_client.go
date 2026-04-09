package client

import (
	"encoding/json"
	"fmt"

	"{moduleName}/wf/model/exception"
	"{moduleName}/wf/model/request"
	"{moduleName}/wf/model/response"
	"{moduleName}/wf/util"
)

const inquiryStatementDetailPath = "/amsin/api/v1/business/account/inquiryStatementDetail"

// InquiryStatementDetailClient handles inquiryStatementDetail API business logic.
// HTTP communication is delegated to WfHttpClient.
// accountingBizNo is obtained from the inquiryStatementList API response.
type InquiryStatementDetailClient struct {
	httpClient *util.WfHttpClient
}

// NewInquiryStatementDetailClient creates a new InquiryStatementDetailClient
func NewInquiryStatementDetailClient(httpClient *util.WfHttpClient) *InquiryStatementDetailClient {
	return &InquiryStatementDetailClient{httpClient: httpClient}
}

// InquiryStatementDetail calls the WF inquiryStatementDetail API
func (c *InquiryStatementDetailClient) InquiryStatementDetail(
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

	respBody, err := c.httpClient.PostJSON(inquiryStatementDetailPath, bodyBytes)
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
