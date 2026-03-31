---
name: wf-inquiry-statement-integration
description: Generate Java or Golang integration code for WorldFirst (WF) inquiryStatementList API with proper RSA256 signing. Use when developing WF account statement queries, implementing inquiryStatementList API calls, or handling WF statement data retrieval with pagination.
---

# WF inquiryStatementList API Integration

Generate production-ready Java code for integrating with WorldFirst inquiryStatementList API.

## Prerequisites

- Java 8 (禁止使用 Java 9+ 专有语法)
- WF Client ID and RSA key pair
- WF API endpoint URL

## Sub-skills

Generate the following components using their dedicated skills:

- **WfConfig.java** — invoke `wf-config` skill (含向用户询问 clientId / base-url 交互)
- **WfSigner.java** — invoke `wf-rsa256-signer` skill
- **WfHttpClientUtil.java** — invoke `wf-http-client` skill

## API Specification

**Endpoint**: `POST /amsin/api/v1/business/account/inquiryStatementList`

**Authentication**: RSA256 Signature — see [wf-rsa256-signer skill](~/.qoder/skills/wf-rsa256-signer/SKILL.md)

### Request Headers

| Header | Required | Description |
|--------|----------|-------------|
| `Client-Id` | Yes | WF client identifier |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<base64>` |
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Request-Time` | Yes | ISO 8601, e.g. `2019-04-04T12:08:56+08:00` |
| `Connected-AccountId` | Conditional | Required for platform-operated merchant accounts |
| `Access-Token` | Conditional | Required for OAUTH authorization |

### Request Parameters

#### Required Fields

| Field | Type | Description |
|-------|------|-------------|
| `startTime` | DateTime | ISO 8601. When `fuzzyName` is empty, duration ≤ 100 days |
| `endTime` | DateTime | ISO 8601. See `startTime` constraint |
| `pageSize` | Integer | **Fixed value: 10** |
| `pageNumber` | Integer | 1-indexed, range: **1–50** |

#### Optional Fields

| Field | Type | Description |
|-------|------|-------------|
| `transactionTypeList` | List\<String\> | `TRANSFER`, `COLLECTION`, etc. Omit = return all |
| `currencyList` | List\<String\> | ISO-4217 codes (e.g. `USD`, `EUR`). Omit = return all |
| `balanceTypes` | List\<String\> | `NORMAL_BALANCE` (default), `SAME_NAME_TOP_UP_BALANCE`, `BUDGET_BALANCE` |
| `budgetAccountIds` | List\<String\> | Budget account IDs |
| `fuzzyName` | String | Fuzzy keyword. When present, 100-day constraint is lifted |

### Response Fields

| Field | Type | Condition | Description |
|-------|------|-----------|-------------|
| `result` | Object | Always | `resultStatus` (S/F/U), `resultCode`, `resultMessage` |
| `responseId` | String | S | Response ID, max 32 chars |
| `feeItemType` | String | S | `OBO_SERVICE_FEE`, `REMIT_SERVICE_FEE` |
| `statementList` | List | S | Statement records |
| `totalCount` | Integer | S | Total records |
| `totalPageNumber` | Integer | S | Total pages |
| `currentPageNumber` | Integer | S | Current page |

#### Statement Record Fields

| Field | Description |
|-------|-------------|
| `transactionId` | Transaction ID |
| `transactionTime` | ISO 8601 |
| `transactionType` | TRANSFER, COLLECTION, etc. |
| `currency` | ISO-4217 code |
| `amount` | Positive = income, negative = expense |
| `balance` | Post-transaction balance |
| `balanceType` | NORMAL_BALANCE, etc. |
| `counterpartyName` / `counterpartyAccount` | Counterparty info |
| `remark` | Transaction summary |
| `status` | Transaction status |
| `feeAmount` / `actualAmount` | Fee and net amount |

### Error Codes

#### Non-retryable (resultStatus=F)

| Code | Handling |
|------|----------|
| `PARAM_ILLEGAL` | Check params |
| `PROCESS_FAIL` | Contact support |
| `INVALID_API` / `INVALID_CLIENT` / `INVALID_SIGNATURE` | Config issue |
| `OAUTH_FAIL` / `ACCESS_TOKEN_EXPIRED` / `AUTHORIZATION_NOT_EXIST` | Auth issue |
| `METHOD_NOT_SUPPORTED` | Ensure POST |
| `USER_NOT_EXIST` / `CURRENCY_NOT_SUPPORT` / `SYSTEM_ERROR` | See message |

#### Retryable (resultStatus=U) — max 7 retries, exponential backoff: 5/10/20/40/80/160/320 min

