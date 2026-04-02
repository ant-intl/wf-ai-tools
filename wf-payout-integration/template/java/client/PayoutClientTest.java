/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf;

import {basePackage}.wf.client.PayoutClient;
import {basePackage}.wf.config.WfConfig;
import {basePackage}.wf.exception.WfException;
import {basePackage}.wf.model.domain.Amount;
import {basePackage}.wf.model.domain.PaymentMethodMetaData;
import {basePackage}.wf.model.domain.TransferFromDetail;
import {basePackage}.wf.model.domain.TransferResult;
import {basePackage}.wf.model.domain.TransferQuote;
import {basePackage}.wf.model.domain.TransferToDetail;
import {basePackage}.wf.model.domain.TransferToMethod;
import {basePackage}.wf.model.request.ConsultPayoutRequest;
import {basePackage}.wf.model.request.CreatePayoutRequest;
import {basePackage}.wf.model.request.InquiryPayoutRequest;
import {basePackage}.wf.model.response.ConsultPayoutResponse;
import {basePackage}.wf.model.response.CreatePayoutResponse;
import {basePackage}.wf.model.response.InquiryPayoutResponse;
import org.junit.Before;
import org.junit.Test;

/**
 * PayoutClient 集成测试（真实签名模式）。
 *
 * @author Qoder
 * @version PayoutClientTest.java, v 0.1 2026-03-27
 */
public class PayoutClientTest {

    private PayoutClient client;

    @Before
    public void setUp() {
        WfConfig config = new WfConfig();
        client = new PayoutClient(config);
        client.init();
    }

    // =========================================================================
    // consultPayout
    // =========================================================================

    /**
     * 测试跨币种咨询：USD -> CNY，获取汇率报价 quoteId。
     */
    @Test
    public void testConsultPayoutCrossCurrency() {
        Amount transferFromAmount = new Amount();
        transferFromAmount.setCurrency("USD");
        transferFromAmount.setValue(10000L); // 100.00 USD

        TransferFromDetail fromDetail = new TransferFromDetail();
        fromDetail.setTransferFromAmount(transferFromAmount);

        Amount transferToAmount = new Amount();
        transferToAmount.setCurrency("CNY");
        // value 留空，WF 自动根据汇率计算

        PaymentMethodMetaData metaData = new PaymentMethodMetaData();
        metaData.setBankAccountName("vaL2LTest");
        metaData.setBankAccountNo("100100004623");
        metaData.setBankName("STARK bankName");
        metaData.setBankBIC("CITIHKHX");
        metaData.setBankCountryCode("CN");
        metaData.setBeneficiaryType("THIRD_PARTY_PERSONAL_BANK_ACCOUNT");

        TransferToMethod toMethod = new TransferToMethod();
        toMethod.setPaymentMethodType("BANK_ACCOUNT_DETAIL");
        toMethod.setPaymentMethodMetaData(metaData);

        TransferToDetail toDetail = new TransferToDetail();
        toDetail.setTransferToAmount(transferToAmount);
        toDetail.setTransferToMethod(toMethod);

        ConsultPayoutRequest request = new ConsultPayoutRequest();
        request.setTransferFromDetail(fromDetail);
        request.setTransferToDetail(toDetail);
        request.setBusinessSceneCode("THIRD_PARTY_PAYOUT"); // CNY 必填

        System.out.println("====== testConsultPayoutCrossCurrency ======");
        try {
            ConsultPayoutResponse response = client.consultPayout(request);
            printConsultPayoutResponse(response);
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }
        System.out.println("==============================================");
    }

