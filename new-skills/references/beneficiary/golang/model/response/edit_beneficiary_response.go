package response

// EditBeneficiaryResponse is the response for editBeneficiary API.
type EditBeneficiaryResponse struct {
	// Result is the API call result (接口调用结果)
	Result Result `json:"result"`

	// BeneficiaryToken is the edited beneficiary token (编辑的收款人令牌)
	BeneficiaryToken string `json:"beneficiaryToken,omitempty"`
}

// IsSuccess returns true if the API call was successful.
func (r *EditBeneficiaryResponse) IsSuccess() bool {
	return r.Result.IsSuccess()
}
