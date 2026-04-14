# WF API Common Module

Generates the shared foundation layer used by **all** WF API integration clients. This module provides configuration, RSA256 signing, HTTP communication, error handling, and common response models.

**Note**: All Java template files use `{basePackage}` placeholder. Golang templates use `{moduleName}` placeholder. Python templates use `{basePackage}` placeholder in import paths. Replace with actual values when generating code.

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
├── golang/
│   ├── config/
│   │   └── config.go
│   ├── signer/
│   │   └── signer.go
│   ├── util/
│   │   └── wf_http_client.go
│   └── model/
│       ├── exception/
│       │   ├── error_code.go
│       │   └── wf_exception.go
│       └── response/
│           └── result.go
└── python/
    ├── requirements.txt
    ├── config/
    │   └── wf_config.py
    ├── signer/
    │   └── wf_signer.py
    ├── util/
    │   └── wf_http_client.py
    └── model/
        ├── exception/
        │   ├── wf_error_code.py
        │   └── wf_exception.py
        └── response/
            └── result.py
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
| API base URL | `https://open-sitprod-sg.alipay.com` |
| Private key file path (PKCS#8) | `/path/to/private_key.pem` |
| WF public key file path | `/path/to/wf_public_key.pem` |
| Language (Java/Golang/Python) | Java |
| Base package (Java/Python) or module name (Golang) | *(user input)* |

---

## Component 1: WfConfig

**Package:** `{basePackage}.config` (Java) / `config` (Golang) / `{basePackage}.wf.config` (Python)

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

Use `read_file` tool to view: `wf-api-integration/references/common/java/config/WfConfig.java`

### Golang Template

Use `read_file` tool to view: `wf-api-integration/references/common/golang/config/config.go`

### Python Rules

- Constructor parameters support environment variable fallback via `os.getenv()`; avoid hardcoding sensitive information
- Timeout units are **seconds** (not milliseconds), `connect_timeout` defaults to `10`, `read_timeout` defaults to `30`
- `__repr__()` **must NOT** output `private_key_path` / `public_key_path` actual values

### Python Template

Use `read_file` tool to view: `wf-api-integration/references/common/python/config/wf_config.py`

---

## Component 2: WfSigner

**Package:** `{basePackage}.signer` (Java) / `signer` (Golang) / `{basePackage}.wf.signer` (Python)

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

Use `read_file` tool to view: `wf-api-integration/references/common/golang/signer/signer.go`

`WfSigner` struct implements `Signer` interface, enabling mock injection for testing.

### Python Public Methods

| Method | Returns | Description |
|--------|---------|-------------|
| `generate_signature(api_path, request_time, request_body)` | `str \| None` | Generate URL-encoded Base64 signature; return `None` on failure |
| `verify_signature(signature_header, api_path, request_time, response_body)` | `bool` | Verify WF response signature; return `False` on failure |
| `get_current_timestamp()` | `str` | ISO 8601 timestamp in Asia/Shanghai timezone (static method) |

### Python Template

Use `read_file` tool to view: `wf-api-integration/references/common/python/signer/wf_signer.py`

### Python Rules

- Uses `cryptography` library: `hashes.SHA256()` + `padding.PKCS1v15()` for signing
- Private key loaded via `serialization.load_pem_private_key()`, public key via `serialization.load_pem_public_key()`
- `generate_signature` returns `None` on any exception; caller MUST check before using
- Uses Python `logging` module; signature content logged at DEBUG level

### Key Rules

- **Key format**: PKCS#8 only (not PKCS#1)
- **Encoding**: Always UTF-8 for content string
- **URL encoding**: `generateSignature` must URL-encode; `verifySignature` must URL-decode before Base64-decoding
- **Null check**: Callers MUST check for null/error return before using
- **Same requestTime**: Verification MUST use the same `requestTime` sent with the original request
- **Logging**: Log signature content at DEBUG level, never log the key itself

---

## Component 3: WfHttpClientUtil

**Package:** `{basePackage}.util` (Java) / `util` (Golang) / `{basePackage}.wf.util` (Python)

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

### Python Design

- `WfHttpClient` class wraps `requests.Session` with config and signer
- Constructor: `WfHttpClient(config: WfConfig, signer: WfSigner = None)` — auto-creates signer if not provided (supports mock injection for testing)
- `send_post_request(url, api_path, request_body) -> str` — handles signing, headers, request/response, and signature verification
- `close()` — closes underlying `requests.Session`
- Timeout uses `(connect_timeout, read_timeout)` tuple in **seconds**
- Raises `WfException` on HTTP error (non-200) or signature verification failure
- Response signature verification reads `Signature` and `response-time` headers

### Python Template

Use `read_file` tool to view: `wf-api-integration/references/common/python/util/wf_http_client.py`

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

**Package:** `{basePackage}.model.exception` (Java) / `model/exception` (Golang) / `{basePackage}.wf.model.exception` (Python)

Enumeration of all WF API error codes with retryability flag.

### Error Code Categories

| Category | Examples | Retryable |
|----------|----------|-----------|
| Common | `PARAM_ILLEGAL`, `INVALID_SIGNATURE`, `SYSTEM_ERROR` | No |
| Business-specific | `BALANCE_NOT_ENOUGH`, `AMOUNT_EXCEED_LIMIT`, `QUOTE_EXPIRED` | No |
| Retryable | `UNKNOWN_EXCEPTION`, `REQUEST_TRAFFIC_EXCEED_LIMIT` | Yes |
| Client internal | `HTTP_REQUEST_FAILED`, `INVALID_RESPONSE_FORMAT`, `SIGNATURE_GENERATION_FAILED` | No |

### Java Template

Use `read_file` tool to view: `wf-api-integration/references/common/java/model/exception/WfErrorCode.java`

### Golang Template

Use `read_file` tool to view: `wf-api-integration/references/common/golang/model/exception/error_code.go`

### Python Template

Use `read_file` tool to view: `wf-api-integration/references/common/python/model/exception/wf_error_code.py`

### Rules

- If `WfErrorCode` already exists, **append** new enum values — do NOT regenerate the entire file
- Each business API integration may add its own specific error codes

---

## Component 5: WfException

**Package:** `{basePackage}.model.exception` (Java) / `model/exception` (Golang) / `{basePackage}.wf.model.exception` (Python)

Unified exception type for all WF API errors.

### Java Template

Use `read_file` tool to view: `wf-api-integration/references/common/java/model/exception/WfException.java`

### Golang Template

Use `read_file` tool to view: `wf-api-integration/references/common/golang/model/exception/wf_exception.go`

### Python Template

Use `read_file` tool to view: `wf-api-integration/references/common/python/model/exception/wf_exception.py`

---

## Component 6: Result

**Package:** `{basePackage}.model.response` (Java) / `model/response` (Golang) / `{basePackage}.wf.model.response` (Python)

Common response result object used by all WF API responses.

### Fields

| Field | Type | Description |
|-------|------|-------------|
| `resultStatus` | `String` | `S`=Success, `F`=Failure, `U`=Unknown (retryable) |
| `resultCode` | `String` | Error code string |
| `resultMessage` | `String` | Human-readable description |

### Java Template

Use `read_file` tool to view: `wf-api-integration/references/common/java/model/response/Result.java`

### Golang Template

Use `read_file` tool to view: `wf-api-integration/references/common/golang/model/response/result.go`

### Python Template

Use `read_file` tool to view: `wf-api-integration/references/common/python/model/response/result.py`

---

## Common Issues

| Issue | Cause | Fix |
|-------|-------|-----|
| `InvalidKeyException` on sign | PKCS#1 key used | Convert to PKCS#8 with `openssl pkcs8` |
| Signature mismatch | Extra spaces/newlines in content | Use exact format specified above |
| Verification always false | Wrong `requestTime` | Use original request's `requestTime`, not a new timestamp |
| `privateKeyPath` leaked to logs | `toString()` includes key paths | `WfConfig.toString()` / `__repr__()` must NOT output key paths |
| Duplicate `WfErrorCode` entries | Re-generated entire enum file | Append new codes to existing file |
| Python `ImportError` for `cryptography` | Missing dependency | Run `pip install -r requirements.txt` |
| Python timeout unit mismatch | Using milliseconds instead of seconds | Python `requests` uses seconds; `connect_timeout=10`, `read_timeout=30` |

---

## How to Generate

1. Ask user for: project path, language (Java/Golang/Python)
2. If Golang: read `go.mod` to get module name, replace `{moduleName}`
3. If Java: ask for base package, replace `{basePackage}`
4. If Python: ask for base package, replace `{basePackage}` in import paths; generate `requirements.txt`
5. Ask for credentials (clientID, userId, baseURL, key paths)
6. Generate all 6 components in order: WfConfig → WfSigner → WfHttpClientUtil → WfErrorCode → WfException → Result
7. Template files under `java/`, `golang/`, and `python/` are reference implementations

---

## Python Dependencies

Python version requires `Python >= 3.10`. The `requirements.txt` includes:

| Package | Version | Purpose |
|---------|---------|---------|
| `requests` | `>=2.28.0,<3.0.0` | HTTP client for sending signed requests |
| `cryptography` | `>=41.0.0,<44.0.0` | RSA256 signing/verification (SHA256withRSA, PKCS1v15, PEM key loading) |

