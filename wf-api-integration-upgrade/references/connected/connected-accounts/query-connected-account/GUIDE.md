# Query a Connected Account 接口接入指引

## 接口说明

根据 WorldFirst 分配的账户 ID 查询单个关联账户的完整详情，包括注册状态、法律实体信息、联系信息、协议记录和审核结果等。

## 官方文档

- [query_a_connected_account 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/query_a_connected_account)

## 请求地址

`POST /api/open/v1/accounts/query`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-04-12T10:00:00+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | string | Yes | WorldFirst 分配的唯一商户标识符（Account ID），从 Create a Connected Account 响应或 Webhook 通知中获取 |

### 请求示例

```json
{
  "id": "2088000000000001"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `id` | string | WorldFirst 分配的唯一商户标识符（Account ID） |
| `referenceAccountId` | string | 集成商侧的商户标识符 |
| `status` | string | 账户注册状态（AccountStatus 枚举） |
| `registrationRegion` | string | 注册国家/地区（ISO 3166-1 alpha-2 代码） |
| `registrationLegalName` | string | 注册的法律实体名称 |
| `legalEntityInfo` | LegalEntityInfo | 法律实体信息，SDK 注册但尚未提交 KYC 的账户可能为 `null` |
| `contact` | Contact | 联系信息 |
| `agreements` | Agreements | 协议记录 |
| `reasonCode` | string | 审核原因代码，仅在 `status` 为 `REJECT` 时返回 |
| `auditDetails` | array[AuditDetail] | 审核拒绝详情，仅在 `status` 为 `REJECT` 时返回，最多 50 条 |
| `createdAt` | datetime | 账户创建时间（ISO 8601 格式） |
| `updatedAt` | datetime | 账户最后更新时间（ISO 8601 格式） |

### LegalEntityInfo Object

| Field | Type | Description |
|-------|------|-------------|
| `legalEntityType` | string | 法律实体类型：`INDIVIDUAL`（个人）或 `COMPANY`（企业/公司） |
| `company` | Company | 公司法律实体详情，当 legalEntityType 为 COMPANY 时返回 |
| `businessProfile` | BusinessProfile | 商业档案，包括主要业务活动和经营地址 |
| `relatedParties` | array[RelatedParty] | 关联方列表（UBO、董事、法定代表人等） |

### Company Object

| Field | Type | Description |
|-------|------|-------------|
| `companyType` | string | 公司类型（CompanyType 枚举） |
| `registrationNumber` | string | 公司注册号码 |
| `registrationDate` | string | 公司注册日期（ISO 8601 格式） |
| `registrationCountry` | string | 公司注册国家/地区（ISO 3166-1 alpha-2 代码） |

### BusinessProfile Object

| Field | Type | Description |
|-------|------|-------------|
| `mainBusinessActivity` | string | 主要业务活动描述 |
| `businessAddress` | string | 经营地址 |

### RelatedParty Object

| Field | Type | Description |
|-------|------|-------------|
| `relationship` | string | 关联方关系类型（Relationship 枚举） |
| `name` | string | 关联方姓名 |
| `certificateType` | string | 证件类型（CertificateType 枚举） |
| `certificateNumber` | string | 证件号码 |

### Contact Object

| Field | Type | Description |
|-------|------|-------------|
| `contactEmail` | string | 联系邮箱地址 |
| `contactPhone` | string | 联系电话号码 |

### Agreements Object

| Field | Type | Description |
|-------|------|-------------|
| `agreedToTermsAndConditions` | boolean | 是否同意平台服务协议 |
| `agreedToDataUsage` | boolean | 是否同意数据使用授权 |

### AuditDetail Object

| Field | Type | Description |
|-------|------|-------------|
| `reasonCode` | string | 拒绝原因代码 |
| `reasonMessage` | string | 拒绝原因描述 |
| `supplementaryMaterial` | string | 需要补充的材料说明 |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "id": "2088000000000001",
  "referenceAccountId": "PARTNER_MERCHANT_001",
  "registrationRegion": "GB",
  "registrationLegalName": "Example Trading Ltd.",
  "contact": {
    "contactEmail": "info@example-trading.com",
    "contactPhone": "+442071234567"
  },
  "agreements": {
    "agreedToTermsAndConditions": true,
    "agreedToDataUsage": true
  },
  "status": "SUCCESS",
  "createdAt": "2026-04-12T10:00:00+08:00",
  "updatedAt": "2026-04-13T15:30:00+08:00"
}
```

