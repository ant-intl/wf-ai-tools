package {basePackage}.wf.service;

import com.alibaba.fastjson2.JSON;

import {basePackage}.wf.client.WfApiClient;
import {basePackage}.wf.config.WfClientConfig;
import {basePackage}.wf.model.exception.WfErrorCode;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.QueryCalendarRequest;
import {basePackage}.wf.model.request.QuerySupportedCurrenciesRequest;
import {basePackage}.wf.model.response.QueryCalendarResponse;
import {basePackage}.wf.model.response.QuerySupportedCurrenciesResponse;

/**
 * WorldFirst 外汇（Foreign Exchange）参考数据服务。
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
 * ReferenceService referenceService = new ReferenceService(config);
 *
 * // 3. 查询交易日历（内部已强制验签，验签失败抛 WfException）
 * QueryCalendarRequest request = new QueryCalendarRequest();
 * request.setDealType("FORWARD");
 * request.setStartDate("2026-04-02");
 * request.setEndDate("2026-04-30");
 * request.setBuyCurrency("USD");
 * request.setSellCurrency("HKD");
 *
 * QueryCalendarResponse response = referenceService.queryCalendar(request);
 * }</pre>
 */
public class ReferenceService {

    /** 查询交易日历 API 路径 */
    private static final String QUERY_CALENDAR_ENDPOINT = "/api/open/v1/fx/references/queryCalendar";

    /** 查询支持币种 API 路径 */
    private static final String QUERY_SUPPORTED_CURRENCIES_ENDPOINT = "/api/open/v1/fx/references/queryCurrencies";

    private final WfApiClient apiClient;

    /**
     * 创建 ReferenceService 实例。
     *
     * @param config 客户端配置
     */
    public ReferenceService(WfClientConfig config) {
        this.apiClient = new WfApiClient(config);
    }

    /**
     * 创建 ReferenceService 实例（使用自定义 ApiClient，便于测试注入）。
     *
     * @param apiClient API 客户端
     */
    public ReferenceService(WfApiClient apiClient) {
        this.apiClient = apiClient;
    }

    // ======================== 参考数据 API ========================

    /**
     * 查询交易日历。
     * <p>
     * 查询指定货币对和交易类型在给定日期范围内的可用交易日或结算日。
     * 返回可用日期列表，具体取决于请求的日历类型（交易日历或结算日历）。
     * <p>
     * 对应 WF API: POST /api/open/v1/fx/references/queryCalendar
     *
     * @param request 查询交易日历请求（包含 dealType、startDate、endDate、buyCurrency、sellCurrency、calendarType）
     * @return 查询交易日历响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public QueryCalendarResponse queryCalendar(QueryCalendarRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(QUERY_CALENDAR_ENDPOINT, body, QueryCalendarResponse.class).getServiceResponse();
    }

    /**
     * 查询支持的货币对。
     * <p>
     * 查询 FX 交易支持的货币对列表，可按交易类型（SPOT、UNFUNDED_SPOT、FORWARD）过滤，
     * 用于在创建报价前确认目标货币对是否可用。
     * <p>
     * 对应 WF API: POST /api/open/v1/fx/references/queryCurrencies
     *
     * @param request 查询支持币种请求（包含 dealType）
     * @return 查询支持币种响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public QuerySupportedCurrenciesResponse querySupportedCurrencies(QuerySupportedCurrenciesRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(QUERY_SUPPORTED_CURRENCIES_ENDPOINT, body, QuerySupportedCurrenciesResponse.class).getServiceResponse();
    }
}
