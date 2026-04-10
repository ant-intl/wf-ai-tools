package response

import (
	"{moduleName}/wf/model/domain"
)

// InquiryBeneficiaryTemplateResponse is the response for inquiryBeneficiaryTemplate API.
// Contains card template field definitions for binding beneficiaries.
type InquiryBeneficiaryTemplateResponse struct {
	// Result is the API call result (接口调用结果)
	Result Result `json:"result"`

	// ResponseID is the unique response ID (响应唯一标识)
	ResponseID string `json:"responseId,omitempty"`

	// CardTemplateData is the standard card template field list (标准卡模版字段列表)
	CardTemplateData []domain.CardTemplateField `json:"cardTemplateData,omitempty"`

	// LocalCardTemplateData is the local clearing network template fields (本地清算网络模版字段)
	LocalCardTemplateData []domain.CardTemplateField `json:"localCardTemplateData,omitempty"`

	// CrossBorderCardTemplateData is the cross-border clearing network template fields (跨境清算网络模版字段)
	CrossBorderCardTemplateData []domain.CardTemplateField `json:"crossBorderCardTemplateData,omitempty"`
}

// IsSuccess returns true if the API call was successful.
func (r *InquiryBeneficiaryTemplateResponse) IsSuccess() bool {
	return r.Result.IsSuccess()
}
