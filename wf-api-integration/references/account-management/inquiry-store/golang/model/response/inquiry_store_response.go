package response

import "{moduleName}/wf/model/domain"

// Result represents the API call result
type Result struct {
	ResultCode    string `json:"resultCode"`
	ResultMessage string `json:"resultMessage,omitempty"`
	ResultStatus  string `json:"resultStatus"`
}

// InquiryStoreResponse represents the response from inquiryStore API
type InquiryStoreResponse struct {
	Result *Result `json:"result"`

	// StoreInformation is the list of store info, returned when resultStatus=S
	StoreInformation []domain.StoreInfo `json:"storeInformation,omitempty"`

	// TotalCount is the total number of items, max 8 chars
	TotalCount int `json:"totalCount,omitempty"`

	// TotalPageNumber is the total number of pages, max 8 chars
	TotalPageNumber int `json:"totalPageNumber,omitempty"`

	// CurrentPageNumber is the current page index, max 8 chars
	CurrentPageNumber int `json:"currentPageNumber,omitempty"`
}
