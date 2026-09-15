# Query a Deal 接口接入指引

## 接口说明

通过交易 ID 检索特定交易的完整详情。响应包含所有可用的交易信息，部分字段根据交易类型和当前状态有条件返回。用于跟踪交易进度、检查结算状态或获取远期交易的保证金详情。

## 官方文档

- [query_a_deal 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/query_a_deal)

## 请求地址

`POST /api/open/v1/fx/deals/query`

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
| `id` | string | Yes | 要查询的交易 ID，必须是当前账户创建的有效交易 |

### 请求示例

```json
{
  "id": "DEAL20260402001"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `id` | string | 交易唯一标识符 |
| `dealType` | string | 交易类型：SPOT、UNFUNDED_SPOT、FORWARD |
| `quote` | DealQuote | 报价信息 |
| `sellAmount` | Amount | 卖出金额 |
| `buyAmount` | Amount | 买入金额 |
| `creditSupport` | CreditSupport | 信用支持信息，仅当 `dealType` 为 FORWARD 时返回 |
| `settlementPeriod` | SettlementPeriod | 结算周期信息，当 `dealType` 为 UNFUNDED_SPOT 或 FORWARD 时返回 |
| `unSettleBuyAmount` | Amount | 未结算买入金额，当 `dealType` 为 UNFUNDED_SPOT/FORWARD 且 `status` 为 PROCESSING/CANCELED 时返回 |
| `unSettleSellAmount` | Amount | 未结算卖出金额，当 `dealType` 为 UNFUNDED_SPOT/FORWARD 且 `status` 为 PROCESSING/CANCELED 时返回 |
| `cancelFeeAmount` | Amount | 取消费用金额，当 `status` 为 CANCELED 时返回 |
| `status` | string | 当前交易状态 |
| `createdAt` | datetime | 交易创建时间（ISO 8601 格式） |
| `settledAt` | datetime | 交易结算时间（ISO 8601 格式），仅结算后返回 |
| `cancelledAt` | datetime | 交易取消时间（ISO 8601 格式），仅取消后返回 |
| `failedAt` | datetime | 交易失败时间（ISO 8601 格式），仅 `status` 为 FAILED 时返回 |
| `failureCode` | string | 失败代码，仅 `status` 为 FAILED 时返回 |
| `failureMessage` | string | 失败描述信息，仅 `status` 为 FAILED 时返回 |

### DealQuote Object

| Field | Type | Description |
|-------|------|-------------|
| `quoteId` | string | 报价唯一标识符 |
| `currencyPair` | string | 货币对，格式为 BASE/QUOTE（如 GBP/EUR） |
| `clientRate` | decimal | 客户端汇率，8 位小数 |
| `effectiveAt` | datetime | 报价生效时间（ISO 8601 格式） |
| `expiresAt` | datetime | 报价过期时间（ISO 8601 格式） |

### 响应示例

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "id": "DEAL20260402001",
  "dealType": "UNFUNDED_SPOT",
  "quote": {
    "quoteId": "QT202604021208001",
    "currencyPair": "GBP/EUR",
    "clientRate": "1.16200000",
    "effectiveAt": "2026-04-02T04:09:30Z",
    "expiresAt": "2026-04-02T04:10:30Z"
  },
  "sellAmount": {
    "currency": "GBP",
    "value": 5000
  },
  "buyAmount": {
    "currency": "EUR",
    "value": 5810
  },
  "settlementPeriod": {
    "endDate": "2026-04-04"
  },
  "status": "SUCCESS",
  "createdAt": "2026-04-02T04:09:35Z",
  "settledAt": "2026-04-02T06:09:35Z"
}
```

## 错误码

| resultCode | resultStatus | 说明 | Troubleshooting |
|------------|--------------|------|-----------------|
| `SUCCESS` | S | 查询成功 | — |
| `UNKNOWN_EXCEPTION` | U | 未知异常 | 携带交易 ID 联系技术支持 |
| `PARAM_ILLEGAL` | F | 参数非法 | 检查 `id` 字段是否有效且格式正确 |
| `USER_NOT_EXIST` | F | 用户不存在 | 验证 API 凭据和商户账户 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权不存在 | 确保 API Key 具有 FX 交易权限 |
| `FUND_ORDER_NOT_EXIST` | F | 交易不存在 | 验证交易 ID 是否正确且属于当前账户 |

