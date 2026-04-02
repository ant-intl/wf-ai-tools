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

func newRealPayoutClient(t *testing.T) *PayoutClient {
	t.Helper()
	cfg := config.NewWfConfig(wfClientID, wfBaseURL, wfPrivateKey, wfPublicKey)
	s, err := signer.NewWfSigner(wfPrivateKey, wfPublicKey)
	if err != nil {
		t.Fatalf("Failed to create signer: %v", err)
	}
	return NewPayoutClient(util.NewWfHttpClient(cfg, s))
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
					BankAccountName: "vaL2LTest",
					BankAccountNo:   "100100004623",
					BankName:        "STARK bankName",
					BankBIC:         "CITIHKHX",
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
