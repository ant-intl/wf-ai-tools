package request

import (
	"fmt"

	"{moduleName}/wf/model/domain"
)

// CreatePayoutRequest represents the request for createPayout API
type CreatePayoutRequest struct {
	TransferRequestID  string                    `json:"transferRequestId"`
	TransferFromDetail *domain.TransferFromDetail `json:"transferFromDetail"`
	TransferToDetail   *domain.TransferToDetail   `json:"transferToDetail"`
	BusinessSceneCode  string                    `json:"businessSceneCode,omitempty"`
}

// Validate validates the request parameters
func (r *CreatePayoutRequest) Validate() error {
	if r.TransferRequestID == "" {
		return fmt.Errorf("transferRequestId is required")
	}
	if len(r.TransferRequestID) > 64 {
		return fmt.Errorf("transferRequestId max length is 64")
	}
	if r.TransferFromDetail == nil || r.TransferFromDetail.TransferFromAmount == nil ||
		r.TransferFromDetail.TransferFromAmount.Currency == "" {
		return fmt.Errorf("transferFromDetail.transferFromAmount.currency is required")
	}
	if r.TransferToDetail == nil || r.TransferToDetail.TransferToAmount == nil ||
		r.TransferToDetail.TransferToAmount.Currency == "" {
		return fmt.Errorf("transferToDetail.transferToAmount.currency is required")
	}
	// from.value and to.value cannot both be set
	fromHasValue := r.TransferFromDetail.TransferFromAmount.Value != nil
	toHasValue := r.TransferToDetail.TransferToAmount.Value != nil
	if fromHasValue && toHasValue {
		return fmt.Errorf("transferFromAmount.value and transferToAmount.value cannot both be set")
	}
	// CNY requires businessSceneCode
	if r.TransferToDetail.TransferToAmount.Currency == "CNY" && r.BusinessSceneCode == "" {
		return fmt.Errorf("businessSceneCode is required when transferToAmount.currency is CNY")
	}
	// Default purposeCode to GDS
	if r.TransferToDetail.PurposeCode == "" {
		r.TransferToDetail.PurposeCode = "GDS"
	}
	// Validate transferToMethod
	if r.TransferToDetail.TransferToMethod == nil {
		return fmt.Errorf("transferToDetail.transferToMethod is required")
	}
	m := r.TransferToDetail.TransferToMethod
	switch m.PaymentMethodType {
	case "BANK_ACCOUNT_DETAIL":
		if m.PaymentMethodMetaData == nil || m.PaymentMethodMetaData.BankAccountNo == "" {
			return fmt.Errorf("paymentMethodMetaData.bankAccountNo is required for BANK_ACCOUNT_DETAIL mode")
		}
	case "BENEFICIARY_TOKEN":
		if m.PaymentMethodID == "" {
			return fmt.Errorf("paymentMethodId (beneficiaryToken) is required for BENEFICIARY_TOKEN mode")
		}
	default:
		return fmt.Errorf("unsupported paymentMethodType: %s", m.PaymentMethodType)
	}
	return nil
}
