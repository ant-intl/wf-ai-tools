package client

import (
	"fmt"
	"testing"
	"time"

	"{moduleName}/wf/config"
	"{moduleName}/wf/model/domain"
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

func newRealPayoutClient(t *testing.T) *PayoutClient {
	t.Helper()
	cfg := config.NewWfConfig(wfClientID, wfUserID, wfBaseURL, wfPrivateKey, wfPublicKey)
	s, err := signer.NewWfSigner(wfPrivateKey, wfPublicKey)
	if err != nil {
		t.Fatalf("Failed to create signer: %v", err)
	}
	return NewPayoutClient(util.NewWfHttpClient(cfg, s))
}

// TestIntegration_ConsultPayout_CrossCurrency tests exchange rate consultation for cross-currency payout
func TestIntegration_ConsultPayout_CrossCurrency(t *testing.T) {
	c := newRealPayoutClient(t)

	fromValue := int64(10000) // USD 100.00
	req := &request.ConsultPayoutRequest{
		TransferFromDetail: &domain.TransferFromDetail{
			TransferFromAmount: &domain.Amount{Currency: "USD", Value: &fromValue},
		},
		TransferToDetail: &domain.TransferToDetail{
			TransferToAmount: &domain.Amount{Currency: "CNY"}, // WF calculates CNY amount
			TransferToMethod: &domain.TransferToMethod{
				PaymentMethodType: "BANK_ACCOUNT_DETAIL",
				PaymentMethodMetaData: &domain.PaymentMethodMetaData{
					BankAccountName: "STARK bankAccountName",
					BankAccountNo:   "777777777",
					BankName:        "STARK bankName",
					BankBIC:         "HSBCHKXXXXX",
					BankCountryCode: "HK",
					BeneficiaryType: "THIRD_PARTY_PERSONAL_BANK_ACCOUNT",
				},
			},
		},
		BusinessSceneCode: "THIRD_PARTY_PAYOUT", // Required for CNY
	}

	resp, err := c.ConsultPayout(req)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s\n", wfErr.Code, wfErr.Message)
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] ConsultPayout\n")
	fmt.Printf("  QuoteID: %s\n", resp.GetQuoteID())
	fmt.Printf("  QuoteCurrencyPair: %s\n", resp.GetQuoteCurrencyPair())
	fmt.Printf("  QuotePrice: %s\n", resp.GetQuotePrice())
	fmt.Printf("  QuoteExpiryTime: %s\n", resp.GetQuoteExpiryTime())
	fmt.Printf("  ChargeMode: %s\n", resp.ChargeMode)
	if resp.AvailableQuota != nil {
		fmt.Printf("  AvailableQuota: %d %s\n", *resp.AvailableQuota.Value, resp.AvailableQuota.Currency)
	}
}

// TestIntegration_CrossCurrencyPayoutFlow tests the complete cross-currency payout flow:
// ConsultPayout -> CreatePayout
func TestIntegration_CrossCurrencyPayoutFlow(t *testing.T) {
	c := newRealPayoutClient(t)

	// Step 1: ConsultPayout to get quoteId
	fromValue := int64(10000) // USD 100.00
	consultReq := &request.ConsultPayoutRequest{
		TransferFromDetail: &domain.TransferFromDetail{
			TransferFromAmount: &domain.Amount{Currency: "USD", Value: &fromValue},
		},
		TransferToDetail: &domain.TransferToDetail{
			TransferToAmount: &domain.Amount{Currency: "CNY"},
			TransferToMethod: &domain.TransferToMethod{
				PaymentMethodType: "BANK_ACCOUNT_DETAIL",
				PaymentMethodMetaData: &domain.PaymentMethodMetaData{
					BankAccountName: "STARK bankAccountName",
					BankAccountNo:   "777777777",
					BankName:        "STARK bankName",
					BankBIC:         "HSBCHKXXXXX",
					BankCountryCode: "HK",
					BeneficiaryType: "THIRD_PARTY_PERSONAL_BANK_ACCOUNT",
				},
			},
		},
		BusinessSceneCode: "THIRD_PARTY_PAYOUT",
	}

	fmt.Println("====== Cross-Currency Payout Flow ======")
	fmt.Println("Step 1: ConsultPayout")

	consultResp, err := c.ConsultPayout(consultReq)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] ConsultPayout Code: %s | Message: %s\n", wfErr.Code, wfErr.Message)
		} else {
			fmt.Printf("[FAIL] ConsultPayout Error: %v\n", err)
		}
		t.FailNow()
	}

	quoteID := consultResp.GetQuoteID()
	fmt.Printf("[PASS] ConsultPayout - QuoteID: %s, QuotePrice: %s\n", quoteID, consultResp.GetQuotePrice())

	if quoteID == "" {
		fmt.Println("[SKIP] QuoteID is empty, skipping CreatePayout")
		return
	}

	// Step 2: CreatePayout with quoteId
	fmt.Println("Step 2: CreatePayout with QuoteID")

	createReq := &request.CreatePayoutRequest{
		TransferRequestID: fmt.Sprintf("cross-currency-%d", time.Now().UnixMilli()),
		TransferFromDetail: &domain.TransferFromDetail{
			TransferFromAmount: &domain.Amount{Currency: "USD", Value: &fromValue},
		},
		TransferToDetail: &domain.TransferToDetail{
			TransferToAmount: &domain.Amount{Currency: "CNY"},
			TransferToMethod: &domain.TransferToMethod{
				PaymentMethodType: "BANK_ACCOUNT_DETAIL",
				PaymentMethodMetaData: &domain.PaymentMethodMetaData{
					BankAccountName: "STARK bankAccountName",
					BankAccountNo:   "777777777",
					BankName:        "STARK bankName",
					BankBIC:         "HSBCHKXXXXX",
					BankCountryCode: "HK",
					BeneficiaryType: "THIRD_PARTY_PERSONAL_BANK_ACCOUNT",
				},
			},
			TransferQuote: &domain.TransferQuote{QuoteID: quoteID},
		},
		BusinessSceneCode: "THIRD_PARTY_PAYOUT",
	}

	createResp, err := c.CreatePayout(createReq)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] CreatePayout Code: %s | Message: %s\n", wfErr.Code, wfErr.Message)
		} else {
			fmt.Printf("[FAIL] CreatePayout Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] CreatePayout\n")
	fmt.Printf("  TransferRequestID: %s\n", createResp.TransferRequestID)
	fmt.Printf("  TransferID: %s\n", createResp.TransferID)
	fmt.Printf("  ResultCode: %s\n", createResp.Result.ResultCode)
	if createResp.IsProcessing() {
		fmt.Println("  Status: PROCESSING — use InquiryPayout to poll final status")
	}
	fmt.Println("=========================================")
}

