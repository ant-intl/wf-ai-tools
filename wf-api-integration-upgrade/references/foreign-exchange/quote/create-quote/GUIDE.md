# Create a Quote 接口接入指引

## 接口说明

生成具有约束力的 FX 报价，锁定汇率用于后续交易创建。返回的报价包含报价 ID、汇率和过期时间。支持即期（SPOT）、无资金即期（UNFUNDED_SPOT）和远期（FORWARD）三种交易类型。

## 官方文档

- [create_a_quote 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_quote)

## 请求地址

`POST /api/open/v1/fx/quotes/create`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-04-02T04:08:56+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|--------|-------------|
| `sellAmount` | Amount | Yes | 卖出金额，与 buyAmount 至少提供一个，且只能有一侧包含 value |
| `buyAmount` | Amount | Yes | 买入金额，与 sellAmount 至少提供一个，且只能有一侧包含 value |
| `dealType` | string | Yes | 交易类型：SPOT、UNFUNDED_SPOT、FORWARD |
| `creditSupport` | CreditSupport | No | 信用支持信息，当 dealType 为 FORWARD 时必填 |
| `settlementPeriod` | SettlementPeriod | No | 结算周期，SPOT 禁止使用；UNFUNDED_SPOT 和 FORWARD 必填 |

### Amount Object

| Field | Type | Description |
|-------|------|-------------|
| `currency` | string | 币种代码（ISO-4217 三字母代码） |
| `value` | integer | 金额值，最小货币单位（如 USD 100.00 → value = 10000） |

### CreditSupport Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `marginCurrency` | string | Yes | 保证金币种（ISO 4217 标准，固定 3 字符） |
| `marginAmount` | Amount | Yes | 保证金金额 |
| `creditLineAmount` | Amount | Yes | 信用额度 |
| `creditLineBaseAmount` | Amount | Yes | 信用额度基准金额 |

### SettlementPeriod Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `startDate` | string | No | 结算开始日期（YYYY-MM-DD 格式，UTC），UNFUNDED_SPOT 禁止使用；FORWARD 且 mode 为 WINDOWED 时必填 |
| `endDate` | string | Yes | 结算结束日期（YYYY-MM-DD 格式，UTC），UNFUNDED_SPOT 和 FORWARD 必填 |
| `mode` | string | No | 结算日期类型：FIXED（固定）、FLEXIBLE（灵活）、WINDOWED（窗口） |

### 请求示例

#### SPOT 交易

```json
{
  "sellAmount": { "currency": "USD", "value": 10000 },
  "buyAmount": { "currency": "HKD" },
  "dealType": "SPOT"
}
```

#### UNFUNDED_SPOT 交易

```json
{
  "sellAmount": { "currency": "USD", "value": 10000 },
  "buyAmount": { "currency": "HKD" },
  "dealType": "UNFUNDED_SPOT",
  "settlementPeriod": {
    "endDate": "2026-04-05",
    "mode": "FIXED"
  }
}
```

#### FORWARD 交易

```json
{
  "sellAmount": { "currency": "USD", "value": 10000 },
  "buyAmount": { "currency": "HKD" },
  "dealType": "FORWARD",
  "creditSupport": {
    "marginCurrency": "USD",
    "marginAmount": { "currency": "USD", "value": 1000 },
    "creditLineAmount": { "currency": "USD", "value": 50000 },
    "creditLineBaseAmount": { "currency": "USD", "value": 100000 }
  },
  "settlementPeriod": {
    "startDate": "2026-05-01",
    "endDate": "2026-05-31",
    "mode": "WINDOWED"
  }
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `quote` | QuoteDetail | 报价详情 |
| `sellAmount` | Amount | 卖出金额 |
| `buyAmount` | Amount | 买入金额 |
| `dealType` | string | 交易类型 |
| `creditSupport` | CreditSupport | 信用支持信息，当 dealType 为 FORWARD 时返回 |
| `settlementPeriod` | SettlementPeriod | 结算周期信息 |

### QuoteDetail Object

| Field | Type | Description |
|-------|------|-------------|
| `quoteId` | string | 报价唯一标识符，使用此 ID 进行后续交易创建 |
| `currencyPair` | string | 货币对，格式为 BASE/QUOTE（如 USD/HKD），固定 7 字符 |
| `clientRate` | decimal | 报价汇率，8 位小数 |
| `effectiveAt` | datetime | 报价生效时间（ISO 8601 格式） |
| `expiresAt` | datetime | 报价过期时间（ISO 8601 格式），必须在此时间前接受报价 |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "quote": {
    "quoteId": "QT20260402120****",
    "currencyPair": "USD/HKD",
    "clientRate": 7.78500000,
    "effectiveAt": "2026-04-02T04:08:56Z",
    "expiresAt": "2026-04-02T04:09:56Z"
  },
  "sellAmount": { "currency": "USD", "value": 10000 },
  "buyAmount": { "currency": "HKD", "value": 77850 },
  "dealType": "SPOT"
}
```

## 错误码

| resultCode | resultStatus | 说明 |
|------------|--------------|------|
| `SUCCESS` | S | 创建成功 |
| `UNKNOWN_EXCEPTION` | U | 未知异常，联系技术支持 |
| `PARAM_ILLEGAL` | F | 参数非法，检查必填字段和交易类型约束 |
| `USER_NOT_EXIST` | F | 用户不存在或未完成 KYC 验证 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权关系不存在 |
| `CURRENCY_NOT_SUPPORTED` | F | 指定的币种不支持 |
| `FX_FORWARD_LEI_CHECK_FAILED` | F | 远期交易的 LEI 检查失败 |
| `FX_DEAL_QUOTA_NOT_ENOUGH` | F | 交易额度不足 |
| `FX_CURRENCY_PAIR_RISK_BREAK` | F | 货币对触发风控 |

