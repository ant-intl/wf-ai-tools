package response

import "{moduleName}/wf/model/domain"

// Result represents the API call result
type Result struct {
	ResultCode    string `json:"resultCode"`
	ResultMessage string `json:"resultMessage,omitempty"`
	ResultStatus  string `json:"resultStatus"`
}

// InquirySubuserResponse represents the response from inquirySubuser API
type InquirySubuserResponse struct {
	Result *Result `json:"result"`

	// PrimaryUserInformation is the primary account info, returned when resultStatus=S
	PrimaryUserInformation *domain.SubUserInfo `json:"primaryUserInformation,omitempty"`

	// UserInformations is the list of subaccount info, returned when resultStatus=S
	UserInformations []domain.SubUserInfo `json:"userInformations,omitempty"`

	// TotalCount is the total number of items, max 8 chars
	TotalCount int `json:"totalCount,omitempty"`

	// TotalPageNumber is the total number of pages, max 8 chars
	TotalPageNumber int `json:"totalPageNumber,omitempty"`

	// CurrentPageNumber is the current page index, max 8 chars
	CurrentPageNumber int `json:"currentPageNumber,omitempty"`
}
