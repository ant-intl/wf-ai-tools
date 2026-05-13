package {basePackage}.wf.util;

import com.alibaba.common.logging.Logger;
import com.alibaba.common.logging.LoggerFactory;
import {basePackage}.wf.config.WfConfig;
import {basePackage}.wf.model.exception.WfErrorCode;
import {basePackage}.wf.model.exception.WfException;
import {basePackage}.wf.signer.WfSigner;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * WorldFirst API HTTP 客户端工具类
 */
public class WfHttpClientUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(WfHttpClientUtil.class);

    private final WfConfig config;
    private final WfSigner signer;
    private final CloseableHttpClient httpClient;

    /**
     * 构造函数，初始化 HTTP 客户端和签名工具
     *
     * @param config WF 配置
     */
    public WfHttpClientUtil(WfConfig config) {
        this.config = config;
        this.signer = new WfSigner(config);

        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(config.getConnectTimeout())
            .setSocketTimeout(config.getReadTimeout())
            .setConnectionRequestTimeout(config.getConnectTimeout())
            .build();

        this.httpClient = HttpClients.custom()
            .setDefaultRequestConfig(requestConfig)
            .build();
    }

    /**
     * 构造 HTTP 工具，允许外部注入 {@link WfSigner}。
     * 适用于测试场景需要 mock 签名器的情况。
     *
     * @param config WF 配置，不得为 null
     * @param signer 签名器，不得为 null
     */
    public WfHttpClientUtil(WfConfig config, WfSigner signer) {
        if (config == null) {
            throw new IllegalArgumentException("config must not be null");
        }
        if (signer == null) {
            throw new IllegalArgumentException("signer must not be null");
        }
        this.config = config;
        this.signer = signer;

        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(config.getConnectTimeout())
            .setSocketTimeout(config.getReadTimeout())
            .setConnectionRequestTimeout(config.getConnectTimeout())
            .build();

        this.httpClient = HttpClients.custom()
            .setDefaultRequestConfig(requestConfig)
            .build();
    }

    /**
     * 发送已签名的 POST 请求并返回响应体
     *
     * @param url         完整请求 URL
     * @param apiPath     API 路径（用于生成签名）
     * @param requestBody 请求体 JSON 字符串
     * @return 响应体字符串
     * @throws WfException 请求失败时抛出
     */
    public String sendPostRequest(String url, String apiPath, String requestBody) throws WfException {
        // 直接用 Java 代码生成当前时间，ISO 8601，Asia/Shanghai 时区
        String requestTime = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"))
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));
        String signature = signer.generateSignature(apiPath, requestTime, requestBody);

        if (signature == null) {
            throw new WfException(WfErrorCode.SIGNATURE_GENERATION_FAILED, "Failed to generate signature for path: " + apiPath);
        }

        LOGGER.info("WfHttpClientUtil sending POST request, url=" + url + ", requestTime=" + requestTime);
        LOGGER.debug("WfHttpClientUtil request body: " + requestBody);

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
        httpPost.setHeader("Client-Id", config.getClientId());
        httpPost.setHeader("Request-Time", requestTime);
        httpPost.setHeader("Signature", "algorithm=RSA256, keyVersion=2, signature=" + signature);
        httpPost.setEntity(new StringEntity(requestBody, StandardCharsets.UTF_8));

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            return handleResponse(response, apiPath);
        } catch (WfException e) {
            throw e;
        } catch (IOException e) {
            LOGGER.error("WfHttpClientUtil HTTP request failed, url=" + url, e);
            throw new WfException(WfErrorCode.HTTP_REQUEST_FAILED, e.getMessage(), e);
        }
    }

    /**
     * 关闭底层 HTTP 客户端（幂等，忽略异常）
     */
    public void close() {
        try {
            httpClient.close();
        } catch (IOException e) {
            LOGGER.warn("WfHttpClientUtil failed to close HTTP client", e);
        }
    }

    /**
     * Getter method for property <tt>config</tt>.
     *
     * @return property value of config
     */
    public WfConfig getConfig() {
        return config;
    }

    /**
     * Getter method for property <tt>signer</tt>.
     *
     * @return property value of signer
     */
    public WfSigner getSigner() {
        return signer;
    }

    /**
     * 处理 HTTP 响应，并对响应进行验签
     *
     * @param response    HTTP 响应对象
     * @param apiPath     API 路径（用于验签）
     * @return 响应体字符串
     * @throws WfException HTTP 状态码非 200 或验签失败时抛出
     * @throws IOException 读取响应体失败时抛出
     */
    private String handleResponse(CloseableHttpResponse response,
                                  String apiPath) throws WfException, IOException {
        int statusCode = response.getStatusLine().getStatusCode();
        String body = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        LOGGER.info("WfHttpClientUtil received response, statusCode=" + statusCode);
        LOGGER.debug("WfHttpClientUtil response body: " + body);

        if (statusCode != 200) {
            throw new WfException(WfErrorCode.HTTP_REQUEST_FAILED,
                    "HTTP status " + statusCode + ", body: " + body);
        }

        // 验证 WF 响应签名
        Header signatureHeader = response.getFirstHeader("Signature");
        Header responseTime = response.getFirstHeader("response-time");
        if (signatureHeader != null && signatureHeader.getValue() != null) {
            boolean valid = signer.verifySignature(
                signatureHeader.getValue(), apiPath, responseTime.getValue(), body);
            if (!valid) {
                LOGGER.error("WfHttpClientUtil response signature verification failed, apiPath=" + apiPath);
                throw new WfException(WfErrorCode.INVALID_SIGNATURE,
                    "Response signature verification failed");
            }
            LOGGER.debug("WfHttpClientUtil response signature verified, apiPath=" + apiPath);
        } else {
            LOGGER.warn("WfHttpClientUtil no Signature header in response, apiPath=" + apiPath);
        }

        return body;
    }
}
