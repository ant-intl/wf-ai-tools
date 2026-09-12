package {basePackage}.wf.spring;

import {basePackage}.wf.client.WfApiClient;
import {basePackage}.wf.config.WfClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WorldFirst API Spring Boot 自动配置。
 * <p>
 * 参考实现：当客户项目为 Spring Boot 时，Agent 应参考此文件生成配置类，
 * 将 WfClientConfig 和 WfApiClient 注册为 Spring Bean，支持依赖注入。
 * </p>
 * <p>
 * 各业务模块的 Service（如 BalanceService、GlobalAccountService 等）应通过
 * {@code @Service} 注解注册为 Spring Bean，并通过构造器注入共享的 {@link WfApiClient}，
 * 例如：
 * <pre>
 * {@literal @}Service
 * public class BalanceService {
 *     private final WfApiClient apiClient;
 *
 *     public BalanceService(WfApiClient apiClient) {
 *         this.apiClient = apiClient;
 *     }
 * }
 * </pre>
 * </p>
 * <p>
 * 对应 application.yml 配置：
 * <pre>
 * wf:
 *   client-id: your_client_id
 *   private-key-path: /path/to/private_key.pem
 *   public-key-path: /path/to/public_key.pem
 *   base-url: https://open-sea.worldfirst.com
 * </pre>
 * </p>
 */
@Configuration
public class WfAutoConfiguration {

    @Bean
    public WfClientConfig wfClientConfig(
            @Value("${wf.client-id}") String clientId,
            @Value("${wf.private-key-path}") String privateKeyPath,
            @Value("${wf.public-key-path}") String publicKeyPath,
            @Value("${wf.base-url:https://open-sea.worldfirst.com}") String baseUrl) {
        return WfClientConfig.builder()
                .clientId(clientId)
                .privateKeyFromPath(privateKeyPath)
                .publicKeyFromPath(publicKeyPath)
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    public WfApiClient wfApiClient(WfClientConfig config) {
        return new WfApiClient(config);
    }
}
