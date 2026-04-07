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
	inquiryTransferPath = "/amsin/api/v1/business/fund/inquiryTransfer"
)

// TransferClient handles inquiryTransfer API business logic.
// HTTP communication is delegated to WfHttpClient.
type TransferClient struct {
	httpClient *util.WfHttpClient
}

// NewTransferClient creates a new TransferClient
func NewTransferClient(httpClient *util.WfHttpClient) *TransferClient {
	return &TransferClient{httpClient: httpClient}
}

// InquiryTransfer calls the WF inquiryTransfer API to query the fund transfer result.
//
// The response contains two result layers:
//   - Result: the API call result
//   - TransferResult: the transfer business result
//
// When response.IsTransferProcessing() is true, caller must continue polling.
func (c *TransferClient) InquiryTransfer(req *request.InquiryTransferRequest) (*response.InquiryTransferResponse, error) {
	if err := req.Validate(); err != nil {
		return nil, exception.NewWfException(exception.ParamIllegal, err.Error())
	}

	bodyBytes, err := json.Marshal(req)
	if err != nil {
		return nil, fmt.Errorf("failed to marshal request: %w", err)
	}

	respBody, err := c.httpClient.PostJSON(inquiryTransferPath, bodyBytes)
	if err != nil {
		return nil, err
	}

	var resp response.InquiryTransferResponse
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
