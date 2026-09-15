package {basePackage}.wf.service;

import com.alibaba.fastjson2.JSON;

import {basePackage}.wf.client.WfApiClient;
import {basePackage}.wf.config.WfClientConfig;
import {basePackage}.wf.model.exception.WfErrorCode;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.CreateQuoteRequest;
import {basePackage}.wf.model.request.QueryRateHistoryRequest;
import {basePackage}.wf.model.request.QueryRatesRequest;
import {basePackage}.wf.model.response.CreateQuoteResponse;
import {basePackage}.wf.model.response.QueryRateHistoryResponse;
import {basePackage}.wf.model.response.QueryRatesResponse;

/**
 * WorldFirst 外汇（Foreign Exchange）报价服务。
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
 * QuoteService quoteService = new QuoteService(config);
 *
 * // 3. 查询实时汇率（内部已强制验签，验签失败抛 WfException）
 * QueryRatesRequest request = new QueryRatesRequest();
 * List<RateCondition> conditions = new ArrayList<>();
 * RateCondition condition = new RateCondition();
 * condition.setSellAmount(new Amount("USD", 10000));
 * condition.setBuyAmount(new Amount("HKD", null));
 * conditions.add(condition);
 * request.setRateConditions(conditions);
 *
 * QueryRatesResponse response = quoteService.queryRates(request);
 * }</pre>
 */
public class QuoteService {

    /** 查询实时汇率 API 路径 */
    private static final String QUERY_RATES_ENDPOINT = "/api/open/v1/fx/rates/query";

    /** 查询历史汇率 API 路径 */
    private static final String QUERY_RATE_HISTORY_ENDPOINT = "/api/open/v1/fx/rates/queryHistory";

    /** 创建绑定报价 API 路径 */
    private static final String CREATE_QUOTE_ENDPOINT = "/api/open/v1/fx/quotes/create";

    private final WfApiClient apiClient;

    /**
     * 创建 QuoteService 实例。
     *
     * @param config 客户端配置
     */
    public QuoteService(WfClientConfig config) {
        this.apiClient = new WfApiClient(config);
    }

    /**
     * 创建 QuoteService 实例（使用自定义 ApiClient，便于测试注入）。
     *
     * @param apiClient API 客户端
     */
    public QuoteService(WfApiClient apiClient) {
        this.apiClient = apiClient;
    }

    // ======================== 汇率查询 API ========================

    /**
     * 查询实时汇率。
     * <p>
     * 对应 WF API: POST /api/open/v1/fx/rates/query
     *
     * @param request 查询实时汇率请求
     * @return 查询实时汇率响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public QueryRatesResponse queryRates(QueryRatesRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(QUERY_RATES_ENDPOINT, body, QueryRatesResponse.class).getServiceResponse();
    }

    /**
     * 查询历史汇率。
     * <p>
     * 对应 WF API: POST /api/open/v1/fx/rates/queryHistory
     *
     * @param request 查询历史汇率请求
     * @return 查询历史汇率响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public QueryRateHistoryResponse queryRateHistory(QueryRateHistoryRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(QUERY_RATE_HISTORY_ENDPOINT, body, QueryRateHistoryResponse.class).getServiceResponse();
    }

    // ======================== 报价 API ========================

    /**
     * 创建绑定报价。
     * <p>
     * 对应 WF API: POST /api/open/v1/fx/quotes/create
     *
     * @param request 创建报价请求
     * @return 创建报价响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public CreateQuoteResponse createQuote(CreateQuoteRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(CREATE_QUOTE_ENDPOINT, body, CreateQuoteResponse.class).getServiceResponse();
    }
}
