/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf;

import {basePackage}.wf.client.account.InquiryAccountInfoClient;
import {basePackage}.wf.config.WfConfig;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.InquiryBalanceRequest;
import {basePackage}.wf.model.request.InquiryAvailableQuotaRequest;
import {basePackage}.wf.model.response.InquiryBalanceResponse;
import {basePackage}.wf.model.response.InquiryAvailableQuotaResponse;
import {basePackage}.wf.signer.WfSigner;
import {basePackage}.wf.util.WfHttpClientUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * InquiryAccountInfoClient 集成测试。
 *
 * <p>包含 inquiryBalance 和 inquiryAvailableQuota 两个接口的测试。
 * Mock WfConfig，不 Mock WfHttpClientUtil（请求真实发往 WF 接口）。
 * WfSigner 根据签名模式决定是否 Mock：
 * <ul>
 *   <li>Mock 签名模式：generateSignature 固定返回 "TESTING_SIGNATURE"，WF 返回 INVALID_SIGNATURE</li>
 *   <li>真实签名模式：使用真实密钥路径，可完整跑通接口</li>
 * </ul>
 *
 * @author Qoder
 * @version InquiryAccountInfoClientTest.java, v 0.1 2026-04-14
 */
public class InquiryAccountInfoClientTest {

    private static final String CLIENT_ID = "YOUR_CLIENT_ID";
    private static final String BASE_URL = "https://open-sitprod-sg.alipay.com";

    private InquiryAccountInfoClient client;
    private WfConfig mockConfig;

