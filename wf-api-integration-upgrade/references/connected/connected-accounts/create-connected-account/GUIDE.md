# Create a Connected Account 接口接入指引

## 接口说明

注册并入驻新关联商户到 WorldFirst，提交商户的商业信息、联系方式和法律实体数据用于 KYC/KYB 审核。支持企业（COMPANY）和个人（INDIVIDUAL）两种注册场景。

## 官方文档

- [create_a_connected_account 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_connected_account)

## 请求地址

`POST /api/open/v1/accounts/create`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-04-12T10:00:00+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

### 顶层字段

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `referenceAccountId` | string | Yes | 集成商侧商户标识符，作为幂等键，最大 64 字符 |
| `registrationRegion` | string | Yes | 注册国家/地区，ISO 3166-1 alpha-2 代码 |
| `registrationLegalName` | string | Yes | 注册法律实体名称，最大 128 字符 |
| `contact` | Contact | Yes | 联系信息（邮箱或电话至少提供一个） |
| `legalEntityInfo` | LegalEntityInfo | Yes | 法律实体信息 |
| `agreements` | Agreements | Yes | 协议记录 |

### Contact Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `contactEmail` | string | Conditional | 联系邮箱地址，与 contactPhone 二选一 |
| `contactPhone` | string | Conditional | 联系电话号码，与 contactEmail 二选一 |

### LegalEntityInfo Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `legalEntityType` | string | Yes | 法律实体类型：`INDIVIDUAL`（个人）或 `COMPANY`（企业/公司） |
| `company` | Company | Conditional | 公司法律实体详情，当 legalEntityType 为 COMPANY 时必填 |
| `individual` | Individual | Conditional | 个人法律实体详情，当 legalEntityType 为 INDIVIDUAL 时必填 |
| `businessProfile` | BusinessProfile | Yes | 商业档案 |
| `relatedParties` | array[RelatedParty] | No | 关联方列表（UBO、董事、法定代表人等） |

### Company Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `legalName` | string | Yes | 公司法定名称 |
| `registrationType` | string | Yes | 注册类型 |
| `registrationNo` | string | Yes | 公司注册号码 |
| `registrationAddress` | Address | Yes | 注册地址 |
| `englishRegistrationAddress` | Address | No | 英文注册地址 |
| `registrationDate` | string | Yes | 注册日期（ISO 8601 格式） |
| `certificates` | array[Certificate] | No | 证件列表 |
| `attachments` | array[Attachment] | No | 附件列表 |
| `companyType` | string | Yes | 公司类型（CompanyType 枚举） |
| `enterpriseType` | string | No | 企业类型 |
| `stockCode` | string | No | 股票代码 |
| `stockMarket` | string | No | 上市市场 |
| `staffNumber` | string | No | 员工人数 |
| `wealthSources` | array[string] | No | 财富来源 |
| `additionalInfo` | string | No | 附加信息 |
| `taxNo` | string | No | 税号 |
| `vatNo` | string | No | VAT 号码 |

### Individual Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `legalName` | string | Yes | 法定姓名 |
| `userName` | UserName | Yes | 用户名（全名、名、姓） |
| `userEnglishName` | UserName | No | 英文姓名 |
| `gender` | string | Yes | 性别 |
| `certificates` | array[Certificate] | Yes | 证件列表 |
| `attachments` | array[Attachment] | No | 附件列表 |
| `nationality` | string | Yes | 国籍（ISO 3166-1 alpha-2） |
| `birthDate` | string | Yes | 出生日期（ISO 8601 格式） |
| `birthPlace` | string | No | 出生地 |
| `residentAddress` | Address | Yes | 居住地址 |
| `englishResidentAddress` | Address | No | 英文居住地址 |
| `contactAddress` | Address | No | 联系地址 |
| `department` | string | No | 部门 |
| `position` | string | No | 职位 |
| `wealthSources` | array[string] | No | 财富来源 |
| `additionalInfo` | string | No | 附加信息 |
| `taxNo` | string | No | 税号 |
| `vatNo` | string | No | VAT 号码 |

