/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.ipay.iissuecard.common.service.integration.wf.signer;

import com.alibaba.common.logging.Logger;
import com.alibaba.common.logging.LoggerFactory;
import com.ipay.iissuecard.common.service.integration.wf.config.WfConfig;

import java.io.IOException;
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * WorldFirst API RSA256 签名工具类
 *
 * @author Qoder
 * @version WfSigner.java, v 0.1 2026-03-24
 */
public class WfSigner {

    private static final Logger LOGGER = LoggerFactory.getLogger(WfSigner.class);

    private static final DateTimeFormatter TIMESTAMP_FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

    private final WfConfig config;

    /**
     * 构造函数
     *
     * @param config WF 配置
     */
    public WfSigner(WfConfig config) {
        this.config = config;
    }

    /**
     * 生成请求签名
     *
     * @param apiPath     API 路径，例如 /amsin/api/v1/business/account/inquiryBalance
     * @param requestTime 请求时间，ISO 8601 格式
     * @param requestBody 请求体 JSON 字符串
     * @return Base64 + URL 编码的签名字符串，失败时返回 null
     */
    public String generateSignature(String apiPath, String requestTime, String requestBody) {
        try {
            String signContent = "POST " + apiPath + "\n"
                + config.getClientId() + "." + requestTime + "." + requestBody;
            LOGGER.debug("WfSigner sign content: " + signContent);
            PrivateKey privateKey = loadPrivateKey();
            byte[] signatureBytes = signWithPrivateKey(signContent, privateKey);
            String base64Signature = Base64.getEncoder().encodeToString(signatureBytes);
            // URL encode 避免签名中的 + / = 等字符在 HTTP 头传输时被误解析
            return URLEncoder.encode(base64Signature, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            LOGGER.error("WfSigner failed to generate signature, apiPath=" + apiPath, e);
            return null;
        }
    }

    /**
     * 验证 WF 响应签名
     *
     * @param signatureHeader 响应头中的 Signature 值
     * @param apiPath         API 路径
     * @param requestTime     原始请求时间（与发送请求时一致）
     * @param responseBody    响应体 JSON 字符串
     * @return 签名有效返回 true，否则返回 false
     */
    public boolean verifySignature(String signatureHeader, String apiPath,
                                   String requestTime, String responseBody) {
        try {
            String base64Sig = null;
            for (String part : signatureHeader.split(",")) {
                String trimmed = part.trim();
                if (trimmed.startsWith("signature=")) {
                    base64Sig = trimmed.substring("signature=".length()).trim();
                    break;
                }
            }
            if (base64Sig == null) {
                LOGGER.warn("WfSigner no signature value found in header: " + signatureHeader);
                return false;
            }

            String signContent = "POST " + apiPath + "\n"
                + config.getClientId() + "." + requestTime + "." + responseBody;

            PublicKey publicKey = loadPublicKey();
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initVerify(publicKey);
            sig.update(signContent.getBytes(StandardCharsets.UTF_8));

            // URL decode 还原签名中被编码的 + / = 等字符，再 Base64 解码
            String decodedSig = URLDecoder.decode(base64Sig, StandardCharsets.UTF_8.name());
            return sig.verify(Base64.getDecoder().decode(decodedSig));
        } catch (Exception e) {
            LOGGER.warn("WfSigner response signature verification failed, apiPath=" + apiPath, e);
            return false;
        }
    }

    /**
     * 获取当前时间戳（ISO 8601，Asia/Shanghai 时区）
     *
     * @return 格式如 2024-01-15T10:30:00+08:00
     */
    public String getCurrentTimestamp() {
        return ZonedDateTime.now(ZoneId.of("Asia/Shanghai")).format(TIMESTAMP_FORMATTER);
    }

    /**
     * 加载 PKCS#8 私钥
     *
     * @return PrivateKey 对象
     * @throws Exception 加载失败时抛出
     */
    private PrivateKey loadPrivateKey() throws Exception {
        String keyContent = loadKeyFromFile(config.getPrivateKeyPath());
        byte[] keyBytes = Base64.getDecoder().decode(keyContent);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
    }

    /**
     * 加载 X.509 公钥
     *
     * @return PublicKey 对象
     * @throws Exception 加载失败时抛出
     */
    private PublicKey loadPublicKey() throws Exception {
        String keyContent = loadKeyFromFile(config.getPublicKeyPath());
        byte[] keyBytes = Base64.getDecoder().decode(keyContent);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePublic(keySpec);
    }

    /**
     * 使用私钥对内容进行 SHA256withRSA 签名
     *
     * @param content    待签名内容
     * @param privateKey 私钥
     * @return 签名字节数组
     * @throws Exception 签名失败时抛出
     */
    private byte[] signWithPrivateKey(String content, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(content.getBytes(StandardCharsets.UTF_8));
        return signature.sign();
    }

    /**
     * 从文件读取密钥内容，去除 PEM 头尾行
     *
     * @param path 密钥文件路径
     * @return 纯 Base64 密钥字符串
     * @throws IOException 读取失败时抛出
     */
    private String loadKeyFromFile(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (String line : Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8)) {
            if (!line.startsWith("-----")) {
                sb.append(line.trim());
            }
        }
        return sb.toString();
    }
}
