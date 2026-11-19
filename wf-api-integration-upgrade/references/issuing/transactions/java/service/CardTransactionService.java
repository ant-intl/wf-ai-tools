package {basePackage}.wf.service;

import com.alibaba.fastjson2.JSON;
import {basePackage}.wf.client.WfApiClient;
import {basePackage}.wf.client.WfRequestContext;
import {basePackage}.wf.config.WfClientConfig;
import {basePackage}.wf.model.request.ListCardTransactionsRequest;
import {basePackage}.wf.model.response.ListCardTransactionsResponse;

/** 卡交易记录 API 薄封装。单次请求；业务状态和重试由调用方按 GUIDE 处理。 */
public class CardTransactionService {
    private final WfApiClient apiClient;

    public CardTransactionService(WfClientConfig config) { this(new WfApiClient(config)); }

    public CardTransactionService(WfApiClient apiClient) { this.apiClient = apiClient; }

    /** POST /api/open/v1/issuing/transactions/list；响应已由公共客户端验签。 */
    public ListCardTransactionsResponse listCardTransactions(ListCardTransactionsRequest request) {
        return apiClient.post("/api/open/v1/issuing/transactions/list", JSON.toJSONString(request), ListCardTransactionsResponse.class).getServiceResponse();
    }

    /** 指定单次调用账户上下文；不改变共享客户端状态。 */
    public ListCardTransactionsResponse listCardTransactions(ListCardTransactionsRequest request, WfRequestContext context) {
        return apiClient.post("/api/open/v1/issuing/transactions/list", JSON.toJSONString(request), ListCardTransactionsResponse.class, context).getServiceResponse();
    }
}
