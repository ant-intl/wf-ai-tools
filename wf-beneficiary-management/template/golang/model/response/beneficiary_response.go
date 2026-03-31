package response

import (
	"{moduleName}/wf/model/domain"
)

// InquiryBeneficiaryTemplateResponse is the response for inquiryBeneficiaryTemplate API.
type InquiryBeneficiaryTemplateResponse struct {
	Result                      Result                     `json:"result"`
	ResponseID                  string                     `json:"responseId,omitempty"`
	CardTemplateData            []domain.CardTemplateField `json:"cardTemplateData,omitempty"`
	LocalCardTemplateData       []domain.CardTemplateField `json:"localCardTemplateData,omitempty"`
	CrossBorderCardTemplateData []domain.CardTemplateField `json:"crossBorderCardTemplateData,omitempty"`
}

// BindBeneficiaryResponse is the response for bindBeneficiary API.
type BindBeneficiaryResponse struct {
	Result      Result              `json:"result"`
	Beneficiary *domain.Beneficiary `json:"beneficiary,omitempty"`
}

// RemoveBeneficiaryResponse is the response for removeBeneficiary API.
type RemoveBeneficiaryResponse struct {
	Result           Result `json:"result"`
	BeneficiaryToken string `json:"beneficiaryToken,omitempty"`
}

// EditBeneficiaryResponse is the response for editBeneficiary API.
type EditBeneficiaryResponse struct {
	Result           Result `json:"result"`
	BeneficiaryToken string `json:"beneficiaryToken,omitempty"`
}

// InquiryBeneficiaryListResponse is the response for inquiryBeneficiaryList API.
type InquiryBeneficiaryListResponse struct {
	Result            Result               `json:"result"`
	ResponseID        string               `json:"responseId,omitempty"`
	Beneficiaries     []domain.Beneficiary `json:"beneficiaries,omitempty"`
	TotalCount        int                  `json:"totalCount,omitempty"`
	TotalPageNumber   int                  `json:"totalPageNumber,omitempty"`
	CurrentPageNumber int                  `json:"currentPageNumber,omitempty"`
}
