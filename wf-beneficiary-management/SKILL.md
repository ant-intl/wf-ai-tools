---
name: wf-beneficiary-management
description: "[user] Generate Java or Golang integration code for WorldFirst (WF) beneficiary management APIs including inquiryBeneficiaryTemplate, bindBeneficiary, removeBeneficiary, editBeneficiary, and inquiryBeneficiaryList. All methods share a single BeneficiaryManagementClient. Before generating code, asks whether user needs card detail mode (only inquiryBeneficiaryTemplate) or token mode (full suite)."
---

# WF Beneficiary Management APIs Integration

Generate production-ready Java code for integrating with WorldFirst beneficiary management APIs.
All 5 interfaces share a single `BeneficiaryManagementClient` class for unified access.

## Prerequisites

- Java 8+
- WF Client ID and RSA key pair
- Shared infrastructure: WfConfig, WfSigner, WfHttpClientUtil (use respective skills if not present)

## Pre-Generation Questions (MUST ASK BEFORE GENERATING CODE)

Before generating any code, you MUST ask the user the following question to determine which APIs to generate:

**Question**: 请问您的 Payout 集成场景是使用卡详情模式还是卡 token 模式？

| Option | Description |
|--------|-------------|
| **卡详情模式** | 每次代发时直接传递银行卡详情信息，不需要提前绑定收款人。只需要集成 `inquiryBeneficiaryTemplate` 接口来查询卡模版字段要求即可。 |
| **卡 token 模式** | 先通过 `bindBeneficiary` 绑定收款人获取 `beneficiaryToken`，后续代发时使用 token。需要集成完整的收款人管理接口套件。 |

### If user selects 卡详情模式 (Card Detail Mode):

Only generate the following:
- `InquiryBeneficiaryTemplateRequest.java`
- `InquiryBeneficiaryTemplateResponse.java`
- `CardTemplateField.java` (if not already present)
- `BeneficiaryManagementClient.java` — only include `inquiryBeneficiaryTemplate()` method
- `BeneficiaryManagementClientTest.java` — only include `testInquiryBeneficiaryTemplate()` test method

Skip all bind/remove/edit/list related code (domain models, request/response classes, error codes, test methods).
This saves token consumption significantly.

### If user selects 卡 token 模式 (Token Mode):

Generate the full beneficiary management suite — all 5 APIs, all domain models, all request/response classes, error codes, and test methods as documented below.

---

## Sub-skills

- **WfConfig.java** — invoke `wf-config` skill
- **WfSigner.java** — invoke `wf-rsa256-signer` skill
- **WfHttpClientUtil.java** — invoke `wf-http-client` skill

---

## API 1: inquiryBeneficiaryTemplate (查询卡模版)

**Endpoint**: `POST /amsin/api/v1/business/account/inquiryBeneficiaryTemplate`

用于查询指定国家/币种/账户类型的卡模版信息，确定绑定收款人时需要传递哪些字段。

### Request Parameters

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `countryCode` | String | Conditional | ISO-3166 2-letter, max 2 chars |
| `currency` | String | Conditional | ISO-4217 3-letter code |
| `beneficiaryType` | String | Conditional | 账户类型 (见 [field-reference.md](field-reference.md)) |

### Response Parameters

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API result |
| `responseId` | String | Unique response ID, max 32 chars |
| `cardTemplateData` | List\<CardTemplateField\> | 标准卡模版字段列表 (见 [field-reference.md](field-reference.md)) |
| `localCardTemplateData` | List\<CardTemplateField\> | 本地清算网络模版 |
| `crossBorderCardTemplateData` | List\<CardTemplateField\> | 跨境清算网络模版 |

---

## API 2: bindBeneficiary (绑定收款人)

**Endpoint**: `POST /amsin/api/v1/business/account/bindBeneficiary`

用于绑定收款人到当前 WF 账户。

