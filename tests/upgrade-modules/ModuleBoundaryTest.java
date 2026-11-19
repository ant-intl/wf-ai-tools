package validation;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.junit.Test;
import validation.wf.client.WfRequestContext;
import validation.wf.model.request.*;
import validation.wf.model.response.*;
import validation.wf.model.domain.*;
import static org.junit.Assert.*;

/** Targeted assertions independent of the generation manifest. */
public class ModuleBoundaryTest {
    @Test public void batchItemInputHasNoBatchPreferencesOrQuote() throws Exception {
        for (String field : new String[]{"transferQuote", "fvtPreference", "oboPreference", "paymentType", "paymentNetwork"}) {
            try { BatchPayoutItemInput.class.getDeclaredField(field); fail(field); }
            catch (NoSuchFieldException expected) { /* deliberately absent */ }
        }
    }

    @Test public void batchPricesKeepNestedFeeAndPromotionStructures() {
        QueryBatchPayoutResponse response = JSON.parseObject("{\"status\":\"COMPLETED\",\"calculationSummary\":{\"currencyPairs\":[{\"clientRate\":1.123456,\"feeItemList\":[{\"feeItemType\":\"SERVICE_FEE\",\"feeItemAmount\":{\"currency\":\"USD\",\"value\":5000000000}}],\"promotionList\":[{\"promotionName\":\"test\"}]}]}}", QueryBatchPayoutResponse.class);
        BatchPayoutCurrencyPair pair = response.getCalculationSummary().getCurrencyPairs().get(0);
        assertEquals("1.123456", pair.getClientRate().toPlainString());
        assertEquals(Long.valueOf(5000000000L), pair.getFeeItemList().get(0).getFeeItemAmount().getValue());
        assertEquals("test", pair.getPromotionList().get(0).getPromotionName());
        assertEquals("COMPLETED", response.getStatus());
    }

    @Test public void itemStatusAndIdentifierStayDistinctFromSinglePayout() {
        ListBatchPayoutItemsResponse response = JSON.parseObject("{\"items\":[{\"itemId\":\"i-1\",\"status\":\"RETURNED\",\"returnedAmount\":{\"currency\":\"USD\",\"value\":1}}]}", ListBatchPayoutItemsResponse.class);
        assertEquals("i-1", response.getItems().get(0).getItemId());
        assertEquals("RETURNED", response.getItems().get(0).getStatus());
    }

    @Test public void cardUpdateOmitsAbsentPropertiesAndReadOnlyLimitUsage() {
        UpdateCardRequest request = new UpdateCardRequest();
        request.setId("c-1"); request.setRequestId("r-1"); request.setStatus("FROZEN");
        JSONObject json = JSON.parseObject(JSON.toJSONString(request));
        assertEquals(3, json.size());
        assertFalse(json.containsKey("transactionLimits"));
        assertEquals(2, CardTransactionLimitInput.class.getDeclaredFields().length);
        assertEquals(4, CardTransactionLimit.class.getDeclaredFields().length);
    }

    @Test public void sensitiveCardResponseToStringNeverPrintsPanOrCvv() {
        QueryCardSensitiveInfoResponse response = JSON.parseObject("{\"cardNumber\":\"5555555555554444\",\"cvv\":\"123\",\"expiryDate\":\"11/29\"}", QueryCardSensitiveInfoResponse.class);
        assertEquals("5555555555554444", response.getCardNumber());
        assertFalse(response.toString().contains("5555"));
        assertFalse(response.toString().contains("123"));
    }

    @Test public void transactionRefundAndInflowMapSeparately() {
        ListCardTransactionsResponse response = JSON.parseObject("{\"items\":[{\"status\":\"PARTIAL_REFUNDED\",\"billType\":\"CARD_REFUND\",\"inAmount\":{\"currency\":\"USD\",\"value\":100},\"exchangeRate\":\"1.000000\"}]}", ListCardTransactionsResponse.class);
        CardTransaction item = response.getItems().get(0);
        assertEquals("PARTIAL_REFUNDED", item.getStatus());
        assertEquals(Long.valueOf(100), item.getInAmount().getValue());
        assertNull(item.getOutAmount()); assertNull(item.getUpdatedAt());
        assertEquals("1.000000", item.getExchangeRate());
    }

    @Test public void reportStatusAndDownloadUrlAreNestedAndNotInListSummary() {
        QueryReportResponse response = JSON.parseObject("{\"report\":{\"status\":\"SUCCESS\",\"downloadUrl\":\"https://download.invalid/test\",\"downloadUrlExpiresAt\":\"2026-11-18T03:00:00Z\"}}", QueryReportResponse.class);
        assertEquals("SUCCESS", response.getReport().getStatus());
        assertEquals("https://download.invalid/test", response.getReport().getDownloadUrl());
        assertFalse(JSON.parseObject(JSON.toJSONString(response)).containsKey("downloadUrl"));
        for (java.lang.reflect.Field f : DownloadReportSummary.class.getDeclaredFields()) assertNotEquals("downloadUrl", f.getName());
    }

    @Test public void rfiSupportsAllThreeAnswerShapesWithoutRequestId() {
        RespondToRfiRequest request = JSON.parseObject("{\"id\":\"rfi-1\",\"answers\":[{\"questionId\":\"q1\",\"text\":\"answer\"},{\"questionId\":\"q2\",\"textList\":[\"a\",\"b\"]},{\"questionId\":\"q3\",\"files\":[{\"fileId\":\"f1\",\"fileName\":\"doc.pdf\"}]}]}", RespondToRfiRequest.class);
        assertEquals(3, request.getAnswers().size());
        assertEquals("f1", request.getAnswers().get(2).getFiles().get(0).getFileId());
        assertEquals(2, RespondToRfiRequest.class.getDeclaredFields().length);
        assertEquals(1, RetrieveRfiRequest.class.getDeclaredFields().length);
    }

    @Test public void rfiQuestionsKeepBooleanRequiredAndOpenAuditKey() {
        RetrieveRfiResponse response = JSON.parseObject("{\"questions\":[{\"id\":\"q1\",\"required\":true,\"auditKey\":\"basicInformation.companyName.value\",\"answerType\":\"TEXT\"}]}", RetrieveRfiResponse.class);
        assertEquals(Boolean.TRUE, response.getQuestions().get(0).getRequired());
        assertEquals("basicInformation.companyName.value", response.getQuestions().get(0).getAuditKey());
        assertNull(response.getExpiresAt());
    }

    @Test public void contextRejectsEmptyAndInjectedHeadersAndRedactsToken() {
        for (String value : new String[]{"", " ", "a\r\nSignature: invalid"}) {
            try { WfRequestContext.forAccount(value); fail("accepted invalid header"); }
            catch (IllegalArgumentException expected) { /* rejected */ }
        }
        WfRequestContext context = WfRequestContext.forAccessToken("local-secret-token");
        assertNull(context.getAccountId());
        assertEquals("local-secret-token", context.getAccessToken());
        assertFalse(context.toString().contains("local-secret-token"));
    }
}
