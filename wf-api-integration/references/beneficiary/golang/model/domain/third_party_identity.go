package domain

// ThirdPartyIdentity contains identity information for third-party beneficiaries.
// Required for CN/CNY third-party scenarios when beneficiaryType is
// THIRD_PARTY_PERSONAL_BANK_ACCOUNT or THIRD_PARTY_COMPANY_BANK_ACCOUNT.
type ThirdPartyIdentity struct {
	// CertificateNo is the certificate number:
	// - Personal: ID card number (身份证号)
	// - Company: Business license number (营业执照号)
	CertificateNo string `json:"certificateNo"`

	// Address is the address information (地址信息)
	Address *Address `json:"address,omitempty"`

	// PhoneNumber is the phone number (电话号码)
	PhoneNumber string `json:"phoneNumber,omitempty"`

	// Email is the email address (邮箱地址)
	Email string `json:"email,omitempty"`
}
