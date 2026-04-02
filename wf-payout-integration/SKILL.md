---
name: wf-payout-integration
description: Generate Java or Golang integration code for WorldFirst (WF) createPayout and inquiryPayout APIs for payout to third-party bank cards with RSA256 signing. Use when implementing WF fund payout, integrating createPayout API, querying payout status via inquiryPayout, or handling WF transfer to external bank accounts.
---

# WF createPayout & inquiryPayout API Integration

Generate production-ready Java code for integrating with WorldFirst createPayout and inquiryPayout APIs.

## Prerequisites

- Java 8 (禁止使用 Java 9+ 专有语法)
- WF Client ID and RSA key pair
- WF API endpoint URL

## Sub-skills

Generate the following components using their dedicated skills:

- **WfConfig.java** — invoke `wf-config` skill (含向用户询问 clientId / base-url 交互)
- **WfSigner.java** — invoke `wf-rsa256-signer` skill
- **WfHttpClientUtil.java** — invoke `wf-http-client` skill

---

## API 1: createPayout

**Endpoint**: `POST /amsin/api/v1/business/fund/createPayout`

**Authentication**: RSA256 Signature — see [wf-rsa256-signer skill](~/.qoder/skills/wf-rsa256-signer/SKILL.md)

### Request Headers

| Header | Required | Description |
|--------|----------|-------------|
| `Client-Id` | Yes | WF client identifier |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<base64>` |
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Request-Time` | Yes | ISO 8601, e.g. `2019-04-04T12:08:56+08:00` |
| `Connected-AccountId` | Conditional | Required when platform customer operates merchant account |

### Request Parameters

#### Root Level

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `transferRequestId` | String | **Yes** | Integrator-defined unique transfer ID. **Idempotent key**. Max 64 chars. |
| `transferFromDetail` | Object | **Yes** | **必填** Payer transfer detail. 必须指定 `transferFromAmount.currency` 来告诉 WF 从哪个币种扣款。 |
| `transferToDetail` | Object | **Yes** | Payee transfer detail (contains `transferToAmount`). |
| `businessSceneCode` | String | Conditional | Transfer business type. **Required** when `transferToDetail.transferToAmount.currency` = `CNY`. Values: `THIRD_PARTY_PAYOUT`, `SAME_NAME_PAYOUT` |

> **接口约束**:
> - `transferFromDetail` 是必填项，必须指定 `transferFromAmount.currency` 来确定扣款币种
> - `transferFromAmount.value` 和 `transferToAmount.value` 不能同时指定（二选一）
> - 当指定收款方金额时，`transferFromAmount` 只传 `currency`，`value` 设为 null，WF 根据手续费/汇率自动计算付款方扣款金额
> - 当指定付款方金额时，`transferToAmount` 只传 `currency`，`value` 设为 null，WF 自动计算收款方到账金额

#### Nested Objects

完整嵌套对象定义见 [field-reference.md](field-reference.md)：

| Object | Description |
|--------|-------------|
| `Amount` | 金额对象，含 `currency` 和 `value`（最小货币单位） |
| `TransferFromDetail` | 付款方详情，含 `transferFromAmount` 和 `transferFromMethod` |
| `TransferToDetail` | 收款方详情，含 `transferToAmount`、`transferToMethod`、`transferQuote`、`purposeCode` |
| `TransferToMethod` | 转账方式，区分卡详情模式 (`BANK_ACCOUNT_DETAIL`) 和 token 模式 (`BENEFICIARY_TOKEN`) |
| `PaymentMethodMetaData` | 银行卡元数据，卡详情模式必填 |
| `TransferQuote` | 汇率报价，跨币种时使用 |

### createPayout Response Fields

| Field | Type | Condition | Description |
|-------|------|-----------|-------------|
| `result` | Object | Always | `resultStatus` (S/F/U), `resultCode`, `resultMessage` |
| `transferRequestId` | String | S | Integrator-defined request ID echo-back |
| `transferId` | String | S | WF-generated transfer ID (max 64 chars) |
| `chargeMode` | String | S | Fee charge mode: `INNER_DEDUCT` or `OUTER_DEDUCT` |
| `transferFromDetail` | TransferFromDetail | S | Actual payer amount and method detail |
| `transferToDetail` | TransferToDetail | S | Actual payee amount, fee, and method detail |

