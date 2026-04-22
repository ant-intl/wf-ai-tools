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

// NotifyPayoutController handles notifyPayout callback notifications from WF.
//
// After a transfer is completed, WF sends a notifyPayout callback via HTTP POST
// to the URL configured in createPayout's transferToDetail.transferNotifyUrl.
// This controller is responsible for:
//  1. Verifying the request signature
//  2. Parsing the request body
//  3. Idempotency check based on transferRequestId
//  4. Processing transfer result
//  5. Signing and returning the response
//
// If no successful response is returned, WF will retry up to 7 times with intervals:
// 2min, 10min, 10min, 1h, 2h, 6h, 15h
type NotifyPayoutController struct {
	Signer signer.Signer

	// OnTransferSuccess is called when a transfer success notification is received.
	// Integrators should implement this callback to handle business logic
	// (e.g., update local order status, record settlement amount).
	OnTransferSuccess func(req *request.NotifyPayoutRequest)

	// OnTransferFail is called when a transfer failure notification is received.
	// Integrators should implement this callback to handle failure logic
	// (e.g., update order status to failed, notify user).
	OnTransferFail func(req *request.NotifyPayoutRequest)
}

const (
	payoutNotifyPath = "/amsin/api/v1/business/fund/notifyPayout"
)

// NewNotifyPayoutController creates a new NotifyPayoutController with default no-op callbacks.
func NewNotifyPayoutController(s signer.Signer) *NotifyPayoutController {
	return &NotifyPayoutController{
		Signer: s,
		OnTransferSuccess: func(req *request.NotifyPayoutRequest) {
			log.Printf("[NotifyPayoutController] transfer succeeded: transferRequestId=%s, transferId=%s, chargeMode=%s, finishTime=%s",
				req.TransferRequestId,
				req.TransferId,
				req.ChargeMode,
				req.TransferFinishTime)
		},
		OnTransferFail: func(req *request.NotifyPayoutRequest) {
			resultCode := "null"
			resultMessage := "null"
			if req.TransferResult != nil {
				resultCode = req.TransferResult.ResultCode
				resultMessage = req.TransferResult.ResultMessage
			}
			log.Printf("[NotifyPayoutController] transfer failed: transferRequestId=%s, transferId=%s, resultCode=%s, resultMessage=%s",
				req.TransferRequestId, req.TransferId, resultCode, resultMessage)
		},
	}
}

// ServeHTTP implements http.Handler, making NotifyPayoutController a standard HTTP handler.
//
// Usage with net/http:
//
//	ctrl := controller.NewNotifyPayoutController(wfSigner)
//	http.Handle("/notify/payout", ctrl)
//
// Usage with gin:
//
//	router.POST("/notify/payout", gin.WrapH(ctrl))
func (c *NotifyPayoutController) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	log.Println("[NotifyPayoutController] received notification")

	// Step 1: Read request body
	body, err := io.ReadAll(r.Body)
	if err != nil {
		log.Printf("[NotifyPayoutController] failed to read request body: %v", err)
		http.Error(w, "Failed to read request body", http.StatusBadRequest)
		return
	}
	defer r.Body.Close()

	// Step 2: Extract required headers
	signatureHeader := r.Header.Get("Signature")
	clientID := r.Header.Get("Client-Id")
	requestTime := r.Header.Get("Request-Time")

	if signatureHeader == "" || clientID == "" || requestTime == "" {
		log.Println("[NotifyPayoutController] missing required headers")
		http.Error(w, "Missing required headers", http.StatusBadRequest)
		return
	}

	// Step 3: Verify signature
	if err := c.Signer.VerifySignatureWithPath(payoutNotifyPath, clientID, requestTime, string(body), signatureHeader); err != nil {
		log.Printf("[NotifyPayoutController] signature verification failed: %v", err)
		http.Error(w, "Signature verification failed", http.StatusBadRequest)
		return
	}

	// Step 4: Parse request body
	var req request.NotifyPayoutRequest
	if err := json.Unmarshal(body, &req); err != nil {
		log.Printf("[NotifyPayoutController] failed to parse request body: %v", err)
		http.Error(w, "Invalid request body", http.StatusBadRequest)
		return
	}

	if req.TransferRequestId == "" {
		log.Println("[NotifyPayoutController] transferRequestId is missing")
		http.Error(w, "transferRequestId is missing", http.StatusBadRequest)
		return
	}

	log.Printf("[NotifyPayoutController] processing transferRequestId=%s, transferId=%s",
		req.TransferRequestId, req.TransferId)

	// Step 5: Idempotency check (integrators should implement their own deduplication logic)
	// TODO: Check if transferRequestId has been processed before
	// if isDuplicate(req.TransferRequestId) {
	//     log.Printf("[NotifyPayoutController] duplicate transferRequestId=%s, skip processing", req.TransferRequestId)
	//     // still return success
	// }

	// Step 6: Process business logic based on transfer result
	if req.TransferResult != nil && req.TransferResult.IsSuccess() {
		if c.OnTransferSuccess != nil {
			c.OnTransferSuccess(&req)
		}
	} else {
		if c.OnTransferFail != nil {
			c.OnTransferFail(&req)
		}
	}

	// Step 7: Build success response
	resp := response.NewSuccessResponse()
	respBody, _ := json.Marshal(resp)

	// Step 8: Sign response
	respSignature, err := c.Signer.GenerateSignatureWithPath(payoutNotifyPath, clientID, c.Signer.GetRequestTime(), string(respBody))
	if err != nil {
		log.Printf("[NotifyPayoutController] failed to sign response: %v", err)
		// Still return success to avoid WF retry
	}

	w.Header().Set("Content-Type", "application/json; charset=UTF-8")
	w.Header().Set("Signature", fmt.Sprintf("algorithm=RSA256, keyVersion=2, signature=%s", respSignature))
	w.WriteHeader(http.StatusOK)
	w.Write(respBody)

	log.Printf("[NotifyPayoutController] processed successfully, transferRequestId=%s", req.TransferRequestId)
}
