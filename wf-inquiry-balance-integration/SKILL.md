---
name: wf-inquiry-balance-integration
description: Generate Java or Golang integration code for WorldFirst (WF) inquiryBalance API with RSA256 signing. Use when implementing WF account balance queries, integrating inquiryBalance API, or handling WF balance data retrieval.
---

# WF inquiryBalance API Integration

Generate production-ready integration code for WorldFirst inquiryBalance API.

## Pre-Generation Questions (MUST ASK)

Before generating any code, you MUST ask the user TWO questions:

**Question 1**: 请问你的项目路径是什么？（例如：`/Users/xxx/project/src`）

**Question 2**: 你需要生成哪种语言的代码？

| Option | Description |
|--------|-------------|
| **Java** | 生成 Java 8 实现，遵循阿里巴巴 Java 编码规范 |
| **Golang** | 生成 Go 实现，使用标准库，按包分文件 |

根据用户选择的语言，参考对应章节生成代码，并将文件保存到用户提供的项目路径下。

---

## API Specification

**Endpoint**: `POST /amsin/api/v1/business/account/inquiryBalance`

**Authentication**: RSA256 Signature

### Signing Algorithm

Signature content format (must be exact, including newline):
```
POST {apiPath}\n{clientId}.{requestTime}.{requestBody}
```

Steps:
1. Construct content string above (UTF-8)
2. Sign with `SHA256withRSA` using PKCS#8 private key
3. Base64-encode the signature bytes
4. **URL-encode** the Base64 string
5. Set header: `algorithm=RSA256, keyVersion=2, signature={urlEncodedBase64}`

**Request-Time format**: ISO 8601 with timezone offset, e.g. `2024-01-15T10:30:00+08:00`

### Request Headers

| Header | Required | Description |
|--------|----------|-------------|
| `Client-Id` | Yes | WF client identifier |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Request-Time` | Yes | ISO 8601, e.g. `2019-04-04T12:08:56+08:00` |
| `Connected-AccountId` | Conditional | Required when platform customer operates merchant account |
| `Access-Token` | Conditional | Required for OAUTH authorization |

### Request Parameters

| Field | Type | Description |
|-------|------|-------------|
| `currencyList` | List\<String\> | ISO-4217 codes (e.g. `USD`, `EUR`). If omitted, returns all currencies |
| `balanceTypes` | List\<String\> | Balance types: `NORMAL_BALANCE` (default), `SAME_NAME_TOP_UP_BALANCE`, `BUDGET_BALANCE` |
| `budgetAccountId` | String | **Required** when `balanceTypes` includes `BUDGET_BALANCE` |

### Response Fields

| Field | Type | Description |
|-------|------|-------------|
| `result` | Object | `resultStatus` (S/F/U), `resultCode`, `resultMessage` |
| `responseId` | String | Unique response ID (on S) |
| `accountBalances` | List | List of account balance objects (on S) |

#### AccountBalance Object (actual WF response structure)

| Field | Type | Description |
|-------|------|-------------|
| `accountNo` | String | Account number |
| `currency` | String | Currency code (ISO-4217) |
| `balanceType` | String | `NORMAL_BALANCE`, `SAME_NAME_TOP_UP_BALANCE`, `BUDGET_BALANCE` |
| `totalBalance` | Object | `{"currency": "USD", "value": 999450809995}` — value in minor units (cents) |
| `availableBalance` | Object | Same structure as totalBalance |
| `frozenBalance` | Object | Same structure as totalBalance |
| `budgetAccountId` | String | Budget account ID (when `balanceType=BUDGET_BALANCE`) |

### Error Codes

#### Non-retryable (resultStatus=F)

| Code | Handling |
|------|----------|
| `PARAM_ILLEGAL` | Check request parameters |
| `OAUTH_FAIL` | OAuth process failed |
| `INVALID_API` | API invalid/inactive |
| `INVALID_CLIENT` | Client ID invalid |
| `INVALID_SIGNATURE` | Signature invalid |
| `METHOD_NOT_SUPPORTED` | Ensure HTTP method is POST |
| `USER_NOT_EXIST` | User not found |
| `ACCOUNT_NOT_EXIST` | Account not found |
| `SYSTEM_ERROR` | System error, do not retry |
| `SERVICE_NOT_ALLOWED` | Service not allowed |
| `CURRENCY_NOT_SUPPORT` | Unsupported currency |
| `CONTRACT_CHECK_FAIL` | Contract check failed |
| `ACCESS_TOKEN_EXPIRED` | Access token expired |
| `AUTHORIZATION_NOT_EXIST` | Authorization missing |

#### Retryable (resultStatus=U) — max 7 retries, exponential backoff: 5/10/20/40/80/160/320 min

| Code | Handling |
|------|----------|
| `UNKNOWN_EXCEPTION` | Retry |
| `REQUEST_TRAFFIC_EXCEED_LIMIT` | Retry |

---

## Java Implementation

### Template Files

All Java template files are located at:

```
~/.qoder/skills/wf-inquiry-balance-integration/template/java/
├── model/
│   ├── request/
│   │   └── InquiryBalanceRequest.java
│   ├── response/
│   │   ├── Result.java                    ← shared, reuse if exists
│   │   └── InquiryBalanceResponse.java
│   ├── domain/
│   │   └── AccountBalance.java
│   └── exception/
│       ├── WfErrorCode.java               ← shared, reuse if exists
│       └── WfException.java               ← shared, reuse if exists
└── client/
    ├── InquiryBalanceClient.java
    └── InquiryBalanceClientTest.java
