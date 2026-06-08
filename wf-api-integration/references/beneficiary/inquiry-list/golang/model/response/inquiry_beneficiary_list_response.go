package response

import (
	"{moduleName}/wf/model/domain"
)

// Result represents the standard WF API result object.
type Result struct {
	ResultStatus  string `json:"resultStatus"`
	ResultCode    string `json:"resultCode"`
	ResultMessage string `json:"resultMessage"`
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

