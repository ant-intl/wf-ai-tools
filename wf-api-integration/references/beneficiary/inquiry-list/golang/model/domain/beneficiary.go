package domain

// Beneficiary represents a bound beneficiary returned in API responses.
type Beneficiary struct {
	BeneficiaryToken       string `json:"beneficiaryToken"`
	BeneficiaryNick        string `json:"beneficiaryNick,omitempty"`
	BeneficiaryType        string `json:"beneficiaryType,omitempty"`
	Status                 string `json:"status,omitempty"`
	ReferenceBeneficiaryID string `json:"referenceBeneficiaryId,omitempty"`
}

