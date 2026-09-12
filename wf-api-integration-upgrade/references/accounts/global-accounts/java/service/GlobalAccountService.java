package {basePackage}.wf.service;

import com.alibaba.fastjson2.JSON;

import {basePackage}.wf.client.WfApiClient;
import {basePackage}.wf.config.WfClientConfig;
import {basePackage}.wf.model.exception.WfErrorCode;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.CloseGlobalAccountRequest;
import {basePackage}.wf.model.request.CreateGlobalAccountRequest;
import {basePackage}.wf.model.request.ListGlobalAccountsRequest;
import {basePackage}.wf.model.request.QueryGlobalAccountRequest;
import {basePackage}.wf.model.request.UpdateGlobalAccountRequest;
import {basePackage}.wf.model.response.GlobalAccountResponse;
import {basePackage}.wf.model.response.ListGlobalAccountsResponse;

/**
 * WorldFirst 全局账户（Global Accounts）服务。
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
 * GlobalAccountService globalAccountService = new GlobalAccountService(config);
 *
 * // 3. 创建全局账户（内部已强制验签，验签失败抛 WfException）
 * CreateGlobalAccountRequest request = new CreateGlobalAccountRequest();
 * request.setRequestId(UUID.randomUUID().toString());
 * request.setBankRegion("US");
 * // ... 设置其他参数
 *
 * GlobalAccountResponse response = globalAccountService.createGlobalAccount(request);
 * }</pre>
 */
public class GlobalAccountService {

    /** 创建全局账户 API 路径 */
    private static final String CREATE_GLOBAL_ACCOUNT_ENDPOINT = "/api/open/v1/globalAccounts/create";

    /** 查询全局账户 API 路径 */
    private static final String QUERY_GLOBAL_ACCOUNT_ENDPOINT = "/api/open/v1/globalAccounts/query";

    /** 列表查询全局账户 API 路径 */
    private static final String LIST_GLOBAL_ACCOUNTS_ENDPOINT = "/api/open/v1/globalAccounts/list";

    /** 更新全局账户 API 路径 */
    private static final String UPDATE_GLOBAL_ACCOUNT_ENDPOINT = "/api/open/v1/globalAccounts/update";

    /** 关闭全局账户 API 路径 */
    private static final String CLOSE_GLOBAL_ACCOUNT_ENDPOINT = "/api/open/v1/globalAccounts/close";

    private final WfApiClient apiClient;

    /**
     * 创建 GlobalAccountService 实例。
     *
     * @param config 客户端配置
     */
    public GlobalAccountService(WfClientConfig config) {
        this.apiClient = new WfApiClient(config);
    }

    /**
     * 创建 GlobalAccountService 实例（使用自定义 ApiClient，便于测试注入）。
     *
     * @param apiClient API 客户端
     */
    public GlobalAccountService(WfApiClient apiClient) {
        this.apiClient = apiClient;
    }

    // ======================== 全局账户 API ========================

    /**
     * 创建全局账户。
     * <p>
     * 对应 WF API: POST /api/open/v1/globalAccounts/create
     *
     * @param request 创建全局账户请求
     * @return 创建全局账户响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public GlobalAccountResponse createGlobalAccount(CreateGlobalAccountRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(CREATE_GLOBAL_ACCOUNT_ENDPOINT, body, GlobalAccountResponse.class).getServiceResponse();
    }

    /**
     * 查询全局账户。
     * <p>
     * 对应 WF API: POST /api/open/v1/globalAccounts/query
     *
     * @param request 查询全局账户请求
     * @return 查询全局账户响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public GlobalAccountResponse queryGlobalAccount(QueryGlobalAccountRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(QUERY_GLOBAL_ACCOUNT_ENDPOINT, body, GlobalAccountResponse.class).getServiceResponse();
    }

    /**
     * 列表查询全局账户。
     * <p>
     * 对应 WF API: POST /api/open/v1/globalAccounts/list
     *
     * @param request 列表查询全局账户请求
     * @return 列表查询全局账户响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public ListGlobalAccountsResponse listGlobalAccounts(ListGlobalAccountsRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(LIST_GLOBAL_ACCOUNTS_ENDPOINT, body, ListGlobalAccountsResponse.class).getServiceResponse();
    }

    /**
     * 更新全局账户。
     * <p>
     * 对应 WF API: POST /api/open/v1/globalAccounts/update
     *
     * @param request 更新全局账户请求
     * @return 更新全局账户响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public GlobalAccountResponse updateGlobalAccount(UpdateGlobalAccountRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(UPDATE_GLOBAL_ACCOUNT_ENDPOINT, body, GlobalAccountResponse.class).getServiceResponse();
    }

    /**
     * 关闭全局账户。
     * <p>
     * 对应 WF API: POST /api/open/v1/globalAccounts/close
     *
     * @param request 关闭全局账户请求
     * @return 关闭全局账户响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public GlobalAccountResponse closeGlobalAccount(CloseGlobalAccountRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(CLOSE_GLOBAL_ACCOUNT_ENDPOINT, body, GlobalAccountResponse.class).getServiceResponse();
    }
}
