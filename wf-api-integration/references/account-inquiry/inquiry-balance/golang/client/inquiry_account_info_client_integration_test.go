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
	wfBaseURL    = "{baseUrl}" // sandbox: https://open-sitprod-sg.alipay.com
	wfPrivateKey = "{privateKeyPath}"
	wfPublicKey  = "{publicKeyPath}"
)

func newRealAccountInfoClient(t *testing.T) *InquiryAccountInfoClient {
	t.Helper()
	cfg := config.NewWfConfig(wfClientID, wfUserID, wfBaseURL, wfPrivateKey, wfPublicKey)
	s, err := signer.NewWfSigner(wfPrivateKey, wfPublicKey)
	if err != nil {
		t.Fatalf("Failed to create signer: %v", err)
	}
	return NewInquiryAccountInfoClient(util.NewWfHttpClient(cfg, s))
}

// ==================== InquiryBalance Tests ====================

// TestIntegration_InquiryBalance_AllCurrencies queries all currency balances from WF sandbox
func TestIntegration_InquiryBalance_AllCurrencies(t *testing.T) {
	c := newRealAccountInfoClient(t)

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
	c := newRealAccountInfoClient(t)

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

// ==================== InquiryAvailableQuota Tests ====================

// TestIntegration_InquiryAvailableQuota_USER_ID queries quota by user ID
func TestIntegration_InquiryAvailableQuota_USER_ID(t *testing.T) {
	c := newRealAccountInfoClient(t)

	req := &request.InquiryAvailableQuotaRequest{
		QuotaAccumulationMethod: "USER_ID",
		QuotaAccumulationId:     "YOUR_USER_ID",
		Currency:                "USD",
	}

	fmt.Println("====== TestInquiryAvailableQuota_USER_ID ======")
	fmt.Printf("Request: %+v\n", req)

	resp, err := c.InquiryAvailableQuota(req)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s\n", wfErr.Code, wfErr.Message)
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] InquiryAvailableQuota\n")
	fmt.Printf("  QuotaAccumulationMethod: %s\n", resp.QuotaAccumulationMethod)
	fmt.Printf("  QuotaAccumulationId: %s\n", resp.QuotaAccumulationId)
	if resp.AvailableQuota != nil {
		fmt.Printf("  AvailableQuota: %d %s\n", resp.AvailableQuota.Value, resp.AvailableQuota.Currency)
	}
	fmt.Println("================================================")
}

// TestIntegration_InquiryAvailableQuota_BENEFICIARY queries quota by beneficiary (requires tradeType)
func TestIntegration_InquiryAvailableQuota_BENEFICIARY(t *testing.T) {
	c := newRealAccountInfoClient(t)

	req := &request.InquiryAvailableQuotaRequest{
		QuotaAccumulationMethod: "BENEFICIARY",
		QuotaAccumulationId:     "YOUR_BENEFICIARY_ID",
		Currency:                "USD",
		TradeType:               "GOODS", // GOODS or SERVICE
	}

	fmt.Println("====== TestInquiryAvailableQuota_BENEFICIARY ======")
	fmt.Printf("Request: %+v\n", req)

	resp, err := c.InquiryAvailableQuota(req)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s\n", wfErr.Code, wfErr.Message)
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] InquiryAvailableQuota\n")
	fmt.Printf("  TradeType: %s\n", resp.TradeType)
	if resp.AvailableQuota != nil {
		fmt.Printf("  AvailableQuota: %d %s\n", resp.AvailableQuota.Value, resp.AvailableQuota.Currency)
	}
	fmt.Println("=====================================================")
}