// TestIntegration_CreatePayout_CardDetail tests payout with bank card details
func TestIntegration_CreatePayout_CardDetail(t *testing.T) {
	c := newRealPayoutClient(t)

	toValue := int64(100) // USD 1.00
	req := &request.CreatePayoutRequest{
		TransferRequestID: fmt.Sprintf("test-payout-%d", time.Now().UnixMilli()),
		TransferFromDetail: &domain.TransferFromDetail{
			TransferFromAmount: &domain.Amount{Currency: "USD"},
		},
		TransferToDetail: &domain.TransferToDetail{
			TransferToAmount: &domain.Amount{Currency: "USD", Value: &toValue},
			TransferToMethod: &domain.TransferToMethod{
				PaymentMethodType: "BANK_ACCOUNT_DETAIL",
				PaymentMethodMetaData: &domain.PaymentMethodMetaData{
					BankAccountName: "STARK bankAccountName",
					BankAccountNo:   "777777777",
					BankName:        "STARK bankName",
					BankBIC:         "HSBCHKXXXXX",
					BankCountryCode: "HK",
					BeneficiaryType: "THIRD_PARTY_PERSONAL_BANK_ACCOUNT",
				},
			},
		},
	}

	resp, err := c.CreatePayout(req)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s\n", wfErr.Code, wfErr.Message)
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] CreatePayout\n")
	fmt.Printf("  TransferRequestID: %s\n", resp.TransferRequestID)
	fmt.Printf("  TransferID: %s\n", resp.TransferID)
	fmt.Printf("  ChargeMode: %s\n", resp.ChargeMode)
	fmt.Printf("  ResultCode: %s\n", resp.Result.ResultCode)
	if resp.IsProcessing() {
		fmt.Println("  Status: PROCESSING — use InquiryPayout to poll final status")
	}
}

// TestIntegration_InquiryPayout_ByRequestId queries payout status by transferRequestId
func TestIntegration_InquiryPayout_ByRequestId(t *testing.T) {
	c := newRealPayoutClient(t)

	// Replace with an actual transferRequestId from a CreatePayout call
	req := &request.InquiryPayoutRequest{
		TransferRequestID: "test-payout-replace-with-real-id",
	}

	resp, err := c.InquiryPayout(req)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s\n", wfErr.Code, wfErr.Message)
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] InquiryPayout\n")
	fmt.Printf("  TransferID: %s\n", resp.TransferID)
	fmt.Printf("  TransferFinishTime: %s\n", resp.TransferFinishTime)
	if resp.TransferResult != nil {
		fmt.Printf("  TransferResult: %s / %s\n", resp.TransferResult.ResultCode, resp.TransferResult.ResultMessage)
		fmt.Printf("  Success: %v | Processing: %v\n",
			resp.TransferResult.IsSuccess(), resp.TransferResult.IsProcessing())
	}
}
