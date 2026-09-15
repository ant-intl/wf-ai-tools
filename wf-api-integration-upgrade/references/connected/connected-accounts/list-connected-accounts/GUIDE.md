# List Connected Accounts 接口接入指引

## 接口说明

分页查询关联账户列表，支持按状态和创建时间范围过滤。采用游标分页，适用于批量查询和对账场景。

## 官方文档

- [list_connected_accounts 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/list_connected_accounts)

## 请求地址

`POST /api/open/v1/accounts/list`

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
| `limit` | integer | Yes | 每页记录数，取值范围 1-20 |
| `cursor` | string | No | 分页游标；首次请求省略，后续请求传入上次响应的 `nextCursor` 或 `prevCursor` |
| `status` | string | No | 按账户状态过滤（AccountStatus 枚举）；如果省略，返回所有状态 |
| `fromCreatedAt` | datetime | No | 创建时间范围起始（ISO 8601 格式，包含），必须与 `toCreatedAt` 配合使用 |
| `toCreatedAt` | datetime | No | 创建时间范围结束（ISO 8601 格式，包含），必须与 `fromCreatedAt` 配合使用 |

### 游标分页说明

- **首次请求**：不传 `cursor`，API 返回 `nextCursor`
- **后续请求**：将上次响应的 `nextCursor` 作为 `cursor` 传入
- **最后一页**：`nextCursor` 为 `null`，表示无更多数据
- **排序**：返回记录按 `createdAt` 降序排列

### 请求示例

```json
{
  "limit": 10,
  "status": "SUCCESS",
  "fromCreatedAt": "2026-04-01T00:00:00+08:00",
  "toCreatedAt": "2026-04-30T23:59:59+08:00"
}
```

> 带游标的后续请求示例：

```json
{
  "limit": 10,
  "cursor": "eyJpZCI6IjIwMjYwNDEw****",
  "status": "SUCCESS",
  "fromCreatedAt": "2026-04-01T00:00:00+08:00",
  "toCreatedAt": "2026-04-30T23:59:59+08:00"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `items` | array[ConnectedAccountRecord] | 关联账户记录列表（基本信息），按 `createdAt` 降序，最多 20 条 |
| `nextCursor` | string | 下一页游标，为空表示无更多数据 |
| `prevCursor` | string | 上一页游标，第一页为 `null` |

### ConnectedAccountRecord Object

| Field | Type | Description |
|-------|------|-------------|
| `id` | string | WorldFirst 分配的唯一商户标识符 |
| `referenceAccountId` | string | 集成商侧的商户标识符 |
| `registrationLegalName` | string | 注册的法律实体名称 |
| `registrationRegion` | string | 注册国家/地区（ISO 3166-1 alpha-2 代码） |
| `status` | string | 账户注册状态（AccountStatus 枚举） |
| `createdAt` | datetime | 账户创建时间（ISO 8601 格式） |
| `updatedAt` | datetime | 账户最后更新时间（ISO 8601 格式） |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "items": [
    {
      "id": "2088000000000001",
      "referenceAccountId": "PARTNER_MERCHANT_001",
      "registrationLegalName": "Example Trading Ltd.",
      "registrationRegion": "GB",
      "status": "SUCCESS",
      "createdAt": "2026-04-12T10:00:00+08:00",
      "updatedAt": "2026-04-13T15:30:00+08:00"
    },
    {
      "id": "2088000000000002",
      "referenceAccountId": "PARTNER_MERCHANT_002",
      "registrationLegalName": "Global Commerce Pte Ltd",
      "registrationRegion": "SG",
      "status": "PROCESSING",
      "createdAt": "2026-04-10T08:30:00+08:00",
      "updatedAt": "2026-04-10T08:30:00+08:00"
    }
  ],
  "nextCursor": "eyJpZCI6IjIwMjYwNDEw****"
}
```

## 错误码

| resultCode | resultStatus | 说明 |
|------------|--------------|------|
| `SUCCESS` | S | 查询成功 |
| `PARAM_ILLEGAL` | F | 参数非法，检查 `limit` 是否在 1-20 范围内，验证 `cursor` 格式（如果提供） |

## 示例代码

参考 [references/connected/connected-accounts/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
connected-accounts/java/
├── service/
│   └── ConnectedAccountService.java                   # 薄封装 Service，包含 listConnectedAccounts 方法
└── model/
    ├── domain/
    │   └── ConnectedAccountRecord.java                # 关联账户记录（用于 list 响应的 items）
    ├── request/
    │   └── ListConnectedAccountsRequest.java          # 请求参数（limit, cursor, status, fromCreatedAt, toCreatedAt）
    └── response/
        └── ListConnectedAccountsResponse.java         # 响应结果（result, items, nextCursor, prevCursor）
```

## 集成使用方式

ConnectedAccountService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `ListConnectedAccountsRequest` 设置查询条件（首次请求不传 `cursor`）
2. 调用 `ConnectedAccountService.listConnectedAccounts(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理账户记录
4. 若 `nextCursor` 不为 `null`，将其作为 `cursor` 传入下一次请求，重复步骤 1-3

### 业务代码示例

```java
// 构造请求
ListConnectedAccountsRequest request = new ListConnectedAccountsRequest();
request.setLimit(10);
request.setStatus("SUCCESS");
request.setFromCreatedAt("2026-04-01T00:00:00+08:00");
request.setToCreatedAt("2026-04-30T23:59:59+08:00");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
ListConnectedAccountsResponse response = accountService.listConnectedAccounts(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    // 处理账户记录
    for (ConnectedAccountRecord record : response.getItems()) {
        System.out.println("账户 ID: " + record.getId());
        System.out.println("集成商 ID: " + record.getReferenceAccountId());
        System.out.println("法律实体名称: " + record.getRegistrationLegalName());
        System.out.println("注册地区: " + record.getRegistrationRegion());
        System.out.println("状态: " + record.getStatus());
        System.out.println("创建时间: " + record.getCreatedAt());
    }

    // 游标分页：继续查询下一页
    if (response.getNextCursor() != null) {
        request.setCursor(response.getNextCursor());
        // 再次调用 accountService.listConnectedAccounts(request) 获取下一页数据
    }
} else {
    // 处理错误
    System.err.println("查询失败: " + response.getResult().getResultMessage());
}
```

### 游标分页完整遍历示例

```java
List<ConnectedAccountRecord> allAccounts = new ArrayList<>();
String cursor = null;

do {
    ListConnectedAccountsRequest request = new ListConnectedAccountsRequest();
    request.setLimit(20);
    request.setStatus("SUCCESS");
    request.setFromCreatedAt("2026-04-01T00:00:00+08:00");
    request.setToCreatedAt("2026-04-30T23:59:59+08:00");
    if (cursor != null) {
        request.setCursor(cursor);
    }

    // 调用 Service（内部已强制验签，验签失败抛 WfException）
    ListConnectedAccountsResponse response = accountService.listConnectedAccounts(request);
    if (!"S".equals(response.getResult().getResultStatus())) {
        throw new RuntimeException("查询失败: " + response.getResult().getResultMessage());
    }

    allAccounts.addAll(response.getItems());
    cursor = response.getNextCursor();
} while (cursor != null);

System.out.println("共查询到 " + allAccounts.size() + " 个关联账户");
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 ConnectedAccountService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new ConnectedAccountService(config)` 创建实例。