    /**
     * 测试跨币种代发完整流程：consultPayout -> createPayout。
     */
    @Test
    public void testCrossCurrencyPayoutFlow() {
        // Step 1: consultPayout 获取 quoteId
        Amount fromAmountConsult = new Amount();
        fromAmountConsult.setCurrency("USD");
        fromAmountConsult.setValue(10000L);

        TransferFromDetail fromDetailConsult = new TransferFromDetail();
        fromDetailConsult.setTransferFromAmount(fromAmountConsult);

        Amount toAmountConsult = new Amount();
        toAmountConsult.setCurrency("CNY");

        PaymentMethodMetaData metaData = new PaymentMethodMetaData();
        metaData.setBankAccountName("vaL2LTest");
        metaData.setBankAccountNo("100100004623");
        metaData.setBankName("STARK bankName");
        metaData.setBankBIC("CITIHKHX");
        metaData.setBankCountryCode("CN");
        metaData.setBeneficiaryType("THIRD_PARTY_PERSONAL_BANK_ACCOUNT");

        TransferToMethod toMethodConsult = new TransferToMethod();
        toMethodConsult.setPaymentMethodType("BANK_ACCOUNT_DETAIL");
        toMethodConsult.setPaymentMethodMetaData(metaData);

        TransferToDetail toDetailConsult = new TransferToDetail();
        toDetailConsult.setTransferToAmount(toAmountConsult);
        toDetailConsult.setTransferToMethod(toMethodConsult);

        ConsultPayoutRequest consultRequest = new ConsultPayoutRequest();
        consultRequest.setTransferFromDetail(fromDetailConsult);
        consultRequest.setTransferToDetail(toDetailConsult);
        consultRequest.setBusinessSceneCode("THIRD_PARTY_PAYOUT");

        System.out.println("====== testCrossCurrencyPayoutFlow ======");
        String quoteId = null;
        try {
            ConsultPayoutResponse consultResponse = client.consultPayout(consultRequest);
            quoteId = consultResponse.getQuoteId();
            System.out.println("Step 1 - ConsultPayout Success");
            printConsultPayoutResponse(consultResponse);
        } catch (WfException e) {
            System.out.println("ConsultPayout Failed: " + e.getErrorCode() + " - " + e.getMessage());
            return;
        }

        // Step 2: createPayout 传入 quoteId
        if (quoteId == null) {
            System.out.println("quoteId is null, skip createPayout");
            return;
        }

        Amount fromAmount = new Amount();
        fromAmount.setCurrency("USD");
        fromAmount.setValue(10000L);

        TransferFromDetail fromDetail = new TransferFromDetail();
        fromDetail.setTransferFromAmount(fromAmount);

        Amount toAmount = new Amount();
        toAmount.setCurrency("CNY");

        TransferQuote transferQuote = new TransferQuote();
        transferQuote.setQuoteId(quoteId);

        TransferToMethod toMethod = new TransferToMethod();
        toMethod.setPaymentMethodType("BANK_ACCOUNT_DETAIL");
        toMethod.setPaymentMethodMetaData(metaData);

        TransferToDetail toDetail = new TransferToDetail();
        toDetail.setTransferToAmount(toAmount);
        toDetail.setTransferToMethod(toMethod);
        toDetail.setTransferQuote(transferQuote);
        toDetail.setPurposeCode("GDS");

        CreatePayoutRequest createRequest = new CreatePayoutRequest();
        createRequest.setTransferRequestId("CROSS_CURRENCY_" + System.currentTimeMillis());
        createRequest.setTransferFromDetail(fromDetail);
        createRequest.setTransferToDetail(toDetail);
        createRequest.setBusinessSceneCode("THIRD_PARTY_PAYOUT");

        try {
            CreatePayoutResponse createResponse = client.createPayout(createRequest);
            System.out.println("Step 2 - CreatePayout Success");
            printCreatePayoutResponse(createResponse);
        } catch (WfException e) {
            System.out.println("CreatePayout Failed: " + e.getErrorCode() + " - " + e.getMessage());
        }
        System.out.println("==========================================");
    }

    // =========================================================================
    // createPayout
    // =========================================================================

