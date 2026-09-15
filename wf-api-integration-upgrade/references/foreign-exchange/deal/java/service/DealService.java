package {basePackage}.wf.service;

import com.alibaba.fastjson2.JSON;

import {basePackage}.wf.client.WfApiClient;
import {basePackage}.wf.config.WfClientConfig;
import {basePackage}.wf.model.exception.WfErrorCode;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.CancelDealRequest;
import {basePackage}.wf.model.request.CreateDealRequest;
import {basePackage}.wf.model.request.ListDealsRequest;
import {basePackage}.wf.model.request.ListMarginChargeRecordsRequest;
import {basePackage}.wf.model.request.QueryDealRequest;
import {basePackage}.wf.model.response.CancelDealResponse;
import {basePackage}.wf.model.response.CreateDealResponse;
import {basePackage}.wf.model.response.ListDealsResponse;
import {basePackage}.wf.model.response.ListMarginChargeRecordsResponse;
import {basePackage}.wf.model.response.QueryDealResponse;

/**
 * WorldFirst 外汇（Foreign Exchange）交易服务。
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
 * DealService dealService = new DealService(config);
 *
 * // 3. 创建交易（内部已强制验签，验签失败抛 WfException）
 * CreateDealRequest request = new CreateDealRequest();
 * request.setRequestId("REQ20260402001");
 * request.setQuoteId("QT202604021208001");
 *
 * CreateDealResponse response = dealService.createDeal(request);
 * }</pre>
 */
public class DealService {

    /** 创建交易 API 路径 */
    private static final String CREATE_DEAL_ENDPOINT = "/api/open/v1/fx/deals/create";

    /** 查询交易 API 路径 */
    private static final String QUERY_DEAL_ENDPOINT = "/api/open/v1/fx/deals/query";

    /** 取消交易 API 路径 */
    private static final String CANCEL_DEAL_ENDPOINT = "/api/open/v1/fx/deals/cancel";

    /** 查询交易列表 API 路径 */
    private static final String LIST_DEALS_ENDPOINT = "/api/open/v1/fx/deals/list";

    /** 查询保证金追缴记录列表 API 路径 */
    private static final String LIST_MARGIN_CHARGE_RECORDS_ENDPOINT = "/api/open/v1/fx/deals/listMarginChargeRecords";

    private final WfApiClient apiClient;

    /**
     * 创建 DealService 实例。
     *
     * @param config 客户端配置
     */
    public DealService(WfClientConfig config) {
        this.apiClient = new WfApiClient(config);
    }

    /**
     * 创建 DealService 实例（使用自定义 ApiClient，便于测试注入）。
     *
     * @param apiClient API 客户端
     */
    public DealService(WfApiClient apiClient) {
        this.apiClient = apiClient;
    }

    // ======================== 交易 API ========================

    /**
     * 创建交易。
     * <p>
     * 使用从 Quote API 获取的有效报价执行 FX 交易。
     * 交易类型（SPOT、UNFUNDED_SPOT、FORWARD）由所使用的报价决定。
     * SPOT 交易自动结算；UNFUNDED_SPOT 和 FORWARD 交易需要通过 Settlement API 显式结算。
     * <p>
     * 对应 WF API: POST /api/open/v1/fx/deals/create
     *
     * @param request 创建交易请求（包含 requestId 和 quoteId）
     * @return 创建交易响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public CreateDealResponse createDeal(CreateDealRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(CREATE_DEAL_ENDPOINT, body, CreateDealResponse.class).getServiceResponse();
    }

    /**
     * 查询交易详情。
     * <p>
     * 通过交易 ID 检索特定交易的完整详情，包括结算状态、保证金信息等。
     * 部分字段根据交易类型和当前状态有条件返回。
     * <p>
     * 对应 WF API: POST /api/open/v1/fx/deals/query
     *
     * @param request 查询交易请求（包含交易 id）
     * @return 查询交易响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public QueryDealResponse queryDeal(QueryDealRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(QUERY_DEAL_ENDPOINT, body, QueryDealResponse.class).getServiceResponse();
    }

    /**
     * 取消交易。
     * <p>
     * 取消尚未结算的交易。仅处于 PROCESSING 状态的交易可被取消。
     * 取消成功后交易状态变为 CANCELED，根据交易类型可能收取取消费用。
     * <p>
     * 对应 WF API: POST /api/open/v1/fx/deals/cancel
     *
     * @param request 取消交易请求（包含交易 id）
     * @return 取消交易响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public CancelDealResponse cancelDeal(CancelDealRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(CANCEL_DEAL_ENDPOINT, body, CancelDealResponse.class).getServiceResponse();
    }

    /**
     * 查询交易列表。
     * <p>
     * 分页查询 FX 交易记录，支持按卖出币种、买入币种、交易状态和创建时间范围过滤。
     * 采用游标分页，{@code fromCreatedAt} 与 {@code toCreatedAt} 的最大跨度为 31 天。
     * <p>
     * 对应 WF API: POST /api/open/v1/fx/deals/list
     *
     * @param request 查询交易列表请求（limit、cursor、sellCurrency、buyCurrency、status、时间范围）
     * @return 查询交易列表响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public ListDealsResponse listDeals(ListDealsRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(LIST_DEALS_ENDPOINT, body, ListDealsResponse.class).getServiceResponse();
    }

    // ======================== 保证金 API ========================

    /**
     * 查询保证金追缴记录列表。
     * <p>
     * 分页查询远期交易（FORWARD）的保证金追缴记录，支持按交易 ID、保证金场景、状态和创建时间范围过滤，
     * 用于跟踪保证金的冻结、追加与释放。采用游标分页，{@code fromCreatedAt} 与 {@code toCreatedAt} 的最大跨度为 31 天。
     * <p>
     * 对应 WF API: POST /api/open/v1/fx/deals/listMarginChargeRecords
     *
     * @param request 查询保证金追缴记录列表请求（limit、cursor、dealId、scene、status、时间范围）
     * @return 查询保证金追缴记录列表响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public ListMarginChargeRecordsResponse listMarginChargeRecords(ListMarginChargeRecordsRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(LIST_MARGIN_CHARGE_RECORDS_ENDPOINT, body, ListMarginChargeRecordsResponse.class).getServiceResponse();
    }
}