### BusinessProfile Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `businessName` | string | Yes | 商业名称 |
| `englishBusinessName` | string | No | 英文商业名称 |
| `industryType` | string | Yes | 行业类型 |
| `merchantCategoryCode` | string | No | 商户类别码 |
| `brand` | string | No | 品牌 |
| `logo` | string | No | Logo URL |
| `operatingRegions` | array[string] | No | 经营地区列表 |
| `goodsOrServices` | array[string] | No | 商品或服务描述 |
| `websites` | array[string] | No | 网站列表 |
| `businessAddress` | Address | Yes | 经营地址 |
| `stores` | array[Store] | No | 门店列表 |
| `fundsSources` | array[string] | No | 资金来源 |
| `fundsDestinations` | array[string] | No | 资金去向 |
| `usEcBusinessType` | string | No | 美国电商业务类型 |
| `webSiteReady` | boolean | No | 网站是否就绪 |
| `ecBusinessWebsites` | array[EcBusinessWebsite] | No | 电商业务网站列表 |

### RelatedParty Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `relationship` | string | Yes | 关联方关系类型（Relationship 枚举） |
| `legalEntityType` | string | Yes | 法律实体类型：INDIVIDUAL 或 COMPANY |
| `individual` | Individual | Conditional | 个人详情，当 legalEntityType 为 INDIVIDUAL 时必填 |
| `company` | Company | Conditional | 公司详情，当 legalEntityType 为 COMPANY 时必填 |
| `shareHoldingRatio` | string | No | 持股比例 |
| `fundsSources` | array[string] | No | 资金来源 |
| `fundsDestinations` | array[string] | No | 资金去向 |
| `additionalInfo` | string | No | 附加信息 |

### Certificate Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `certificateType` | string | Yes | 证件类型（CertificateType 枚举） |
| `certificateNo` | string | Yes | 证件号码 |
| `files` | array[string] | Yes | 证件文件 URL 列表 |
| `holderName` | string | No | 持有人姓名 |
| `effectivePeriodType` | string | No | 有效期类型 |
| `effectiveDate` | string | No | 生效日期（ISO 8601 格式） |
| `expiresDate` | string | No | 过期日期（ISO 8601 格式） |
| `certificateAuthority` | string | No | 发证机关 |

### Attachment Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `attachmentType` | string | Yes | 附件类型 |
| `attachmentKey` | string | Yes | 附件标识 |
| `attachmentName` | string | No | 附件名称 |

### Address Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `region` | string | Yes | 国家/地区（ISO 3166-1 alpha-2） |
| `state` | string | No | 州/省 |
| `city` | string | Yes | 城市 |
| `address1` | string | Yes | 地址行 1 |
| `address2` | string | No | 地址行 2 |
| `zipCode` | string | No | 邮政编码 |

### UserName Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `fullName` | string | No | 全名 |
| `firstName` | string | No | 名 |
| `middleName` | string | No | 中间名 |
| `lastName` | string | No | 姓 |

### Store Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `storeName` | string | Yes | 门店名称 |
| `storeAddress` | Address | Yes | 门店地址 |

### EcBusinessWebsite Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `url` | string | Yes | 网站 URL |

### Agreements Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `agreedToTermsAndConditions` | boolean | Yes | 是否同意平台服务协议，必须为 true |
| `agreedToDataUsage` | boolean | Yes | 是否同意数据使用授权，必须为 true |
| `agreedTo2C2PAnd2C2BPlusTermsAndConditions` | boolean | Conditional | TH 地区必填，是否同意 2C2P 和 2C2B+ 条款 |
| `accessType` | string | No | 访问权限类型（AccessType 枚举） |
| `accessScope` | string | No | 访问范围，当 accessType 为 SCOPED 时必填 |

### 请求示例（COMPANY 场景）

