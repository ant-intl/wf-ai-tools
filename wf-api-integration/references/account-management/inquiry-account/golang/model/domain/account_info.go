package domain

// AccountInfo represents account information returned by inquiryAccount API
type AccountInfo struct {
	// 账户号码（VIRTUAL_ACCOUNT/RECEIVE_ACCOUNT 时返回）
	AccountNo string `json:"accountNo,omitempty"`

	// 账户币种列表（ISO-4217）
	CurrencyList []string `json:"currencyList"`

	// 账号类型
	AccountType string `json:"accountType"`

	// 账户状态：ACTIVE（已激活）、ABNORMAL（异常）
	AccountStatus string `json:"accountStatus"`

	// 银行账户信息（VIRTUAL_ACCOUNT 时返回）
	BankAccountList []BankAccount `json:"bankAccountList,omitempty"`
}

// BankAccount represents bank account information
type BankAccount struct {
	// 银行账号
	BankAccountNo string `json:"bankAccountNo,omitempty"`

	// 账号户主姓名
	HolderName *UserName `json:"holderName,omitempty"`

	// 户主账户类型：INDIVIDUAL/COMPANY
	HolderAccountType string `json:"holderAccountType,omitempty"`

	// 户主地址
	HolderAddress *Address `json:"holderAddress,omitempty"`

	// VA 对应币种列表（ISO-4217）
	CurrencyList []string `json:"currencyList,omitempty"`

	// 银行名称
	BankName string `json:"bankName,omitempty"`

	// 汇款路径代码（如 ABA），USD+US 时必填
	RoutingNumber string `json:"routingNumber,omitempty"`

	// 银行地址
	BankAddress *Address `json:"bankAddress,omitempty"`

	// 银行所在国家/地区（ISO-3166 二字母）
	BankRegion string `json:"bankRegion"`

	// 银行 BIC 代码（8-11 位）
	BankBIC string `json:"bankBIC,omitempty"`

	// IBAN（EUR+EU 或 GBP+GB 时必填）
	BankAccountIBAN string `json:"bankAccountIBAN,omitempty"`

	// BSB 号码（AUD+AU 或 NZD+NZ 时必填）
	BankAccountBSB string `json:"bankAccountBSB,omitempty"`

	// 账号创建时间
	AccountCreationDate string `json:"accountCreationDate,omitempty"`

	// 银行代码
	BankCode string `json:"bankCode,omitempty"`

	// 银行分行代码
	BranchCode string `json:"branchCode,omitempty"`

	// 银行账户类型：checking/saving（JPY/CAD 时必填）
	BankAccountType string `json:"bankAccountType,omitempty"`

	// 收款地区：GLOBAL/LOCAL（AUD/NZD 时必填）
	CollectionArea string `json:"collectionArea,omitempty"`

	// Wire 汇款路线号码（USD+US 时必填）
	WireRoutingNumber string `json:"wireRoutingNumber,omitempty"`

	// Sort Code（GBP+GB 或 EUR+GB 时必填）
	SortCode string `json:"sortCode,omitempty"`
}

// UserName represents a person's name
type UserName struct {
	FirstName  string `json:"firstName,omitempty"`
	MiddleName string `json:"middleName,omitempty"`
	LastName   string `json:"lastName,omitempty"`
	FullName   string `json:"fullName"`
}

// Address represents a physical address
type Address struct {
	Region   string `json:"region,omitempty"`
	State    string `json:"state,omitempty"`
	City     string `json:"city,omitempty"`
	Address1 string `json:"address1,omitempty"`
	Address2 string `json:"address2,omitempty"`
	ZipCode  string `json:"zipCode,omitempty"`
}
