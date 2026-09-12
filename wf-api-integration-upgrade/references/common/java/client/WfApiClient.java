package {basePackage}.wf.client;

import com.alibaba.fastjson2.JSON;

import {basePackage}.wf.config.WfClientConfig;
import {basePackage}.wf.model.exception.WfErrorCode;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.security.WfSignatureUtil;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * WorldFirst API 客户端，封装 HTTP 请求与加验签逻辑。
 * <p>
 * 基于 OkHttp 4.x（{@link OkHttpClient}）实现，连接池复用、JDK 8 兼容。
 * 每次请求自动完成以下流程：
 * <ol>
 *   <li>生成 ISO 8601 格式的 Request-Time</li>
 *   <li>构建签名字符串并使用私钥签名</li>
 *   <li>设置 Signature / Client-Id / Request-Time 请求头</li>
 *   <li>发送 HTTP 请求</li>
 *   <li>使用公钥验证响应签名</li>
 * </ol>
 *
 * <pre>{@code
 * WfClientConfig config = WfClientConfig.builder()
 *     .clientId("YOUR_CLIENT_ID")
 *     .privateKeyFromPath("/path/to/private_key.pem")
 *     .publicKeyFromPath("/path/to/wf_public_key.pem")
 *     .build();
 *
 * WfApiClient client = new WfApiClient(config);
 * WfApiResponse<MyResponse> response = client.post("/api/open/v1/payouts/create", requestBody, MyResponse.class);
 * MyResponse result = response.getServiceResponse();
 * }</pre>
 */
public class WfApiClient {

    /** JSON 媒体类型 */
    private static final MediaType JSON_MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");

    /** 客户端配置 */
    private final WfClientConfig config;

    /** OkHttp 客户端（连接池复用） */
    private final OkHttpClient httpClient;

    /**
     * 创建 WfApiClient 实例。
     * <p>
     * 内部创建 {@link OkHttpClient} 实例，连接池复用，超时时间取自
     * {@code config.getTimeoutMillis()}。
     *
     * @param config 客户端配置
     */
    public WfApiClient(WfClientConfig config) {
        this.config = config;
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(config.getTimeoutMillis(), TimeUnit.MILLISECONDS)
                .readTimeout(config.getTimeoutMillis(), TimeUnit.MILLISECONDS)
                .build();
    }

    /**
     * 发送 POST 请求。
     *
     * @param endpoint API 路径，如 "/api/open/v1/payouts/create"
     * @param body     请求体 JSON 字符串
     * @param responseClass 业务响应类型
     * @return API 响应
     * @throws WfException 签名失败、验签失败或 HTTP 请求异常
     */
    public <T> WfApiResponse<T> post(String endpoint, String body, Class<T> responseClass) {
        if (body == null) {
            throw new IllegalArgumentException("POST 请求体不能为 null");
        }
        return execute("POST", endpoint, body, responseClass);
    }

    /**
     * 发送 GET 请求。
     *
     * @param endpoint API 路径
     * @param responseClass 业务响应类型
     * @return API 响应
     * @throws WfException 验签失败或 HTTP 请求异常
     */
    public <T> WfApiResponse<T> get(String endpoint, Class<T> responseClass) {
        return execute("GET", endpoint, null, responseClass);
    }

