# Create a Confirmation Letter 接口接入指引

## 接口说明

创建确认函下载任务。任务异步处理 —— 创建后通过 Query a Confirmation Letter 轮询获取结果，任务完成后使用预签名 URL 下载 PDF。

## 官方文档

- [Create a Confirmation Letter 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_confirmation_letter)

## 请求地址

`POST /api/open/v1/confirmationLetters/create`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-04-27T12:08:56+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `requestId` | string | Yes | 幂等键，每次请求使用唯一值（如 UUID），避免因重试导致重复创建任务。最大 64 字符 |
| `confirmationLetterType` | string | Yes | 确认函类型：`STATEMENT_DETAIL_LETTER`（交易证明函）或 `ACCOUNT_VERIFICATION_LETTER`（账户验证函） |
| `statementDetailLetterParams` | StatementDetailLetterParams | Conditional | 当 `confirmationLetterType` 为 `STATEMENT_DETAIL_LETTER` 时必填 |
| `accountVerificationLetterParams` | AccountVerificationLetterParams | Conditional | 当 `confirmationLetterType` 为 `ACCOUNT_VERIFICATION_LETTER` 时必填 |

### StatementDetailLetterParams Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `statementId` | string | Yes | 对账单 ID，最大 64 字符 |
| `electronicallySigned` | boolean | No | 是否包含电子签名，省略时默认 `false` |
| `language` | string | No | 生成确认函的语言，省略时默认 `EN_GB`，最大 10 字符 |

### AccountVerificationLetterParams Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `globalAccountId` | string | Yes | 全球账户 ID，最大 64 字符 |

### 请求示例（STATEMENT_DETAIL_LETTER）

```json
{
  "requestId": "f18d0117-095a-4782-a9f1-eeb16c3b****",
  "confirmationLetterType": "STATEMENT_DETAIL_LETTER",
  "statementDetailLetterParams": {
    "statementId": "STM202604230001",
    "electronicallySigned": true,
    "language": "EN_GB"
  }
}
```

### 请求示例（ACCOUNT_VERIFICATION_LETTER）

```json
{
  "requestId": "a29e0228-106b-5893-b0g2-ffc27d4c****",
  "confirmationLetterType": "ACCOUNT_VERIFICATION_LETTER",
  "accountVerificationLetterParams": {
    "globalAccountId": "GA202604010001"
  }
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `id` | string | 下载任务的唯一标识符，用于后续查询任务状态 |
| `status` | string | 任务状态，新创建的任务始终为 `PROCESSING` |
| `createdAt` | datetime | 任务创建时间，ISO 8601 扩展格式 |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "id": "f18d0117-095a-4782-a9f1-eeb16c3b****",
  "status": "PROCESSING",
  "createdAt": "2026-04-27T12:08:56+08:00"
}
```

## 错误码

| resultCode | resultStatus | resultMessage | Troubleshooting |
|------------|--------------|---------------|-----------------|
| `SUCCESS` | S | success | — |
| `PARAM_ILLEGAL` | F | Illegal parameter. Refer to the API documentation to verify the request header and parameters, then retry. | 检查 `confirmationLetterType` 是否使用合法值；`STATEMENT_DETAIL_LETTER` 类型需提供 `statementDetailLetterParams.statementId`；`ACCOUNT_VERIFICATION_LETTER` 类型需提供 `accountVerificationLetterParams.globalAccountId` |
| `REPEAT_REQ_INCONSISTENT` | F | A request with the same requestId already exists but with different parameters. Use a new requestId or the same parameters. | `requestId` 已被使用但参数不同，请生成新的唯一 `requestId` |
| `ORDER_NOT_EXIST` | F | The specified statement does not exist. Verify the statementId and retry. | `statementDetailLetterParams.statementId` 不存在，请通过 List Statements 核实 |
| `ACCOUNT_NOT_EXIST` | F | The specified global account does not exist. | `accountVerificationLetterParams.globalAccountId` 不存在，请通过 Query a Global Account 核实 |
| `USER_NOT_EXIST` | F | The user does not exist. Verify the account information and retry. | 调用方账户不存在，请核实 `account-id` header 或关联账户 |
| `CONTRACT_CHECK_FAIL` | F | The contract check has failed. Verify the contract status and retry. | 调用方无所需合约或权限 |
| `PROCESS_FAIL` | F | A general business failure occurred. Do not retry. | 确认函创建失败，请检查错误信息详情 |

## 示例代码

