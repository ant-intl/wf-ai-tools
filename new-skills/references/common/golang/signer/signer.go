package signer

import (
	"crypto"
	"crypto/rand"
	"crypto/rsa"
	"crypto/sha256"
	"crypto/x509"
	"encoding/base64"
	"encoding/pem"
	"fmt"
	"net/url"
	"os"
	"time"
)

// Signer is the interface for WF API signing.
// Using an interface allows for mock injection in tests.
type Signer interface {
	GenerateSignatureWithPath(apiPath, clientID, requestTime, body string) (string, error)
	GetRequestTime() string
}

// WfSigner handles RSA256 signing for WorldFirst API requests.
type WfSigner struct {
	privateKey *rsa.PrivateKey
	publicKey  *rsa.PublicKey
}

// NewWfSigner creates a new signer with key file paths.
// Both keys must be in PEM format. Private key must be PKCS#8.
func NewWfSigner(privateKeyPath, publicKeyPath string) (*WfSigner, error) {
	privateKey, err := loadPrivateKey(privateKeyPath)
	if err != nil {
		return nil, fmt.Errorf("failed to load private key: %w", err)
	}

	publicKey, err := loadPublicKey(publicKeyPath)
	if err != nil {
		return nil, fmt.Errorf("failed to load public key: %w", err)
	}

	return &WfSigner{
		privateKey: privateKey,
		publicKey:  publicKey,
	}, nil
}

// GenerateSignatureWithPath generates RSA256 signature with explicit API path.
// Content format: "POST {apiPath}\n{clientId}.{requestTime}.{requestBody}"
// Returns URL-encoded Base64 signature.
func (s *WfSigner) GenerateSignatureWithPath(apiPath, clientID, requestTime, body string) (string, error) {
	content := fmt.Sprintf("POST %s\n%s.%s.%s", apiPath, clientID, requestTime, body)
	hashed := sha256.Sum256([]byte(content))
	signatureBytes, err := rsa.SignPKCS1v15(rand.Reader, s.privateKey, crypto.SHA256, hashed[:])
	if err != nil {
		return "", fmt.Errorf("failed to sign: %w", err)
	}
	base64Sig := base64.StdEncoding.EncodeToString(signatureBytes)
	return url.QueryEscape(base64Sig), nil
}

// VerifySignatureWithPath verifies response signature with explicit API path.
// Content format: "POST {apiPath}\n{clientId}.{responseTime}.{responseBody}"
func (s *WfSigner) VerifySignatureWithPath(apiPath, clientID, responseTime, body, signatureHeader string) error {
	// Extract signature= value from header
	var base64Sig string
	for _, part := range splitHeader(signatureHeader) {
		if len(part) > 10 && part[:10] == "signature=" {
			base64Sig = part[10:]
			break
		}
	}
	if base64Sig == "" {
		return fmt.Errorf("no signature value found in header")
	}

	// URL-decode
	decoded, err := url.QueryUnescape(base64Sig)
	if err != nil {
		return fmt.Errorf("failed to url-decode signature: %w", err)
	}

	sigBytes, err := base64.StdEncoding.DecodeString(decoded)
	if err != nil {
		return fmt.Errorf("failed to base64-decode signature: %w", err)
	}

	content := fmt.Sprintf("POST %s\n%s.%s.%s", apiPath, clientID, responseTime, body)
	hashed := sha256.Sum256([]byte(content))

	err = rsa.VerifyPKCS1v15(s.publicKey, crypto.SHA256, hashed[:], sigBytes)
	if err != nil {
		return fmt.Errorf("signature verification failed: %w", err)
	}
	return nil
}

// GetRequestTime returns ISO 8601 formatted time with timezone.
func (s *WfSigner) GetRequestTime() string {
	return time.Now().Format("2006-01-02T15:04:05-07:00")
}

func splitHeader(header string) []string {
	var parts []string
	for _, p := range splitComma(header) {
		parts = append(parts, trimSpace(p))
	}
	return parts
}

func splitComma(s string) []string {
	var result []string
	start := 0
	for i := 0; i < len(s); i++ {
		if s[i] == ',' {
			result = append(result, s[start:i])
			start = i + 1
		}
	}
	result = append(result, s[start:])
	return result
}

func trimSpace(s string) string {
	start, end := 0, len(s)
	for start < end && (s[start] == ' ' || s[start] == '\t') {
		start++
	}
	for end > start && (s[end-1] == ' ' || s[end-1] == '\t') {
		end--
	}
	return s[start:end]
}

func loadPrivateKey(path string) (*rsa.PrivateKey, error) {
	data, err := os.ReadFile(path)
	if err != nil {
		return nil, err
	}

	block, _ := pem.Decode(data)
	if block == nil {
		return nil, fmt.Errorf("failed to decode PEM block")
	}

	key, err := x509.ParsePKCS8PrivateKey(block.Bytes)
	if err != nil {
		return nil, err
	}

	rsaKey, ok := key.(*rsa.PrivateKey)
	if !ok {
		return nil, fmt.Errorf("not an RSA private key")
	}
	return rsaKey, nil
}

func loadPublicKey(path string) (*rsa.PublicKey, error) {
	data, err := os.ReadFile(path)
	if err != nil {
		return nil, err
	}

	block, _ := pem.Decode(data)
	if block == nil {
		return nil, fmt.Errorf("failed to decode PEM block")
	}

	key, err := x509.ParsePKIXPublicKey(block.Bytes)
	if err != nil {
		return nil, err
	}

	rsaKey, ok := key.(*rsa.PublicKey)
	if !ok {
		return nil, fmt.Errorf("not an RSA public key")
	}
	return rsaKey, nil
}
