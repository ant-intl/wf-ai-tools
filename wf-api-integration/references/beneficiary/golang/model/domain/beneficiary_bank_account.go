package domain

// BeneficiaryBankAccount contains bank account details for binding a beneficiary.
// Field requirements are determined by the card template returned by inquiryBeneficiaryTemplate.
type BeneficiaryBankAccount struct {
	// BankAccountName is the account name in English (账户名称-英文)
	BankAccountName string `json:"bankAccountName,omitempty"`

	// BankAccountNo is the bank account number / card number (银行账号/卡号)
	BankAccountNo string `json:"bankAccountNo,omitempty"`

	// BankName is the bank name in English (银行名称-英文)
	BankName string `json:"bankName,omitempty"`

	// BankBIC is the Bank BIC/SWIFT code (银行BIC/SWIFT代码)
	BankBIC string `json:"bankBIC,omitempty"`

	// BankAccountIBAN is the IBAN (IBAN账号)
	BankAccountIBAN string `json:"bankAccountIBAN,omitempty"`

	// RoutingNumber is the routing number (路由号码)
	RoutingNumber string `json:"routingNumber,omitempty"`

	// BeneficiaryAddress is the beneficiary address (收款人地址)
	BeneficiaryAddress string `json:"beneficiaryAddress,omitempty"`

	// BeneficiaryCountryCode is the beneficiary country code (收款人国家代码)
	BeneficiaryCountryCode string `json:"beneficiaryCountryCode,omitempty"`

	// BankCountryCode is the bank country code (ISO-3166, 2-letter) (银行国家代码)
	BankCountryCode string `json:"bankCountryCode,omitempty"`

	// BeneficiaryPhone is the beneficiary phone (收款人电话)
	BeneficiaryPhone string `json:"beneficiaryPhone,omitempty"`

	// BankBranchCode is the bank branch code (银行分行代码)
	BankBranchCode string `json:"bankBranchCode,omitempty"`

	// BankLocalName is the bank local name (银行本地名称)
	BankLocalName string `json:"bankLocalName,omitempty"`

	// BankAccountLocalName is the account name in local language (账户名称-本地语言)
	BankAccountLocalName string `json:"bankAccountLocalName,omitempty"`
}
