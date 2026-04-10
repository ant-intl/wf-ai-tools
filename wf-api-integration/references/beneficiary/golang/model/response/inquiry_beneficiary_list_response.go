package response

import (
	"{moduleName}/wf/model/domain"
)

// InquiryBeneficiaryListResponse is the response for inquiryBeneficiaryList API.
type InquiryBeneficiaryListResponse struct {
	// Result is the API call result (接口调用结果)
	Result Result `json:"result"`

	// ResponseID is the unique response ID (响应唯一标识)
	ResponseID string `json:"responseId,omitempty"`

	// Beneficiaries is the list of beneficiaries (收款人列表)
	Beneficiaries []domain.Beneficiary `json:"beneficiaries,omitempty"`

	// TotalCount is the total number of items (总条数)
	TotalCount int `json:"totalCount,omitempty"`

	// TotalPageNumber is the total number of pages (总页数)
	TotalPageNumber int `json:"totalPageNumber,omitempty"`

	// CurrentPageNumber is the current page number (当前页码)
	CurrentPageNumber int `json:"currentPageNumber,omitempty"`
}

// IsSuccess returns true if the API call was successful.
func (r *InquiryBeneficiaryListResponse) IsSuccess() bool {
	return r.Result.IsSuccess()
}
