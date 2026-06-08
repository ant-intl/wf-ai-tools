package request

import "fmt"

// EditBeneficiaryRequest updates the nick name of an existing beneficiary.
type EditBeneficiaryRequest struct {
	BeneficiaryToken string `json:"beneficiaryToken"`
	BeneficiaryNick  string `json:"beneficiaryNick"`
}

func (r *EditBeneficiaryRequest) Validate() error {
	if r.BeneficiaryToken == "" {
		return fmt.Errorf("beneficiaryToken is required")
	}
	if r.BeneficiaryNick == "" {
		return fmt.Errorf("beneficiaryNick is required")
	}
	return nil
}

