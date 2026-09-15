# 授权（OAuth2）模块

## 官方文档

- [WorldFirst 开发者文档 - OAuth2 Overview](https://docs.worldfirst.com/wfdocs/api-sdk/oauth2_overview)
- [authorize](https://docs.worldfirst.com/wfdocs/api-sdk/authorize) | [request_a_token](https://docs.worldfirst.com/wfdocs/api-sdk/request_a_token) | [revoke_a_token](https://docs.worldfirst.com/wfdocs/api-sdk/revoke_a_token)

## 接口列表

| 接口 | 目录 | 说明 | Endpoint |
|------|------|------|----------|
| 授权 | `authorize/GUIDE.md` | 发起 OAuth 授权流程，将用户重定向到 WorldFirst 授权页面（浏览器端重定向，非标准 API 调用） | `GET /api/open/v1/oauth/authorize` |
| 请求令牌 | `request-token/GUIDE.md` | 使用授权码或刷新令牌换取访问令牌 | `POST /api/open/v1/oauth/token` |
| 撤销令牌 | `revoke-token/GUIDE.md` | 撤销已发放的访问令牌 | `POST /api/open/v1/oauth/revoke` |

## OAuth2Service 说明

`OAuth2Service` 是 OAuth2 授权模块的服务入口，采用**薄封装模式**：

- 不做参数校验（由调用方或 WF 服务端负责）
- 验签后自动反序列化响应体，直接返回业务响应对象（如 `RequestTokenResponse`）
- 自动注入签名与验签（由底层 `WfApiClient` 完成，验签失败抛 `WfException`）
- **注意**：`authorize` 接口为浏览器端重定向，不通过 `OAuth2Service` 调用，需在前端/浏览器中构造 URL 并重定向用户

### 方法列表

| 方法 | 请求类型 | 响应类型 | 说明 |
|------|----------|----------|------|
| `buildAuthorizationUrl(AuthorizeRequest)` | `AuthorizeRequest` | `String` | 构造授权 URL，供浏览器重定向使用 |
| `requestToken(RequestTokenRequest)` | `RequestTokenRequest` | `RequestTokenResponse` | 使用授权码或刷新令牌换取访问令牌 |
| `revokeToken(RevokeTokenRequest)` | `RevokeTokenRequest` | `RevokeTokenResponse` | 撤销访问令牌 |

### 构造器

| 构造器 | 说明 |
|--------|------|
| `OAuth2Service(WfClientConfig config)` | 传入配置，内部创建 `WfApiClient` |
| `OAuth2Service(WfApiClient apiClient)` | 传入已构造的 `WfApiClient`，便于测试注入 |

## 授权流程

1. **重定向**用户到 WorldFirst 授权页面（使用 `buildAuthorizationUrl` 构造 URL）
2. 用户审核并批准权限后，WorldFirst 重定向回 `redirectUri`，携带 `authCode`（授权码，10 分钟有效，单次使用）
3. **换取令牌**：使用 `requestToken` 将授权码换取访问令牌（`accessToken`）
4. **使用令牌**：在后续 API 调用中携带访问令牌
5. **刷新令牌**（可选）：在访问令牌过期前，使用刷新令牌获取新的访问令牌
6. **撤销令牌**：不再需要时，使用 `revokeToken` 撤销访问令牌

## 对接流程

### 1. 配置

```java
WfClientConfig config = WfClientConfig.builder()
    .clientId("YOUR_CLIENT_ID")
    .privateKeyFromPath("/path/to/private_key.pem")
    .publicKeyFromPath("/path/to/wf_public_key.pem")
    .baseUrl("https://YOUR_BASE_URL")
    .build();
```

### 2. 创建 Service

```java
OAuth2Service oauth2Service = new OAuth2Service(config);
```

### 3. 构造授权 URL 并重定向用户

```java
AuthorizeRequest authRequest = new AuthorizeRequest();
authRequest.setClientId("YOUR_CLIENT_ID");
authRequest.setRedirectUri("https://your-app.com/callback");
authRequest.setScope("payouts:write accounts:read");
authRequest.setState("a1b2c3d4e5f6");
authRequest.setReferenceAccountId("merchant-123");

String authorizationUrl = oauth2Service.buildAuthorizationUrl(authRequest);
// 将用户浏览器重定向到 authorizationUrl
```

### 4. 使用授权码换取令牌（内部已强制验签，验签失败抛 WfException）

```java
RequestTokenRequest tokenRequest = new RequestTokenRequest();
tokenRequest.setGrantType("AUTHORIZATION_CODE");
tokenRequest.setCode("SG_WDFTW3HK_zzzz****");

RequestTokenResponse tokenResponse = oauth2Service.requestToken(tokenRequest);

if ("S".equals(tokenResponse.getResult().getResultStatus())) {
    String accessToken = tokenResponse.getAccessToken();
    String refreshToken = tokenResponse.getRefreshToken();
    String expiresAt = tokenResponse.getExpiresAt();
}
```

### 5. 刷新令牌

```java
RequestTokenRequest refreshRequest = new RequestTokenRequest();
refreshRequest.setGrantType("REFRESH_TOKEN");
refreshRequest.setRefreshToken("SG_WDFTW3HK_yyyy****");

RequestTokenResponse refreshResponse = oauth2Service.requestToken(refreshRequest);
```

### 6. 撤销令牌

```java
RevokeTokenRequest revokeRequest = new RevokeTokenRequest();
revokeRequest.setToken("SG_WDFTW3HK_xxxx****");

RevokeTokenResponse revokeResponse = oauth2Service.revokeToken(revokeRequest);
```

## Scopes（权限范围）

权限范围遵循 `resource:action` 格式，多个权限以空格分隔。支持的权限：

| Scope | 说明 |
|-------|------|
| `globalAccounts:read` | 读取全球账户信息 |
| `globalAccounts:write` | 创建和管理全球账户 |
| `balances:read` | 读取账户余额 |
| `stores:read` | 读取店铺信息 |
| `payouts:read` | 读取付款信息 |
| `payouts:write` | 创建和管理付款 |
| `beneficiaries:read` | 读取收款人信息 |
| `beneficiaries:write` | 创建和管理收款人 |
| `fx:read` | 读取外汇信息 |
| `fx:write` | 创建和管理外汇交易 |
| `accounts:read` | 读取关联账户信息 |
| `statements:read` | 读取对账单信息 |
| `reports:read` | 读取报告信息 |
| `reports:write` | 创建和管理报告 |
| `files:read` | 读取已上传文件 |
| `files:write` | 上传和管理文件 |
| `tradeOrders:read` | 读取贸易订单信息 |
| `tradeOrders:write` | 提交和管理贸易订单 |
| `letters:read` | 读取确认函 |
| `letters:write` | 创建确认函 |
| `deposits:read` | 读取存款信息 |
| `deposits:write` | 创建和管理存款 |

## 注意事项

### authorize
- 此接口为**浏览器端重定向**，不是标准 API 调用，URL 中不传输客户端凭据
- 仅对已批准的合作伙伴开放，需联系 WorldFirst 获取审批
- `state` 参数用于防止 CSRF 攻击，建议使用至少 32 字符的随机不可猜测字符串
- `redirectUri` 必须与 OAuth 应用注册时填写的 URI 完全匹配
- 授权码（`authCode`）有效期 10 分钟，且仅能使用一次

### request_a_token
- `grantType` 为 `AUTHORIZATION_CODE` 时，必须提供 `code`
- `grantType` 为 `REFRESH_TOKEN` 时，必须提供 `refreshToken`
- 授权码有效期 10 分钟，单次使用
- 刷新令牌在有效期内可重复使用
- `expiresAt` 和 `refreshTokenExpiresAt` 均为 ISO 8601 格式

### revoke_a_token
- 撤销后令牌立即失效，无法再用于 API 调用
- 适用于用户断开账户连接或不再需要访问权限的场景
- 撤销不存在的令牌会返回 `AUTHORIZATION_NOT_EXIST` 错误

## 文件结构

```
oauth2/
├── README.md
├── java/
│   ├── service/
│   │   └── OAuth2Service.java                       # OAuth2 授权服务（薄封装）
│   └── model/
│       ├── request/
│       │   ├── AuthorizeRequest.java                # 授权 URL 构造参数
│       │   ├── RequestTokenRequest.java             # 请求令牌参数
│       │   └── RevokeTokenRequest.java              # 撤销令牌参数
│       └── response/
│           ├── RequestTokenResponse.java            # 请求令牌响应
│           └── RevokeTokenResponse.java             # 撤销令牌响应
├── authorize/
│   └── GUIDE.md                                     # authorize 接口接入指引
├── request-token/
│   └── GUIDE.md                                     # request_a_token 接口接入指引
└── revoke-token/
    └── GUIDE.md                                     # revoke_a_token 接口接入指引
```
