package {basePackage}.wf.service;

import com.alibaba.fastjson2.JSON;

import {basePackage}.wf.client.WfApiClient;
import {basePackage}.wf.config.WfClientConfig;
import {basePackage}.wf.model.exception.WfErrorCode;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.QueryQuotaRequest;
import {basePackage}.wf.model.request.QueryTradeOrdersRequest;
import {basePackage}.wf.model.request.SubmitTradeOrdersRequest;
import {basePackage}.wf.model.response.QueryQuotaResponse;
import {basePackage}.wf.model.response.QueryTradeOrdersResponse;
import {basePackage}.wf.model.response.SubmitTradeOrdersResponse;

/**
 * WorldFirst 贸易订单（Trade Orders）服务。
 *
 * <p>内部使用 {@link WfApiClient} 处理 HTTP 请求与加验签，采用薄封装模式：
 * 不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。
 * <p><b>安全强制</b>：{@link WfApiClient} 在返回响应前会执行验签，
 * 验签失败时抛出 {@link WfException}（错误码 {@link WfErrorCode#INVALID_SIGNATURE}），
 * 确保调用方拿到的响应均已完成验签，避免中间人攻击。
 *
 * <pre>{@code
 * // 1. 创建配置
 * WfClientConfig config = WfClientConfig.builder()
 *     .clientId("YOUR_CLIENT_ID")
 *     .privateKeyFromPath("/path/to/private_key.pem")
 *     .publicKeyFromPath("/path/to/wf_public_key.pem")
 *     .baseUrl("https://YOUR_BASE_URL")
 *     .build();
 *
 * // 2. 创建服务
 * TradeOrderService tradeOrderService = new TradeOrderService(config);
 *
 * // 3. 提交贸易订单（内部已强制验签，验签失败抛 WfException）
 * SubmitTradeOrdersRequest request = new SubmitTradeOrdersRequest();
 * request.setBatchRequestId("req_20260429001");
 * request.setSceneCode("PAY_INTO_CHINA");
 * // ... 设置其他字段
 *
 * SubmitTradeOrdersResponse response = tradeOrderService.submitTradeOrders(request);
 * }</pre>
 */
public class TradeOrderService {

    /** 提交贸易订单 API 路径 */
    private static final String SUBMIT_TRADE_ORDERS_ENDPOINT = "/api/open/v1/tradeOrders/submit";

    /** 查询贸易订单 API 路径 */
    private static final String QUERY_TRADE_ORDERS_ENDPOINT = "/api/open/v1/tradeOrders/query";

    /** 查询可用结算额度 API 路径 */
    private static final String QUERY_QUOTA_ENDPOINT = "/api/open/v1/tradeOrders/queryQuota";

    private final WfApiClient apiClient;

    /**
     * 创建 TradeOrderService 实例。
     *
     * @param config 客户端配置
     */
    public TradeOrderService(WfClientConfig config) {
        this.apiClient = new WfApiClient(config);
    }

    /**
     * 创建 TradeOrderService 实例（使用自定义 ApiClient，便于测试注入）。
     *
     * @param apiClient API 客户端
     */
    public TradeOrderService(WfApiClient apiClient) {
        this.apiClient = apiClient;
    }

    // ======================== 贸易订单 API ========================

    /**
     * 提交一批贸易订单。
     * <p>
     * 对应 WF API: POST /api/open/v1/tradeOrders/submit
     *
     * @param request 提交贸易订单请求
     * @return 提交贸易订单响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public SubmitTradeOrdersResponse submitTradeOrders(SubmitTradeOrdersRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(SUBMIT_TRADE_ORDERS_ENDPOINT, body, SubmitTradeOrdersResponse.class).getServiceResponse();
    }

    /**
     * 查询贸易订单批次处理状态。
     * <p>
     * 对应 WF API: POST /api/open/v1/tradeOrders/query
     *
     * @param request 查询贸易订单请求
     * @return 查询贸易订单响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public QueryTradeOrdersResponse queryTradeOrders(QueryTradeOrdersRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(QUERY_TRADE_ORDERS_ENDPOINT, body, QueryTradeOrdersResponse.class).getServiceResponse();
    }

    /**
     * 查询可用结算额度。
     * <p>
     * 对应 WF API: POST /api/open/v1/tradeOrders/queryQuota
     *
     * @param request 查询可用额度请求
     * @return 查询可用额度响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public QueryQuotaResponse queryQuota(QueryQuotaRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(QUERY_QUOTA_ENDPOINT, body, QueryQuotaResponse.class).getServiceResponse();
    }
}
