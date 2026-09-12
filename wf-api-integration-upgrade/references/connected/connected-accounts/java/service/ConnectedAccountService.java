package {basePackage}.wf.service;

import com.alibaba.fastjson2.JSON;

import {basePackage}.wf.client.WfApiClient;
import {basePackage}.wf.config.WfClientConfig;
import {basePackage}.wf.model.exception.WfErrorCode;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.CreateConnectedAccountRequest;
import {basePackage}.wf.model.request.ListConnectedAccountsRequest;
import {basePackage}.wf.model.request.QueryConnectedAccountRequest;
import {basePackage}.wf.model.response.CreateConnectedAccountResponse;
import {basePackage}.wf.model.response.ListConnectedAccountsResponse;
import {basePackage}.wf.model.response.QueryConnectedAccountResponse;

/**
 * WorldFirst 关联账户（Connected Accounts）服务。
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
 * ConnectedAccountService accountService = new ConnectedAccountService(config);
 *
 * // 3. 创建关联账户（内部已强制验签，验签失败抛 WfException）
 * CreateConnectedAccountRequest createRequest = new CreateConnectedAccountRequest();
 * createRequest.setReferenceAccountId("PARTNER_MERCHANT_001");
 * createRequest.setRegistrationRegion("GB");
 * createRequest.setRegistrationLegalName("Example Trading Ltd.");
 * // ... 设置 contact, legalEntityInfo, agreements
 *
 * CreateConnectedAccountResponse createResponse = accountService.createConnectedAccount(createRequest);
 *
 * // 4. 查询关联账户（内部已强制验签，验签失败抛 WfException）
 * QueryConnectedAccountRequest queryRequest = new QueryConnectedAccountRequest();
 * queryRequest.setId(createResponse.getId());
 *
 * QueryConnectedAccountResponse queryResponse = accountService.queryConnectedAccount(queryRequest);
 * }</pre>
 */
public class ConnectedAccountService {

    /** 创建关联账户 API 路径 */
    private static final String CREATE_CONNECTED_ACCOUNT_ENDPOINT = "/api/open/v1/accounts/create";

    /** 查询关联账户 API 路径 */
    private static final String QUERY_CONNECTED_ACCOUNT_ENDPOINT = "/api/open/v1/accounts/query";

    /** 查询关联账户列表 API 路径 */
    private static final String LIST_CONNECTED_ACCOUNTS_ENDPOINT = "/api/open/v1/accounts/list";

    private final WfApiClient apiClient;

    /**
     * 创建 ConnectedAccountService 实例。
     *
     * @param config 客户端配置
     */
    public ConnectedAccountService(WfClientConfig config) {
        this.apiClient = new WfApiClient(config);
    }

    /**
     * 创建 ConnectedAccountService 实例（使用自定义 ApiClient，便于测试注入）。
     *
     * @param apiClient API 客户端
     */
    public ConnectedAccountService(WfApiClient apiClient) {
        this.apiClient = apiClient;
    }

    // ======================== 关联账户 API ========================

    /**
     * 创建关联账户。
     * <p>
     * 对应 WF API: POST /api/open/v1/accounts/create
     *
     * @param request 创建关联账户请求
     * @return 创建关联账户响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public CreateConnectedAccountResponse createConnectedAccount(CreateConnectedAccountRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(CREATE_CONNECTED_ACCOUNT_ENDPOINT, body, CreateConnectedAccountResponse.class).getServiceResponse();
    }

    /**
     * 查询关联账户详情。
     * <p>
     * 对应 WF API: POST /api/open/v1/accounts/query
     *
     * @param request 查询关联账户请求
     * @return 查询关联账户响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public QueryConnectedAccountResponse queryConnectedAccount(QueryConnectedAccountRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(QUERY_CONNECTED_ACCOUNT_ENDPOINT, body, QueryConnectedAccountResponse.class).getServiceResponse();
    }

    /**
     * 查询关联账户列表。
     * <p>
     * 对应 WF API: POST /api/open/v1/accounts/list
     *
     * @param request 查询关联账户列表请求
     * @return 查询关联账户列表响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public ListConnectedAccountsResponse listConnectedAccounts(ListConnectedAccountsRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(LIST_CONNECTED_ACCOUNTS_ENDPOINT, body, ListConnectedAccountsResponse.class).getServiceResponse();
    }
}
