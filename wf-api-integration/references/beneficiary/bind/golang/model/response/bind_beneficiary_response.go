package response

import (
	"{moduleName}/wf/model/domain"
)

// Result represents the standard WF API result object.
type Result struct {
	ResultStatus  string `json:"resultStatus"`
	ResultCode    string `json:"resultCode"`
	ResultMessage string `json:"resultMessage"`
}

// BindBeneficiaryResponse is the response for bindBeneficiary API.
type BindBeneficiaryResponse struct {
	Result      Result              `json:"result"`
	Beneficiary *domain.Beneficiary `json:"beneficiary,omitempty"`
}

