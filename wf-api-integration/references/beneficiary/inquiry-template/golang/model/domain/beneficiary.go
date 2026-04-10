package domain

// FieldRestriction contains validation rules for a card template field.
type FieldRestriction struct {
	RestrictionMsg  string `json:"restrictionMsg,omitempty"`
	RestrictionRegex string `json:"restrictionRegex,omitempty"`
	RestrictionType string `json:"restrictionType,omitempty"`
}

// CardTemplateField represents a single field definition in a card template.
// Returned by inquiryBeneficiaryTemplate to indicate which fields are required
// when binding a beneficiary.
// Note: WF API returns "required" as a string ("Y"/"N"), not a boolean.
type CardTemplateField struct {
	FieldName        string           `json:"fieldName"`
	FieldDescription string           `json:"fieldDescription,omitempty"`
	Required         string           `json:"required,omitempty"`
	Restriction      *FieldRestriction `json:"restriction,omitempty"`
}

// BeneficiaryBankAccount contains bank account details for binding a beneficiary.
// Field requirements are determined by the card template returned by inquiryBeneficiaryTemplate.
type BeneficiaryBankAccount struct {
	BankAccountName      string `json:"bankAccountName,omitempty"`
	BankAccountNo        string `json:"bankAccountNo,omitempty"`
	BankName             string `json:"bankName,omitempty"`
	BankBIC              string `json:"bankBIC,omitempty"`
	BankAccountIBAN      string `json:"bankAccountIBAN,omitempty"`
	RoutingNumber        string `json:"routingNumber,omitempty"`
	BeneficiaryAddress   string `json:"beneficiaryAddress,omitempty"`
	BeneficiaryCountryCode string `json:"beneficiaryCountryCode,omitempty"`
	BeneficiaryPhone     string `json:"beneficiaryPhone,omitempty"`
	BankBranchCode       string `json:"bankBranchCode,omitempty"`
	BankLocalName        string `json:"bankLocalName,omitempty"`
	BankAccountLocalName string `json:"bankAccountLocalName,omitempty"`
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
