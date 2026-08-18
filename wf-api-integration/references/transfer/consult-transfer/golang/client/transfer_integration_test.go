package client

import (
	"fmt"
	"testing"

	"{moduleName}/wf/config"
	"{moduleName}/wf/model/domain"
	"{moduleName}/wf/model/exception"
	"{moduleName}/wf/model/request"
	"{moduleName}/wf/signer"
	"{moduleName}/wf/util"
)

// Fill in your WF credentials before running
const (
	wfClientID   = "{clientId}"
	wfUserID     = "{userId}"
	wfBaseURL    = "{baseUrl}" // https://open-sea.worldfirst.com
	wfPrivateKey = "{privateKeyPath}"
	wfPublicKey  = "{publicKeyPath}"
)

func newRealTransferClient(t *testing.T) *TransferClient {
	t.Helper()
	cfg := config.NewWfConfig(wfClientID, wfUserID, wfBaseURL, wfPrivateKey, wfPublicKey)
	s, err := signer.NewWfSigner(wfPrivateKey, wfPublicKey)
	if err != nil {
		t.Fatalf("Failed to create signer: %v", err)
	}
	return NewTransferClient(util.NewWfHttpClient(cfg, s))
}

// TestIntegration_ConsultTransfer tests same-currency transfer consultation
func TestIntegration_ConsultTransfer(t *testing.T) {
	c := newRealTransferClient(t)

	toValue := int64(10000) // USD 100.00
	req := &request.ConsultTransferRequest{
		TransferFromDetail: &domain.TransferFromDetail{
			TransferFromAmount: &domain.Amount{Currency: "USD"},
		},
		TransferToDetail: &domain.TransferToDetail{
			TransferToAmount: &domain.Amount{Currency: "USD", Value: &toValue},
		},
	}

	resp, err := c.ConsultTransfer(req)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s\n", wfErr.Code, wfErr.Message)
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] ConsultTransfer\n")
	fmt.Printf("  ResultCode: %s\n", resp.Result.ResultCode)
	if resp.TransferFromDetail != nil && resp.TransferFromDetail.TransferFromAmount != nil {
		fmt.Printf("  FromCurrency: %s\n", resp.TransferFromDetail.TransferFromAmount.Currency)
	}
	if resp.TransferToDetail != nil && resp.TransferToDetail.TransferToAmount != nil {
		fmt.Printf("  ToCurrency: %s\n", resp.TransferToDetail.TransferToAmount.Currency)
	}
}

// TestIntegration_ConsultTransfer_CrossCurrency tests cross-currency transfer consultation
func TestIntegration_ConsultTransfer_CrossCurrency(t *testing.T) {
	c := newRealTransferClient(t)

	toValue := int64(5000) // GBP 50.00
	req := &request.ConsultTransferRequest{
		TransferFromDetail: &domain.TransferFromDetail{
			TransferFromAmount: &domain.Amount{Currency: "USD"},
		},
		TransferToDetail: &domain.TransferToDetail{
			TransferToAmount: &domain.Amount{Currency: "GBP", Value: &toValue},
		},
	}

	resp, err := c.ConsultTransfer(req)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s\n", wfErr.Code, wfErr.Message)
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] ConsultTransfer CrossCurrency\n")
	fmt.Printf("  ResultCode: %s\n", resp.Result.ResultCode)
	if resp.TransferToDetail != nil && resp.TransferToDetail.TransferQuote != nil {
		fmt.Printf("  QuoteId: %s\n", resp.TransferToDetail.TransferQuote.QuoteId)
		fmt.Printf("  QuoteCurrencyPair: %s\n", resp.TransferToDetail.TransferQuote.QuoteCurrencyPair)
		fmt.Printf("  QuotePrice: %s\n", resp.TransferToDetail.TransferQuote.QuotePrice)
	}
	if resp.TransferFromDetail != nil && resp.TransferFromDetail.FeeAmount != nil {
		fmt.Printf("  FeeCurrency: %s\n", resp.TransferFromDetail.FeeAmount.Currency)
		if resp.TransferFromDetail.FeeAmount.Value != nil {
			fmt.Printf("  FeeValue: %d\n", *resp.TransferFromDetail.FeeAmount.Value)
		}
	}
}
