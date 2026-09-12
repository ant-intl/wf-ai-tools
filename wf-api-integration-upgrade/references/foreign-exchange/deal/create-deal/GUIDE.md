# Create a Deal 接口接入指引

## 接口说明

使用从 Quote API 获取的有效报价执行 FX 交易。交易类型（SPOT、UNFUNDED_SPOT、FORWARD）由所使用的报价决定。SPOT 交易自动结算；UNFUNDED_SPOT 和 FORWARD 交易需要通过 Settlement API 显式结算。FORWARD 交易额外需要保证金管理。

## 官方文档

- [create_a_deal 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/create_a_deal)

## 请求地址

`POST /api/open/v1/fx/deals/create`

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
| `requestId` | string | Yes | 幂等 ID，用于防止重复提交，每次创建交易请求必须唯一 |
| `quoteId` | string | Yes | 报价 ID，从 Quote API 获取，报价必须在创建交易时有效且未过期 |

### 请求示例

```json
{
  "requestId": "REQ20260402001",
  "quoteId": "QT202604021208001"
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
| `creditSupport` | CreditSupport | 信用支持信息，仅 FORWARD 交易返回 |
| `settlementPeriod` | SettlementPeriod | 结算周期信息，UNFUNDED_SPOT 和 FORWARD 交易返回 |
| `unSettleBuyAmount` | Amount | 未结算买入金额，UNFUNDED_SPOT/FORWARD 且状态为 PROCESSING/CANCELED 时返回 |
| `unSettleSellAmount` | Amount | 未结算卖出金额，UNFUNDED_SPOT/FORWARD 且状态为 PROCESSING/CANCELED 时返回 |
| `cancelFeeAmount` | Amount | 取消费用金额，当 status 为 CANCELED 时返回 |
| `status` | string | 当前交易状态 |
| `createdAt` | datetime | 交易创建时间（ISO 8601 格式） |
| `settledAt` | datetime | 交易结算时间（ISO 8601 格式），仅结算后返回 |
| `cancelledAt` | datetime | 交易取消时间（ISO 8601 格式），仅取消后返回 |
| `failedAt` | datetime | 交易失败时间（ISO 8601 格式），仅 status 为 FAILED 时返回 |
| `failureCode` | string | 失败代码，仅 status 为 FAILED 时返回 |
| `failureMessage` | string | 失败描述信息，仅 status 为 FAILED 时返回 |

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
  "status": "PROCESSING",
  "createdAt": "2026-04-02T04:09:35Z"
}
```

## 错误码

| resultCode | resultStatus | 说明 | Troubleshooting |
|------------|--------------|------|-----------------|
| `SUCCESS` | S | 创建成功 | — |
| `UNKNOWN_EXCEPTION` | U | 未知异常 | 携带 `requestId` 联系技术支持 |
| `PROCESS_FAIL` | F | 处理失败 | 重试请求，若问题持续请确认报价是否仍然有效 |
| `PARAM_ILLEGAL` | F | 参数非法 | 检查 `requestId` 和 `quoteId` 是否有效且格式正确 |
| `USER_NOT_EXIST` | F | 用户不存在 | 验证 API 凭据和商户账户 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权不存在 | 确保 API Key 具有 FX 交易权限 |
| `FX_QUOTE_EXPIRED` | F | 报价已过期 | 从 Quote API 获取新报价后重试 |
| `FX_FORWARD_LEI_CHECK_FAILED` | F | 远期 LEI 检查失败 | 确保已注册有效的 Legal Entity Identifier (LEI) |
| `FX_DEAL_INITIAL_MARGIN_FREEZE_FAILED` | F | 初始保证金冻结失败 | 确保账户有足够资金用于保证金冻结后重试 |

## 示例代码

参考 [references/foreign-exchange/deal/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
deal/java/
├── service/
│   └── DealService.java                               # 薄封装 Service，包含 createDeal 方法
└── model/
    ├── domain/
    │   └── DealQuote.java                             # 交易关联的报价信息
    ├── request/
    │   └── CreateDealRequest.java                     # 请求参数（requestId, quoteId）
    └── response/
        └── CreateDealResponse.java                    # 响应结果（result, id, dealType, quote, amounts, status 等）
```

## 集成使用方式

DealService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 从 Quote API 获取有效报价（`quoteId`）
2. 构造 `CreateDealRequest` 设置 `requestId`（幂等 ID）和 `quoteId`
3. 调用 `DealService.createDeal(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
4. 检查 `result.resultStatus` 是否为 `S`，获取交易 ID 和状态
5. 根据交易类型处理后续逻辑：SPOT 自动结算，UNFUNDED_SPOT/FORWARD 需显式结算

### 业务代码示例

```java
// 构造请求
CreateDealRequest request = new CreateDealRequest();
request.setRequestId("REQ20260402001");
request.setQuoteId("QT202604021208001");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
CreateDealResponse response = dealService.createDeal(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("交易 ID: " + response.getId());
    System.out.println("交易类型: " + response.getDealType());
    System.out.println("状态: " + response.getStatus());
    System.out.println("创建时间: " + response.getCreatedAt());

    // 报价信息
    DealQuote quote = response.getQuote();
    System.out.println("货币对: " + quote.getCurrencyPair());
    System.out.println("汇率: " + quote.getClientRate());

    // 金额信息
    System.out.println("卖出: " + response.getSellAmount().getValue() + " " + response.getSellAmount().getCurrency());
    System.out.println("买入: " + response.getBuyAmount().getValue() + " " + response.getBuyAmount().getCurrency());

    // 保存交易 ID 用于后续查询/取消/结算
    String dealId = response.getId();

    // 根据交易类型处理后续逻辑
    switch (response.getDealType()) {
        case "SPOT":
            // SPOT 交易自动结算，无需额外操作
            System.out.println("SPOT 交易将自动结算");
            break;
        case "UNFUNDED_SPOT":
            // 需要通过 Settlement API 显式结算
            System.out.println("结算截止: " + response.getSettlementPeriod().getEndDate());
            break;
        case "FORWARD":
            // 需要保证金管理 + 显式结算
            CreditSupport cs = response.getCreditSupport();
            System.out.println("保证金: " + cs.getMarginAmount().getValue() + " " + cs.getMarginCurrency());
            break;
    }
} else {
    System.err.println("创建交易失败: " + response.getResult().getResultMessage());
}
```

### 幂等性保障

```java
// requestId 用于防止重复提交，相同 requestId 的重复请求返回相同结果
String requestId = UUID.randomUUID().toString();

CreateDealRequest request = new CreateDealRequest();
request.setRequestId(requestId);
request.setQuoteId("QT202604021208001");

// 首次调用
CreateDealResponse response1 = dealService.createDeal(request);

// 网络超时后重试（相同 requestId），返回相同结果
CreateDealResponse response2 = dealService.createDeal(request);
// response2.getId() == response1.getId()
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 DealService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new DealService(config)` 创建实例。
