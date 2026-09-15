# Update OTP Config 接口接入指引

## 接口说明

开启或关闭通过 API 接收账户下卡片的 3DS 一次性密码（OTP）通知。配置作用于**账户维度**，对账户下全部卡片生效。响应仅返回调用结果，**不回显配置值**，需再次调用 `query_otp_config` 确认生效配置。

## 官方文档

- [update_otp_config 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/update_otp_config)
- 枚举取值（`OtpApiNotifyPreference`）见本模块 [README](../README.md)

## 请求地址

`POST /api/open/v1/issuing/cards/updateOtpConfig`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-08-08T08:00:00+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `otpApiNotifyPreference` | string | Yes | 是否通过 API 接收 3DS OTP 通知：`ON`（接收）、`OFF`（不接收）；其他取值返回 `PARAM_ILLEGAL` |

### 请求示例

```json
{
  "otpApiNotifyPreference": "ON"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`）。`resultStatus=S` 即表示配置已更新 |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  }
}
```

## 错误码

| resultCode | resultStatus | 说明 | Troubleshooting |
|------------|--------------|------|-----------------|
| `SUCCESS` | S | 配置更新成功 | 可调用 `query_otp_config` 确认生效值 |
| `USER_NOT_EXIST` | F | 用户不存在 | 使用正确的用户信息重试 |
| `CONTRACT_CHECK_FAIL` | F | 合约校验失败 | 联系万里汇支持确认合约状态后重试 |
| `PARAM_ILLEGAL` | F | 参数非法 | 核对 `otpApiNotifyPreference` 是否为 `ON` / `OFF` |
| `AUTHORIZATION_NOT_EXIST` | F | 授权关系不存在 | 确认 `account-id` / `access-token` 授权关系有效后重试 |
| `PROCESS_FAIL` | F | 通用业务失败 | **不要重试**，联系万里汇支持 |
| `UNKNOWN_EXCEPTION` | U | 未知异常 | 稍后重试；若问题持续联系万里汇支持 |

## 示例代码

参考 [references/issuing/otp-management/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
otp-management/java/
├── service/
│   └── OtpManagementService.java                      # 薄封装 Service，包含 updateOtpConfig 方法
└── model/
    ├── request/
    │   └── UpdateOtpConfigRequest.java                # 请求参数（otpApiNotifyPreference）
    └── response/
        └── UpdateOtpConfigResponse.java               # 响应结果（仅 result）
```

> `Result` 为通用对象，复用 `model/response` 包下的已有定义。本模块无 domain 对象。

## 集成使用方式

OtpManagementService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `UpdateOtpConfigRequest`，设置 `otpApiNotifyPreference`（仅 `ON` / `OFF`）
2. 调用 `OtpManagementService.updateOtpConfig(request)` 获取响应（内部已强制验签）
3. 检查 `result.resultStatus` 是否为 `S`；因响应不回显配置值，建议再调 `query_otp_config` 确认

### 业务代码示例

```java
UpdateOtpConfigRequest request = new UpdateOtpConfigRequest();
request.setOtpApiNotifyPreference("ON");           // 关闭改为 "OFF"

UpdateOtpConfigResponse response = otpManagementService.updateOtpConfig(request);

if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("配置更新成功");
} else if ("PARAM_ILLEGAL".equals(response.getResult().getResultCode())) {
    System.out.println("otpApiNotifyPreference 取值非法，仅支持 ON / OFF");
} else if ("PROCESS_FAIL".equals(response.getResult().getResultCode())) {
    System.out.println("业务失败，不要重试，联系万里汇支持");
} else {
    System.err.println("更新失败: " + response.getResult().getResultCode()
        + " - " + response.getResult().getResultMessage());
}
```

### 更新后确认生效配置

```java
UpdateOtpConfigRequest request = new UpdateOtpConfigRequest();
request.setOtpApiNotifyPreference("ON");
otpManagementService.updateOtpConfig(request);

// 响应不回显配置值：查询确认实际生效值，避免误判
QueryOtpConfigResponse confirm = otpManagementService.queryOtpConfig(new QueryOtpConfigRequest());
if ("S".equals(confirm.getResult().getResultStatus())) {
    System.out.println("当前生效配置: " + confirm.getOtpApiNotifyPreference());
}
```

> **幂等性**：本接口为配置覆盖式更新，重复提交相同 `otpApiNotifyPreference` 不产生副作用，无需 `requestId`。
>
> **Spring Boot 项目**：通过 `@Autowired` 注入 OtpManagementService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new OtpManagementService(config)` 创建实例。