### createPayout Important: PROCESSING Result

When `result.resultStatus=S` and `result.resultCode=PROCESSING`, the transfer is **still in progress**.
Caller **MUST** use `inquiryPayout` to poll the final status.

### createPayout Error Codes

#### Non-retryable (resultStatus=F)

| Code | Handling |
|------|----------|
| `PARAM_ILLEGAL` | Check request parameters |
| `PROCESS_FAIL` | General business failure, do not retry |
| `INVALID_API` | API invalid/inactive |
| `INVALID_CLIENT` | Client ID invalid |
| `INVALID_SIGNATURE` | Signature invalid |
| `METHOD_NOT_SUPPORTED` | Ensure HTTP method is POST |
| `UN_SUPPORT_BUSINESS` | Unsupported business; check currency/params |
| `USER_NO_PERMISSION` | User has no permission |
| `CARD_INFO_NOT_MATCH` | Card info mismatch, use different card |
| `CURRENCY_NOT_SUPPORT` | Unsupported currency |
| `USER_NOT_EXIST` | User not found |
| `USER_ACCOUNT_ABNORMAL` | Account status abnormal |
| `REPEAT_REQ_INCONSISTENT` | Idempotent violation; same transferRequestId, different body |
| `USER_STATUS_ABNORMAL` | User status abnormal |
| `BALANCE_NOT_ENOUGH` | Insufficient balance |
| `AMOUNT_EXCEED_LIMIT` | Amount exceeds limit |
| `CONTRACT_NOT_EXIST` | Contract does not exist |
| `CONTRACT_CHECK_FAIL` | Contract check failed |
| `QUOTE_EXPIRED` | Quote expired; re-create quote first |
| `AVAILABLE_QUOTA_NOT_ENOUGH` | Payout quota insufficient |

#### Retryable (resultStatus=U) — max 7 retries, exponential backoff: 5/10/20/40/80/160/320 min

| Code | Handling |
|------|----------|
| `UNKNOWN_EXCEPTION` | Retry |
| `REQUEST_TRAFFIC_EXCEED_LIMIT` | Retry |
| `FEE_EXCEPTION` | Fee issue, check params then retry |

---

## API 2: inquiryPayout

**Endpoint**: `POST /amsin/api/v1/business/fund/inquiryPayout`

### Request Parameters

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `transferId` | String | Conditional | WF-generated payout ID. Cannot be empty if `transferRequestId` is empty. |
| `transferRequestId` | String | Conditional | Integrator-defined request ID. Max 64 chars. Cannot be empty if `transferId` is empty. |

> `transferId` 与 `transferRequestId` 不能同时为空，二选一传入即可。

### inquiryPayout Response Fields

| Field | Type | Condition | Description |
|-------|------|-----------|-------------|
| `result` | Object | Always | API 调用级别结果：`resultStatus` (S/F/U), `resultCode`, `resultMessage` |
| `transferResult` | Object | result.S | **代发单级别结果**：见 [field-reference.md](field-reference.md) |
| `transferRequestId` | String | result.S | Integrator-defined request ID |
| `transferId` | String | result.S | WF-generated transfer ID |
| `transferFinishTime` | String | result.S | Transfer completion time, ISO 8601 |
| `chargeMode` | String | result.S | Fee charge mode: `INNER_DEDUCT` or `OUTER_DEDUCT` |
| `transferFromDetail` | TransferFromDetail | result.S | Payer actual deduction details |
| `transferToDetail` | TransferToDetail | result.S | Payee actual receipt details |

> **两层结果区分**：
> - `result.resultStatus=S` → 本次查询 API 调用成功，继续查看 `transferResult`
> - `transferResult.resultCode=SUCCESS` → 代发单最终成功
> - `transferResult.resultCode=PROCESSING` → 代发仍在处理中，继续轮询
> - `transferResult.resultStatus=F` → 代发失败

### inquiryPayout Error Codes

#### API Level (result.resultCode)

