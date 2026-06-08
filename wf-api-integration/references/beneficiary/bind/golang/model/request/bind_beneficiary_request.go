package request

import (
	"fmt"

	"{moduleName}/wf/model/domain"
)

// BindBeneficiaryRequest binds a new beneficiary to the WF account.
type BindBeneficiaryRequest struct {
	BindBeneficiaryRequestID string                           `json:"bindBeneficiaryRequestId"`
	BeneficiaryType          string                           `json:"beneficiaryType"`
	CountryCode              string                           `json:"countryCode,omitempty"`
	Currency                 string                           `json:"currency,omitempty"`
	BeneficiaryNick          string                           `json:"beneficiaryNick,omitempty"`
	TemplateCategory         string                           `json:"templateCategory,omitempty"`
	ReferenceBeneficiaryID   string                           `json:"referenceBeneficiaryId,omitempty"`
	BeneficiaryBankAccount   *domain.BeneficiaryBankAccount   `json:"beneficiaryBankAccount,omitempty"`
	BeneficiaryAlipayAccount *domain.BeneficiaryAlipayAccount `json:"beneficiaryAlipayAccount,omitempty"`
	ThirdPartyIdentity       *domain.ThirdPartyIdentity       `json:"thirdPartyIdentity,omitempty"`
}

func (r *BindBeneficiaryRequest) Validate() error {
	if r.BindBeneficiaryRequestID == "" {
		return fmt.Errorf("bindBeneficiaryRequestId is required")
	}
	if r.BeneficiaryType == "" {
		return fmt.Errorf("beneficiaryType is required")
	}
	return nil
}

