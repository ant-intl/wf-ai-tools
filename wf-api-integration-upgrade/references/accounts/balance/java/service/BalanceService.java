package {basePackage}.wf.service;

import com.alibaba.fastjson2.JSON;

import {basePackage}.wf.client.WfApiClient;
import {basePackage}.wf.config.WfClientConfig;
import {basePackage}.wf.model.exception.WfErrorCode;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.ListBalanceHistoryRequest;
import {basePackage}.wf.model.request.QueryBalanceRequest;
import {basePackage}.wf.model.response.ListBalanceHistoryResponse;
import {basePackage}.wf.model.response.QueryBalanceResponse;

/**
 * WorldFirst 账户管理（Account Management）余额服务。
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
 * BalanceService balanceService = new BalanceService(config);
 *
 * // 3. 查询余额（内部已强制验签，验签失败抛 WfException）
 * QueryBalanceRequest request = new QueryBalanceRequest();
 * request.setCurrencies(Arrays.asList("USD"));
 * // ... 设置其他参数
 *
 * QueryBalanceResponse response = balanceService.queryBalance(request);
 * }</pre>
 */
public class BalanceService {

    /** 查询余额 API 路径 */
    private static final String QUERY_BALANCE_ENDPOINT = "/api/open/v1/balances/query";

    /** 查询余额变动历史 API 路径 */
    private static final String LIST_BALANCE_HISTORY_ENDPOINT = "/api/open/v1/balances/listHistory";

    private final WfApiClient apiClient;

    /**
     * 创建 BalanceService 实例。
     *
     * @param config 客户端配置
     */
    public BalanceService(WfClientConfig config) {
        this.apiClient = new WfApiClient(config);
    }

    /**
     * 创建 BalanceService 实例（使用自定义 ApiClient，便于测试注入）。
     *
     * @param apiClient API 客户端
     */
    public BalanceService(WfApiClient apiClient) {
        this.apiClient = apiClient;
    }

    // ======================== 余额 API ========================

    /**
     * 查询余额。
     * <p>
     * 对应 WF API: POST /api/open/v1/balances/query
     *
     * @param request 查询余额请求
     * @return 查询余额响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public QueryBalanceResponse queryBalance(QueryBalanceRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(QUERY_BALANCE_ENDPOINT, body, QueryBalanceResponse.class).getServiceResponse();
    }

    /**
     * 查询余额变动历史。
     * <p>
     * 对应 WF API: POST /api/open/v1/balances/listHistory
     *
     * @param request 查询余额变动历史请求
     * @return 查询余额变动历史响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public ListBalanceHistoryResponse listBalanceHistory(ListBalanceHistoryRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(LIST_BALANCE_HISTORY_ENDPOINT, body, ListBalanceHistoryResponse.class).getServiceResponse();
    }
}
