package domain

// Address represents a physical address.
// Used in ThirdPartyIdentity for address fields.
type Address struct {
	// Region is the country/region code, ISO-3166 standard (国家/地区代码)
	Region string `json:"region"`

	// State is the province/state, max 8 characters (省/州)
	State string `json:"state,omitempty"`

	// City is the city, max 32 characters (城市)
	City string `json:"city,omitempty"`

	// Address1 is address line 1, max 128 characters (地址行1)
	Address1 string `json:"address1,omitempty"`

	// Address2 is address line 2, max 128 characters (地址行2)
	Address2 string `json:"address2,omitempty"`

	// ZipCode is the postal code, max 32 characters (邮政编码)
	ZipCode string `json:"zipCode,omitempty"`
}
