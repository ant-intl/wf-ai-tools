package response

// Result represents the common result structure in WF API responses
type Result struct {
	ResultStatus  string `json:"resultStatus"`
	ResultCode    string `json:"resultCode"`
	ResultMessage string `json:"resultMessage"`
}

func (r *Result) IsSuccess() bool { return r.ResultStatus == "S" }
func (r *Result) IsFailure() bool { return r.ResultStatus == "F" }
func (r *Result) IsUnknown() bool { return r.ResultStatus == "U" }
