package {basePackage}.wf.service;

import com.alibaba.fastjson2.JSON;

import {basePackage}.wf.client.WfApiClient;
import {basePackage}.wf.config.WfClientConfig;
import {basePackage}.wf.model.exception.WfErrorCode;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.ConsultPayoutRequest;
import {basePackage}.wf.model.request.CreatePayoutRequest;
import {basePackage}.wf.model.request.QueryPayoutRequest;
import {basePackage}.wf.model.response.PayoutResponse;

/**
 * WorldFirst 代发（Payouts）服务。
 *
 * <p>内部使用 {@link WfApiClient} 处理 HTTP 请求与加验签，采用薄封装模式：
 * 不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。
 * <p><b>安全强制</b>：{@link WfApiClient} 在返回响应前会执行验签，
 * 验签失败时抛出 {@link WfException}（错误码 {@link WfErrorCode#INVALID_SIGNATURE}）。
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
 * PayoutService payoutService = new PayoutService(config);
 *
 * // 3. 创建代发（内部已强制验签，验签失败抛 WfException）
 * CreatePayoutRequest request = new CreatePayoutRequest();
 * request.setRequestId(UUID.randomUUID().toString());
 * // ... 设置其他参数
 *
 * PayoutResponse response = payoutService.createPayout(request);
 * }</pre>
 */
public class PayoutService {

    /** 咨询代发 API 路径 */
    private static final String CONSULT_PAYOUT_ENDPOINT = "/api/open/v1/payouts/consult";

    /** 创建代发 API 路径 */
    private static final String CREATE_PAYOUT_ENDPOINT = "/api/open/v1/payouts/create";

    /** 查询代发 API 路径 */
    private static final String QUERY_PAYOUT_ENDPOINT = "/api/open/v1/payouts/query";

    private final WfApiClient apiClient;

    /**
     * 创建 PayoutService 实例。
     *
     * @param config 客户端配置
     */
    public PayoutService(WfClientConfig config) {
        this.apiClient = new WfApiClient(config);
    }

    /**
     * 创建 PayoutService 实例（使用自定义 ApiClient，便于测试注入）。
     *
     * @param apiClient API 客户端
     */
    public PayoutService(WfApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * 咨询代发：预校验代发并获取手续费和汇率估算。
     * <p>
     * 对应 WF API: POST /api/open/v1/payouts/consult
     *
     * @param request 咨询代发请求
     * @return 咨询代发响应（含费用和汇率估算，已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public PayoutResponse consultPayout(ConsultPayoutRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(CONSULT_PAYOUT_ENDPOINT, body, PayoutResponse.class).getServiceResponse();
    }

    /**
     * 创建代发：发起代发到收款方银行账户或电子钱包。
     * <p>
     * 对应 WF API: POST /api/open/v1/payouts/create
     *
     * @param request 创建代发请求
     * @return 创建代发响应（含代发单 ID 和状态，已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public PayoutResponse createPayout(CreatePayoutRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(CREATE_PAYOUT_ENDPOINT, body, PayoutResponse.class).getServiceResponse();
    }

    /**
     * 查询代发：根据代发单 ID 查询当前状态和详情。
     * <p>
     * 对应 WF API: POST /api/open/v1/payouts/query
     *
     * @param request 查询代发请求
     * @return 查询代发响应（含代发单完整信息，已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public PayoutResponse queryPayout(QueryPayoutRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(QUERY_PAYOUT_ENDPOINT, body, PayoutResponse.class).getServiceResponse();
    }
}