## 错误码

| resultCode | resultStatus | 说明 |
|------------|--------------|------|
| `SUCCESS` | S | 查询成功 |
| `PARAM_ILLEGAL` | F | 参数非法，检查 `id` 是否为有效的账户标识符 |
| `ACCOUNT_NOT_EXIST` | F | 指定的账户不存在，确认 `id` 值后重试 |
| `ACCESS_DENIED` | F | 访问被拒绝，只能查询自己名下的账户信息，检查 `account-id` 或 `access-token` 头 |

## 示例代码

参考 [references/connected/connected-accounts/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
connected-accounts/java/
├── service/
│   └── ConnectedAccountService.java                   # 薄封装 Service，包含 queryConnectedAccount 方法
└── model/
    ├── domain/
    │   ├── LegalEntityInfo.java                       # 法律实体信息
    │   ├── Company.java                               # 公司实体详情
    │   ├── BusinessProfile.java                       # 商业档案
    │   ├── RelatedParty.java                          # 关联方
    │   ├── Contact.java                               # 联系信息
    │   ├── Agreements.java                            # 协议记录
    │   └── AuditDetail.java                           # 审核拒绝详情
    ├── request/
    │   └── QueryConnectedAccountRequest.java          # 请求参数（id）
    └── response/
        └── QueryConnectedAccountResponse.java         # 响应结果（result + 完整账户详情）
```

## 集成使用方式

ConnectedAccountService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `QueryConnectedAccountRequest` 设置账户 ID
2. 调用 `ConnectedAccountService.queryConnectedAccount(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理账户详情

### 业务代码示例

```java
// 构造请求
QueryConnectedAccountRequest request = new QueryConnectedAccountRequest();
request.setId("2088000000000001");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
QueryConnectedAccountResponse response = accountService.queryConnectedAccount(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("账户 ID: " + response.getId());
    System.out.println("状态: " + response.getStatus());
    System.out.println("注册地区: " + response.getRegistrationRegion());
    System.out.println("法律实体名称: " + response.getRegistrationLegalName());
    
    // 处理法律实体信息（SDK 注册但尚未提交 KYC 的账户可能为 null）
    if (response.getLegalEntityInfo() != null) {
        LegalEntityInfo entityInfo = response.getLegalEntityInfo();
        System.out.println("实体类型: " + entityInfo.getLegalEntityType());
        
        if ("COMPANY".equals(entityInfo.getLegalEntityType()) && entityInfo.getCompany() != null) {
            Company company = entityInfo.getCompany();
            System.out.println("公司类型: " + company.getCompanyType());
            System.out.println("注册号码: " + company.getRegistrationNumber());
        }
        
        // 处理关联方信息
        if (entityInfo.getRelatedParties() != null) {
            for (RelatedParty party : entityInfo.getRelatedParties()) {
                System.out.println("关联方: " + party.getName() + " (" + party.getRelationship() + ")");
            }
        }
    }
    
    // 处理联系信息
    if (response.getContact() != null) {
        System.out.println("联系邮箱: " + response.getContact().getContactEmail());
        System.out.println("联系电话: " + response.getContact().getContactPhone());
    }
    
    // 处理审核拒绝信息
    if ("REJECT".equals(response.getStatus())) {
        System.out.println("拒绝原因代码: " + response.getReasonCode());
        if (response.getAuditDetails() != null) {
            for (AuditDetail detail : response.getAuditDetails()) {
                System.out.println("拒绝详情: " + detail.getReasonMessage());
                System.out.println("需要补充: " + detail.getSupplementaryMaterial());
            }
        }
    }
} else {
    // 处理错误
    System.err.println("查询失败: " + response.getResult().getResultMessage());
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 ConnectedAccountService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new ConnectedAccountService(config)` 创建实例。
