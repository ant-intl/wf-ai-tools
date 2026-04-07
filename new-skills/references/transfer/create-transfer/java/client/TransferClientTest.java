/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf;

import {basePackage}.wf.client.TransferClient;
import {basePackage}.wf.config.WfConfig;
import {basePackage}.wf.exception.WfException;
import {basePackage}.wf.model.domain.Amount;
import {basePackage}.wf.model.domain.TransferFromDetail;
import {basePackage}.wf.model.domain.TransferToDetail;
import {basePackage}.wf.model.request.CreateTransferRequest;
import {basePackage}.wf.model.response.CreateTransferResponse;
import org.junit.Before;
import org.junit.Test;

/**
 * TransferClient 集成测试（真实签名模式）。
 *
 * @author Qoder
 * @version TransferClientTest.java, v 0.1 2026-04-01
 */
public class TransferClientTest {

    private TransferClient client;

    @Before
    public void setUp() {
        WfConfig config = new WfConfig();
        client = new TransferClient(config);
        client.init();
    }

    // =========================================================================
    // createTransfer
    // =========================================================================

    @Test
    public void testCreateTransfer() {
        Amount transferFromAmount = new Amount();
        transferFromAmount.setCurrency("USD");

        TransferFromDetail fromDetail = new TransferFromDetail();
        fromDetail.setTransferFromAmount(transferFromAmount);

        Amount transferToAmount = new Amount();
        transferToAmount.setCurrency("USD");
        transferToAmount.setValue(10000L); // 100.00 USD

        TransferToDetail toDetail = new TransferToDetail();
        toDetail.setTransferToAmount(transferToAmount);

        CreateTransferRequest request = new CreateTransferRequest();
        request.setTransferRequestId("TRANSFER_" + System.currentTimeMillis());
        request.setTransferFromDetail(fromDetail);
        request.setTransferToDetail(toDetail);

        System.out.println("====== testCreateTransfer ======");
        try {
            CreateTransferResponse response = client.createTransfer(request);
            printCreateTransferResponse(response);
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }
        System.out.println("================================");
    }

    @Test
    public void testCreateTransferMultiAccount() {
        Amount transferFromAmount = new Amount();
        transferFromAmount.setCurrency("USD");

        TransferFromDetail fromDetail = new TransferFromDetail();
        fromDetail.setTransferFromAmount(transferFromAmount);

        Amount transferToAmount = new Amount();
        transferToAmount.setCurrency("USD");
        transferToAmount.setValue(5000L); // 50.00 USD

        TransferToDetail toDetail = new TransferToDetail();
        toDetail.setTransferToAmount(transferToAmount);

        CreateTransferRequest request = new CreateTransferRequest();
        request.setTransferRequestId("TRANSFER_MULTI_" + System.currentTimeMillis());
        request.setBusinessSceneCode("MULTI_ACCOUNT_TRANSFER");
        request.setTransferFromDetail(fromDetail);
        request.setTransferToDetail(toDetail);

        System.out.println("====== testCreateTransferMultiAccount ======");
        try {
            CreateTransferResponse response = client.createTransfer(request);
            printCreateTransferResponse(response);
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }
        System.out.println("============================================");
    }

    // =========================================================================
    // Helpers
    // =========================================================================

    private void printCreateTransferResponse(CreateTransferResponse response) {
        System.out.println("ResultStatus: " + response.getResult().getResultStatus());
        System.out.println("ResultCode: " + response.getResult().getResultCode());
        System.out.println("TransferId: " + response.getTransferId());
        System.out.println("BusinessSceneCode: " + response.getBusinessSceneCode());
        if (response.isProcessing()) {
            System.out.println("Status: PROCESSING - call inquiryTransfer to get final result");
        }
        System.out.println("Response: " + response);
    }
}

