/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf;

import {basePackage}.wf.client.statement.InquiryStatementDetailClient;
import {basePackage}.wf.config.WfConfig;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.InquiryStatementDetailRequest;
import {basePackage}.wf.model.response.InquiryStatementDetailResponse;
import {basePackage}.wf.signer.WfSigner;
import {basePackage}.wf.util.WfHttpClientUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * InquiryStatementDetailClient 集成测试。
 *
 * <p>Mock WfConfig，不 Mock WfHttpClientUtil（请求真实发往 WF 接口）。
 * WfSigner 根据签名模式决定是否 Mock：
 * <ul>
 *   <li>Mock 签名模式：generateSignature 固定返回 "TESTING_SIGNATURE"，WF 返回 INVALID_SIGNATURE</li>
 *   <li>真实签名模式：使用真实密钥路径，可完整跑通接口</li>
 * </ul>
 *
 * @author Qoder
 * @version InquiryStatementDetailClientTest.java, v 0.1 2026-04-08
 */
public class InquiryStatementDetailClientTest {

    private static final String CLIENT_ID = "YOUR_CLIENT_ID";
    private static final String BASE_URL = "https://open-sitprod-sg.alipay.com";

    private InquiryStatementDetailClient client;
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
        client = new InquiryStatementDetailClient();
        client.setConfig(mockConfig);
        client.setHttpClientUtil(httpClientUtil);

        // --- 真实签名模式（需要替换下方密钥路径并切换此段代码）---
        // Mockito.when(mockConfig.getPrivateKeyPath()).thenReturn("/path/to/your/private_key.pem");
        // Mockito.when(mockConfig.getPublicKeyPath()).thenReturn("/path/to/your/wf_public_key.pem");
        // client = new InquiryStatementDetailClient();
        // client.setConfig(mockConfig);
        // client.init();
    }

    /**
     * 测试查询账单流水详情。
     * 请将 accountingBizNo 替换为通过 inquiryStatementList 获取的真实值。
     */
    @Test
    public void testInquiryStatementDetail() {
        InquiryStatementDetailRequest request = new InquiryStatementDetailRequest();
        request.setAccountingBizNo("YOUR_ACCOUNTING_BIZ_NO");

        System.out.println("====== testInquiryStatementDetail ======");
        System.out.println("Request: accountingBizNo=" + request.getAccountingBizNo());

        try {
            InquiryStatementDetailResponse response = client.inquiryStatementDetail(request);
            System.out.println("Response: " + response);
            System.out.println("ResultStatus: " + response.getResult().getResultStatus());
            System.out.println("TransactionId: " + response.getTransactionId());
            System.out.println("TransactionStatus: " + response.getTransactionStatus());
            System.out.println("TransactionType: " + response.getTransactionType());
            System.out.println("TransactionTime: " + response.getTransactionTime());
            if (response.getTransactionAmount() != null) {
                System.out.println("TransactionAmount: " + response.getTransactionAmount());
            }
            if (response.getFundMoveDetail() != null) {
                System.out.println("FundMoveDetail: " + response.getFundMoveDetail());
            }
            if (response.getCombinedTransactionList() != null) {
                System.out.println("CombinedTransactionList size: " + response.getCombinedTransactionList().size());
            }
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }

        System.out.println("========================================");
    }
}
