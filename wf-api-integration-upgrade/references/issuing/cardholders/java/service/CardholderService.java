package {basePackage}.wf.service;

import com.alibaba.fastjson2.JSON;

import {basePackage}.wf.client.WfApiClient;
import {basePackage}.wf.config.WfClientConfig;
import {basePackage}.wf.model.exception.WfErrorCode;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.CreateCardholderRequest;
import {basePackage}.wf.model.request.DeleteCardholderRequest;
import {basePackage}.wf.model.request.ListCardholdersRequest;
import {basePackage}.wf.model.request.QueryCardholderRequest;
import {basePackage}.wf.model.response.CreateCardholderResponse;
import {basePackage}.wf.model.response.DeleteCardholderResponse;
import {basePackage}.wf.model.response.ListCardholdersResponse;
import {basePackage}.wf.model.response.QueryCardholderResponse;

/**
 * WorldFirst 发卡（Issuing）持卡人服务。
 *
 * <p>内部使用 {@link WfApiClient} 处理 HTTP 请求与加验签，采用薄封装模式：
 * 不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。
 * <p><b>安全强制</b>：{@link WfApiClient} 在返回响应前会执行验签，
 * 验签失败时抛出 {@link WfException}（错误码 {@link WfErrorCode#INVALID_SIGNATURE}），
 * 确保调用方拿到的响应均已完成验签，避免中间人攻击。
 *
 * <p>持卡人须达到 ACTIVE 状态后方可发卡；删除为永久操作，且要求名下已无关联卡片。
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
 * CardholderService cardholderService = new CardholderService(config);
 *
 * // 3. 创建持卡人（内部已强制验签，验签失败抛 WfException）
 * CreateCardholderRequest request = new CreateCardholderRequest();
 * request.setRequestId("req_20240115_001");
 * request.setType("EMPLOYEE");
 * request.setNationality("CHN");
 *
 * CreateCardholderResponse response = cardholderService.createCardholder(request);
 * }</pre>
 */
public class CardholderService {

    /** 创建持卡人 API 路径 */
    private static final String CREATE_CARDHOLDER_ENDPOINT = "/api/open/v1/issuing/cardholders/create";

    /** 查询持卡人 API 路径 */
    private static final String QUERY_CARDHOLDER_ENDPOINT = "/api/open/v1/issuing/cardholders/query";

    /** 查询持卡人列表 API 路径 */
    private static final String LIST_CARDHOLDERS_ENDPOINT = "/api/open/v1/issuing/cardholders/list";

    /** 删除持卡人 API 路径 */
    private static final String DELETE_CARDHOLDER_ENDPOINT = "/api/open/v1/issuing/cardholders/delete";

    private final WfApiClient apiClient;

    /**
     * 创建 CardholderService 实例。
     *
     * @param config 客户端配置
     */
    public CardholderService(WfClientConfig config) {
        this.apiClient = new WfApiClient(config);
    }

    /**
     * 创建 CardholderService 实例（使用自定义 ApiClient，便于测试注入）。
     *
     * @param apiClient API 客户端
     */
    public CardholderService(WfApiClient apiClient) {
        this.apiClient = apiClient;
    }

    // ======================== 持卡人 API ========================

    /**
     * 创建持卡人。
     * <p>
     * 注册持卡人并提交身份证明材料进入 KYC 审核。创建成功后状态固定为 PENDING，
     * 需通过 {@link #queryCardholder} 轮询状态，达到 ACTIVE 后方可发卡。
     * {@code requestId} 为幂等键，重试必须复用同一取值以避免重复创建。
     * <p>
     * 对应 WF API: POST /api/open/v1/issuing/cardholders/create
     *
     * @param request 创建持卡人请求（包含 requestId、type、personInfo、nationality、identification）
     * @return 创建持卡人响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public CreateCardholderResponse createCardholder(CreateCardholderRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(CREATE_CARDHOLDER_ENDPOINT, body, CreateCardholderResponse.class).getServiceResponse();
    }

    /**
     * 查询持卡人详情。
     * <p>
     * 通过持卡人 ID 检索详情与审核状态，用于在创建后轮询 KYC 结果。
     * <p>
     * 对应 WF API: POST /api/open/v1/issuing/cardholders/query
     *
     * @param request 查询持卡人请求（包含 id）
     * @return 查询持卡人响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public QueryCardholderResponse queryCardholder(QueryCardholderRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(QUERY_CARDHOLDER_ENDPOINT, body, QueryCardholderResponse.class).getServiceResponse();
    }

    /**
     * 查询持卡人列表。
     * <p>
     * 分页查询当前账户下的持卡人，可按状态过滤。采用游标分页，
     * 响应未返回 {@code nextCursor} 即表示已无更多数据。
     * <p>
     * 对应 WF API: POST /api/open/v1/issuing/cardholders/list
     *
     * @param request 查询持卡人列表请求（包含 status、limit、cursor）
     * @return 查询持卡人列表响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public ListCardholdersResponse listCardholders(ListCardholdersRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(LIST_CARDHOLDERS_ENDPOINT, body, ListCardholdersResponse.class).getServiceResponse();
    }

    /**
     * 删除持卡人。
     * <p>
     * 永久删除持卡人。仅当该持卡人名下已无任何关联卡片时才可删除，
     * 否则返回 CARDHOLDER_HAS_ASSOCIATED_CARD。删除不可恢复，执行前建议先查询确认。
     * <p>
     * 对应 WF API: POST /api/open/v1/issuing/cardholders/delete
     *
     * @param request 删除持卡人请求（包含 id）
     * @return 删除持卡人响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public DeleteCardholderResponse deleteCardholder(DeleteCardholderRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(DELETE_CARDHOLDER_ENDPOINT, body, DeleteCardholderResponse.class).getServiceResponse();
    }
}
