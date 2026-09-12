# 确认函（Confirmation Letters）模块

## 官方文档

- [WorldFirst 开发者文档 - Confirmation Letters Overview](https://docs.worldfirst.com/wfdocs/api-sdk/confirmation_letters_overview)
- [Create a Confirmation Letter](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_confirmation_letter) | [Query a Confirmation Letter](https://docs.worldfirst.com/wfdocs/api-sdk/query_a_confirmation_letter)

## 接口列表

| 接口 | 目录 | 说明 | Endpoint |
|------|------|------|----------|
| 创建确认函 | `create-confirmation-letter/GUIDE.md` | 创建确认函下载任务（异步处理） | `POST /api/open/v1/confirmationLetters/create` |
| 查询确认函 | `query-confirmation-letter/GUIDE.md` | 查询任务状态，获取预签名下载链接 | `POST /api/open/v1/confirmationLetters/query` |

## ConfirmationLetterService 说明

`ConfirmationLetterService` 是确认函模块的服务入口，采用**薄封装模式**：

- 不做参数校验（由调用方或 WF 服务端负责）
- 验签后自动反序列化响应体，直接返回业务响应对象（如 `CreateConfirmationLetterResponse`、`QueryConfirmationLetterResponse`）
- 自动注入签名与验签（由底层 `WfApiClient` 完成，验签失败抛 `WfException`）

### 方法列表

| 方法 | 请求类型 | 响应类型 | 说明 |
|------|----------|----------|------|
| `createConfirmationLetter(CreateConfirmationLetterRequest)` | `CreateConfirmationLetterRequest` | `CreateConfirmationLetterResponse` | 创建确认函下载任务，返回任务 ID |
| `queryConfirmationLetter(QueryConfirmationLetterRequest)` | `QueryConfirmationLetterRequest` | `QueryConfirmationLetterResponse` | 查询任务状态及下载链接 |

### 构造器

| 构造器 | 说明 |
|--------|------|
| `ConfirmationLetterService(WfClientConfig config)` | 传入配置，内部创建 `WfApiClient` |
| `ConfirmationLetterService(WfApiClient apiClient)` | 传入已构造的 `WfApiClient`，便于测试注入 |

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
ConfirmationLetterService confirmationLetterService = new ConfirmationLetterService(config);
```

### 3. 创建确认函下载任务（内部已强制验签，验签失败抛 WfException）

```java
// 构造 STATEMENT_DETAIL_LETTER 请求
StatementDetailLetterParams letterParams = new StatementDetailLetterParams();
letterParams.setStatementId("STM202604230001");
letterParams.setElectronicallySigned(true);
letterParams.setLanguage("EN_GB");

CreateConfirmationLetterRequest request = new CreateConfirmationLetterRequest();
request.setRequestId(UUID.randomUUID().toString());
request.setConfirmationLetterType("STATEMENT_DETAIL_LETTER");
request.setStatementDetailLetterParams(letterParams);

CreateConfirmationLetterResponse response = confirmationLetterService.createConfirmationLetter(request);

if ("S".equals(response.getResult().getResultStatus())) {
    String taskId = response.getId();
    System.out.println("任务创建成功，ID: " + taskId);
    System.out.println("任务状态: " + response.getStatus());
}
```

### 4. 查询任务状态并下载（内部已强制验签，验签失败抛 WfException）

```java
QueryConfirmationLetterRequest queryRequest = new QueryConfirmationLetterRequest();
queryRequest.setId("f18d0117-095a-4782-a9f1-eeb16c3b****");

QueryConfirmationLetterResponse queryResponse = confirmationLetterService.queryConfirmationLetter(queryRequest);

if ("S".equals(queryResponse.getResult().getResultStatus())) {
    String status = queryResponse.getStatus();
    if ("SUCCESS".equals(status)) {
        System.out.println("下载链接: " + queryResponse.getDownloadUrl());
        System.out.println("链接过期时间: " + queryResponse.getDownloadUrlExpiresAt());
    } else if ("PROCESSING".equals(status)) {
        System.out.println("任务处理中，请稍后重试");
    } else if ("FAIL".equals(status)) {
        System.err.println("任务失败: " + queryResponse.getFailureCode()
            + " - " + queryResponse.getFailureMessage());
    }
}
```

## 注意事项

### create_a_confirmation_letter
- `requestId` 为幂等键，每次请求应使用唯一值（如 UUID），避免因重试导致重复创建任务
- `confirmationLetterType` 支持两种类型：
  - `STATEMENT_DETAIL_LETTER`：基于对账单记录的交易证明函，需提供 `statementDetailLetterParams`
  - `ACCOUNT_VERIFICATION_LETTER`：全球账户验证信息，需提供 `accountVerificationLetterParams`
- `statementDetailLetterParams.electronicallySigned` 默认为 `false`（无电子签名）
- `statementDetailLetterParams.language` 默认为 `EN_GB`，最大 10 字符
- 任务创建后状态为 `PROCESSING`，需通过查询接口轮询获取结果

### query_a_confirmation_letter
- `id` 为创建任务时返回的唯一任务标识符
- 任务状态 `SUCCESS` 时返回 `downloadUrl` 和 `downloadUrlExpiresAt`
- 任务状态 `FAIL` 时返回 `failureCode` 和 `failureMessage`
- 下载链接为预签名 URL，请在过期前完成下载

## 枚举类型说明

### ConfirmationLetterType（确认函类型）
- `STATEMENT_DETAIL_LETTER`: 基于对账单记录的交易证明函，需提供 `statementDetailLetterParams`
- `ACCOUNT_VERIFICATION_LETTER`: 全球账户验证信息，需提供 `accountVerificationLetterParams`

### TaskStatus（任务状态）
- `PROCESSING`: 任务处理中，由 `create` 和 `query` 接口返回
- `SUCCESS`: 任务成功完成，可获取 `downloadUrl`，由 `query` 接口和 `confirmationLetters.SUCCESS` webhook 返回
- `FAIL`: 任务失败，查看 `failureCode` 和 `failureMessage` 了解详情，由 `query` 接口和 `confirmationLetters.FAIL` webhook 返回

## 文件结构

```
confirmation-letters/
├── README.md
├── java/
│   ├── service/
│   │   └── ConfirmationLetterService.java                    # 确认函服务（薄封装）
│   └── model/
│       ├── request/
│       │   ├── CreateConfirmationLetterRequest.java          # 创建确认函请求
│       │   ├── QueryConfirmationLetterRequest.java           # 查询确认函请求
│       │   ├── StatementDetailLetterParams.java              # 交易证明函参数
│       │   └── AccountVerificationLetterParams.java          # 账户验证函参数
│       └── response/
│           ├── CreateConfirmationLetterResponse.java         # 创建确认函响应
│           └── QueryConfirmationLetterResponse.java          # 查询确认函响应
├── create-confirmation-letter/
│   └── GUIDE.md                                              # create 接口接入指引
└── query-confirmation-letter/
    └── GUIDE.md                                              # query 接口接入指引
```

> **复用 domain 对象**：`Result` 等通用对象与其他模块共享，引用 `{basePackage}.wf.model.domain` 包下的已有定义。本模块无独有 domain 对象。
