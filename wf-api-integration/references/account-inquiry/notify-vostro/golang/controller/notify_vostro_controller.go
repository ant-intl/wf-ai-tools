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

// NotifyVostroController handles notifyVostro callback notifications from WF.
//
// When the integrator's WF account receives a deposit, WF sends a notifyVostro
// callback via HTTP POST. This controller is responsible for:
//  1. Verifying the request signature
//  2. Parsing the request body
//  3. Idempotency check based on fundingId
//  4. Processing funding success / refund notifications
//  5. Signing and returning the response
//
// If no successful response is returned, WF will retry up to 7 times with intervals:
// 2min, 10min, 10min, 1h, 2h, 6h, 15h
type NotifyVostroController struct {
	Signer signer.Signer

	// OnFundingSuccess is called when a funding success notification is received.
	// Integrators should implement this callback to handle business logic
	// (e.g., update balance, record transaction, send internal notification).
	OnFundingSuccess func(req *request.NotifyVostroRequest)

	// OnRefundSuccess is called when a refund success notification is received.
	// Integrators should implement this callback to handle refund business logic.
	OnRefundSuccess func(req *request.NotifyVostroRequest)
}

const (
	vostroNotifyPath = "/amsin/api/v1/business/fund/notifyVostro"
)

// NewNotifyVostroController creates a new NotifyVostroController with default no-op callbacks.
func NewNotifyVostroController(s signer.Signer) *NotifyVostroController {
	return &NotifyVostroController{
		Signer: s,
		OnFundingSuccess: func(req *request.NotifyVostroRequest) {
			log.Printf("[NotifyVostroController] funding success: fundingId=%s, amount=%d %s, beneficiary=%s",
				req.FundingID,
				req.BalanceChangeAmount.Value, req.BalanceChangeAmount.Currency,
				req.BeneficiaryAccount.BeneficiaryBankAccountNo)
		},
		OnRefundSuccess: func(req *request.NotifyVostroRequest) {
			log.Printf("[NotifyVostroController] refund success: fundingId=%s, amount=%d %s, beneficiary=%s",
				req.FundingID,
				req.BalanceChangeAmount.Value, req.BalanceChangeAmount.Currency,
				req.BeneficiaryAccount.BeneficiaryBankAccountNo)
		},
	}
}

// ServeHTTP implements http.Handler, making NotifyVostroController a standard HTTP handler.
//
// Usage with net/http:
//
//	ctrl := controller.NewNotifyVostroController(wfSigner)
//	http.Handle("/notify/vostro", ctrl)
//
// Usage with gin:
//
//	router.POST("/notify/vostro", gin.WrapH(ctrl))
func (c *NotifyVostroController) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	log.Println("[NotifyVostroController] received notification")

	// Step 1: Read request body
	body, err := io.ReadAll(r.Body)
	if err != nil {
		log.Printf("[NotifyVostroController] failed to read request body: %v", err)
		http.Error(w, "Failed to read request body", http.StatusBadRequest)
		return
	}
	defer r.Body.Close()

	// Step 2: Extract required headers
	signatureHeader := r.Header.Get("Signature")
	clientID := r.Header.Get("Client-Id")
	requestTime := r.Header.Get("Request-Time")

	if signatureHeader == "" || clientID == "" || requestTime == "" {
		log.Println("[NotifyVostroController] missing required headers")
		http.Error(w, "Missing required headers", http.StatusBadRequest)
		return
	}

	// Step 3: Verify signature
	if err := c.Signer.VerifySignatureWithPath(vostroNotifyPath, clientID, requestTime, string(body), signatureHeader); err != nil {
		log.Printf("[NotifyVostroController] signature verification failed: %v", err)
		http.Error(w, "Signature verification failed", http.StatusBadRequest)
		return
	}

	// Step 4: Parse request body
	var req request.NotifyVostroRequest
	if err := json.Unmarshal(body, &req); err != nil {
		log.Printf("[NotifyVostroController] failed to parse request body: %v", err)
		http.Error(w, "Invalid request body", http.StatusBadRequest)
		return
	}

	if req.FundingID == "" {
		log.Println("[NotifyVostroController] fundingId is missing")
		http.Error(w, "fundingId is missing", http.StatusBadRequest)
		return
	}

	log.Printf("[NotifyVostroController] processing fundingId=%s", req.FundingID)

	// Step 5: Idempotency check (integrators should implement their own deduplication logic)
	// TODO: Check if fundingId has been processed before
	// if isDuplicate(req.FundingID) {
	//     log.Printf("[NotifyVostroController] duplicate fundingId=%s, skip processing", req.FundingID)
	//     // still return success
	// }

	// Step 6: Process business logic based on balanceResult
	if req.BalanceResult != nil && req.BalanceResult.ResultStatus == "S" {
		switch req.BalanceResult.ResultCode {
		case "SUCCESS":
			if c.OnFundingSuccess != nil {
				c.OnFundingSuccess(&req)
			}
		case "REFUND":
			if c.OnRefundSuccess != nil {
				c.OnRefundSuccess(&req)
			}
		default:
			log.Printf("[NotifyVostroController] unknown resultCode=%s with resultStatus=S, fundingId=%s",
				req.BalanceResult.ResultCode, req.FundingID)
			if c.OnFundingSuccess != nil {
				c.OnFundingSuccess(&req)
			}
		}
	} else {
		resultStatus := "null"
		resultCode := "null"
		if req.BalanceResult != nil {
			resultStatus = req.BalanceResult.ResultStatus
			resultCode = req.BalanceResult.ResultCode
		}
		log.Printf("[NotifyVostroController] unexpected balanceResult: status=%s, code=%s, fundingId=%s",
			resultStatus, resultCode, req.FundingID)
	}

	// Step 7: Build success response
	resp := response.NewSuccessResponse()
	respBody, _ := json.Marshal(resp)

	// Step 8: Sign response
	respSignature, err := c.Signer.GenerateSignatureWithPath(vostroNotifyPath, clientID, c.Signer.GetRequestTime(), string(respBody))
	if err != nil {
		log.Printf("[NotifyVostroController] failed to sign response: %v", err)
		// Still return success to avoid WF retry
	}

	w.Header().Set("Content-Type", "application/json; charset=UTF-8")
	w.Header().Set("Signature", fmt.Sprintf("algorithm=RSA256, keyVersion=2, signature=%s", respSignature))
	w.WriteHeader(http.StatusOK)
	w.Write(respBody)

	log.Printf("[NotifyVostroController] processed successfully, fundingId=%s", req.FundingID)
}

