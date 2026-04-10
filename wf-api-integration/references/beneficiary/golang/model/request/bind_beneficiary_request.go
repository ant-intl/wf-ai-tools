package request

import (
	"fmt"

	"{moduleName}/wf/model/domain"
)

// BindBeneficiaryRequest binds a new beneficiary to the WF account.
type BindBeneficiaryRequest struct {
	// BindBeneficiaryRequestID is the idempotent request ID, max 64 characters (幂等请求ID)
	BindBeneficiaryRequestID string `json:"bindBeneficiaryRequestId"`

	// AccessToken is the OAuth access token, max 64 characters (OAuth访问令牌)
	AccessToken string `json:"accessToken,omitempty"`

	// BeneficiaryType is the account type (账户类型)
	BeneficiaryType string `json:"beneficiaryType"`

	// BeneficiaryBankAccount contains bank account details (银行账户信息)
	// Fill according to card template fields.
	BeneficiaryBankAccount *domain.BeneficiaryBankAccount `json:"beneficiaryBankAccount,omitempty"`

	// BeneficiaryAlipayAccount contains Alipay account details (支付宝账户信息)
	BeneficiaryAlipayAccount *domain.BeneficiaryAlipayAccount `json:"beneficiaryAlipayAccount,omitempty"`

	// ThirdPartyIdentity contains third-party identity info (三方身份信息)
	// Required for CN/CNY third-party scenarios.
	ThirdPartyIdentity *domain.ThirdPartyIdentity `json:"thirdPartyIdentity,omitempty"`

	// CountryCode is the bank country code, ISO-3166 2-letter (银行国家代码)
	CountryCode string `json:"countryCode,omitempty"`

	// Currency is the currency code, ISO-4217 3-letter (币种代码)
	Currency string `json:"currency,omitempty"`

	// BeneficiaryNick is the beneficiary nickname, max 70 characters (收款人昵称)
	BeneficiaryNick string `json:"beneficiaryNick,omitempty"`

	// TemplateCategory is the template type:
	// GENERAL_TEMPLATE(default), LOCAL_TEMPLATE, CROSS_BORDER_TEMPLATE (模版类型)
	TemplateCategory string `json:"templateCategory,omitempty"`

	// ReferenceBeneficiaryID is the integrator's custom unique ID, max 64 characters (集成商自定义唯一ID)
	ReferenceBeneficiaryID string `json:"referenceBeneficiaryId,omitempty"`
}

// Validate validates the BindBeneficiaryRequest.
func (r *BindBeneficiaryRequest) Validate() error {
	if r.BindBeneficiaryRequestID == "" {
		return fmt.Errorf("bindBeneficiaryRequestId is required")
	}
	if len(r.BindBeneficiaryRequestID) > 64 {
		return fmt.Errorf("bindBeneficiaryRequestId max length is 64")
	}
	if r.BeneficiaryType == "" {
		return fmt.Errorf("beneficiaryType is required")
	}
	if r.AccessToken != "" && len(r.AccessToken) > 64 {
		return fmt.Errorf("accessToken max length is 64")
	}
	if r.BeneficiaryNick != "" && len(r.BeneficiaryNick) > 70 {
		return fmt.Errorf("beneficiaryNick max length is 70")
	}
	if r.ReferenceBeneficiaryID != "" && len(r.ReferenceBeneficiaryID) > 64 {
		return fmt.Errorf("referenceBeneficiaryId max length is 64")
	}
	return nil
}
