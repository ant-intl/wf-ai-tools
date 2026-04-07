/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import {basePackage}.wf.client.TradeOrderManagementClient;
import {basePackage}.wf.config.WfConfig;
import {basePackage}.wf.exception.WfException;
import {basePackage}.wf.model.domain.Amount;
import {basePackage}.wf.model.domain.Address;
import {basePackage}.wf.model.domain.AttachmentInfo;
import {basePackage}.wf.model.domain.BizContractInfo;
import {basePackage}.wf.model.domain.Buyer;
import {basePackage}.wf.model.domain.BuyerName;
import {basePackage}.wf.model.domain.Customer;
import {basePackage}.wf.model.domain.Goods;
import {basePackage}.wf.model.domain.Merchant;
import {basePackage}.wf.model.domain.Shipping;
import {basePackage}.wf.model.domain.Store;
import {basePackage}.wf.model.domain.TradeOrder;
import {basePackage}.wf.model.domain.TradeOrderResult;
import {basePackage}.wf.model.domain.WayBillInfo;
import {basePackage}.wf.model.request.InquiryTradeOrderRequest;
import {basePackage}.wf.model.request.SubmitTradeOrderRequest;
import {basePackage}.wf.model.response.InquiryTradeOrderResponse;
import {basePackage}.wf.model.response.SubmitTradeOrderResponse;
import org.junit.Before;
import org.junit.Test;

/**
 * TradeOrderManagementClient 集成测试（真实签名模式）。
 *
 * @author Qoder
 * @version TradeOrderManagementClientTest.java, v 0.1 2026-04-03
 */
public class TradeOrderManagementClientTest {

    private TradeOrderManagementClient client;

    @Before
    public void setUp() {
        WfConfig config = new WfConfig();
        client = new TradeOrderManagementClient(config);
        client.init();
    }

    // =========================================================================
    // submitTradeOrder — PAY_INTO_CHINA (B2C)
    // =========================================================================

    @Test
    public void testSubmitTradeOrderB2C() {
        String now = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        // 商品信息
        Goods goods = new Goods();
        goods.setGoodsName("Test Product");
        goods.setGoodsCategory("Electronics");
        goods.setGoodsQuantity("1");

        // 物流信息
        WayBillInfo wayBillInfo = new WayBillInfo();
        wayBillInfo.setShippingOrderReferenceNo("WB_" + System.currentTimeMillis());

        Address shippingAddress = new Address();
        shippingAddress.setRegion("US");
        shippingAddress.setState("CA");
        shippingAddress.setCity("San Francisco");
        shippingAddress.setAddress1("123 Main St");
        shippingAddress.setZipCode("94105");

        Shipping shipping = new Shipping();
        List<WayBillInfo> wayBillInfos = new ArrayList<>();
        wayBillInfos.add(wayBillInfo);
        shipping.setWayBillInfos(wayBillInfos);
        shipping.setShippingAddress(shippingAddress);

        // 商户信息
        Store store = new Store();
        store.setStoreShopUrl("https://www.example-store.com");

        Merchant merchant = new Merchant();
        merchant.setStore(store);

        // 卖家信息
        Customer seller = new Customer();
        seller.setCustomerId("SELLER_USER_ID");
        seller.setReferenceCustomerId("SELLER_REF_ID");

        // 买家信息
        BuyerName buyerName = new BuyerName();
        buyerName.setFullName("John Doe");
        buyerName.setFirstName("John");
        buyerName.setLastName("Doe");

        Buyer buyer = new Buyer();
        buyer.setReferenceBuyerId("BUYER_REF_001");
        buyer.setBuyerName(buyerName);
        buyer.setBuyerEmail("john.doe@example.com");
        buyer.setBuyerCountry("US");

        // 交易订单
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setReferenceOrderNo("B2C_ORDER_" + System.currentTimeMillis());
        tradeOrder.setPaymentTime(now);
        tradeOrder.setOrderTime(now);
        tradeOrder.setOrderType("LOAN");
        tradeOrder.setTradeType("GOODS");
        tradeOrder.setTransAmount(new Amount("USD", 10000L));
        tradeOrder.setTradeAmount(new Amount("USD", 9500L));
        tradeOrder.setMerchant(merchant);
        tradeOrder.setSeller(seller);
        tradeOrder.setBuyer(buyer);
        tradeOrder.setGoods(Collections.singletonList(goods));
        tradeOrder.setShipping(shipping);

        // 请求
        SubmitTradeOrderRequest request = new SubmitTradeOrderRequest();
        request.setRequestId("B2C_REQ_" + System.currentTimeMillis());
        request.setSceneCode("PAY_INTO_CHINA");
        request.setQuotaAccumulationMethod("USER_ID");
        request.setQuotaAccumulationId("SELLER_USER_ID");
        request.setPlatform("SHOPIFY");
        request.setTradeOrders(Collections.singletonList(tradeOrder));

        System.out.println("====== testSubmitTradeOrderB2C ======");
        try {
            SubmitTradeOrderResponse response = client.submitTradeOrder(request);
            printSubmitResponse(response);
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }
        System.out.println("=====================================");
    }

    // =========================================================================
    // submitTradeOrder — CREATE_B2B_ORDERS (B2B)
    // =========================================================================

