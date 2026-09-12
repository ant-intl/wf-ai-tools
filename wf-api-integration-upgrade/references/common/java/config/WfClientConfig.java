package {basePackage}.wf.config;

import {basePackage}.wf.security.WfSignatureUtil;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * WorldFirst API 客户端配置（不可变对象）。
 * <p>
 * 使用 Builder 模式构建，所有字段均为 final。密钥加载委托给
 * {@link WfSignatureUtil} 完成，支持从 PEM 内容字符串或文件路径加载。
 *
 * <pre>{@code
 * WfClientConfig config = WfClientConfig.builder()
 *     .clientId("YOUR_CLIENT_ID")
 *     .privateKeyFromPath("/path/to/private_key.pem")
 *     .publicKeyFromPath("/path/to/wf_public_key.pem")
 *     .baseUrl("https://YOUR_BASE_URL")
 *     .keyVersion(2)
 *     .timeoutMillis(15000)
 *     .build();
 * }</pre>
 */
public class WfClientConfig {

    /** 客户端 ID */
    private final String clientId;

    /** 集成商私钥（用于签名请求） */
    private final PrivateKey privateKey;

    /** WorldFirst 公钥（用于验证响应签名） */
    private final PublicKey publicKey;

    /** API 基础 URL */
    private final String baseUrl;

    /** 密钥版本号，当前固定为 2 */
    private final int keyVersion;

    /** HTTP 超时时间（毫秒） */
    private final int timeoutMillis;

    /**
     * 私有构造函数，仅通过 Builder 创建实例。
     *
     * @param builder 配置构建器
     */
    private WfClientConfig(Builder builder) {
        this.clientId = builder.clientId;
        this.privateKey = builder.privateKey;
        this.publicKey = builder.publicKey;
        this.baseUrl = builder.baseUrl;
        this.keyVersion = builder.keyVersion;
        this.timeoutMillis = builder.timeoutMillis;
    }

    /**
     * 创建 Builder 实例。
     *
     * @return 新的 Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Getter method for property <tt>clientId</tt>.
     *
     * @return 客户端 ID
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Getter method for property <tt>privateKey</tt>.
     *
     * @return 集成商私钥
     */
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    /**
     * Getter method for property <tt>publicKey</tt>.
     *
     * @return WorldFirst 公钥
     */
    public PublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * Getter method for property <tt>baseUrl</tt>.
     *
     * @return API 基础 URL
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Getter method for property <tt>keyVersion</tt>.
     *
     * @return 密钥版本号
     */
    public int getKeyVersion() {
        return keyVersion;
    }

    /**
     * Getter method for property <tt>timeoutMillis</tt>.
     *
     * @return HTTP 超时毫秒数
     */
    public int getTimeoutMillis() {
        return timeoutMillis;
    }

    @Override
    public String toString() {
        return "WfClientConfig{clientId='" + clientId + "', baseUrl='" + baseUrl 
            + "', keyVersion=" + keyVersion + ", timeoutMillis=" + timeoutMillis 
            + ", privateKey=[PROTECTED], publicKey=[PROTECTED]}";
    }

    /**
     * WfClientConfig 构建器。
     * <p>
     * 支持链式调用，通过 {@link #build()} 方法创建不可变的 WfClientConfig 实例。
     */
    public static class Builder {

        /** 客户端 ID */
        private String clientId;

        /** 集成商私钥 */
        private PrivateKey privateKey;

        /** WorldFirst 公钥 */
        private PublicKey publicKey;

        /** API 基础 URL，默认生产环境 */
        private String baseUrl = "https://open-sea.worldfirst.com";

        /** 密钥版本号，默认 2 */
        private int keyVersion = 2;

        /** HTTP 超时毫秒，默认 15000 */
        private int timeoutMillis = 15000;

        /**
         * 默认构造函数。
         */
        public Builder() {
        }

        /**
         * 设置客户端 ID。
         *
         * @param clientId 客户端 ID
         * @return Builder 实例
         */
        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        /**
         * 设置私钥（从 PEM 内容字符串加载）。
         *
         * @param pemContent 私钥 PEM 内容
         * @return Builder 实例
         */
        public Builder privateKey(String pemContent) {
            this.privateKey = WfSignatureUtil.loadPrivateKey(pemContent);
            return this;
        }

        /**
         * 设置私钥（从 PrivateKey 对象）。
         *
         * @param privateKey RSA 私钥
         * @return Builder 实例
         */
        public Builder privateKey(PrivateKey privateKey) {
            this.privateKey = privateKey;
            return this;
        }

        /**
         * 设置私钥（从 PEM 文件路径加载）。
         *
         * @param path 私钥文件路径
         * @return Builder 实例
         */
        public Builder privateKeyFromPath(String path) {
            this.privateKey = WfSignatureUtil.loadPrivateKeyFromFile(path);
            return this;
        }

        /**
         * 设置公钥（从 PEM 内容字符串加载）。
         *
         * @param pemContent 公钥 PEM 内容
         * @return Builder 实例
         */
        public Builder publicKey(String pemContent) {
            this.publicKey = WfSignatureUtil.loadPublicKey(pemContent);
            return this;
        }

        /**
         * 设置公钥（从 PublicKey 对象）。
         *
         * @param publicKey RSA 公钥
         * @return Builder 实例
         */
        public Builder publicKey(PublicKey publicKey) {
            this.publicKey = publicKey;
            return this;
        }

        /**
         * 设置公钥（从 PEM 文件路径加载）。
         *
         * @param path 公钥文件路径
         * @return Builder 实例
         */
        public Builder publicKeyFromPath(String path) {
            this.publicKey = WfSignatureUtil.loadPublicKeyFromFile(path);
            return this;
        }

        /**
         * 设置 API 基础 URL。
         *
         * @param baseUrl API 基础 URL
         * @return Builder 实例
         */
        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * 设置密钥版本号（当前固定为 2）。
         *
         * @param keyVersion 密钥版本号
         * @return Builder 实例
         */
        public Builder keyVersion(int keyVersion) {
            this.keyVersion = keyVersion;
            return this;
        }

        /**
         * 设置 HTTP 超时毫秒数。
         *
         * @param timeoutMillis 超时毫秒数
         * @return Builder 实例
         */
        public Builder timeoutMillis(int timeoutMillis) {
            this.timeoutMillis = timeoutMillis;
            return this;
        }

        /**
         * 构建 WfClientConfig 实例。
         * <p>
         * 校验 clientId、privateKey、publicKey、baseUrl 非空。
         *
         * @return WfClientConfig 实例
         * @throws IllegalArgumentException 当必填字段为空时
         */
        public WfClientConfig build() {
            if (clientId == null || clientId.trim().isEmpty()) {
                throw new IllegalArgumentException("clientId 不能为空");
            }
            if (privateKey == null) {
                throw new IllegalArgumentException("privateKey 不能为空");
            }
            if (publicKey == null) {
                throw new IllegalArgumentException("publicKey 不能为空");
            }
            if (baseUrl == null || baseUrl.trim().isEmpty()) {
                throw new IllegalArgumentException("baseUrl 不能为空");
            }
            if (!baseUrl.startsWith("https://")) {
                throw new IllegalArgumentException("baseUrl 必须使用 HTTPS 协议，当前: " + baseUrl);
            }
            return new WfClientConfig(this);
        }
    }
}
