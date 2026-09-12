# List Statements 接口接入指引

## 接口说明

分页查询对账单列表，支持按交易类型、币种、余额类型和交易时间范围过滤。采用游标分页，适用于对账和审计场景。

## 官方文档

- [list_statements 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/list_statements)

## 请求地址

`POST /api/open/v1/statements/list`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-04-23T10:15:30+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `limit` | integer | Yes | 每页记录数，取值范围 1-100，默认 20 |
| `cursor` | string | No | 分页游标；首次请求省略，后续请求传入上次响应的 `nextCursor` 或 `prevCursor` |
| `fromTransactAt` | datetime | No | 交易时间范围起始（ISO 8601 格式，包含），必须与 `toTransactAt` 配合使用，最大时间跨度 100 天 |
| `toTransactAt` | datetime | No | 交易时间范围结束（ISO 8601 格式，不包含），必须与 `fromTransactAt` 配合使用 |
| `transactionTypes` | array[string] | No | 按交易类型过滤（OR 逻辑），省略返回所有类型 |
| `currencies` | array[string] | No | 按币种过滤（ISO-4217 三字母代码），省略返回所有币种 |
| `balanceTypes` | array[string] | No | 按余额类型过滤，省略默认为 `NORMAL_BALANCE` |
| `budgetAccountIds` | array[string] | No | 预算账户 ID 列表，当 `balanceTypes` 包含 `BUDGET_BALANCE` 时必填 |
| `keyword` | string | No | 模糊搜索关键词，匹配对方名称和备注 |

### 游标分页说明

- **首次请求**：不传 `cursor`，API 返回 `nextCursor`
- **后续请求**：将上次响应的 `nextCursor` 作为 `cursor` 传入
- **最后一页**：`nextCursor` 为 `null`，表示无更多数据
- **排序**：返回记录按 `transactedAt` 降序排列

### 请求示例

```json
{
  "limit": 20,
  "fromTransactAt": "2026-04-01T00:00:00+08:00",
  "toTransactAt": "2026-04-23T23:59:59+08:00",
  "transactionTypes": ["TRANSFER", "COLLECTION"],
  "currencies": ["USD", "GBP"],
  "balanceTypes": ["NORMAL_BALANCE"]
}
```

> 带游标的后续请求示例：

```json
{
  "limit": 20,
  "cursor": "eyJpZCI6IlNUTTIwMjYwNDE4****",
  "fromTransactAt": "2026-04-01T00:00:00+08:00",
  "toTransactAt": "2026-04-23T23:59:59+08:00",
  "transactionTypes": ["TRANSFER", "COLLECTION"],
  "currencies": ["USD", "GBP"],
  "balanceTypes": ["NORMAL_BALANCE"]
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `items` | array[StatementRecord] | 对账单记录列表 |
| `nextCursor` | string | 下一页游标，为空表示无更多数据 |
| `prevCursor` | string | 上一页游标，第一页为 `null` |

### StatementRecord Object

| Field | Type | Description |
|-------|------|-------------|
| `id` | string | 对账单唯一标识 |
| `transactionType` | string | 交易类型（TransactionType 枚举） |
| `status` | string | 对账单状态（StatementStatus 枚举） |
| `transactionAmount` | Amount | 交易金额，正数为入账，负数为出账 |
| `originalTransactionAmount` | Amount | 手续费扣除前的原始交易金额 |
| `feeAmount` | Amount | WorldFirst 收取的手续费 |
| `netAmount` | Amount | 手续费扣除后的净金额 |
| `balanceAmount` | Amount | 交易后的账户余额 |
| `balanceType` | string | 余额类型（BalanceType 枚举） |
| `feeItemType` | string | 费用项类型，仅当 `transactionType` 为 `CHARGE` 时返回 |
| `exchangeRate` | ExchangeRate | 汇率信息，仅涉及币种转换时返回 |
| `fundFlowDetail` | FundFlowDetail | 资金流详情 |
| `transactedAt` | datetime | 交易时间（ISO 8601 格式） |

### Amount Object

| Field | Type | Description |
|-------|------|-------------|
| `currency` | string | 币种代码（ISO-4217） |
| `value` | integer | 金额值，最小货币单位（如 USD 100.00 → value = 10000） |

### ExchangeRate Object

| Field | Type | Description |
|-------|------|-------------|
| `sellCurrency` | string | 卖出币种 |
| `buyCurrency` | string | 买入币种 |
| `rate` | string | 汇率值 |

### FundFlowDetail Object

| Field | Type | Description |
|-------|------|-------------|
| `payerInfo` | PayerInfo | 付款方信息 |
| `beneficiaryInfo` | BeneficiaryInfo | 收款方信息 |

### PayerInfo Object

| Field | Type | Description |
|-------|------|-------------|
| `name` | string | 付款方名称（脱敏） |

### BeneficiaryInfo Object

| Field | Type | Description |
|-------|------|-------------|
| `name` | string | 收款方名称（脱敏） |

### 响应示例

```json
{
  "result": {
    "resultStatus": "S",
    "resultCode": "SUCCESS",
    "resultMessage": "success"
  },
  "items": [
    {
      "id": "STM202604230000****",
      "transactionType": "COLLECTION",
      "status": "SUCCESS",
      "transactionAmount": { "value": 1500000, "currency": "USD" },
      "originalTransactionAmount": { "value": 1500000, "currency": "USD" },
      "balanceAmount": { "value": 5230050, "currency": "USD" },
      "balanceType": "NORMAL_BALANCE",
      "fundFlowDetail": {
        "payerInfo": { "name": "Acme Corp ****" },
        "beneficiaryInfo": { "name": "Global Trade Inc." }
      },
      "transactedAt": "2026-04-20T14:30:00+08:00"
    }
  ],
  "nextCursor": "eyJpZCI6IlNUTTIwMjYwNDE4****",
  "prevCursor": null
}
```

## 错误码

| resultCode | resultStatus | 说明 | Troubleshooting |
|------------|--------------|------|-----------------|
| `SUCCESS` | S | 查询成功 | — |
| `PARAM_ILLEGAL` | F | 参数非法 | 检查 `limit` 范围（1–100），验证日期格式和时间跨度（≤ 100 天） |
| `PROCESS_FAIL` | F | 业务处理失败，不可重试 | 验证商户是否有对应权限 |
| `USER_NOT_EXIST` | F | 用户不存在 | 验证商户账户是否有效且已激活 |
| `SYSTEM_ERROR` | F | 系统错误，不可重试 | 稍后重试 |
| `SERVICE_NOT_ALLOWED` | F | 服务不允许 | 联系 WorldFirst 支持 |
| `CURRENCY_NOT_SUPPORT` | F | 币种不支持 | 从 `currencies` 中移除不支持的币种 |
| `CONTRACT_CHECK_FAIL` | F | 合约校验失败 | 验证商户合约是否包含对账单查询权限 |
| `ACCESS_TOKEN_EXPIRED` | F | 访问令牌过期 | 刷新 OAuth 访问令牌后重试 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权不存在 | 验证 `account-id` 是否属于当前平台商户 |

## 示例代码

参考 [references/reporting/statement-report/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
statement-report/java/
├── service/
│   └── StatementService.java                          # 薄封装 Service，包含 listStatements 方法
└── model/
    ├── domain/
    │   ├── ExchangeRate.java                          # 汇率信息（sellCurrency, buyCurrency, rate）
    │   ├── FundFlowDetail.java                        # 资金流详情（payerInfo, beneficiaryInfo）
    │   ├── PayerInfo.java                             # 付款方信息
    │   ├── BeneficiaryInfo.java                       # 收款方信息
    │   └── StatementRecord.java                       # 对账单记录（用于 list 响应的 items）
    ├── request/
    │   └── ListStatementsRequest.java                 # 请求参数（limit, cursor, fromTransactAt, toTransactAt, transactionTypes, currencies, balanceTypes, budgetAccountIds, keyword）
    └── response/
        └── ListStatementsResponse.java                # 响应结果（result, items, nextCursor, prevCursor）
```

