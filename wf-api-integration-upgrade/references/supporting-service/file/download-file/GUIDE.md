# Download a File 接口接入指引

## 接口说明

根据文件 ID 获取已上传文件的临时下载链接。下载链接有效期 1 小时，过期后需重新调用接口获取新链接。

## 官方文档

- [Download a File 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/download_a_file)

## 请求地址

`POST /api/open/v1/files/download`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-04-30T10:30:00+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | string | Yes | 唯一文件标识符，由 Upload a File 接口返回 |

### 请求示例

```json
{
  "id": "20260430_a1b2c3d4****"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `id` | string | 唯一文件标识符 |
| `fileName` | string | 原始文件名 |
| `createdAt` | datetime | 文件原始上传时间，ISO 8601 格式 |
| `downloadUrl` | string | 临时下载链接，有效期 1 小时。仅在 `resultStatus` 为 `S` 时返回 |
| `downloadUrlExpiresAt` | datetime | 下载链接过期时间，ISO 8601 格式。仅在 `resultStatus` 为 `S` 时返回 |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "id": "20260430_a1b2c3d4****",
  "fileName": "business_license.pdf",
  "createdAt": "2026-04-30T10:30:00+08:00",
  "downloadUrl": "https://api.worldfirst.com/v1/files/download/20260430_a1b2c3d4****?token=eyJhbGciOi...&expires=3600",
  "downloadUrlExpiresAt": "2026-04-30T11:30:00+08:00"
}
```

## 错误码

| resultCode | resultStatus | resultMessage | Troubleshooting |
|------------|--------------|---------------|-----------------|
| `SUCCESS` | S | success | — |
| `PARAM_ILLEGAL` | F | Invalid file ID. | 提供由 Upload a File 接口返回的有效文件 ID |
| `FILE_NOT_FOUND` | F | File not found. | 指定的文件 ID 不存在或已被删除 |
| `SYSTEM_ERROR` | F | System error. | 使用相同参数重试 |
| `AUTHORIZATION_NOT_EXIST` | F | oauth not exist. | 验证 `account-id` 属于当前平台商户 |

## 示例代码

参考 [references/supporting-service/file/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
file/java/
├── service/
│   └── FileService.java                               # 薄封装 Service，包含 downloadFile 方法
└── model/
    ├── request/
    │   └── DownloadFileRequest.java                   # 请求参数（id）
    └── response/
        └── DownloadFileResponse.java                  # 响应结果（result, id, fileName, createdAt, downloadUrl, downloadUrlExpiresAt）
```

## 集成使用方式

FileService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `DownloadFileRequest`，设置 `id`（由上传接口返回）
2. 调用 `FileService.downloadFile(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，获取 `downloadUrl` 进行文件下载
4. 注意 `downloadUrl` 有效期为 1 小时，过期需重新获取

### 业务代码示例

```java
// 构造请求
DownloadFileRequest request = new DownloadFileRequest();
request.setId("20260430_a1b2c3d4****");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
DownloadFileResponse response = fileService.downloadFile(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    String downloadUrl = response.getDownloadUrl();
    String expiresAt = response.getDownloadUrlExpiresAt();
    System.out.println("文件名: " + response.getFileName());
    System.out.println("下载链接: " + downloadUrl);
    System.out.println("过期时间: " + expiresAt);

    // 使用 HTTP 客户端下载文件
    // 注意：downloadUrl 有效期 1 小时，请尽快下载
} else {
    System.err.println("获取下载链接失败: " + response.getResult().getResultMessage());
}
```

### 上传后立即获取下载链接

```java
// 先上传文件
byte[] fileBytes = Files.readAllBytes(Paths.get("/path/to/report.pdf"));
String base64Content = Base64.getEncoder().encodeToString(fileBytes);

UploadFileRequest uploadRequest = new UploadFileRequest();
uploadRequest.setFileContent(base64Content);
uploadRequest.setFileName("report.pdf");

UploadFileResponse uploadResponse = fileService.uploadFile(uploadRequest);

if ("S".equals(uploadResponse.getResult().getResultStatus())) {
    String fileId = uploadResponse.getId();
    System.out.println("上传成功，文件 ID: " + fileId);

    // 立即获取下载链接
    DownloadFileRequest downloadRequest = new DownloadFileRequest();
    downloadRequest.setId(fileId);

    DownloadFileResponse downloadResponse = fileService.downloadFile(downloadRequest);

    if ("S".equals(downloadResponse.getResult().getResultStatus())) {
        System.out.println("下载链接: " + downloadResponse.getDownloadUrl());
        System.out.println("链接有效期至: " + downloadResponse.getDownloadUrlExpiresAt());
    }
}
```

### 下载链接过期后重新获取

```java
/**
 * 获取有效的下载链接，如果链接已过期则自动重新获取。
 */
public String getValidDownloadUrl(FileService fileService, String fileId) {
    DownloadFileRequest request = new DownloadFileRequest();
    request.setId(fileId);

    DownloadFileResponse response = fileService.downloadFile(request);

    if ("S".equals(response.getResult().getResultStatus())) {
        return response.getDownloadUrl();
    } else {
        throw new RuntimeException("获取下载链接失败: " + response.getResult().getResultMessage());
    }
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 FileService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new FileService(config)` 创建实例。
