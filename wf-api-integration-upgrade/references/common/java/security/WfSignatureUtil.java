package {basePackage}.wf.security;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * WorldFirst API 签名与验签工具类。
 * <p>
 * 签名算法：RSA256（RSA + SHA-256），使用 JDK 原生 API 实现，不依赖第三方加密库。
 * <p>
 * 签名流程：
 * <ol>
 *   <li>构建签名字符串："{HTTP-Method} {Request-URL-Endpoint}\n{Client-Id}.{Request-Time}.{Request-Body}"</li>
 *   <li>使用私钥进行 SHA256withRSA 签名</li>
 *   <li>对签名结果进行 Base64 编码</li>
 *   <li>对 Base64 结果进行 URL 编码（UTF-8）</li>
 * </ol>
 * <p>
 * 验签流程：
 * <ol>
 *   <li>从 Signature header 中提取 signature 值</li>
 *   <li>URL 解码 → Base64 解码 → 得到原始签名字节</li>
 *   <li>使用 WorldFirst 公钥进行 SHA256withRSA 验签</li>
 * </ol>
 */
public class WfSignatureUtil {

    /** 签名算法标识，用于 Signature header */
    private static final String ALGORITHM = "RSA256";

    /** JDK 签名算法名称 */
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    /** 私有构造函数，禁止实例化 */
    private WfSignatureUtil() {
        // 工具类禁止实例化
    }

    // ======================== 签名 ========================

    /**
     * 构建签名字符串。
     * <p>
     * 格式："{method} {endpoint}\n{clientId}.{requestTime}.{body}"
     *
     * @param method      HTTP 方法，如 "POST"、"GET"
     * @param endpoint    请求路径，如 "/api/open/v1/payouts/create"
     * @param clientId    客户端 ID
     * @param requestTime ISO 8601 格式时间戳，如 "2026-06-08T10:00:00+08:00"
     * @param body        请求体原始 JSON 字符串（GET 请求时为 null）
     * @return 签名字符串
     */
    public static String buildSigningString(String method, String endpoint,
                                            String clientId, String requestTime, String body) {
        String safeBody = body == null ? "" : body;
        String safeTime = requestTime == null ? "" : requestTime;
        return method + " " + endpoint + "\n"
                + clientId + "." + safeTime + "." + safeBody;
    }

    /**
     * 构建完整的 Signature header 值。
     * <p>
     * 内部完成：签名 → Base64 编码 → URL 编码，返回格式为：
     * "algorithm=RSA256, keyVersion={keyVersion}, signature={encodedSignature}"
     *
     * @param signingString 签名字符串
     * @param privateKey    RSA 私钥
     * @param keyVersion    密钥版本号，当前固定为 2
     * @return 完整的 Signature header 值
     */
    public static String buildSignatureHeader(String signingString, PrivateKey privateKey, int keyVersion) {
        String encodedSignature = signAndEncode(signingString, privateKey);
        return "algorithm=" + ALGORITHM + ", keyVersion=" + keyVersion + ", signature=" + encodedSignature;
    }

    /**
     * 对签名字符串进行签名并完成 Base64 + URL 编码。
     *
     * @param signingString 签名字符串
     * @param privateKey    RSA 私钥
     * @return URL 编码后的 Base64 签名
     */
    private static String signAndEncode(String signingString, PrivateKey privateKey) {
        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(privateKey);
            signature.update(signingString.getBytes(StandardCharsets.UTF_8));
            byte[] signedBytes = signature.sign();
            String base64Signature = Base64.getEncoder().encodeToString(signedBytes);
            return URLEncoder.encode(base64Signature, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("签名失败", e);
        }
    }

    // ======================== 验签 ========================