    @Test
    public void testCreatePayoutCardDetail() {
        Amount transferFromAmount = new Amount();
        transferFromAmount.setCurrency("USD");

        TransferFromDetail fromDetail = new TransferFromDetail();
        fromDetail.setTransferFromAmount(transferFromAmount);

        Amount transferToAmount = new Amount();
        transferToAmount.setCurrency("USD");
        transferToAmount.setValue(10000L); // 100.00 USD

        PaymentMethodMetaData metaData = new PaymentMethodMetaData();
        metaData.setBankAccountName("vaL2LTest");
        metaData.setBankAccountNo("100100004623");
        metaData.setBankName("STARK bankName");
        metaData.setBankBIC("CITIHKHX");
        metaData.setBankCountryCode("HK");
        metaData.setBeneficiaryType("THIRD_PARTY_PERSONAL_BANK_ACCOUNT");

        TransferToMethod toMethod = new TransferToMethod();
        toMethod.setPaymentMethodType("BANK_ACCOUNT_DETAIL");
        toMethod.setPaymentMethodMetaData(metaData);

        TransferToDetail toDetail = new TransferToDetail();
        toDetail.setTransferToAmount(transferToAmount);
        toDetail.setTransferToMethod(toMethod);
        toDetail.setPurposeCode("GDS");

        CreatePayoutRequest request = new CreatePayoutRequest();
        request.setTransferRequestId("PAYOUT_CARD_" + System.currentTimeMillis());
        request.setTransferFromDetail(fromDetail);
        request.setTransferToDetail(toDetail);

        System.out.println("====== testCreatePayoutCardDetail ======");
        try {
            CreatePayoutResponse response = client.createPayout(request);
            printCreatePayoutResponse(response);
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }
        System.out.println("============================================");
    }

    @Test
    public void testCreatePayoutTokenMode() {
        Amount transferFromAmount = new Amount();
        transferFromAmount.setCurrency("USD");

        TransferFromDetail fromDetail = new TransferFromDetail();
        fromDetail.setTransferFromAmount(transferFromAmount);

        Amount transferToAmount = new Amount();
        transferToAmount.setCurrency("USD");
        transferToAmount.setValue(10000L);

        TransferToMethod toMethod = new TransferToMethod();
        toMethod.setPaymentMethodType("BENEFICIARY_TOKEN");
        toMethod.setPaymentMethodId("YOUR_BENEFICIARY_TOKEN");

        TransferToDetail toDetail = new TransferToDetail();
        toDetail.setTransferToAmount(transferToAmount);
        toDetail.setTransferToMethod(toMethod);
        toDetail.setPurposeCode("GDS");

        CreatePayoutRequest request = new CreatePayoutRequest();
        request.setTransferRequestId("PAYOUT_TOKEN_" + System.currentTimeMillis());
        request.setTransferFromDetail(fromDetail);
        request.setTransferToDetail(toDetail);

        System.out.println("====== testCreatePayoutTokenMode ======");
        try {
            CreatePayoutResponse response = client.createPayout(request);
            printCreatePayoutResponse(response);
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }
        System.out.println("========================================");
    }

    @Test
    public void testCreatePayoutFromAmount() {
        Amount transferFromAmount = new Amount();
        transferFromAmount.setCurrency("USD");
        transferFromAmount.setValue(10050L); // 100.50 USD

        TransferFromDetail fromDetail = new TransferFromDetail();
        fromDetail.setTransferFromAmount(transferFromAmount);

        Amount transferToAmount = new Amount();
        transferToAmount.setCurrency("USD");

        PaymentMethodMetaData metaData = new PaymentMethodMetaData();
        metaData.setBankAccountName("vaL2LTest");
        metaData.setBankAccountNo("100100004623");
        metaData.setBankName("STARK bankName");
        metaData.setBankBIC("CITIHKHX");
        metaData.setBankCountryCode("HK");
        metaData.setBeneficiaryType("THIRD_PARTY_PERSONAL_BANK_ACCOUNT");

        TransferToMethod toMethod = new TransferToMethod();
        toMethod.setPaymentMethodType("BANK_ACCOUNT_DETAIL");
        toMethod.setPaymentMethodMetaData(metaData);

        TransferToDetail toDetail = new TransferToDetail();
        toDetail.setTransferToAmount(transferToAmount);
        toDetail.setTransferToMethod(toMethod);
        toDetail.setPurposeCode("GDS");

        CreatePayoutRequest request = new CreatePayoutRequest();
        request.setTransferRequestId("PAYOUT_FROM_" + System.currentTimeMillis());
        request.setTransferFromDetail(fromDetail);
        request.setTransferToDetail(toDetail);

        System.out.println("====== testCreatePayoutFromAmount ======");
        try {
            CreatePayoutResponse response = client.createPayout(request);
            printCreatePayoutResponse(response);
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }
        System.out.println("=========================================");
    }