```

**Note**: All template files use `{basePackage}` placeholder for the package prefix. Before generating code, ask user for their base package (e.g., `com.example.project`), then replace `{basePackage}` with the actual value.

### Sub-skills

- **WfConfig.java** — invoke `wf-config` skill
- **WfSigner.java** — invoke `wf-rsa256-signer` skill
- **WfHttpClientUtil.java** — invoke `wf-http-client` skill

### Package Structure

```
{basePackage}.wf
├── model/
│   ├── request/    InquiryBalanceRequest.java
│   ├── response/   InquiryBalanceResponse.java
│   │               Result.java                 ← 共享，已存在则复用
│   ├── domain/     AccountBalance.java
│   └── exception/  WfException.java, WfErrorCode.java  ← 共享，已存在则复用
├── client/   InquiryBalanceClient.java
├── config/   WfConfig.java    ← 共享，复用
├── signer/   WfSigner.java    ← 共享，复用
└── util/     WfHttpClientUtil.java  ← 共享，复用
```

### Alibaba Java Coding Standards

- CamelCase classes, lowerCamelCase variables, UPPER_SNAKE_CASE constants
- Same-line opening brace, 4-space indent, max 120 chars/line
- Javadoc on all public methods, SLF4J logging, checked exceptions for recoverable errors

### InquiryBalanceClient Key behaviors

1. **Validate** — `budgetAccountId` required when `balanceTypes` includes `BUDGET_BALANCE`
2. **Build JSON body** — only include non-null/non-empty optional fields
3. **Call** `httpClientUtil.sendPostRequest(url, path, body)`
4. **Parse** response with fastjson
5. **Handle resultStatus**: S → return, F → throw WfException, U → throw WfException, null → throw INVALID_RESPONSE_FORMAT

### Java Test Generation

Before generating test code, ask:

**Question**: 签名逻辑使用哪种模式？

| Option | Description |
|--------|-------------|
| **Mock 签名** | Mock WfSigner 固定返回 `"TESTING_SIGNATURE"`，跳过真实签名 |
| **真实签名** | 使用用户提供的私钥/公钥文件路径进行真实签名 |

---

## Golang Implementation

### Prerequisites

This API depends on the following shared infrastructure. Generate them first using their respective skills:
- **wf-config** → `config/config.go`
- **wf-rsa256-signer** → `signer/signer.go`
- **wf-http-client** → `util/wf_http_client.go`

### Template Files

All Golang template files are located at:

```
~/.qoder/skills/wf-inquiry-balance-integration/template/golang/
├── model/
│   ├── request/
│   │   └── inquiry_balance_request.go
│   ├── response/
│   │   ├── result.go                          ← shared, reuse if exists
│   │   └── inquiry_balance_response.go
│   └── exception/
│       ├── error_code.go                      ← shared, reuse if exists
│       └── wf_exception.go                    ← shared, reuse if exists
└── client/
    ├── inquiry_balance_client.go
    └── inquiry_balance_integration_test.go
```

**Note**: Template uses `{moduleName}` placeholder. Replace with actual module path from user's `go.mod`.

### Generation Steps

1. Ask user for: project path, Go module name, clientId, privateKeyPath, publicKeyPath
2. Read `go.mod` in the user's project to get the module name
3. Ensure `config`, `signer`, `util` packages exist (invoke base skills if not)
4. Generate code based on user's actual project structure and module path
5. For shared files (`result.go`, `error_code.go`, `wf_exception.go`): **skip if already exists** in the target project
6. Template files under `template/golang/` are for reference only

### Key Design Notes

- `signer.Signer` is an **interface** — enables mock injection if needed in future tests
- `WfHttpClient` is a **shared utility** — all API clients inject it, never embed HTTP logic in a client directly
- `availableBalance` / `totalBalance` / `frozenBalance` are **objects** `{"currency":"USD","value":999450809995}`, NOT strings. Value is in minor units (cents).
- Signing content: `"POST {path}\n{clientId}.{requestTime}.{body}"`, Base64 result must be **URL-encoded**
- Key format: **PKCS#8** private key only. Convert with: `openssl pkcs8 -topk8 -nocrypt -in key.pem -out key_pkcs8.pem`

### Golang Checklist

- [ ] Ask user for project path and language before generating
- [ ] Read `go.mod` to get module name, use actual module path in imports
- [ ] Ensure base infrastructure exists (invoke `wf-config`, `wf-rsa256-signer`, `wf-http-client` skills if needed)
- [ ] Generate `model/request/inquiry_balance_request.go`
- [ ] Generate `model/response/result.go` ← skip if exists
- [ ] Generate `model/response/inquiry_balance_response.go`
- [ ] Generate `model/exception/error_code.go` ← skip if exists
- [ ] Generate `model/exception/wf_exception.go` ← skip if exists
- [ ] Generate `client/inquiry_balance_client.go`
- [ ] Generate `client/inquiry_balance_integration_test.go` with user's credentials
- [ ] Run `go build ./wf/...` to verify compilation
- [ ] Run `go test -v -run TestIntegration ./wf/client/...` to verify real sandbox call passes

---

## Common Issues

1. **Signature format**: content must be `POST {path}\n{clientId}.{requestTime}.{body}`, Base64 result must be URL-encoded
2. **Balance value**: `availableBalance.value` is an integer in minor units (cents), NOT a decimal string
3. **Budget Account**: `budgetAccountId` is mandatory when `balanceTypes` contains `BUDGET_BALANCE`
4. **Key format**: PKCS#8 only. Convert: `openssl pkcs8 -topk8 -nocrypt -in key.pem -out key_pkcs8.pem`
5. **WfHttpClient is shared**: never embed HTTP logic inside each API client — inject `WfHttpClient` instead
