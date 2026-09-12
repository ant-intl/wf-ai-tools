# Query Available Settlement Quota 接口接入指引

## 接口说明

查询指定币种和累积方式下的剩余 CNY 结算额度。响应按累积标识和币种拆分可用额度明细，帮助在发起付款前判断是否有足够的结算额度。

## 官方文档

- [query_available_settlement_quota 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/query_available_settlement_quota)

## 请求地址

`POST /api/open/v1/tradeOrders/queryQuota`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-05-08T14:30:00+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `currency` | string | Yes | 查询币种，ISO 4217 三字母代码（如 `USD`、`EUR`） |
| `quotaAccumulationId` | string | Yes | 与 quotaAccumulationMethod 配对的累积标识 |
| `quotaAccumulationMethod` | string | Conditional | 额度累积方式（QuotaAccumulationMethod 枚举），传入 `USER_ID` 时可不传 |
| `tradeCategory` | string | Conditional | 贸易类别过滤：`GOODS` 或 `SERVICE`，仅在查询受益人级别额度时必填 |

### 请求示例

```json
{
  "currency": "USD",
  "quotaAccumulationMethod": "BENEFICIARY_ID",
  "quotaAccumulationId": "RA_****",
  "tradeCategory": "GOODS"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `availableQuotas` | array | 可用额度明细，按累积标识拆分，最多 100 条 |
| `createdAt` | datetime | 额度快照时间戳（ISO 8601 扩展格式） |

### AvailableQuotaEntry Object

| Field | Type | Description |
|-------|------|-------------|
| `quotaAccumulationId` | string | 该额度条目的累积标识 |
| `availableQuota` | Amount | 以请求币种汇总的总可用额度 |
| `availableQuotaByCurrency` | array | 按币种拆分的可用额度明细 |

### QuotaByCurrency Object

| Field | Type | Description |
|-------|------|-------------|
| `value` | integer | 额度金额，最小货币单位（如 USD 的分） |
| `currency` | string | 币种代码，ISO 4217 三字母代码 |

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
  "createdAt": "2026-05-08T14:30:00+08:00",
  "availableQuotas": [
    {
      "quotaAccumulationId": "RA_****",
      "availableQuota": { "value": 100000, "currency": "USD" },
      "availableQuotaByCurrency": [
        { "value": 100000, "currency": "USD" }
      ]
    }
  ]
}
```

## 错误码

| resultCode | resultStatus | 说明 |
|------------|--------------|------|
| `SUCCESS` | S | 查询成功 |
| `PARAM_ILLEGAL` | F | 参数非法（如 currency 格式错误、quotaAccumulationId 为空），不可重试 |
| `ACCOUNT_NOT_EXIST` | F | 账户不存在，不可重试 |
| `CONTRACT_CHECK_FAIL` | F | 合约校验失败，不可重试 |
| `PROCESS_FAIL` | F | 业务处理失败，不可重试 |
| `INVOKE_ICIF_FAIL` | F | 内部服务调用失败，可重试 |
| `UNKNOWN_EXCEPTION` | U | 未知异常，可重试 |

## 示例代码

参考 [references/supporting-service/tradeOrders/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
tradeOrders/java/
├── service/
│   └── TradeOrderService.java                       # 薄封装 Service，包含 queryQuota 方法
└── model/
    ├── domain/
    │   ├── AvailableQuotaEntry.java                 # 可用额度条目
    │   └── QuotaByCurrency.java                     # 按币种拆分的额度
    ├── request/
    │   └── QueryQuotaRequest.java                   # 请求参数（currency, quotaAccumulationId, quotaAccumulationMethod, tradeCategory）
    └── response/
        └── QueryQuotaResponse.java                  # 响应结果（result + availableQuotas + createdAt）
```

## 集成使用方式

TradeOrderService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `QueryQuotaRequest` 设置查询条件
2. 调用 `TradeOrderService.queryQuota(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理可用额度

### 业务代码示例

```java
// 构造请求
QueryQuotaRequest request = new QueryQuotaRequest();
request.setCurrency("USD");
request.setQuotaAccumulationMethod("BENEFICIARY_ID");
request.setQuotaAccumulationId("RA_****");
request.setTradeCategory("GOODS");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
QueryQuotaResponse response = tradeOrderService.queryQuota(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("额度快照时间: " + response.getCreatedAt());

    for (AvailableQuotaEntry entry : response.getAvailableQuotas()) {
        System.out.println("累积标识: " + entry.getQuotaAccumulationId());
        System.out.println("总可用额度: " + entry.getAvailableQuota().getValue()
            + " " + entry.getAvailableQuota().getCurrency());

        if (entry.getAvailableQuotaByCurrency() != null) {
            for (QuotaByCurrency quota : entry.getAvailableQuotaByCurrency()) {
                System.out.println("  币种: " + quota.getCurrency()
                    + ", 额度: " + quota.getValue());
            }
        }
    }
} else {
    System.err.println("查询失败: " + response.getResult().getResultMessage());
}
```

### 付款前额度检查示例

```java
/**
 * 检查指定累积标识下是否有足够的结算额度。
 *
 * @param accumulationId 累积标识
 * @param requiredAmount 所需额度（最小货币单位）
 * @param currency       币种
 * @return 是否有足够额度
 */
public boolean hasSufficientQuota(String accumulationId, long requiredAmount, String currency) {
    QueryQuotaRequest request = new QueryQuotaRequest();
    request.setCurrency(currency);
    request.setQuotaAccumulationId(accumulationId);

    QueryQuotaResponse response = tradeOrderService.queryQuota(request);
    if (!"S".equals(response.getResult().getResultStatus())) {
        return false;
    }

    if (response.getAvailableQuotas() == null) {
        return false;
    }

    for (AvailableQuotaEntry entry : response.getAvailableQuotas()) {
        if (accumulationId.equals(entry.getQuotaAccumulationId())
                && entry.getAvailableQuota() != null
                && entry.getAvailableQuota().getValue() >= requiredAmount) {
            return true;
        }
    }
    return false;
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 TradeOrderService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new TradeOrderService(config)` 创建实例。
