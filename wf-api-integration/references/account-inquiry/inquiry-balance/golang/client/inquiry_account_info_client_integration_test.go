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

// ==================== InquiryAccount Tests ====================

// TestIntegration_InquiryAccount_ReceiveAccount queries account info by RECEIVE_ACCOUNT
func TestIntegration_InquiryAccount_ReceiveAccount(t *testing.T) {
	c := newRealAccountInfoClient(t)

	req := &request.InquiryAccountRequest{
		AccountType:         "RECEIVE_ACCOUNT",
		ReferenceCustomerID: "YOUR_CUSTOMER_ID",
	}

	fmt.Println("====== TestInquiryAccount_ReceiveAccount ======")
	fmt.Printf("Request: %+v\n", req)

	resp, err := c.InquiryAccount(req)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s | Retryable: %v\n",
				wfErr.Code, wfErr.Message, wfErr.Code.IsRetryable())
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] ResponseID: %s | AccountID: %s\n", resp.ResponseID, resp.AccountID)
	if resp.AccountInfos != nil {
		fmt.Printf("  AccountInfos count: %d\n", len(resp.AccountInfos))
		for _, info := range resp.AccountInfos {
			fmt.Printf("  - AccountNo: %s | Status: %s | Type: %s | Currencies: %v\n",
				info.AccountNo, info.AccountStatus, info.AccountType, info.CurrencyList)
		}
	}
	if resp.Customer != nil {
		fmt.Printf("  Customer: %s\n", resp.Customer.CustomerCompanyName)
	}
	fmt.Println("================================================")
}

// TestIntegration_InquiryAccount_VirtualAccount queries account info by VIRTUAL_ACCOUNT
func TestIntegration_InquiryAccount_VirtualAccount(t *testing.T) {
	c := newRealAccountInfoClient(t)

	req := &request.InquiryAccountRequest{
		AccountType: "VIRTUAL_ACCOUNT",
		AccessToken: "YOUR_ACCESS_TOKEN",
	}

	fmt.Println("====== TestInquiryAccount_VirtualAccount ======")
	fmt.Printf("Request: %+v\n", req)

	resp, err := c.InquiryAccount(req)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s\n", wfErr.Code, wfErr.Message)
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] ResponseID: %s | AccountID: %s\n", resp.ResponseID, resp.AccountID)
	if resp.AccountInfos != nil {
		for _, info := range resp.AccountInfos {
			fmt.Printf("  - AccountNo: %s | Status: %s | Currencies: %v\n",
				info.AccountNo, info.AccountStatus, info.CurrencyList)
			if info.BankAccountList != nil {
				for _, bank := range info.BankAccountList {
					fmt.Printf("    Bank: %s | Region: %s | AccountNo: %s | BIC: %s\n",
						bank.BankName, bank.BankRegion, bank.BankAccountNo, bank.BankBIC)
				}
			}
		}
	}
	fmt.Println("================================================")
}

// TestIntegration_InquiryAccount_AlipayWallet queries account info by ALIPAY_WALLET
func TestIntegration_InquiryAccount_AlipayWallet(t *testing.T) {
	c := newRealAccountInfoClient(t)

	req := &request.InquiryAccountRequest{
		AccountType:         "ALIPAY_WALLET",
		ReferenceCustomerID: "YOUR_CUSTOMER_ID",
	}

	fmt.Println("====== TestInquiryAccount_AlipayWallet ======")
	fmt.Printf("Request: %+v\n", req)

	resp, err := c.InquiryAccount(req)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s\n", wfErr.Code, wfErr.Message)
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] ResponseID: %s\n", resp.ResponseID)
	if resp.Customer != nil {
		fmt.Printf("  Customer: %s | LegalEntityType: %s\n",
			resp.Customer.CustomerCompanyName, resp.Customer.LegalEntityType)
	}
	fmt.Println("=============================================")
}

// TestIntegration_InquiryAccount_AlipayShadowWallet queries account info by ALIPAY_SHADOW_WALLET
func TestIntegration_InquiryAccount_AlipayShadowWallet(t *testing.T) {
	c := newRealAccountInfoClient(t)

	req := &request.InquiryAccountRequest{
		AccountType: "ALIPAY_SHADOW_WALLET",
		AccountID:   "YOUR_ACCOUNT_ID",
	}

	fmt.Println("====== TestInquiryAccount_AlipayShadowWallet ======")
	fmt.Printf("Request: %+v\n", req)

	resp, err := c.InquiryAccount(req)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s\n", wfErr.Code, wfErr.Message)
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] ResponseID: %s | AccountID: %s\n", resp.ResponseID, resp.AccountID)
	if resp.AffiliatedCustomer != nil {
		fmt.Printf("  AffiliatedCustomer: %s | AlipayNo: %s | Region: %s\n",
			resp.AffiliatedCustomer.CompanyName, resp.AffiliatedCustomer.AlipayNo,
			resp.AffiliatedCustomer.Region)
	}
	fmt.Println("====================================================")
}

// TestIntegration_InquiryAccount_AlipayOriginWallet queries account info by ALIPAY_ORIGIN_WALLET
func TestIntegration_InquiryAccount_AlipayOriginWallet(t *testing.T) {
	c := newRealAccountInfoClient(t)

	req := &request.InquiryAccountRequest{
		AccountType: "ALIPAY_ORIGIN_WALLET",
	}

	fmt.Println("====== TestInquiryAccount_AlipayOriginWallet ======")
	fmt.Printf("Request: %+v\n", req)

	resp, err := c.InquiryAccount(req)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s\n", wfErr.Code, wfErr.Message)
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] ResponseID: %s\n", resp.ResponseID)
	if resp.AlipayCustomer != nil {
		fmt.Printf("  AlipayCustomer: %s | AlipayNo: %s | Region: %s\n",
			resp.AlipayCustomer.CompanyName, resp.AlipayCustomer.AlipayNo,
			resp.AlipayCustomer.Region)
	}
	fmt.Println("====================================================")
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

// TestIntegration_InquiryAvailableQuota_RECEIVING_ACCOUNT queries quota by receiving account
func TestIntegration_InquiryAvailableQuota_RECEIVING_ACCOUNT(t *testing.T) {
	c := newRealAccountInfoClient(t)

	req := &request.InquiryAvailableQuotaRequest{
		QuotaAccumulationMethod: "RECEIVING_ACCOUNT",
		QuotaAccumulationId:     "YOUR_RA_NUMBER",
		Currency:                "USD",
	}

	fmt.Println("====== TestInquiryAvailableQuota_RECEIVING_ACCOUNT ======")
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
	fmt.Println("==========================================================")
}

// TestIntegration_InquiryAvailableQuota_VIRTUAL_ACCOUNT queries quota by virtual account
func TestIntegration_InquiryAvailableQuota_VIRTUAL_ACCOUNT(t *testing.T) {
	c := newRealAccountInfoClient(t)

	req := &request.InquiryAvailableQuotaRequest{
		QuotaAccumulationMethod: "VIRTUAL_ACCOUNT",
		QuotaAccumulationId:     "YOUR_VA_NUMBER",
		Currency:                "USD",
	}

	fmt.Println("====== TestInquiryAvailableQuota_VIRTUAL_ACCOUNT ======")
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
	fmt.Println("========================================================")
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