    /**
     * 从 Signature header 中提取 signature 参数值。
     * <p>
     * 按逗号分割 header 内容，查找以 "signature=" 开头的部分。
     *
     * @param signatureHeader 完整的 Signature header，如 "algorithm=RSA256, keyVersion=2, signature=xxx"
     * @return signature 参数值（URL 编码状态）
     * @throws IllegalArgumentException 当 header 为空或未找到 signature 参数时
     */
    public static String extractSignature(String signatureHeader) {
        if (signatureHeader == null || signatureHeader.isEmpty()) {
            throw new IllegalArgumentException("Signature header 为空");
        }
        for (String part : signatureHeader.split(",")) {
            String trimmed = part.trim();
            if (trimmed.startsWith("signature=")) {
                return trimmed.substring("signature=".length()).trim();
            }
        }
        throw new IllegalArgumentException("Signature header 中未找到 signature 参数");
    }

    /**
     * 验证 WorldFirst 响应签名。
     * <p>
     * 流程：从 Signature header 提取签名 → URL 解码 → Base64 解码 → SHA256withRSA 验签。
     *
     * @param verifyString    验签字符串（与签名字符串格式相同，但使用响应时间和响应体）
     * @param publicKey       WorldFirst 公钥
     * @param signatureHeader 完整的 Signature header
     * @return true 表示验签通过；当 signatureHeader 为空时返回 false
     * @throws RuntimeException 当验签过程中发生异常时
     */
    public static boolean verifyResponse(String verifyString, PublicKey publicKey, String signatureHeader) {
        if (signatureHeader == null || signatureHeader.isEmpty()) {
            return false;
        }
        try {
            String encodedSignature = extractSignature(signatureHeader);
            String urlDecoded = URLDecoder.decode(encodedSignature, "UTF-8");
            byte[] signatureBytes = Base64.getDecoder().decode(urlDecoded);

            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(verifyString.getBytes(StandardCharsets.UTF_8));
            return signature.verify(signatureBytes);
        } catch (Exception e) {
            throw new RuntimeException("验签失败", e);
        }
    }

    // ======================== 密钥加载 ========================

    /**
     * 从 PEM 内容加载 RSA 私钥。
     * <p>
     * 支持 PKCS#8 格式（-----BEGIN PRIVATE KEY-----），自动去除 PEM 头尾行和空白字符。
     *
     * @param pemContent PEM 文件内容字符串
     * @return RSA 私钥
     * @throws RuntimeException 当密钥加载失败时
     */
    public static PrivateKey loadPrivateKey(String pemContent) {
        try {
            String base64 = pemContent
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                    .replace("-----END RSA PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] keyBytes = Base64.getDecoder().decode(base64);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("加载私钥失败", e);
        }
    }

    /**
     * 从 PEM 文件路径加载 RSA 私钥。
     *
     * @param pemFilePath PEM 文件路径
     * @return RSA 私钥
     * @throws RuntimeException 当文件读取或密钥加载失败时
     */
    public static PrivateKey loadPrivateKeyFromFile(String pemFilePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(pemFilePath)), StandardCharsets.UTF_8);
            return loadPrivateKey(content);
        } catch (Exception e) {
            throw new RuntimeException("从文件加载私钥失败: " + pemFilePath, e);
        }
    }

    /**
     * 从 PEM 内容加载 RSA 公钥。
     * <p>
     * 支持 X.509 格式（-----BEGIN PUBLIC KEY-----），自动去除 PEM 头尾行和空白字符。
     *
     * @param pemContent PEM 文件内容字符串
     * @return RSA 公钥
     * @throws RuntimeException 当密钥加载失败时
     */
    public static PublicKey loadPublicKey(String pemContent) {
        try {
            String base64 = pemContent
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] keyBytes = Base64.getDecoder().decode(base64);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("加载公钥失败", e);
        }
    }

    /**
     * 从 PEM 文件路径加载 RSA 公钥。
     *
     * @param pemFilePath PEM 文件路径
     * @return RSA 公钥
     * @throws RuntimeException 当文件读取或密钥加载失败时
     */
    public static PublicKey loadPublicKeyFromFile(String pemFilePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(pemFilePath)), StandardCharsets.UTF_8);
            return loadPublicKey(content);
        } catch (Exception e) {
            throw new RuntimeException("从文件加载公钥失败: " + pemFilePath, e);
        }
    }
}
