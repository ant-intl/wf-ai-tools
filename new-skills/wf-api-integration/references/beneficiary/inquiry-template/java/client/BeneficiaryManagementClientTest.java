/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf;

import {basePackage}.wf.client.BeneficiaryManagementClient;
import {basePackage}.wf.config.WfConfig;
import {basePackage}.wf.model.domain.Beneficiary;
import {basePackage}.wf.model.domain.CardTemplateField;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.BindBeneficiaryRequest;
import {basePackage}.wf.model.request.EditBeneficiaryRequest;
import {basePackage}.wf.model.request.InquiryBeneficiaryListRequest;
import {basePackage}.wf.model.request.InquiryBeneficiaryTemplateRequest;
import {basePackage}.wf.model.request.RemoveBeneficiaryRequest;
import {basePackage}.wf.model.response.BindBeneficiaryResponse;
import {basePackage}.wf.model.response.EditBeneficiaryResponse;
import {basePackage}.wf.model.response.InquiryBeneficiaryListResponse;
import {basePackage}.wf.model.response.InquiryBeneficiaryTemplateResponse;
import {basePackage}.wf.model.response.RemoveBeneficiaryResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BeneficiaryManagementClient 集成测试。
 *
 * <p>Mock WfConfig，不 Mock WfSigner 和 WfHttpClientUtil。
 *
 * @author Qoder
 * @version BeneficiaryManagementClientTest.java, v 0.1 2026-03-26
 */
public class BeneficiaryManagementClientTest {

    private static final String CLIENT_ID = "YOUR_CLIENT_ID";
    private static final String BASE_URL = "https://iopengw-sggz95m.alipay.com";

    private BeneficiaryManagementClient client;
    private WfConfig mockConfig;

    @Before
    public void setUp() {
        mockConfig = Mockito.mock(WfConfig.class);
        Mockito.when(mockConfig.getClientId()).thenReturn(CLIENT_ID);
        Mockito.when(mockConfig.getBaseUrl()).thenReturn(BASE_URL);
        Mockito.when(mockConfig.getConnectTimeout()).thenReturn(10000);
        Mockito.when(mockConfig.getReadTimeout()).thenReturn(30000);
        // 配置私钥/公钥路径，用于真实签名
        // 请替换为实际的密钥文件路径
        Mockito.when(mockConfig.getPrivateKeyPath()).thenReturn("/path/to/your/private_key.pem");
        Mockito.when(mockConfig.getPublicKeyPath()).thenReturn("/path/to/your/wf_public_key.pem");

        client = new BeneficiaryManagementClient(mockConfig);
        client.init();
    }

    // -------------------------------------------------------------------------
    // 1. 查询卡模版
    // -------------------------------------------------------------------------

    @Test
    public void testInquiryBeneficiaryTemplate() {
        InquiryBeneficiaryTemplateRequest request = new InquiryBeneficiaryTemplateRequest();
        request.setCountryCode("HK");
        request.setCurrency("USD");
        request.setBeneficiaryType("THIRD_PARTY_PERSONAL_BANK_ACCOUNT");

        System.out.println("====== testInquiryBeneficiaryTemplate ======");
        System.out.println("Request: " + request);

        try {
            InquiryBeneficiaryTemplateResponse response = client.inquiryBeneficiaryTemplate(request);
            System.out.println("Response: " + response);
            printTemplateFields(response.getCardTemplateData());
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }

        System.out.println("=============================================");
    }

    // -------------------------------------------------------------------------
    // 2. 绑定收款人
    // -------------------------------------------------------------------------

