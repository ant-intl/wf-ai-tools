package config

// WfConfig holds WorldFirst API configuration.
type WfConfig struct {
	// ClientID is the WF client identifier assigned by WorldFirst.
	ClientID string
	// UserID is the WF user identifier (the login userId).
	UserID string
	// BaseURL is the API endpoint (sandbox: https://open-sitprod-sg.alipay.com, production: https://iopengw.alipay.com).
	BaseURL string
	// PrivateKeyPath is the path to the customer's PKCS#8 private key PEM file.
	PrivateKeyPath string
	// PublicKeyPath is the path to the WF RSA public key PEM file.
	PublicKeyPath string
}

// NewWfConfig creates a new WfConfig instance.
// Parameters:
//   - clientID: WF client ID
//   - userID: WF user ID (login userId)
//   - baseURL: API endpoint (sandbox: https://open-sitprod-sg.alipay.com, production: https://iopengw.alipay.com)
//   - privateKeyPath: Path to PKCS#8 private key PEM file
//   - publicKeyPath: Path to WF public key PEM file
func NewWfConfig(clientID, userID, baseURL, privateKeyPath, publicKeyPath string) *WfConfig {
	return &WfConfig{
		ClientID:       clientID,
		UserID:         userID,
		BaseURL:        baseURL,
		PrivateKeyPath: privateKeyPath,
		PublicKeyPath:  publicKeyPath,
	}
}
