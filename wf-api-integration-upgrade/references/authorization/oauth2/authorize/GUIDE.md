# Authorize 接口接入指引

## 接口说明

发起 OAuth 授权流程，将用户重定向到 WorldFirst 授权页面。这是一个**浏览器端重定向**，而非标准 API 调用 — URL 中不传输客户端凭据。用户审核并批准权限后，WorldFirst 重定向回你的 `redirectUri`，携带授权码（`authCode`）。

> **[WARNING]** 此接口仅对已批准的合作伙伴开放。请联系我们获取审批，我们将为你的业务量身定制解决方案。

## 官方文档

- [authorize 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/authorize)

## 请求地址

`GET /api/open/v1/oauth/authorize`

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `clientId` | string | Yes | 应用的 Client ID。最大长度：64 字符 |
| `redirectUri` | string | Yes | 授权回调地址（HTTPS 格式）。必须与 OAuth 应用注册时填写的 URI 完全匹配。最大长度：2,048 字符 |
| `scope` | string | Yes | 请求的权限范围，空格分隔。格式：`resource:action`，如 `payouts:write accounts:read` |
| `state` | string | Yes | 防 CSRF 攻击的随机不可猜测字符串。建议最少 32 字符，最大 64 字符。此值将在重定向回调中原样返回用于验证 |
| `referenceAccountId` | string | Yes | 外部商户标识。同一个 WorldFirst 账户可以授权多个平台店铺。最大长度：64 字符 |

### 请求示例

```
https://{domain}/api/open/v1/oauth/authorize?clientId=3K5YHP442YBE9W0xxxx&redirectUri=https%3A%2F%2FredirectUri.com%2Fcallback&scope=payouts%3Awrite%20accounts%3Aread&state=a1b2c3d4e5f6&referenceAccountId=nc-test
```

## 响应参数

用户授权后，WorldFirst 将用户重定向到你的 `redirectUri`，URL 中携带以下参数：

| Field | Type | Description |
|-------|------|-------------|
| `authCode` | string | 授权码。有效期 10 分钟，单次使用 |
| `state` | string | 与原始请求中相同的 `state` 值，用于 CSRF 验证 |

### 响应示例

```
https://redirectUri.com/callback?authCode=SG_WDFTW3HK_2828881301697638178237324xxx&state=a1b2c3d4e5f6
```

## 错误码

此接口为浏览器重定向，不返回标准 JSON 错误码。授权失败时，WorldFirst 会在授权页面上直接展示错误信息。

## 示例代码

参考 [references/authorization/oauth2/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
oauth2/java/
├── service/
│   └── OAuth2Service.java                           # 薄封装 Service，包含 buildAuthorizationUrl 方法
└── model/
    └── request/
        └── AuthorizeRequest.java                    # 授权 URL 构造参数
```

## 集成使用方式

OAuth2Service 的 `buildAuthorizationUrl` 方法不发起 HTTP 调用，仅构造授权 URL 字符串。调用方需将用户浏览器重定向到该 URL。

### 调用流程

1. 构造 `AuthorizeRequest` 设置授权参数
2. 调用 `OAuth2Service.buildAuthorizationUrl(request)` 获取授权 URL
3. 将用户浏览器重定向到该 URL
4. 用户在 WorldFirst 页面审核并授权
5. WorldFirst 重定向回 `redirectUri`，携带 `authCode` 和 `state`
6. 验证 `state` 值是否与原始请求一致（防 CSRF）
7. 使用 `authCode` 调用 `requestToken` 换取访问令牌

### 业务代码示例

```java
// 1. 构造授权请求
AuthorizeRequest authRequest = new AuthorizeRequest();
authRequest.setClientId("YOUR_CLIENT_ID");
authRequest.setRedirectUri("https://your-app.com/callback");
authRequest.setScope("payouts:write accounts:read");
authRequest.setState(generateSecureRandomState()); // 至少 32 字符的随机字符串
authRequest.setReferenceAccountId("merchant-123");

// 2. 构造授权 URL
String authorizationUrl = oauth2Service.buildAuthorizationUrl(authRequest);

// 3. 将用户重定向到授权 URL（以 Spring MVC 为例）
// return "redirect:" + authorizationUrl;

// 4. 在回调接口中处理授权响应
// @GetMapping("/callback")
// public String callback(@RequestParam String authCode, @RequestParam String state) {
//     // 验证 state 防止 CSRF
//     if (!expectedState.equals(state)) {
//         throw new SecurityException("CSRF validation failed");
//     }
//     
//     // 使用 authCode 换取令牌
//     RequestTokenRequest tokenRequest = new RequestTokenRequest();
//     tokenRequest.setGrantType("AUTHORIZATION_CODE");
//     tokenRequest.setCode(authCode);
//     RequestTokenResponse tokenResponse = oauth2Service.requestToken(tokenRequest);
// }
```

### 生成安全的 state 参数示例

```java
import java.security.SecureRandom;
import java.util.Base64;

private static String generateSecureRandomState() {
    SecureRandom random = new SecureRandom();
    byte[] bytes = new byte[32]; // 256 bits
    random.nextBytes(bytes);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
}
```

## 注意事项

- **浏览器端重定向**：此接口不是标准 API 调用，URL 中不传输客户端凭据（如签名、Client-Id Header）
- **仅对已批准合作伙伴开放**：需联系 WorldFirst 获取审批
- **state 参数**：必须使用不可猜测的随机字符串，建议至少 32 字符，用于防止 CSRF 攻击
- **redirectUri 匹配**：必须与 OAuth 应用注册时填写的 URI 完全一致（包括协议、域名、路径）
- **授权码有效期**：10 分钟，且仅能使用一次，过期或使用后需重新发起授权流程
- **scope 编码**：URL 中空格应使用 `%20` 编码（而非 `+`），以确保可靠解析
- **referenceAccountId**：同一 WorldFirst 账户可通过不同的 `referenceAccountId` 授权多个平台店铺

> **Spring Boot 项目**：通过 `@Autowired` 注入 OAuth2Service，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new OAuth2Service(config)` 创建实例。