    @Test
    public void testBindBeneficiary() {
        // 构造银行账户信息（按 USD/HK 卡模版字段）
        Map<String, String> bankAccount = new HashMap<>();
        bankAccount.put("bankAccountName", "vaL2LTest");
        bankAccount.put("bankAccountNo", "100100004623");
        bankAccount.put("bankName", "STARK bankName");
        bankAccount.put("bankBIC", "CITIHKHX");
        bankAccount.put("beneficiaryCountryCode", "HK");
        bankAccount.put("beneficiaryType", "THIRD_PARTY_PERSONAL_BANK_ACCOUNT");

        BindBeneficiaryRequest request = new BindBeneficiaryRequest();
        request.setBindBeneficiaryRequestId("BIND_" + System.currentTimeMillis());
        request.setBeneficiaryType("THIRD_PARTY_PERSONAL_BANK_ACCOUNT");
        request.setCountryCode("HK");
        request.setCurrency("USD");
        request.setBeneficiaryNick("Test Beneficiary");
        request.setBeneficiaryBankAccount(bankAccount);

        System.out.println("====== testBindBeneficiary ======");
        System.out.println("Request: " + request);

        try {
            BindBeneficiaryResponse response = client.bindBeneficiary(request);
            System.out.println("Response: " + response);
            Beneficiary beneficiary = response.getBeneficiary();
            if (beneficiary != null) {
                System.out.println("beneficiaryToken: " + beneficiary.getBeneficiaryToken());
            }
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }

        System.out.println("=================================");
    }

    // -------------------------------------------------------------------------
    // 3. 删除收款人
    // -------------------------------------------------------------------------

    @Test
    public void testRemoveBeneficiary() {
        // 注意：需要先绑定收款人获取有效的 beneficiaryToken
        RemoveBeneficiaryRequest request = new RemoveBeneficiaryRequest();
        request.setRemoveBeneficiaryRequestId("REMOVE_" + System.currentTimeMillis());
        request.setBeneficiaryToken("YOUR_BENEFICIARY_TOKEN");

        System.out.println("====== testRemoveBeneficiary ======");
        System.out.println("Request: " + request);

        try {
            RemoveBeneficiaryResponse response = client.removeBeneficiary(request);
            System.out.println("Response: " + response);
            System.out.println("Removed beneficiaryToken: " + response.getBeneficiaryToken());
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }

        System.out.println("===================================");
    }

    // -------------------------------------------------------------------------
    // 4. 编辑收款人
    // -------------------------------------------------------------------------

    @Test
    public void testEditBeneficiary() {
        // 注意：需要先绑定收款人获取有效的 beneficiaryToken
        EditBeneficiaryRequest request = new EditBeneficiaryRequest();
        request.setBeneficiaryToken("YOUR_BENEFICIARY_TOKEN");
        request.setBeneficiaryNick("Updated Nickname");

        System.out.println("====== testEditBeneficiary ======");
        System.out.println("Request: " + request);

        try {
            EditBeneficiaryResponse response = client.editBeneficiary(request);
            System.out.println("Response: " + response);
            System.out.println("Updated beneficiaryToken: " + response.getBeneficiaryToken());
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }

        System.out.println("=================================");
    }

    // -------------------------------------------------------------------------
    // 5. 查询收款人列表
    // -------------------------------------------------------------------------

    @Test
    public void testInquiryBeneficiaryList() {
        InquiryBeneficiaryListRequest request = new InquiryBeneficiaryListRequest();
        request.setPageSize(10);
        request.setPageNumber(1);

        System.out.println("====== testInquiryBeneficiaryList ======");
        System.out.println("Request: " + request);

        try {
            InquiryBeneficiaryListResponse response = client.inquiryBeneficiaryList(request);
            System.out.println("Response: " + response);
            System.out.println("totalCount: " + response.getTotalCount());
            System.out.println("totalPageNumber: " + response.getTotalPageNumber());

            List<Beneficiary> beneficiaries = response.getBeneficiaries();
            if (beneficiaries != null) {
                for (Beneficiary b : beneficiaries) {
                    System.out.println("  - " + b.getBeneficiaryNick()
                        + " | token=" + b.getBeneficiaryToken()
                        + " | type=" + b.getBeneficiaryType());
                }
            }
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }

        System.out.println("========================================");
    }

    // -------------------------------------------------------------------------
    // Helper methods
    // -------------------------------------------------------------------------

    private void printTemplateFields(List<CardTemplateField> fields) {
        if (fields == null || fields.isEmpty()) {
            System.out.println("cardTemplateData: (empty or null)");
            return;
        }
        System.out.println("cardTemplateData (" + fields.size() + " fields):");
        for (CardTemplateField field : fields) {
            System.out.println("  - " + field.getFieldName()
                + " | required=" + field.getRequired()
                + " | pattern=" + field.getPattern()
                + " | desc=" + field.getFieldDescription());
        }
    }
}
