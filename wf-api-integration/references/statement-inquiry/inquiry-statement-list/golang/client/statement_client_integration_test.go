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

func newRealStatementClient(t *testing.T) *StatementClient {
	t.Helper()
	cfg := config.NewWfConfig(wfClientID, wfUserID, wfBaseURL, wfPrivateKey, wfPublicKey)
	s, err := signer.NewWfSigner(wfPrivateKey, wfPublicKey)
	if err != nil {
		t.Fatalf("Failed to create signer: %v", err)
	}
	return NewStatementClient(util.NewWfHttpClient(cfg, s))
}

// TestIntegration_InquiryStatementList queries statement list from WF sandbox
func TestIntegration_InquiryStatementList(t *testing.T) {
	c := newRealStatementClient(t)

	resp, err := c.InquiryStatementList(&request.InquiryStatementRequest{
		StartTime:  "2026-01-01T00:00:00+08:00",
		EndTime:    "2026-03-27T23:59:59+08:00",
		PageNumber: 1,
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

	fmt.Printf("[PASS] ResponseID: %s | Total: %d | Pages: %d | Current: %d\n",
		resp.ResponseID, resp.TotalCount, resp.TotalPageNumber, resp.CurrentPageNumber)
	for i, s := range resp.StatementList {
		fmt.Printf("  [%d] %s | %s | %s %s | %s\n",
			i+1, s.TransactionTime, s.TransactionType, s.Amount, s.Currency, s.Remark)
	}
}

// TestIntegration_InquiryStatementDetail queries statement detail from WF sandbox.
// It first calls InquiryStatementList to obtain a real accountingBizNo.
func TestIntegration_InquiryStatementDetail(t *testing.T) {
	c := newRealStatementClient(t)

	// Step 1: query statement list to get a real accountingBizNo
	listResp, err := c.InquiryStatementList(&request.InquiryStatementRequest{
		StartTime:  "2026-01-01T00:00:00+08:00",
		EndTime:    "2026-03-27T23:59:59+08:00",
		PageNumber: 1,
	})
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] InquiryStatementList Code: %s | Message: %s | Retryable: %v\n",
				wfErr.Code, wfErr.Message, wfErr.Code.IsRetryable())
		} else {
			fmt.Printf("[FAIL] InquiryStatementList Error: %v\n", err)
		}
		t.FailNow()
	}
	accountingBizNo := listResp.StatementList[1].AccountingBizNo

	// Step 2: query statement detail
	resp, err := c.InquiryStatementDetail(&request.InquiryStatementDetailRequest{
		AccountingBizNo: accountingBizNo,
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

	fmt.Printf("[PASS] ResponseID: %s | TransactionID: %s | Status: %s | Type: %s | Time: %s\n",
		resp.ResponseID, resp.TransactionID, resp.TransactionStatus,
		resp.TransactionType, resp.TransactionTime)

	if resp.TransactionAmount != nil {
		fmt.Printf("  TransactionAmount: %+v\n", resp.TransactionAmount)
	}
	if resp.FundMoveDetail != nil {
		fmt.Printf("  FundMoveDetail: %+v\n", resp.FundMoveDetail)
	}
	if resp.CombinedTransactionList != nil {
		fmt.Printf("  CombinedTransactionList count: %d\n", len(resp.CombinedTransactionList))
	}
}
