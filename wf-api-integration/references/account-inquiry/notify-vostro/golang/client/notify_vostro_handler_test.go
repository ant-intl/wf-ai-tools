package client

import (
	"fmt"
	"testing"

	"{moduleName}/wf/model/request"
)

// ==================== NotifyVostro Handler Tests ====================

// TestHandleNotification_FundingSuccess tests handling a funding success notification
func TestHandleNotification_FundingSuccess(t *testing.T) {
	handler := NewNotifyVostroHandler()

	// Track if callback was invoked
	callbackInvoked := false
	handler.OnFundingSuccess = func(req *request.NotifyVostroRequest) {
		callbackInvoked = true
		fmt.Printf("  OnFundingSuccess callback: fundingId=%s, amount=%d %s, beneficiary=%s\n",
			req.FundingID,
			req.BalanceChangeAmount.Value, req.BalanceChangeAmount.Currency,
			req.BeneficiaryAccount.BeneficiaryBankAccountNo)
	}

	body := []byte(`{
		"fundingId": "20260421000000001",
		"balanceResult": {
			"resultCode": "SUCCESS",
			"resultStatus": "S",
			"resultMessage": "Success"
		},
		"payerBankAccount": {
			"payerBankAccountNo": "622848*****1234",
			"payerBankName": "Bank of China"
		},
		"beneficiaryAccount": {
			"beneficiaryBankAccountNo": "VA0000001234567890"
		},
		"balanceChangeAmount": {
			"currency": "USD",
			"value": 10000
		},
		"balanceChangeTime": "2026-04-21T12:01:01+08:00",
		"remitInfo": "Test funding"
	}`)

	fmt.Println("====== TestHandleNotification_FundingSuccess ======")

	resp := handler.HandleNotification(body)

	fmt.Printf("  Response: resultStatus=%s, resultCode=%s\n",
		resp.Result.ResultStatus, resp.Result.ResultCode)

	if resp.Result.ResultStatus != "S" {
		t.Errorf("expected resultStatus=S, got %s", resp.Result.ResultStatus)
	}
	if resp.Result.ResultCode != "SUCCESS" {
		t.Errorf("expected resultCode=SUCCESS, got %s", resp.Result.ResultCode)
	}
	if !callbackInvoked {
		t.Error("expected OnFundingSuccess callback to be invoked")
	}

	fmt.Println("====================================================")
}

// TestHandleNotification_RefundSuccess tests handling a refund success notification
func TestHandleNotification_RefundSuccess(t *testing.T) {
	handler := NewNotifyVostroHandler()

	callbackInvoked := false
	handler.OnRefundSuccess = func(req *request.NotifyVostroRequest) {
		callbackInvoked = true
		fmt.Printf("  OnRefundSuccess callback: fundingId=%s, amount=%d %s\n",
			req.FundingID,
			req.BalanceChangeAmount.Value, req.BalanceChangeAmount.Currency)
	}

	body := []byte(`{
		"fundingId": "20260421000000002",
		"balanceResult": {
			"resultCode": "REFUND",
			"resultStatus": "S",
			"resultMessage": "Refund Success"
		},
		"payerBankAccount": {
			"payerBankAccountNo": "622848*****5678",
			"payerBankName": "ICBC"
		},
		"beneficiaryAccount": {
			"beneficiaryBankAccountNo": "VA0000009876543210"
		},
		"balanceChangeAmount": {
			"currency": "EUR",
			"value": 5000
		},
		"balanceChangeTime": "2026-04-21T14:30:00+08:00"
	}`)

	fmt.Println("====== TestHandleNotification_RefundSuccess ======")

	resp := handler.HandleNotification(body)

	fmt.Printf("  Response: resultStatus=%s, resultCode=%s\n",
		resp.Result.ResultStatus, resp.Result.ResultCode)

	if resp.Result.ResultStatus != "S" {
		t.Errorf("expected resultStatus=S, got %s", resp.Result.ResultStatus)
	}
	if !callbackInvoked {
		t.Error("expected OnRefundSuccess callback to be invoked")
	}

	fmt.Println("===================================================")
}
