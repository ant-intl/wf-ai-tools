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
	apiPath = "/amsin/api/v1/business/account/inquiryAvailableQuota"
)

// InquiryAvailableQuotaClient 查询可申报结汇额度客户端
type InquiryAvailableQuotaClient struct {
	config         *config.WfConfig
	httpClientUtil *util.WfHttpClient
	signer         signer.Signer
}

// NewInquiryAvailableQuotaClient 创建客户端实例
func NewInquiryAvailableQuotaClient(cfg *config.WfConfig, s signer.Signer) *InquiryAvailableQuotaClient {
	return &InquiryAvailableQuotaClient{
		config:         cfg,
		httpClientUtil: util.NewWfHttpClient(cfg, s),
		signer:         s,
	}
}

// InquiryAvailableQuota 查询可申报的结汇额度
//
// 支持四种累计方式：
//   - USER_ID: 按用户ID累计
//   - RECEIVING_ACCOUNT: 按收款账户累计
//   - VIRTUAL_ACCOUNT: 按虚拟账户累计
//   - BENEFICIARY: 按收款人累计（需传tradeType）
func (c *InquiryAvailableQuotaClient) InquiryAvailableQuota(req *request.InquiryAvailableQuotaRequest) (*response.InquiryAvailableQuotaResponse, error) {
	if err := c.validate(req); err != nil {
		return nil, err
	}

	requestBody, err := json.Marshal(req)
	if err != nil {
		return nil, fmt.Errorf("failed to marshal request: %w", err)
	}

	url := c.config.BaseUrl + apiPath
	log.Printf("[InquiryAvailableQuota] Request URL: %s", url)

	responseBody, err := c.httpClientUtil.SendPostRequest(url, apiPath, string(requestBody))
	if err != nil {
		return nil, fmt.Errorf("http request failed: %w", err)
	}

	return c.parseResponse(responseBody)
}

// validate 参数校验
func (c *InquiryAvailableQuotaClient) validate(req *request.InquiryAvailableQuotaRequest) error {
	if req == nil {
		return exception.NewWfException(exception.PARAM_ILLEGAL, "request must not be nil")
	}
	if req.QuotaAccumulationMethod == "" {
		return exception.NewWfException(exception.PARAM_ILLEGAL, "quotaAccumulationMethod must not be blank")
	}
	if req.QuotaAccumulationId == "" {
		return exception.NewWfException(exception.PARAM_ILLEGAL, "quotaAccumulationId must not be blank")
	}
	if req.Currency == "" {
		return exception.NewWfException(exception.PARAM_ILLEGAL, "currency must not be blank")
	}
	// BENEFICIARY 方式时必须传 tradeType
	if req.QuotaAccumulationMethod == "BENEFICIARY" && req.TradeType == "" {
		return exception.NewWfException(exception.PARAM_ILLEGAL, "tradeType is required when quotaAccumulationMethod is BENEFICIARY")
	}
	return nil
}

// parseResponse 解析响应
func (c *InquiryAvailableQuotaClient) parseResponse(responseBody string) (*response.InquiryAvailableQuotaResponse, error) {
	var resp response.InquiryAvailableQuotaResponse
	if err := json.Unmarshal([]byte(responseBody), &resp); err != nil {
		return nil, exception.NewWfException(exception.INVALID_RESPONSE_FORMAT,
			fmt.Sprintf("failed to parse response: %v", err))
	}

	if resp.Result == nil {
		return nil, exception.NewWfException(exception.INVALID_RESPONSE_FORMAT, "response result is nil")
	}

	switch resp.Result.ResultStatus {
	case "S":
		log.Printf("[InquiryAvailableQuota] Success, availableQuota=%v", resp.AvailableQuota)
		return &resp, nil
	case "F":
		return nil, exception.NewWfException(exception.WfErrorCode(resp.Result.ResultCode), resp.Result.ResultMessage)
	default:
		// U 状态或其他，需要重试
		return nil, exception.NewWfException(exception.WfErrorCode(resp.Result.ResultCode),
			fmt.Sprintf("unknown/retryable status: %s", resp.Result.ResultStatus))
	}
}