### Request Parameters

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `bindBeneficiaryRequestId` | String | **Yes** | 幂等请求ID, max 64 chars |
| `beneficiaryType` | String | **Yes** | 账户类型 (见 [field-reference.md](field-reference.md)) |
| `beneficiaryBankAccount` | BeneficiaryBankAccount | Conditional | 银行账户信息 (见 [field-reference.md](field-reference.md)) |
| `beneficiaryAlipayAccount` | BeneficiaryAlipayAccount | Conditional | 支付宝账户信息 (见 [field-reference.md](field-reference.md)) |
| `thirdPartyIdentity` | ThirdPartyIdentity | Conditional | 三方身份信息 (见 [field-reference.md](field-reference.md)) |
| `countryCode` | String | Conditional | ISO-3166 2-letter |
| `currency` | String | Conditional | ISO-4217 3-letter |
| `beneficiaryNick` | String | Conditional | 收款人昵称, max 70 chars |
| `templateCategory` | String | No | 模版类型: `GENERAL_TEMPLATE`(默认), `LOCAL_TEMPLATE`, `CROSS_BORDER_TEMPLATE` |
| `referenceBeneficiaryId` | String | No | 集成商自定义ID, max 64 chars |

### Response Parameters

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API result |
| `beneficiary` | Beneficiary | 绑定的收款人信息（含 `beneficiaryToken`，见 [field-reference.md](field-reference.md)) |

---

## API 3: removeBeneficiary (删除收款人)

**Endpoint**: `POST /amsin/api/v1/business/account/removeBeneficiary`

用于删除已绑定的收款人。

### Request Parameters

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `removeBeneficiaryRequestId` | String | **Yes** | 幂等请求ID, max 64 chars |
| `beneficiaryToken` | String | **Yes** | 收款人令牌 (Base64), max 128 chars |

### Response Parameters

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API result |
| `beneficiaryToken` | String | 被删除的收款人令牌 |

---

## API 4: editBeneficiary (编辑收款人)

**Endpoint**: `POST /amsin/api/v1/business/account/editBeneficiary`

用于修改收款人昵称。

### Request Parameters

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `beneficiaryToken` | String | **Yes** | 收款人令牌 (Base64), max 128 chars |
| `beneficiaryNick` | String | **Yes** | 新昵称, max 70 chars |

### Response Parameters

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API result |
| `beneficiaryToken` | String | 收款人令牌 |

---

## API 5: inquiryBeneficiaryList (查询收款人列表)

**Endpoint**: `POST /amsin/api/v1/business/account/inquiryBeneficiaryList`

用于分页查询已绑定的收款人列表。

### Request Parameters

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `pageSize` | Integer | **Yes** | 每页条数, max 50 |
| `pageNumber` | Integer | **Yes** | 页码 (1, 2, 3...) |
| `beneficiaryToken` | String | No | 收款人令牌 (精确匹配) |
| `beneficiaryNick` | String | No | 收款人昵称 (模糊匹配) |
| `referenceBeneficiaryId` | String | No | 集成商自定义ID |
| `bankAccountNo` | String | No | 银行账号 (模糊匹配) |
| `bankAccountIBAN` | String | No | IBAN |
| `currencyList` | List\<String\> | No | 币种过滤 |
| `accountName` | String | No | 账户名称 (模糊匹配) |
| `assetType` | String | No | `BANK_ACCOUNT` 或 `ALIPAY_ACCOUNT` |
| `relationFilter` | List\<String\> | No | `SAME_NAME` 或 `THIRD_PARTY` |

### Response Parameters

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API result |
| `responseId` | String | 响应ID |
| `beneficiaries` | List\<Beneficiary\> | 收款人列表 (见 [field-reference.md](field-reference.md)) |
| `totalCount` | Integer | 总条数 |
| `totalPageNumber` | Integer | 总页数 |
| `currentPageNumber` | Integer | 当前页码 |

---

## Error Codes

### System Codes

| Code | Status | Handling |
|------|--------|----------|
| `SUCCESS` | S | Success |
| `PROCESS_FAIL` | F | Contact support |
| `PARAM_ILLEGAL` | F | Check parameters |
| `UNKNOWN_EXCEPTION` | U | Retry (7x, exponential backoff) |
| `REQUEST_TRAFFIC_EXCEED_LIMIT` | U | Retry |
| `OAUTH_FAIL` | F | Auth failed |
| `INVALID_API` | F | API invalid |
| `INVALID_CLIENT` | F | Client ID invalid |
| `INVALID_SIGNATURE` | F | Signature invalid |
| `METHOD_NOT_SUPPORTED` | F | Use POST |

