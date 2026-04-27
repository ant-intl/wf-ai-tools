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
	// from.value and to.value are mutually exclusive, but one must be specified
	fromHasValue := r.TransferFromDetail.TransferFromAmount.Value != nil
	toHasValue := r.TransferToDetail.TransferToAmount.Value != nil
	if fromHasValue && toHasValue {
		return fmt.Errorf("transferFromAmount.value and transferToAmount.value are mutually exclusive")
	}
	if !fromHasValue && !toHasValue {
		return fmt.Errorf("either transferFromAmount.value or transferToAmount.value must be specified")
	}
	if fromHasValue && *r.TransferFromDetail.TransferFromAmount.Value <= 0 {
		return fmt.Errorf("transferFromAmount.value must be positive when specified")
	}
	if toHasValue && *r.TransferToDetail.TransferToAmount.Value <= 0 {
		return fmt.Errorf("transferToAmount.value must be positive when specified")
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
		if m.PaymentMethodMetaData == "" {
			return fmt.Errorf("paymentMethodMetaData is required when paymentMethodType=BANK_ACCOUNT_DETAIL")
		}
		if m.PaymentMethodID != "" {
			return fmt.Errorf("paymentMethodId must not be specified when paymentMethodType=BANK_ACCOUNT_DETAIL")
		}
	case "BENEFICIARY_TOKEN":
		if m.PaymentMethodID == "" {
			return fmt.Errorf("paymentMethodId (beneficiaryToken) is required for BENEFICIARY_TOKEN mode")
		}
		if m.PaymentMethodMetaData != "" {
			return fmt.Errorf("paymentMethodMetaData must not be specified when paymentMethodType=BENEFICIARY_TOKEN")
		}
	case "ALIPAY_CN_DETAIL":
		// ALIPAY_CN_DETAIL 模式：代发到支付宝账户，paymentMethodMetaData 必传
		if m.PaymentMethodMetaData == "" {
			return fmt.Errorf("paymentMethodMetaData must not be empty when paymentMethodType=ALIPAY_CN_DETAIL")
		}
	case "REFERENCE_ALIPAY_CN":
		// REFERENCE_ALIPAY_CN 模式：代发到关联的支付宝钱包，paymentMethodId 传 referenceCustomerId
		if m.PaymentMethodID == "" {
			return fmt.Errorf("paymentMethodId (referenceCustomerId) is required for REFERENCE_ALIPAY_CN mode")
		}
	case "WALLET_ACCOUNT_DETAIL":
		// WALLET_ACCOUNT_DETAIL 模式：代发到钱包账户，paymentMethodMetaData 必传（JSON 字符串）
		if m.PaymentMethodMetaData == "" {
			return fmt.Errorf("paymentMethodMetaData is required when paymentMethodType=WALLET_ACCOUNT_DETAIL")
		}
	default:
		return fmt.Errorf("unsupported paymentMethodType: %s, expected BANK_ACCOUNT_DETAIL, BENEFICIARY_TOKEN, ALIPAY_CN_DETAIL, REFERENCE_ALIPAY_CN or WALLET_ACCOUNT_DETAIL", m.PaymentMethodType)
	}
	return nil
}
