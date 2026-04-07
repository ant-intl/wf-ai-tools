package client

import (
	"fmt"
	"testing"
	"time"

	"{moduleName}/wf/config"
	"{moduleName}/wf/model/domain"
	"{moduleName}/wf/model/exception"
	"{moduleName}/wf/model/request"
	"{moduleName}/wf/signer"
	"{moduleName}/wf/util"
)

func newRealTradeOrderClient(t *testing.T) *TradeOrderManagementClient {
	t.Helper()
	cfg := config.NewWfConfig(wfClientID, wfBaseURL, wfPrivateKey, wfPublicKey)
	s, err := signer.NewWfSigner(wfPrivateKey, wfPublicKey)
	if err != nil {
		t.Fatalf("Failed to create signer: %v", err)
	}
	return NewTradeOrderManagementClient(util.NewWfHttpClient(cfg, s))
}

// TestIntegration_SubmitTradeOrder_B2C tests PAY_INTO_CHINA scene with GOODS trade type
func TestIntegration_SubmitTradeOrder_B2C(t *testing.T) {
	c := newRealTradeOrderClient(t)

	now := time.Now().Format(time.RFC3339)
	transValue := int64(10000) // USD 100.00
	tradeValue := int64(9500)  // USD 95.00

	req := &request.SubmitTradeOrderRequest{
		RequestID:               fmt.Sprintf("b2c-req-%d", time.Now().UnixMilli()),
		SceneCode:               "PAY_INTO_CHINA",
		QuotaAccumulationMethod: "USER_ID",
		QuotaAccumulationID:     "SELLER_USER_ID",
		Platform:                "SHOPIFY",
		TradeOrders: []domain.TradeOrder{
			{
				ReferenceOrderNo: fmt.Sprintf("B2C_ORDER_%d", time.Now().UnixMilli()),
				PaymentTime:      now,
				OrderTime:        now,
				OrderType:        "LOAN",
				TradeType:        "GOODS",
				TransAmount:      &domain.Amount{Currency: "USD", Value: &transValue},
				TradeAmount:      &domain.Amount{Currency: "USD", Value: &tradeValue},
				Merchant: &domain.Merchant{
					Store: &domain.Store{StoreShopURL: "https://www.example-store.com"},
				},
				Seller: &domain.Customer{
					CustomerID:          "SELLER_USER_ID",
					ReferenceCustomerID: "SELLER_REF_ID",
				},
				Buyer: &domain.Buyer{
					ReferenceBuyerID: "BUYER_REF_001",
					BuyerName:        &domain.BuyerName{FullName: "John Doe", FirstName: "John", LastName: "Doe"},
					BuyerEmail:       "john.doe@example.com",
					BuyerCountry:     "US",
				},
				Goods: []domain.Goods{
					{GoodsName: "Test Product", GoodsCategory: "Electronics", GoodsQuantity: "1"},
				},
				Shipping: &domain.Shipping{
					WayBillInfos:    []domain.WayBillInfo{{ShippingOrderReferenceNo: fmt.Sprintf("WB_%d", time.Now().UnixMilli())}},
					ShippingAddress: &domain.Address{Region: "US", State: "CA", City: "San Francisco", Address1: "123 Main St", ZipCode: "94105"},
				},
			},
		},
	}

	resp, err := c.SubmitTradeOrder(req)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s\n", wfErr.Code, wfErr.Message)
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] SubmitTradeOrder B2C\n")
	fmt.Printf("  ResultCode: %s\n", resp.Result.ResultCode)
	fmt.Printf("  RequestID: %s\n", resp.RequestID)
	if len(resp.TradeOrderResult) > 0 {
		for i, r := range resp.TradeOrderResult {
			fmt.Printf("  Order[%d]: %s - %s - %s\n", i, r.ReferenceOrderNo, r.OrderStatus, r.StatusMessage)
		}
	}
}

