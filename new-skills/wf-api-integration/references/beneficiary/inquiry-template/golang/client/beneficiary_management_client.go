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
	inquiryBeneficiaryTemplatePath = "/amsin/api/v1/business/account/inquiryBeneficiaryTemplate"
	bindBeneficiaryPath            = "/amsin/api/v1/business/account/bindBeneficiary"
	removeBeneficiaryPath          = "/amsin/api/v1/business/account/removeBeneficiary"
	editBeneficiaryPath            = "/amsin/api/v1/business/account/editBeneficiary"
	inquiryBeneficiaryListPath     = "/amsin/api/v1/business/account/inquiryBeneficiaryList"
)

// BeneficiaryManagementClient handles all beneficiary management APIs.
// HTTP communication is delegated to WfHttpClient.
type BeneficiaryManagementClient struct {
	httpClient *util.WfHttpClient
}

// NewBeneficiaryManagementClient creates a new BeneficiaryManagementClient.
func NewBeneficiaryManagementClient(httpClient *util.WfHttpClient) *BeneficiaryManagementClient {
	return &BeneficiaryManagementClient{httpClient: httpClient}
}

// InquiryBeneficiaryTemplate queries the card template for a country/currency/beneficiary type.
// Use the returned field definitions to determine which fields are required when binding a beneficiary.
func (c *BeneficiaryManagementClient) InquiryBeneficiaryTemplate(
	req *request.InquiryBeneficiaryTemplateRequest,
) (*response.InquiryBeneficiaryTemplateResponse, error) {
	bodyBytes, err := json.Marshal(req)
	if err != nil {
		return nil, fmt.Errorf("failed to marshal request: %w", err)
	}

	respBody, err := c.httpClient.PostJSON(inquiryBeneficiaryTemplatePath, bodyBytes)
	if err != nil {
		return nil, err
	}

	var resp response.InquiryBeneficiaryTemplateResponse
	if err := json.Unmarshal(respBody, &resp); err != nil {
		return nil, fmt.Errorf("failed to unmarshal response: %w", err)
	}

	return handleBeneficiaryResult(resp.Result.ResultStatus, resp.Result.ResultCode, resp.Result.ResultMessage, &resp)
}

// BindBeneficiary binds a new beneficiary to the WF account and returns a beneficiaryToken.
func (c *BeneficiaryManagementClient) BindBeneficiary(
	req *request.BindBeneficiaryRequest,
) (*response.BindBeneficiaryResponse, error) {
	if err := req.Validate(); err != nil {
		return nil, exception.NewWfException(exception.ParamIllegal, err.Error())
	}

	bodyBytes, err := json.Marshal(req)
	if err != nil {
		return nil, fmt.Errorf("failed to marshal request: %w", err)
	}

	respBody, err := c.httpClient.PostJSON(bindBeneficiaryPath, bodyBytes)
	if err != nil {
		return nil, err
	}

	var resp response.BindBeneficiaryResponse
	if err := json.Unmarshal(respBody, &resp); err != nil {
		return nil, fmt.Errorf("failed to unmarshal response: %w", err)
	}

	return handleBeneficiaryResult(resp.Result.ResultStatus, resp.Result.ResultCode, resp.Result.ResultMessage, &resp)
}

// RemoveBeneficiary removes a bound beneficiary identified by beneficiaryToken.
func (c *BeneficiaryManagementClient) RemoveBeneficiary(
	req *request.RemoveBeneficiaryRequest,
) (*response.RemoveBeneficiaryResponse, error) {
	if err := req.Validate(); err != nil {
		return nil, exception.NewWfException(exception.ParamIllegal, err.Error())
	}

	bodyBytes, err := json.Marshal(req)
	if err != nil {
		return nil, fmt.Errorf("failed to marshal request: %w", err)
	}

	respBody, err := c.httpClient.PostJSON(removeBeneficiaryPath, bodyBytes)
	if err != nil {
		return nil, err
	}

	var resp response.RemoveBeneficiaryResponse
	if err := json.Unmarshal(respBody, &resp); err != nil {
		return nil, fmt.Errorf("failed to unmarshal response: %w", err)
	}

	return handleBeneficiaryResult(resp.Result.ResultStatus, resp.Result.ResultCode, resp.Result.ResultMessage, &resp)
}

// EditBeneficiary updates the nick name of an existing beneficiary.
func (c *BeneficiaryManagementClient) EditBeneficiary(
	req *request.EditBeneficiaryRequest,
) (*response.EditBeneficiaryResponse, error) {
	if err := req.Validate(); err != nil {
		return nil, exception.NewWfException(exception.ParamIllegal, err.Error())
	}

	bodyBytes, err := json.Marshal(req)
	if err != nil {
		return nil, fmt.Errorf("failed to marshal request: %w", err)
	}

	respBody, err := c.httpClient.PostJSON(editBeneficiaryPath, bodyBytes)
	if err != nil {
		return nil, err
	}

	var resp response.EditBeneficiaryResponse
	if err := json.Unmarshal(respBody, &resp); err != nil {
		return nil, fmt.Errorf("failed to unmarshal response: %w", err)
	}

	return handleBeneficiaryResult(resp.Result.ResultStatus, resp.Result.ResultCode, resp.Result.ResultMessage, &resp)
}

// InquiryBeneficiaryList queries the list of bound beneficiaries with pagination.
// pageSize max 50, pageNumber starts from 1.
func (c *BeneficiaryManagementClient) InquiryBeneficiaryList(
	req *request.InquiryBeneficiaryListRequest,
) (*response.InquiryBeneficiaryListResponse, error) {
	if err := req.Validate(); err != nil {
		return nil, exception.NewWfException(exception.ParamIllegal, err.Error())
	}

	bodyBytes, err := json.Marshal(req)
	if err != nil {
		return nil, fmt.Errorf("failed to marshal request: %w", err)
	}

	respBody, err := c.httpClient.PostJSON(inquiryBeneficiaryListPath, bodyBytes)
	if err != nil {
		return nil, err
	}

	var resp response.InquiryBeneficiaryListResponse
	if err := json.Unmarshal(respBody, &resp); err != nil {
		return nil, fmt.Errorf("failed to unmarshal response: %w", err)
	}

	return handleBeneficiaryResult(resp.Result.ResultStatus, resp.Result.ResultCode, resp.Result.ResultMessage, &resp)
}

// handleBeneficiaryResult is a generic helper to process the result status.
func handleBeneficiaryResult[T any](status, code, message string, resp *T) (*T, error) {
	if status == "" {
		return nil, exception.NewWfException(exception.InvalidResponseFormat, "resultStatus is missing")
	}
	switch status {
	case "S":
		return resp, nil
	case "F", "U":
		return nil, exception.NewWfException(exception.FromCode(code), message)
	default:
		return nil, exception.NewWfException(exception.InvalidResponseFormat,
			fmt.Sprintf("unknown resultStatus: %s", status))
	}
}
