# 全局账户（Global Accounts）模块

## 官方文档

- [WorldFirst 开发者文档 - Global Accounts](https://docs.worldfirst.com/wfdocs/api-sdk/global_accounts_overview)

## 接口列表

| 接口 | 目录 | 说明 | Endpoint |
|------|------|------|----------|
| 创建全局账户 | `create-a-global-account/GUIDE.md` | 开通全球收款账户，指定银行地区和收款能力 | `POST /api/open/v1/globalAccounts/create` |
| 查询全局账户 | `query-a-global-account/GUIDE.md` | 根据账户 ID 查询全局账户详细信息 | `POST /api/open/v1/globalAccounts/query` |
| 列表查询全局账户 | `list-global-accounts/GUIDE.md` | 分页查询全局账户列表，支持多维度过滤 | `POST /api/open/v1/globalAccounts/list` |
| 更新全局账户 | `update-a-global-account/GUIDE.md` | 更新全局账户昵称 | `POST /api/open/v1/globalAccounts/update` |
| 关闭全局账户 | `close-a-global-account/GUIDE.md` | 永久关闭全局账户，关闭后不可恢复 | `POST /api/open/v1/globalAccounts/close` |

## GlobalAccountService 说明

`GlobalAccountService` 是全局账户模块的服务入口，采用**薄封装模式**：

- 不做参数校验（由调用方或 WF 服务端负责）
- 验签后自动反序列化响应体，直接返回业务响应对象（如 `GlobalAccountResponse`）
- 自动注入签名与验签（由底层 `WfApiClient` 完成，验签失败抛 `WfException`）

### 方法列表

| 方法 | 请求类型 | 响应类型 | 说明 |
|------|----------|----------|------|
| `createGlobalAccount(CreateGlobalAccountRequest)` | `CreateGlobalAccountRequest` | `GlobalAccountResponse` | 创建全局账户 |
| `queryGlobalAccount(QueryGlobalAccountRequest)` | `QueryGlobalAccountRequest` | `GlobalAccountResponse` | 查询全局账户 |
| `listGlobalAccounts(ListGlobalAccountsRequest)` | `ListGlobalAccountsRequest` | `ListGlobalAccountsResponse` | 列表查询全局账户（游标分页） |
| `updateGlobalAccount(UpdateGlobalAccountRequest)` | `UpdateGlobalAccountRequest` | `GlobalAccountResponse` | 更新全局账户昵称 |
| `closeGlobalAccount(CloseGlobalAccountRequest)` | `CloseGlobalAccountRequest` | `GlobalAccountResponse` | 关闭全局账户 |

### 构造器

| 构造器 | 说明 |
|--------|------|
| `GlobalAccountService(WfClientConfig config)` | 传入配置，内部创建 `WfApiClient` |
| `GlobalAccountService(WfApiClient apiClient)` | 传入已构造的 `WfApiClient`，便于测试注入 |

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
GlobalAccountService globalAccountService = new GlobalAccountService(config);
```

### 3. 调用方法（内部已强制验签，验签失败抛 WfException）

```java
// 创建全局账户
CreateGlobalAccountRequest createRequest = new CreateGlobalAccountRequest();
createRequest.setRequestId(UUID.randomUUID().toString());
createRequest.setBankRegion("US");
createRequest.setRequiredFeatures(Arrays.asList(
    new RequiredFeature("USD", "LOCAL"),
    new RequiredFeature("USD", "CROSS")
));

GlobalAccountResponse createResponse = globalAccountService.createGlobalAccount(createRequest);

// 查询全局账户
QueryGlobalAccountRequest queryRequest = new QueryGlobalAccountRequest();
queryRequest.setId("2026032519121000470000009473");

GlobalAccountResponse queryResponse = globalAccountService.queryGlobalAccount(queryRequest);

// 列表查询
ListGlobalAccountsRequest listRequest = new ListGlobalAccountsRequest();
listRequest.setBankRegion("US");
listRequest.setStatus("ACTIVE");
listRequest.setLimit(20);

ListGlobalAccountsResponse listResponse = globalAccountService.listGlobalAccounts(listRequest);

// 更新昵称
UpdateGlobalAccountRequest updateRequest = new UpdateGlobalAccountRequest();
updateRequest.setId("2026032519121000470000009473");
updateRequest.setNickName("New Nickname");

GlobalAccountResponse updateResponse = globalAccountService.updateGlobalAccount(updateRequest);

// 关闭账户
CloseGlobalAccountRequest closeRequest = new CloseGlobalAccountRequest();
closeRequest.setId("2026032519121000470000009473");

GlobalAccountResponse closeResponse = globalAccountService.closeGlobalAccount(closeRequest);
```

### 4. 检查业务结果

```java
// 单账户响应（create / query / update / close）
if ("S".equals(createResponse.getResult().getResultStatus())) {
    System.out.println("Account ID: " + createResponse.getId());
    System.out.println("Status: " + createResponse.getStatus());
}

// 列表响应
if ("S".equals(listResponse.getResult().getResultStatus())) {
    for (GlobalAccount account : listResponse.getItems()) {
        System.out.println(account.getId() + " " + account.getStatus());
    }
}
```

## 注意事项

### create_a_global_account
- `requestId` 为幂等键，每次请求必须唯一（建议使用 UUID），重复的 requestId 会被拒绝
- `bankRegion` 需为 ISO 3166 两位代码（如 US、GB、HK）
- `requiredFeatures` 最多 10 个元素，每个指定一种币种 + 支付方式组合
- 账户创建可能返回 `PROCESSING` 状态，需轮询 query 接口确认最终状态

### query_a_global_account
- `id` 为创建账户时返回的唯一标识
- 不同状态的账户返回字段不同（如 `ACTIVE` 返回 accountNumber，`CLOSED` 返回 closeReason）

### list_global_accounts
- 使用**游标分页**（cursor-based pagination）
- `limit` 取值范围 1-20
- 首次请求不传 `cursor`，后续请求传入上一次响应返回的 `nextCursor`
- 当 `nextCursor` 为空时表示已到最后一页

### update_a_global_account
- 仅支持修改 `nickName` 字段
- 账户需为 `ACTIVE` 状态才能更新

### close_a_global_account
- 关闭操作**不可逆**，关闭后账户无法收款且无法重新开启
- 仅 `ACTIVE` 状态的账户可被关闭

## 枚举参考

### GlobalAccountStatus

| 值 | 说明 |
|----|------|
| `PROCESSING` | 账户创建中 |
| `ACTIVE` | 账户正常，可收款 |
| `FAILED` | 账户创建失败 |
| `CLOSED` | 账户已永久关闭 |

### PaymentType

| 值 | 说明 |
|----|------|
| `LOCAL` | 本地清算（如 ACH、SEPA、Faster Payments） |
| `CROSS` | 跨境汇款（如 SWIFT） |

### AccountType

| 值 | 说明 |
|----|------|
| `CHECKING` | 支票账户（目前仅支持此类型） |

## 文件结构

```
global-accounts/
├── README.md
├── java/
│   ├── service/
│   │   └── GlobalAccountService.java              # 全局账户服务（薄封装）
│   └── model/
│       ├── domain/
│       │   ├── GlobalAccount.java                 # 全局账户对象
│       │   ├── Institution.java                   # 银行机构信息
│       │   ├── RequiredFeature.java               # 申请的收款能力
│       │   ├── SupportedFeature.java              # 已开通的收款能力
│       │   └── RoutingCode.java                   # 路由码
│       ├── request/
│       │   ├── CreateGlobalAccountRequest.java    # 创建全局账户请求
│       │   ├── QueryGlobalAccountRequest.java     # 查询全局账户请求
│       │   ├── ListGlobalAccountsRequest.java     # 列表查询请求
│       │   ├── UpdateGlobalAccountRequest.java    # 更新全局账户请求
│       │   └── CloseGlobalAccountRequest.java     # 关闭全局账户请求
│       └── response/
│           ├── GlobalAccountResponse.java          # 单账户响应（create/query/update/close）
│           └── ListGlobalAccountsResponse.java     # 列表响应
├── create-a-global-account/
│   └── GUIDE.md                                    # create_a_global_account 接口接入指引
├── query-a-global-account/
│   └── GUIDE.md                                    # query_a_global_account 接口接入指引
├── list-global-accounts/
│   └── GUIDE.md                                    # list_global_accounts 接口接入指引
├── update-a-global-account/
│   └── GUIDE.md                                    # update_a_global_account 接口接入指引
└── close-a-global-account/
    └── GUIDE.md                                    # close_a_global_account 接口接入指引
```