```json
{
  "referenceAccountId": "PARTNER_MERCHANT_001",
  "registrationRegion": "GB",
  "registrationLegalName": "Example Trading Ltd.",
  "contact": {
    "contactEmail": "info@example-trading.com",
    "contactPhone": "+442071234567"
  },
  "legalEntityInfo": {
    "legalEntityType": "COMPANY",
    "company": {
      "legalName": "Example Trading Ltd.",
      "registrationType": "PRIVATE_LIMITED",
      "registrationNo": "12345678",
      "registrationAddress": {
        "region": "GB",
        "city": "London",
        "address1": "123 Business Street",
        "zipCode": "EC1A 1BB"
      },
      "registrationDate": "2020-01-15",
      "companyType": "PRIVATE_LIMITED"
    },
    "businessProfile": {
      "businessName": "Example Trading",
      "industryType": "RETAIL",
      "businessAddress": {
        "region": "GB",
        "city": "London",
        "address1": "123 Business Street",
        "zipCode": "EC1A 1BB"
      }
    },
    "relatedParties": [
      {
        "relationship": "LEGAL_REPRESENTATIVE",
        "legalEntityType": "INDIVIDUAL",
        "individual": {
          "legalName": "John Smith",
          "userName": {
            "fullName": "John Smith",
            "firstName": "John",
            "lastName": "Smith"
          },
          "gender": "MALE",
          "certificates": [
            {
              "certificateType": "PASSPORT",
              "certificateNo": "AB1234567",
              "files": ["https://example.com/passport.jpg"]
            }
          ],
          "nationality": "GB",
          "birthDate": "1980-05-20",
          "residentAddress": {
            "region": "GB",
            "city": "London",
            "address1": "456 Residential Road",
            "zipCode": "SW1A 1AA"
          }
        }
      }
    ]
  },
  "agreements": {
    "agreedToTermsAndConditions": true,
    "agreedToDataUsage": true
  }
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `status` | string | 账户注册状态（AccountStatus 枚举） |
| `id` | string | WorldFirst 分配的唯一商户标识符（Account ID） |
| `referenceAccountId` | string | 集成商侧的商户标识符（与请求一致） |
| `createdAt` | datetime | 账户创建时间（ISO 8601 格式） |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "status": "REGISTERED",
  "id": "2088000000000001",
  "referenceAccountId": "PARTNER_MERCHANT_001",
  "createdAt": "2026-04-12T10:00:00+08:00"
}
```

## 错误码

| resultCode | resultStatus | 说明 |
|------------|--------------|------|
| `SUCCESS` | S | 创建成功，账户已进入审核流程 |
| `REPEAT_REGISTRATION` | F | 重复注册，referenceAccountId 已存在 |
| `REGISTRATION_UNDER_REVIEW` | F | 账户正在审核中，无法重复提交 |
| `REGISTRATION_CLOSED` | F | 注册通道已关闭 |
| `PARAM_ILLEGAL` | F | 参数非法，检查必填字段和格式 |
| `INVALID_PHONE_FORMAT` | F | 电话号码格式无效 |
| `INVALID_EMAIL_FORMAT` | F | 邮箱格式无效 |
| `UN_SUPPORT_BUSINESS` | F | 不支持的业务类型 |
| `SYSTEM_ERROR` | U | 系统错误，请稍后重试 |

## 示例代码

