/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf;

import {basePackage}.wf.client.account.NotifyVostroHandler;
import {basePackage}.wf.model.response.NotifyVostroResponse;
import org.junit.Before;
import org.junit.Test;

/**
 * NotifyVostroHandler 单元测试。
 *
 * <p>模拟万里汇发送的 notifyVostro 回调通知，验证处理器的解析和响应逻辑。
 *
 * @author Qoder
 * @version NotifyVostroHandlerTest.java, v 0.1 2026-04-21
 */
public class NotifyVostroHandlerTest {

    private NotifyVostroHandler handler;

    @Before
    public void setUp() {
        handler = new NotifyVostroHandler();
    }

    // ==================== Funding Success Tests ====================

    /**
     * 测试处理垫付成功通知。
     */
    @Test
    public void testHandleFundingSuccess() {
        String requestBody = "{"
            + "\"fundingId\":\"20260421000000001\","
            + "\"balanceResult\":{"
            + "  \"resultCode\":\"SUCCESS\","
            + "  \"resultStatus\":\"S\","
            + "  \"resultMessage\":\"Success\""
            + "},"
            + "\"payerBankAccount\":{"
            + "  \"payerBankAccountNo\":\"622848*****1234\","
            + "  \"payerBankName\":\"Bank of China\""
            + "},"
            + "\"beneficiaryAccount\":{"
            + "  \"beneficiaryBankAccountNo\":\"VA0000001234567890\""
            + "},"
            + "\"balanceChangeAmount\":{"
            + "  \"currency\":\"USD\","
            + "  \"value\":10000"
            + "},"
            + "\"balanceChangeTime\":\"2026-04-21T12:01:01+08:00\","
            + "\"remitInfo\":\"Test funding\""
            + "}";

        System.out.println("====== testHandleFundingSuccess ======");
        System.out.println("Request body: " + requestBody);

        NotifyVostroResponse response = handler.handleNotification(requestBody);

        System.out.println("Response: " + response);
        System.out.println("ResultStatus: " + response.getResult().getResultStatus());
        System.out.println("ResultCode: " + response.getResult().getResultCode());

        assert "S".equals(response.getResult().getResultStatus());
        assert "SUCCESS".equals(response.getResult().getResultCode());

        System.out.println("======================================");
    }

    /**
     * 测试处理退款成功通知。
     */
    @Test
    public void testHandleRefundSuccess() {
        String requestBody = "{"
            + "\"fundingId\":\"20260421000000002\","
            + "\"balanceResult\":{"
            + "  \"resultCode\":\"REFUND\","
            + "  \"resultStatus\":\"S\","
            + "  \"resultMessage\":\"Refund Success\""
            + "},"
            + "\"payerBankAccount\":{"
            + "  \"payerBankAccountNo\":\"622848*****5678\","
            + "  \"payerBankName\":\"ICBC\""
            + "},"
            + "\"beneficiaryAccount\":{"
            + "  \"beneficiaryBankAccountNo\":\"VA0000009876543210\""
            + "},"
            + "\"balanceChangeAmount\":{"
            + "  \"currency\":\"EUR\","
            + "  \"value\":5000"
            + "},"
            + "\"balanceChangeTime\":\"2026-04-21T14:30:00+08:00\""
            + "}";

        System.out.println("====== testHandleRefundSuccess ======");
        System.out.println("Request body: " + requestBody);

        NotifyVostroResponse response = handler.handleNotification(requestBody);

        System.out.println("Response: " + response);
        System.out.println("ResultStatus: " + response.getResult().getResultStatus());
        System.out.println("ResultCode: " + response.getResult().getResultCode());

        assert "S".equals(response.getResult().getResultStatus());
        assert "SUCCESS".equals(response.getResult().getResultCode());

        System.out.println("=====================================");
    }
}
