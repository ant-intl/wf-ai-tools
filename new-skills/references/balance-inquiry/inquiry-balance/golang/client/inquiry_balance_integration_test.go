package client

import (
	"fmt"
	"testing"

	"{moduleName}/wf/config"
	"{moduleName}/wf/model/exception"
	"{moduleName}/wf/model/request"
	"{moduleName}/wf/signer"
	"{moduleName}/wf/util"
)

// Fill in your WF sandbox/production credentials before running
const (
	wfClientID   = "{clientId}"
	wfUserID     = "{userId}"
	wfBaseURL    = "{baseUrl}" // sandbox: https://iopengw-sggz95m.alipay.com
	wfPrivateKey = "{privateKeyPath}"
	wfPublicKey  = "{publicKeyPath}"
)

func newRealClient(t *testing.T) *InquiryBalanceClient {
	t.Helper()
	cfg := config.NewWfConfig(wfClientID, wfUserID, wfBaseURL, wfPrivateKey, wfPublicKey)
	s, err := signer.NewWfSigner(wfPrivateKey, wfPublicKey)
	if err != nil {
		t.Fatalf("Failed to create signer: %v", err)
	}
	return NewInquiryBalanceClient(util.NewWfHttpClient(cfg, s))
}

// TestIntegration_InquiryBalance_AllCurrencies queries all currency balances from WF sandbox
func TestIntegration_InquiryBalance_AllCurrencies(t *testing.T) {
	c := newRealClient(t)

	resp, err := c.InquiryBalance(&request.InquiryBalanceRequest{
		BalanceTypes: []string{"NORMAL_BALANCE"},
	})
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s | Retryable: %v\n",
				wfErr.Code, wfErr.Message, wfErr.Code.IsRetryable())
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] ResponseID: %s | Accounts: %d\n", resp.ResponseID, len(resp.AccountBalances))
	for _, acc := range resp.AccountBalances {
		fmt.Printf("  - [%s] AccountNo: %s | Available: %d | Total: %d | Frozen: %d | Type: %s\n",
			acc.Currency, acc.AccountNo,
			acc.AvailableBalance.Value, acc.TotalBalance.Value, acc.FrozenBalance.Value,
			acc.BalanceType)
	}
}

// TestIntegration_InquiryBalance_SpecificCurrencies queries specific currency balances
func TestIntegration_InquiryBalance_SpecificCurrencies(t *testing.T) {
	c := newRealClient(t)

	resp, err := c.InquiryBalance(&request.InquiryBalanceRequest{
		CurrencyList: []string{"USD", "EUR", "GBP"},
		BalanceTypes: []string{"NORMAL_BALANCE"},
	})
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s | Retryable: %v\n",
				wfErr.Code, wfErr.Message, wfErr.Code.IsRetryable())
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] ResponseID: %s | Accounts: %d\n", resp.ResponseID, len(resp.AccountBalances))
	for _, acc := range resp.AccountBalances {
		fmt.Printf("  - [%s] AccountNo: %s | Available: %d | Total: %d | Frozen: %d\n",
			acc.Currency, acc.AccountNo,
			acc.AvailableBalance.Value, acc.TotalBalance.Value, acc.FrozenBalance.Value)
	}
}
