# Query OTP Config 接口接入指引

## 接口说明

查询账户下卡片的 3DS 一次性密码（OTP）通知是否通过 API 投递，返回 `otpApiNotifyPreference`（`ON` 接收 / `OFF` 不接收）。配置作用于**账户维度**，不针对单张卡片。**本接口无请求参数**，请求体为 `{}`。

## 官方文档

- [query_otp_config 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/query_otp_config)
- 枚举取值（`OtpApiNotifyPreference`）见本模块 [README](../README.md)

## 请求地址

`POST /api/open/v1/issuing/cards/queryOtpConfig`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-08-08T08:00:00+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

无。请求体为 `{}`。

### 请求示例

```json
{}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `otpApiNotifyPreference` | string | 3DS OTP 通知是否通过 API 投递：`ON`（通过 API 接收）、`OFF`（不通过 API 接收） |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "otpApiNotifyPreference": "OFF"
}
```

## 错误码

| resultCode | resultStatus | 说明 | Troubleshooting |
|------------|--------------|------|-----------------|
| `SUCCESS` | S | 查询成功 | - |
| `USER_NOT_EXIST` | F | 用户不存在 | 使用正确的用户信息重试 |
| `BUDGET_NOT_FOUND` | F | 预算账户不存在 | 核对 `id` 是否正确 |
| `CONTRACT_CHECK_FAIL` | F | 合约校验失败 | 联系万里汇支持确认合约状态后重试 |
| `PARAM_ILLEGAL` | F | 参数非法 | 核对请求字段是否符合本规范 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权关系不存在 | 确认 `account-id` / `access-token` 授权关系有效后重试 |
| `PROCESS_FAIL` | F | 通用业务失败 | **不要重试**，联系万里汇支持 |
| `UNKNOWN_EXCEPTION` | U | 未知异常 | 稍后重试；若问题持续联系万里汇支持 |

## 示例代码

参考 [references/issuing/otp-management/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
otp-management/java/
├── service/
│   └── OtpManagementService.java                      # 薄封装 Service，包含 queryOtpConfig 方法
└── model/
    ├── request/
    │   └── QueryOtpConfigRequest.java                 # 无业务字段，序列化为 {}
    └── response/
        └── QueryOtpConfigResponse.java                # 响应结果（result, otpApiNotifyPreference）
```

> `Result` 为通用对象，复用 `model/response` 包下的已有定义。本模块无 domain 对象。

## 集成使用方式

OtpManagementService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造空的 `QueryOtpConfigRequest`（无需设置任何字段）
2. 调用 `OtpManagementService.queryOtpConfig(request)` 获取响应（内部已强制验签）
3. 检查 `result.resultStatus` 是否为 `S`，读取 `otpApiNotifyPreference`

### 业务代码示例

```java
QueryOtpConfigResponse response = otpManagementService.queryOtpConfig(new QueryOtpConfigRequest());

if ("S".equals(response.getResult().getResultStatus())) {
    if ("ON".equals(response.getOtpApiNotifyPreference())) {
        System.out.println("当前通过 API 接收 3DS OTP 通知");
    } else {
        System.out.println("当前不通过 API 接收 3DS OTP 通知");
    }
} else {
    System.err.println("查询失败: " + response.getResult().getResultCode()
        + " - " + response.getResult().getResultMessage());
}
```

### 典型用法：更新后确认生效值

```java
// update_otp_config 响应不回显配置值，需再查一次确认
UpdateOtpConfigRequest updateRequest = new UpdateOtpConfigRequest();
updateRequest.setOtpApiNotifyPreference("ON");
otpManagementService.updateOtpConfig(updateRequest);

QueryOtpConfigResponse confirm = otpManagementService.queryOtpConfig(new QueryOtpConfigRequest());
System.out.println("当前生效配置: " + confirm.getOtpApiNotifyPreference());
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 OtpManagementService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new OtpManagementService(config)` 创建实例。
