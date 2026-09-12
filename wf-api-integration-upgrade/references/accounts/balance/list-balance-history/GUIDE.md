# List Balance History 接口接入指引

## 接口说明

分页查询商户账户余额变动记录。每条记录记录一次入账或出账事件，包括变动金额、变动后余额快照和交易时间戳。适用于对账和审计场景。采用游标分页。

## 官方文档

- [list_balance_history 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/list_balance_history)

## 请求地址

`POST /api/open/v1/balances/listHistory`

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
| `limit` | integer | No | 每页记录数，范围 1-100，默认 20 |
| `cursor` | string | No | 分页游标；首次请求省略，后续请求传入上次响应的 `nextCursor` |
| `currency` | string | No | ISO 4217 币种过滤；为空则返回所有币种变动记录 |
| `fromTransactAt` | datetime | No | 查询起始时间（ISO 8601），e.g. `2024-01-01T00:00:00+08:00` |
| `toTransactAt` | datetime | No | 查询结束时间（ISO 8601）；距 `fromTransactAt` 不超过 3 个月 |

### 游标分页说明

- **首次请求**：不传 `cursor`，API 返回 `nextCursor`
- **后续请求**：将上次响应的 `nextCursor` 作为 `cursor` 传入
- **最后一页**：`nextCursor` 为 `null`，表示无更多数据
- **排序**：返回记录按 `transactedAt` 降序排列

### 请求示例

```json
{
  "limit": 20,
  "currency": "USD",
  "fromTransactAt": "2024-01-01T00:00:00+08:00",
  "toTransactAt": "2024-03-31T23:59:59+08:00"
}
```

> 带游标的后续请求示例：

```json
{
  "limit": 20,
  "cursor": "eyJwYWdlIjoyMX0=",
  "currency": "USD",
  "fromTransactAt": "2024-01-01T00:00:00+08:00",
  "toTransactAt": "2024-03-31T23:59:59+08:00"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `nextCursor` | string | 下一页游标；最后一页为 `null` |
| `prevCursor` | string | 上一页游标；第一页为 `null` |
| `items` | array[BalanceHistoryRecord] | 余额变动记录列表，按 `transactedAt` 降序 |

### BalanceHistoryRecord Object

| Field | Type | Description |
|-------|------|-------------|
| `id` | string | 记录唯一标识 |
| `currency` | string | ISO 4217 币种代码 |
| `changeAmount` | Amount | 变动金额（正数为入账，负数为出账） |
| `balanceAmount` | Amount | 变动后余额快照 |
| `description` | string | 变动描述 |
| `transactedAt` | datetime | 交易时间（ISO 8601） |

### Amount Object

| Field | Type | Description |
|-------|------|-------------|
| `currency` | string | 币种代码 |
| `value` | integer | 金额值，最小货币单位（如 USD 100.00 → value = 10000） |

### 响应示例

```json
{
  "result": {
    "resultStatus": "S",
    "resultCode": "SUCCESS",
    "resultMessage": "success"
  },
  "nextCursor": "eyJwYWdlIjoyMX0=",
  "prevCursor": null,
  "items": [
    {
      "id": "rec_20240315_001",
      "currency": "USD",
      "changeAmount": {
        "currency": "USD",
        "value": 500000
      },
      "balanceAmount": {
        "currency": "USD",
        "value": 1500000
      },
      "description": "Incoming transfer",
      "transactedAt": "2024-03-15T10:30:00+08:00"
    },
    {
      "id": "rec_20240310_002",
      "currency": "USD",
      "changeAmount": {
        "currency": "USD",
        "value": -200000
      },
      "balanceAmount": {
        "currency": "USD",
        "value": 1000000
      },
      "description": "Payout to supplier",
      "transactedAt": "2024-03-10T14:20:00+08:00"
    }
  ]
}
```

## 错误码

| resultCode | resultStatus | 说明 |
|------------|--------------|------|
| `SUCCESS` | S | 调用成功 |
| `PARAM_ILLEGAL` | F | 参数非法，不可重试 |
| `INVALID_CURSOR` | F | 游标无效或已过期，不可重试 |
| `ACCOUNT_NOT_EXIST` | F | 账户不存在，不可重试 |
| `CONTRACT_CHECK_FAIL` | F | 合约校验失败，不可重试 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权不存在，不可重试 |
| `UNKNOWN_EXCEPTION` | U | 未知异常，可重试 |

## 示例代码

参考 [references/accounts/balance/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
balance/java/
├── service/
│   └── BalanceService.java                       # 薄封装 Service，包含 listBalanceHistory 方法
└── model/
    ├── domain/
    │   └── BalanceHistoryRecord.java             # 变动记录（id, currency, changeAmount, balanceAmount, description, transactedAt）
    # 说明：Amount 定义在 common 模块
    ├── request/
    │   └── ListBalanceHistoryRequest.java        # 请求参数（limit, cursor, currency, fromTransactAt, toTransactAt）
    └── response/
        └── ListBalanceHistoryResponse.java       # 响应结果（result, nextCursor, prevCursor, items）
```

## 集成使用方式

BalanceService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `ListBalanceHistoryRequest` 设置查询条件（首次请求不传 `cursor`）
2. 调用 `BalanceService.listBalanceHistory(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理变动记录
4. 若 `nextCursor` 不为 `null`，将其作为 `cursor` 传入下一次请求，重复步骤 1-3

### 业务代码示例

```java
// 构造请求
ListBalanceHistoryRequest request = new ListBalanceHistoryRequest();
request.setLimit(20);
request.setCurrency("USD");
request.setFromTransactAt("2024-01-01T00:00:00+08:00");
request.setToTransactAt("2024-03-31T23:59:59+08:00");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
ListBalanceHistoryResponse response = balanceService.listBalanceHistory(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    // 处理变动记录
    for (BalanceHistoryRecord record : response.getItems()) {
        System.out.println(record.getTransactedAt() + " " + record.getCurrency()
            + " 变动: " + record.getChangeAmount().getValue()
            + " 余额: " + record.getBalanceAmount().getValue()
            + " 描述: " + record.getDescription());
    }

    // 游标分页：继续查询下一页
    if (response.getNextCursor() != null) {
        request.setCursor(response.getNextCursor());
        // 再次调用 balanceService.listBalanceHistory(request) 获取下一页数据
    }
} else {
    // 处理错误
    System.err.println("查询失败: " + response.getResult().getResultMessage());
}
```

### 游标分页完整遍历示例

```java
List<BalanceHistoryRecord> allRecords = new ArrayList<>();
String cursor = null;

do {
    ListBalanceHistoryRequest request = new ListBalanceHistoryRequest();
    request.setLimit(100);
    request.setCurrency("USD");
    request.setFromTransactAt("2024-01-01T00:00:00+08:00");
    request.setToTransactAt("2024-03-31T23:59:59+08:00");
    if (cursor != null) {
        request.setCursor(cursor);
    }

    // 调用 Service（内部已强制验签，验签失败抛 WfException）
    ListBalanceHistoryResponse response = balanceService.listBalanceHistory(request);
    if (!"S".equals(response.getResult().getResultStatus())) {
        throw new RuntimeException("查询失败: " + response.getResult().getResultMessage());
    }

    allRecords.addAll(response.getItems());
    cursor = response.getNextCursor();
} while (cursor != null);

System.out.println("共查询到 " + allRecords.size() + " 条变动记录");
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 BalanceService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new BalanceService(config)` 创建实例。
