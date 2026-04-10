package response

import (
	"{moduleName}/wf/model/domain"
)

// BindBeneficiaryResponse is the response for bindBeneficiary API.
// Contains the bound beneficiary information including beneficiaryToken.
type BindBeneficiaryResponse struct {
	// Result is the API call result (接口调用结果)
	Result Result `json:"result"`

	// Beneficiary contains the bound beneficiary info (绑定的收款人信息)
	Beneficiary *domain.Beneficiary `json:"beneficiary,omitempty"`
}

// IsSuccess returns true if the API call was successful.
func (r *BindBeneficiaryResponse) IsSuccess() bool {
	return r.Result.IsSuccess()
}
