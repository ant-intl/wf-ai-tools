package domain

// StoreInfo represents store information returned by inquiryStore API
type StoreInfo struct {
	// StoreName is the store name
	StoreName string `json:"storeName,omitempty"`

	// MarketplaceName is the marketplace/platform name
	MarketplaceName string `json:"marketplaceName,omitempty"`

	// AuthorizedStatus is the store authorization status: AUTHORIZED, NEVER_AUTHORIZED
	AuthorizedStatus string `json:"authorizedStatus,omitempty"`

	// AccountInformation is the list of account info associated with the store
	AccountInformation []AccountInfo `json:"accountInformation,omitempty"`
}

// AccountInfo represents account information associated with a store
type AccountInfo struct {
	AccountNo       string        `json:"accountNo,omitempty"`
	CurrencyList    []string      `json:"currencyList"`
	AccountType     string        `json:"accountType"`
	AccountStatus   string        `json:"accountStatus"`
	BankAccountList []BankAccount `json:"bankAccountList,omitempty"`
}

// BankAccount represents bank account information
type BankAccount struct {
	BankAccountNo       string    `json:"bankAccountNo,omitempty"`
	HolderName          *UserName `json:"holderName,omitempty"`
	HolderAccountType   string    `json:"holderAccountType,omitempty"`
	HolderAddress       *Address  `json:"holderAddress,omitempty"`
	CurrencyList        []string  `json:"currencyList,omitempty"`
	BankName            string    `json:"bankName,omitempty"`
	RoutingNumber       string    `json:"routingNumber,omitempty"`
	BankAddress         *Address  `json:"bankAddress,omitempty"`
	BankRegion          string    `json:"bankRegion"`
	BankBIC             string    `json:"bankBIC,omitempty"`
	BankAccountIBAN     string    `json:"bankAccountIBAN,omitempty"`
	BankAccountBSB      string    `json:"bankAccountBSB,omitempty"`
	AccountCreationDate string    `json:"accountCreationDate,omitempty"`
	BankCode            string    `json:"bankCode,omitempty"`
	BranchCode          string    `json:"branchCode,omitempty"`
	BankAccountType     string    `json:"bankAccountType,omitempty"`
	CollectionArea      string    `json:"collectionArea,omitempty"`
	WireRoutingNumber   string    `json:"wireRoutingNumber,omitempty"`
	SortCode            string    `json:"sortCode,omitempty"`
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
