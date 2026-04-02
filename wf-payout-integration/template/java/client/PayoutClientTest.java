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
import {basePackage}.wf.model.domain.TransferToDetail;
import {basePackage}.wf.model.domain.TransferToMethod;
import {basePackage}.wf.model.request.CreatePayoutRequest;
import {basePackage}.wf.model.request.InquiryPayoutRequest;
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
