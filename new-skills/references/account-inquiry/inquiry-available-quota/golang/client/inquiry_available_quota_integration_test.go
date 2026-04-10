package client

import (
	"fmt"
	"testing"

	"{moduleName}/wf/config"
	"{moduleName}/wf/model/exception"
	"{moduleName}/wf/model/request"
	"{moduleName}/wf/signer"
)

// 配置信息（请替换为实际值）
const (
	wfClientID   = "{clientId}"
	wfUserID     = "{userId}"
	wfBaseURL    = "{baseUrl}" // sandbox: https://iopengw-sggz95m.alipay.com
	wfPrivateKey = "{privateKeyPath}"
	wfPublicKey  = "{publicKeyPath}"
)

func newRealInquiryAvailableQuotaClient(t *testing.T) *InquiryAvailableQuotaClient {
	t.Helper()
	cfg := config.NewWfConfig(wfClientID, wfUserID, wfBaseURL, wfPrivateKey, wfPublicKey)
	s, err := signer.NewWfSigner(wfPrivateKey, wfPublicKey)
	if err != nil {
		t.Fatalf("Failed to create signer: %v", err)
	}
	return NewInquiryAvailableQuotaClient(cfg, s)
}

// TestInquiryAvailableQuota_USER_ID 测试按用户ID查询结汇额度
func TestInquiryAvailableQuota_USER_ID(t *testing.T) {
	c := newRealInquiryAvailableQuotaClient(t)

	req := &request.InquiryAvailableQuotaRequest{
		QuotaAccumulationMethod: "USER_ID",
		QuotaAccumulationId:     "YOUR_USER_ID",
		Currency:                "USD",
	}

	resp, err := c.InquiryAvailableQuota(req)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s\n", wfErr.Code, wfErr.Message)
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] InquiryAvailableQuota\n")
	fmt.Printf("  QuotaAccumulationMethod: %s\n", resp.QuotaAccumulationMethod)
	fmt.Printf("  QuotaAccumulationId: %s\n", resp.QuotaAccumulationId)
	if resp.AvailableQuota != nil {
		fmt.Printf("  AvailableQuota: %d %s\n", resp.AvailableQuota.Value, resp.AvailableQuota.Currency)
	}
}

// TestInquiryAvailableQuota_RECEIVING_ACCOUNT 测试按收款账户查询结汇额度
func TestInquiryAvailableQuota_RECEIVING_ACCOUNT(t *testing.T) {
	c := newRealInquiryAvailableQuotaClient(t)

	req := &request.InquiryAvailableQuotaRequest{
		QuotaAccumulationMethod: "RECEIVING_ACCOUNT",
		QuotaAccumulationId:     "YOUR_RA_NUMBER",
		Currency:                "USD",
	}

	resp, err := c.InquiryAvailableQuota(req)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s\n", wfErr.Code, wfErr.Message)
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] InquiryAvailableQuota\n")
	if resp.AvailableQuota != nil {
		fmt.Printf("  AvailableQuota: %d %s\n", resp.AvailableQuota.Value, resp.AvailableQuota.Currency)
	}
}

// TestInquiryAvailableQuota_BENEFICIARY 测试按收款人查询结汇额度（需传tradeType）
func TestInquiryAvailableQuota_BENEFICIARY(t *testing.T) {
	c := newRealInquiryAvailableQuotaClient(t)

	req := &request.InquiryAvailableQuotaRequest{
		QuotaAccumulationMethod: "BENEFICIARY",
		QuotaAccumulationId:     "YOUR_BENEFICIARY_ID",
		Currency:                "USD",
		TradeType:               "GOODS", // GOODS 或 SERVICE
	}

	resp, err := c.InquiryAvailableQuota(req)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s\n", wfErr.Code, wfErr.Message)
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] InquiryAvailableQuota\n")
	fmt.Printf("  TradeType: %s\n", resp.TradeType)
	if resp.AvailableQuota != nil {
		fmt.Printf("  AvailableQuota: %d %s\n", resp.AvailableQuota.Value, resp.AvailableQuota.Currency)
	}
}
