# Revoke a Token 接口接入指引

## 接口说明

撤销已发放的访问令牌。撤销后令牌立即失效，无法再用于 API 调用。适用于用户断开账户连接或不再需要访问权限的场景。

## 官方文档

- [revoke_a_token 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/revoke_a_token)

## 请求地址

`POST /api/open/v1/oauth/revoke`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-06-24T07:13:29+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `token` | string | Yes | 要撤销的访问令牌。最大长度：512 字符 |

### 请求示例

```json
{
  "token": "SG_WDFTW3HK_xxxx****"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`）。参见 [Result](https://docs.worldfirst.com/wfdocs/api-sdk/result) |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultStatus": "S",
    "resultMessage": "success"
  }
}
```

## 错误码

| resultCode | resultStatus | 说明 |
|------------|--------------|------|
| `SUCCESS` | S | 撤销成功 |
| `PARAM_ILLEGAL` | F | 缺少必填参数或参数格式无效 |
| `PROCESS_FAIL` | F | 一般性业务失败，不可重试 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权不存在（令牌已被撤销或从未发放） |

## 示例代码

参考 [references/authorization/oauth2/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
oauth2/java/
├── service/
│   └── OAuth2Service.java                           # 薄封装 Service，包含 revokeToken 方法
└── model/
    ├── request/
    │   └── RevokeTokenRequest.java                  # 请求参数（token）
    └── response/
        └── RevokeTokenResponse.java                 # 响应结果（result）
```

## 集成使用方式

OAuth2Service 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `RevokeTokenRequest` 设置要撤销的访问令牌
2. 调用 `OAuth2Service.revokeToken(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，确认撤销成功
4. 清理本地存储的令牌信息

### 业务代码示例

```java
// 构造请求
RevokeTokenRequest request = new RevokeTokenRequest();
request.setToken("SG_WDFTW3HK_xxxx****");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
RevokeTokenResponse response = oauth2Service.revokeToken(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("令牌已成功撤销");
    
    // 清理本地存储的令牌
    tokenStore.delete(accountId);
} else {
    String resultCode = response.getResult().getResultCode();
    if ("AUTHORIZATION_NOT_EXIST".equals(resultCode)) {
        System.out.println("令牌不存在或已被撤销，清理本地存储");
        tokenStore.delete(accountId);
    } else {
        System.err.println("撤销失败: " + response.getResult().getResultMessage());
    }
}
```

### 用户断开连接完整流程示例

```java
/**
 * 用户断开 WorldFirst 账户连接。
 * 撤销令牌并清理本地数据。
 */
public void disconnectAccount(String accountId) {
    TokenRecord record = tokenStore.load(accountId);
    if (record == null) {
        System.out.println("账户未连接或令牌已清理");
        return;
    }
    
    // 撤销访问令牌
    RevokeTokenRequest revokeRequest = new RevokeTokenRequest();
    revokeRequest.setToken(record.getAccessToken());
    
    RevokeTokenResponse response = oauth2Service.revokeToken(revokeRequest);
    
    if ("S".equals(response.getResult().getResultStatus())) {
        System.out.println("令牌撤销成功");
    } else {
        // 即使撤销失败，也应清理本地存储
        System.err.println("撤销响应: " + response.getResult().getResultCode() 
            + " - " + response.getResult().getResultMessage());
    }
    
    // 无论撤销是否成功，都清理本地存储
    tokenStore.delete(accountId);
    System.out.println("本地令牌数据已清理");
}
```

## 注意事项

- 撤销后令牌**立即失效**，使用该令牌的所有后续 API 调用将失败
- 适用于用户主动断开账户连接、应用卸载或安全事件响应等场景
- 撤销不存在的令牌会返回 `AUTHORIZATION_NOT_EXIST` 错误，可视为幂等操作
- 撤销访问令牌后，关联的刷新令牌也将失效
- 建议在撤销后清理本地存储的令牌信息，避免误用已撤销的令牌
- 撤销操作不可逆，如需再次访问需重新发起授权流程

> **Spring Boot 项目**：通过 `@Autowired` 注入 OAuth2Service，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new OAuth2Service(config)` 创建实例。
