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

// NotifyTradeOrderController handles notifyTradeOrder callback notifications from WF.
// This is only applicable to the PAY_INTO_CHINA scene.
//
// After trade order processing is complete, WF sends a notifyTradeOrder
// callback via HTTP POST to the integrator's notifyUrl. This controller is responsible for:
//  1. Verifying the request signature
//  2. Parsing the request body
//  3. Idempotency check based on requestId
//  4. Processing trade order results (preferably async)
//  5. Signing and returning the response
//
// If no successful response is returned, WF will retry up to 7 times with intervals:
// 2min, 10min, 10min, 1h, 2h, 6h, 15h
type NotifyTradeOrderController struct {
	Signer signer.Signer

	// OnTradeOrderResult is called when a trade order result notification is received.
	// Integrators should implement this callback to handle business logic
	// (e.g., update local order status, process settlement results).
	OnTradeOrderResult func(req *request.NotifyTradeOrderRequest)
}

const (
	tradeOrderNotifyPath = "/amsin/api/v1/business/account/notifyTradeOrder"
)

// NewNotifyTradeOrderController creates a new NotifyTradeOrderController with default no-op callbacks.
func NewNotifyTradeOrderController(s signer.Signer) *NotifyTradeOrderController {
	return &NotifyTradeOrderController{
		Signer: s,
		OnTradeOrderResult: func(req *request.NotifyTradeOrderRequest) {
			log.Printf("[NotifyTradeOrderController] trade order results received: requestId=%s, resultCount=%d",
				req.RequestID,
				len(req.TradeOrderResults))
			for i, r := range req.TradeOrderResults {
				log.Printf("[NotifyTradeOrderController] result[%d]: referenceOrderNo=%s, orderStatus=%s, orderType=%s, statusMessage=%s",
					i, r.ReferenceOrderNo, r.OrderStatus, r.OrderType, r.StatusMessage)
			}
		},
	}
}

// ServeHTTP implements http.Handler, making NotifyTradeOrderController a standard HTTP handler.
//
// Usage with net/http:
//
//	ctrl := controller.NewNotifyTradeOrderController(wfSigner)
//	http.Handle("/notify/trade-order", ctrl)
//
// Usage with gin:
//
//	router.POST("/notify/trade-order", gin.WrapH(ctrl))
func (c *NotifyTradeOrderController) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	log.Println("[NotifyTradeOrderController] received notification")

	// Step 1: Read request body
	body, err := io.ReadAll(r.Body)
	if err != nil {
		log.Printf("[NotifyTradeOrderController] failed to read request body: %v", err)
		http.Error(w, "Failed to read request body", http.StatusBadRequest)
		return
	}
	defer r.Body.Close()

	// Step 2: Extract required headers
	signatureHeader := r.Header.Get("Signature")
	clientID := r.Header.Get("Client-Id")
	requestTime := r.Header.Get("Request-Time")

	if signatureHeader == "" || clientID == "" || requestTime == "" {
		log.Println("[NotifyTradeOrderController] missing required headers")
		http.Error(w, "Missing required headers", http.StatusBadRequest)
		return
	}

	// Step 3: Verify signature
	if err := c.Signer.VerifySignatureWithPath(tradeOrderNotifyPath, clientID, requestTime, string(body), signatureHeader); err != nil {
		log.Printf("[NotifyTradeOrderController] signature verification failed: %v", err)
		http.Error(w, "Signature verification failed", http.StatusBadRequest)
		return
	}

	// Step 4: Parse request body
	var req request.NotifyTradeOrderRequest
	if err := json.Unmarshal(body, &req); err != nil {
		log.Printf("[NotifyTradeOrderController] failed to parse request body: %v", err)
		http.Error(w, "Invalid request body", http.StatusBadRequest)
		return
	}

	if req.RequestID == "" {
		log.Println("[NotifyTradeOrderController] requestId is missing")
		http.Error(w, "requestId is missing", http.StatusBadRequest)
		return
	}

	log.Printf("[NotifyTradeOrderController] processing requestId=%s", req.RequestID)

	// Step 5: Idempotency check (integrators should implement their own deduplication logic)
	// TODO: Check if requestId has been processed before
	// if isDuplicate(req.RequestID) {
	//     log.Printf("[NotifyTradeOrderController] duplicate requestId=%s, skip processing", req.RequestID)
	//     // still return success
	// }

	// Step 6: Process business logic — async processing recommended to avoid WF timeout retry
	if c.OnTradeOrderResult != nil {
		c.OnTradeOrderResult(&req)
	}

	// Step 7: Build success response
	resp := response.NewSuccessResponse()
	respBody, _ := json.Marshal(resp)

	// Step 8: Sign response
	respSignature, err := c.Signer.GenerateSignatureWithPath(tradeOrderNotifyPath, clientID, c.Signer.GetRequestTime(), string(respBody))
	if err != nil {
		log.Printf("[NotifyTradeOrderController] failed to sign response: %v", err)
		// Still return success to avoid WF retry
	}

	w.Header().Set("Content-Type", "application/json; charset=UTF-8")
	w.Header().Set("Signature", fmt.Sprintf("algorithm=RSA256, keyVersion=2, signature=%s", respSignature))
	w.WriteHeader(http.StatusOK)
	w.Write(respBody)

	log.Printf("[NotifyTradeOrderController] processed successfully, requestId=%s", req.RequestID)
}
