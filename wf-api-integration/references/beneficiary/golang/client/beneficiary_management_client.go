package client

import (
	"encoding/json"
	"fmt"
	"log"

	"{moduleName}/wf/config"
	"{moduleName}/wf/model/exception"
	"{moduleName}/wf/model/request"
	"{moduleName}/wf/model/response"
	"{moduleName}/wf/signer"
	"{moduleName}/wf/util"
)

const (
	// API paths for beneficiary management
	inquiryBeneficiaryTemplatePath = "/amsin/api/v1/business/account/inquiryBeneficiaryTemplate"
	bindBeneficiaryPath            = "/amsin/api/v1/business/account/bindBeneficiary"
	removeBeneficiaryPath          = "/amsin/api/v1/business/account/removeBeneficiary"
	editBeneficiaryPath            = "/amsin/api/v1/business/account/editBeneficiary"
	inquiryBeneficiaryListPath     = "/amsin/api/v1/business/account/inquiryBeneficiaryList"
)

// BeneficiaryManagementClient is a unified client for all beneficiary management APIs.
// It provides 5 interface methods:
//   1. InquiryBeneficiaryTemplate - Query card template (查询卡模版)
//   2. BindBeneficiary - Bind beneficiary (绑定收款人)
//   3. RemoveBeneficiary - Remove beneficiary (删除收款人)
//   4. EditBeneficiary - Edit beneficiary nickname (编辑收款人昵称)
//   5. InquiryBeneficiaryList - Query beneficiary list (查询收款人列表)
type BeneficiaryManagementClient struct {
	config         *config.WfConfig
	httpClientUtil *util.WfHttpClient
	signer         signer.Signer
}

// NewBeneficiaryManagementClient creates a new BeneficiaryManagementClient.
func NewBeneficiaryManagementClient(cfg *config.WfConfig, s signer.Signer) *BeneficiaryManagementClient {
	return &BeneficiaryManagementClient{
		config:         cfg,
		httpClientUtil: util.NewWfHttpClient(cfg, s),
		signer:         s,
	}
}

// -------------------------------------------------------------------------
// 1. InquiryBeneficiaryTemplate - Query card template
// -------------------------------------------------------------------------

// InquiryBeneficiaryTemplate queries the card template for a country/currency/beneficiary type.
// Use the returned field definitions to determine which fields are required when binding a beneficiary.
//
// Parameters:
//   - req: The inquiry request containing countryCode, currency, and/or beneficiaryType
//
// Returns:
//   - The template response containing cardTemplateData, localCardTemplateData, and crossBorderCardTemplateData
//   - Error if the request fails or response is invalid
func (c *BeneficiaryManagementClient) InquiryBeneficiaryTemplate(
	req *request.InquiryBeneficiaryTemplateRequest,
) (*response.InquiryBeneficiaryTemplateResponse, error) {
	if req == nil {
		return nil, exception.NewWfException(exception.ParamIllegal, "request must not be nil")
	}

	resp := &response.InquiryBeneficiaryTemplateResponse{}
	if err := c.executeRequest(req, inquiryBeneficiaryTemplatePath, resp); err != nil {
		return nil, err
	}
	return resp, nil
}

// -------------------------------------------------------------------------
// 2. BindBeneficiary - Bind beneficiary
// -------------------------------------------------------------------------

// BindBeneficiary binds a new beneficiary to the WF account and returns a beneficiaryToken.
// The beneficiaryBankAccount fields should be filled according to the card template.
//
// Parameters:
//   - req: The bind request containing beneficiary details
//
// Returns:
//   - The bind response containing the beneficiaryToken
//   - Error if validation fails or the request fails
func (c *BeneficiaryManagementClient) BindBeneficiary(
	req *request.BindBeneficiaryRequest,
) (*response.BindBeneficiaryResponse, error) {
	if err := req.Validate(); err != nil {
		return nil, exception.NewWfException(exception.ParamIllegal, err.Error())
	}

	resp := &response.BindBeneficiaryResponse{}
	if err := c.executeRequest(req, bindBeneficiaryPath, resp); err != nil {
		return nil, err
	}
	return resp, nil
}

// -------------------------------------------------------------------------
// 3. RemoveBeneficiary - Remove beneficiary
// -------------------------------------------------------------------------

// RemoveBeneficiary removes a bound beneficiary identified by beneficiaryToken.
//
// Parameters:
//   - req: The remove request containing the beneficiaryToken to remove
//
// Returns:
//   - The remove response confirming the deletion
//   - Error if validation fails or the request fails
func (c *BeneficiaryManagementClient) RemoveBeneficiary(
	req *request.RemoveBeneficiaryRequest,
) (*response.RemoveBeneficiaryResponse, error) {
	if err := req.Validate(); err != nil {
		return nil, exception.NewWfException(exception.ParamIllegal, err.Error())
	}

	resp := &response.RemoveBeneficiaryResponse{}
	if err := c.executeRequest(req, removeBeneficiaryPath, resp); err != nil {
		return nil, err
	}
	return resp, nil
}

