# Query Rate History 接口接入指引

## 接口说明

查询指定货币对在时间范围内的历史汇率数据，按小时粒度返回。当查询范围包含当前时间时，还会返回最新汇率快照。

## 官方文档

- [query_rate_history 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/query_rate_history)

## 请求地址

`POST /api/open/v1/fx/rates/queryHistory`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-04-02T10:00:00+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `sellCurrency` | string | Yes | 卖出币种（ISO 4217 三字母代码） |
| `buyCurrency` | string | Yes | 买入币种（ISO 4217 三字母代码） |
| `startTime` | datetime | Yes | 查询时间范围起始（ISO 8601 格式） |
| `endTime` | datetime | Yes | 查询时间范围结束（ISO 8601 格式），按小时间隔返回汇率数据直到此时间 |

### 请求示例

```json
{
  "sellCurrency": "USD",
  "buyCurrency": "HKD",
  "startTime": "2026-04-01T00:00:00Z",
  "endTime": "2026-04-02T00:00:00Z"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `sellCurrency` | string | 卖出币种（回显请求值） |
| `buyCurrency` | string | 买入币种（回显请求值） |
| `currentRate` | RateHistoryDetail | 最新汇率快照，当查询时间范围包含当前时间且汇率服务可提供实时报价时返回 |
| `historicalRates` | array[RateHistoryDetail] | 历史汇率快照列表（按小时间隔），最多 720 个元素 |

### RateHistoryDetail Object

| Field | Type | Description |
|-------|------|-------------|
| `clientRate` | decimal | 客户端汇率，8 位小数 |
| `marketRate` | decimal | 市场参考汇率，8 位小数，仅当汇率服务提供市场汇率时返回 |
| `time` | datetime | 汇率快照时间戳（ISO 8601 格式） |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "sellCurrency": "USD",
  "buyCurrency": "HKD",
  "currentRate": {
    "clientRate": 7.78500000,
    "marketRate": 7.78000000,
    "time": "2026-04-02T10:00:00Z"
  },
  "historicalRates": [
    {
      "clientRate": 7.78300000,
      "marketRate": 7.77800000,
      "time": "2026-04-01T00:00:00Z"
    },
    {
      "clientRate": 7.78450000,
      "marketRate": 7.77950000,
      "time": "2026-04-01T01:00:00Z"
    }
  ]
}
```

## 错误码

| resultCode | resultStatus | 说明 |
|------------|--------------|------|
| `SUCCESS` | S | 查询成功 |
| `UNKNOWN_EXCEPTION` | U | 未知异常，联系技术支持 |
| `PARAM_ILLEGAL` | F | 参数非法，检查必填字段和时间范围 |
| `USER_NOT_EXIST` | F | 用户不存在或未完成 KYC 验证 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权关系不存在 |
| `CURRENCY_NOT_SUPPORTED` | F | 指定的币种不支持 |

## 示例代码

参考 [references/foreign-exchange/quote/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
quote/java/
├── service/
│   └── QuoteService.java                              # 薄封装 Service，包含 queryRateHistory 方法
└── model/
    ├── domain/
    │   └── RateHistoryDetail.java                     # 历史汇率详情（clientRate, marketRate, time）
    ├── request/
    │   └── QueryRateHistoryRequest.java               # 请求参数（sellCurrency, buyCurrency, startTime, endTime）
    └── response/
        └── QueryRateHistoryResponse.java              # 响应结果（result, sellCurrency, buyCurrency, currentRate, historicalRates）
```

## 集成使用方式

QuoteService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `QueryRateHistoryRequest` 设置货币对和时间范围
2. 调用 `QuoteService.queryRateHistory(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理历史汇率数据

### 业务代码示例

```java
// 构造请求
QueryRateHistoryRequest request = new QueryRateHistoryRequest();
request.setSellCurrency("USD");
request.setBuyCurrency("HKD");
request.setStartTime("2026-04-01T00:00:00Z");
request.setEndTime("2026-04-02T00:00:00Z");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
QueryRateHistoryResponse response = quoteService.queryRateHistory(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("货币对: " + response.getSellCurrency() + "/" + response.getBuyCurrency());
    
    // 处理当前汇率（如果查询范围包含当前时间）
    if (response.getCurrentRate() != null) {
        RateHistoryDetail currentRate = response.getCurrentRate();
        System.out.println("当前汇率: " + currentRate.getClientRate());
        System.out.println("市场汇率: " + currentRate.getMarketRate());
        System.out.println("时间: " + currentRate.getTime());
    }
    
    // 处理历史汇率列表
    if (response.getHistoricalRates() != null && !response.getHistoricalRates().isEmpty()) {
        System.out.println("\n历史汇率数据（共 " + response.getHistoricalRates().size() + " 条）:");
        for (RateHistoryDetail rate : response.getHistoricalRates()) {
            System.out.println("时间: " + rate.getTime() + 
                             ", 客户端汇率: " + rate.getClientRate() + 
                             ", 市场汇率: " + rate.getMarketRate());
        }
    }
} else {
    // 处理错误
    System.err.println("查询失败: " + response.getResult().getResultMessage());
}
```

### 汇率趋势分析示例

```java
// 查询过去 7 天的汇率历史
QueryRateHistoryRequest request = new QueryRateHistoryRequest();
request.setSellCurrency("USD");
request.setBuyCurrency("HKD");
request.setStartTime("2026-03-26T00:00:00Z");
request.setEndTime("2026-04-02T00:00:00Z");

QueryRateHistoryResponse response = quoteService.queryRateHistory(request);

if ("S".equals(response.getResult().getResultStatus()) && response.getHistoricalRates() != null) {
    List<RateHistoryDetail> rates = response.getHistoricalRates();
    
    // 计算最高、最低、平均汇率
    double maxRate = 0, minRate = Double.MAX_VALUE, sumRate = 0;
    for (RateHistoryDetail rate : rates) {
        double clientRate = Double.parseDouble(rate.getClientRate());
        maxRate = Math.max(maxRate, clientRate);
        minRate = Math.min(minRate, clientRate);
        sumRate += clientRate;
    }
    double avgRate = sumRate / rates.size();
    
    System.out.println("汇率统计（过去 7 天）:");
    System.out.println("最高汇率: " + maxRate);
    System.out.println("最低汇率: " + minRate);
    System.out.println("平均汇率: " + String.format("%.8f", avgRate));
    System.out.println("数据点数: " + rates.size());
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 QuoteService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new QuoteService(config)` 创建实例。
