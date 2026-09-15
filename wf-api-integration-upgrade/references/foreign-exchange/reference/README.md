# 外汇参考数据（Reference）模块

## 官方文档

- [WorldFirst 开发者文档 - Reference Overview](https://docs.worldfirst.com/wfdocs/api-sdk/reference_overview)
- [query_trading_calendar](https://docs.worldfirst.com/wfdocs/api-sdk/query_trading_calendar) | [query_supported_currencies](https://docs.worldfirst.com/wfdocs/api-sdk/query_supported_currencies)

## 接口列表

| 接口 | 目录 | 说明 | Endpoint |
|------|------|------|----------|
| 查询交易日历 | `query-calendar/GUIDE.md` | 查询指定货币对和交易类型在日期范围内的可用交易日或结算日 | `POST /api/open/v1/fx/references/queryCalendar` |
| 查询支持币种 | `query-supported-currencies/GUIDE.md` | 查询 FX 交易支持的货币对列表，可按交易类型过滤 | `POST /api/open/v1/fx/references/queryCurrencies` |

## ReferenceService 说明

`ReferenceService` 是外汇参考数据模块的服务入口，采用**薄封装模式**：

- 不做参数校验（由调用方或 WF 服务端负责）
- 验签后自动反序列化响应体，直接返回业务响应对象（如 `QueryCalendarResponse`）
- 自动注入签名与验签（由底层 `WfApiClient` 完成，验签失败抛 `WfException`）

### 方法列表

| 方法 | 请求类型 | 响应类型 | 说明 |
|------|----------|----------|------|
| `queryCalendar(QueryCalendarRequest)` | `QueryCalendarRequest` | `QueryCalendarResponse` | 查询交易日历 |
| `querySupportedCurrencies(QuerySupportedCurrenciesRequest)` | `QuerySupportedCurrenciesRequest` | `QuerySupportedCurrenciesResponse` | 查询支持的货币对列表 |

### 构造器

| 构造器 | 说明 |
|--------|------|
| `ReferenceService(WfClientConfig config)` | 传入配置，内部创建 `WfApiClient` |
| `ReferenceService(WfApiClient apiClient)` | 传入已构造的 `WfApiClient`，便于测试注入 |

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
ReferenceService referenceService = new ReferenceService(config);
```

### 3. 调用方法（内部已强制验签，验签失败抛 WfException）

```java
// 查询交易日历
QueryCalendarRequest request = new QueryCalendarRequest();
request.setDealType("FORWARD");
request.setStartDate("2026-04-02");
request.setEndDate("2026-04-30");
request.setBuyCurrency("USD");
request.setSellCurrency("HKD");

QueryCalendarResponse response = referenceService.queryCalendar(request);

// 查询支持的货币对
QuerySupportedCurrenciesRequest currenciesRequest = new QuerySupportedCurrenciesRequest();
currenciesRequest.setDealType("FORWARD");

QuerySupportedCurrenciesResponse currenciesResponse = referenceService.querySupportedCurrencies(currenciesRequest);
```

### 4. 检查业务结果

```java
if ("S".equals(response.getResult().getResultStatus())) {
    List<String> availableDates = response.getAvailableDates();
    System.out.println("可用交易日数量: " + availableDates.size());
    for (String date : availableDates) {
        System.out.println("  " + date);
    }
}

if ("S".equals(currenciesResponse.getResult().getResultStatus())) {
    for (CurrencyPair pair : currenciesResponse.getCurrencyPairs()) {
        System.out.println("货币对: " + pair.getSellCurrency() + "/" + pair.getBuyCurrency());
    }
}
```

## 注意事项

### query_trading_calendar
- `dealType`、`startDate`、`endDate`、`buyCurrency`、`sellCurrency` 均为必填
- `calendarType` 为可选字段，默认为 `DEAL_CALENDAR`（交易日历）
- `startDate` 和 `endDate` 格式为 `YYYY-MM-DD`（UTC），`startDate` 必须早于 `endDate`
- `buyCurrency` 和 `sellCurrency` 遵循 ISO 4217 标准三字母货币代码
- `dealType` 有效值：`SPOT`（即期）、`UNFUNDED_SPOT`（无资金即期）、`FORWARD`（远期）
- `calendarType` 有效值：`DEAL_CALENDAR`（交易日历，默认）、`SETTLEMENT_CALENDAR`（结算日历）
- 响应 `availableDates` 为 `YYYY-MM-DD` 格式的日期字符串列表

### query_supported_currencies
- `dealType` 必填，有效值：`SPOT`、`UNFUNDED_SPOT`、`FORWARD`
- 响应 `currencyPairs` 为 `CurrencyPair` 列表，每项由 `sellCurrency` + `buyCurrency` 组成，均为 ISO 4217 三字母代码
- 建议在创建报价（`create_quote`）前调用本接口校验目标货币对是否支持，避免报价创建失败
- 本接口为参考数据查询，无分页，一次性返回全部匹配货币对

## 枚举类型说明

### DealType（交易类型）
- `SPOT`: 即期交易日历
- `UNFUNDED_SPOT`: 无资金即期交易日历
- `FORWARD`: 远期交易日历

### CalendarType（日历类型）
- `DEAL_CALENDAR`: 交易日历，显示可创建交易的日期（默认）
- `SETTLEMENT_CALENDAR`: 结算日历，显示可完成结算的日期

## 文件结构

```
reference/
├── README.md
├── java/
│   ├── service/
│   │   └── ReferenceService.java                      # 外汇参考数据服务（薄封装）
│   └── model/
│       ├── domain/
│       │   └── CurrencyPair.java                      # 支持的货币对（用于 currencyPairs 列表）
│       ├── request/
│       │   ├── QueryCalendarRequest.java               # 查询交易日历请求
│       │   └── QuerySupportedCurrenciesRequest.java    # 查询支持币种请求（dealType）
│       └── response/
│           ├── QueryCalendarResponse.java              # 查询交易日历响应
│           └── QuerySupportedCurrenciesResponse.java   # 查询支持币种响应
├── query-calendar/
│   └── GUIDE.md                                       # query_trading_calendar 接口接入指引
└── query-supported-currencies/
    └── GUIDE.md                                       # query_supported_currencies 接口接入指引
```

> **复用 domain 对象**：`Result` 等通用对象与其他模块共享，引用 `{basePackage}.wf.model.domain` 包下的已有定义。`CurrencyPair` 为本模块独有的 domain 对象。