    @Test
    public void testSubmitTradeOrderB2B() {
        String now = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        // 商品信息（B2B 需要额外字段）
        Goods goods = new Goods();
        goods.setGoodsName("Industrial Parts");
        goods.setGoodsQuantity("100");
        goods.setGoodsUnit("PCS");
        goods.setGoodsCnName("工业零件");
        goods.setStoreUrl("https://www.b2b-store.com/product/123");

        // 合同信息
        AttachmentInfo contractFile = new AttachmentInfo();
        contractFile.setFileName("contract.pdf");
        contractFile.setFileKey("FILE_KEY_CONTRACT_001");

        AttachmentInfo otherFile = new AttachmentInfo();
        otherFile.setFileName("invoice.pdf");
        otherFile.setFileKey("FILE_KEY_INVOICE_001");

        BizContractInfo bizContractInfo = new BizContractInfo();
        bizContractInfo.setBuyerEnName("Global Buyer Corp");
        bizContractInfo.setTradeCountry("US");
        bizContractInfo.setDeliverCountry("US");
        bizContractInfo.setContractList(Collections.singletonList(contractFile));
        bizContractInfo.setOtherAttachmentList(Collections.singletonList(otherFile));
        bizContractInfo.setAttachmentDesc("Pro-forma invoice and purchase contract");

        // 物流信息
        Shipping shipping = new Shipping();
        shipping.setIsShipped("Y");
        shipping.setIsDeclared("N");

        WayBillInfo wayBillInfo = new WayBillInfo();
        wayBillInfo.setShippingOrderReferenceNo("WB_B2B_" + System.currentTimeMillis());
        shipping.setWayBillInfos(Collections.singletonList(wayBillInfo));
        shipping.setShippingMethod("BY_SEA");

        // 交易订单
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setReferenceOrderNo("B2B_ORDER_" + System.currentTimeMillis());
        tradeOrder.setPaymentTime(now);
        tradeOrder.setTradeType("GOODS");
        tradeOrder.setTransAmount(new Amount("USD", 500000L));
        tradeOrder.setTradeAmount(new Amount("USD", 500000L));
        tradeOrder.setTradeTerms("FOB");
        tradeOrder.setIsUsedForExchange("Y");
        tradeOrder.setBizContractInfo(bizContractInfo);
        tradeOrder.setLogisticsMode("REGULAR_MODE");
        tradeOrder.setGoods(Collections.singletonList(goods));
        tradeOrder.setShipping(shipping);

        // 请求
        SubmitTradeOrderRequest request = new SubmitTradeOrderRequest();
        request.setRequestId("B2B_REQ_" + System.currentTimeMillis());
        request.setSceneCode("CREATE_B2B_ORDERS");
        request.setQuotaAccumulationMethod("USER_ID");
        request.setQuotaAccumulationId("B2B_SELLER_USER_ID");
        request.setTradeOrders(Collections.singletonList(tradeOrder));

        System.out.println("====== testSubmitTradeOrderB2B ======");
        try {
            SubmitTradeOrderResponse response = client.submitTradeOrder(request);
            printSubmitResponse(response);
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }
        System.out.println("=====================================");
    }

    // =========================================================================
    // inquiryTradeOrder
    // =========================================================================

    @Test
    public void testInquiryTradeOrder() {
        InquiryTradeOrderRequest request = new InquiryTradeOrderRequest();
        request.setRequestId("B2C_REQ_YOUR_TIMESTAMP");
        request.setSceneCode("PAY_INTO_CHINA");
        request.setQuotaAccumulationMethod("USER_ID");
        request.setQuotaAccumulationId("SELLER_USER_ID");
        request.setTradeType("GOODS");

        System.out.println("====== testInquiryTradeOrder ======");
        try {
            InquiryTradeOrderResponse response = client.inquiryTradeOrder(request);
            printInquiryResponse(response);
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }
        System.out.println("===================================");
    }

    // =========================================================================
    // Helpers
    // =========================================================================

    private void printSubmitResponse(SubmitTradeOrderResponse response) {
        System.out.println("ResultStatus: " + response.getResult().getResultStatus());
        System.out.println("ResultCode: " + response.getResult().getResultCode());
        System.out.println("RequestId: " + response.getRequestId());
        if (response.getAcceptOrderId() != null) {
            System.out.println("AcceptOrderId: " + response.getAcceptOrderId());
        }
        if (response.getTradeOrderResult() != null) {
            System.out.println("TradeOrderResults (" + response.getTradeOrderResult().size() + "):");
            for (TradeOrderResult result : response.getTradeOrderResult()) {
                System.out.println("  - OrderNo: " + result.getReferenceOrderNo()
                    + ", Status: " + result.getOrderStatus()
                    + ", Message: " + result.getStatusMessage());
            }
        }
        System.out.println("Response: " + response);
    }

    private void printInquiryResponse(InquiryTradeOrderResponse response) {
        System.out.println("ResultStatus: " + response.getResult().getResultStatus());
        System.out.println("ResultCode: " + response.getResult().getResultCode());
        System.out.println("RequestId: " + response.getRequestId());
        System.out.println("BatchStatus: " + response.getBatchStatus());
        if (response.isFinished() && response.getTradeOrderResults() != null) {
            System.out.println("TradeOrderResults (" + response.getTradeOrderResults().size() + "):");
            for (TradeOrderResult result : response.getTradeOrderResults()) {
                System.out.println("  - OrderNo: " + result.getReferenceOrderNo()
                    + ", Status: " + result.getOrderStatus()
                    + ", Message: " + result.getStatusMessage());
                if (result.getRemainAmount() != null) {
                    System.out.println("    RemainAmount: " + result.getRemainAmount().getValue()
                        + " " + result.getRemainAmount().getCurrency());
                }
            }
        } else if (response.isProcessing()) {
            System.out.println("Status: PROCESSING - continue polling");
        }
        System.out.println("Full Response: " + response);
    }
}