    /**
     * 执行 HTTP 请求（带签名和验签）。
     * <p>
     * 使用 {@link OkHttpClient} 发送 HTTPS 请求，超时时间取自
     * {@code config.getTimeoutMillis()}（在构造函数中已配置）。
     *
     * @param method   HTTP 方法（POST / GET）
     * @param endpoint API 路径
     * @param body     请求体（GET 请求时为 null）
     * @param responseClass 业务响应类型，用于反序列化响应体
     * @return API 响应
     * @throws WfException 当 HTTP 请求发生 IOException 时
     */
    private <T> WfApiResponse<T> execute(String method, String endpoint, String body,
                                        Class<T> responseClass) {
        // 1. 生成请求时间（ISO 8601 格式）
        String requestTime = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        // 2. 构建签名字符串并签名
        String signingString = WfSignatureUtil.buildSigningString(
                method, endpoint, config.getClientId(), requestTime, body);
        String signatureHeader = WfSignatureUtil.buildSignatureHeader(
                signingString, config.getPrivateKey(), config.getKeyVersion());

        // 3. 构建请求（4 个 header: Content-Type, Client-Id, Request-Time, Signature）
        String url = config.getBaseUrl() + endpoint;
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .header("Content-Type", "application/json; charset=UTF-8")
                .header("Client-Id", config.getClientId())
                .header("Request-Time", requestTime)
                .header("Signature", signatureHeader);

        // POST 请求携带 body
        if ("POST".equalsIgnoreCase(method)) {
            requestBuilder.method("POST", RequestBody.create(body, JSON_MEDIA_TYPE));
        } else {
            requestBuilder.method("GET", null);
        }

        // 4. 发送 HTTPS 请求并提取响应（OkHttp 的 Response 实现了 Closeable）
        try (Response response = httpClient.newCall(requestBuilder.build()).execute()) {
            int statusCode = response.code();
            String responseBody = response.body() != null ? response.body().string() : "";

            // 提取所有响应头（大小写不敏感）
            Map<String, String> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            for (String name : response.headers().names()) {
                headers.put(name, response.header(name));
            }

            // 5. 验签响应
            // 验签字符串格式与签名字符串相同，但使用 Response-Time 和响应体
            String responseSignature = headers.get("Signature");
            String responseTime = headers.get("Response-Time");
            String verifyString = WfSignatureUtil.buildSigningString(
                    method, endpoint, config.getClientId(), responseTime, responseBody);
            boolean signatureVerified = WfSignatureUtil.verifyResponse(
                    verifyString, config.getPublicKey(), responseSignature);

            if (!signatureVerified) {
                throw new WfException(WfErrorCode.INVALID_SIGNATURE,
                        "验签失败");
            }
            // 6. 反序列化响应体并返回封装响应
            T parsedResponse = JSON.parseObject(responseBody, responseClass);
            return new WfApiResponse<>(statusCode, responseBody, headers, parsedResponse);
        } catch (IOException e) {
            throw new WfException(WfErrorCode.HTTP_REQUEST_FAILED,
                    "HTTP 请求失败: " + e.getMessage(), e);
        }
    }

    /**
     * WF API 响应封装。
     * <p>
     * 包含 HTTP 状态码、响应体、全部响应头以及反序列化后的业务响应对象。
     *
     * @param <T> 业务响应类型
     */
    public static class WfApiResponse<T> {

        /** HTTP 状态码 */
        private final int statusCode;

        /** 响应体（JSON 字符串） */
        private final String body;

        /** 响应头（大小写不敏感） */
        private final Map<String, String> headers;

        /** 反序列化后的业务响应对象 */
        private final T serviceResponse;

        /**
         * 构造函数。
         *
         * @param statusCode        HTTP 状态码
         * @param body              响应体
         * @param headers           响应头
         * @param serviceResponse   反序列化后的业务响应对象
         */
        public WfApiResponse(int statusCode, String body, Map<String, String> headers,
                            T serviceResponse) {
            this.statusCode = statusCode;
            this.body = body;
            this.headers = headers;
            this.serviceResponse = serviceResponse;
        }

        /**
         * Getter method for property <tt>statusCode</tt>.
         *
         * @return HTTP 状态码
         */
        public int getStatusCode() {
            return statusCode;
        }

        /**
         * Getter method for property <tt>body</tt>.
         *
         * @return 响应体
         */
        public String getBody() {
            return body;
        }

        /**
         * Getter method for property <tt>headers</tt>.
         *
         * @return 响应头（大小写不敏感）
         */
        public Map<String, String> getHeaders() {
            return headers;
        }

        /**
         * 按名称获取响应头值（大小写不敏感）。
         *
         * @param name header 名称
         * @return header 值，不存在时返回 null
         */
        public String getHeader(String name) {
            return headers.get(name);
        }

        /**
         * Getter method for property <tt>serviceResponse</tt>.
         *
         * @return 反序列化后的业务响应对象
         */
        public T getServiceResponse() {
            return serviceResponse;
        }

        @Override
        public String toString() {
            String bodyPreview = body == null ? "null"
                : (body.length() > 100 ? body.substring(0, 100) + "...(truncated)" : body);
            return "WfApiResponse{statusCode=" + statusCode
                + ", headers=" + (headers == null ? 0 : headers.size())
                + ", bodyLength=" + (body == null ? 0 : body.length())
                + ", bodyPreview='" + bodyPreview + "'}";
        }
    }
}
