package domain

// BeneficiaryAlipayAccount contains Alipay account details for binding an Alipay beneficiary.
// Required when beneficiaryType is RELATED_MERCHANT_ALIPAY_COMPANY_ACCOUNT.
type BeneficiaryAlipayAccount struct {
	// AlipayAccountName is the Alipay account name (支付宝账户名称)
	AlipayAccountName string `json:"alipayAccountName"`

	// AlipayAccountID is the Alipay account ID (支付宝账户ID)
	AlipayAccountID string `json:"alipayAccountId"`
}
