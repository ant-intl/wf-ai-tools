package response

// Result represents the standard WF API result object.
type Result struct {
	ResultStatus  string `json:"resultStatus"`
	ResultCode    string `json:"resultCode"`
	ResultMessage string `json:"resultMessage"`
}

// EditBeneficiaryResponse is the response for editBeneficiary API.
type EditBeneficiaryResponse struct {
	Result           Result `json:"result"`
	BeneficiaryToken string `json:"beneficiaryToken,omitempty"`
}

