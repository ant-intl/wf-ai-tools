package {basePackage}.wf.service;

import com.alibaba.fastjson2.JSON;

import {basePackage}.wf.client.WfApiClient;
import {basePackage}.wf.config.WfClientConfig;
import {basePackage}.wf.model.exception.WfErrorCode;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.ListStatementsRequest;
import {basePackage}.wf.model.request.QueryStatementRequest;
import {basePackage}.wf.model.response.ListStatementsResponse;
import {basePackage}.wf.model.response.QueryStatementResponse;

/**
 * WorldFirst 对账单（Statement Report）服务。
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
 * StatementService statementService = new StatementService(config);
 *
 * // 3. 查询对账单（内部已强制验签，验签失败抛 WfException）
 * QueryStatementRequest request = new QueryStatementRequest();
 * request.setId("STM202604180000****");
 *
 * QueryStatementResponse response = statementService.queryStatement(request);
 * }</pre>
 */
public class StatementService {

    /** 查询对账单 API 路径 */
    private static final String QUERY_STATEMENT_ENDPOINT = "/api/open/v1/statements/query";

    /** 查询对账单列表 API 路径 */
    private static final String LIST_STATEMENTS_ENDPOINT = "/api/open/v1/statements/list";

    private final WfApiClient apiClient;

    /**
     * 创建 StatementService 实例。
     *
     * @param config 客户端配置
     */
    public StatementService(WfClientConfig config) {
        this.apiClient = new WfApiClient(config);
    }

    /**
     * 创建 StatementService 实例（使用自定义 ApiClient，便于测试注入）。
     *
     * @param apiClient API 客户端
     */
    public StatementService(WfApiClient apiClient) {
        this.apiClient = apiClient;
    }

    // ======================== 对账单 API ========================

    /**
     * 查询对账单详情。
     * <p>
     * 对应 WF API: POST /api/open/v1/statements/query
     *
     * @param request 查询对账单请求
     * @return 查询对账单响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public QueryStatementResponse queryStatement(QueryStatementRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(QUERY_STATEMENT_ENDPOINT, body, QueryStatementResponse.class).getServiceResponse();
    }

    /**
     * 查询对账单列表。
     * <p>
     * 对应 WF API: POST /api/open/v1/statements/list
     *
     * @param request 查询对账单列表请求
     * @return 查询对账单列表响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public ListStatementsResponse listStatements(ListStatementsRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(LIST_STATEMENTS_ENDPOINT, body, ListStatementsResponse.class).getServiceResponse();
    }
}
