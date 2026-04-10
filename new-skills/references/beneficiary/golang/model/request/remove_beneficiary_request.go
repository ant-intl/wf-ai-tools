package request

import "fmt"

// RemoveBeneficiaryRequest removes a bound beneficiary by token.
type RemoveBeneficiaryRequest struct {
	// RemoveBeneficiaryRequestID is the idempotent request ID, max 64 characters (幂等请求ID)
	RemoveBeneficiaryRequestID string `json:"removeBeneficiaryRequestId"`

	// AccessToken is the OAuth access token, max 64 characters (OAuth访问令牌)
	AccessToken string `json:"accessToken,omitempty"`

	// BeneficiaryToken is the beneficiary token (Base64 encrypted), max 128 characters (收款人令牌)
	BeneficiaryToken string `json:"beneficiaryToken"`
}

// Validate validates the RemoveBeneficiaryRequest.
func (r *RemoveBeneficiaryRequest) Validate() error {
	if r.RemoveBeneficiaryRequestID == "" {
		return fmt.Errorf("removeBeneficiaryRequestId is required")
	}
	if len(r.RemoveBeneficiaryRequestID) > 64 {
		return fmt.Errorf("removeBeneficiaryRequestId max length is 64")
	}
	if r.BeneficiaryToken == "" {
		return fmt.Errorf("beneficiaryToken is required")
	}
	if len(r.BeneficiaryToken) > 128 {
		return fmt.Errorf("beneficiaryToken max length is 128")
	}
	if r.AccessToken != "" && len(r.AccessToken) > 64 {
		return fmt.Errorf("accessToken max length is 64")
	}
	return nil
}
