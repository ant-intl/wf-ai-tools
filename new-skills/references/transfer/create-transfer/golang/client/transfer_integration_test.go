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

func newRealTransferClient(t *testing.T) *TransferClient {
	t.Helper()
	cfg := config.NewWfConfig(wfClientID, wfBaseURL, wfPrivateKey, wfPublicKey)
	s, err := signer.NewWfSigner(wfPrivateKey, wfPublicKey)
	if err != nil {
		t.Fatalf("Failed to create signer: %v", err)
	}
	return NewTransferClient(util.NewWfHttpClient(cfg, s))
}

// TestIntegration_CreateTransfer tests account-to-account transfer
func TestIntegration_CreateTransfer(t *testing.T) {
	c := newRealTransferClient(t)

	toValue := int64(10000) // USD 100.00
	req := &request.CreateTransferRequest{
		TransferRequestID: fmt.Sprintf("test-transfer-%d", time.Now().UnixMilli()),
		TransferFromDetail: &domain.TransferFromDetail{
			TransferFromAmount: &domain.Amount{Currency: "USD"},
		},
		TransferToDetail: &domain.TransferToDetail{
			TransferToAmount: &domain.Amount{Currency: "USD", Value: &toValue},
		},
	}

	resp, err := c.CreateTransfer(req)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s\n", wfErr.Code, wfErr.Message)
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] CreateTransfer\n")
	fmt.Printf("  TransferRequestID: %s\n", resp.TransferRequestID)
	fmt.Printf("  TransferID: %s\n", resp.TransferID)
	fmt.Printf("  BusinessSceneCode: %s\n", resp.BusinessSceneCode)
	fmt.Printf("  ResultCode: %s\n", resp.Result.ResultCode)
	if resp.IsProcessing() {
		fmt.Println("  Status: PROCESSING — use inquiryTransfer to poll final status")
	}
}

// TestIntegration_CreateTransfer_MultiAccount tests main/sub account balance transfer
func TestIntegration_CreateTransfer_MultiAccount(t *testing.T) {
	c := newRealTransferClient(t)

	toValue := int64(5000) // USD 50.00
	req := &request.CreateTransferRequest{
		TransferRequestID: fmt.Sprintf("test-transfer-multi-%d", time.Now().UnixMilli()),
		BusinessSceneCode: "MULTI_ACCOUNT_TRANSFER",
		TransferFromDetail: &domain.TransferFromDetail{
			TransferFromAmount: &domain.Amount{Currency: "USD"},
		},
		TransferToDetail: &domain.TransferToDetail{
			TransferToAmount: &domain.Amount{Currency: "USD", Value: &toValue},
		},
	}

	resp, err := c.CreateTransfer(req)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s\n", wfErr.Code, wfErr.Message)
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] CreateTransfer MultiAccount\n")
	fmt.Printf("  TransferRequestID: %s\n", resp.TransferRequestID)
	fmt.Printf("  TransferID: %s\n", resp.TransferID)
	fmt.Printf("  BusinessSceneCode: %s\n", resp.BusinessSceneCode)
	fmt.Printf("  ResultCode: %s\n", resp.Result.ResultCode)
	if resp.IsProcessing() {
		fmt.Println("  Status: PROCESSING — use inquiryTransfer to poll final status")
	}
}

