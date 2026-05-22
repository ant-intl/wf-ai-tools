package response

import "{moduleName}/wf/model/domain"

// Result represents the API call result
type Result struct {
	ResultCode    string `json:"resultCode"`
	ResultMessage string `json:"resultMessage"`
	ResultStatus  string `json:"resultStatus"`
}

// InquiryAccountResponse represents the response from inquiryAccount API
type InquiryAccountResponse struct {
	// 接口调用结果
	Result *Result `json:"result"`

	// 响应唯一 ID
	ResponseID string `json:"responseId"`

	// 万里汇账户唯一标识
	AccountID string `json:"accountId,omitempty"`

	// 账号信息列表
	AccountInfos []domain.AccountInfo `json:"accountInfos,omitempty"`

	// 客户信息
	Customer *domain.Customer `json:"customer,omitempty"`

	// 企业支付宝用户信息（ALIPAY_ORIGIN_WALLET 时返回）
	AlipayCustomer *domain.AlipayCustomer `json:"alipayCustomer,omitempty"`

	// 关联公司信息（ALIPAY_SHADOW_WALLET 时返回）
	AffiliatedCustomer *domain.AlipayCustomer `json:"affiliatedCustomer,omitempty"`
}

// IsSuccess 判断接口调用是否成功
func (r *InquiryAccountResponse) IsSuccess() bool {
	return r.Result != nil && r.Result.ResultStatus == "S"
}
