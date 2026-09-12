# OTP 配置管理（OTP Management）模块

本模块管理账户下卡片的 3DS 一次性密码（OTP）通知投递方式：查询与更新「是否通过 API 接收 OTP 通知」。配置作用于**账户维度**，对账户下全部卡片生效，不支持按单张卡片设置。

> 关闭 API 投递后，OTP 通知改由万里汇原有渠道（如邮件/短信）下发；开启后需保证自身接收端可用，否则可能影响 3DS 验证时效。

## 官方文档

- [query_otp_config](https://docs.worldfirst.com/wfdocs/api-sdk/query_otp_config) | [update_otp_config](https://docs.worldfirst.com/wfdocs/api-sdk/update_otp_config)

## 接口列表

| 接口 | 目录 | 说明 | Endpoint |
|------|------|------|----------|
| 查询 OTP 配置 | `query-otp-config/GUIDE.md` | 查询 3DS OTP 通知是否通过 API 投递（无请求参数） | `POST /api/open/v1/issuing/cards/queryOtpConfig` |
| 更新 OTP 配置 | `update-otp-config/GUIDE.md` | 开启或关闭通过 API 接收 3DS OTP 通知 | `POST /api/open/v1/issuing/cards/updateOtpConfig` |

> 本模块接口目录为 `issuing/otp-management`，但两个接口的 URL 前缀为 `/api/open/v1/issuing/cards/`，拼路径时以本表 Endpoint 为准。

## OtpManagementService 说明

`OtpManagementService` 是 OTP 配置管理模块的服务入口，采用**薄封装模式**：

- 不做参数校验（由调用方或 WF 服务端负责）
- 验签后自动反序列化响应体，直接返回业务响应对象（如 `QueryOtpConfigResponse`）
- 自动注入签名与验签（由底层 `WfApiClient` 完成，验签失败抛 `WfException`）

### 方法列表

| 方法 | 请求类型 | 响应类型 | 说明 |
|------|----------|----------|------|
| `queryOtpConfig(QueryOtpConfigRequest)` | `QueryOtpConfigRequest` | `QueryOtpConfigResponse` | 查询 OTP API 通知配置 |
| `updateOtpConfig(UpdateOtpConfigRequest)` | `UpdateOtpConfigRequest` | `UpdateOtpConfigResponse` | 更新 OTP API 通知配置 |

### 构造器

| 构造器 | 说明 |
|--------|------|
| `OtpManagementService(WfClientConfig config)` | 传入配置，内部创建 `WfApiClient` |
| `OtpManagementService(WfApiClient apiClient)` | 传入已构造的 `WfApiClient`，便于测试注入 |

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
OtpManagementService otpManagementService = new OtpManagementService(config);
```

### 3. 调用方法（内部已强制验签，验签失败抛 WfException）

```java
// 查询当前 OTP API 通知配置（无请求参数）
QueryOtpConfigResponse queryResponse = otpManagementService.queryOtpConfig(new QueryOtpConfigRequest());

// 开启通过 API 接收 3DS OTP 通知
UpdateOtpConfigRequest updateRequest = new UpdateOtpConfigRequest();
updateRequest.setOtpApiNotifyPreference("ON");

UpdateOtpConfigResponse updateResponse = otpManagementService.updateOtpConfig(updateRequest);
```

### 4. 检查业务结果

```java
if ("S".equals(queryResponse.getResult().getResultStatus())) {
    System.out.println("当前配置: " + queryResponse.getOtpApiNotifyPreference());   // ON / OFF
}

if ("S".equals(updateResponse.getResult().getResultStatus())) {
    System.out.println("配置更新成功");
    // 更新响应不回显配置值，需再查一次确认生效值
    System.out.println("复查生效值: "
        + otpManagementService.queryOtpConfig(new QueryOtpConfigRequest()).getOtpApiNotifyPreference());
}
```

## 注意事项

### query_otp_config
- **无请求参数**，请求体为 `{}`；`QueryOtpConfigRequest` 无业务字段，仅为统一调用形态与后续扩展保留
- 响应仅 `result` 与 `otpApiNotifyPreference` 两个字段，无分页、无列表
- 建议在应用启动或配置变更后主动查询一次，据返回值决定是否启用 OTP 通知接收端点

### update_otp_config
- `otpApiNotifyPreference` 必填，仅接受 `ON` / `OFF`，其他取值返回 `PARAM_ILLEGAL`
- 响应**只含 `result`**，不回显配置值；`resultStatus=S` 即表示更新成功，生效值以 `query_otp_config` 为准
- 本接口为配置覆盖式更新，重复提交相同取值无副作用，**不需要 `requestId`**
- 配置为账户维度全局生效，变更会影响账户下全部卡片的 3DS 验证链路，建议在变更窗口内执行并记录
- `PROCESS_FAIL` 不可重试，需联系万里汇支持；`UNKNOWN_EXCEPTION`（`resultStatus=U`）可稍后重试，重试后必须查询确认

## 枚举类型说明

### OtpApiNotifyPreference（OTP API 通知开关）

| 取值 | 说明 |
|------|------|
| `ON` | 通过 API 接收 3DS OTP 通知 |
| `OFF` | 不通过 API 接收 3DS OTP 通知 |

## 模块专用错误码

本模块未引入新错误码：`SUCCESS`、`USER_NOT_EXIST`、`CONTRACT_CHECK_FAIL`、`PARAM_ILLEGAL`、`AUTHORIZATION_NOT_EXIST`、`PROCESS_FAIL`、`UNKNOWN_EXCEPTION`、`BUDGET_NOT_FOUND` 均已在共享枚举 `{basePackage}.wf.model.exception.WfErrorCode` 的通用分节或既有模块分节中定义，无需追加。

## 文件结构

```
otp-management/
├── README.md
├── java/
│   ├── service/
│   │   └── OtpManagementService.java                  # OTP 配置管理服务（薄封装）
│   └── model/
│       ├── request/
│       │   ├── QueryOtpConfigRequest.java             # 查询 OTP 配置请求（无业务字段）
│       │   └── UpdateOtpConfigRequest.java            # 更新 OTP 配置请求（otpApiNotifyPreference）
│       └── response/
│           ├── QueryOtpConfigResponse.java            # 查询 OTP 配置响应（result, otpApiNotifyPreference）
│           └── UpdateOtpConfigResponse.java           # 更新 OTP 配置响应（仅 result）
├── query-otp-config/
│   └── GUIDE.md                                       # query_otp_config 接口接入指引
└── update-otp-config/
    └── GUIDE.md                                       # update_otp_config 接口接入指引
```

> **复用 domain 对象**：仅 `Result` 一个通用对象，位于 `{basePackage}.wf.model.response` 包（同包响应类无需 import）。本模块无独有 domain 对象。
>
> **依赖的其他模块**：无。接收端的 OTP 通知回调与卡片交易链路（`issuing` 发卡模块）配合使用。
