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
import {basePackage}.wf.model.request.ConsultTransferRequest;
import {basePackage}.wf.model.response.ConsultTransferResponse;
import org.junit.Before;
import org.junit.Test;

/**
 * TransferClient consultTransfer 集成测试（真实签名模式）。
 *
 * @author Qoder
 * @version TransferClientTest.java, v 0.1 2026-04-07
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
    // consultTransfer
    // =========================================================================

    @Test
    public void testConsultTransfer() {
        Amount transferFromAmount = new Amount();
        transferFromAmount.setCurrency("USD");

        TransferFromDetail fromDetail = new TransferFromDetail();
        fromDetail.setTransferFromAmount(transferFromAmount);

        Amount transferToAmount = new Amount();
        transferToAmount.setCurrency("USD");
        transferToAmount.setValue(10000L); // 100.00 USD

        TransferToDetail toDetail = new TransferToDetail();
        toDetail.setTransferToAmount(transferToAmount);

        ConsultTransferRequest request = new ConsultTransferRequest();
        request.setTransferFromDetail(fromDetail);
        request.setTransferToDetail(toDetail);

        System.out.println("====== testConsultTransfer ======");
        try {
            ConsultTransferResponse response = client.consultTransfer(request);
            printConsultTransferResponse(response);
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }
        System.out.println("================================");
    }

    @Test
    public void testConsultTransferCrossCurrency() {
        Amount transferFromAmount = new Amount();
        transferFromAmount.setCurrency("USD");

        TransferFromDetail fromDetail = new TransferFromDetail();
        fromDetail.setTransferFromAmount(transferFromAmount);

        Amount transferToAmount = new Amount();
        transferToAmount.setCurrency("GBP");
        transferToAmount.setValue(5000L); // 50.00 GBP

        TransferToDetail toDetail = new TransferToDetail();
        toDetail.setTransferToAmount(transferToAmount);

        ConsultTransferRequest request = new ConsultTransferRequest();
        request.setTransferFromDetail(fromDetail);
        request.setTransferToDetail(toDetail);

        System.out.println("====== testConsultTransferCrossCurrency ======");
        try {
            ConsultTransferResponse response = client.consultTransfer(request);
            printConsultTransferResponse(response);
            if (response.getTransferToDetail() != null
                && response.getTransferToDetail().getTransferQuote() != null) {
                System.out.println("QuoteId: " + response.getTransferToDetail().getTransferQuote().getQuoteId());
                System.out.println("QuoteCurrencyPair: " + response.getTransferToDetail().getTransferQuote().getQuoteCurrencyPair());
                System.out.println("QuotePrice: " + response.getTransferToDetail().getTransferQuote().getQuotePrice());
            }
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }
        System.out.println("=============================================");
    }

    // =========================================================================
    // Helpers
    // =========================================================================

    private void printConsultTransferResponse(ConsultTransferResponse response) {
        System.out.println("ResultStatus: " + response.getResult().getResultStatus());
        System.out.println("ResultCode: " + response.getResult().getResultCode());
        if (response.getTransferFromDetail() != null) {
            System.out.println("TransferFromDetail: " + response.getTransferFromDetail());
        }
        if (response.getTransferToDetail() != null) {
            System.out.println("TransferToDetail: " + response.getTransferToDetail());
        }
        System.out.println("Response: " + response);
    }
}
