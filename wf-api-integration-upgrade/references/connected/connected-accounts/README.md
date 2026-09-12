# 关联账户（Connected Accounts）模块

## 官方文档

- [WorldFirst 开发者文档 - Connected Accounts Overview](https://docs.worldfirst.com/wfdocs/api-sdk/connected_accounts_overview)
- [create_a_connected_account](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_connected_account) | [query_a_connected_account](https://docs.worldfirst.com/wfdocs/api-sdk/query_a_connected_account) | [list_connected_accounts](https://docs.worldfirst.com/wfdocs/api-sdk/list_connected_accounts)

## 接口列表

| 接口 | 目录 | 说明 | Endpoint |
|------|------|------|----------|
| 创建关联账户 | `create-connected-account/GUIDE.md` | 注册并入驻新关联商户到 WorldFirst | `POST /api/open/v1/accounts/create` |
| 查询关联账户详情 | `query-connected-account/GUIDE.md` | 根据 ID 查询单个关联账户的完整详情 | `POST /api/open/v1/accounts/query` |
| 查询关联账户列表 | `list-connected-accounts/GUIDE.md` | 分页查询关联账户列表，支持按状态、创建时间范围过滤 | `POST /api/open/v1/accounts/list` |

## ConnectedAccountService 说明

`ConnectedAccountService` 是关联账户模块的服务入口，采用**薄封装模式**：

- 不做参数校验（由调用方或 WF 服务端负责）
- 验签后自动反序列化响应体，直接返回业务响应对象（如 `QueryConnectedAccountResponse`）
- 自动注入签名与验签（由底层 `WfApiClient` 完成，验签失败抛 `WfException`）

### 方法列表

| 方法 | 请求类型 | 响应类型 | 说明 |
|------|----------|----------|------|
| `createConnectedAccount(CreateConnectedAccountRequest)` | `CreateConnectedAccountRequest` | `CreateConnectedAccountResponse` | 创建关联账户，注册并入驻新商户 |
| `queryConnectedAccount(QueryConnectedAccountRequest)` | `QueryConnectedAccountRequest` | `QueryConnectedAccountResponse` | 查询单个关联账户详情 |
| `listConnectedAccounts(ListConnectedAccountsRequest)` | `ListConnectedAccountsRequest` | `ListConnectedAccountsResponse` | 分页查询关联账户列表，支持游标分页 |

### 构造器

| 构造器 | 说明 |
|--------|------|
| `ConnectedAccountService(WfClientConfig config)` | 传入配置，内部创建 `WfApiClient` |
| `ConnectedAccountService(WfApiClient apiClient)` | 传入已构造的 `WfApiClient`，便于测试注入 |

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
ConnectedAccountService accountService = new ConnectedAccountService(config);
```

### 3. 调用方法（内部已强制验签，验签失败抛 WfException）

```java
// 创建关联账户
CreateConnectedAccountRequest createRequest = new CreateConnectedAccountRequest();
createRequest.setReferenceAccountId("PARTNER_MERCHANT_001");
createRequest.setRegistrationRegion("GB");
createRequest.setRegistrationLegalName("Example Trading Ltd.");
// ... 设置 contact, legalEntityInfo, agreements

CreateConnectedAccountResponse createResponse = accountService.createConnectedAccount(createRequest);

// 查询关联账户详情
QueryConnectedAccountRequest queryRequest = new QueryConnectedAccountRequest();
queryRequest.setId(createResponse.getId());

QueryConnectedAccountResponse queryResponse = accountService.queryConnectedAccount(queryRequest);

// 查询关联账户列表
ListConnectedAccountsRequest listRequest = new ListConnectedAccountsRequest();
listRequest.setLimit(10);
listRequest.setStatus("SUCCESS");
listRequest.setFromCreatedAt("2026-04-01T00:00:00+08:00");
listRequest.setToCreatedAt("2026-04-30T23:59:59+08:00");

ListConnectedAccountsResponse listResponse = accountService.listConnectedAccounts(listRequest);
```

### 4. 检查业务结果

```java
if ("S".equals(queryResponse.getResult().getResultStatus())) {
    System.out.println("账户 ID: " + queryResponse.getId());
    System.out.println("状态: " + queryResponse.getStatus());
    System.out.println("注册地区: " + queryResponse.getRegistrationRegion());
    System.out.println("法律实体名称: " + queryResponse.getRegistrationLegalName());
    
    if (queryResponse.getLegalEntityInfo() != null) {
        System.out.println("实体类型: " + queryResponse.getLegalEntityInfo().getLegalEntityType());
    }
    
    if (queryResponse.getContact() != null) {
        System.out.println("联系邮箱: " + queryResponse.getContact().getContactEmail());
    }
}
```

## 注意事项

### create_a_connected_account
- `referenceAccountId` 作为幂等键，同一标识符重复提交将返回已有账户信息而非创建新账户
- `contact` 中 `contactEmail` 和 `contactPhone` 至少提供一个
- `legalEntityInfo.legalEntityType` 为 COMPANY 时，`company` 字段必填；为 INDIVIDUAL 时，`individual` 字段必填
- `agreements.agreedToTermsAndConditions` 和 `agreements.agreedToDataUsage` 必须为 true
- TH（泰国）地区注册时，`agreements.agreedTo2C2PAnd2C2BPlusTermsAndConditions` 必填
- 创建成功后账户状态通常为 `REGISTERED`，需等待 KYC/KYB 审核完成

### query_a_connected_account
- `id` 必填，使用 Create a Connected Account 响应或 Webhook 通知中获取的 WorldFirst 账户 ID
- `legalEntityInfo` 在 SDK 注册但尚未提交 KYC 的账户中可能为 `null`
- `reasonCode` 和 `auditDetails` 仅在 `status` 为 `REJECT` 时返回
- `auditDetails` 最多返回 50 条

### list_connected_accounts
- 使用**游标分页**（cursor-based pagination），非传统页码分页
- `limit` 取值范围 1-20，必填
- 首次请求不传 `cursor`，后续请求传入上一次响应返回的 `nextCursor`
- 当 `nextCursor` 为空时表示已到最后一页
- `fromCreatedAt` / `toCreatedAt` 必须成对使用，类型为 `String`，使用 ISO 8601 格式
- `items` 列表中的账户记录按 `createdAt` 降序排列
- `items` 中返回的是账户基本信息，完整详情需调用 query 接口

## 枚举类型说明

### AccountStatus（账户注册状态）
- `REGISTERED`: 已提交但尚未进入审核
- `PROCESSING`: KYC/KYB 审核中
- `SUCCESS`: 审核通过，账户完全可用
- `FAILED`: 审核失败（终态），不可重新提交
- `REJECT`: 审核被拒绝，需补充材料后重新提交

### LegalEntityType（法律实体类型）
- `INDIVIDUAL`: 个人
- `COMPANY`: 企业/公司

### CompanyType（公司类型）
- `SOLE_PROPRIETORSHIP_ENTERPRISE`: 个体工商户
- `PUBLIC_LIMITED`: 有限责任公司
- `PUBLIC_LIMITED_SHARES`: 股份有限公司
- `PARTNERSHIP`: 有限合伙
- `PUBLIC_LIMITED_FOREIGN`: 外资公司
- `PUBLIC_LIMITED_STATE`: 国有公司
- `GOVERNMENT`: 集体企业
- `PRIVATE_LIMITED`: 私人有限公司
- `PUBLIC_LIMITED_PUBLIC`: 公众公司
- `PUBLIC_LIMITED_LISTED`: 上市公司
- `PUBLIC_LIMITED_CONTROL`: 控股公司
- `PUBLIC_LIMITED_GROUP`: 集团公司

### Relationship（关联方关系）
- `SHAREHOLDER`: 股东
- `BOARD_MEMBER`: 董事会成员
- `BENEFICIAL_OWNER`: 最终受益人（UBO）
- `LEGAL_REPRESENTATIVE`: 法定代表人
- `AUTHORIZED_SIGNATORY`: 授权签字人
- `DIRECTOR`: 董事
- `COMMON`: 普通成员
- `AFFILIATION_COMPANY`: 关联公司
- `ENTITY_SHAREHOLDER`: 法人股东

### CertificateType（证件类型）
- `ID_CARD`: 身份证
- `PASSPORT`: 护照
- `DRIVING_LICENSE`: 驾驶证
- `ENTERPRISE_REGISTRATION`: 企业注册文件/营业执照
- `TAX_REGISTRATION_CERTIFICATE`: 税务登记证书
- `BUSINESS_LICENSE`: 营业执照
- `MAINLAND_TRAVEL_PERMIT_HK_MC`: 港澳居民来往内地通行证
- `MAINLAND_TRAVEL_PERMIT_TAIWAN`: 台湾居民来往大陆通行证
- `HK_ID_CARD1`: 香港身份证（旧版）
- `HK_ID_CARD2`: 香港身份证（新版）

### RegistrationType（公司注册类型）
- `ENTERPRISE_REGISTRATION_CN`: CN - 营业执照（统一社会信用代码）
- `CERTIFICATE_OF_INCORPORATION_HK`: HK - 公司注册证书
- `BUSINESS_REGISTRATION_HK`: HK - 商业登记证
- `CERTIFICATE_OF_INCORPORATION_US`: US - 公司注册证书/章程
- `CERTIFICATE_OF_INCORPORATION_UK`: UK - 公司注册证书
- `CERTIFICATE_OF_INCORPORATION_SG`: SG - 公司注册证书 + ACRA Profile
- `CERTIFICATE_OF_INCORPORATION_AU`: AU - 注册证书
- `CERTIFICATE_OF_INCORPORATION_NZ`: NZ - 公司注册证书
- `CERTIFICATE_OF_INCORPORATION_JP`: JP - 登记证书
- `BUSINESS_REGISTRATION_KR`: KR - 商业登记证书
- `ENTERPRISE_REGISTRATION_VN`: VN - 企业注册证书
- `TRADE_REGISTER_EXTRACT_NL`: NL (EEA) - KvK Extract
- `TRADE_REGISTER_EXTRACT_DE`: DE (EEA) - Handelsregisterauszug
- `KBIS_EXTRACT_FR`: FR (EEA) - Extrait Kbis

### AttachmentType（附件类型）
- `ASSOCIATION_ARTICLE`: 公司章程
- `AGREEMENT`: 协议
- `ADDRESS_PROOF`: 地址证明
- `FINANCIAL_REPORT`: 财务报告
- `OTHER_MATERIAL`: 其他材料
- `ID_CARD_FRONT_SIDE`: 身份证正面
- `ID_CARD_BACK_SIDE`: 身份证背面
- `BUSINESS_LICENSE`: 营业执照
- `PASSPORT_PERSONAL_INFORMATION_PAGE`: 护照个人信息页
- `OWNERSHIP_STRUCTURE_PIC`: 股权结构图
- `INDUSTRY_LICENSE`: 行业许可证
- `COMPANY_EVIDENCE_OF_AUTHENTICITY`: 公司真实性证明
- `OFFICE_HEAD_PIC`: 办公室门头照
- `FACIAL_IMAGE`: 人脸照片

### EffectivePeriodType（有效期类型）
- `TIME_RANGE`: 在指定时间段内有效
- `LONG_TERM`: 长期有效

### AccessType（访问权限类型）
- `FULL_ACCESS`: 对子商户拥有完全操作权限（默认）
- `SCOPED`: 有限操作权限，需通过 `accessScope` 指定授权范围

## 文件结构

```
connected-accounts/
├── README.md
├── java/
│   ├── service/
│   │   └── ConnectedAccountService.java              # 关联账户服务（薄封装）
│   └── model/
│       ├── domain/
│       │   ├── LegalEntityInfo.java                   # 法律实体信息
│       │   ├── Company.java                           # 公司实体详情
│       │   ├── Individual.java                        # 个人实体详情
│       │   ├── BusinessProfile.java                   # 商业档案
│       │   ├── RelatedParty.java                      # 关联方（UBO、董事、法定代表人）
│       │   ├── Contact.java                           # 联系信息
│       │   ├── Agreements.java                        # 协议记录
│       │   ├── AuditDetail.java                       # 审核拒绝详情
│       │   ├── Certificate.java                       # 证件
│       │   ├── Attachment.java                        # 附件
│       │   ├── Store.java                             # 门店
│       │   ├── EcBusinessWebsite.java                 # 电商业务网站
│       │   └── ConnectedAccountRecord.java            # 关联账户记录（用于 list 响应）
│       ├── request/
│       │   ├── CreateConnectedAccountRequest.java     # 创建关联账户请求
│       │   ├── QueryConnectedAccountRequest.java      # 查询关联账户请求
│       │   └── ListConnectedAccountsRequest.java      # 查询关联账户列表请求
│       └── response/
│           ├── CreateConnectedAccountResponse.java    # 创建关联账户响应
│           ├── QueryConnectedAccountResponse.java     # 查询关联账户响应
│           └── ListConnectedAccountsResponse.java     # 查询关联账户列表响应
├── create-connected-account/
│   └── GUIDE.md                                       # create_a_connected_account 接口接入指引
├── query-connected-account/
│   └── GUIDE.md                                       # query_a_connected_account 接口接入指引
└── list-connected-accounts/
    └── GUIDE.md                                       # list_connected_accounts 接口接入指引
```
