package client

import (
	"fmt"
	"testing"

	"{moduleName}/wf/model/request"
)

// ==================== NotifyBalanceChange Handler Tests ====================

// TestHandleNotification_TransferInflow tests handling a transfer inflow notification
func TestHandleNotification_TransferInflow(t *testing.T) {
	handler := NewNotifyBalanceChangeHandler()

	// Track if callback was invoked
	callbackCount := 0
	handler.OnBalanceChange = func(changeLog *request.BalanceChangeLog) {
		callbackCount++
		fmt.Printf("  OnBalanceChange callback: accountingBizNo=%s, transactionType=%s, "+
			"amount=%d %s, balance=%d %s\n",
			changeLog.AccountingBizNo, changeLog.TransactionType,
			changeLog.TransactionAmount.Value, changeLog.TransactionAmount.Currency,
			changeLog.AccountBalance.Value, changeLog.AccountBalance.Currency)
	}

	body := []byte(`{
		"notifySequence": 1,
		"balanceChangeLogs": [{
			"balanceChangeTime": "2026-04-21T12:01:01+08:00",
			"accountNo": "2120113011000500048044119",
			"balanceType": "NORMAL_BALANCE",
			"accountingBizNo": "20260421290010101201921009001",
			"transactionAmount": {
				"currency": "USD",
				"value": 10000
			},
			"accountBalance": {
				"currency": "USD",
				"value": 50000
			},
			"transactionType": "TRANSFER",
			"transactionId": "outBiz4524ee85-49ab-4d2b-9bdb-3ee7a48001",
			"extTransactionId": "EXT20260421001",
			"beneficiaryName": "John ***",
			"beneficiaryAccountNo": "VA000000***",
			"remarks": "Test transfer"
		}]
	}`)

	fmt.Println("====== TestHandleNotification_TransferInflow ======")

	resp := handler.HandleNotification(body)

	fmt.Printf("  Response: resultStatus=%s, resultCode=%s\n",
		resp.Result.ResultStatus, resp.Result.ResultCode)

	if resp.Result.ResultStatus != "S" {
		t.Errorf("expected resultStatus=S, got %s", resp.Result.ResultStatus)
	}
	if resp.Result.ResultCode != "SUCCESS" {
		t.Errorf("expected resultCode=SUCCESS, got %s", resp.Result.ResultCode)
	}
	if callbackCount != 1 {
		t.Errorf("expected 1 callback invocation, got %d", callbackCount)
	}

	fmt.Println("====================================================")
}

// TestHandleNotification_TransferOutflow tests handling a transfer outflow notification (negative amount)
func TestHandleNotification_TransferOutflow(t *testing.T) {
	handler := NewNotifyBalanceChangeHandler()

	callbackCount := 0
	handler.OnBalanceChange = func(changeLog *request.BalanceChangeLog) {
		callbackCount++
		fmt.Printf("  OnBalanceChange callback: transactionType=%s, amount=%d %s\n",
			changeLog.TransactionType,
			changeLog.TransactionAmount.Value, changeLog.TransactionAmount.Currency)
	}

	body := []byte(`{
		"notifySequence": 2,
		"balanceChangeLogs": [{
			"balanceChangeTime": "2026-04-21T14:30:00+08:00",
			"accountNo": "2120113011000500048044119",
			"balanceType": "NORMAL_BALANCE",
			"accountingBizNo": "20260421290010101201921009002",
			"transactionAmount": {
				"currency": "EUR",
				"value": -5000
			},
			"accountBalance": {
				"currency": "EUR",
				"value": 15000
			},
			"transactionType": "WITHDRAWAL",
			"transactionId": "outBiz4524ee85-49ab-4d2b-9bdb-3ee7a48002"
		}]
	}`)

	fmt.Println("====== TestHandleNotification_TransferOutflow ======")

	resp := handler.HandleNotification(body)

	fmt.Printf("  Response: resultStatus=%s, resultCode=%s\n",
		resp.Result.ResultStatus, resp.Result.ResultCode)

	if resp.Result.ResultStatus != "S" {
		t.Errorf("expected resultStatus=S, got %s", resp.Result.ResultStatus)
	}
	if callbackCount != 1 {
		t.Errorf("expected 1 callback invocation, got %d", callbackCount)
	}

	fmt.Println("=====================================================")
}

// TestHandleNotification_MultipleChangeLogs tests handling multiple balance change logs
func TestHandleNotification_MultipleChangeLogs(t *testing.T) {
	handler := NewNotifyBalanceChangeHandler()

	callbackCount := 0
	handler.OnBalanceChange = func(changeLog *request.BalanceChangeLog) {
		callbackCount++
		fmt.Printf("  OnBalanceChange callback #%d: accountingBizNo=%s, transactionType=%s, "+
			"amount=%d %s\n",
			callbackCount, changeLog.AccountingBizNo, changeLog.TransactionType,
			changeLog.TransactionAmount.Value, changeLog.TransactionAmount.Currency)
	}

	body := []byte(`{
		"notifySequence": 3,
		"balanceChangeLogs": [
			{
				"balanceChangeTime": "2026-04-21T10:00:00+08:00",
				"accountNo": "2120113011000500048044119",
				"balanceType": "NORMAL_BALANCE",
				"accountingBizNo": "20260421290010101201921009003",
				"transactionAmount": {"currency": "HKD", "value": 29697},
				"accountBalance": {"currency": "HKD", "value": 29697},
				"transactionType": "COLLECTION",
				"beneficiaryName": "Alice ***",
				"beneficiaryAccountNo": "VA000001***"
			},
			{
				"balanceChangeTime": "2026-04-21T10:05:00+08:00",
				"accountNo": "2120113011000500048044119",
				"balanceType": "NORMAL_BALANCE",
				"accountingBizNo": "20260421290010101201921009004",
				"transactionAmount": {"currency": "HKD", "value": -1000},
				"accountBalance": {"currency": "HKD", "value": 28697},
				"transactionType": "CHARGE",
				"remarks": "Service fee"
			}
		]
	}`)

	fmt.Println("====== TestHandleNotification_MultipleChangeLogs ======")

	resp := handler.HandleNotification(body)

	fmt.Printf("  Response: resultStatus=%s, resultCode=%s\n",
		resp.Result.ResultStatus, resp.Result.ResultCode)

	if resp.Result.ResultStatus != "S" {
		t.Errorf("expected resultStatus=S, got %s", resp.Result.ResultStatus)
	}
	if callbackCount != 2 {
		t.Errorf("expected 2 callback invocations, got %d", callbackCount)
	}

	fmt.Println("========================================================")
}