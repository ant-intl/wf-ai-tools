# 外汇报价（Quote）模块

## 官方文档

- [WorldFirst 开发者文档 - Quote Overview](https://docs.worldfirst.com/wfdocs/api-sdk/quote_overview)
- [query_rates](https://docs.worldfirst.com/wfdocs/api-sdk/query_rates) | [query_rate_history](https://docs.worldfirst.com/wfdocs/api-sdk/query_rate_history) | [create_a_quote](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_quote)

## 接口列表

| 接口 | 目录 | 说明 | Endpoint |
|------|------|------|----------|
| 查询实时汇率 | `query-rates/GUIDE.md` | 批量查询一个或多个货币对的实时汇率 | `POST /api/open/v1/fx/rates/query` |
| 查询历史汇率 | `query-rate-history/GUIDE.md` | 查询指定货币对在时间范围内的历史汇率（按小时粒度） | `POST /api/open/v1/fx/rates/queryHistory` |
| 创建绑定报价 | `create-quote/GUIDE.md` | 生成具有约束力的 FX 报价，锁定汇率用于后续交易 | `POST /api/open/v1/fx/quotes/create` |

## QuoteService 说明

`QuoteService` 是外汇报价模块的服务入口，采用**薄封装模式**：

- 不做参数校验（由调用方或 WF 服务端负责）
- 验签后自动反序列化响应体，直接返回业务响应对象（如 `QueryRatesResponse`）
- 自动注入签名与验签（由底层 `WfApiClient` 完成，验签失败抛 `WfException`）

### 方法列表

| 方法 | 请求类型 | 响应类型 | 说明 |
|------|----------|----------|------|
| `queryRates(QueryRatesRequest)` | `QueryRatesRequest` | `QueryRatesResponse` | 批量查询实时汇率，支持最多 50 个货币对 |
| `queryRateHistory(QueryRateHistoryRequest)` | `QueryRateHistoryRequest` | `QueryRateHistoryResponse` | 查询历史汇率数据，按小时粒度返回 |
| `createQuote(CreateQuoteRequest)` | `CreateQuoteRequest` | `CreateQuoteResponse` | 创建绑定报价，锁定汇率用于后续交易 |

### 构造器

| 构造器 | 说明 |
|--------|------|
| `QuoteService(WfClientConfig config)` | 传入配置，内部创建 `WfApiClient` |
| `QuoteService(WfApiClient apiClient)` | 传入已构造的 `WfApiClient`，便于测试注入 |

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
QuoteService quoteService = new QuoteService(config);
```

### 3. 调用方法（内部已强制验签，验签失败抛 WfException）

```java
// 查询实时汇率
QueryRatesRequest queryRatesRequest = new QueryRatesRequest();
List<RateCondition> conditions = new ArrayList<>();
RateCondition condition = new RateCondition();
condition.setSellAmount(new Amount("USD", 10000));
condition.setBuyAmount(new Amount("HKD", null));
conditions.add(condition);
queryRatesRequest.setRateConditions(conditions);

QueryRatesResponse queryRatesResponse = quoteService.queryRates(queryRatesRequest);

// 查询历史汇率
QueryRateHistoryRequest historyRequest = new QueryRateHistoryRequest();
historyRequest.setSellCurrency("USD");
historyRequest.setBuyCurrency("HKD");
historyRequest.setStartTime("2026-04-01T00:00:00Z");
historyRequest.setEndTime("2026-04-02T00:00:00Z");

QueryRateHistoryResponse historyResponse = quoteService.queryRateHistory(historyRequest);

// 创建绑定报价
CreateQuoteRequest createQuoteRequest = new CreateQuoteRequest();
createQuoteRequest.setSellAmount(new Amount("USD", 10000));
createQuoteRequest.setBuyAmount(new Amount("HKD", null));
createQuoteRequest.setDealType("SPOT");

CreateQuoteResponse createQuoteResponse = quoteService.createQuote(createQuoteRequest);
```

### 4. 检查业务结果

```java
if ("S".equals(queryRatesResponse.getResult().getResultStatus())) {
    for (RateDetail detail : queryRatesResponse.getRateDetails()) {
        System.out.println("货币对: " + detail.getSellAmount().getCurrency() + "/" + detail.getBuyAmount().getCurrency());
        System.out.println("汇率: " + detail.getClientRate());
        System.out.println("创建时间: " + detail.getCreatedAt());
    }
}

if ("S".equals(historyResponse.getResult().getResultStatus())) {
    System.out.println("当前汇率: " + historyResponse.getCurrentRate().getClientRate());
    for (RateHistoryDetail rate : historyResponse.getHistoricalRates()) {
        System.out.println("时间: " + rate.getTime() + ", 汇率: " + rate.getClientRate());
    }
}

if ("S".equals(createQuoteResponse.getResult().getResultStatus())) {
    QuoteDetail quote = createQuoteResponse.getQuote();
    System.out.println("报价 ID: " + quote.getQuoteId());
    System.out.println("汇率: " + quote.getClientRate());
    System.out.println("过期时间: " + quote.getExpiresAt());
}
```

## 注意事项

### query_rates
- `rateConditions` 必填，支持最多 50 个查询条件
- 每个条件中 `sellAmount` 和 `buyAmount` 至少提供一个，且只能有一侧包含 `value`
- 可选 `settlementDate` 参数用于查询特定结算日的汇率
- 响应中的 `rateDetails` 列表顺序与请求中的 `rateConditions` 顺序一致

### query_rate_history
- `sellCurrency`、`buyCurrency`、`startTime`、`endTime` 均为必填
- 时间范围使用 ISO 8601 格式（UTC）
- 返回按小时粒度的历史汇率数据，最多 720 条记录
- 当查询范围包含当前时间时，`currentRate` 字段返回最新汇率

### create_a_quote
- `dealType` 必填，支持 `SPOT`、`UNFUNDED_SPOT`、`FORWARD` 三种类型
- `sellAmount` 和 `buyAmount` 至少提供一个，且只能有一侧包含 `value`
- `FORWARD` 类型必须提供 `creditSupport`（保证金信息）和 `settlementPeriod`（结算周期）
- `UNFUNDED_SPOT` 和 `FORWARD` 类型必须提供 `settlementPeriod`
- `SPOT` 类型禁止提供 `settlementPeriod`
- 报价具有有效期（`expiresAt`），必须在过期前使用 `quoteId` 创建交易

## 枚举类型说明

### DealType（交易类型）
- `SPOT`: 标准即期交易，自动结算
- `UNFUNDED_SPOT`: 无资金即期交易，需要显式结算
- `FORWARD`: 远期交易，需要保证金和结算周期

### SettlementMode（结算模式）
- `FIXED`: 固定结算日期
- `FLEXIBLE`: 灵活结算日期
- `WINDOWED`: 窗口结算周期（需要 `startDate`）

## 文件结构

```
quote/
├── README.md
├── java/
│   ├── service/
│   │   └── QuoteService.java                          # 外汇报价服务（薄封装）
│   └── model/
│       ├── domain/
│       │   ├── CreditSupport.java                     # 信用支持信息（保证金相关）
│       │   ├── SettlementPeriod.java                  # 结算周期信息
│       │   ├── QuoteDetail.java                       # 报价详情（用于 create 响应）
│       │   ├── RateCondition.java                     # 汇率查询条件
│       │   ├── RateDetail.java                        # 汇率详情（用于 query 响应）
│       │   └── RateHistoryDetail.java                 # 历史汇率详情
│       ├── request/
│       │   ├── QueryRatesRequest.java                 # 查询实时汇率请求
│       │   ├── QueryRateHistoryRequest.java           # 查询历史汇率请求
│       │   └── CreateQuoteRequest.java                # 创建报价请求
│       └── response/
│           ├── QueryRatesResponse.java                # 查询实时汇率响应
│           ├── QueryRateHistoryResponse.java          # 查询历史汇率响应
│           └── CreateQuoteResponse.java               # 创建报价响应
├── query-rates/
│   └── GUIDE.md                                       # query_rates 接口接入指引
├── query-rate-history/
│   └── GUIDE.md                                       # query_rate_history 接口接入指引
└── create-quote/
    └── GUIDE.md                                       # create_a_quote 接口接入指引
```