| Code | Handling |
|------|----------|
| `UNKNOWN_EXCEPTION` | Retry |
| `REQUEST_TRAFFIC_EXCEED_LIMIT` | Retry |

## Package Structure

```
{basePackage}.wf
├── model/
│   ├── request/    InquiryStatementRequest.java         ← 本接口生成
│   ├── response/   InquiryStatementResponse.java        ← 本接口生成
│   │               Result.java                          ← 共享，仅首次生成，已存在则复用
│   ├── domain/     StatementRecord.java                 ← 本接口生成
│   └── exception/  WfException.java, WfErrorCode.java   ← 共享，已存在则复用；新接口的错误码直接往 WfErrorCode 追加
├── client/   InquiryStatementClient.java               ← 放到统一 client 包下
├── config/   WfConfig.java                              ← 共享，复用
├── signer/   WfSigner.java                             ← 共享，复用
└── util/     WfHttpClientUtil.java                     ← 共享，复用
```

## Alibaba Java Coding Standards

- CamelCase classes, lowerCamelCase variables, UPPER_SNAKE_CASE constants
- Same-line opening brace, 4-space indent, max 120 chars/line
- Javadoc on all public methods, SLF4J logging, checked exceptions for recoverable errors

## InquiryStatementClient

Handles business logic only — no direct HTTP code. Delegates to `WfHttpClientUtil`.

### Key behaviors

1. **Validate** — required fields, pageNumber range (1–50), time range (≤100 days unless fuzzyName set)
2. **pageSize** — always override to fixed value `10` before sending
3. **Build JSON body** — only include non-null/non-empty optional fields
4. **Call** `httpClientUtil.sendPostRequest(url, path, body)`
5. **Parse** response with fastjson
6. **Handle resultStatus**:
   - `S` → return response
   - `F` → look up `WfErrorCode.fromCode(resultCode)`, throw `WfException`
   - `U` → throw `WfException` (caller retries)
   - null result → throw `INVALID_RESPONSE_FORMAT`

### init() pattern

```java
public void init() {
    this.httpClientUtil = new WfHttpClientUtil(config);
    this.signer = httpClientUtil.getSigner();
}
```

## InquiryStatementClientTest

### Pre-Generation Question: Signature Mode (MUST ASK)

Before generating test code, you MUST ask the user:

**Question**: 生成测试类时，签名逻辑使用哪种模式？

| Option | Description |
|--------|-------------|
| **Mock 签名（跳过验签）** | Mock WfSigner 的 `generateSignature` 方法固定返回 `"TESTING_SIGNATURE"`，跳过真实签名流程。适用于快速联调验证网络连通性和请求/响应格式（WF 会返回 `INVALID_SIGNATURE`）。 |
| **真实签名（使用密钥文件）** | 使用用户提供的私钥和公钥文件路径进行真实签名和验签。需要用户提供 `privateKeyPath` 和 `publicKeyPath`。 |

#### If user selects Mock 签名:

setUp 中 mock WfSigner，通过 `WfHttpClientUtil(config, mockSigner)` 注入，再通过 `client.setHttpClientUtil()` 注入到 Client：

```java
WfSigner mockSigner = Mockito.mock(WfSigner.class);
Mockito.when(mockSigner.generateSignature(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
    .thenReturn("TESTING_SIGNATURE");
Mockito.when(mockSigner.verifySignature(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
    .thenReturn(true);

WfHttpClientUtil httpClientUtil = new WfHttpClientUtil(mockConfig, mockSigner);
client = new InquiryStatementClient();
client.setConfig(mockConfig);
client.setHttpClientUtil(httpClientUtil);
```

#### If user selects 真实签名:

setUp 中配置真实密钥路径（需要向用户询问路径），通过 `init()` 初始化：

```java
Mockito.when(mockConfig.getPrivateKeyPath()).thenReturn("<用户提供的私钥路径>");
Mockito.when(mockConfig.getPublicKeyPath()).thenReturn("<用户提供的公钥路径>");

client = new InquiryStatementClient();
client.setConfig(mockConfig);
client.init();
```

---

生成单元测试类，放在项目**独立测试模块** `app/test` 中，**不要**放在集成模块自己的 `src/test/java/` 下。

**目的**：让用户直接运行测试方法观察真实 HTTP 调用效果（沙箱/生产均可）。

### 文件位置规则

```
app/test/src/test/java/com/ipay/ibizopenprod/common/service/integration/wf/InquiryStatementClientTest.java
```

