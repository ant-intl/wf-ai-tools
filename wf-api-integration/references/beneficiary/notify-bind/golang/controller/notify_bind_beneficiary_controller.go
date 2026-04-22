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

// NotifyBindBeneficiaryController handles notifyBindBeneficiary callback notifications from WF.
//
// After a beneficiary is successfully bound, WF sends a notifyBindBeneficiary
// callback via HTTP POST. This controller is responsible for:
//  1. Verifying the request signature
//  2. Parsing the request body
//  3. Idempotency check based on bindBeneficiaryRequestId
//  4. Processing beneficiary binding result
//  5. Signing and returning the response
//
// If no successful response is returned, WF will retry up to 7 times with intervals:
// 2min, 10min, 10min, 1h, 2h, 6h, 15h
type NotifyBindBeneficiaryController struct {
	Signer signer.Signer

	// OnBindSuccess is called when a beneficiary binding success notification is received.
	// Integrators should implement this callback to handle business logic
	// (e.g., update local beneficiary status, store beneficiaryToken).
	OnBindSuccess func(req *request.NotifyBindBeneficiaryRequest)

	// OnBindFail is called when a beneficiary binding failure notification is received.
	// Integrators should implement this callback to handle failure logic.
	OnBindFail func(req *request.NotifyBindBeneficiaryRequest)
}

const (
	bindBeneficiaryNotifyPath = "/amsin/api/v1/business/account/notifyBindBeneficiary"
)

// NewNotifyBindBeneficiaryController creates a new NotifyBindBeneficiaryController with default no-op callbacks.
func NewNotifyBindBeneficiaryController(s signer.Signer) *NotifyBindBeneficiaryController {
	return &NotifyBindBeneficiaryController{
		Signer: s,
		OnBindSuccess: func(req *request.NotifyBindBeneficiaryRequest) {
			log.Printf("[NotifyBindBeneficiaryController] beneficiary bound successfully: bindBeneficiaryRequestId=%s, beneficiaryToken=%s, status=%s",
				req.BindBeneficiaryRequestId,
				req.Beneficiary.BeneficiaryToken,
				req.Beneficiary.Status)
		},
		OnBindFail: func(req *request.NotifyBindBeneficiaryRequest) {
			resultCode := "null"
			resultMessage := "null"
			if req.Result != nil {
				resultCode = req.Result.ResultCode
				resultMessage = req.Result.ResultMessage
			}
			log.Printf("[NotifyBindBeneficiaryController] beneficiary binding failed: bindBeneficiaryRequestId=%s, resultCode=%s, resultMessage=%s",
				req.BindBeneficiaryRequestId, resultCode, resultMessage)
		},
	}
}

// ServeHTTP implements http.Handler, making NotifyBindBeneficiaryController a standard HTTP handler.
//
// Usage with net/http:
//
//	ctrl := controller.NewNotifyBindBeneficiaryController(wfSigner)
//	http.Handle("/notify/bind-beneficiary", ctrl)
//
// Usage with gin:
//
//	router.POST("/notify/bind-beneficiary", gin.WrapH(ctrl))
func (c *NotifyBindBeneficiaryController) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	log.Println("[NotifyBindBeneficiaryController] received notification")

	// Step 1: Read request body
	body, err := io.ReadAll(r.Body)
	if err != nil {
		log.Printf("[NotifyBindBeneficiaryController] failed to read request body: %v", err)
		http.Error(w, "Failed to read request body", http.StatusBadRequest)
		return
	}
	defer r.Body.Close()

	// Step 2: Extract required headers
	signatureHeader := r.Header.Get("Signature")
	clientID := r.Header.Get("Client-Id")
	requestTime := r.Header.Get("Request-Time")

	if signatureHeader == "" || clientID == "" || requestTime == "" {
		log.Println("[NotifyBindBeneficiaryController] missing required headers")
		http.Error(w, "Missing required headers", http.StatusBadRequest)
		return
	}

	// Step 3: Verify signature
	if err := c.Signer.VerifySignatureWithPath(bindBeneficiaryNotifyPath, clientID, requestTime, string(body), signatureHeader); err != nil {
		log.Printf("[NotifyBindBeneficiaryController] signature verification failed: %v", err)
		http.Error(w, "Signature verification failed", http.StatusBadRequest)
		return
	}

	// Step 4: Parse request body
	var req request.NotifyBindBeneficiaryRequest
	if err := json.Unmarshal(body, &req); err != nil {
		log.Printf("[NotifyBindBeneficiaryController] failed to parse request body: %v", err)
		http.Error(w, "Invalid request body", http.StatusBadRequest)
		return
	}

	if req.BindBeneficiaryRequestId == "" {
		log.Println("[NotifyBindBeneficiaryController] bindBeneficiaryRequestId is missing")
		http.Error(w, "bindBeneficiaryRequestId is missing", http.StatusBadRequest)
		return
	}

	log.Printf("[NotifyBindBeneficiaryController] processing bindBeneficiaryRequestId=%s", req.BindBeneficiaryRequestId)

	// Step 5: Idempotency check (integrators should implement their own deduplication logic)
	// TODO: Check if bindBeneficiaryRequestId has been processed before
	// if isDuplicate(req.BindBeneficiaryRequestId) {
	//     log.Printf("[NotifyBindBeneficiaryController] duplicate bindBeneficiaryRequestId=%s, skip processing", req.BindBeneficiaryRequestId)
	//     // still return success
	// }

	// Step 6: Process business logic based on result
	if req.Result != nil && req.Result.ResultStatus == "S" {
		if req.Beneficiary != nil {
			if c.OnBindSuccess != nil {
				c.OnBindSuccess(&req)
			}
		}
	} else {
		if c.OnBindFail != nil {
			c.OnBindFail(&req)
		}
	}

	// Step 7: Build success response
	resp := response.NewSuccessResponse()
	respBody, _ := json.Marshal(resp)

	// Step 8: Sign response
	respSignature, err := c.Signer.GenerateSignatureWithPath(bindBeneficiaryNotifyPath, clientID, c.Signer.GetRequestTime(), string(respBody))
	if err != nil {
		log.Printf("[NotifyBindBeneficiaryController] failed to sign response: %v", err)
		// Still return success to avoid WF retry
	}

	w.Header().Set("Content-Type", "application/json; charset=UTF-8")
	w.Header().Set("Signature", fmt.Sprintf("algorithm=RSA256, keyVersion=2, signature=%s", respSignature))
	w.WriteHeader(http.StatusOK)
	w.Write(respBody)

	log.Printf("[NotifyBindBeneficiaryController] processed successfully, bindBeneficiaryRequestId=%s", req.BindBeneficiaryRequestId)
}

