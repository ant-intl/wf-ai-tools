/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf;

import {basePackage}.wf.client.account.InquiryAccountInfoClient;
import {basePackage}.wf.config.WfConfig;
import {basePackage}.wf.model.domain.AccountInfo;
import {basePackage}.wf.model.domain.BankAccount;
import {basePackage}.wf.model.domain.Customer;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.domain.SubUserInfo;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.InquiryAccountRequest;
import {basePackage}.wf.model.request.InquiryBalanceRequest;
import {basePackage}.wf.model.request.InquiryAvailableQuotaRequest;
import {basePackage}.wf.model.request.InquirySubuserRequest;
import {basePackage}.wf.model.request.InquiryStoreRequest;
import {basePackage}.wf.model.response.InquiryAccountResponse;
import {basePackage}.wf.model.response.InquiryBalanceResponse;
import {basePackage}.wf.model.response.InquiryAvailableQuotaResponse;
import {basePackage}.wf.model.response.InquirySubuserResponse;
import {basePackage}.wf.model.response.InquiryStoreResponse;
import org.junit.Before;
import org.junit.Test;

/**
 * InquiryAccountInfoClient 集成测试。
 *
 * <p>包含 inquiryAccount、inquiryBalance、inquiryAvailableQuota、inquirySubuser 和 inquiryStore 五个接口的测试。
 *
 * @author Qoder
 * @version InquiryAccountInfoClientTest.java, v 0.1 2026-04-14
 */
public class InquiryAccountInfoClientTest {

    private InquiryAccountInfoClient client;
    private WfConfig mockConfig;

    @Before
    public void setUp() {
        mockConfig = new WfConfig();
        client = new InquiryAccountInfoClient();
        client.setConfig(mockConfig);
        client.init();
    }

    // ==================== inquiryAccount Tests ====================

    /**
     * 测试查询收款账户信息（RECEIVE_ACCOUNT）。
     */
    @Test
    public void testInquiryAccountByReceiveAccount() {
        InquiryAccountRequest request = new InquiryAccountRequest();
        request.setAccountType("RECEIVE_ACCOUNT");
        request.setReferenceCustomerId("YOUR_CUSTOMER_ID");

        System.out.println("====== testInquiryAccountByReceiveAccount ======");
        System.out.println("Request: " + request);

        try {
            InquiryAccountResponse response = client.inquiryAccount(request);
            System.out.println("Response: " + response);
            System.out.println("ResultStatus: " + response.getResult().getResultStatus());
            System.out.println("AccountId: " + response.getAccountId());

            if (response.getAccountInfos() != null) {
                System.out.println("AccountInfos count: " + response.getAccountInfos().size());
                for (AccountInfo info : response.getAccountInfos()) {
                    System.out.println("  - AccountNo: " + info.getAccountNo()
                        + " | Status: " + info.getAccountStatus()
                        + " | Currencies: " + info.getCurrencyList());
                }
            }

            Customer customer = response.getCustomer();
            if (customer != null) {
                System.out.println("Customer: " + customer.getCustomerCompanyName());
            }
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }

        System.out.println("=================================================");
    }

    /**
     * 测试查询虚拟账户信息（VIRTUAL_ACCOUNT）。
     */
    @Test
    public void testInquiryAccountByVirtualAccount() {
        InquiryAccountRequest request = new InquiryAccountRequest();
        request.setAccountType("VIRTUAL_ACCOUNT");
        request.setAccessToken("YOUR_ACCESS_TOKEN");

        System.out.println("====== testInquiryAccountByVirtualAccount ======");
        System.out.println("Request: " + request);

        try {
            InquiryAccountResponse response = client.inquiryAccount(request);
            System.out.println("Response: " + response);
            System.out.println("ResultStatus: " + response.getResult().getResultStatus());
            System.out.println("AccountId: " + response.getAccountId());

            if (response.getAccountInfos() != null) {
                for (AccountInfo info : response.getAccountInfos()) {
                    System.out.println("  - AccountNo: " + info.getAccountNo()
                        + " | Status: " + info.getAccountStatus()
                        + " | Currencies: " + info.getCurrencyList());
                    if (info.getBankAccountList() != null) {
                        for (BankAccount bank : info.getBankAccountList()) {
                            System.out.println("    Bank: " + bank.getBankName()
                                + " | Region: " + bank.getBankRegion()
                                + " | AccountNo: " + bank.getBankAccountNo());
                        }
                    }
                }
            }
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }

        System.out.println("=================================================");
    }

    /**
     * 测试查询支付宝钱包信息（ALIPAY_WALLET）。
     */
    @Test
    public void testInquiryAccountByAlipayWallet() {
        InquiryAccountRequest request = new InquiryAccountRequest();
        request.setAccountType("ALIPAY_WALLET");
        request.setReferenceCustomerId("YOUR_CUSTOMER_ID");

        System.out.println("====== testInquiryAccountByAlipayWallet ======");
        System.out.println("Request: " + request);

        try {
            InquiryAccountResponse response = client.inquiryAccount(request);
            System.out.println("Response: " + response);
            System.out.println("ResultStatus: " + response.getResult().getResultStatus());

            Customer customer = response.getCustomer();
            if (customer != null) {
                System.out.println("Customer: " + customer.getCustomerCompanyName()
                    + " | LegalEntityType: " + customer.getLegalEntityType());
            }
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }

        System.out.println("================================================");
    }

