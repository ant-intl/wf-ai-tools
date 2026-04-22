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

// NotifyTransferController handles notifyTransfer callback notifications from WF.
//
// After a transfer is completed, WF sends a notifyTransfer callback via HTTP POST.
// The integrator sets the callback URL via transferToDetail.transferNotifyUrl when
// calling createTransfer. This controller is responsible for:
//  1. Verifying the request signature
//  2. Parsing the request body
//  3. Idempotency check based on transferRequestId
//  4. Processing transfer result (success/failure)
//  5. Signing and returning the response
//
// If no successful response is returned, WF will retry up to 7 times with intervals:
// 2min, 10min, 10min, 1h, 2h, 6h, 15h
type NotifyTransferController struct {
	Signer signer.Signer

	// OnTransferSuccess is called when a transfer success notification is received.
	// Integrators should implement this callback to handle business logic
	// (e.g., update local transfer order status, record transfer completion time).
	OnTransferSuccess func(req *request.NotifyTransferRequest)

	// OnTransferFail is called when a transfer failure notification is received.
	// Integrators should implement this callback to handle failure logic.
	OnTransferFail func(req *request.NotifyTransferRequest)
}

const (
	transferNotifyPath = "/amsin/api/v1/business/fund/notifyTransfer"
)

// NewNotifyTransferController creates a new NotifyTransferController with default no-op callbacks.
func NewNotifyTransferController(s signer.Signer) *NotifyTransferController {
	return &NotifyTransferController{
		Signer: s,
		OnTransferSuccess: func(req *request.NotifyTransferRequest) {
			log.Printf("[NotifyTransferController] transfer succeeded: transferRequestId=%s, transferId=%s, transferFinishTime=%s",
				req.TransferRequestId,
				req.TransferId,
				req.TransferFinishTime)

			if req.TransferFromDetail != nil && req.TransferFromDetail.TransferFromAmount != nil {
				log.Printf("[NotifyTransferController] transferFrom: currency=%s, value=%v",
					req.TransferFromDetail.TransferFromAmount.Currency,
					req.TransferFromDetail.TransferFromAmount.Value)
			}
			if req.TransferToDetail != nil && req.TransferToDetail.TransferToAmount != nil {
				log.Printf("[NotifyTransferController] transferTo: currency=%s, value=%v, purposeCode=%s",
					req.TransferToDetail.TransferToAmount.Currency,
					req.TransferToDetail.TransferToAmount.Value,
					req.TransferToDetail.PurposeCode)
			}
			if req.TransferOrderAddition != nil {
				log.Printf("[NotifyTransferController] transferOrderAddition: referenceOrderId=%s",
					req.TransferOrderAddition.ReferenceOrderId)
			}
		},
		OnTransferFail: func(req *request.NotifyTransferRequest) {
			resultCode := "null"
			resultMessage := "null"
			if req.TransferResult != nil {
				resultCode = req.TransferResult.ResultCode
				resultMessage = req.TransferResult.ResultMessage
			}
			log.Printf("[NotifyTransferController] transfer failed: transferRequestId=%s, transferId=%s, resultCode=%s, resultMessage=%s",
				req.TransferRequestId, req.TransferId, resultCode, resultMessage)
		},
	}
}

// ServeHTTP implements http.Handler, making NotifyTransferController a standard HTTP handler.
//
// Usage with net/http:
//
//	ctrl := controller.NewNotifyTransferController(wfSigner)
//	http.Handle("/notify/transfer", ctrl)
//
// Usage with gin:
//
//	router.POST("/notify/transfer", gin.WrapH(ctrl))
func (c *NotifyTransferController) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	log.Println("[NotifyTransferController] received notification")

	// Step 1: Read request body
	body, err := io.ReadAll(r.Body)
	if err != nil {
		log.Printf("[NotifyTransferController] failed to read request body: %v", err)
		http.Error(w, "Failed to read request body", http.StatusBadRequest)
		return
	}
	defer r.Body.Close()

	// Step 2: Extract required headers
	signatureHeader := r.Header.Get("Signature")
	clientID := r.Header.Get("Client-Id")
	requestTime := r.Header.Get("Request-Time")

	if signatureHeader == "" || clientID == "" || requestTime == "" {
		log.Println("[NotifyTransferController] missing required headers")
		http.Error(w, "Missing required headers", http.StatusBadRequest)
		return
	}

	// Step 3: Verify signature
	if err := c.Signer.VerifySignatureWithPath(transferNotifyPath, clientID, requestTime, string(body), signatureHeader); err != nil {
		log.Printf("[NotifyTransferController] signature verification failed: %v", err)
		http.Error(w, "Signature verification failed", http.StatusBadRequest)
		return
	}

	// Step 4: Parse request body
	var req request.NotifyTransferRequest
	if err := json.Unmarshal(body, &req); err != nil {
		log.Printf("[NotifyTransferController] failed to parse request body: %v", err)
		http.Error(w, "Invalid request body", http.StatusBadRequest)
		return
	}

	if req.TransferRequestId == "" {
		log.Println("[NotifyTransferController] transferRequestId is missing")
		http.Error(w, "transferRequestId is missing", http.StatusBadRequest)
		return
	}

	log.Printf("[NotifyTransferController] processing transferRequestId=%s, transferId=%s",
		req.TransferRequestId, req.TransferId)

	// Step 5: Idempotency check (integrators should implement their own deduplication logic)
	// TODO: Check if transferRequestId has been processed before
	// if isDuplicate(req.TransferRequestId) {
	//     log.Printf("[NotifyTransferController] duplicate transferRequestId=%s, skip processing", req.TransferRequestId)
	//     // still return success
	// }

	// Step 6: Process business logic based on transfer result
	if req.IsTransferSuccess() {
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
	respSignature, err := c.Signer.GenerateSignatureWithPath(transferNotifyPath, clientID, c.Signer.GetRequestTime(), string(respBody))
	if err != nil {
		log.Printf("[NotifyTransferController] failed to sign response: %v", err)
		// Still return success to avoid WF retry
	}

	w.Header().Set("Content-Type", "application/json; charset=UTF-8")
	w.Header().Set("Signature", fmt.Sprintf("algorithm=RSA256, keyVersion=2, signature=%s", respSignature))
	w.WriteHeader(http.StatusOK)
	w.Write(respBody)

	log.Printf("[NotifyTransferController] processed successfully, transferRequestId=%s", req.TransferRequestId)
}