| Code | Handling |
|------|----------|
| `PARAM_ILLEGAL` | Check request parameters |
| `INVALID_API` / `INVALID_CLIENT` / `INVALID_SIGNATURE` | Fix config |
| `PROCESS_FAIL` | Do not retry; contact support |
| `UNKNOWN_EXCEPTION` / `REQUEST_TRAFFIC_EXCEED_LIMIT` | Retry |

#### Transfer Level (transferResult.resultCode)

| Code | Handling |
|------|----------|
| `SUCCESS` | Transfer completed successfully |
| `PROCESSING` | Still in progress; continue polling |
| `ORDER_NOT_FOUND` | Retry with correct transferId/transferRequestId |
| `ORDER_IS_CLOSED` | Order closed; do not retry |
| `ORDER_IS_REVERSED` | Order reversed; do not retry |
| `ORDER_NOT_EXIST` | Fix parameters |
| `PROCESS_FAIL` / `USER_ACCOUNT_ABNORMAL` / `USER_STATUS_ABNORMAL` / `BALANCE_NOT_ENOUGH` / `RISK_REJECT` | Contact support or fix account |

---

## Package Structure

```
{basePackage}.wf
├── model/
│   ├── request/    CreatePayoutRequest.java              ← createPayout 生成
│   │               InquiryPayoutRequest.java             ← inquiryPayout 生成
│   ├── response/   CreatePayoutResponse.java             ← createPayout 生成
│   │               InquiryPayoutResponse.java            ← inquiryPayout 生成
│   │               Result.java                           ← 共享，仅首次生成，已存在则复用
│   ├── domain/     Amount.java                           ← 共享
│   │               TransferFromDetail.java               ← 共享
│   │               TransferFromMethod.java               ← 共享
│   │               TransferToDetail.java                 ← 共享
│   │               TransferToMethod.java                 ← 共享
│   │               PaymentMethodMetaData.java            ← 共享
│   │               TransferQuote.java                    ← 共享
│   │               BeneficiaryInfo.java                  ← 共享
│   │               TransferResult.java                   ← inquiryPayout 生成（代发单级别结果）
│   └── exception/  WfException.java, WfErrorCode.java    ← 共享；新接口错误码追加到 WfErrorCode
├── client/   PayoutClient.java                          ← 统一代发客户端（createPayout + inquiryPayout）
├── config/   WfConfig.java                              ← 共享，复用
├── signer/   WfSigner.java                              ← 共享，复用
└── util/     WfHttpClientUtil.java                      ← 共享，复用
```

## Alibaba Java Coding Standards

- CamelCase classes, lowerCamelCase variables, UPPER_SNAKE_CASE constants
- Same-line opening brace, 4-space indent, max 120 chars/line
- Javadoc on all public methods, SLF4J logging

---

## PayoutClient

统一封装 createPayout 和 inquiryPayout 两个接口，通过不同方法区分。Handles business logic only — no direct HTTP code. Delegates to `WfHttpClientUtil`.

### Key behaviors

#### createPayout()

1. **Validate**:
   - `transferRequestId` must not be blank, max 64 chars
   - `transferFromDetail` must not be null; `transferFromAmount.currency` must not be blank
   - `transferToDetail` must not be null; `transferToAmount.currency` must not be blank
   - `transferFromAmount.value` 与 `transferToAmount.value` 不能同时指定，二选一
   - Card detail mode: `paymentMethodType=BANK_ACCOUNT_DETAIL`，`paymentMethodMetaData.bankAccountNo` 必填
   - Token mode: `paymentMethodType=BENEFICIARY_TOKEN`，`paymentMethodId` 必填，不能同时传 `paymentMethodMetaData`
   - When `transferToAmount.currency=CNY`, `businessSceneCode` must not be blank
   - `purposeCode` 必填，默认自动填充 `GDS`
2. **Call** `httpClientUtil.sendPostRequest(url, PATH_CREATE_PAYOUT, body)`
3. **Handle resultStatus**: `S` → return; `F`/`U` → throw `WfException`

#### inquiryPayout()

1. **Validate**: `transferId` 与 `transferRequestId` 至少一个不为空
2. **Call** `httpClientUtil.sendPostRequest(url, PATH_INQUIRY_PAYOUT, body)`
3. **Handle API resultStatus**: `S` → return response（调用方自行判断 `transferResult`）；`F`/`U` → throw `WfException`
4. **Caller 判断 transferResult**：
   - `transferResult.isSuccess()` → 代发成功
   - `transferResult.isProcessing()` → 继续轮询
   - 其他 → 代发失败，读取 `resultCode`

