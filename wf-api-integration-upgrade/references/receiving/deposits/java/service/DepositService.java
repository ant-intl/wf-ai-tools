package {basePackage}.wf.service;

import com.alibaba.fastjson2.JSON;

import {basePackage}.wf.client.WfApiClient;
import {basePackage}.wf.config.WfClientConfig;
import {basePackage}.wf.model.exception.WfErrorCode;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.ListDepositsRequest;
import {basePackage}.wf.model.request.QueryDepositRequest;
import {basePackage}.wf.model.response.ListDepositsResponse;
import {basePackage}.wf.model.response.QueryDepositResponse;

/**
 * WorldFirst 收款（Receiving）存款服务。
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
 * DepositService depositService = new DepositService(config);
 *
 * // 3. 查询存款（内部已强制验签，验签失败抛 WfException）
 * QueryDepositRequest request = new QueryDepositRequest();
 * request.setId("dep_2026051901HJK7N");
 *
 * QueryDepositResponse response = depositService.queryDeposit(request);
 * }</pre>
 */
public class DepositService {

    /** 查询存款 API 路径 */
    private static final String QUERY_DEPOSIT_ENDPOINT = "/api/open/v1/deposits/query";

    /** 查询存款列表 API 路径 */
    private static final String LIST_DEPOSITS_ENDPOINT = "/api/open/v1/deposits/list";

    private final WfApiClient apiClient;

    /**
     * 创建 DepositService 实例。
     *
     * @param config 客户端配置
     */
    public DepositService(WfClientConfig config) {
        this.apiClient = new WfApiClient(config);
    }

    /**
     * 创建 DepositService 实例（使用自定义 ApiClient，便于测试注入）。
     *
     * @param apiClient API 客户端
     */
    public DepositService(WfApiClient apiClient) {
        this.apiClient = apiClient;
    }

    // ======================== 存款 API ========================

    /**
     * 查询存款详情。
     * <p>
     * 对应 WF API: POST /api/open/v1/deposits/query
     *
     * @param request 查询存款请求
     * @return 查询存款响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public QueryDepositResponse queryDeposit(QueryDepositRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(QUERY_DEPOSIT_ENDPOINT, body, QueryDepositResponse.class).getServiceResponse();
    }

    /**
     * 查询存款列表。
     * <p>
     * 对应 WF API: POST /api/open/v1/deposits/list
     *
     * @param request 查询存款列表请求
     * @return 查询存款列表响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public ListDepositsResponse listDeposits(ListDepositsRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(LIST_DEPOSITS_ENDPOINT, body, ListDepositsResponse.class).getServiceResponse();
    }
}
