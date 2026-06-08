package domain

// BeneficiaryBankAccount contains bank account details for binding a beneficiary.
// Field requirements are determined by the card template returned by inquiryBeneficiaryTemplate.
type BeneficiaryBankAccount struct {
	BankAccountName        string `json:"bankAccountName,omitempty"`
	BankAccountNo          string `json:"bankAccountNo,omitempty"`
	BankName               string `json:"bankName,omitempty"`
	BankBIC                string `json:"bankBIC,omitempty"`
	BankAccountIBAN        string `json:"bankAccountIBAN,omitempty"`
	RoutingNumber          string `json:"routingNumber,omitempty"`
	BeneficiaryAddress     string `json:"beneficiaryAddress,omitempty"`
	BeneficiaryCountryCode string `json:"beneficiaryCountryCode,omitempty"`
	BeneficiaryPhone       string `json:"beneficiaryPhone,omitempty"`
	BankBranchCode         string `json:"bankBranchCode,omitempty"`
	BankLocalName          string `json:"bankLocalName,omitempty"`
	BankAccountLocalName   string `json:"bankAccountLocalName,omitempty"`
	BankCountryCode        string `json:"bankCountryCode,omitempty"`
}

// BeneficiaryAlipayAccount contains Alipay account details for binding an Alipay beneficiary.
type BeneficiaryAlipayAccount struct {
	AlipayAccountName string `json:"alipayAccountName"`
	AlipayAccountID   string `json:"alipayAccountId"`
}

// Address represents a physical address.
type Address struct {
	Region   string `json:"region"`
	State    string `json:"state,omitempty"`
	City     string `json:"city,omitempty"`
	Address1 string `json:"address1,omitempty"`
	Address2 string `json:"address2,omitempty"`
	ZipCode  string `json:"zipCode,omitempty"`
}

// ThirdPartyIdentity contains identity information for third-party beneficiaries.
// Required for CN/CNY third-party scenarios.
type ThirdPartyIdentity struct {
	CertificateNo string   `json:"certificateNo"`
	Address       *Address `json:"address,omitempty"`
	PhoneNumber   string   `json:"phoneNumber,omitempty"`
	Email         string   `json:"email,omitempty"`
}

// Beneficiary represents a bound beneficiary returned in API responses.
type Beneficiary struct {
	BeneficiaryToken       string `json:"beneficiaryToken"`
	BeneficiaryNick        string `json:"beneficiaryNick,omitempty"`
	BeneficiaryType        string `json:"beneficiaryType,omitempty"`
	Status                 string `json:"status,omitempty"`
	ReferenceBeneficiaryID string `json:"referenceBeneficiaryId,omitempty"`
}

