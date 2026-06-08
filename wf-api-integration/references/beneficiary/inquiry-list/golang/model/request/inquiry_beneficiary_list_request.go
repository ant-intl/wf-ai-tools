package request

import "fmt"

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