参考 [references/connected/connected-accounts/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
connected-accounts/java/
├── service/
│   └── ConnectedAccountService.java                   # 薄封装 Service，包含 createConnectedAccount 方法
└── model/
    ├── domain/
    │   ├── LegalEntityInfo.java                       # 法律实体信息
    │   ├── Company.java                               # 公司实体详情
    │   ├── Individual.java                            # 个人实体详情
    │   ├── BusinessProfile.java                       # 商业档案
    │   ├── RelatedParty.java                          # 关联方
    │   ├── Contact.java                               # 联系信息
    │   ├── Agreements.java                            # 协议记录
    │   ├── Certificate.java                           # 证件
    │   ├── Attachment.java                            # 附件
    │   ├── Store.java                                 # 门店
    │   └── EcBusinessWebsite.java                     # 电商业务网站
    ├── request/
    │   └── CreateConnectedAccountRequest.java         # 请求参数
    └── response/
        └── CreateConnectedAccountResponse.java        # 响应结果（result + 账户基本信息）
```

## 集成使用方式

ConnectedAccountService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `CreateConnectedAccountRequest` 设置商户信息
2. 调用 `ConnectedAccountService.createConnectedAccount(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理账户创建结果

### 业务代码示例（COMPANY 场景）

```java
// 构造联系信息
Contact contact = new Contact();
contact.setContactEmail("info@example-trading.com");
contact.setContactPhone("+442071234567");

// 构造公司地址
Address registrationAddress = new Address();
registrationAddress.setRegion("GB");
registrationAddress.setCity("London");
registrationAddress.setAddress1("123 Business Street");
registrationAddress.setZipCode("EC1A 1BB");

// 构造公司信息
Company company = new Company();
company.setLegalName("Example Trading Ltd.");
company.setRegistrationType("PRIVATE_LIMITED");
company.setRegistrationNo("12345678");
company.setRegistrationAddress(registrationAddress);
company.setRegistrationDate("2020-01-15");
company.setCompanyType("PRIVATE_LIMITED");

// 构造经营地址
Address businessAddress = new Address();
businessAddress.setRegion("GB");
businessAddress.setCity("London");
businessAddress.setAddress1("123 Business Street");
businessAddress.setZipCode("EC1A 1BB");

// 构造商业档案
BusinessProfile businessProfile = new BusinessProfile();
businessProfile.setBusinessName("Example Trading");
businessProfile.setIndustryType("RETAIL");
businessProfile.setBusinessAddress(businessAddress);

// 构造法定代表人证件
Certificate certificate = new Certificate();
certificate.setCertificateType("PASSPORT");
certificate.setCertificateNo("AB1234567");
certificate.setFiles(java.util.Arrays.asList("https://example.com/passport.jpg"));

// 构造法定代表人居住地址
Address residentAddress = new Address();
residentAddress.setRegion("GB");
residentAddress.setCity("London");
residentAddress.setAddress1("456 Residential Road");
residentAddress.setZipCode("SW1A 1AA");

// 构造法定代表人用户名
UserName userName = new UserName();
userName.setFullName("John Smith");
userName.setFirstName("John");
userName.setLastName("Smith");

// 构造法定代表人信息
Individual legalRep = new Individual();
legalRep.setLegalName("John Smith");
legalRep.setUserName(userName);
legalRep.setGender("MALE");
legalRep.setCertificates(java.util.Arrays.asList(certificate));
legalRep.setNationality("GB");
legalRep.setBirthDate("1980-05-20");
legalRep.setResidentAddress(residentAddress);

// 构造关联方
RelatedParty relatedParty = new RelatedParty();
relatedParty.setRelationship("LEGAL_REPRESENTATIVE");
relatedParty.setLegalEntityType("INDIVIDUAL");
relatedParty.setIndividual(legalRep);

// 构造法律实体信息
LegalEntityInfo legalEntityInfo = new LegalEntityInfo();
legalEntityInfo.setLegalEntityType("COMPANY");
legalEntityInfo.setCompany(company);
legalEntityInfo.setBusinessProfile(businessProfile);
legalEntityInfo.setRelatedParties(java.util.Arrays.asList(relatedParty));

// 构造协议记录
Agreements agreements = new Agreements();
agreements.setAgreedToTermsAndConditions(true);
agreements.setAgreedToDataUsage(true);

// 构造创建请求
CreateConnectedAccountRequest request = new CreateConnectedAccountRequest();
request.setReferenceAccountId("PARTNER_MERCHANT_001");
request.setRegistrationRegion("GB");
request.setRegistrationLegalName("Example Trading Ltd.");
request.setContact(contact);
request.setLegalEntityInfo(legalEntityInfo);
request.setAgreements(agreements);

// 调用 Service（内部已强制验签，验签失败抛 WfException）
CreateConnectedAccountResponse response = accountService.createConnectedAccount(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("账户创建成功");
    System.out.println("WorldFirst 账户 ID: " + response.getId());
    System.out.println("集成商侧标识符: " + response.getReferenceAccountId());
    System.out.println("注册状态: " + response.getStatus());
    System.out.println("创建时间: " + response.getCreatedAt());
} else {
    // 处理错误
    System.err.println("创建失败: " + response.getResult().getResultCode());
    System.err.println("错误信息: " + response.getResult().getResultMessage());
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 ConnectedAccountService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new ConnectedAccountService(config)` 创建实例。
