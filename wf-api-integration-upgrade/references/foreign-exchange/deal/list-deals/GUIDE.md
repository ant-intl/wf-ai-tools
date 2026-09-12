# List Deals 接口接入指引

## 接口说明

分页查询 FX 交易记录列表，支持按卖出币种、买入币种、交易状态和创建时间范围过滤。采用游标分页，适用于交易对账、批量跟踪交易状态等场景。

## 官方文档

- [list_deals 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/list_deals)
- 枚举取值（`DealType`、`DealStatus`、`DealMode`）见 [Deal Overview](https://docs.worldfirst.com/wfdocs/api-sdk/deal_overview)

## 请求地址

`POST /api/open/v1/fx/deals/list`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-04-02T04:09:30+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `limit` | integer | No | 每页记录数，取值范围 1-100；不传时默认 20 |
| `cursor` | string | No | 分页游标；首次请求省略，后续请求传入上次响应的 `nextCursor` 或 `prevCursor` |
| `sellCurrency` | string | No | 按卖出币种过滤（ISO 4217 三字母代码，如 `USD`） |
| `buyCurrency` | string | No | 按买入币种过滤（ISO 4217 三字母代码，如 `HKD`） |
| `status` | string | No | 按交易状态过滤（DealStatus 枚举） |
| `fromCreatedAt` | datetime | No | 创建时间范围起始（ISO 8601 格式），与 `toCreatedAt` 配合使用时最大跨度 31 天 |
| `toCreatedAt` | datetime | No | 创建时间范围结束（ISO 8601 格式） |

### 游标分页说明

- **首次请求**：不传 `cursor`，API 返回 `nextCursor`
- **后续请求**：将上次响应的 `nextCursor` 作为 `cursor` 传入
- **最后一页**：`nextCursor` 为 `null`，表示无更多数据
- **时间范围**：`fromCreatedAt` 与 `toCreatedAt` 的最大跨度为 31 天，超范围查询需缩小时间窗口分批拉取

### 请求示例

```json
{
  "limit": 20,
  "sellCurrency": "USD",
  "buyCurrency": "HKD",
  "status": "SUCCESS"
}
```

> 带游标的后续请求示例：

```json
{
  "limit": 20,
  "cursor": "eyJsYXN0X2lkIjoiREVBTDAyMCJ9",
  "sellCurrency": "USD",
  "buyCurrency": "HKD",
  "status": "SUCCESS"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `items` | array[DealRecord] | 交易记录列表 |
| `nextCursor` | string | 下一页游标，有更多结果时返回 |
| `prevCursor` | string | 上一页游标，存在上一页时返回 |

### DealRecord Object

| Field | Type | Description |
|-------|------|-------------|
| `id` | string | 交易唯一标识符 |
| `dealType` | string | 交易类型（DealType 枚举） |
| `quote` | DealQuote | 本笔交易使用的报价信息 |
| `sellAmount` | Amount | 卖出金额 |
| `buyAmount` | Amount | 买入金额 |
| `creditSupport` | CreditSupport | 保证金与信用额度信息，仅 `dealType` 为 FORWARD 时返回 |
| `settlementPeriod` | SettlementPeriod | 结算周期信息，`dealType` 为 UNFUNDED_SPOT 或 FORWARD 时返回 |
| `unSettleBuyAmount` | Amount | 未结算买入金额，`dealType` 为 UNFUNDED_SPOT/FORWARD 且 `status` 为 PROCESSING/CANCELLED 时返回 |
| `unSettleSellAmount` | Amount | 未结算卖出金额，条件同上 |
| `cancelFeeAmount` | Amount | 取消费用金额，`status` 为 CANCELLED 时返回 |
| `status` | string | 当前交易状态（DealStatus 枚举） |
| `createdAt` | datetime | 交易创建时间（ISO 8601 格式） |
| `settledAt` | datetime | 交易结算时间，`status` 为 SUCCESS 时返回 |
| `cancelledAt` | datetime | 交易取消时间，`status` 为 CANCELLED 时返回 |
| `failedAt` | datetime | 交易失败时间，`status` 为 FAILED 时返回 |
| `failureCode` | string | 失败结果码，`status` 为 FAILED 时返回 |
| `failureMessage` | string | 失败原因描述，`status` 为 FAILED 时返回 |

### DealQuote Object

| Field | Type | Description |
|-------|------|-------------|
| `quoteId` | string | 创建本笔交易使用的报价 ID |
| `currencyPair` | string | 货币对，格式为 `sell/buy`（如 `USD/HKD`） |
| `clientRate` | decimal | 成交汇率，8 位小数 |
| `effectiveAt` | datetime | 报价生效时间（ISO 8601 格式） |
| `expiresAt` | datetime | 报价过期时间（ISO 8601 格式） |

### CreditSupport Object

| Field | Type | Description |
|-------|------|-------------|
| `marginCurrency` | string | 保证金币种（ISO 4217） |
| `marginAmount` | Amount | 本笔交易冻结的保证金金额 |
| `creditLineAmount` | Amount | 本笔交易占用的信用额度 |
| `creditLineBaseAmount` | Amount | 本笔交易的信用额度基准金额 |

### SettlementPeriod Object

| Field | Type | Description |
|-------|------|-------------|
| `startDate` | string | 结算开始日期（`YYYY-MM-DD`），`FORWARD` 且 `mode` 为 `WINDOWED` 时返回 |
| `endDate` | string | 结算结束日期（`YYYY-MM-DD`） |
| `mode` | string | 结算日期模式（DealMode 枚举）：`FIXED`、`FLEXIBLE`、`WINDOWED` |

### Amount Object

| Field | Type | Description |
|-------|------|-------------|
| `currency` | string | 币种代码（ISO-4217） |
| `value` | integer | 金额值，最小货币单位（如 USD 100.00 → value = 10000） |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "nextCursor": "eyJsYXN0X2lkIjoiREVBTDAyMCJ9",
  "items": [
    {
      "id": "DEAL20260402001",
      "dealType": "UNFUNDED_SPOT",
      "quote": {
        "quoteId": "QT202604021208001",
        "currencyPair": "USD/HKD",
        "clientRate": "7.78500000",
        "effectiveAt": "2026-04-02T04:08:56Z",
        "expiresAt": "2026-04-02T04:09:56Z"
      },
      "sellAmount": { "currency": "USD", "value": 10000 },
      "buyAmount": { "currency": "HKD", "value": 77850 },
      "status": "SUCCESS",
      "createdAt": "2026-04-02T04:09:35Z",
      "settledAt": "2026-04-02T06:09:35Z"
    }
  ]
}
```

## 错误码

| resultCode | resultStatus | 说明 | Troubleshooting |
|------------|--------------|------|-----------------|
| `SUCCESS` | S | 查询成功 | — |
| `PARAM_ILLEGAL` | F | 参数非法 | 检查 `limit` 是否在 1-100 区间，`fromCreatedAt`/`toCreatedAt` 是否为合法 ISO 8601 格式且跨度不超过 31 天 |
| `USER_NOT_EXIST` | F | 用户不存在或未完成 KYC 验证 | 完成 KYC 验证后再查询交易 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权关系不存在 | 确认 `account-id` 请求头引用的是您 client 下已授权的子商户 |
| `UNKNOWN_EXCEPTION` | U | 未知异常，可重试 | 稍后重试，若问题持续联系技术支持 |

## 示例代码

参考 [references/foreign-exchange/deal/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
deal/java/
├── service/
│   └── DealService.java                               # 薄封装 Service，包含 listDeals 方法
└── model/
    ├── domain/
    │   ├── DealQuote.java                             # 交易关联的报价信息
    │   └── DealRecord.java                            # 交易记录（用于 list 响应的 items）
    ├── request/
    │   └── ListDealsRequest.java                      # 请求参数（limit, cursor, sellCurrency, buyCurrency, status, fromCreatedAt, toCreatedAt）
    └── response/
        └── ListDealsResponse.java                     # 响应结果（result, items, nextCursor, prevCursor）
```

> `Amount`、`CreditSupport`、`SettlementPeriod`、`Result` 为通用对象，复用 `model/domain`、`model/response` 包下的已有定义。

## 集成使用方式

DealService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `ListDealsRequest` 设置查询条件（首次请求不传 `cursor`）
2. 调用 `DealService.listDeals(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理交易记录
4. 若 `nextCursor` 不为 `null`，将其作为 `cursor` 传入下一次请求，重复步骤 1-3

### 业务代码示例

```java
// 构造请求
ListDealsRequest request = new ListDealsRequest();
request.setLimit(20);
request.setSellCurrency("USD");
request.setBuyCurrency("HKD");
request.setStatus("SUCCESS");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
ListDealsResponse response = dealService.listDeals(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    for (DealRecord deal : response.getItems()) {
        System.out.println("交易 ID: " + deal.getId());
        System.out.println("交易类型: " + deal.getDealType());
        System.out.println("状态: " + deal.getStatus());
        System.out.println("汇率: " + deal.getQuote().getClientRate());
        System.out.println("卖出: " + deal.getSellAmount().getValue() + " " + deal.getSellAmount().getCurrency());
        System.out.println("买入: " + deal.getBuyAmount().getValue() + " " + deal.getBuyAmount().getCurrency());
    }

    // 游标分页：继续查询下一页
    if (response.getNextCursor() != null) {
        request.setCursor(response.getNextCursor());
        // 再次调用 dealService.listDeals(request) 获取下一页数据
    }
} else {
    System.err.println("查询失败: " + response.getResult().getResultCode()
        + " - " + response.getResult().getResultMessage());
}
```

### 游标分页完整遍历示例

```java
List<DealRecord> allDeals = new ArrayList<>();
String cursor = null;

do {
    ListDealsRequest request = new ListDealsRequest();
    request.setLimit(100);
    request.setSellCurrency("USD");
    request.setFromCreatedAt("2026-03-01T00:00:00Z");
    request.setToCreatedAt("2026-03-31T23:59:59Z");
    if (cursor != null) {
        request.setCursor(cursor);
    }

    // 调用 Service（内部已强制验签，验签失败抛 WfException）
    ListDealsResponse response = dealService.listDeals(request);
    if (!"S".equals(response.getResult().getResultStatus())) {
        throw new RuntimeException("查询失败: " + response.getResult().getResultMessage());
    }

    allDeals.addAll(response.getItems());
    cursor = response.getNextCursor();
} while (cursor != null);

System.out.println("共查询到 " + allDeals.size() + " 笔交易");
```

### 待结算交易扫描示例

```java
// 扫描处于 PROCESSING 的远期/无资金即期交易，推进结算
ListDealsRequest request = new ListDealsRequest();
request.setLimit(100);
request.setStatus("PROCESSING");

ListDealsResponse response = dealService.listDeals(request);

if ("S".equals(response.getResult().getResultStatus())) {
    for (DealRecord deal : response.getItems()) {
        if ("SPOT".equals(deal.getDealType())) {
            // SPOT 自动结算，无需处理
            continue;
        }
        // UNFUNDED_SPOT / FORWARD 需显式结算
        if (deal.getSettlementPeriod() != null) {
            System.out.println("交易 " + deal.getId()
                + " 结算截止: " + deal.getSettlementPeriod().getEndDate());
        }
        if (deal.getUnSettleSellAmount() != null) {
            System.out.println("  待结算卖出: " + deal.getUnSettleSellAmount().getValue()
                + " " + deal.getUnSettleSellAmount().getCurrency());
        }
    }
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 DealService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new DealService(config)` 创建实例。
