# Query a Confirmation Letter 接口接入指引

## 接口说明

查询确认函下载任务的状态和结果。当任务完成（`SUCCESS`）时，响应中包含预签名下载链接及其过期时间。

## 官方文档

- [Query a Confirmation Letter 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/query_a_confirmation_letter)

## 请求地址

`POST /api/open/v1/confirmationLetters/query`

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
| `id` | string | Yes | 下载任务的唯一标识符，由 Create a Confirmation Letter 接口返回。最大 64 字符 |

### 请求示例

```json
{
  "id": "f18d0117-095a-4782-a9f1-eeb16c3b****"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `id` | string | 下载任务标识符（回显） |
| `status` | string | 任务状态：`PROCESSING`（处理中）、`SUCCESS`（成功）、`FAIL`（失败） |
| `downloadUrl` | string | 预签名下载链接，仅在 `status` 为 `SUCCESS` 时返回。最大 2,048 字符 |
| `downloadUrlExpiresAt` | datetime | 下载链接过期时间，仅在 `status` 为 `SUCCESS` 时返回。ISO 8601 扩展格式 |
| `failureCode` | string | 业务失败原因码，仅在 `status` 为 `FAIL` 时返回 |
| `failureMessage` | string | 失败原因的纯文本描述，仅在 `status` 为 `FAIL` 时返回 |
| `createdAt` | datetime | 任务创建时间，ISO 8601 扩展格式 |

### 响应示例（SUCCESS）

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "id": "f18d0117-095a-4782-a9f1-eeb16c3b****",
  "status": "SUCCESS",
  "downloadUrl": "https://example.com/files/****1234.pdf",
  "downloadUrlExpiresAt": "2026-04-28T12:08:56+08:00",
  "createdAt": "2026-04-27T12:08:56+08:00"
}
```

### 响应示例（PROCESSING）

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

### 响应示例（FAIL）

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "id": "f18d0117-095a-4782-a9f1-eeb16c3b****",
  "status": "FAIL",
  "failureCode": "STATEMENT_NOT_FOUND",
  "failureMessage": "The specified statement does not exist.",
  "createdAt": "2026-04-27T12:08:56+08:00"
}
```

## 错误码

| resultCode | resultStatus | resultMessage | Troubleshooting |
|------------|--------------|---------------|-----------------|
| `SUCCESS` | S | success | — |
| `PROCESS_FAIL` | F | A general business failure occurred. Do not retry. | 查询无法完成，请核实 `id` 是否为由 Create a Confirmation Letter 返回的有效任务标识符 |

## 示例代码

参考 [references/supporting-service/confirmation-letters/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
confirmation-letters/java/
├── service/
│   └── ConfirmationLetterService.java                       # 薄封装 Service，包含 queryConfirmationLetter 方法
└── model/
    ├── request/
    │   └── QueryConfirmationLetterRequest.java              # 请求参数（id）
    └── response/
        └── QueryConfirmationLetterResponse.java             # 响应结果（result, id, status, downloadUrl, downloadUrlExpiresAt, failureCode, failureMessage, createdAt）
```

## 集成使用方式

ConfirmationLetterService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `QueryConfirmationLetterRequest`，设置 `id`（由创建接口返回的任务 ID）
2. 调用 `ConfirmationLetterService.queryConfirmationLetter(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，再根据 `status` 判断任务状态：
   - `PROCESSING`：任务仍在处理中，稍后重试
   - `SUCCESS`：获取 `downloadUrl` 进行下载
   - `FAIL`：查看 `failureCode` 和 `failureMessage` 了解失败原因

### 业务代码示例

```java
// 构造请求
QueryConfirmationLetterRequest request = new QueryConfirmationLetterRequest();
request.setId("f18d0117-095a-4782-a9f1-eeb16c3b****");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
QueryConfirmationLetterResponse response = confirmationLetterService.queryConfirmationLetter(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    String status = response.getStatus();

    if ("SUCCESS".equals(status)) {
        System.out.println("下载链接: " + response.getDownloadUrl());
        System.out.println("链接有效期至: " + response.getDownloadUrlExpiresAt());
        // 使用 HTTP 客户端下载 PDF
    } else if ("PROCESSING".equals(status)) {
        System.out.println("任务处理中，请稍后重试");
    } else if ("FAIL".equals(status)) {
        System.err.println("任务失败: " + response.getFailureCode()
            + " - " + response.getFailureMessage());
    }
} else {
    System.err.println("查询失败: " + response.getResult().getResultMessage());
}
```

### 带重试的轮询查询

```java
/**
 * 轮询查询确认函任务状态，直到任务完成或达到最大重试次数。
 *
 * @param taskId       任务 ID
 * @param intervalMs   轮询间隔（毫秒），建议 2000-5000
 * @param maxRetries   最大重试次数
 * @return 下载链接（任务成功时），或 null（任务失败或超时）
 */
public String pollForDownloadUrl(String taskId, long intervalMs, int maxRetries) throws InterruptedException {
    for (int i = 0; i < maxRetries; i++) {
        QueryConfirmationLetterRequest request = new QueryConfirmationLetterRequest();
        request.setId(taskId);

        QueryConfirmationLetterResponse response = confirmationLetterService.queryConfirmationLetter(request);

        String status = response.getStatus();
        if ("SUCCESS".equals(status)) {
            System.out.println("任务完成，下载链接: " + response.getDownloadUrl());
            System.out.println("链接有效期至: " + response.getDownloadUrlExpiresAt());
            return response.getDownloadUrl();
        } else if ("FAIL".equals(status)) {
            System.err.println("任务失败: " + response.getFailureCode()
                + " - " + response.getFailureMessage());
            return null;
        }

        // PROCESSING: 等待后重试
        System.out.println("任务处理中... (" + (i + 1) + "/" + maxRetries + ")");
        Thread.sleep(intervalMs);
    }

    System.err.println("轮询超时，任务仍未完成");
    return null;
}
```

### 端到端：创建任务并下载 PDF

```java
// 1. 创建确认函任务
StatementDetailLetterParams letterParams = new StatementDetailLetterParams();
letterParams.setStatementId("STM202604230001");
letterParams.setElectronicallySigned(true);

CreateConfirmationLetterRequest createRequest = new CreateConfirmationLetterRequest();
createRequest.setRequestId(UUID.randomUUID().toString());
createRequest.setConfirmationLetterType("STATEMENT_DETAIL_LETTER");
createRequest.setStatementDetailLetterParams(letterParams);

CreateConfirmationLetterResponse createResponse = confirmationLetterService.createConfirmationLetter(createRequest);

if (!"S".equals(createResponse.getResult().getResultStatus())) {
    throw new RuntimeException("创建任务失败: " + createResponse.getResult().getResultMessage());
}

String taskId = createResponse.getId();
System.out.println("任务已创建，ID: " + taskId);

// 2. 轮询等待任务完成
String downloadUrl = pollForDownloadUrl(taskId, 3000, 30);

if (downloadUrl != null) {
    // 3. 使用 HTTP 客户端下载 PDF 文件
    System.out.println("开始下载: " + downloadUrl);
    // 注意：downloadUrl 为预签名 URL，有过期时间，请尽快下载
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 ConfirmationLetterService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new ConfirmationLetterService(config)` 创建实例。
