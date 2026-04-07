---
name: wf-api-common
description: Generate common/shared foundation code for all WorldFirst (WF) API integrations, including WfConfig, WfSigner, WfHttpClientUtil, WfErrorCode, WfException, and Result classes. Use when initializing a new WF API integration project or when any WF API client needs the shared infrastructure layer (config, signing, HTTP client, error handling, response model).
---

# WF API Common Module

Generates the shared foundation layer used by **all** WF API integration clients. This module provides configuration, RSA256 signing, HTTP communication, error handling, and common response models.

**Note**: All Java template files use `{basePackage}` placeholder. Golang templates use `{moduleName}` placeholder. Replace with actual values when generating code.

## Module Structure

```
common/
├── java/
│   ├── config/
│   │   └── WfConfig.java
│   ├── signer/
│   │   └── WfSigner.java
│   ├── util/
│   │   └── WfHttpClientUtil.java
│   └── model/
│       ├── exception/
│       │   ├── WfErrorCode.java
│       │   └── WfException.java
│       └── response/
│           └── Result.java
└── golang/
    ├── config/
    │   └── config.go
    ├── signer/
    │   └── signer.go
    ├── util/
    │   └── wf_http_client.go
    └── model/
        ├── exception/
        │   ├── error_code.go
        │   └── wf_exception.go
        └── response/
            └── result.go
```

## Design Principle

**Shared infrastructure**: All WF API clients (payout, transfer, beneficiary, balance inquiry, etc.) depend on this common module. No business-specific API client should duplicate config, signing, HTTP handling, or error code logic. Generate this module **first** before generating any specific API integration.

---

## Step 1: Ask User via AskUserQuestion

**Must** ask the user the following before generating code:

