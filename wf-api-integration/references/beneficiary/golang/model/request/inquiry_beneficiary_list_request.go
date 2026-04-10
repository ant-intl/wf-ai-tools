package request

import "fmt"

// InquiryBeneficiaryListRequest queries the list of bound beneficiaries with pagination.
type InquiryBeneficiaryListRequest struct {
	// PageSize is the number of items per page, max 50 (每页条数)
	PageSize int `json:"pageSize"`

	// PageNumber is the page number, starting from 1 (页码，从1开始)
	PageNumber int `json:"pageNumber"`

	// AccessToken is the OAuth access token, max 64 characters (OAuth访问令牌)
	AccessToken string `json:"accessToken,omitempty"`

	// BeneficiaryToken is for exact match filtering (收款人令牌，精确匹配)
	BeneficiaryToken string `json:"beneficiaryToken,omitempty"`

	// BeneficiaryNick is for fuzzy match filtering (收款人昵称，模糊匹配)
	BeneficiaryNick string `json:"beneficiaryNick,omitempty"`

	// ReferenceBeneficiaryID is the integrator's custom unique ID (集成商自定义唯一ID)
	ReferenceBeneficiaryID string `json:"referenceBeneficiaryId,omitempty"`

	// BankAccountNo is for fuzzy match filtering (银行账号，模糊匹配)
	BankAccountNo string `json:"bankAccountNo,omitempty"`

	// BankAccountIBAN is the IBAN for filtering (IBAN)
	BankAccountIBAN string `json:"bankAccountIBAN,omitempty"`

	// CurrencyList is the list of currencies for filtering (币种过滤列表)
	CurrencyList []string `json:"currencyList,omitempty"`

	// AccountName is for fuzzy match filtering (账户名称，模糊匹配)
	AccountName string `json:"accountName,omitempty"`

	// AssetType is the asset type: BANK_ACCOUNT or ALIPAY_ACCOUNT (资产类型)
	AssetType string `json:"assetType,omitempty"`

	// RelationFilter is the relationship filter: SAME_NAME or THIRD_PARTY (关系过滤)
	RelationFilter []string `json:"relationFilter,omitempty"`
}

// Validate validates the InquiryBeneficiaryListRequest.
func (r *InquiryBeneficiaryListRequest) Validate() error {
	if r.PageSize <= 0 || r.PageSize > 50 {
		return fmt.Errorf("pageSize must be between 1 and 50")
	}
	if r.PageNumber <= 0 {
		return fmt.Errorf("pageNumber must be >= 1")
	}
	if r.AccessToken != "" && len(r.AccessToken) > 64 {
		return fmt.Errorf("accessToken max length is 64")
	}
	return nil
}