    @Before
    public void setUp() {
        mockConfig = Mockito.mock(WfConfig.class);
        Mockito.when(mockConfig.getClientId()).thenReturn(CLIENT_ID);
        Mockito.when(mockConfig.getBaseUrl()).thenReturn(BASE_URL);
        Mockito.when(mockConfig.getConnectTimeout()).thenReturn(10000);
        Mockito.when(mockConfig.getReadTimeout()).thenReturn(30000);

        // --- Mock 签名模式（跳过验签，WF 将返回 INVALID_SIGNATURE）---
        WfSigner mockSigner = Mockito.mock(WfSigner.class);
        Mockito.when(mockSigner.generateSignature(
                Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
            .thenReturn("TESTING_SIGNATURE");
        Mockito.when(mockSigner.verifySignature(
                Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
            .thenReturn(true);

        WfHttpClientUtil httpClientUtil = new WfHttpClientUtil(mockConfig, mockSigner);
        client = new InquiryAccountInfoClient();
        client.setConfig(mockConfig);
        client.setHttpClientUtil(httpClientUtil);

        // --- 真实签名模式（需要替换下方密钥路径并切换此段代码）---
        // Mockito.when(mockConfig.getPrivateKeyPath()).thenReturn("/path/to/your/private_key.pem");
        // Mockito.when(mockConfig.getPublicKeyPath()).thenReturn("/path/to/your/wf_public_key.pem");
        // client = new InquiryAccountInfoClient();
        // client.setConfig(mockConfig);
        // client.init();
    }

    // ==================== inquiryBalance Tests ====================

    /**
     * 测试查询所有币种余额。
     */
    @Test
    public void testInquiryBalance() {
        InquiryBalanceRequest request = new InquiryBalanceRequest();
        // 不传 currencyList 则查询所有币种

        System.out.println("====== testInquiryBalance ======");
        System.out.println("Request: " + request);

        try {
            InquiryBalanceResponse response = client.inquiryBalance(request);
            System.out.println("Response: " + response);
            System.out.println("ResultStatus: " + response.getResult().getResultStatus());
            if (response.getAccountBalanceList() != null) {
                System.out.println("Balance count: " + response.getAccountBalanceList().size());
            }
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }

        System.out.println("================================");
    }

    // ==================== inquiryAvailableQuota Tests ====================

    /**
     * 测试按用户ID查询结汇额度。
     */
    @Test
    public void testInquiryAvailableQuotaByUserId() {
        InquiryAvailableQuotaRequest request = new InquiryAvailableQuotaRequest();
        request.setQuotaAccumulationMethod("USER_ID");
        request.setQuotaAccumulationId("YOUR_USER_ID");
        request.setCurrency("USD");

        System.out.println("====== testInquiryAvailableQuotaByUserId ======");
        System.out.println("Request: " + request);

        try {
            InquiryAvailableQuotaResponse response = client.inquiryAvailableQuota(request);
            System.out.println("Response: " + response);
            System.out.println("ResultStatus: " + response.getResult().getResultStatus());
            if (response.getAvailableQuota() != null) {
                System.out.println("AvailableQuota: " + response.getAvailableQuota().getValue()
                    + " " + response.getAvailableQuota().getCurrency());
            }
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }

        System.out.println("================================================");
    }

    /**
     * 测试按收款账户查询结汇额度。
     */
    @Test
    public void testInquiryAvailableQuotaByReceivingAccount() {
        InquiryAvailableQuotaRequest request = new InquiryAvailableQuotaRequest();
        request.setQuotaAccumulationMethod("RECEIVING_ACCOUNT");
        request.setQuotaAccumulationId("YOUR_RA_NUMBER");
        request.setCurrency("USD");

        System.out.println("====== testInquiryAvailableQuotaByReceivingAccount ======");
        System.out.println("Request: " + request);

        try {
            InquiryAvailableQuotaResponse response = client.inquiryAvailableQuota(request);
            System.out.println("Response: " + response);
            System.out.println("ResultStatus: " + response.getResult().getResultStatus());
            if (response.getAvailableQuota() != null) {
                System.out.println("AvailableQuota: " + response.getAvailableQuota().getValue()
                    + " " + response.getAvailableQuota().getCurrency());
            }
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }

        System.out.println("==========================================================");
    }

    /**
     * 测试按虚拟账户查询结汇额度。
     */
    @Test
    public void testInquiryAvailableQuotaByVirtualAccount() {
        InquiryAvailableQuotaRequest request = new InquiryAvailableQuotaRequest();
        request.setQuotaAccumulationMethod("VIRTUAL_ACCOUNT");
        request.setQuotaAccumulationId("YOUR_VA_NUMBER");
        request.setCurrency("USD");

        System.out.println("====== testInquiryAvailableQuotaByVirtualAccount ======");
        System.out.println("Request: " + request);

        try {
            InquiryAvailableQuotaResponse response = client.inquiryAvailableQuota(request);
            System.out.println("Response: " + response);
            System.out.println("ResultStatus: " + response.getResult().getResultStatus());
            if (response.getAvailableQuota() != null) {
                System.out.println("AvailableQuota: " + response.getAvailableQuota().getValue()
                    + " " + response.getAvailableQuota().getCurrency());
            }
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }

        System.out.println("========================================================");
    }

    /**
     * 测试按收款人查询结汇额度（需要传入 tradeType）。
     */
    @Test
    public void testInquiryAvailableQuotaByBeneficiary() {
        InquiryAvailableQuotaRequest request = new InquiryAvailableQuotaRequest();
        request.setQuotaAccumulationMethod("BENEFICIARY");
        request.setQuotaAccumulationId("YOUR_BENEFICIARY_ID");
        request.setCurrency("USD");
        request.setTradeType("GOODS"); // GOODS 或 SERVICE

        System.out.println("====== testInquiryAvailableQuotaByBeneficiary ======");
        System.out.println("Request: " + request);

        try {
            InquiryAvailableQuotaResponse response = client.inquiryAvailableQuota(request);
            System.out.println("Response: " + response);
            System.out.println("ResultStatus: " + response.getResult().getResultStatus());
            System.out.println("TradeType: " + response.getTradeType());
            if (response.getAvailableQuota() != null) {
                System.out.println("AvailableQuota: " + response.getAvailableQuota().getValue()
                    + " " + response.getAvailableQuota().getCurrency());
            }
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }

        System.out.println("=====================================================");
    }
}
