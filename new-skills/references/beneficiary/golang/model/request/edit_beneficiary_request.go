package request

import "fmt"

// EditBeneficiaryRequest updates the nick name of an existing beneficiary.
type EditBeneficiaryRequest struct {
	// BeneficiaryToken is the beneficiary token (Base64 encrypted), max 128 characters (收款人令牌)
	BeneficiaryToken string `json:"beneficiaryToken"`

	// BeneficiaryNick is the new beneficiary nickname, max 70 characters (新收款人昵称)
	BeneficiaryNick string `json:"beneficiaryNick"`
}

// Validate validates the EditBeneficiaryRequest.
func (r *EditBeneficiaryRequest) Validate() error {
	if r.BeneficiaryToken == "" {
		return fmt.Errorf("beneficiaryToken is required")
	}
	if len(r.BeneficiaryToken) > 128 {
		return fmt.Errorf("beneficiaryToken max length is 128")
	}
	if r.BeneficiaryNick == "" {
		return fmt.Errorf("beneficiaryNick is required")
	}
	if len(r.BeneficiaryNick) > 70 {
		return fmt.Errorf("beneficiaryNick max length is 70")
	}
	return nil
}
