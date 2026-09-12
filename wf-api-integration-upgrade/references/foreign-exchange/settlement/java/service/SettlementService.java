package {basePackage}.wf.service;

import com.alibaba.fastjson2.JSON;

import {basePackage}.wf.client.WfApiClient;
import {basePackage}.wf.config.WfClientConfig;
import {basePackage}.wf.model.exception.WfErrorCode;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.CreateSettlementRequest;
import {basePackage}.wf.model.request.ListSettlementsRequest;
import {basePackage}.wf.model.request.QuerySettlementRequest;
import {basePackage}.wf.model.response.CreateSettlementResponse;
import {basePackage}.wf.model.response.ListSettlementsResponse;
import {basePackage}.wf.model.response.QuerySettlementResponse;

/**
 * WorldFirst 外汇（Foreign Exchange）结算服务。
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
 * SettlementService settlementService = new SettlementService(config);
 *
 * // 3. 创建结算（内部已强制验签，验签失败抛 WfException）
 * CreateSettlementRequest request = new CreateSettlementRequest();
 * request.setRequestId("SR20260402001");
 * request.setQuoteId("QT202604021208001");
 *
 * CreateSettlementResponse response = settlementService.createSettlement(request);
 * }</pre>
 */
public class SettlementService {

    /** 创建结算 API 路径 */
    private static final String CREATE_SETTLEMENT_ENDPOINT = "/api/open/v1/fx/settlements/create";

    /** 查询结算 API 路径 */
    private static final String QUERY_SETTLEMENT_ENDPOINT = "/api/open/v1/fx/settlements/query";

    /** 查询结算列表 API 路径 */
    private static final String LIST_SETTLEMENTS_ENDPOINT = "/api/open/v1/fx/settlements/list";

    private final WfApiClient apiClient;

    /**
     * 创建 SettlementService 实例。
     *
     * @param config 客户端配置
     */
    public SettlementService(WfClientConfig config) {
        this.apiClient = new WfApiClient(config);
    }

    /**
     * 创建 SettlementService 实例（使用自定义 ApiClient，便于测试注入）。
     *
     * @param apiClient API 客户端
     */
    public SettlementService(WfApiClient apiClient) {
        this.apiClient = apiClient;
    }

    // ======================== 结算 API ========================

    /**
     * 创建结算。
     * <p>
     * 发起 FX 结算以执行货币兑换。SPOT 结算需提供有效 quoteId；
     * FORWARD 和 UNFUNDED_SPOT 结算需提供 dealId 并指定卖出或买入金额。
     * <p>
     * 对应 WF API: POST /api/open/v1/fx/settlements/create
     *
     * @param request 创建结算请求（包含 requestId、quoteId/dealId、金额等）
     * @return 创建结算响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public CreateSettlementResponse createSettlement(CreateSettlementRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(CREATE_SETTLEMENT_ENDPOINT, body, CreateSettlementResponse.class).getServiceResponse();
    }

    /**
     * 查询结算详情。
     * <p>
     * 通过结算 ID 检索特定结算的完整详情，包括金额、报价详情、状态和时间戳。
     * <p>
     * 对应 WF API: POST /api/open/v1/fx/settlements/query
     *
     * @param request 查询结算请求（包含结算 id）
     * @return 查询结算响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public QuerySettlementResponse querySettlement(QuerySettlementRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(QUERY_SETTLEMENT_ENDPOINT, body, QuerySettlementResponse.class).getServiceResponse();
    }

    /**
     * 查询结算列表。
     * <p>
     * 分页查询 FX 结算记录，支持按关联交易 ID、卖出币种、买入币种、结算状态和创建时间范围过滤。
     * 采用游标分页，{@code fromCreatedAt} 与 {@code toCreatedAt} 的最大跨度为 30 天。
     * <p>
     * 对应 WF API: POST /api/open/v1/fx/settlements/list
     *
     * @param request 查询结算列表请求（limit、cursor、dealId、sellCurrency、buyCurrency、status、时间范围）
     * @return 查询结算列表响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public ListSettlementsResponse listSettlements(ListSettlementsRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(LIST_SETTLEMENTS_ENDPOINT, body, ListSettlementsResponse.class).getServiceResponse();
    }
}
