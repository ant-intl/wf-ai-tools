package domain

// Beneficiary represents a bound beneficiary returned in API responses.
type Beneficiary struct {
	// BeneficiaryToken is the Base64 encrypted bank account info (收款人令牌)
	BeneficiaryToken string `json:"beneficiaryToken"`

	// BeneficiaryNick is the beneficiary nickname (收款人昵称)
	BeneficiaryNick string `json:"beneficiaryNick,omitempty"`

	// BeneficiaryType is the account type (账户类型)
	BeneficiaryType string `json:"beneficiaryType,omitempty"`

	// Status is the beneficiary status (状态)
	Status string `json:"status,omitempty"`

	// ReferenceBeneficiaryID is the integrator's custom unique ID (集成商自定义唯一ID)
	ReferenceBeneficiaryID string `json:"referenceBeneficiaryId,omitempty"`
}
