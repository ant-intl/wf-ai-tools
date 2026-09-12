# List Daily Balances 接口接入指引

## 接口说明

按日期区间查询各预算账户的**日终余额**与**当日资金流入/流出**汇总，采用游标分页。可按单个预算账户与币种过滤。`startDate` 与 `endDate` 区间跨度**不得超过 31 天**，超出返回 `PARAM_ILLEGAL`。

> 本接口列表字段名为 `dailyBalances`（不是 `items`），分页以响应中 `nextCursor` 是否返回为准。

## 官方文档

- [list_daily_balances 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/list_daily_balances)

## 请求地址

`POST /api/open/v1/issuing/budgets/listDailyBalance`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-08-08T08:00:00+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `startDate` | string | Yes | 查询起始日期（客户本地时区），格式 `yyyy-MM-dd`；须早于或等于 `endDate` |
| `endDate` | string | Yes | 查询结束日期（客户本地时区），格式 `yyyy-MM-dd`；与 `startDate` 跨度不超过 31 天 |
| `budgetId` | string | No | 预算账户 ID，最大 64 字符；不传时返回当前账户下全部预算账户的每日余额 |
| `currencies` | array[string] | No | 币种过滤，每项为 ISO 4217 三字母货币代码，最多 10 项；不传时返回全部币种 |
| `cursor` | string | No | 分页游标；首次请求省略，后续传入上一次响应的 `nextCursor` 或 `prevCursor` |
| `limit` | integer | No | 每页记录数，取值范围 1-100，不传时默认 20 |

### 请求示例

```json
{
  "startDate": "2026-06-01",
  "endDate": "2026-06-07",
  "budgetId": "BUDGET_20260401100101****",
  "currencies": ["USD", "EUR"],
  "cursor": "1",
  "limit": 20
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `dailyBalances` | array[DailyBalanceRecord] | 每日余额列表；无数据时返回空列表 |
| `nextCursor` | string | 下一页游标；未返回表示已到最后一页 |
| `prevCursor` | string | 上一页游标；未返回表示当前为第一页 |

### DailyBalanceRecord Object

| Field | Type | Description |
|-------|------|-------------|
| `budgetId` | string | 预算账户唯一标识，最大 64 字符 |
| `date` | string | 余额日期，格式 `yyyy-MM-dd` |
| `endOfDayBalance` | Amount | 日终余额合计 |
| `dailyFundInAmount` | Amount | 当日资金流入合计 |
| `dailyFundOutAmount` | Amount | 当日资金流出合计 |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "dailyBalances": [
    {
      "budgetId": "BUDGET_20260401100101****",
      "date": "2026-06-01",
      "endOfDayBalance": { "currency": "USD", "value": 88000000 },
      "dailyFundInAmount": { "currency": "USD", "value": 0 },
      "dailyFundOutAmount": { "currency": "USD", "value": 0 }
    }
  ],
  "nextCursor": "eyJxxxx",
  "prevCursor": "edSxxx"
}
```

## 错误码

| resultCode | resultStatus | 说明 | Troubleshooting |
|------------|--------------|------|-----------------|
| `SUCCESS` | S | 查询成功 | - |
| `PARAM_ILLEGAL` | F | 参数非法 | 核对日期格式与区间跨度（≤31 天）、币种代码（三字母、≤10 项）等字段 |
| `USER_NO_PERMISSION` | F | 用户无权限 | 改用对目标预算账户有权限的用户重试 |
| `SYSTEM_ERROR` | F | 系统异常 | 契约标注 `resultStatus=F`，但排查建议为稍后重试；重试须保持查询条件一致（本接口为只读，无幂等风险） |

> ⚠️ 本接口错误码列表未包含 `UNKNOWN_EXCEPTION` / `PROCESS_FAIL`，与其他 budgets 接口不同；服务端仍可能返回通用错误码，健壮性处理时按 `resultStatus` 分支兜底即可。

## 示例代码

参考 [references/issuing/budgets/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
budgets/java/
├── service/
│   └── BudgetService.java                             # 薄封装 Service，包含 listDailyBalances 方法
└── model/
    ├── domain/
    │   └── DailyBalanceRecord.java                    # 每日余额记录（budgetId, date, endOfDayBalance, dailyFundIn/OutAmount）
    ├── request/
    │   └── ListDailyBalancesRequest.java              # 请求参数（startDate, endDate, budgetId, currencies, cursor, limit）
    └── response/
        └── ListDailyBalancesResponse.java             # 响应结果（result, dailyBalances, nextCursor, prevCursor）
```

> `Amount` 复用 `model/domain` 下的通用金额对象；`Result` 复用 `model/response` 下的已有定义。

## 集成使用方式

BudgetService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `ListDailyBalancesRequest`，设置 `startDate`/`endDate`（跨度 ≤31 天），按需设置 `budgetId`、`currencies`、`limit`
2. 调用 `BudgetService.listDailyBalances(request)` 获取响应（内部已强制验签）
3. 检查 `result.resultStatus` 是否为 `S`，遍历 `dailyBalances`
4. 若响应返回 `nextCursor`，将其设置到下一次请求的 `cursor` 继续翻页，直至 `nextCursor` 缺失

### 业务代码示例

```java
ListDailyBalancesRequest request = new ListDailyBalancesRequest();
request.setStartDate("2026-06-01");
request.setEndDate("2026-06-07");
request.setCurrencies(Arrays.asList("USD", "EUR"));
request.setLimit(20);

ListDailyBalancesResponse response = budgetService.listDailyBalances(request);

if ("S".equals(response.getResult().getResultStatus())) {
    for (DailyBalanceRecord rec : response.getDailyBalances()) {
        System.out.println(rec.getBudgetId() + " " + rec.getDate()
            + " EOD=" + rec.getEndOfDayBalance().getCurrency() + ":" + rec.getEndOfDayBalance().getValue()
            + " in=" + rec.getDailyFundInAmount().getValue()
            + " out=" + rec.getDailyFundOutAmount().getValue());
    }
}
```

### 游标分页遍历示例

```java
ListDailyBalancesRequest request = new ListDailyBalancesRequest();
request.setStartDate("2026-06-01");
request.setEndDate("2026-06-30");                     // 恰好 30 天跨度，仍在 31 天内
request.setLimit(100);

String cursor = null;
do {
    request.setCursor(cursor);
    ListDailyBalancesResponse page = budgetService.listDailyBalances(request);
    if (!"S".equals(page.getResult().getResultStatus())) {
        throw new IllegalStateException("查询失败: " + page.getResult().getResultCode());
    }
    for (DailyBalanceRecord rec : page.getDailyBalances()) {
        // 逐条处理
    }
    cursor = page.getNextCursor();                   // 缺失即表示已到最后一页
} while (cursor != null);
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 BudgetService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new BudgetService(config)` 创建实例。
