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
	wfDetailClientID   = "{clientId}"
	wfDetailUserID     = "{userId}"
	wfDetailBaseURL    = "{baseUrl}" // sandbox: https://open-sitprod-sg.alipay.com
	wfDetailPrivateKey = "{privateKeyPath}"
	wfDetailPublicKey  = "{publicKeyPath}"
)

func newRealStatementDetailClient(t *testing.T) *InquiryStatementDetailClient {
	t.Helper()
	cfg := config.NewWfConfig(wfDetailClientID, wfDetailUserID, wfDetailBaseURL, wfDetailPrivateKey, wfDetailPublicKey)
	s, err := signer.NewWfSigner(wfDetailPrivateKey, wfDetailPublicKey)
	if err != nil {
		t.Fatalf("Failed to create signer: %v", err)
	}
	return NewInquiryStatementDetailClient(util.NewWfHttpClient(cfg, s))
}

// TestIntegration_InquiryStatementDetail queries statement detail from WF sandbox.
// Replace accountingBizNo with a real value obtained from inquiryStatementList.
func TestIntegration_InquiryStatementDetail(t *testing.T) {
	c := newRealStatementDetailClient(t)

	resp, err := c.InquiryStatementDetail(&request.InquiryStatementDetailRequest{
		AccountingBizNo: "YOUR_ACCOUNTING_BIZ_NO",
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
	if resp.FeeAmount != nil {
		fmt.Printf("  FeeAmount: %+v\n", resp.FeeAmount)
	}
	if resp.NetAmount != nil {
		fmt.Printf("  NetAmount: %+v\n", resp.NetAmount)
	}
	if resp.ReceiveAmount != nil {
		fmt.Printf("  ReceiveAmount: %+v\n", resp.ReceiveAmount)
	}
	if resp.FundMoveDetail != nil {
		fmt.Printf("  Payer: %s | Beneficiary: %s\n",
			resp.FundMoveDetail.PayerName, resp.FundMoveDetail.BeneficiaryName)
	}
	if resp.FailReason != nil {
		fmt.Printf("  FailReason: %s - %s\n", resp.FailReason.ResultCode, resp.FailReason.ResultMessage)
	}
	fmt.Printf("  CombinedTransactionList count: %d\n", len(resp.CombinedTransactionList))
}
