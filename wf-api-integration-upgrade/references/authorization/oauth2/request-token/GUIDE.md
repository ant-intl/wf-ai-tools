# Request a Token 接口接入指引

## 接口说明

使用授权码或刷新令牌换取访问令牌。在 [Authorize](../authorize/GUIDE.md) 步骤获取授权码后，使用此接口换取访问令牌；也可在访问令牌过期前使用刷新令牌获取新的访问令牌。

## 官方文档

- [request_a_token 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/request_a_token)

## 请求地址

`POST /api/open/v1/oauth/token`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-06-24T07:13:29+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description                                                                                  |
|-------|------|----------|----------------------------------------------------------------------------------------------|
| `grantType` | string | Yes | 授权类型。支持值：`AUTHORIZATION_CODE`（使用授权码换取令牌）、`REFRESH_TOKEN`（刷新现有访问令牌）                           |
| `code` | string | Conditional | 授权码，从 authorize 重定向回调中获取。当 `grantType` 为 `AUTHORIZATION_CODE` 时必填。最大长度：256 字符。有效期 10 分钟，单次使用 |
| `refreshToken` | string | Conditional | 刷新令牌。当 `grantType` 为 `REFRESH_TOKEN` 时必填。最大长度：256 字符。在有效期内可重复使用                              |

### 请求示例（使用授权码）

```json
{
  "grantType": "AUTHORIZATION_CODE",
  "code": "SG_WDFTW3HK_zzzz****"
}
```

### 请求示例（使用刷新令牌）

```json
{
  "grantType": "REFRESH_TOKEN",
  "refreshToken": "SG_WDFTW3HK_yyyy****"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`）。参见 [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result) |
| `accessToken` | string | 访问令牌，用于后续 API 调用的身份认证 |
| `expiresAt` | string | 访问令牌过期时间。**ISO 8601** 格式（如 `2026-06-24T07:13:29Z`） |
| `refreshToken` | string | 刷新令牌，用于获取新的访问令牌（支持刷新时返回） |
| `refreshTokenExpiresAt` | string | 刷新令牌过期时间（支持刷新时返回）。**ISO 8601** 格式 |
| `accountId` | string | 令牌有权访问的 WorldFirst 账户 ID。最大长度：64 字符 |
| `scope` | string | 已授权的权限范围，空格分隔。参见 [Scopes](https://docs.worldfirst.com/wfdocs/api-sdk/oauth2_overview) |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultStatus": "S",
    "resultMessage": "success"
  },
  "accessToken": "SG_WDFTW3HK_xxxx****",
  "expiresAt": "2026-06-24T07:13:29Z",
  "refreshToken": "SG_WDFTW3HK_yyyy****",
  "refreshTokenExpiresAt": "2026-07-24T07:13:29Z",
  "accountId": "acct_50045123",
  "scope": "payouts:write accounts:read"
}
```

## 错误码

| resultCode | resultStatus | 说明 |
|------------|--------------|------|
| `SUCCESS` | S | 令牌发放成功 |
| `PARAM_ILLEGAL` | F | 缺少必填参数或参数格式无效 |
| `PROCESS_FAIL` | F | 一般性业务失败，不可重试 |
| `AUTH_CODE_NOT_EXIST` | F | 授权码不存在 |
| `AUTH_CODE_EXPIRED` | F | 授权码已过期 |

## 示例代码

参考 [references/authorization/oauth2/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
oauth2/java/
├── service/
│   └── OAuth2Service.java                           # 薄封装 Service，包含 requestToken 方法
└── model/
    ├── request/
    │   └── RequestTokenRequest.java                 # 请求参数（grantType, code, refreshToken）
    └── response/
        └── RequestTokenResponse.java                # 响应结果（result, accessToken, expiresAt, refreshToken, ...）
```

## 集成使用方式

OAuth2Service 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程（授权码换令牌）

1. 从 authorize 回调中获取 `authCode`，并验证 `state`
2. 构造 `RequestTokenRequest`，设置 `grantType` 为 `AUTHORIZATION_CODE`，设置 `code`
3. 调用 `OAuth2Service.requestToken(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
4. 检查 `result.resultStatus` 是否为 `S`，保存 `accessToken` 和 `refreshToken`

### 业务代码示例（授权码换令牌）

```java
// 构造请求
RequestTokenRequest request = new RequestTokenRequest();
request.setGrantType("AUTHORIZATION_CODE");
request.setCode("SG_WDFTW3HK_zzzz****");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
RequestTokenResponse response = oauth2Service.requestToken(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    String accessToken = response.getAccessToken();
    String refreshToken = response.getRefreshToken();
    String expiresAt = response.getExpiresAt();
    String accountId = response.getAccountId();
    String scope = response.getScope();
    
    System.out.println("访问令牌: " + accessToken);
    System.out.println("过期时间: " + expiresAt);
    System.out.println("账户 ID: " + accountId);
    System.out.println("权限范围: " + scope);
    
    // 安全存储令牌（建议使用加密存储）
    tokenStore.save(accountId, accessToken, refreshToken, expiresAt);
} else {
    System.err.println("获取令牌失败: " + response.getResult().getResultMessage());
}
```

### 业务代码示例（刷新令牌）

```java
// 构造刷新请求
RequestTokenRequest refreshRequest = new RequestTokenRequest();
refreshRequest.setGrantType("REFRESH_TOKEN");
refreshRequest.setRefreshToken("SG_WDFTW3HK_yyyy****");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
RequestTokenResponse response = oauth2Service.requestToken(refreshRequest);

if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("新访问令牌: " + response.getAccessToken());
    System.out.println("新过期时间: " + response.getExpiresAt());
    
    // 更新存储的令牌
    tokenStore.update(response.getAccessToken(), response.getExpiresAt());
} else {
    System.err.println("刷新令牌失败: " + response.getResult().getResultMessage());
}
```

### 令牌自动刷新示例

```java
/**
 * 获取有效的访问令牌，过期时自动刷新。
 */
public String getValidAccessToken(String accountId) {
    TokenRecord record = tokenStore.load(accountId);
    
    // 检查访问令牌是否即将过期（提前 5 分钟）
    if (isExpiringSoon(record.getExpiresAt(), 5)) {
        // 使用刷新令牌获取新的访问令牌
        RequestTokenRequest refreshRequest = new RequestTokenRequest();
        refreshRequest.setGrantType("REFRESH_TOKEN");
        refreshRequest.setRefreshToken(record.getRefreshToken());
        
        RequestTokenResponse response = oauth2Service.requestToken(refreshRequest);
        if ("S".equals(response.getResult().getResultStatus())) {
            tokenStore.update(accountId, response.getAccessToken(), response.getExpiresAt());
            return response.getAccessToken();
        } else {
            throw new RuntimeException("令牌刷新失败: " + response.getResult().getResultMessage());
        }
    }
    
    return record.getAccessToken();
}
```

## 注意事项

- `grantType` 为 `AUTHORIZATION_CODE` 时，必须提供 `code`
- `grantType` 为 `REFRESH_TOKEN` 时，必须提供 `refreshToken`
- 授权码有效期 10 分钟，且仅能使用一次
- 刷新令牌在有效期内可重复使用
- `expiresAt` 和 `refreshTokenExpiresAt` 均为 ISO 8601 格式
- 建议在访问令牌过期前提前刷新（如提前 5 分钟），避免 API 调用因令牌过期而失败
- 令牌应安全存储（加密），避免泄露

> **Spring Boot 项目**：通过 `@Autowired` 注入 OAuth2Service，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new OAuth2Service(config)` 创建实例。