参考 [references/supporting-service/confirmation-letters/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
confirmation-letters/java/
├── service/
│   └── ConfirmationLetterService.java                       # 薄封装 Service，包含 createConfirmationLetter 方法
└── model/
    ├── request/
    │   ├── CreateConfirmationLetterRequest.java             # 请求参数（requestId, confirmationLetterType, 子参数）
    │   ├── StatementDetailLetterParams.java                 # 交易证明函参数（statementId, electronicallySigned, language）
    │   └── AccountVerificationLetterParams.java             # 账户验证函参数（globalAccountId）
    └── response/
        └── CreateConfirmationLetterResponse.java            # 响应结果（result, id, status, createdAt）
```

## 集成使用方式

ConfirmationLetterService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 根据确认函类型构造对应的参数对象（`StatementDetailLetterParams` 或 `AccountVerificationLetterParams`）
2. 构造 `CreateConfirmationLetterRequest`，设置 `requestId`（幂等键）、`confirmationLetterType` 及对应参数
3. 调用 `ConfirmationLetterService.createConfirmationLetter(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
4. 检查 `result.resultStatus` 是否为 `S`，保存 `id` 用于后续轮询查询

### 业务代码示例（STATEMENT_DETAIL_LETTER）

```java
// 构造交易证明函参数
StatementDetailLetterParams letterParams = new StatementDetailLetterParams();
letterParams.setStatementId("STM202604230001");
letterParams.setElectronicallySigned(true);
letterParams.setLanguage("EN_GB");

// 构造请求
CreateConfirmationLetterRequest request = new CreateConfirmationLetterRequest();
request.setRequestId(UUID.randomUUID().toString());
request.setConfirmationLetterType("STATEMENT_DETAIL_LETTER");
request.setStatementDetailLetterParams(letterParams);

// 调用 Service（内部已强制验签，验签失败抛 WfException）
CreateConfirmationLetterResponse response = confirmationLetterService.createConfirmationLetter(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    String taskId = response.getId();
    System.out.println("任务创建成功，ID: " + taskId);
    System.out.println("任务状态: " + response.getStatus());
    System.out.println("创建时间: " + response.getCreatedAt());
    // 保存 taskId，用于后续轮询查询
} else {
    System.err.println("任务创建失败: " + response.getResult().getResultMessage());
}
```

### 业务代码示例（ACCOUNT_VERIFICATION_LETTER）

```java
// 构造账户验证函参数
AccountVerificationLetterParams letterParams = new AccountVerificationLetterParams();
letterParams.setGlobalAccountId("GA202604010001");

// 构造请求
CreateConfirmationLetterRequest request = new CreateConfirmationLetterRequest();
request.setRequestId(UUID.randomUUID().toString());
request.setConfirmationLetterType("ACCOUNT_VERIFICATION_LETTER");
request.setAccountVerificationLetterParams(letterParams);

// 调用 Service（内部已强制验签，验签失败抛 WfException）
CreateConfirmationLetterResponse response = confirmationLetterService.createConfirmationLetter(request);

if ("S".equals(response.getResult().getResultStatus())) {
    String taskId = response.getId();
    System.out.println("账户验证函任务创建成功，ID: " + taskId);
} else {
    System.err.println("任务创建失败: " + response.getResult().getResultMessage());
}
```

### 创建后轮询获取下载链接

```java
// 1. 创建任务
CreateConfirmationLetterRequest createRequest = new CreateConfirmationLetterRequest();
createRequest.setRequestId(UUID.randomUUID().toString());
createRequest.setConfirmationLetterType("STATEMENT_DETAIL_LETTER");
StatementDetailLetterParams params = new StatementDetailLetterParams();
params.setStatementId("STM202604230001");
createRequest.setStatementDetailLetterParams(params);

CreateConfirmationLetterResponse createResponse = confirmationLetterService.createConfirmationLetter(createRequest);

if ("S".equals(createResponse.getResult().getResultStatus())) {
    String taskId = createResponse.getId();

    // 2. 轮询查询任务状态（建议间隔 2-5 秒，设置最大重试次数）
    int maxRetries = 30;
    for (int i = 0; i < maxRetries; i++) {
        Thread.sleep(3000);

        QueryConfirmationLetterRequest queryRequest = new QueryConfirmationLetterRequest();
        queryRequest.setId(taskId);
        QueryConfirmationLetterResponse queryResponse = confirmationLetterService.queryConfirmationLetter(queryRequest);

        String status = queryResponse.getStatus();
        if ("SUCCESS".equals(status)) {
            System.out.println("下载链接: " + queryResponse.getDownloadUrl());
            System.out.println("链接有效期至: " + queryResponse.getDownloadUrlExpiresAt());
            break;
        } else if ("FAIL".equals(status)) {
            System.err.println("任务失败: " + queryResponse.getFailureCode()
                + " - " + queryResponse.getFailureMessage());
            break;
        }
        // PROCESSING: 继续等待
        System.out.println("任务处理中... (" + (i + 1) + "/" + maxRetries + ")");
    }
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 ConfirmationLetterService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new ConfirmationLetterService(config)` 创建实例。
