/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf;

import {basePackage}.wf.client.InquiryStatementClient;
import {basePackage}.wf.config.WfConfig;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.InquiryStatementRequest;
import {basePackage}.wf.model.response.InquiryStatementResponse;
import {basePackage}.wf.signer.WfSigner;
import {basePackage}.wf.util.WfHttpClientUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * InquiryStatementClient 集成测试。
 *
 * <p>Mock WfConfig，不 Mock WfHttpClientUtil（请求真实发往 WF 接口）。
 * WfSigner 根据签名模式决定是否 Mock：
 * <ul>
 *   <li>Mock 签名模式：generateSignature 固定返回 "TESTING_SIGNATURE"，WF 返回 INVALID_SIGNATURE</li>
 *   <li>真实签名模式：使用真实密钥路径，可完整跑通接口</li>
 * </ul>
 *
 * @author Qoder
 * @version InquiryStatementClientTest.java, v 0.1 2026-03-27
 */
public class InquiryStatementClientTest {

    private static final String CLIENT_ID = "YOUR_CLIENT_ID";
    private static final String BASE_URL = "https://iopengw-sggz95m.alipay.com";

    private InquiryStatementClient client;
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
        client = new InquiryStatementClient();
        client.setConfig(mockConfig);
        client.setHttpClientUtil(httpClientUtil);

        // --- 真实签名模式（需要替换下方密钥路径并切换此段代码）---
        // Mockito.when(mockConfig.getPrivateKeyPath()).thenReturn("/path/to/your/private_key.pem");
        // Mockito.when(mockConfig.getPublicKeyPath()).thenReturn("/path/to/your/wf_public_key.pem");
        // client = new InquiryStatementClient();
        // client.setConfig(mockConfig);
        // client.init();
    }

    /**
     * 测试查询流水列表（第一页）。
     */
    @Test
    public void testInquiryStatementList() {
        InquiryStatementRequest request = new InquiryStatementRequest();
        request.setStartTime("2026-03-01T00:00:00+08:00");
        request.setEndTime("2026-03-27T23:59:59+08:00");
        request.setPageNumber(1);

        System.out.println("====== testInquiryStatementList ======");
        System.out.println("Request: startTime=" + request.getStartTime()
            + ", endTime=" + request.getEndTime()
            + ", pageNumber=" + request.getPageNumber());

        try {
            InquiryStatementResponse response = client.inquiryStatementList(request);
            System.out.println("Response: " + response);
            System.out.println("ResultStatus: " + response.getResult().getResultStatus());
            System.out.println("TotalCount: " + response.getTotalCount());
            System.out.println("TotalPageNumber: " + response.getTotalPageNumber());
            if (response.getStatementList() != null) {
                System.out.println("Records in page: " + response.getStatementList().size());
            }
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }

        System.out.println("======================================");
    }
}
