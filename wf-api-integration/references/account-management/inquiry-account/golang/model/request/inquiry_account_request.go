package request

import "fmt"

// InquiryAccountRequest represents the request for inquiryAccount API
type InquiryAccountRequest struct {
	// 查询账号类型（必填）：RECEIVE_ACCOUNT/VIRTUAL_ACCOUNT/ALIPAY_WALLET/ALIPAY_SHADOW_WALLET/ALIPAY_ORIGIN_WALLET
	AccountType string `json:"accountType"`

	// 集成商分配给注册用户的唯一用户ID（RECEIVE_ACCOUNT/ALIPAY_WALLET 时必填）
	ReferenceCustomerID string `json:"referenceCustomerId,omitempty"`

	// 万里汇账户唯一标识（ALIPAY_SHADOW_WALLET 时必填）
	AccountID string `json:"accountId,omitempty"`

	// OAuth 访问令牌（VIRTUAL_ACCOUNT 时必填）
	AccessToken string `json:"accessToken,omitempty"`
}

// validAccountTypes contains all valid accountType values
var validAccountTypes = map[string]bool{
	"RECEIVE_ACCOUNT":      true,
	"VIRTUAL_ACCOUNT":      true,
	"ALIPAY_WALLET":        true,
	"ALIPAY_SHADOW_WALLET": true,
	"ALIPAY_ORIGIN_WALLET": true,
}

// Validate validates the request parameters
func (r *InquiryAccountRequest) Validate() error {
	if r.AccountType == "" {
		return fmt.Errorf("accountType must not be blank")
	}
	if !validAccountTypes[r.AccountType] {
		return fmt.Errorf("accountType must be one of: RECEIVE_ACCOUNT, VIRTUAL_ACCOUNT, ALIPAY_WALLET, ALIPAY_SHADOW_WALLET, ALIPAY_ORIGIN_WALLET")
	}

	switch r.AccountType {
	case "RECEIVE_ACCOUNT", "ALIPAY_WALLET":
		if r.ReferenceCustomerID == "" {
			return fmt.Errorf("referenceCustomerId is required when accountType is %s", r.AccountType)
		}
	case "VIRTUAL_ACCOUNT":
		if r.AccessToken == "" {
			return fmt.Errorf("accessToken is required when accountType is VIRTUAL_ACCOUNT")
		}
	case "ALIPAY_SHADOW_WALLET":
		if r.AccountID == "" {
			return fmt.Errorf("accountId is required when accountType is ALIPAY_SHADOW_WALLET")
		}
	}

	return nil
}
