# 公共组件（Common）

本模块为所有 WF API 集成客户端提供共享基础设施层，包含配置管理、RSA256 签名验签、HTTP 通信、错误处理、通用响应模型和公共数据类型（Amount、Address、UserName）。

> **重要说明**：本目录下的所有 Java 模板代码均使用 `{basePackage}` 作为包名占位符。Agent 在实际集成时会将 `{basePackage}` 替换为客户项目的真实包名（如 `com.example.company`），并根据客户环境（Spring Boot / 普通 Java / Lombok / fastjson / Jackson 等）进行适配。模板代码为参考实现，Agent 可根据客户项目的代码风格、框架约定和依赖情况进行调整。

## 组件职责

本模块共包含 9 个 Java 组件：

| # | 组件 | 包路径 | 职责 |
|---|------|--------|------|
| 1 | `WfClientConfig` | `{basePackage}.wf.config` | 客户端配置（Builder 模式不可变对象），持有 clientId、密钥、baseUrl、超时等 |
| 2 | `WfSignatureUtil` | `{basePackage}.wf.security` | RSA256 签名与验签工具类，纯静态方法，密钥加载 |
| 3 | `WfApiClient` | `{basePackage}.wf.client` | HTTP 客户端，封装请求签名注入与响应验签，基于 OkHttp 4.x（OkHttpClient） |
| 4 | `WfErrorCode` | `{basePackage}.wf.model.exception` | 错误码枚举，含 code 字符串和 retryable 标记 |
| 5 | `WfException` | `{basePackage}.wf.model.exception` | 统一业务异常，携带 WfErrorCode |
| 6 | `Result` | `{basePackage}.wf.model.response` | 通用响应结果对象（resultStatus / resultCode / resultMessage） |
| 7 | `Amount` | `{basePackage}.wf.model.domain` | 通用金额对象（currency + value），以最小货币单位表示 |
| 8 | `Address` | `{basePackage}.wf.model.domain` | 通用地址对象（region / state / city / address1 / address2 / zipCode） |
| 9 | `UserName` | `{basePackage}.wf.model.domain` | 通用姓名对象（firstName / middleName / lastName / fullName） |

## 包结构

```
common/
└── java/
    ├── config/
    │   └── WfClientConfig.java          # 客户端配置（Builder 模式）
    ├── security/
    │   └── WfSignatureUtil.java         # 签名与验签工具类
    ├── client/
    │   └── WfApiClient.java             # HTTP 客户端 + WfApiResponse 内部类
    ├── model/
    │   ├── domain/
    │   │   ├── Amount.java              # 通用金额对象
    │   │   ├── Address.java             # 通用地址对象
    │   │   └── UserName.java            # 通用姓名对象
    │   ├── exception/
    │   │   ├── WfErrorCode.java         # 错误码枚举
    │   │   └── WfException.java         # 业务异常
    │   └── response/
    │       └── Result.java              # 通用响应结果
    └── spring/
        └── WfAutoConfiguration.java    # Spring Boot 自动配置参考
```

## 使用示例

### 1. 构建配置

```java
WfClientConfig config = WfClientConfig.builder()
    .clientId("YOUR_CLIENT_ID")
    .privateKeyFromPath("/path/to/private_key.pem")
    .publicKeyFromPath("/path/to/wf_public_key.pem")
    .baseUrl("https://YOUR_BASE_URL")
    .keyVersion(2)
    .timeoutMillis(15000)
    .build();
```

### 2. 创建客户端并发送请求

```java
WfApiClient client = new WfApiClient(config);

// POST 请求（需提供业务响应类型，验签失败抛 WfException）
String requestBody = "{\"param\":\"value\"}";
WfApiResponse<MyResponse> response = client.post("/api/open/v1/payouts/create", requestBody, MyResponse.class);

// GET 请求
WfApiResponse<MyResponse> getResponse = client.get("/api/open/v1/accounts/inquiry", MyResponse.class);

// 获取反序列化后的业务响应对象（验签已在内部完成）
MyResponse result = response.getServiceResponse();
System.out.println("状态码: " + response.getStatusCode());
```

