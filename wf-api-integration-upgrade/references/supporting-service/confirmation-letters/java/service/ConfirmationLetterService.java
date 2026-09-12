package {basePackage}.wf.service;

import com.alibaba.fastjson2.JSON;

import {basePackage}.wf.client.WfApiClient;
import {basePackage}.wf.config.WfClientConfig;
import {basePackage}.wf.model.exception.WfErrorCode;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.CreateConfirmationLetterRequest;
import {basePackage}.wf.model.request.QueryConfirmationLetterRequest;
import {basePackage}.wf.model.response.CreateConfirmationLetterResponse;
import {basePackage}.wf.model.response.QueryConfirmationLetterResponse;

/**
 * WorldFirst 确认函（Confirmation Letters）服务。
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
 * ConfirmationLetterService service = new ConfirmationLetterService(config);
 *
 * // 3. 创建确认函下载任务（内部已强制验签，验签失败抛 WfException）
 * CreateConfirmationLetterRequest createRequest = new CreateConfirmationLetterRequest();
 * createRequest.setRequestId(UUID.randomUUID().toString());
 * createRequest.setConfirmationLetterType("STATEMENT_DETAIL_LETTER");
 * StatementDetailLetterParams params = new StatementDetailLetterParams();
 * params.setStatementId("STM202604230001");
 * params.setElectronicallySigned(true);
 * createRequest.setStatementDetailLetterParams(params);
 * CreateConfirmationLetterResponse createResponse = service.createConfirmationLetter(createRequest);
 *
 * // 4. 查询任务状态（内部已强制验签，验签失败抛 WfException）
 * QueryConfirmationLetterRequest queryRequest = new QueryConfirmationLetterRequest();
 * queryRequest.setId(createResponse.getId());
 * QueryConfirmationLetterResponse queryResponse = service.queryConfirmationLetter(queryRequest);
 * }</pre>
 */
public class ConfirmationLetterService {

    /** 创建确认函 API 路径 */
    private static final String CREATE_CONFIRMATION_LETTER_ENDPOINT = "/api/open/v1/confirmationLetters/create";

    /** 查询确认函 API 路径 */
    private static final String QUERY_CONFIRMATION_LETTER_ENDPOINT = "/api/open/v1/confirmationLetters/query";

    private final WfApiClient apiClient;

    /**
     * 创建 ConfirmationLetterService 实例。
     *
     * @param config 客户端配置
     */
    public ConfirmationLetterService(WfClientConfig config) {
        this.apiClient = new WfApiClient(config);
    }

    /**
     * 创建 ConfirmationLetterService 实例（使用自定义 ApiClient，便于测试注入）。
     *
     * @param apiClient API 客户端
     */
    public ConfirmationLetterService(WfApiClient apiClient) {
        this.apiClient = apiClient;
    }

    // ======================== 确认函 API ========================

    /**
     * 创建确认函下载任务。
     * <p>
     * 异步生成确认函 PDF 文件。任务创建后状态为 PROCESSING，
     * 需通过 {@link #queryConfirmationLetter} 轮询获取结果和下载链接。
     * <p>
     * 支持两种确认函类型：
     * <ul>
     *   <li>{@code STATEMENT_DETAIL_LETTER} — 基于对账单记录的交易证明函</li>
     *   <li>{@code ACCOUNT_VERIFICATION_LETTER} — 全球账户验证信息</li>
     * </ul>
     * <p>
     * 对应 WF API: POST /api/open/v1/confirmationLetters/create
     *
     * @param request 创建确认函请求（包含 requestId、confirmationLetterType 及对应参数）
     * @return 创建确认函响应（已验签并反序列化），包含任务 ID 和初始状态
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public CreateConfirmationLetterResponse createConfirmationLetter(CreateConfirmationLetterRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(CREATE_CONFIRMATION_LETTER_ENDPOINT, body, CreateConfirmationLetterResponse.class).getServiceResponse();
    }

    /**
     * 查询确认函下载任务状态。
     * <p>
     * 当任务状态为 {@code SUCCESS} 时，响应中包含预签名下载链接及其过期时间；
     * 当任务状态为 {@code FAIL} 时，响应中包含失败原因码和描述信息。
     * <p>
     * 对应 WF API: POST /api/open/v1/confirmationLetters/query
     *
     * @param request 查询确认函请求（包含 id）
     * @return 查询确认函响应（已验签并反序列化），包含任务状态、下载链接或失败信息
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public QueryConfirmationLetterResponse queryConfirmationLetter(QueryConfirmationLetterRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(QUERY_CONFIRMATION_LETTER_ENDPOINT, body, QueryConfirmationLetterResponse.class).getServiceResponse();
    }
}