### Business Codes

| Code | Status | API | Description |
|------|--------|-----|-------------|
| `UN_SUPPORT_BUSINESS` | F | All | Unsupported business |
| `USER_NOT_EXIST` | F | All | User not found |
| `CARD_TEMPLATE_NOT_EXIST` | F | Bind | No template matches |
| `BENEFICIARY_ALREADY_EXISTED` | F | Bind | Beneficiary already exists |
| `REPEAT_BIND_BENEFICIARY_REQUEST` | U | Bind | Duplicate bind request |
| `EXCEED_MAX_COUNT_LIMIT` | F | Bind | Exceeded max beneficiaries |
| `USER_NO_PERMISSION` | F | Bind | No permission |
| `REFERENCE_BENEFICIARY_ID_EXIST` | F | Bind | Reference ID already exists |
| `RISK_REJECT` | F | Bind | Risk rejection |
| `BENEFICIARY_NOT_EXIST` | F | Remove/Edit | Beneficiary not found |
| `REPEAT_REQ_INCONSISTENT` | F | Remove | Inconsistent repeat request |

---

## Package Structure

```
{basePackage}.wf
├── model/
│   ├── domain/
│   │   ├── CardTemplateField.java
│   │   ├── Beneficiary.java
│   │   ├── BeneficiaryBankAccount.java    ← 复用 PaymentMethodMetaData
│   │   ├── BeneficiaryAlipayAccount.java
│   │   ├── ThirdPartyIdentity.java
│   │   └── Address.java
│   ├── request/
│   │   ├── InquiryBeneficiaryTemplateRequest.java
│   │   ├── BindBeneficiaryRequest.java
│   │   ├── RemoveBeneficiaryRequest.java
│   │   ├── EditBeneficiaryRequest.java
│   │   └── InquiryBeneficiaryListRequest.java
│   └── response/
│       ├── InquiryBeneficiaryTemplateResponse.java
│       ├── BindBeneficiaryResponse.java
│       ├── RemoveBeneficiaryResponse.java
│       ├── EditBeneficiaryResponse.java
│       └── InquiryBeneficiaryListResponse.java
├── client/
│   └── BeneficiaryManagementClient.java   ← 统一客户端，5个方法
├── config/   WfConfig.java
├── signer/   WfSigner.java
└── util/     WfHttpClientUtil.java
```

---

## BeneficiaryManagementClient

统一客户端类，包含 5 个方法：

```java
public class BeneficiaryManagementClient {
    
    // 1. 查询卡模版
    public InquiryBeneficiaryTemplateResponse inquiryBeneficiaryTemplate(
        InquiryBeneficiaryTemplateRequest request);
    
    // 2. 绑定收款人
    public BindBeneficiaryResponse bindBeneficiary(
        BindBeneficiaryRequest request);
    
    // 3. 删除收款人
    public RemoveBeneficiaryResponse removeBeneficiary(
        RemoveBeneficiaryRequest request);
    
    // 4. 编辑收款人
    public EditBeneficiaryResponse editBeneficiary(
        EditBeneficiaryRequest request);
    
    // 5. 查询收款人列表
    public InquiryBeneficiaryListResponse inquiryBeneficiaryList(
        InquiryBeneficiaryListRequest request);
}
```

---

## Test Class

### Pre-Generation Question: Signature Mode (MUST ASK)

Before generating test code, you MUST ask the user:

**Question**: 生成测试类时，签名逻辑使用哪种模式？

| Option | Description |
|--------|-------------|
| **Mock 签名（跳过验签）** | Mock WfSigner 的 `generateSignature` 方法固定返回 `"TESTING_SIGNATURE"`，跳过真实签名流程。适用于快速联调验证网络连通性和请求/响应格式（WF 会返回 `INVALID_SIGNATURE`）。 |
| **真实签名（使用密钥文件）** | 使用用户提供的私钥和公钥文件路径进行真实签名和验签。需要用户提供 `privateKeyPath` 和 `publicKeyPath`。 |