### 3. 业务服务集成模式

各业务模块（如 Payout、Transfer、Beneficiary、Account 等）基于公共组件构建：

```java
// WfClientConfig.builder()...build() → new WfApiClient(config) → 业务 Service
WfClientConfig config = WfClientConfig.builder()
    .clientId("YOUR_CLIENT_ID")
    .privateKeyFromPath("/path/to/private_key.pem")
    .publicKeyFromPath("/path/to/wf_public_key.pem")
    .build();

WfApiClient client = new WfApiClient(config);

// 业务 Service 内部使用 client 发送请求并解析响应
// 例：BalanceService、PayoutService、TransferService 等
```

### 4. 错误处理

```java
try {
    WfApiResponse<MyResponse> response = client.post("/api/open/v1/payouts/create", body, MyResponse.class);
    MyResponse result = response.getServiceResponse();
    // 处理响应...
} catch (WfException e) {
    WfErrorCode errorCode = e.getErrorCode();
    if (e.isRetryable()) {
        // 可重试错误（如 UNKNOWN_EXCEPTION、REQUEST_TRAFFIC_EXCEED_LIMIT、HTTP_REQUEST_FAILED）
        // 执行重试逻辑
    } else {
        // 不可重试错误，记录日志并返回
        System.err.println("错误码: " + errorCode.getCode() + ", 消息: " + e.getMessage());
    }
}
```

## 公共数据类型