## 集成使用方式

StatementService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `ListStatementsRequest` 设置查询条件（首次请求不传 `cursor`）
2. 调用 `StatementService.listStatements(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理对账单记录
4. 若 `nextCursor` 不为 `null`，将其作为 `cursor` 传入下一次请求，重复步骤 1-3

### 业务代码示例

```java
// 构造请求
ListStatementsRequest request = new ListStatementsRequest();
request.setLimit(20);
request.setFromTransactAt("2026-04-01T00:00:00+08:00");
request.setToTransactAt("2026-04-23T23:59:59+08:00");
request.setTransactionTypes(Arrays.asList("TRANSFER", "COLLECTION"));
request.setCurrencies(Arrays.asList("USD", "GBP"));
request.setBalanceTypes(Arrays.asList("NORMAL_BALANCE"));

// 调用 Service（内部已强制验签，验签失败抛 WfException）
ListStatementsResponse response = statementService.listStatements(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    // 处理对账单记录
    for (StatementRecord record : response.getItems()) {
        System.out.println("对账单 ID: " + record.getId());
        System.out.println("交易类型: " + record.getTransactionType());
        System.out.println("状态: " + record.getStatus());
        System.out.println("交易金额: " + record.getTransactionAmount().getValue() + " " + record.getTransactionAmount().getCurrency());
        System.out.println("交易时间: " + record.getTransactedAt());
    }

    // 游标分页：继续查询下一页
    if (response.getNextCursor() != null) {
        request.setCursor(response.getNextCursor());
        // 再次调用 statementService.listStatements(request) 获取下一页数据
    }
} else {
    // 处理错误
    System.err.println("查询失败: " + response.getResult().getResultMessage());
}
```

### 游标分页完整遍历示例

```java
List<StatementRecord> allStatements = new ArrayList<>();
String cursor = null;

do {
    ListStatementsRequest request = new ListStatementsRequest();
    request.setLimit(100);
    request.setFromTransactAt("2026-04-01T00:00:00+08:00");
    request.setToTransactAt("2026-04-23T23:59:59+08:00");
    request.setTransactionTypes(Arrays.asList("TRANSFER", "COLLECTION"));
    request.setCurrencies(Arrays.asList("USD", "GBP"));
    request.setBalanceTypes(Arrays.asList("NORMAL_BALANCE"));
    if (cursor != null) {
        request.setCursor(cursor);
    }

    // 调用 Service（内部已强制验签，验签失败抛 WfException）
    ListStatementsResponse response = statementService.listStatements(request);
    if (!"S".equals(response.getResult().getResultStatus())) {
        throw new RuntimeException("查询失败: " + response.getResult().getResultMessage());
    }

    allStatements.addAll(response.getItems());
    cursor = response.getNextCursor();
} while (cursor != null);

System.out.println("共查询到 " + allStatements.size() + " 条对账单记录");
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 StatementService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new StatementService(config)` 创建实例。