#### If user selects Mock 签名:

setUp 中 mock WfSigner，通过 `WfHttpClientUtil(config, mockSigner)` 注入：

```java
WfSigner mockSigner = Mockito.mock(WfSigner.class);
Mockito.when(mockSigner.generateSignature(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
    .thenReturn("TESTING_SIGNATURE");
Mockito.when(mockSigner.verifySignature(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
    .thenReturn(true);

WfHttpClientUtil httpClientUtil = new WfHttpClientUtil(mockConfig, mockSigner);
client = new BeneficiaryManagementClient(mockConfig);
client.setHttpClientUtil(httpClientUtil);
```

#### If user selects 真实签名:

setUp 中配置真实密钥路径（需要向用户询问路径），通过 `init()` 正常初始化：

```java
Mockito.when(mockConfig.getPrivateKeyPath()).thenReturn("<用户提供的私钥路径>");
Mockito.when(mockConfig.getPublicKeyPath()).thenReturn("<用户提供的公钥路径>");

client = new BeneficiaryManagementClient(mockConfig);
client.init();
```

---

生成独立的测试类 `BeneficiaryManagementClientTest.java`：

- Mock `WfConfig`
- 根据用户选择的签名模式决定是否 Mock `WfSigner`
- 每个接口生成一个成功调用测试用例

### 默认测试卡信息

使用 USD/HK 三方个人银行卡：

```java
// USD/HK 三方个人银行卡
bankAccountName = "vaL2LTest"
bankAccountNo = "100100004623"
bankName = "STARK bankName"
bankBIC = "CITIHKHX"
bankCountryCode = "HK"
beneficiaryType = "THIRD_PARTY_PERSONAL_BANK_ACCOUNT"
```

---

## WfErrorCode Additions

追加以下错误码到 `WfErrorCode.java`（检查重复后追加）：

```java
// -------------------------------------------------------------------------
// bindBeneficiary 接口错误码
// -------------------------------------------------------------------------
REPEAT_BIND_BENEFICIARY_REQUEST("REPEAT_BIND_BENEFICIARY_REQUEST", "Duplicate bind beneficiary request"),
EXCEED_MAX_COUNT_LIMIT("EXCEED_MAX_COUNT_LIMIT", "Exceeded max beneficiary count limit"),
USER_NO_PERMISSION("USER_NO_PERMISSION", "User has no permission"),
BENEFICIARY_ALREADY_EXISTED("BENEFICIARY_ALREADY_EXISTED", "Beneficiary already exists"),
REFERENCE_BENEFICIARY_ID_EXIST("REFERENCE_BENEFICIARY_ID_EXIST", "Reference beneficiary ID already exists"),
RISK_REJECT("RISK_REJECT", "Risk rejection"),

// -------------------------------------------------------------------------
// removeBeneficiary / editBeneficiary 接口错误码
// -------------------------------------------------------------------------
BENEFICIARY_NOT_EXIST("BENEFICIARY_NOT_EXIST", "Beneficiary not found"),
```

---

## Checklist

- [ ] Generate domain models: Address.java, ThirdPartyIdentity.java, BeneficiaryAlipayAccount.java, Beneficiary.java
- [ ] Generate Request classes: 5 request classes
- [ ] Generate Response classes: 5 response classes
- [ ] **Reuse** CardTemplateField.java and InquiryBeneficiaryTemplateRequest/Response from existing code
- [ ] Generate BeneficiaryManagementClient.java with 5 methods
- [ ] **Append** error codes to WfErrorCode.java (check duplicates first)
- [ ] Generate BeneficiaryManagementClientTest.java with 5 test methods
- [ ] Delete old skill: wf-inquiry-beneficiary-template-integration (merged into this one)
- [ ] Follow Alibaba Java coding standards

---

## Common Issues

1. **CARD_INFO_ABNORMAL**: 绑定收款人时卡信息不符合模版要求，需先调用 `inquiryBeneficiaryTemplate` 获取必填字段
2. **BENEFICIARY_ALREADY_EXISTED**: 该收款人已存在，无需重复绑定
3. **beneficiaryToken**: 删除/编辑/查询时需要使用绑定返回的 token，Base64 编码
4. **分页查询**: `pageSize` 最大 50，`pageNumber` 从 1 开始

