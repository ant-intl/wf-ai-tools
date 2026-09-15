# 文件上传/下载（File）模块

## 官方文档

- [WorldFirst 开发者文档 - File Upload/Download Overview](https://docs.worldfirst.com/wfdocs/api-sdk/file_upload_or_download_overview)
- [Upload a File](https://docs.worldfirst.com/wfdocs/api-sdk/upload_a_file) | [Download a File](https://docs.worldfirst.com/wfdocs/api-sdk/download_a_file)

## 接口列表

| 接口 | 目录 | 说明 | Endpoint |
|------|------|------|----------|
| 上传文件 | `upload-file/GUIDE.md` | 上传 base64 编码文件，获取文件 ID | `POST /api/open/v1/files/upload` |
| 下载文件 | `download-file/GUIDE.md` | 根据文件 ID 获取临时下载链接（有效期 1 小时） | `POST /api/open/v1/files/download` |

## FileService 说明

`FileService` 是文件上传/下载模块的服务入口，采用**薄封装模式**：

- 不做参数校验（由调用方或 WF 服务端负责）
- 验签后自动反序列化响应体，直接返回业务响应对象（如 `UploadFileResponse`、`DownloadFileResponse`）
- 自动注入签名与验签（由底层 `WfApiClient` 完成，验签失败抛 `WfException`）

### 方法列表

| 方法 | 请求类型 | 响应类型 | 说明 |
|------|----------|----------|------|
| `uploadFile(UploadFileRequest)` | `UploadFileRequest` | `UploadFileResponse` | 上传文件，返回文件 ID |
| `downloadFile(DownloadFileRequest)` | `DownloadFileRequest` | `DownloadFileResponse` | 获取文件临时下载链接 |

### 构造器

| 构造器 | 说明 |
|--------|------|
| `FileService(WfClientConfig config)` | 传入配置，内部创建 `WfApiClient` |
| `FileService(WfApiClient apiClient)` | 传入已构造的 `WfApiClient`，便于测试注入 |

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
FileService fileService = new FileService(config);
```

### 3. 上传文件（内部已强制验签，验签失败抛 WfException）

```java
// 读取文件并 base64 编码
byte[] fileBytes = Files.readAllBytes(Paths.get("/path/to/business_license.pdf"));
String base64Content = Base64.getEncoder().encodeToString(fileBytes);

UploadFileRequest request = new UploadFileRequest();
request.setFileContent(base64Content);
request.setFileName("business_license.pdf");

UploadFileResponse response = fileService.uploadFile(request);

if ("S".equals(response.getResult().getResultStatus())) {
    String fileId = response.getId();
    System.out.println("文件上传成功，ID: " + fileId);
}
```

### 4. 下载文件（内部已强制验签，验签失败抛 WfException）

```java
DownloadFileRequest request = new DownloadFileRequest();
request.setId("20260430_a1b2c3d4****");

DownloadFileResponse response = fileService.downloadFile(request);

if ("S".equals(response.getResult().getResultStatus())) {
    String downloadUrl = response.getDownloadUrl();
    System.out.println("下载链接: " + downloadUrl);
    System.out.println("过期时间: " + response.getDownloadUrlExpiresAt());
}
```

## 注意事项

### upload_a_file
- `fileContent` 为 base64 编码的文件内容，原始文件大小不超过 **7 MB**
- `fileName` 为原始文件名（含扩展名），长度不超过 256 字符
- 支持的扩展名：`pdf`、`jpg`、`jpeg`、`png`、`zip`
- 文件名不允许包含以下字符：`` ` / : * ? " < > | ``
- 上传成功后返回 `id`，用于后续下载

### download_a_file
- `id` 为上传文件时返回的唯一文件标识符
- 下载链接 `downloadUrl` 有效期为 **1 小时**，过期后需重新调用接口获取
- `downloadUrl` 和 `downloadUrlExpiresAt` 仅在 `resultStatus` 为 `S` 时返回

## 文件结构

```
file/
├── README.md
├── java/
│   ├── service/
│   │   └── FileService.java                           # 文件上传/下载服务（薄封装）
│   └── model/
│       ├── request/
│       │   ├── UploadFileRequest.java                 # 上传文件请求
│       │   └── DownloadFileRequest.java               # 下载文件请求
│       └── response/
│           ├── UploadFileResponse.java                # 上传文件响应
│           └── DownloadFileResponse.java              # 下载文件响应
├── upload-file/
│   └── GUIDE.md                                       # upload_a_file 接口接入指引
└── download-file/
    └── GUIDE.md                                       # download_a_file 接口接入指引
```

> **复用 domain 对象**：`Result` 等通用对象与其他模块共享，引用 `{basePackage}.wf.model.domain` 包下的已有定义。本模块无独有 domain 对象。
