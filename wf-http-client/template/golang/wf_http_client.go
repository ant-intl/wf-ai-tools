package util

import (
	"bytes"
	"fmt"
	"io"
	"net/http"
	"time"

	"{moduleName}/wf/config"
	"{moduleName}/wf/signer"
)

// WfHttpClient handles all HTTP communication with WF API.
// Responsible for: signing, setting headers, sending request, returning raw response body.
// Business logic (request building, response parsing) stays in each API client.
type WfHttpClient struct {
	config     *config.WfConfig
	signer     signer.Signer
	httpClient *http.Client
}

// NewWfHttpClient creates a new WfHttpClient.
func NewWfHttpClient(cfg *config.WfConfig, s signer.Signer) *WfHttpClient {
	return &WfHttpClient{
		config:     cfg,
		signer:     s,
		httpClient: &http.Client{Timeout: 30 * time.Second},
	}
}

// PostJSON sends a signed POST request to the given API path with a JSON body.
// Returns the raw response body bytes.
func (c *WfHttpClient) PostJSON(apiPath string, bodyBytes []byte) ([]byte, error) {
	requestTime := c.signer.GetRequestTime()
	signature, err := c.signer.GenerateSignatureWithPath(apiPath, c.config.ClientID, requestTime, string(bodyBytes))
	if err != nil {
		return nil, fmt.Errorf("failed to generate signature: %w", err)
	}

	url := c.config.BaseURL + apiPath
	httpReq, err := http.NewRequest(http.MethodPost, url, bytes.NewBuffer(bodyBytes))
	if err != nil {
		return nil, fmt.Errorf("failed to create request: %w", err)
	}

	httpReq.Header.Set("Content-Type", "application/json; charset=UTF-8")
	httpReq.Header.Set("Client-Id", c.config.ClientID)
	httpReq.Header.Set("Request-Time", requestTime)
	httpReq.Header.Set("Signature", fmt.Sprintf("algorithm=RSA256, keyVersion=2, signature=%s", signature))

	httpResp, err := c.httpClient.Do(httpReq)
	if err != nil {
		return nil, fmt.Errorf("HTTP request failed: %w", err)
	}
	defer httpResp.Body.Close()

	respBody, err := io.ReadAll(httpResp.Body)
	if err != nil {
		return nil, fmt.Errorf("failed to read response body: %w", err)
	}

	return respBody, nil
}
