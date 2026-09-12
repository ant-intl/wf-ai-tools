package {basePackage}.wf.service;

import com.alibaba.fastjson2.JSON;

import {basePackage}.wf.client.WfApiClient;
import {basePackage}.wf.config.WfClientConfig;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.QueryOtpConfigRequest;
import {basePackage}.wf.model.request.UpdateOtpConfigRequest;
import {basePackage}.wf.model.response.QueryOtpConfigResponse;
import {basePackage}.wf.model.response.UpdateOtpConfigResponse;

/**
 * WorldFirst 发卡（Issuing）OTP 配置管理服务。
 *
 * <p>内部使用 {@link WfApiClient} 处理 HTTP 请求与加验签，采用薄封装模式：
 * 不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。
 * <p><b>安全强制</b>：{@link WfApiClient} 在返回响应前会执行验签，
 * 验签失败时抛出 {@link WfException}（错误码 INVALID_SIGNATURE），
 * 确保调用方拿到的响应均已完成验签，避免中间人攻击。
 *
 * <p>本模块管理账户下卡片的 3DS 一次性密码（OTP）通知投递方式，
 * 即是否通过 API 接收 OTP 通知（{@code OtpApiNotifyPreference}：ON / OFF）。
 * 配置作用于账户维度，非单张卡片维度。
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
 * OtpManagementService otpManagementService = new OtpManagementService(config);
 *
 * // 3. 查询 OTP API 通知配置（内部已强制验签，验签失败抛 WfException）
 * QueryOtpConfigResponse response = otpManagementService.queryOtpConfig(new QueryOtpConfigRequest());
 * }</pre>
 */
public class OtpManagementService {

    /** 查询 OTP API 通知配置 API 路径 */
    private static final String QUERY_OTP_CONFIG_ENDPOINT = "/api/open/v1/issuing/cards/queryOtpConfig";

    /** 更新 OTP API 通知配置 API 路径 */
    private static final String UPDATE_OTP_CONFIG_ENDPOINT = "/api/open/v1/issuing/cards/updateOtpConfig";

    private final WfApiClient apiClient;

    /**
     * 创建 OtpManagementService 实例。
     *
     * @param config 客户端配置
     */
    public OtpManagementService(WfClientConfig config) {
        this.apiClient = new WfApiClient(config);
    }

    /**
     * 创建 OtpManagementService 实例（使用自定义 ApiClient，便于测试注入）。
     *
     * @param apiClient API 客户端
     */
    public OtpManagementService(WfApiClient apiClient) {
        this.apiClient = apiClient;
    }

    // ======================== OTP 配置管理 API ========================

    /**
     * 查询 OTP API 通知配置。
     * <p>
     * 查询账户下卡片的 3DS OTP 通知是否通过 API 投递，返回
     * {@code otpApiNotifyPreference}（ON：接收；OFF：不接收）。
     * 本接口无请求参数，请求体为 {@code {}}。
     * <p>
     * 对应 WF API: POST /api/open/v1/issuing/cards/queryOtpConfig
     *
     * @param request 查询 OTP 配置请求（无业务字段，保留用于扩展）
     * @return 查询 OTP 配置响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public QueryOtpConfigResponse queryOtpConfig(QueryOtpConfigRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(QUERY_OTP_CONFIG_ENDPOINT, body, QueryOtpConfigResponse.class).getServiceResponse();
    }

    /**
     * 更新 OTP API 通知配置。
     * <p>
     * 开启或关闭通过 API 接收账户下卡片的 3DS OTP 通知。
     * {@code otpApiNotifyPreference} 仅接受 ON / OFF 两个取值；更新结果通过
     * {@code result} 判定，响应不回显配置值，需再次查询确认。
     * <p>
     * 对应 WF API: POST /api/open/v1/issuing/cards/updateOtpConfig
     *
     * @param request 更新 OTP 配置请求（包含 otpApiNotifyPreference）
     * @return 更新 OTP 配置响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public UpdateOtpConfigResponse updateOtpConfig(UpdateOtpConfigRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(UPDATE_OTP_CONFIG_ENDPOINT, body, UpdateOtpConfigResponse.class).getServiceResponse();
    }
}
