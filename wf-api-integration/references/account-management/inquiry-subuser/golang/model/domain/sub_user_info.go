package domain

// UserName represents a user's name
type UserName struct {
	// FirstName, max 32 chars
	FirstName string `json:"firstName,omitempty"`

	// MiddleName, max 32 chars
	MiddleName string `json:"middleName,omitempty"`

	// LastName, max 32 chars
	LastName string `json:"lastName,omitempty"`

	// FullName, max 96 chars (required)
	FullName string `json:"fullName"`
}

// Address represents a user's address
type Address struct {
	// Region is the ISO-3166 two-letter country/region code (required), max 2 chars
	Region string `json:"region"`

	// State is the province/state/county, max 8 chars
	State string `json:"state,omitempty"`

	// City is the city/district/town/village, max 32 chars
	City string `json:"city,omitempty"`

	// Address1 is the first line of address (street, PO box, company name), max 128 chars
	Address1 string `json:"address1,omitempty"`

	// Address2 is the second line of address (building, unit, door number), max 128 chars
	Address2 string `json:"address2,omitempty"`

	// ZipCode is the postal code, max 32 chars
	ZipCode string `json:"zipCode,omitempty"`
}

// SubUserInfo represents the information of a primary account or subaccount user
type SubUserInfo struct {
	// UserID is the WF user ID, max 32 chars
	UserID string `json:"userId,omitempty"`

	// UserName is the user's name (returned for primary account)
	UserName *UserName `json:"userName,omitempty"`

	// LogonID is the user's login account, max 128 chars
	LogonID string `json:"logonId,omitempty"`

	// UserNickName is the subaccount nickname (returned for subaccounts)
	UserNickName *UserName `json:"userNickName,omitempty"`

	// UserAddress is the user's address
	UserAddress *Address `json:"userAddress,omitempty"`

	// UserEmail is the user's email address, max 67 chars
	UserEmail string `json:"userEmail,omitempty"`

	// UserMobile is the user's mobile number, max 32 chars
	UserMobile string `json:"userMobile,omitempty"`
}
