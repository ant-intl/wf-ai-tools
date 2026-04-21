package client

import (
	"encoding/json"
	"fmt"
	"log"

	"{moduleName}/wf/model/request"
	"{moduleName}/wf/model/response"
)

// NotifyVostroHandler handles notifyVostro callback notifications from WF.
//
// When the integrator's WF account receives a deposit, WF sends a notifyVostro
// callback. This handler is responsible for:
//  1. Parsing the request body
//  2. Idempotency check based on fundingId
//  3. Processing funding success / refund notifications
//  4. Building the response to acknowledge receipt
//
// If no successful response is returned, WF will retry up to 7 times with intervals:
// 2min, 10min, 10min, 1h, 2h, 6h, 15h
type NotifyVostroHandler struct {
	// OnFundingSuccess is called when a funding success notification is received.
	// Integrators should implement this callback to handle business logic
	// (e.g., update balance, record transaction, send internal notification).
	OnFundingSuccess func(req *request.NotifyVostroRequest)

	// OnRefundSuccess is called when a refund success notification is received.
	// Integrators should implement this callback to handle refund business logic.
	OnRefundSuccess func(req *request.NotifyVostroRequest)
}

// NewNotifyVostroHandler creates a new NotifyVostroHandler with default no-op callbacks.
func NewNotifyVostroHandler() *NotifyVostroHandler {
	return &NotifyVostroHandler{
		OnFundingSuccess: func(req *request.NotifyVostroRequest) {
			log.Printf("[NotifyVostroHandler] funding success: fundingId=%s, amount=%d %s, beneficiary=%s",
				req.FundingID,
				req.BalanceChangeAmount.Value, req.BalanceChangeAmount.Currency,
				req.BeneficiaryAccount.BeneficiaryBankAccountNo)
		},
		OnRefundSuccess: func(req *request.NotifyVostroRequest) {
			log.Printf("[NotifyVostroHandler] refund success: fundingId=%s, amount=%d %s, beneficiary=%s",
				req.FundingID,
				req.BalanceChangeAmount.Value, req.BalanceChangeAmount.Currency,
				req.BeneficiaryAccount.BeneficiaryBankAccountNo)
		},
	}
}

// HandleNotification processes a notifyVostro callback notification.
//
// The caller (e.g., HTTP handler) should:
//  1. Verify the request signature before calling this method
//  2. Pass the raw request body as []byte
//  3. Marshal the returned response and send it back to WF
//
// Returns a NotifyVostroResponse that should be sent back to WF.
func (h *NotifyVostroHandler) HandleNotification(body []byte) *response.NotifyVostroResponse {
	log.Println("[NotifyVostroHandler] received notification")

	// 1. Parse request body
	var req request.NotifyVostroRequest
	if err := json.Unmarshal(body, &req); err != nil {
		log.Printf("[NotifyVostroHandler] failed to parse request body: %v", err)
		return response.NewFailResponse("UNKNOWN_EXCEPTION",
			fmt.Sprintf("failed to parse request body: %v", err))
	}

	if req.FundingID == "" {
		log.Println("[NotifyVostroHandler] fundingId is missing")
		return response.NewFailResponse("UNKNOWN_EXCEPTION", "fundingId is missing")
	}

	log.Printf("[NotifyVostroHandler] processing fundingId=%s", req.FundingID)

	// 2. Idempotency check (integrators should implement their own deduplication logic)
	// TODO: Check if fundingId has been processed before
	// if isDuplicate(req.FundingID) {
	//     log.Printf("[NotifyVostroHandler] duplicate fundingId=%s, skip processing", req.FundingID)
	//     return response.NewSuccessResponse()
	// }

	// 3. Process business logic based on balanceResult
	if req.BalanceResult == nil {
		log.Printf("[NotifyVostroHandler] balanceResult is nil, fundingId=%s", req.FundingID)
		return response.NewFailResponse("UNKNOWN_EXCEPTION", "balanceResult is missing")
	}

	switch {
	case req.BalanceResult.ResultStatus == "S" && req.BalanceResult.ResultCode == "SUCCESS":
		if h.OnFundingSuccess != nil {
			h.OnFundingSuccess(&req)
		}
	case req.BalanceResult.ResultStatus == "S" && req.BalanceResult.ResultCode == "REFUND":
		if h.OnRefundSuccess != nil {
			h.OnRefundSuccess(&req)
		}
	default:
		log.Printf("[NotifyVostroHandler] unexpected balanceResult: status=%s, code=%s, fundingId=%s",
			req.BalanceResult.ResultStatus, req.BalanceResult.ResultCode, req.FundingID)
	}

	// 4. Mark as processed (integrators should implement their own persistence logic)
	// TODO: markAsProcessed(req.FundingID)

	// 5. Return success response
	log.Printf("[NotifyVostroHandler] processed successfully, fundingId=%s", req.FundingID)
	return response.NewSuccessResponse()
}
