package {basePackage}.wf.service;

import com.alibaba.fastjson2.JSON;

import {basePackage}.wf.client.WfApiClient;
import {basePackage}.wf.config.WfClientConfig;
import {basePackage}.wf.model.exception.WfErrorCode;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.model.request.AuthorizeRequest;
import {basePackage}.wf.model.request.RequestTokenRequest;
import {basePackage}.wf.model.request.RevokeTokenRequest;
import {basePackage}.wf.model.response.RequestTokenResponse;
import {basePackage}.wf.model.response.RevokeTokenResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * WorldFirst OAuth2 授权服务。
 *
 * <p>内部使用 {@link WfApiClient} 处理 HTTP 请求与加验签，采用薄封装模式：
 * 不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。
 * <p><b>安全强制</b>：{@link WfApiClient} 在返回响应前会执行验签，
 * 验签失败时抛出 {@link WfException}（错误码 {@link WfErrorCode#INVALID_SIGNATURE}），
 * 确保调用方拿到的响应均已完成验签，避免中间人攻击。
 *
 * <p><b>注意</b>：{@code authorize} 接口为浏览器端重定向，不通过本 Service 发起 HTTP 调用，
 * 而是通过 {@link #buildAuthorizationUrl(AuthorizeRequest)} 构造 URL 后由前端重定向用户。
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
 * OAuth2Service oauth2Service = new OAuth2Service(config);
 *
 * // 3. 构造授权 URL（浏览器重定向）
 * AuthorizeRequest authRequest = new AuthorizeRequest();
 * authRequest.setClientId("YOUR_CLIENT_ID");
 * authRequest.setRedirectUri("https://your-app.com/callback");
 * authRequest.setScope("payouts:write accounts:read");
 * authRequest.setState("a1b2c3d4e5f6");
 * authRequest.setReferenceAccountId("merchant-123");
 * String url = oauth2Service.buildAuthorizationUrl(authRequest);
 *
 * // 4. 换取令牌（内部已强制验签，验签失败抛 WfException）
 * RequestTokenRequest tokenRequest = new RequestTokenRequest();
 * tokenRequest.setGrantType("AUTHORIZATION_CODE");
 * tokenRequest.setCode("SG_WDFTW3HK_zzzz****");
 * RequestTokenResponse tokenResponse = oauth2Service.requestToken(tokenRequest);
 * }</pre>
 */
public class OAuth2Service {

    /** 授权 API 路径（用于构造 URL，非 HTTP 调用） */
    private static final String AUTHORIZE_ENDPOINT = "/api/open/v1/oauth/authorize";

    /** 请求令牌 API 路径 */
    private static final String TOKEN_ENDPOINT = "/api/open/v1/oauth/token";

    /** 撤销令牌 API 路径 */
    private static final String REVOKE_ENDPOINT = "/api/open/v1/oauth/revoke";

    private final WfApiClient apiClient;
    private final String baseUrl;

    /**
     * 创建 OAuth2Service 实例。
     *
     * @param config 客户端配置
     */
    public OAuth2Service(WfClientConfig config) {
        this.apiClient = new WfApiClient(config);
        this.baseUrl = config.getBaseUrl();
    }

    /**
     * 创建 OAuth2Service 实例（使用自定义 ApiClient，便于测试注入）。
     *
     * @param apiClient API 客户端
     * @param baseUrl   基础 URL
     */
    public OAuth2Service(WfApiClient apiClient, String baseUrl) {
        this.apiClient = apiClient;
        this.baseUrl = baseUrl;
    }

    // ======================== OAuth2 API ========================

    /**
     * 构造授权 URL，供浏览器重定向使用。
     * <p>
     * 对应 WF API: GET /api/open/v1/oauth/authorize
     * <p>
     * <b>注意</b>：此方法不发起 HTTP 调用，仅构造 URL 字符串。
     * 调用方需将用户浏览器重定向到返回的 URL。
     * scope 参数中的空格使用 %20 编码（而非 +），以确保可靠解析。
     *
     * @param request 授权请求参数
     * @return 完整的授权 URL 字符串
     */
    public String buildAuthorizationUrl(AuthorizeRequest request) {
        StringBuilder url = new StringBuilder(baseUrl)
                .append(AUTHORIZE_ENDPOINT)
                .append("?clientId=").append(request.getClientId())
                .append("&redirectUri=").append(encode(request.getRedirectUri()))
                .append("&scope=").append(encode(request.getScope()))
                .append("&state=").append(request.getState())
                .append("&referenceAccountId=").append(request.getReferenceAccountId());
        return url.toString();
    }

    /**
     * 使用授权码或刷新令牌换取访问令牌。
     * <p>
     * 对应 WF API: POST /api/open/v1/oauth/token
     *
     * @param request 请求令牌参数
     * @return 请求令牌响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public RequestTokenResponse requestToken(RequestTokenRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(TOKEN_ENDPOINT, body, RequestTokenResponse.class).getServiceResponse();
    }

    /**
     * 撤销访问令牌。
     * <p>
     * 对应 WF API: POST /api/open/v1/oauth/revoke
     *
     * @param request 撤销令牌参数
     * @return 撤销令牌响应（已验签并反序列化）
     * @throws WfException 响应验签失败时抛出（错误码 INVALID_SIGNATURE）
     */
    public RevokeTokenResponse revokeToken(RevokeTokenRequest request) {
        String body = JSON.toJSONString(request);
        return apiClient.post(REVOKE_ENDPOINT, body, RevokeTokenResponse.class).getServiceResponse();
    }

    /**
     * URL 编码，空格使用 %20（而非 +）。
     */
    private static String encode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 encoding not supported", e);
        }
    }
}
