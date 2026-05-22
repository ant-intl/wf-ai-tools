package response

import "{moduleName}/wf/model/domain"

// InquiryAvailableQuotaResponse 查询可申报结汇额度响应
type InquiryAvailableQuotaResponse struct {
	// 接口调用结果
	Result *Result `json:"result"`

	// 结汇额度累计方式
	QuotaAccumulationMethod string `json:"quotaAccumulationMethod"`

	// 累计方式标识
	QuotaAccumulationId string `json:"quotaAccumulationId"`

	// 可申报的结汇额度
	AvailableQuota *domain.Amount `json:"availableQuota"`

	// 贸易类型（BENEFICIARY时返回）
	TradeType string `json:"tradeType,omitempty"`
}

// IsSuccess 判断接口调用是否成功
func (r *InquiryAvailableQuotaResponse) IsSuccess() bool {
	return r.Result != nil && r.Result.ResultStatus == "S"
}