```java
public void init() {
    this.httpClientUtil = new WfHttpClientUtil(config);
    this.signer = httpClientUtil.getSigner();
}

public void setHttpClientUtil(WfHttpClientUtil httpClientUtil) {
    this.httpClientUtil = httpClientUtil;
    if (httpClientUtil != null) { this.signer = httpClientUtil.getSigner(); }
}
```

---

## PayoutClientTest

### Pre-Generation Question: Signature Mode (MUST ASK)

Before generating test code, you MUST ask the user:

**Question**: 生成测试类时，签名逻辑使用哪种模式？

| Option | Description |
|--------|-------------|
| **Mock 签名（跳过验签）** | Mock WfSigner 的 `generateSignature` 方法固定返回 `"TESTING_SIGNATURE"`。WF 会返回 `INVALID_SIGNATURE`，适用于快速验证请求格式。 |
| **真实签名（使用密钥文件）** | 使用 WfConfig 中配置的真实私钥和公钥路径，可完整跑通接口。 |

#### If user selects Mock 签名:

```java
WfSigner mockSigner = Mockito.mock(WfSigner.class);
Mockito.when(mockSigner.generateSignature(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
    .thenReturn("TESTING_SIGNATURE");
Mockito.when(mockSigner.verifySignature(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
    .thenReturn(true);

WfHttpClientUtil httpClientUtil = new WfHttpClientUtil(mockConfig, mockSigner);
client = new PayoutClient(mockConfig);
client.setHttpClientUtil(httpClientUtil);
```

#### If user selects 真实签名:

```java
// 直接使用 WfConfig 中已配置的密钥路径
WfConfig config = new WfConfig();
client = new PayoutClient(config);
client.init();
```

### 文件位置

```
app/test/src/test/java/com/ipay/ibizopenprod/common/service/integration/wf/PayoutClientTest.java
```

### 测试方法

| 方法 | 说明 |
|------|------|
| `testCreatePayoutCardDetail` | 卡详情模式，指定 transferToAmount，transferFromAmount 只传 currency |
| `testCreatePayoutTokenMode` | token 模式，paymentMethodType=BENEFICIARY_TOKEN，paymentMethodId=beneficiaryToken |
| `testCreatePayoutFromAmount` | 指定 transferFromAmount（含 value），transferToAmount 只传 currency |
| `testInquiryPayoutByRequestId` | 按 transferRequestId 查询，将 createPayout 时用的 transferRequestId 填入 |
| `testInquiryPayoutByTransferId` | 按 transferId 查询，将 createPayout 响应返回的 transferId 填入 |

#### 默认测试卡信息（卡详情模式）

```java
metaData.setBankAccountName("vaL2LTest");
metaData.setBankAccountNo("100100004623");
metaData.setBankName("STARK bankName");
metaData.setBankBIC("CITIHKHX");
metaData.setBankCountryCode("HK");
metaData.setBeneficiaryType("THIRD_PARTY_PERSONAL_BANK_ACCOUNT");
```

---

## Checklist

### createPayout
- [ ] Generate WfConfig (use **wf-config** skill)
- [ ] Generate domain model classes: Amount, TransferFromDetail, TransferToDetail, TransferToMethod, PaymentMethodMetaData, TransferQuote, TransferFromMethod, BeneficiaryInfo
- [ ] Generate CreatePayoutRequest (`wf.model.request`)
- [ ] Generate CreatePayoutResponse (`wf.model.response`)
- [ ] **Reuse** `Result.java` from `{basePackage}.wf.model.response`
- [ ] **Reuse** WfException / WfErrorCode（已存在则追加错误码，不重复生成）
- [ ] Generate WfSigner (use **wf-rsa256-signer** skill)
- [ ] Generate WfHttpClientUtil (use **wf-http-client** skill)
- [ ] Generate PayoutClient (`wf.client/`) with `createPayout()` method