// TestIntegration_SubmitTradeOrder_B2B tests CREATE_B2B_ORDERS scene
func TestIntegration_SubmitTradeOrder_B2B(t *testing.T) {
	c := newRealTradeOrderClient(t)

	now := time.Now().Format(time.RFC3339)
	amount := int64(500000) // USD 5000.00

	req := &request.SubmitTradeOrderRequest{
		RequestID:               fmt.Sprintf("b2b-req-%d", time.Now().UnixMilli()),
		SceneCode:               "CREATE_B2B_ORDERS",
		QuotaAccumulationMethod: "USER_ID",
		QuotaAccumulationID:     "B2B_SELLER_USER_ID",
		TradeOrders: []domain.TradeOrder{
			{
				ReferenceOrderNo: fmt.Sprintf("B2B_ORDER_%d", time.Now().UnixMilli()),
				PaymentTime:      now,
				TradeType:        "GOODS",
				TransAmount:      &domain.Amount{Currency: "USD", Value: &amount},
				TradeAmount:      &domain.Amount{Currency: "USD", Value: &amount},
				TradeTerms:       "FOB",
				IsUsedForExchange: "Y",
				LogisticsMode:    "REGULAR_MODE",
				BizContractInfo: &domain.BizContractInfo{
					BuyerEnName:    "Global Buyer Corp",
					TradeCountry:   "US",
					DeliverCountry: "US",
					ContractList:   []domain.AttachmentInfo{{FileName: "contract.pdf", FileKey: "FILE_KEY_CONTRACT_001"}},
					OtherAttachmentList: []domain.AttachmentInfo{{FileName: "invoice.pdf", FileKey: "FILE_KEY_INVOICE_001"}},
					AttachmentDesc: "Pro-forma invoice and purchase contract",
				},
				Goods: []domain.Goods{
					{GoodsName: "Industrial Parts", GoodsQuantity: "100", GoodsUnit: "PCS", GoodsCnName: "工业零件", StoreURL: "https://www.b2b-store.com/product/123"},
				},
				Shipping: &domain.Shipping{
					IsShipped:  "Y",
					IsDeclared: "N",
					WayBillInfos:    []domain.WayBillInfo{{ShippingOrderReferenceNo: fmt.Sprintf("WB_B2B_%d", time.Now().UnixMilli())}},
					ShippingMethod:  "BY_SEA",
				},
			},
		},
	}

	resp, err := c.SubmitTradeOrder(req)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s\n", wfErr.Code, wfErr.Message)
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] SubmitTradeOrder B2B\n")
	fmt.Printf("  ResultCode: %s\n", resp.Result.ResultCode)
	fmt.Printf("  AcceptOrderID: %s\n", resp.AcceptOrderID)
}

// TestIntegration_InquiryTradeOrder queries trade order status by requestId
func TestIntegration_InquiryTradeOrder(t *testing.T) {
	c := newRealTradeOrderClient(t)

	req := &request.InquiryTradeOrderRequest{
		RequestID:               "b2c-req-replace-with-real-id",
		SceneCode:               "PAY_INTO_CHINA",
		QuotaAccumulationMethod: "USER_ID",
		QuotaAccumulationID:     "SELLER_USER_ID",
		TradeType:               "GOODS",
	}

	resp, err := c.InquiryTradeOrder(req)
	if err != nil {
		if wfErr, ok := err.(*exception.WfException); ok {
			fmt.Printf("[FAIL] Code: %s | Message: %s\n", wfErr.Code, wfErr.Message)
		} else {
			fmt.Printf("[FAIL] Error: %v\n", err)
		}
		t.FailNow()
	}

	fmt.Printf("[PASS] InquiryTradeOrder\n")
	fmt.Printf("  RequestID: %s\n", resp.RequestID)
	fmt.Printf("  BatchStatus: %s\n", resp.BatchStatus)
	if resp.IsFinished() && len(resp.TradeOrderResults) > 0 {
		for i, r := range resp.TradeOrderResults {
			fmt.Printf("  Order[%d]: %s - %s - %s\n", i, r.ReferenceOrderNo, r.OrderStatus, r.StatusMessage)
		}
	} else if resp.IsProcessing() {
		fmt.Println("  Status: PROCESSING - continue polling")
	}
}
