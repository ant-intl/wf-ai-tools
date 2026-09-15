# Upload a File 接口接入指引

## 接口说明

上传 base64 编码的文件至 WorldFirst，用于开户、KYC/KYB 验证及其他辅助材料提交。上传成功后返回唯一文件 ID，可用于后续下载。

## 官方文档

- [Upload a File 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/upload_a_file)

## 请求地址

`POST /api/open/v1/files/upload`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-04-30T10:30:00+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field         | Type | Required | Description                                                                             |
|---------------|------|----------|-----------------------------------------------------------------------------------------|
| `fileContent` | string | Yes | Base64 编码的文件内容，原始文件大小不超过 7 MB                                                           |
| `fileName`    | string | Yes | 原始文件名（含扩展名），允许扩展名：`pdf`、`jpg`、`jpeg`、`png`、`zip`，不允许字符：`, / : * ? " < > ｜` 长度不超过 256 字符 |

### 请求示例

```json
{
  "fileContent": "JVBERi0xLjQKMSAwIG9iago8PA...",
  "fileName": "business_license.pdf"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `id` | string | 系统分配的唯一文件标识符 |
| `fileName` | string | 原始文件名 |
| `createdAt` | datetime | 文件上传时间，ISO 8601 格式 |

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
  "createdAt": "2026-04-30T10:30:00+08:00"
}
```

## 错误码

| resultCode | resultStatus | resultMessage | Troubleshooting |
|------------|--------------|---------------|-----------------|
| `SUCCESS` | S | success | — |
| `PARAM_ILLEGAL` | F | fileContent is not valid base64 encoding. | 检查 `fileContent` 是否为合法的 base64 编码 |
| `PARAM_ILLEGAL` | F | fileName length exceeds 256 characters. | 缩短 `fileName` 后重试 |
| `PARAM_ILLEGAL` | F | Unsupported file extension. | 使用以下扩展名之一：pdf、jpg、jpeg、png、zip |
| `PARAM_ILLEGAL` | F | fileName contains illegal characters. | 移除文件名中的非法字符：`` ` / : * ? " < > `` |
| `FILE_SIZE_TOO_LARGE` | F | File size is too large. | 确保原始文件大小不超过 7 MB |
| `SYSTEM_ERROR` | F | System error. | 使用相同参数重试 |
| `AUTHORIZATION_NOT_EXIST` | F | oauth not exist. | 验证 `account-id` 属于当前平台商户 |

## 示例代码

参考 [references/supporting-service/file/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
file/java/
├── service/
│   └── FileService.java                               # 薄封装 Service，包含 uploadFile 方法
└── model/
    ├── request/
    │   └── UploadFileRequest.java                     # 请求参数（fileContent, fileName）
    └── response/
        └── UploadFileResponse.java                    # 响应结果（result, id, fileName, createdAt）
```

## 集成使用方式

FileService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 将文件读取为字节数组并进行 base64 编码
2. 构造 `UploadFileRequest`，设置 `fileContent` 和 `fileName`
3. 调用 `FileService.uploadFile(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
4. 检查 `result.resultStatus` 是否为 `S`，获取 `id` 用于后续下载

### 业务代码示例

```java
// 读取文件并 base64 编码
byte[] fileBytes = Files.readAllBytes(Paths.get("/path/to/business_license.pdf"));
String base64Content = Base64.getEncoder().encodeToString(fileBytes);

// 构造请求
UploadFileRequest request = new UploadFileRequest();
request.setFileContent(base64Content);
request.setFileName("business_license.pdf");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
UploadFileResponse response = fileService.uploadFile(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    String fileId = response.getId();
    System.out.println("文件上传成功，ID: " + fileId);
    System.out.println("文件名: " + response.getFileName());
    System.out.println("上传时间: " + response.getCreatedAt());
    // 保存 fileId，用于后续下载
} else {
    System.err.println("上传失败: " + response.getResult().getResultMessage());
}
```

### 批量上传多个文件

```java
String[] filePaths = {
    "/path/to/business_license.pdf",
    "/path/to/id_card_front.jpg",
    "/path/to/bank_statement.pdf"
};

List<String> uploadedFileIds = new ArrayList<>();

for (String filePath : filePaths) {
    Path path = Paths.get(filePath);
    byte[] fileBytes = Files.readAllBytes(path);
    String base64Content = Base64.getEncoder().encodeToString(fileBytes);

    UploadFileRequest request = new UploadFileRequest();
    request.setFileContent(base64Content);
    request.setFileName(path.getFileName().toString());

    UploadFileResponse response = fileService.uploadFile(request);

    if ("S".equals(response.getResult().getResultStatus())) {
        uploadedFileIds.add(response.getId());
        System.out.println("已上传: " + response.getFileName() + " -> " + response.getId());
    } else {
        System.err.println("上传失败: " + response.getFileName()
            + " 原因: " + response.getResult().getResultMessage());
    }
}

System.out.println("共上传成功 " + uploadedFileIds.size() + " 个文件");
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 FileService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new FileService(config)` 创建实例。
