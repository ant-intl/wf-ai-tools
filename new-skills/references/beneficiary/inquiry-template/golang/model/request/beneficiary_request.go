package request

import (
	"fmt"

	"{moduleName}/wf/model/domain"
)

// InquiryBeneficiaryTemplateRequest queries the card template for a specific
// country/currency/beneficiary type combination.
// At least one of CountryCode, Currency, or BeneficiaryType should be provided.
type InquiryBeneficiaryTemplateRequest struct {
	CountryCode     string `json:"countryCode,omitempty"`
	Currency        string `json:"currency,omitempty"`
	BeneficiaryType string `json:"beneficiaryType,omitempty"`
}

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

// InquiryBeneficiaryListRequest queries the list of bound beneficiaries with pagination.
type InquiryBeneficiaryListRequest struct {
	PageSize               int      `json:"pageSize"`
	PageNumber             int      `json:"pageNumber"`
	BeneficiaryToken       string   `json:"beneficiaryToken,omitempty"`
	BeneficiaryNick        string   `json:"beneficiaryNick,omitempty"`
	ReferenceBeneficiaryID string   `json:"referenceBeneficiaryId,omitempty"`
	BankAccountNo          string   `json:"bankAccountNo,omitempty"`
	BankAccountIBAN        string   `json:"bankAccountIBAN,omitempty"`
	CurrencyList           []string `json:"currencyList,omitempty"`
	AccountName            string   `json:"accountName,omitempty"`
	AssetType              string   `json:"assetType,omitempty"`
	RelationFilter         []string `json:"relationFilter,omitempty"`
}

func (r *InquiryBeneficiaryListRequest) Validate() error {
	if r.PageSize <= 0 || r.PageSize > 50 {
		return fmt.Errorf("pageSize must be between 1 and 50")
	}
	if r.PageNumber <= 0 {
		return fmt.Errorf("pageNumber must be >= 1")
	}
	return nil
}
