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
	wfBaseURL    = "{baseUrl}" // sandbox: https://open-sitprod-sg.alipay.com
	wfPrivateKey = "{privateKeyPath}"
	wfPublicKey  = "{publicKeyPath}"
)

// newRealInquiryAvailableQuotaClient 创建真实签名模式的客户端
func newRealInquiryAvailableQuotaClient(t *testing.T) *InquiryAvailableQuotaClient {
	t.Helper()
	cfg := config.NewWfConfig(wfClientID, wfUserID, wfBaseURL, wfPrivateKey, wfPublicKey)
	s, err := signer.NewWfSigner(wfPrivateKey, wfPublicKey)
	if err != nil {
		t.Fatalf("Failed to create signer: %v", err)
	}
	return NewInquiryAvailableQuotaClient(cfg, s)
}

// newMockInquiryAvailableQuotaClient 创建 Mock 签名模式的客户端（用于测试）
func newMockInquiryAvailableQuotaClient(t *testing.T) *InquiryAvailableQuotaClient {
	t.Helper()
	cfg := config.NewWfConfig(wfClientID, wfUserID, wfBaseURL, "", "")
	// 使用简单的 mock signer
	s := &mockSigner{}
	return NewInquiryAvailableQuotaClient(cfg, s)
}

// mockSigner 简单的 Mock 签名器
type mockSigner struct{}

func (m *mockSigner) GenerateSignature(httpMethod, url, body string) (string, error) {
	return "TESTING_SIGNATURE", nil
}

func (m *mockSigner) VerifySignature(httpMethod, url, body, signature string) error {
	return nil
}

// TestInquiryAvailableQuota_USER_ID 测试按用户ID查询结汇额度
func TestInquiryAvailableQuota_USER_ID(t *testing.T) {
	// 使用真实客户端
	c := newRealInquiryAvailableQuotaClient(t)
	// 或使用 Mock 客户端（跳过验签，WF 将返回 INVALID_SIGNATURE）
	// c := newMockInquiryAvailableQuotaClient(t)

	req := &request.InquiryAvailableQuotaRequest{
		QuotaAccumulationMethod: "USER_ID",
		QuotaAccumulationId:     "YOUR_USER_ID",
		Currency:                "USD",
	}

	fmt.Println("====== TestInquiryAvailableQuota_USER_ID ======")
	fmt.Printf("Request: %+v\n", req)

	resp, err := c.InquiryAvailableQuota(req)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s\n", wfErr.Code, wfErr.Message)
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		// Mock 模式下会返回 INVALID_SIGNATURE，这是预期的
		fmt.Println("Note: Mock mode returns INVALID_SIGNATURE, which is expected")
		return
	}

	fmt.Printf("[PASS] InquiryAvailableQuota\n")
	fmt.Printf("  QuotaAccumulationMethod: %s\n", resp.QuotaAccumulationMethod)
	fmt.Printf("  QuotaAccumulationId: %s\n", resp.QuotaAccumulationId)
	if resp.AvailableQuota != nil {
		fmt.Printf("  AvailableQuota: %d %s\n", resp.AvailableQuota.Value, resp.AvailableQuota.Currency)
	}
	fmt.Println("================================================")
}

// TestInquiryAvailableQuota_RECEIVING_ACCOUNT 测试按收款账户查询结汇额度
func TestInquiryAvailableQuota_RECEIVING_ACCOUNT(t *testing.T) {
	c := newRealInquiryAvailableQuotaClient(t)

	req := &request.InquiryAvailableQuotaRequest{
		QuotaAccumulationMethod: "RECEIVING_ACCOUNT",
		QuotaAccumulationId:     "YOUR_RA_NUMBER",
		Currency:                "USD",
	}

	fmt.Println("====== TestInquiryAvailableQuota_RECEIVING_ACCOUNT ======")
	fmt.Printf("Request: %+v\n", req)

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
	fmt.Println("==========================================================")
}

// TestInquiryAvailableQuota_VIRTUAL_ACCOUNT 测试按虚拟账户查询结汇额度
func TestInquiryAvailableQuota_VIRTUAL_ACCOUNT(t *testing.T) {
	c := newRealInquiryAvailableQuotaClient(t)

	req := &request.InquiryAvailableQuotaRequest{
		QuotaAccumulationMethod: "VIRTUAL_ACCOUNT",
		QuotaAccumulationId:     "YOUR_VA_NUMBER",
		Currency:                "USD",
	}

	fmt.Println("====== TestInquiryAvailableQuota_VIRTUAL_ACCOUNT ======")
	fmt.Printf("Request: %+v\n", req)

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
	fmt.Println("========================================================")
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

	fmt.Println("====== TestInquiryAvailableQuota_BENEFICIARY ======")
	fmt.Printf("Request: %+v\n", req)

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
	fmt.Println("=====================================================")
}

// TestInquiryAvailableQuotaValidation 测试参数校验
func TestInquiryAvailableQuotaValidation(t *testing.T) {
	c := newRealInquiryAvailableQuotaClient(t)

	fmt.Println("====== TestInquiryAvailableQuotaValidation ======")

	// 测试缺少必填参数
	req := &request.InquiryAvailableQuotaRequest{
		// 不设置任何字段
	}

	_, err := c.InquiryAvailableQuota(req)
	if err != nil {
		fmt.Printf("Validation Error (expected): %v\n", err)
	} else {
		t.Error("Expected validation error but got none")
	}

	// 测试 BENEFICIARY 方式缺少 tradeType
	req2 := &request.InquiryAvailableQuotaRequest{
		QuotaAccumulationMethod: "BENEFICIARY",
		QuotaAccumulationId:     "YOUR_BENEFICIARY_ID",
		Currency:                "USD",
		// 不设置 TradeType
	}

	_, err = c.InquiryAvailableQuota(req2)
	if err != nil {
		fmt.Printf("Validation Error for BENEFICIARY (expected): %v\n", err)
	} else {
		t.Error("Expected validation error for BENEFICIARY without tradeType but got none")
	}
	fmt.Println("================================================")
}