    // =========================================================================
    // inquiryPayout
    // =========================================================================

    @Test
    public void testInquiryPayoutByRequestId() {
        InquiryPayoutRequest request = new InquiryPayoutRequest();
        request.setTransferRequestId("PAYOUT_PLAIN_YOUR_TIMESTAMP");

        System.out.println("====== testInquiryPayoutByRequestId ======");
        try {
            InquiryPayoutResponse response = client.inquiryPayout(request);
            printInquiryPayoutResponse(response);
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }
        System.out.println("==========================================");
    }

    @Test
    public void testInquiryPayoutByTransferId() {
        InquiryPayoutRequest request = new InquiryPayoutRequest();
        request.setTransferId("YOUR_TRANSFER_ID");

        System.out.println("====== testInquiryPayoutByTransferId ======");
        try {
            InquiryPayoutResponse response = client.inquiryPayout(request);
            printInquiryPayoutResponse(response);
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }
        System.out.println("===========================================");
    }

    // =========================================================================
    // Helpers
    // =========================================================================

    private void printConsultPayoutResponse(ConsultPayoutResponse response) {
        System.out.println("ResultStatus: " + response.getResult().getResultStatus());
        System.out.println("ResultCode: " + response.getResult().getResultCode());
        System.out.println("QuoteId: " + response.getQuoteId());
        System.out.println("ChargeMode: " + response.getChargeMode());
        if (response.getTransferToDetail() != null && response.getTransferToDetail().getTransferQuote() != null) {
            TransferQuote quote = response.getTransferToDetail().getTransferQuote();
            System.out.println("QuoteCurrencyPair: " + quote.getQuoteCurrencyPair());
            System.out.println("QuotePrice: " + quote.getQuotePrice());
            System.out.println("QuoteExpiryTime: " + quote.getQuoteExpiryTime());
        }
        if (response.getAvailableQuota() != null) {
            System.out.println("AvailableQuota: " + response.getAvailableQuota().getValue()
                + " " + response.getAvailableQuota().getCurrency());
        }
        System.out.println("Response: " + response);
    }

    private void printCreatePayoutResponse(CreatePayoutResponse response) {
        System.out.println("ResultStatus: " + response.getResult().getResultStatus());
        System.out.println("ResultCode: " + response.getResult().getResultCode());
        System.out.println("TransferId: " + response.getTransferId());
        System.out.println("ChargeMode: " + response.getChargeMode());
        if (response.isProcessing()) {
            System.out.println("Status: PROCESSING - call inquiryPayout to get final result");
        }
        System.out.println("Response: " + response);
    }

    private void printInquiryPayoutResponse(InquiryPayoutResponse response) {
        System.out.println("API ResultStatus: " + response.getResult().getResultStatus());
        TransferResult transferResult = response.getTransferResult();
        if (transferResult != null) {
            System.out.println("Transfer ResultStatus: " + transferResult.getResultStatus());
            System.out.println("Transfer ResultCode: " + transferResult.getResultCode());
            if (transferResult.isSuccess()) {
                System.out.println("Status: SUCCESS");
                System.out.println("TransferId: " + response.getTransferId());
                System.out.println("TransferFinishTime: " + response.getTransferFinishTime());
                System.out.println("ChargeMode: " + response.getChargeMode());
            } else if (transferResult.isProcessing()) {
                System.out.println("Status: PROCESSING - continue polling");
            } else {
                System.out.println("Status: FAILED - " + transferResult.getResultCode()
                    + " " + transferResult.getResultMessage());
            }
        }
        System.out.println("Full Response: " + response);
    }
}
