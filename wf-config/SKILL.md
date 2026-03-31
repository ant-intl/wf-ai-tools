---
name: wf-config
description: Interactively collect WF API credentials from the user and generate WfConfig configuration class. Use when setting up any WorldFirst (WF) API integration, initializing WF client configuration, or generating WfConfig with user-provided clientId and base URL.
---

# WF Config Setup

Asks the user for WF API credentials and generates `WfConfig.java` or `config.go`.

**Note**: All Java template files use `{basePackage}` placeholder. Golang templates use `{moduleName}` placeholder. Replace with actual values when generating code.

## Step 1: Ask User via AskUserQuestion

**必须**在生成代码前通过 `AskUserQuestion` 工具询问以下四项，每项提供默认值选项：

| 问题 | 默认值 |
|------|--------|
| WF 分配的 Client ID | `YOUR_CLIENT_ID` |
| API 接口域名（base-url） | `https://iopengw-sggz95m.alipay.com` |
| 客户私钥文件路径（PKCS#8） | `/path/to/private_key.pem` |
| WF公钥文件路径 | `/path/to/wf_public_key.pem` |

用户可选择使用默认值或自行输入。将最终值写入生成类的字段默认值中。

## Step 2: Generate WfConfig.java

**Package:** `{basePackage}.config`

### Fields

| Field | Type | Default | Description |
|-------|------|---------|-------------|
| `clientId` | `String` | *(user input)* | WF client identifier |
| `baseUrl` | `String` | *(user input)* | API base URL |
| `privateKeyPath` | `String` | *(user input)* | PKCS#8 private key file path |
| `publicKeyPath` | `String` | *(user input)* | WF public key file path |
| `connectTimeout` | `int` | `10000` | HTTP connect timeout (ms) |
| `readTimeout` | `int` | `30000` | HTTP read timeout (ms) |

### Rules

- 每个字段提供标准 getter/setter，Javadoc 格式遵循阿里巴巴编码规范
- 提供 `toString()` 方法，**不得**输出 `privateKeyPath` / `publicKeyPath` 的实际内容
- 不使用 `@ConfigurationProperties`，字段直接赋默认值，便于非 Spring 场景使用

### Template

```java
public class WfConfig {

    /** WF client identifier */
    private String clientId = "{USER_CLIENT_ID}";

    /** WF API base URL */
    private String baseUrl = "{USER_BASE_URL}";

    /** RSA private key file path (PKCS#8) */
    private String privateKeyPath = "{USER_PRIVATE_KEY_PATH}";

    /** WF RSA public key file path */
    private String publicKeyPath = "{USER_PUBLIC_KEY_PATH}";

    /** HTTP connect timeout in milliseconds */
    private int connectTimeout = 10000;

    /** HTTP read timeout in milliseconds */
    private int readTimeout = 30000;

    // standard getters and setters ...

    @Override
    public String toString() {
        return "WfConfig{clientId='" + clientId + "', baseUrl='" + baseUrl
            + "', connectTimeout=" + connectTimeout + ", readTimeout=" + readTimeout + '}';
    }
}
```

## Common Issues

| Issue | Fix |
|-------|-----|
| 用户跳过询问 | 使用默认值，在生成代码注释中提示替换 |
| `privateKeyPath` 泄漏到日志 | `toString()` 中不包含该字段 |
| 密钥路径不存在 | 在代码注释中提示用户确保文件存在且有读取权限 |

---

## Golang Template

Pre-built Golang implementation is available under `template/golang/`. Use this when the user requests Golang code.

**Note**: Template uses `{moduleName}` placeholder. Replace with actual module path from user's `go.mod`.

### Template Structure

```
template/golang/
└── config.go
```

### Golang Implementation

```go
package config

// WfConfig holds WorldFirst API configuration.
type WfConfig struct {
    ClientID       string
    BaseURL        string
    PrivateKeyPath string
    PublicKeyPath  string
}

// NewWfConfig creates a new WfConfig instance.
func NewWfConfig(clientID, baseURL, privateKeyPath, publicKeyPath string) *WfConfig {
    return &WfConfig{
        ClientID:       clientID,
        BaseURL:        baseURL,
        PrivateKeyPath: privateKeyPath,
        PublicKeyPath:  publicKeyPath,
    }
}
```

### How to Generate

1. Ask user for: project path, language (Java/Golang)
2. If Golang: read `go.mod` to get module name, replace `{moduleName}`
3. If Java: ask for base package, replace `{basePackage}`
4. Ask for credentials (clientID, baseURL, key paths)
5. Generate code based on user's actual project structure
