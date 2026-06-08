package request

import "fmt"

// RemoveBeneficiaryRequest removes a bound beneficiary by token.
type RemoveBeneficiaryRequest struct {
	RemoveBeneficiaryRequestID string `json:"removeBeneficiaryRequestId"`
	BeneficiaryToken           string `json:"beneficiaryToken"`
}

func (r *RemoveBeneficiaryRequest) Validate() error {
	if r.RemoveBeneficiaryRequestID == "" {
		return fmt.Errorf("removeBeneficiaryRequestId is required")
	}
	if r.BeneficiaryToken == "" {
		return fmt.Errorf("beneficiaryToken is required")
	}
	return nil
}