---

## Java Template

Pre-built Java implementation is available under `template/java/`. Use this when the user requests Java code.

**Note**: All Java template files use `{basePackage}` placeholder for the package prefix. Before generating code, ask user for their base package (e.g., `com.example.project`), then replace `{basePackage}` with the actual value.

### Template Structure

```
template/java/
├── model/
│   ├── domain/
│   │   ├── CardTemplateField.java
│   │   ├── Beneficiary.java
│   │   ├── BeneficiaryBankAccount.java
│   │   ├── BeneficiaryAlipayAccount.java
│   │   ├── ThirdPartyIdentity.java
│   │   └── Address.java
│   ├── request/
│   │   ├── InquiryBeneficiaryTemplateRequest.java
│   │   ├── BindBeneficiaryRequest.java
│   │   ├── RemoveBeneficiaryRequest.java
│   │   ├── EditBeneficiaryRequest.java
│   │   └── InquiryBeneficiaryListRequest.java
│   ├── response/
│   │   ├── InquiryBeneficiaryTemplateResponse.java
│   │   ├── BindBeneficiaryResponse.java
│   │   ├── RemoveBeneficiaryResponse.java
│   │   ├── EditBeneficiaryResponse.java
│   │   ├── InquiryBeneficiaryListResponse.java
│   │   └── Result.java
│   └── exception/
│       ├── WfErrorCode.java
│       └── WfException.java
└── client/
    ├── BeneficiaryManagementClient.java
    └── BeneficiaryManagementClientTest.java
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
│   ├── domain/beneficiary.go                 # CardTemplateField, BeneficiaryBankAccount,
│   │                                         # BeneficiaryAlipayAccount, ThirdPartyIdentity,
│   │                                         # Address, Beneficiary
│   ├── request/beneficiary_request.go        # All 5 request structs + Validate()
│   ├── response/result.go
│   ├── response/beneficiary_response.go      # All 5 response structs
│   └── exception/
│       ├── error_code.go
│       └── wf_exception.go
└── client/
    ├── beneficiary_management_client.go      # Single client, 5 methods
    └── beneficiary_management_integration_test.go
```

### Key Design Points

- `CardTemplateField` structure matches WF API response:
  ```go
  type FieldRestriction struct {
      RestrictionMsg   string `json:"restrictionMsg,omitempty"`
      RestrictionRegex string `json:"restrictionRegex,omitempty"`
      RestrictionType  string `json:"restrictionType,omitempty"`
  }

  type CardTemplateField struct {
      FieldName        string            `json:"fieldName"`
      FieldDescription string            `json:"fieldDescription,omitempty"`
      Required         string            `json:"required,omitempty"`         // "Y" or "N"
      Restriction      *FieldRestriction `json:"restriction,omitempty"`
  }
  ```
- `BeneficiaryManagementClient` uses a generic helper `handleBeneficiaryResult[T]` (Go 1.18+ generics) to avoid duplicating S/F/U switch logic
- `BindBeneficiaryRequest` uses typed domain structs (`*domain.BeneficiaryBankAccount`, etc.) instead of `interface{}`
- Integration test includes all 5 methods; RemoveBeneficiary and EditBeneficiary require a real `beneficiaryToken` from BindBeneficiary

### Card Detail Mode (卡详情模式)

Only generate:
- `domain/beneficiary.go` (CardTemplateField only)
- `request/beneficiary_request.go` (InquiryBeneficiaryTemplateRequest only)
- `response/beneficiary_response.go` (InquiryBeneficiaryTemplateResponse only)
- `client/beneficiary_management_client.go` (InquiryBeneficiaryTemplate method only)
- Integration test with TestIntegration_InquiryBeneficiaryTemplate only

### How to Generate

1. Ask user for: project path, Go module name, clientId, privateKeyPath, publicKeyPath
2. Ask user: 卡详情模式 or 卡 token 模式?
3. Generate code based on user's actual project structure and module path
4. Template files under `template/golang/` are for reference only
