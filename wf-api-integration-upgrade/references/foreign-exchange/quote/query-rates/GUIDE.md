# Query Rates 接口接入指引

## 接口说明

批量查询一个或多个货币对的实时汇率。支持最多 50 个查询条件，可指定卖出金额或买入金额来获取对应的汇率。可选提供结算日期以查询特定值日的汇率。

## 官方文档

- [query_rates 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/query_rates)

## 请求地址

`POST /api/open/v1/fx/rates/query`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-04-02T12:08:56+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `rateConditions` | array[RateCondition] | Yes | 汇率查询条件列表，支持最多 50 个元素 |
| `settlementDate` | string | No | 结算日期（YYYY-MM-DD 格式，UTC），固定 10 字符，用于查询特定结算日的汇率 |

### RateCondition Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `sellAmount` | Amount | No | 卖出金额，与 buyAmount 至少提供一个，且只能有一侧包含 value |
| `buyAmount` | Amount | No | 买入金额，与 sellAmount 至少提供一个，且只能有一侧包含 value |

### Amount Object

| Field | Type | Description |
|-------|------|-------------|
| `currency` | string | 币种代码（ISO-4217 三字母代码） |
| `value` | integer | 金额值，最小货币单位（如 USD 100.00 → value = 10000） |

### 请求示例

```json
{
  "rateConditions": [
    {
      "sellAmount": { "currency": "USD", "value": 10000 },
      "buyAmount": { "currency": "HKD" }
    }
  ]
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `settlementDate` | string | 结算日期（YYYY-MM-DD 格式，UTC），当请求中提供 settlementDate 时返回 |
| `rateDetails` | array[RateDetail] | 汇率查询结果列表，最多 100 个元素 |

### RateDetail Object

| Field | Type | Description |
|-------|------|-------------|
| `sellAmount` | Amount | 卖出金额 |
| `buyAmount` | Amount | 买入金额 |
| `clientRate` | decimal | 客户端汇率，8 位小数 |
| `createdAt` | datetime | 汇率创建时间（ISO 8601 格式） |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "rateDetails": [
    {
      "sellAmount": { "currency": "USD", "value": 10000 },
      "buyAmount": { "currency": "HKD", "value": 77850 },
      "clientRate": 7.78500000,
      "createdAt": "2026-04-02T12:08:56Z"
    }
  ]
}
```

## 错误码

| resultCode | resultStatus | 说明 |
|------------|--------------|------|
| `SUCCESS` | S | 查询成功 |
| `UNKNOWN_EXCEPTION` | U | 未知异常，联系技术支持 |
| `PARAM_ILLEGAL` | F | 参数非法，检查必填字段和格式 |
| `USER_NOT_EXIST` | F | 用户不存在或未完成 KYC 验证 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权关系不存在 |
| `CURRENCY_NOT_SUPPORTED` | F | 指定的币种不支持 |
| `FX_SETTLEMENT_DATE_NOT_SUPPORTED` | F | 该货币对不支持此结算日期 |
| `FX_CURRENCY_PAIR_RISK_BREAK` | F | 货币对触发风控 |

## 示例代码

参考 [references/foreign-exchange/quote/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
quote/java/
├── service/
│   └── QuoteService.java                              # 薄封装 Service，包含 queryRates 方法
└── model/
    ├── domain/
    │   ├── RateCondition.java                         # 汇率查询条件
    │   └── RateDetail.java                            # 汇率详情（用于响应）
    ├── request/
    │   └── QueryRatesRequest.java                     # 请求参数（rateConditions, settlementDate）
    └── response/
        └── QueryRatesResponse.java                    # 响应结果（result, settlementDate, rateDetails）
```

## 集成使用方式

QuoteService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `QueryRatesRequest` 设置查询条件列表
2. 调用 `QuoteService.queryRates(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理汇率详情

### 业务代码示例

```java
// 构造请求
QueryRatesRequest request = new QueryRatesRequest();
List<RateCondition> conditions = new ArrayList<>();

RateCondition condition1 = new RateCondition();
condition1.setSellAmount(new Amount("USD", 10000L));
condition1.setBuyAmount(new Amount("HKD", null));
conditions.add(condition1);

RateCondition condition2 = new RateCondition();
condition2.setSellAmount(new Amount("EUR", 5000L));
condition2.setBuyAmount(new Amount("GBP", null));
conditions.add(condition2);

request.setRateConditions(conditions);

// 可选：设置结算日期
request.setSettlementDate("2026-04-05");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
QueryRatesResponse response = quoteService.queryRates(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    // 处理汇率详情
    for (RateDetail detail : response.getRateDetails()) {
        System.out.println("卖出: " + detail.getSellAmount().getValue() + " " + detail.getSellAmount().getCurrency());
        System.out.println("买入: " + detail.getBuyAmount().getValue() + " " + detail.getBuyAmount().getCurrency());
        System.out.println("汇率: " + detail.getClientRate());
        System.out.println("创建时间: " + detail.getCreatedAt());
        System.out.println("---");
    }
    
    // 如果请求中提供了 settlementDate，响应中也会返回
    if (response.getSettlementDate() != null) {
        System.out.println("结算日期: " + response.getSettlementDate());
    }
} else {
    // 处理错误
    System.err.println("查询失败: " + response.getResult().getResultMessage());
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 QuoteService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new QuoteService(config)` 创建实例。
