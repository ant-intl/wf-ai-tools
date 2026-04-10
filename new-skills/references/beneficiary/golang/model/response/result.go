package response

// Result represents the common result structure in WF API responses.
type Result struct {
	// ResultStatus is the result status: S (success), F (failure), U (unknown)
	ResultStatus string `json:"resultStatus"`

	// ResultCode is the result code (结果码)
	ResultCode string `json:"resultCode"`

	// ResultMessage is the result message (结果信息)
	ResultMessage string `json:"resultMessage"`
}

// IsSuccess returns true if the result status is "S" (success).
func (r *Result) IsSuccess() bool {
	return r.ResultStatus == "S"
}

// IsFailure returns true if the result status is "F" (failure).
func (r *Result) IsFailure() bool {
	return r.ResultStatus == "F"
}

// IsUnknown returns true if the result status is "U" (unknown/retryable).
func (r *Result) IsUnknown() bool {
	return r.ResultStatus == "U"
}