| Question | Default |
|----------|---------|
| WF Client ID | `YOUR_CLIENT_ID` |
| WF User ID（登录 userId） | `YOUR_USER_ID` |
| API base URL | `https://iopengw-sggz95m.alipay.com` |
| Private key file path (PKCS#8) | `/path/to/private_key.pem` |
| WF public key file path | `/path/to/wf_public_key.pem` |
| Language (Java/Golang) | Java |
| Base package (Java) or module name (Golang) | *(user input)* |

---

## Component 1: WfConfig

**Package:** `{basePackage}.config` (Java) / `config` (Golang)

Holds all WF API configuration: client ID, base URL, key paths, and timeout settings.

### Java Fields

| Field | Type | Default | Description |
|-------|------|---------|-------------|
| `clientId` | `String` | *(user input)* | WF client identifier |
| `userId` | `String` | *(user input)* | WF user identifier（登录 userId） |
| `baseUrl` | `String` | *(user input)* | API base URL |
| `privateKeyPath` | `String` | *(user input)* | PKCS#8 private key file path |
| `publicKeyPath` | `String` | *(user input)* | WF public key file path |
| `connectTimeout` | `int` | `10000` | HTTP connect timeout (ms) |
| `readTimeout` | `int` | `30000` | HTTP read timeout (ms) |

### Java Rules

- Standard getter/setter for each field, Javadoc follows Alibaba coding guidelines
- Provide `toString()` — **must NOT** output `privateKeyPath` / `publicKeyPath` actual values
- Do not use `@ConfigurationProperties`; assign default values directly for non-Spring usage

### Java Template

```java
public class WfConfig {

    /** WF client identifier */
    private String clientId = "{USER_CLIENT_ID}";

    /** WF user identifier（登录 userId） */
    private String userId = "{USER_USER_ID}";

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
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
```

### Golang Template

```go
package config

type WfConfig struct {
    ClientID       string
    UserID         string
    BaseURL        string
    PrivateKeyPath string
    PublicKeyPath  string
}

func NewWfConfig(clientID, userID, baseURL, privateKeyPath, publicKeyPath string) *WfConfig {
    return &WfConfig{
        ClientID:       clientID,
        UserID:         userID,
        BaseURL:        baseURL,
        PrivateKeyPath: privateKeyPath,
        PublicKeyPath:  publicKeyPath,
    }
}
```

---

## Component 2: WfSigner

**Package:** `{basePackage}.signer` (Java) / `signer` (Golang)

Handles RSA256 request signing and response signature verification.

### Signing Algorithm

**Signature content format** (must be exact, including newline):
```
POST {apiPath}\n{clientId}.{requestTime}.{requestBody}
```

**Steps:**
1. Construct content string (UTF-8)
2. Sign with `SHA256withRSA` using PKCS#8 private key
3. Base64-encode the signature bytes
4. **URL-encode** the Base64 string
5. Set header: `algorithm=RSA256, keyVersion=2, signature={urlEncodedBase64}`

**Request-Time format:** ISO 8601 — `yyyy-MM-dd'T'HH:mm:ssXXX`, e.g. `2024-01-15T10:30:00+08:00`

### Java Public Methods

| Method | Returns | Description |
|--------|---------|-------------|
| `generateSignature(String apiPath, String requestTime, String requestBody)` | `String` | Generate URL-encoded Base64 signature; return `null` on failure |
| `verifySignature(String signatureHeader, String apiPath, String requestTime, String responseBody)` | `boolean` | Verify WF response signature; return `false` on failure |
| `getCurrentTimestamp()` | `String` | ISO 8601 timestamp in Asia/Shanghai timezone |

### Golang Interface & Struct

```go
type Signer interface {
    GenerateSignatureWithPath(apiPath, clientID, requestTime, body string) (string, error)
    GetRequestTime() string
}
```

`WfSigner` struct implements `Signer` interface, enabling mock injection for testing.

### Key Rules

- **Key format**: PKCS#8 only (not PKCS#1)
- **Encoding**: Always UTF-8 for content string
- **URL encoding**: `generateSignature` must URL-encode; `verifySignature` must URL-decode before Base64-decoding
- **Null check**: Callers MUST check for null/error return before using
- **Same requestTime**: Verification MUST use the same `requestTime` sent with the original request
- **Logging**: Log signature content at DEBUG level, never log the key itself

---

## Component 3: WfHttpClientUtil

**Package:** `{basePackage}.util` (Java) / `util` (Golang)

Reusable HTTP layer for all WF API integrations. **Single responsibility**: all WF HTTP request logic lives here.

### Java Constructor

`WfHttpClientUtil(WfConfig config)` — creates `CloseableHttpClient` with timeout settings and internal `WfSigner`.

`WfHttpClientUtil(WfConfig config, WfSigner signer)` — allows external signer injection for testing.

### Java Public Methods

| Method | Returns | Description |
|--------|---------|-------------|
| `sendPostRequest(String url, String apiPath, String requestBody)` | `String` | Send signed POST, return response body |
| `close()` | `void` | Close underlying HTTP client (idempotent) |
| `getConfig()` | `WfConfig` | Accessor |
| `getSigner()` | `WfSigner` | Accessor |

### Required Headers

Every request MUST set these four headers:

| Header | Value |
|--------|-------|
| `Content-Type` | `application/json; charset=UTF-8` |
| `Client-Id` | `config.getClientId()` |
| `Request-Time` | ISO 8601, Asia/Shanghai timezone |
| `Signature` | `algorithm=RSA256, keyVersion=2, signature={base64}` |

### Response Handling

- Check HTTP status code (non-200 throws `WfException`)
- Verify response signature from `Signature` header using `WfSigner.verifySignature()`
- Log verification result

### Golang Design

- `WfHttpClient` struct holds config, signer, and `http.Client`
- Constructor takes `*config.WfConfig` and `signer.Signer` interface (dependency injection)
- `PostJSON(apiPath string, bodyBytes []byte) ([]byte, error)` — handles signing, headers, and request/response
- Returns raw response body bytes; caller handles JSON unmarshaling

### Rules

- **Always** use `try-with-resources` for `CloseableHttpResponse` (Java)
- **Never** catch `WfException` and re-wrap it (rethrow as-is)
- **Always** check signature is non-null before sending
- `close()` must be idempotent and swallow `IOException`

### Logging Requirements

- `INFO`: Log URL and `requestTime` before each request; log response HTTP status code
- `DEBUG`: Log request body and response body
- `WARN`: Log `close()` failures; log missing Signature header in response
- `ERROR`: Log unexpected exceptions; log signature verification failures

---

## Component 4: WfErrorCode

**Package:** `{basePackage}.model.exception` (Java) / `model/exception` (Golang)

Enumeration of all WF API error codes with retryability flag.

### Error Code Categories

| Category | Examples | Retryable |
|----------|----------|-----------|
| Common | `PARAM_ILLEGAL`, `INVALID_SIGNATURE`, `SYSTEM_ERROR` | No |
| Business-specific | `BALANCE_NOT_ENOUGH`, `AMOUNT_EXCEED_LIMIT`, `QUOTE_EXPIRED` | No |
| Retryable | `UNKNOWN_EXCEPTION`, `REQUEST_TRAFFIC_EXCEED_LIMIT` | Yes |
| Client internal | `HTTP_REQUEST_FAILED`, `INVALID_RESPONSE_FORMAT`, `SIGNATURE_GENERATION_FAILED` | No |

### Java Template

```java
public enum WfErrorCode {
    PARAM_ILLEGAL("PARAM_ILLEGAL", false),
    INVALID_SIGNATURE("INVALID_SIGNATURE", false),
    HTTP_REQUEST_FAILED("HTTP_REQUEST_FAILED", false),
    UNKNOWN_EXCEPTION("UNKNOWN_EXCEPTION", true),
    // ... other codes
    UNKNOWN("UNKNOWN", false);

    private final String code;
    private final boolean retryable;

    // constructor, getters, fromCode() ...
}
```

### Golang Template

```go
type WfErrorCode string

const (
    ParamIllegal    WfErrorCode = "PARAM_ILLEGAL"
    InvalidSignature WfErrorCode = "INVALID_SIGNATURE"
    // ...
)

func (e WfErrorCode) IsRetryable() bool { ... }
```

### Rules

- If `WfErrorCode` already exists, **append** new enum values — do NOT regenerate the entire file
- Each business API integration may add its own specific error codes

---

## Component 5: WfException

**Package:** `{basePackage}.model.exception` (Java) / `model/exception` (Golang)

Unified exception type for all WF API errors.

### Java Template

```java
public class WfException extends RuntimeException {
    private final WfErrorCode errorCode;

    public WfException(WfErrorCode errorCode, String message) { ... }
    public WfException(WfErrorCode errorCode, String message, Throwable cause) { ... }

    public WfErrorCode getErrorCode() { return errorCode; }
    public boolean isRetryable() { return errorCode != null && errorCode.isRetryable(); }
}
```

### Golang Template

```go
type WfException struct {
    Code    WfErrorCode
    Message string
}

func (e *WfException) Error() string {
    return fmt.Sprintf("WF Error [%s]: %s", e.Code, e.Message)
}
```

---

## Component 6: Result

**Package:** `{basePackage}.model.response` (Java) / `model/response` (Golang)

Common response result object used by all WF API responses.

### Fields

| Field | Type | Description |
|-------|------|-------------|
| `resultStatus` | `String` | `S`=Success, `F`=Failure, `U`=Unknown (retryable) |
| `resultCode` | `String` | Error code string |
| `resultMessage` | `String` | Human-readable description |

### Java Template

```java
public class Result {
    private String resultStatus;
    private String resultCode;
    private String resultMessage;

    // standard getters and setters ...

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
```

### Golang Template

```go
type Result struct {
    ResultStatus  string `json:"resultStatus"`
    ResultCode    string `json:"resultCode"`
    ResultMessage string `json:"resultMessage"`
}

func (r *Result) IsSuccess() bool { return r.ResultStatus == "S" }
func (r *Result) IsFailure() bool { return r.ResultStatus == "F" }
func (r *Result) IsUnknown() bool { return r.ResultStatus == "U" }
```

---

## Common Issues

| Issue | Cause | Fix |
|-------|-------|-----|
| `InvalidKeyException` on sign | PKCS#1 key used | Convert to PKCS#8 with `openssl pkcs8` |
| Signature mismatch | Extra spaces/newlines in content | Use exact format specified above |
| Verification always false | Wrong `requestTime` | Use original request's `requestTime`, not a new timestamp |
| `privateKeyPath` leaked to logs | `toString()` includes key paths | `WfConfig.toString()` must NOT output key paths |
| Duplicate `WfErrorCode` entries | Re-generated entire enum file | Append new codes to existing file |

---

## How to Generate

1. Ask user for: project path, language (Java/Golang)
2. If Golang: read `go.mod` to get module name, replace `{moduleName}`
3. If Java: ask for base package, replace `{basePackage}`
4. Ask for credentials (clientID, userId, baseURL, key paths)
5. Generate all 6 components in order: WfConfig → WfSigner → WfHttpClientUtil → WfErrorCode → WfException → Result
6. Template files under `java/` and `golang/` are reference implementations

