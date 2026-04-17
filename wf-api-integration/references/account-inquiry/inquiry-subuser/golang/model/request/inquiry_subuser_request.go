package request

import "fmt"

// InquirySubuserRequest represents the request for inquirySubuser API
type InquirySubuserRequest struct {
	// PageSize is the number of items per page (required)
	PageSize int `json:"pageSize"`

	// PageNumber is the page index starting from 1 (required)
	PageNumber int `json:"pageNumber"`
}

// Validate validates the request parameters
func (r *InquirySubuserRequest) Validate() error {
	if r.PageSize <= 0 {
		return fmt.Errorf("pageSize must be a positive integer")
	}
	if r.PageNumber <= 0 {
		return fmt.Errorf("pageNumber must be a positive integer")
	}
	return nil
}