## 示例代码

参考 [references/foreign-exchange/deal/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
deal/java/
├── service/
│   └── DealService.java                               # 薄封装 Service，包含 queryDeal 方法
└── model/
    ├── domain/
    │   └── DealQuote.java                             # 交易关联的报价信息
    ├── request/
    │   └── QueryDealRequest.java                      # 请求参数（id）
    └── response/
        └── QueryDealResponse.java                     # 响应结果（result, id, dealType, quote, amounts, status 等）
```

## 集成使用方式

DealService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `QueryDealRequest` 设置交易 `id`
2. 调用 `DealService.queryDeal(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理交易详情

### 业务代码示例

```java
// 构造请求
QueryDealRequest request = new QueryDealRequest();
request.setId("DEAL20260402001");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
QueryDealResponse response = dealService.queryDeal(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("交易 ID: " + response.getId());
    System.out.println("交易类型: " + response.getDealType());
    System.out.println("交易状态: " + response.getStatus());
    System.out.println("创建时间: " + response.getCreatedAt());

    // 报价信息
    DealQuote quote = response.getQuote();
    System.out.println("货币对: " + quote.getCurrencyPair());
    System.out.println("汇率: " + quote.getClientRate());

    // 金额信息
    System.out.println("卖出: " + response.getSellAmount().getValue() + " " + response.getSellAmount().getCurrency());
    System.out.println("买入: " + response.getBuyAmount().getValue() + " " + response.getBuyAmount().getCurrency());

    // 根据状态处理不同逻辑
    switch (response.getStatus()) {
        case "PROCESSING":
            System.out.println("交易处理中，等待结算");
            if (response.getUnSettleSellAmount() != null) {
                System.out.println("未结算卖出: " + response.getUnSettleSellAmount().getValue());
            }
            break;
        case "SUCCESS":
            System.out.println("交易已结算: " + response.getSettledAt());
            break;
        case "CANCELED":
            System.out.println("交易已取消: " + response.getCancelledAt());
            if (response.getCancelFeeAmount() != null) {
                System.out.println("取消费用: " + response.getCancelFeeAmount().getValue());
            }
            break;
        case "FAILED":
            System.out.println("交易失败: " + response.getFailureCode());
            System.out.println("失败原因: " + response.getFailureMessage());
            break;
    }

    // FORWARD 交易查看保证金信息
    if ("FORWARD".equals(response.getDealType()) && response.getCreditSupport() != null) {
        CreditSupport cs = response.getCreditSupport();
        System.out.println("保证金币种: " + cs.getMarginCurrency());
        System.out.println("保证金金额: " + cs.getMarginAmount().getValue());
        System.out.println("信用额度: " + cs.getCreditLineAmount().getValue());
    }

    // 查看结算周期
    if (response.getSettlementPeriod() != null) {
        SettlementPeriod sp = response.getSettlementPeriod();
        System.out.println("结算截止: " + sp.getEndDate());
        if (sp.getStartDate() != null) {
            System.out.println("结算开始: " + sp.getStartDate());
        }
    }
} else {
    System.err.println("查询失败: " + response.getResult().getResultMessage());
}
```

### 交易状态轮询示例

```java
// 创建交易后轮询状态直到结算完成
String dealId = createDealResponse.getId();
QueryDealRequest queryRequest = new QueryDealRequest();
queryRequest.setId(dealId);

int maxRetries = 60;
int intervalSeconds = 10;

for (int i = 0; i < maxRetries; i++) {
    QueryDealResponse queryResponse = dealService.queryDeal(queryRequest);

    if ("S".equals(queryResponse.getResult().getResultStatus())) {
        String status = queryResponse.getStatus();
        System.out.println("当前状态: " + status);

        if ("SUCCESS".equals(status)) {
            System.out.println("交易已结算: " + queryResponse.getSettledAt());
            break;
        } else if ("CANCELED".equals(status) || "FAILED".equals(status)) {
            System.out.println("交易终止: " + status);
            break;
        }
        // PROCESSING 状态继续轮询
    }

    Thread.sleep(intervalSeconds * 1000L);
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 DealService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new DealService(config)` 创建实例。
