# List Global Accounts 接口接入指引

## 接口说明

分页查询全局账户列表，支持按银行地区、状态、收款能力和创建时间范围过滤。采用游标分页。

## 官方文档

- [list_global_accounts 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/list_global_accounts)

## 请求地址

`POST /api/open/v1/globalAccounts/list`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2024-01-01T12:08:56+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `bankRegion` | string | No | 按银行地区过滤（ISO 3166） |
| `nickName` | string | No | 按账户昵称过滤 |
| `status` | string | No | 按账户状态过滤（`PROCESSING`/`ACTIVE`/`FAILED`/`CLOSED`） |
| `supportedFeatures` | array[object] | No | 按已开通收款能力过滤 |
| `fromCreatedAt` | string | No | 按创建时间起始过滤（含，ISO 8601） |
| `toCreatedAt` | string | No | 按创建时间截止过滤（含，ISO 8601） |
| `limit` | integer | Yes | 每页记录数，范围 1-20 |
| `cursor` | string | No | 分页游标，使用上次响应的 `nextCursor` 或 `prevCursor` |

### supportedFeatures 过滤对象

复用 `RequiredFeature` 对象结构，用于按已开通收款能力过滤。

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `currency` | string | Yes | 货币代码（ISO-4217） |
| `paymentType` | string | No | 支付方式：`LOCAL`（本地清算）或 `CROSS`（跨境汇款） |

### 游标分页说明

- **首次请求**：不传 `cursor`，API 返回 `nextCursor`
- **后续请求**：将上次响应的 `nextCursor` 作为 `cursor` 传入
- **最后一页**：`nextCursor` 为 `null`，表示无更多数据

### 请求示例

```json
{
  "bankRegion": "US",
  "status": "ACTIVE",
  "supportedFeatures": [{ "currency": "USD" }],
  "cursor": "",
  "limit": 20
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果 |
| `items` | array[GlobalAccount] | 全局账户列表，最多 20 条 |
| `nextCursor` | string | 下一页游标，最后一页为 `null` |
| `prevCursor` | string | 上一页游标，第一页为 `null` |

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
      "id": "2026032519121000470000009473",
      "accountName": "Sandbox Business",
      "nickName": "USD in US for Subsidiary Company ABC",
      "accountType": "CHECKING",
      "status": "ACTIVE",
      "accountNumber": "8484346726",
      "iban": "IVAJPNIUM201000002",
      "swiftCode": "CMFGUS33",
      "institution": {
        "branchCode": "",
        "bankName": "Example Bank Ltd",
        "bankRegion": "US"
      },
      "requiredFeatures": [
        { "currency": "USD", "paymentType": "LOCAL" }
      ],
      "supportedFeatures": [
        {
          "type": "RECEIVING",
          "currency": "USD",
          "paymentNetwork": "ACH",
          "routingCodes": [{ "type": "ACH", "value": "026073150" }],
          "paymentType": "LOCAL"
        }
      ],
      "createdAt": "2026-03-30T12:08:56+08:00"
    }
  ],
  "nextCursor": "",
  "prevCursor": ""
}
```

> items 中每个元素的完整字段结构与 query_a_global_account 响应一致，详见 [create_a_global_account GUIDE](../create-a-global-account/GUIDE.md)。

## 错误码

| resultCode | resultStatus | 说明 | 排查建议 |
|------------|--------------|------|----------|
| `SUCCESS` | S | 成功 | — |
| `PARAM_ILLEGAL` | F | 参数非法 | 检查过滤条件是否有效，`limit` 是否在 1-20 之间 |
| `PERMISSION_DENIED` | F | 无权限 | 验证是否有执行此操作的权限 |

## 示例代码

参考 [references/accounts/global-accounts/java/](../java/) 中的参考实现代码。

### 集成使用方式

```java
// 构造请求
ListGlobalAccountsRequest request = new ListGlobalAccountsRequest();
request.setBankRegion("US");
request.setStatus("ACTIVE");
request.setLimit(20);

// 调用 Service（内部已强制验签，验签失败抛 WfException）
ListGlobalAccountsResponse response = globalAccountService.listGlobalAccounts(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    for (GlobalAccount account : response.getItems()) {
        System.out.println("账户ID: " + account.getId() + " 状态: " + account.getStatus());
    }

    // 游标分页：继续查询下一页
    if (response.getNextCursor() != null && !response.getNextCursor().isEmpty()) {
        request.setCursor(response.getNextCursor());
        // 再次调用 globalAccountService.listGlobalAccounts(request) 获取下一页
    }
} else {
    System.err.println("查询失败: " + response.getResult().getResultMessage());
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 GlobalAccountService。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new GlobalAccountService(config)` 创建实例。