// -------------------------------------------------------------------------
// 4. EditBeneficiary - Edit beneficiary nickname
// -------------------------------------------------------------------------

// EditBeneficiary updates the nick name of an existing beneficiary.
//
// Parameters:
//   - req: The edit request containing beneficiaryToken and new beneficiaryNick
//
// Returns:
//   - The edit response confirming the update
//   - Error if validation fails or the request fails
func (c *BeneficiaryManagementClient) EditBeneficiary(
	req *request.EditBeneficiaryRequest,
) (*response.EditBeneficiaryResponse, error) {
	if err := req.Validate(); err != nil {
		return nil, exception.NewWfException(exception.ParamIllegal, err.Error())
	}

	resp := &response.EditBeneficiaryResponse{}
	if err := c.executeRequest(req, editBeneficiaryPath, resp); err != nil {
		return nil, err
	}
	return resp, nil
}

// -------------------------------------------------------------------------
// 5. InquiryBeneficiaryList - Query beneficiary list
// -------------------------------------------------------------------------

// InquiryBeneficiaryList queries the list of bound beneficiaries with pagination.
// pageSize max 50, pageNumber starts from 1.
//
// Parameters:
//   - req: The list request containing pagination and filter parameters
//
// Returns:
//   - The list response containing beneficiaries and pagination info
//   - Error if validation fails or the request fails
func (c *BeneficiaryManagementClient) InquiryBeneficiaryList(
	req *request.InquiryBeneficiaryListRequest,
) (*response.InquiryBeneficiaryListResponse, error) {
	if err := req.Validate(); err != nil {
		return nil, exception.NewWfException(exception.ParamIllegal, err.Error())
	}

	resp := &response.InquiryBeneficiaryListResponse{}
	if err := c.executeRequest(req, inquiryBeneficiaryListPath, resp); err != nil {
		return nil, err
	}
	return resp, nil
}

// -------------------------------------------------------------------------
// Private helper methods
// -------------------------------------------------------------------------

// executeRequest is a generic helper to execute HTTP requests and parse responses.
func (c *BeneficiaryManagementClient) executeRequest(req interface{}, apiPath string, resp interface{}) error {
	requestBody, err := json.Marshal(req)
	if err != nil {
		return fmt.Errorf("failed to marshal request: %w", err)
	}

	url := c.config.BaseURL + apiPath
	log.Printf("[BeneficiaryManagementClient] Request URL: %s", url)

	responseBody, err := c.httpClientUtil.SendPostRequest(url, apiPath, string(requestBody))
	if err != nil {
		return fmt.Errorf("http request failed: %w", err)
	}

	return parseResponse(responseBody, resp)
}

// parseResponse parses the HTTP response and handles result status.
func parseResponse(responseBody string, resp interface{}) error {
	if err := json.Unmarshal([]byte(responseBody), resp); err != nil {
		return exception.NewWfException(exception.InvalidResponseFormat,
			fmt.Sprintf("failed to parse response: %v", err))
	}

	// Use type assertion to get the Result field
	result := getResultFromResponse(resp)
	if result == nil {
		return exception.NewWfException(exception.InvalidResponseFormat, "response result is nil")
	}

	switch result.ResultStatus {
	case "S":
		log.Printf("[BeneficiaryManagementClient] Success, resultCode=%s", result.ResultCode)
		return nil
	case "F":
		return exception.NewWfException(exception.FromCode(result.ResultCode), result.ResultMessage)
	case "U":
		// Unknown status - may need retry
		return exception.NewWfException(exception.FromCode(result.ResultCode),
			fmt.Sprintf("unknown/retryable status: %s - %s", result.ResultStatus, result.ResultMessage))
	default:
		return exception.NewWfException(exception.InvalidResponseFormat,
			fmt.Sprintf("unknown resultStatus: %s", result.ResultStatus))
	}
}

// getResultFromResponse extracts the Result field from response using type assertion.
func getResultFromResponse(resp interface{}) *response.Result {
	// Type assertion for known response types
	switch r := resp.(type) {
	case *response.InquiryBeneficiaryTemplateResponse:
		return &r.Result
	case *response.BindBeneficiaryResponse:
		return &r.Result
	case *response.RemoveBeneficiaryResponse:
		return &r.Result
	case *response.EditBeneficiaryResponse:
		return &r.Result
	case *response.InquiryBeneficiaryListResponse:
		return &r.Result
	default:
		return nil
	}
}
