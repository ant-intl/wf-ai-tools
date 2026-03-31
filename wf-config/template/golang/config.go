package config

// WfConfig holds WorldFirst API configuration.
type WfConfig struct {
	ClientID       string
	BaseURL        string
	PrivateKeyPath string
	PublicKeyPath  string
}

// NewWfConfig creates a new WfConfig instance.
// Parameters:
//   - clientID: WF client ID
//   - baseURL: API endpoint (sandbox: https://iopengw-sggz95m.alipay.com, production: https://iopengw.alipay.com)
//   - privateKeyPath: Path to PKCS#8 private key PEM file
//   - publicKeyPath: Path to WF public key PEM file
func NewWfConfig(clientID, baseURL, privateKeyPath, publicKeyPath string) *WfConfig {
	return &WfConfig{
		ClientID:       clientID,
		BaseURL:        baseURL,
		PrivateKeyPath: privateKeyPath,
		PublicKeyPath:  publicKeyPath,
	}
}