    /**
     * 测试查询关联公司支付宝钱包信息（ALIPAY_SHADOW_WALLET）。
     */
    @Test
    public void testInquiryAccountByAlipayShadowWallet() {
        InquiryAccountRequest request = new InquiryAccountRequest();
        request.setAccountType("ALIPAY_SHADOW_WALLET");
        request.setAccountId("YOUR_ACCOUNT_ID");

        System.out.println("====== testInquiryAccountByAlipayShadowWallet ======");
        System.out.println("Request: " + request);

        try {
            InquiryAccountResponse response = client.inquiryAccount(request);
            System.out.println("Response: " + response);
            System.out.println("ResultStatus: " + response.getResult().getResultStatus());

            if (response.getAffiliatedCustomer() != null) {
                System.out.println("AffiliatedCustomer: " + response.getAffiliatedCustomer().getCompanyName()
                    + " | AlipayNo: " + response.getAffiliatedCustomer().getAlipayNo());
            }
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }

        System.out.println("=====================================================");
    }

    /**
     * 测试查询企业支付宝钱包信息（ALIPAY_ORIGIN_WALLET）。
     */
    @Test
    public void testInquiryAccountByAlipayOriginWallet() {
        InquiryAccountRequest request = new InquiryAccountRequest();
        request.setAccountType("ALIPAY_ORIGIN_WALLET");

        System.out.println("====== testInquiryAccountByAlipayOriginWallet ======");
        System.out.println("Request: " + request);

        try {
            InquiryAccountResponse response = client.inquiryAccount(request);
            System.out.println("Response: " + response);
            System.out.println("ResultStatus: " + response.getResult().getResultStatus());

            if (response.getAlipayCustomer() != null) {
                System.out.println("AlipayCustomer: " + response.getAlipayCustomer().getCompanyName()
                    + " | AlipayNo: " + response.getAlipayCustomer().getAlipayNo()
                    + " | Region: " + response.getAlipayCustomer().getRegion());
            }
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }

        System.out.println("=====================================================");
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

    // ==================== inquirySubuser Tests ====================

    /**
     * 测试查询第一页子账号信息。
     */
    @Test
    public void testInquirySubuserFirstPage() {
        InquirySubuserRequest request = new InquirySubuserRequest();
        request.setPageSize(10);
        request.setPageNumber(1);

        System.out.println("====== testInquirySubuserFirstPage ======");
        System.out.println("Request: " + request);

        try {
            InquirySubuserResponse response = client.inquirySubuser(request);
            System.out.println("Response: " + response);
            System.out.println("ResultStatus: " + response.getResult().getResultStatus());
            System.out.println("TotalCount: " + response.getTotalCount());
            System.out.println("TotalPageNumber: " + response.getTotalPageNumber());
            System.out.println("CurrentPageNumber: " + response.getCurrentPageNumber());

            if (response.getPrimaryUserInformation() != null) {
                SubUserInfo primary = response.getPrimaryUserInformation();
                System.out.println("PrimaryUser - UserId: " + primary.getUserId()
                    + " | LogonId: " + primary.getLogonId());
                if (primary.getUserName() != null) {
                    System.out.println("PrimaryUser - FullName: " + primary.getUserName().getFullName());
                }
            }

            if (response.getUserInformations() != null) {
                System.out.println("SubUsers count: " + response.getUserInformations().size());
                for (SubUserInfo subUser : response.getUserInformations()) {
                    System.out.println("  - UserId: " + subUser.getUserId()
                        + " | LogonId: " + subUser.getLogonId()
                        + " | NickName: " + (subUser.getUserNickName() != null
                            ? subUser.getUserNickName().getFullName() : "N/A"));
                }
            }
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }

        System.out.println("=========================================");
    }

    // ==================== inquiryStore Tests ====================

    /**
     * 测试查询第一页店铺信息。
     */
    @Test
    public void testInquiryStoreFirstPage() {
        InquiryStoreRequest request = new InquiryStoreRequest();
        request.setPageSize(10);
        request.setPageNumber(1);

        System.out.println("====== testInquiryStoreFirstPage ======");
        System.out.println("Request: " + request);

        try {
            InquiryStoreResponse response = client.inquiryStore(request);
            System.out.println("Response: " + response);
            System.out.println("ResultStatus: " + response.getResult().getResultStatus());
            System.out.println("TotalCount: " + response.getTotalCount());
            System.out.println("TotalPageNumber: " + response.getTotalPageNumber());
            System.out.println("CurrentPageNumber: " + response.getCurrentPageNumber());

            if (response.getStoreInformation() != null) {
                System.out.println("Stores count: " + response.getStoreInformation().size());
                for ({basePackage}.wf.model.domain.StoreInfo store : response.getStoreInformation()) {
                    System.out.println("  - StoreName: " + store.getStoreName()
                        + " | Marketplace: " + store.getMarketplaceName()
                        + " | AuthorizedStatus: " + store.getAuthorizedStatus());
                    if (store.getAccountInformation() != null) {
                        for (AccountInfo account : store.getAccountInformation()) {
                            System.out.println("    Account - No: " + account.getAccountNo()
                                + " | Type: " + account.getAccountType()
                                + " | Status: " + account.getAccountStatus()
                                + " | Currencies: " + account.getCurrencyList());
                            if (account.getBankAccountList() != null) {
                                for (BankAccount bank : account.getBankAccountList()) {
                                    System.out.println("      Bank: " + bank.getBankName()
                                        + " | Region: " + bank.getBankRegion()
                                        + " | BIC: " + bank.getBankBIC()
                                        + " | Currencies: " + bank.getCurrencyList());
                                }
                            }
                        }
                    }
                }
            }
        } catch (WfException e) {
            System.out.println("WfException: " + e.getErrorCode() + " - " + e.getMessage());
        }

        System.out.println("=======================================");
    }
}
