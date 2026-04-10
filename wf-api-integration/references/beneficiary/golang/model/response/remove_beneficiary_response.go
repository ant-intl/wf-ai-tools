package response

// RemoveBeneficiaryResponse is the response for removeBeneficiary API.
type RemoveBeneficiaryResponse struct {
	// Result is the API call result (接口调用结果)
	Result Result `json:"result"`

	// BeneficiaryToken is the removed beneficiary token (被删除的收款人令牌)
	BeneficiaryToken string `json:"beneficiaryToken,omitempty"`
}

// IsSuccess returns true if the API call was successful.
func (r *RemoveBeneficiaryResponse) IsSuccess() bool {
	return r.Result.IsSuccess()
}
