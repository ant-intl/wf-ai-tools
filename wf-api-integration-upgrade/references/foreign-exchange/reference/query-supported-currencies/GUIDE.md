# Query Supported Currencies 接口接入指引

## 接口说明

查询 FX 交易支持的货币对列表，可按交易类型过滤，用于在创建报价前确认目标货币对是否可用。

## 官方文档

- [query_supported_currencies 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/query_supported_currencies)

## 请求地址

`POST /api/open/v1/fx/references/queryCurrencies`

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
| `dealType` | string | Yes | 按交易类型过滤，仅返回该交易类型支持的货币对。有效值：`SPOT`（即期）、`UNFUNDED_SPOT`（无资金即期）、`FORWARD`（远期） |

### 请求示例

```json
{
  "dealType": "FORWARD"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `currencyPairs` | array[CurrencyPair] | 支持的货币对列表 |

### CurrencyPair Object

| Field | Type | Description |
|-------|------|-------------|
| `sellCurrency` | string | 卖出币种，ISO 4217 三字母代码 |
| `buyCurrency` | string | 买入币种，ISO 4217 三字母代码 |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "currencyPairs": [
    { "sellCurrency": "USD", "buyCurrency": "HKD" },
    { "sellCurrency": "USD", "buyCurrency": "JPY" },
    { "sellCurrency": "GBP", "buyCurrency": "EUR" }
  ]
}
```

## 错误码

| resultCode | resultStatus | 说明 | Troubleshooting |
|------------|--------------|------|-----------------|
| `SUCCESS` | S | 查询成功 | — |
| `PARAM_ILLEGAL` | F | 参数非法 | 检查 `dealType` 是否为 SPOT/UNFUNDED_SPOT/FORWARD 之一 |
| `USER_NOT_EXIST` | F | 用户不存在 | 验证 API 凭据和商户账户 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权不存在 | 确保 API Key 具有 FX 参考数据查询权限 |
| `UNKNOWN_EXCEPTION` | U | 未知异常，可重试 | 稍后重试，若问题持续联系技术支持 |

## 示例代码

参考 [references/foreign-exchange/reference/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
reference/java/
├── service/
│   └── ReferenceService.java                          # 薄封装 Service，包含 querySupportedCurrencies 方法
└── model/
    ├── domain/
    │   └── CurrencyPair.java                          # 支持的货币对（用于响应的 currencyPairs）
    ├── request/
    │   └── QuerySupportedCurrenciesRequest.java        # 请求参数（dealType）
    └── response/
        └── QuerySupportedCurrenciesResponse.java       # 响应结果（result, currencyPairs）
```

> `Result` 为通用对象，复用 `model/response` 包下的已有定义。

## 集成使用方式

ReferenceService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `QuerySupportedCurrenciesRequest` 设置 `dealType`
2. 调用 `ReferenceService.querySupportedCurrencies(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，遍历 `currencyPairs`

### 业务代码示例

```java
// 构造请求
QuerySupportedCurrenciesRequest request = new QuerySupportedCurrenciesRequest();
request.setDealType("FORWARD");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
QuerySupportedCurrenciesResponse response = referenceService.querySupportedCurrencies(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    for (CurrencyPair pair : response.getCurrencyPairs()) {
        System.out.println("货币对: " + pair.getSellCurrency() + "/" + pair.getBuyCurrency());
    }
} else {
    System.err.println("查询失败: " + response.getResult().getResultCode()
        + " - " + response.getResult().getResultMessage());
}
```

### 报价前币种对可用性校验示例

```java
// 创建报价前，先确认目标货币对在指定交易类型下是否支持
QuerySupportedCurrenciesRequest request = new QuerySupportedCurrenciesRequest();
request.setDealType("FORWARD");

QuerySupportedCurrenciesResponse response = referenceService.querySupportedCurrencies(request);

if ("S".equals(response.getResult().getResultStatus())) {
    boolean supported = response.getCurrencyPairs().stream()
        .anyMatch(p -> "USD".equals(p.getSellCurrency()) && "HKD".equals(p.getBuyCurrency()));

    if (supported) {
        // 继续调用 QuoteService.createQuote 创建报价
    } else {
        System.out.println("FORWARD 交易不支持 USD/HKD，请更换币种对或交易类型");
    }
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 ReferenceService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new ReferenceService(config)` 创建实例。
