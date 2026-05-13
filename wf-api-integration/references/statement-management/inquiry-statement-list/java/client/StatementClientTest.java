package {basePackage}.wf;

import {basePackage}.wf.client.statement.StatementClient;
import {basePackage}.wf.config.WfConfig;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.InquiryStatementRequest;
import {basePackage}.wf.model.request.InquiryStatementDetailRequest;
import {basePackage}.wf.model.response.InquiryStatementResponse;
import {basePackage}.wf.model.response.InquiryStatementDetailResponse;
import org.junit.Before;
import org.junit.Test;

/**
 * StatementClient 集成测试（真实签名模式）。
 *
 * <p>包含 inquiryStatementList 和 inquiryStatementDetail 两个接口的测试。
 *
 */
public class StatementClientTest {

    private StatementClient client;
    private WfConfig mockConfig;

    @Before
    public void setUp() {
        mockConfig = new WfConfig();
        client = new StatementClient();
        client.setConfig(mockConfig);
        client.init();
    }

    /**
     * 测试查询流水列表（第一页）。
     */
    @Test
    public void testInquiryStatementList() {
        InquiryStatementRequest request = InquiryStatementRequest.builder()
            .startTime("2026-01-01T00:00:00+08:00")
            .endTime("2026-03-27T23:59:59+08:00")
            .pageNumber(1)
            .build();

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

    /**
     * 测试查询账单流水详情。
     * 请将 accountingBizNo 替换为通过 inquiryStatementList 获取的真实值。
     */
    @Test
    public void testInquiryStatementDetail() {

        InquiryStatementRequest inquiryStatementRequest = InquiryStatementRequest.builder()
                .startTime("2026-01-01T00:00:00+08:00")
                .endTime("2026-03-27T23:59:59+08:00")
                .pageNumber(1)
                .build();

        InquiryStatementResponse inquiryStatementResponse = client.inquiryStatementList(inquiryStatementRequest);
        String accountingBizNo = inquiryStatementResponse.getStatementList().get(1).getAccountingBizNo();

        InquiryStatementDetailRequest request = InquiryStatementDetailRequest.builder()
            .accountingBizNo(accountingBizNo)
            .build();

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
