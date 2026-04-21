package client

import (
	"encoding/json"
	"fmt"
	"log"

	"{moduleName}/wf/model/request"
	"{moduleName}/wf/model/response"
)

// NotifyBalanceChangeHandler handles notifyBalanceChange callback notifications from WF.
//
// When a transaction occurs in the integrator's WF balance account, WF sends a
// notifyBalanceChange callback. This handler is responsible for:
//  1. Parsing the request body
//  2. Idempotency check based on notifySequence
//  3. Processing each balance change log
//  4. Building the response to acknowledge receipt
//
// If no successful response is returned, WF will retry up to 7 times with intervals:
// 2min, 10min, 10min, 1h, 2h, 6h, 15h
type NotifyBalanceChangeHandler struct {
	// OnBalanceChange is called for each balance change log in the notification.
	// Integrators should implement this callback to handle business logic
	// (e.g., update balance, record transaction, send internal notification).
	OnBalanceChange func(changeLog *request.BalanceChangeLog)
}

// NewNotifyBalanceChangeHandler creates a new NotifyBalanceChangeHandler with default logging callback.
func NewNotifyBalanceChangeHandler() *NotifyBalanceChangeHandler {
	return &NotifyBalanceChangeHandler{
		OnBalanceChange: func(changeLog *request.BalanceChangeLog) {
			log.Printf("[NotifyBalanceChangeHandler] balance change: accountingBizNo=%s, transactionType=%s, "+
				"accountNo=%s, amount=%d %s, balance=%d %s",
				changeLog.AccountingBizNo, changeLog.TransactionType,
				changeLog.AccountNo,
				changeLog.TransactionAmount.Value, changeLog.TransactionAmount.Currency,
				changeLog.AccountBalance.Value, changeLog.AccountBalance.Currency)
		},
	}
}

// HandleNotification processes a notifyBalanceChange callback notification.
//
// The caller (e.g., HTTP handler) should:
//  1. Verify the request signature before calling this method
//  2. Pass the raw request body as []byte
//  3. Marshal the returned response and send it back to WF
//
// Returns a NotifyBalanceChangeResponse that should be sent back to WF.
func (h *NotifyBalanceChangeHandler) HandleNotification(body []byte) *response.NotifyBalanceChangeResponse {
	log.Println("[NotifyBalanceChangeHandler] received notification")

	// 1. Parse request body
	var req request.NotifyBalanceChangeRequest
	if err := json.Unmarshal(body, &req); err != nil {
		log.Printf("[NotifyBalanceChangeHandler] failed to parse request body: %v", err)
		return response.NewFailResponse("UNKNOWN_EXCEPTION",
			fmt.Sprintf("failed to parse request body: %v", err))
	}

	log.Printf("[NotifyBalanceChangeHandler] processing notifySequence=%d", req.NotifySequence)

	// 2. Idempotency check (integrators should implement their own deduplication logic)
	// TODO: Check if notifySequence has been processed before
	// if isDuplicate(req.NotifySequence) {
	//     log.Printf("[NotifyBalanceChangeHandler] duplicate notifySequence=%d, skip processing", req.NotifySequence)
	//     return response.NewSuccessResponse()
	// }

	// 3. Process each balance change log
	if len(req.BalanceChangeLogs) == 0 {
		log.Printf("[NotifyBalanceChangeHandler] balanceChangeLogs is empty, notifySequence=%d", req.NotifySequence)
		return response.NewSuccessResponse()
	}

	log.Printf("[NotifyBalanceChangeHandler] processing %d balance change log(s), notifySequence=%d",
		len(req.BalanceChangeLogs), req.NotifySequence)

	for i := range req.BalanceChangeLogs {
		changeLog := &req.BalanceChangeLogs[i]
		if h.OnBalanceChange != nil {
			h.OnBalanceChange(changeLog)
		}
	}

	// 4. Mark as processed (integrators should implement their own persistence logic)
	// TODO: markAsProcessed(req.NotifySequence)

	// 5. Return success response
	log.Printf("[NotifyBalanceChangeHandler] processed successfully, notifySequence=%d", req.NotifySequence)
	return response.NewSuccessResponse()
}