## 示例代码

参考 [references/foreign-exchange/quote/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
quote/java/
├── service/
│   └── QuoteService.java                              # 薄封装 Service，包含 createQuote 方法
└── model/
    ├── domain/
    │   ├── CreditSupport.java                         # 信用支持信息（保证金相关）
    │   ├── SettlementPeriod.java                      # 结算周期信息
    │   └── QuoteDetail.java                           # 报价详情（quoteId, clientRate, expiresAt 等）
    ├── request/
    │   └── CreateQuoteRequest.java                    # 请求参数（sellAmount, buyAmount, dealType, creditSupport, settlementPeriod）
    └── response/
        └── CreateQuoteResponse.java                   # 响应结果（result, quote, sellAmount, buyAmount, dealType 等）
```

## 集成使用方式

QuoteService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `CreateQuoteRequest` 设置金额、交易类型及相关参数
2. 调用 `QuoteService.createQuote(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，获取报价 ID 和汇率
4. 在报价过期前（`expiresAt`）使用 `quoteId` 创建交易

### 业务代码示例

#### SPOT 交易示例

```java
// 构造 SPOT 请求
CreateQuoteRequest request = new CreateQuoteRequest();
request.setSellAmount(new Amount("USD", 10000L));
request.setBuyAmount(new Amount("HKD", null));
request.setDealType("SPOT");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
CreateQuoteResponse response = quoteService.createQuote(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    QuoteDetail quote = response.getQuote();
    System.out.println("报价 ID: " + quote.getQuoteId());
    System.out.println("货币对: " + quote.getCurrencyPair());
    System.out.println("汇率: " + quote.getClientRate());
    System.out.println("生效时间: " + quote.getEffectiveAt());
    System.out.println("过期时间: " + quote.getExpiresAt());
    System.out.println("卖出金额: " + response.getSellAmount().getValue() + " " + response.getSellAmount().getCurrency());
    System.out.println("买入金额: " + response.getBuyAmount().getValue() + " " + response.getBuyAmount().getCurrency());
    
    // 保存 quoteId 用于后续交易创建
    String quoteId = quote.getQuoteId();
    // 注意：必须在 expiresAt 之前使用此 quoteId 创建交易
} else {
    System.err.println("创建报价失败: " + response.getResult().getResultMessage());
}
```

#### FORWARD 交易示例

```java
// 构造 FORWARD 请求
CreateQuoteRequest request = new CreateQuoteRequest();
request.setSellAmount(new Amount("USD", 10000L));
request.setBuyAmount(new Amount("HKD", null));
request.setDealType("FORWARD");

// 设置信用支持信息
CreditSupport creditSupport = new CreditSupport();
creditSupport.setMarginCurrency("USD");
creditSupport.setMarginAmount(new Amount("USD", 1000L));
creditSupport.setCreditLineAmount(new Amount("USD", 50000L));
creditSupport.setCreditLineBaseAmount(new Amount("USD", 100000L));
request.setCreditSupport(creditSupport);

// 设置结算周期
SettlementPeriod settlementPeriod = new SettlementPeriod();
settlementPeriod.setStartDate("2026-05-01");
settlementPeriod.setEndDate("2026-05-31");
settlementPeriod.setMode("WINDOWED");
request.setSettlementPeriod(settlementPeriod);

// 调用 Service
CreateQuoteResponse response = quoteService.createQuote(request);

if ("S".equals(response.getResult().getResultStatus())) {
    QuoteDetail quote = response.getQuote();
    System.out.println("远期报价 ID: " + quote.getQuoteId());
    System.out.println("汇率: " + quote.getClientRate());
    System.out.println("过期时间: " + quote.getExpiresAt());
    
    // 处理信用支持信息
    if (response.getCreditSupport() != null) {
        CreditSupport cs = response.getCreditSupport();
        System.out.println("保证金币种: " + cs.getMarginCurrency());
        System.out.println("保证金金额: " + cs.getMarginAmount().getValue());
    }
    
    // 处理结算周期
    if (response.getSettlementPeriod() != null) {
        SettlementPeriod sp = response.getSettlementPeriod();
        System.out.println("结算开始: " + sp.getStartDate());
        System.out.println("结算结束: " + sp.getEndDate());
        System.out.println("结算模式: " + sp.getMode());
    }
}
```

### 报价有效期管理

```java
// 创建报价后，检查剩余有效时间
CreateQuoteResponse response = quoteService.createQuote(request);

if ("S".equals(response.getResult().getResultStatus())) {
    QuoteDetail quote = response.getQuote();
    
    // 解析过期时间
    Instant expiresAt = Instant.parse(quote.getExpiresAt());
    Instant now = Instant.now();
    Duration remaining = Duration.between(now, expiresAt);
    
    System.out.println("报价剩余有效时间: " + remaining.getSeconds() + " 秒");
    
    // 确保有足够时间完成交易创建
    if (remaining.getSeconds() < 30) {
        System.out.println("警告: 报价即将过期，建议重新获取报价");
    } else {
        // 使用 quoteId 创建交易
        String quoteId = quote.getQuoteId();
        // 调用交易创建接口...
    }
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 QuoteService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new QuoteService(config)` 创建实例。