### inquiryPayout
- [ ] Generate TransferResult domain class (`wf.model.domain`)
- [ ] Generate InquiryPayoutRequest (`wf.model.request`)
- [ ] Generate InquiryPayoutResponse (`wf.model.response`)
- [ ] **Append** inquiryPayout error codes to WfErrorCode: `ORDER_NOT_FOUND`, `ORDER_IS_CLOSED`, `ORDER_IS_REVERSED`, `ORDER_NOT_EXIST`
- [ ] **Add** `inquiryPayout()` method to existing PayoutClient (`wf.client/`)

### Test
- [ ] Generate PayoutClientTest with 5 test methods covering both APIs（询问签名模式后生成）

---

## Common Issues

1. **transferFromDetail 必填**: 必须指定 `transferFromAmount.currency`，告知 WF 从哪个币种扣款
2. **value 互斥**: `transferFromAmount.value` 与 `transferToAmount.value` 不能同时指定，二选一
3. **Idempotency**: `transferRequestId` 是幂等键，相同 ID + 不同 body → `REPEAT_REQ_INCONSISTENT`
4. **PROCESSING status**: createPayout 响应 `resultCode=PROCESSING` 时必须调用 inquiryPayout 轮询最终状态
5. **CNY businessSceneCode**: 收款币种为 CNY 时必填，三方卡用 `THIRD_PARTY_PAYOUT`
6. **Amount format**: `value` 为 Long，最小货币单位。2 位小数币种 × 100，0 位小数币种 × 1
7. **轮询策略**: transferResult.resultCode=PROCESSING 时，最多 7 次，指数退避（5/10/20/40/80/160/320 分钟）
8. **超时处理**: createPayout 调用超过 2 小时无结果且 inquiryPayout 返回 UNKNOWN，联系 WF 支持

---

## Java Template

Pre-built Java implementation is available under `template/java/`. Use this when the user requests Java code.

**Note**: All Java template files use `{basePackage}` placeholder for the package prefix. Before generating code, ask user for their base package (e.g., `com.example.project`), then replace `{basePackage}` with the actual value.

### Template Structure

```
template/java/
├── model/
│   ├── domain/
│   │   ├── Amount.java
│   │   ├── TransferFromDetail.java
│   │   ├── TransferToDetail.java
│   │   ├── TransferToMethod.java
│   │   ├── PaymentMethodMetaData.java
│   │   ├── TransferQuote.java
│   │   ├── TransferFromMethod.java
│   │   ├── BeneficiaryInfo.java
│   │   ├── BankAccountDetail.java
│   │   └── TransferResult.java
│   ├── request/
│   │   ├── CreatePayoutRequest.java
│   │   └── InquiryPayoutRequest.java
│   ├── response/
│   │   ├── Result.java
│   │   ├── CreatePayoutResponse.java
│   │   └── InquiryPayoutResponse.java
│   └── exception/
│       ├── WfErrorCode.java
│       └── WfException.java
└── client/
    ├── PayoutClient.java
    └── PayoutClientTest.java
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
│   ├── domain/payout.go                      # Amount, TransferFromDetail, TransferToDetail, etc.
│   ├── request/create_payout_request.go
│   ├── request/inquiry_payout_request.go
│   ├── response/result.go
│   ├── response/create_payout_response.go
│   ├── response/inquiry_payout_response.go
│   └── exception/
│       ├── error_code.go
│       └── wf_exception.go
└── client/
    ├── payout_client.go
    └── payout_integration_test.go
```

### Key Design Points

- `PayoutClient` takes a `*util.WfHttpClient` via constructor (dependency injection)
- `CreatePayout` validates request and handles `S`/`F`/`U` result statuses
- `InquiryPayout` returns `*InquiryPayoutResponse` — caller checks `resp.TransferResult.IsSuccess()` / `IsProcessing()`
- `Amount.Value` is `*int64` in minor units (USD 1.00 = 100)
- Integration test uses real credentials; replace `wfClientID`, key paths with actual values

### Module Path

```
module code.alipay.com/AntCloudQuality/golangbs
go 1.23
```

### How to Generate

1. Ask user for: project path, Go module name, clientId, privateKeyPath, publicKeyPath
2. Generate code based on user's actual project structure and module path
3. Template files under `template/golang/` are for reference only
