package client

import (
	"fmt"
	"testing"
	"time"

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

func newRealTransferClient(t *testing.T) *TransferClient {
	t.Helper()
	cfg := config.NewWfConfig(wfClientID, wfUserID, wfBaseURL, wfPrivateKey, wfPublicKey)
	s, err := signer.NewWfSigner(wfPrivateKey, wfPublicKey)
	if err != nil {
		t.Fatalf("Failed to create signer: %v", err)
	}
	return NewTransferClient(util.NewWfHttpClient(cfg, s))
}

// TestIntegration_InquiryTransfer tests querying an account-to-account transfer result
func TestIntegration_InquiryTransfer(t *testing.T) {
	c := newRealTransferClient(t)

	req := &request.InquiryTransferRequest{
		TransferRequestID: fmt.Sprintf("test-transfer-%d", time.Now().UnixMilli()),
	}

	resp, err := c.InquiryTransfer(req)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s\n", wfErr.Code, wfErr.Message)
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] InquiryTransfer\n")
	fmt.Printf("  TransferRequestID: %s\n", resp.TransferRequestID)
	fmt.Printf("  TransferID: %s\n", resp.TransferID)
	fmt.Printf("  BusinessSceneCode: %s\n", resp.BusinessSceneCode)
	fmt.Printf("  ResultCode: %s\n", resp.Result.ResultCode)
	fmt.Printf("  TransferResult.Code: %s\n", resp.TransferResult.ResultCode)
	if resp.TransferFinishTime != "" {
		fmt.Printf("  TransferFinishTime: %s\n", resp.TransferFinishTime)
	}
	if resp.IsTransferSuccess() {
		fmt.Println("  Status: SUCCESS — transfer completed")
	} else if resp.IsTransferProcessing() {
		fmt.Println("  Status: PROCESSING — continue polling with inquiryTransfer")
	} else if resp.IsTransferFailed() {
		fmt.Println("  Status: FAILED — transfer failed")
	}
}

// TestIntegration_InquiryTransfer_MultiAccount tests querying a main/sub account transfer result
func TestIntegration_InquiryTransfer_MultiAccount(t *testing.T) {
	c := newRealTransferClient(t)

	req := &request.InquiryTransferRequest{
		TransferRequestID: fmt.Sprintf("test-transfer-multi-%d", time.Now().UnixMilli()),
	}

	resp, err := c.InquiryTransfer(req)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s\n", wfErr.Code, wfErr.Message)
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] InquiryTransfer MultiAccount\n")
	fmt.Printf("  TransferRequestID: %s\n", resp.TransferRequestID)
	fmt.Printf("  TransferID: %s\n", resp.TransferID)
	fmt.Printf("  BusinessSceneCode: %s\n", resp.BusinessSceneCode)
	fmt.Printf("  ResultCode: %s\n", resp.Result.ResultCode)
	fmt.Printf("  TransferResult.Code: %s\n", resp.TransferResult.ResultCode)
	if resp.TransferFinishTime != "" {
		fmt.Printf("  TransferFinishTime: %s\n", resp.TransferFinishTime)
	}
	if resp.IsTransferSuccess() {
		fmt.Println("  Status: SUCCESS — transfer completed")
	} else if resp.IsTransferProcessing() {
		fmt.Println("  Status: PROCESSING — continue polling with inquiryTransfer")
	} else if resp.IsTransferFailed() {
		fmt.Println("  Status: FAILED — transfer failed")
	}
}