参考 [WF API Data Types 文档](https://docs.worldfirst.com/wfdocs/api-sdk/data_types)，本模块提供以下跨模块复用的公共数据结构：

### Amount

通用金额对象，值以最小货币单位（minor units）存储以避免浮点精度问题。

| 字段 | 类型 | 说明 |
|------|------|------|
| `currency` | String | ISO 4217 三字母货币代码（使用 CNH 表示离岸人民币），如 "USD" |
| `value` | Integer | 最小货币单位的整数金额。如 10.00 USD → value = 1000；1000 JPY → value = 1000 |

```java
Amount amount = new Amount("USD", 10000);  // 100.00 USD
```

### Address

通用地址对象，所有字段均为可选。各业务模块（Payout、Beneficiary、Account 等）均可复用。

| 字段 | 类型 | 说明 |
|------|------|------|
| `region` | String | ISO 3166-1 两字母国家/地区代码，如 "HK" |
| `state` | String | 州/省，如 "California" |
| `city` | String | 城市，如 "Hong Kong" |
| `address1` | String | 地址行 1，如 "Central, Hong Kong" |
| `address2` | String | 地址行 2，如 "Suite 1234" |
| `zipCode` | String | 邮政编码，如 "000000" |

### UserName

通用姓名对象，支持拆分姓名组件和组合全名两种方式。各业务模块均可复用。

| 字段 | 类型 | 说明 |
|------|------|------|
| `firstName` | String | 名（given name），如 "San" |
| `middleName` | String | 中间名，如 "Ming" |
| `lastName` | String | 姓（family name），如 "Zhang" |
| `fullName` | String | 全名，如 "Zhang San" |

### 字段格式约定

以下基础字段在各 API 中统一使用：

| 字段 | 类型 | 格式 | 示例 |
|------|------|------|------|
| `region` | String | ISO 3166-1 两字母国家/地区代码 | "HK" |
| `currency` | String | ISO 4217 三字母货币代码 | "USD" |
| `date` | String | `YYYY-MM-DD` | "2025-06-01" |
| `timestamp` | String | `YYYY-MM-DDTHH:mm:ssZ`（ISO 8601） | "2025-06-01T10:30:00Z" |

## 签名算法说明

### 签名流程

1. **构建签名字符串**：
   ```
   {HTTP-Method} {Request-URL-Endpoint}\n{Client-Id}.{Request-Time}.{Request-Body}
   ```
   示例：
   ```
   POST /api/open/v1/payouts/create
   YOUR_CLIENT_ID.2026-07-07T10:00:00+08:00.{"param":"value"}
   ```

2. **签名**：使用 RSA 私钥进行 `SHA256withRSA` 签名

3. **编码**：签名结果 → Base64 编码 → URL 编码（UTF-8）

4. **设置请求头**：
   ```
   Signature: algorithm=RSA256, keyVersion=2, signature={urlEncodedBase64}
   ```

### 验签流程

1. 从响应 `Signature` header 提取 `signature=` 参数值
2. URL 解码 → Base64 解码 → 得到原始签名字节
3. 构建验签字符串（格式同签名字符串，使用 `Response-Time` 和响应体）
4. 使用 WorldFirst 公钥进行 `SHA256withRSA` 验签

### Request-Time 格式

ISO 8601 格式：`2026-07-07T10:00:00+08:00`（由 `ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)` 生成）

### 密钥格式

- **私钥**：PKCS#8 格式（`-----BEGIN PRIVATE KEY-----`）
- **公钥**：X.509 格式（`-----BEGIN PUBLIC KEY-----`）
- 加载时自动去除 PEM 头尾标记和空白字符

## 依赖说明

| 依赖 | 用途 | 说明 |
|------|------|------|
| JDK 8+ | Signature、KeyFactory | 核心运行时 |
| `com.squareup.okhttp3:okhttp` | HTTP 客户端（OkHttp 4.x） | WfApiClient 底层 HTTP 通信，连接池复用、JDK 8 兼容 |
| `com.alibaba.fastjson2` | JSON 序列化/反序列化 | 业务层使用 |
| `org.apache.commons.lang3` | ToStringBuilder | toString 实现 |

> Agent 集成时可根据客户项目实际情况替换 JSON 库（如 Jackson）和 toString 方案。

## 安全注意事项

1. **私钥保护**：私钥文件路径不应硬编码在代码中，建议通过环境变量或配置中心注入。`WfClientConfig.toString()` 已做脱敏处理，密钥字段显示为 `[PROTECTED]`，但生产环境仍应避免日志中打印完整配置对象。

2. **验签强制执行**：`WfApiClient` 在返回响应前已强制验签，验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果。

3. **密钥版本管理**：`keyVersion` 当前固定为 2，如 WorldFirst 更新密钥版本需同步调整。

4. **超时设置**：默认超时 15000ms（15秒），生产环境建议根据业务场景调整。`WfApiClient` 的连接超时和请求超时均取自 `config.getTimeoutMillis()`。

5. **异常处理**：`WfException` 携带 `WfErrorCode`，通过 `isRetryable()` 判断是否可重试。可重试错误码包括 `UNKNOWN_EXCEPTION`、`REQUEST_TRAFFIC_EXCEED_LIMIT`、`HTTP_REQUEST_FAILED`。

6. **Base URL 环境**：生产环境 `https://open-sea.worldfirst.com`，测试/SIT 环境请替换为对应沙箱地址。

## Agent 集成适配指南

这些参考代码使用 `{basePackage}` 占位符，Agent 在实际集成时会：

1. **替换包名**：将所有 `{basePackage}` 替换为客户项目的真实包名
2. **适配框架**：根据客户是否使用 Spring Boot，决定是否将 `WfClientConfig` 改造为 `@ConfigurationProperties` Bean，是否将 `WfApiClient` 注册为 Spring Bean
3. **适配代码风格**：根据客户项目是否使用 Lombok，决定是否用 `@Data` / `@Builder` 注解替代手写 getter/setter/Builder
4. **适配 JSON 库**：根据客户项目依赖，选择 fastjson2 或 Jackson 进行 JSON 序列化
5. **适配日志方案**：参考代码不包含日志输出，Agent 可根据客户项目使用的日志框架（SLF4J / Logback / Log4j2）在 Service 和 Client 中添加关键操作日志