- 模块根：`app/test`（与 `app/common/service/integration` 并列的独立 Maven 模块）
- 包名：`com.ipay.ibizopenprod.common.service.integration.wf`
- **每个 Client 单独一个测试类，不与其他 Client 共享**

### Mock 规则

- `WfConfig` 用 Mockito **mock**，无需真实属性文件
- `WfSigner`：根据用户选择的签名模式，决定是 Mock 还是使用真实密钥
- `InquiryStatementClient` 通过 `setHttpClientUtil()` 注入 `WfHttpClientUtil`（Mock 签名模式），或通过 `init()` 正常初始化（真实签名模式）

### 测试方法：testInquiryStatementList

用 `InquiryStatementRequest` 组装 startTime、endTime、pageNumber，调用 `client.inquiryStatementList(request)`，打印响应结果；catch `WfException` 并打印错误码，不做 assert（方便直接观察真实响应）。

### 注意事项

- 测试直接发真实 HTTP，不 mock 网络层
- `config.getBaseUrl()` 决定目标环境，沙箱地址：`https://iopengw-sggz95m.alipay.com`

## Checklist

- [ ] Generate WfConfig (use **wf-config** skill — 含用户询问交互)
- [ ] Generate model classes: InquiryStatementRequest（`wf.model.request`）, InquiryStatementResponse（`wf.model.response`）, StatementRecord（`wf.model.domain`）
- [ ] **Reuse** `Result.java` from `{basePackage}.wf.model.response`（已存在则不重复生成）
- [ ] **Reuse** WfException + WfErrorCode from `{basePackage}.wf.model.exception`（已存在则复用，仅将本接口新增的错误码追加到 WfErrorCode）
- [ ] Generate WfSigner (use **wf-rsa256-signer** skill)
- [ ] Generate WfHttpClientUtil (use **wf-http-client** skill)
- [ ] Generate InquiryStatementClient (delegates HTTP to WfHttpClientUtil, placed in `wf.client/`)
- [ ] Generate InquiryStatementClientTest（见下方 ## InquiryStatementClientTest 节）
- [ ] Follow Alibaba Java coding standards throughout

## Common Issues

1. `pageNumber` is 1-indexed (starts at 1, max 50)
2. `pageSize` fixed at 10 — never let caller change it
3. Time format: ISO 8601 with timezone, e.g. `2024-01-01T00:00:00+08:00`
4. Signature issues → see wf-rsa256-signer skill Common Issues section
5. HTTP issues → see wf-http-client skill Rules section
---

## Java Template

Pre-built Java implementation is available under `template/java/`. Use this when the user requests Java code.

**Note**: All Java template files use `{basePackage}` placeholder for the package prefix. Before generating code, ask user for their base package (e.g., `com.example.project`), then replace `{basePackage}` with the actual value.

### Template Structure

```
template/java/
├── model/
│   ├── domain/
│   │   └── StatementRecord.java
│   ├── request/
│   │   └── InquiryStatementRequest.java
│   ├── response/
│   │   ├── Result.java
│   │   └── InquiryStatementResponse.java
│   └── exception/
│       ├── WfErrorCode.java
│       └── WfException.java
└── client/
    ├── InquiryStatementClient.java
    └── InquiryStatementClientTest.java
```

## Golang Template

Pre-built Golang implementation is available under `template/golang/`. Use this when the user requests Golang code.

### Prerequisites

This API depends on the following shared infrastructure. Generate them first using their respective skills:
- **wf-config** → `config/config.go`
- **wf-rsa256-signer** → `signer/signer.go`
- **wf-http-client** → `util/wf_http_client.go`

**Note**: Template uses `{moduleName}` placeholder. Replace with actual module path from user's `go.mod`.

### Template Structure

```
template/golang/
├── model/
│   ├── request/inquiry_statement_request.go
│   ├── response/result.go
│   ├── response/inquiry_statement_response.go
│   └── exception/
│       ├── error_code.go
│       └── wf_exception.go
└── client/
    ├── inquiry_statement_client.go
    └── inquiry_statement_integration_test.go
```

### Key Design Points

- `InquiryStatementClient` takes a `*util.WfHttpClient` via constructor
- `PageNumber` starts from 1; `PageSize` is fixed at 10 per WF spec
- `StartTime` / `EndTime` use ISO 8601 format: `2024-01-01T00:00:00+08:00`
- Response `StatementRecords` is `[]domain.StatementRecord` with pagination fields

### How to Generate

1. Ask user for: project path, Go module name, clientId, privateKeyPath, publicKeyPath
2. Generate code based on user's actual project structure and module path
3. Template files under `template/golang/` are for reference only
