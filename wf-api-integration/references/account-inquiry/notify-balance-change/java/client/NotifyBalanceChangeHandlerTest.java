/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package {basePackage}.wf;

import {basePackage}.wf.client.account.NotifyBalanceChangeHandler;
import {basePackage}.wf.model.response.NotifyBalanceChangeResponse;
import org.junit.Before;
import org.junit.Test;

/**
 * NotifyBalanceChangeHandler 单元测试。
 *
 * <p>模拟万里汇发送的 notifyBalanceChange 回调通知，验证处理器的解析和响应逻辑。
 *
 * @author Qoder
 * @version NotifyBalanceChangeHandlerTest.java, v 0.1 2026-04-21
 */
public class NotifyBalanceChangeHandlerTest {

    private NotifyBalanceChangeHandler handler;

    @Before
    public void setUp() {
        handler = new NotifyBalanceChangeHandler();
    }

    // ==================== Balance Change Notification Tests ====================

    /**
     * 测试处理转账入账通知（单条记录）。
     */
    @Test
    public void testHandleTransferInflow() {
        String requestBody = "{"
            + "\"notifySequence\":1,"
            + "\"balanceChangeLogs\":[{"
            + "  \"balanceChangeTime\":\"2026-04-21T12:01:01+08:00\","
            + "  \"accountNo\":\"2120113011000500048044119\","
            + "  \"balanceType\":\"NORMAL_BALANCE\","
            + "  \"accountingBizNo\":\"20260421290010101201921009001\","
            + "  \"transactionAmount\":{"
            + "    \"currency\":\"USD\","
            + "    \"value\":10000"
            + "  },"
            + "  \"accountBalance\":{"
            + "    \"currency\":\"USD\","
            + "    \"value\":50000"
            + "  },"
            + "  \"transactionType\":\"TRANSFER\","
            + "  \"transactionId\":\"outBiz4524ee85-49ab-4d2b-9bdb-3ee7a48001\","
            + "  \"extTransactionId\":\"EXT20260421001\","
            + "  \"beneficiaryName\":\"John ***\","
            + "  \"beneficiaryAccountNo\":\"VA000000***\","
            + "  \"remarks\":\"Test transfer\""
            + "}]"
            + "}";

        System.out.println("====== testHandleTransferInflow ======");
        System.out.println("Request body: " + requestBody);

        NotifyBalanceChangeResponse response = handler.handleNotification(requestBody);

        System.out.println("Response: " + response);
        System.out.println("ResultStatus: " + response.getResult().getResultStatus());
        System.out.println("ResultCode: " + response.getResult().getResultCode());

        assert "S".equals(response.getResult().getResultStatus());
        assert "SUCCESS".equals(response.getResult().getResultCode());

        System.out.println("======================================");
    }

    /**
     * 测试处理转账出账通知（负数金额）。
     */
    @Test
    public void testHandleTransferOutflow() {
        String requestBody = "{"
            + "\"notifySequence\":2,"
            + "\"balanceChangeLogs\":[{"
            + "  \"balanceChangeTime\":\"2026-04-21T14:30:00+08:00\","
            + "  \"accountNo\":\"2120113011000500048044119\","
            + "  \"balanceType\":\"NORMAL_BALANCE\","
            + "  \"accountingBizNo\":\"20260421290010101201921009002\","
            + "  \"transactionAmount\":{"
            + "    \"currency\":\"EUR\","
            + "    \"value\":-5000"
            + "  },"
            + "  \"accountBalance\":{"
            + "    \"currency\":\"EUR\","
            + "    \"value\":15000"
            + "  },"
            + "  \"transactionType\":\"WITHDRAWAL\","
            + "  \"transactionId\":\"outBiz4524ee85-49ab-4d2b-9bdb-3ee7a48002\""
            + "}]"
            + "}";

        System.out.println("====== testHandleTransferOutflow ======");
        System.out.println("Request body: " + requestBody);

        NotifyBalanceChangeResponse response = handler.handleNotification(requestBody);

        System.out.println("Response: " + response);
        System.out.println("ResultStatus: " + response.getResult().getResultStatus());
        System.out.println("ResultCode: " + response.getResult().getResultCode());

        assert "S".equals(response.getResult().getResultStatus());
        assert "SUCCESS".equals(response.getResult().getResultCode());

        System.out.println("=======================================");
    }

    /**
     * 测试处理多条余额变动记录。
     */
    @Test
    public void testHandleMultipleChangeLogs() {
        String requestBody = "{"
            + "\"notifySequence\":3,"
            + "\"balanceChangeLogs\":["
            + "  {"
            + "    \"balanceChangeTime\":\"2026-04-21T10:00:00+08:00\","
            + "    \"accountNo\":\"2120113011000500048044119\","
            + "    \"balanceType\":\"NORMAL_BALANCE\","
            + "    \"accountingBizNo\":\"20260421290010101201921009003\","
            + "    \"transactionAmount\":{\"currency\":\"HKD\",\"value\":29697},"
            + "    \"accountBalance\":{\"currency\":\"HKD\",\"value\":29697},"
            + "    \"transactionType\":\"COLLECTION\","
            + "    \"beneficiaryName\":\"Alice ***\","
            + "    \"beneficiaryAccountNo\":\"VA000001***\""
            + "  },"
            + "  {"
            + "    \"balanceChangeTime\":\"2026-04-21T10:05:00+08:00\","
            + "    \"accountNo\":\"2120113011000500048044119\","
            + "    \"balanceType\":\"NORMAL_BALANCE\","
            + "    \"accountingBizNo\":\"20260421290010101201921009004\","
            + "    \"transactionAmount\":{\"currency\":\"HKD\",\"value\":-1000},"
            + "    \"accountBalance\":{\"currency\":\"HKD\",\"value\":28697},"
            + "    \"transactionType\":\"CHARGE\","
            + "    \"remarks\":\"Service fee\""
            + "  }"
            + "]"
            + "}";

        System.out.println("====== testHandleMultipleChangeLogs ======");
        System.out.println("Request body: " + requestBody);

        NotifyBalanceChangeResponse response = handler.handleNotification(requestBody);

        System.out.println("Response: " + response);
        System.out.println("ResultStatus: " + response.getResult().getResultStatus());
        System.out.println("ResultCode: " + response.getResult().getResultCode());

        assert "S".equals(response.getResult().getResultStatus());
        assert "SUCCESS".equals(response.getResult().getResultCode());

        System.out.println("==========================================");
    }
}
