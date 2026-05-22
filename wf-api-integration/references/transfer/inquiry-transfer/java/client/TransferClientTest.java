package {basePackage}.wf;

import {basePackage}.wf.client.TransferClient;
import {basePackage}.wf.config.WfConfig;
import {basePackage}.wf.exception.WfException;
import {basePackage}.wf.model.request.InquiryTransferRequest;
import {basePackage}.wf.model.response.InquiryTransferResponse;
import org.junit.Before;
import org.junit.Test;

/**
 * TransferClient inquiryTransfer 集成测试（真实签名模式）。
 *
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
    // inquiryTransfer
    // =========================================================================

    @Test
    public void testInquiryTransfer() {
        InquiryTransferRequest request = new InquiryTransferRequest();
        request.setTransferRequestId("TRANSFER_" + System.currentTimeMillis());

        System.out.println("====== testInquiryTransfer ======");
        try {
            InquiryTransferResponse response = client.inquiryTransfer(request);
            printInquiryTransferResponse(response);
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }
        System.out.println("=================================");
    }

    @Test
    public void testInquiryTransferMultiAccount() {
        InquiryTransferRequest request = new InquiryTransferRequest();
        request.setTransferRequestId("TRANSFER_MULTI_" + System.currentTimeMillis());

        System.out.println("====== testInquiryTransferMultiAccount ======");
        try {
            InquiryTransferResponse response = client.inquiryTransfer(request);
            printInquiryTransferResponse(response);
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }
        System.out.println("=============================================");
    }

    // =========================================================================
    // Helpers
    // =========================================================================

    private void printInquiryTransferResponse(InquiryTransferResponse response) {
        System.out.println("ResultStatus: " + response.getResult().getResultStatus());
        System.out.println("ResultCode: " + response.getResult().getResultCode());
        System.out.println("TransferRequestId: " + response.getTransferRequestId());
        System.out.println("TransferId: " + response.getTransferId());
        System.out.println("BusinessSceneCode: " + response.getBusinessSceneCode());
        if (response.getTransferResult() != null) {
            System.out.println("TransferResult.Status: " + response.getTransferResult().getResultStatus());
            System.out.println("TransferResult.Code: " + response.getTransferResult().getResultCode());
            System.out.println("TransferResult.Message: " + response.getTransferResult().getResultMessage());
        }
        if (response.getTransferFinishTime() != null) {
            System.out.println("TransferFinishTime: " + response.getTransferFinishTime());
        }
        if (response.isTransferSuccess()) {
            System.out.println("Status: SUCCESS - transfer completed");
        } else if (response.isTransferProcessing()) {
            System.out.println("Status: PROCESSING - continue polling with inquiryTransfer");
        } else if (response.isTransferFailed()) {
            System.out.println("Status: FAILED - transfer failed");
        }
        System.out.println("Response: " + response);
    }
}
