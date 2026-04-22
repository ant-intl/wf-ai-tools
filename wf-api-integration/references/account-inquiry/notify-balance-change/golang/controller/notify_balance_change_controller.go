package controller

import (
	"encoding/json"
	"fmt"
	"io"
	"log"
	"net/http"

	"{moduleName}/wf/model/request"
	"{moduleName}/wf/model/response"
	"{moduleName}/wf/signer"
)

// NotifyBalanceChangeController handles notifyBalanceChange callback notifications from WF.
//
// When a transaction occurs in the integrator's WF balance account, WF sends a
// notifyBalanceChange callback via HTTP POST. This controller is responsible for:
//  1. Verifying the request signature
//  2. Parsing the request body
//  3. Idempotency check based on notifySequence
//  4. Processing each balance change log
//  5. Signing and returning the response
//
// If no successful response is returned, WF will retry up to 7 times with intervals:
// 2min, 10min, 10min, 1h, 2h, 6h, 15h
type NotifyBalanceChangeController struct {
	Signer signer.Signer

	// OnBalanceChange is called for each balance change log in the notification.
	// Integrators should implement this callback to handle business logic
	// (e.g., update balance, record transaction, send internal notification).
	OnBalanceChange func(changeLog *request.BalanceChangeLog)
}

const (
	balanceChangeNotifyPath = "/amsin/api/v1/business/fund/notifyBalanceChange"
)

// NewNotifyBalanceChangeController creates a new NotifyBalanceChangeController with default logging callback.
func NewNotifyBalanceChangeController(s signer.Signer) *NotifyBalanceChangeController {
	return &NotifyBalanceChangeController{
		Signer: s,
		OnBalanceChange: func(changeLog *request.BalanceChangeLog) {
			log.Printf("[NotifyBalanceChangeController] balance change: accountingBizNo=%s, transactionType=%s, "+
				"accountNo=%s, amount=%d %s, balance=%d %s",
				changeLog.AccountingBizNo, changeLog.TransactionType,
				changeLog.AccountNo,
				changeLog.TransactionAmount.Value, changeLog.TransactionAmount.Currency,
				changeLog.AccountBalance.Value, changeLog.AccountBalance.Currency)
		},
	}
}

// ServeHTTP implements http.Handler, making NotifyBalanceChangeController a standard HTTP handler.
//
// Usage with net/http:
//
//	ctrl := controller.NewNotifyBalanceChangeController(wfSigner)
//	http.Handle("/notify/balance-change", ctrl)
//
// Usage with gin:
//
//	router.POST("/notify/balance-change", gin.WrapH(ctrl))
func (c *NotifyBalanceChangeController) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	log.Println("[NotifyBalanceChangeController] received notification")

	// Step 1: Read request body
	body, err := io.ReadAll(r.Body)
	if err != nil {
		log.Printf("[NotifyBalanceChangeController] failed to read request body: %v", err)
		http.Error(w, "Failed to read request body", http.StatusBadRequest)
		return
	}
	defer r.Body.Close()

	// Step 2: Extract required headers
	signatureHeader := r.Header.Get("Signature")
	clientID := r.Header.Get("Client-Id")
	requestTime := r.Header.Get("Request-Time")

	if signatureHeader == "" || clientID == "" || requestTime == "" {
		log.Println("[NotifyBalanceChangeController] missing required headers")
		http.Error(w, "Missing required headers", http.StatusBadRequest)
		return
	}

	// Step 3: Verify signature
	if err := c.Signer.VerifySignatureWithPath(balanceChangeNotifyPath, clientID, requestTime, string(body), signatureHeader); err != nil {
		log.Printf("[NotifyBalanceChangeController] signature verification failed: %v", err)
		http.Error(w, "Signature verification failed", http.StatusBadRequest)
		return
	}

	// Step 4: Parse request body
	var req request.NotifyBalanceChangeRequest
	if err := json.Unmarshal(body, &req); err != nil {
		log.Printf("[NotifyBalanceChangeController] failed to parse request body: %v", err)
		http.Error(w, "Invalid request body", http.StatusBadRequest)
		return
	}

	log.Printf("[NotifyBalanceChangeController] processing notifySequence=%d", req.NotifySequence)

	// Step 5: Idempotency check (integrators should implement their own deduplication logic)
	// TODO: Check if notifySequence has been processed before
	// if isDuplicate(req.NotifySequence) {
	//     log.Printf("[NotifyBalanceChangeController] duplicate notifySequence=%d, skip processing", req.NotifySequence)
	//     // still return success
	// }

	// Step 6: Process each balance change log
	if len(req.BalanceChangeLogs) == 0 {
		log.Printf("[NotifyBalanceChangeController] balanceChangeLogs is empty, notifySequence=%d", req.NotifySequence)
	} else {
		log.Printf("[NotifyBalanceChangeController] processing %d balance change log(s), notifySequence=%d",
			len(req.BalanceChangeLogs), req.NotifySequence)

		for i := range req.BalanceChangeLogs {
			changeLog := &req.BalanceChangeLogs[i]
			if c.OnBalanceChange != nil {
				c.OnBalanceChange(changeLog)
			}
		}
	}

	// Step 7: Build success response
	resp := response.NewSuccessResponse()
	respBody, _ := json.Marshal(resp)

	// Step 8: Sign response
	respSignature, err := c.Signer.GenerateSignatureWithPath(balanceChangeNotifyPath, clientID, c.Signer.GetRequestTime(), string(respBody))
	if err != nil {
		log.Printf("[NotifyBalanceChangeController] failed to sign response: %v", err)
		// Still return success to avoid WF retry
	}

	w.Header().Set("Content-Type", "application/json; charset=UTF-8")
	w.Header().Set("Signature", fmt.Sprintf("algorithm=RSA256, keyVersion=2, signature=%s", respSignature))
	w.WriteHeader(http.StatusOK)
	w.Write(respBody)

	log.Printf("[NotifyBalanceChangeController] processed successfully, notifySequence=%d", req.NotifySequence)
}

