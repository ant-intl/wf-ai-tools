# Cancel a Deal 接口接入指引

## 接口说明

取消尚未结算的交易。仅处于 PROCESSING 状态的交易可被取消。取消成功后交易状态变为 CANCELED，根据交易类型可能收取取消费用。响应返回更新后的交易详情，包括取消时间戳和适用费用。

## 官方文档

- [cancel_a_deal 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/cancel_a_deal)

## 请求地址

`POST /api/open/v1/fx/deals/cancel`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-04-02T04:20:00+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | string | Yes | 要取消的交易 ID，交易必须处于 PROCESSING 状态且属于当前账户 |

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
| `creditSupport` | CreditSupport | 信用支持信息，仅 FORWARD 交易返回 |
| `settlementPeriod` | SettlementPeriod | 结算周期信息，UNFUNDED_SPOT 和 FORWARD 交易返回 |
| `unSettleBuyAmount` | Amount | 未结算买入金额，取消后填充（UNFUNDED_SPOT 和 FORWARD） |
| `unSettleSellAmount` | Amount | 未结算卖出金额，取消后填充（UNFUNDED_SPOT 和 FORWARD） |
| `cancelFeeAmount` | Amount | 取消费用金额，当 `status` 为 CANCELED 时返回 |
| `status` | string | 当前交易状态，取消成功后为 CANCELED |
| `createdAt` | datetime | 交易创建时间（ISO 8601 格式） |
| `settledAt` | datetime | 交易结算时间（ISO 8601 格式），仅结算后返回 |
| `cancelledAt` | datetime | 交易取消时间（ISO 8601 格式），取消成功后填充 |
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
  "unSettleSellAmount": {
    "currency": "GBP",
    "value": 5000
  },
  "unSettleBuyAmount": {
    "currency": "EUR",
    "value": 5810
  },
  "cancelFeeAmount": {
    "currency": "GBP",
    "value": 50
  },
  "status": "CANCELED",
  "createdAt": "2026-04-02T04:09:35Z",
  "cancelledAt": "2026-04-02T04:20:00Z"
}
```

## 错误码

| resultCode | resultStatus | 说明 | Troubleshooting |
|------------|--------------|------|-----------------|
| `SUCCESS` | S | 取消成功 | — |
| `UNKNOWN_EXCEPTION` | U | 未知异常 | 携带交易 ID 联系技术支持 |
| `PARAM_ILLEGAL` | F | 参数非法 | 检查 `id` 字段是否有效且格式正确 |
| `PROCESS_FAIL` | F | 处理失败 | 重试请求，若问题持续请确认交易是否仍为 PROCESSING 状态 |
| `USER_NOT_EXIST` | F | 用户不存在 | 验证 API 凭据和商户账户 |
| `AUTHORIZATION_NOT_EXIST` | F | 授权不存在 | 确保 API Key 具有 FX 交易权限 |
| `FUND_ORDER_NOT_EXIST` | F | 交易不存在 | 验证交易 ID 是否正确且属于当前账户 |
| `FX_DEAL_CANCEL_FEE_NOT_ENOUGH` | F | 取消费用余额不足 | 确保账户有足够资金支付取消费用后重试 |

## 示例代码

参考 [references/foreign-exchange/deal/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
deal/java/
├── service/
│   └── DealService.java                               # 薄封装 Service，包含 cancelDeal 方法
└── model/
    ├── domain/
    │   └── DealQuote.java                             # 交易关联的报价信息
    ├── request/
    │   └── CancelDealRequest.java                     # 请求参数（id）
    └── response/
        └── CancelDealResponse.java                    # 响应结果（result, id, dealType, status, cancelFeeAmount 等）
```

## 集成使用方式

DealService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `CancelDealRequest` 设置交易 `id`
2. 调用 `DealService.cancelDeal(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，确认取消成功
4. 处理取消费用和未结算金额

### 业务代码示例

```java
// 构造请求
CancelDealRequest request = new CancelDealRequest();
request.setId("DEAL20260402001");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
CancelDealResponse response = dealService.cancelDeal(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("交易已取消: " + response.getId());
    System.out.println("取消时间: " + response.getCancelledAt());
    System.out.println("交易状态: " + response.getStatus());

    // 检查取消费用
    if (response.getCancelFeeAmount() != null) {
        System.out.println("取消费用: " + response.getCancelFeeAmount().getValue()
            + " " + response.getCancelFeeAmount().getCurrency());
    }

    // 检查未结算金额（UNFUNDED_SPOT 和 FORWARD）
    if (response.getUnSettleSellAmount() != null) {
        System.out.println("未结算卖出: " + response.getUnSettleSellAmount().getValue()
            + " " + response.getUnSettleSellAmount().getCurrency());
    }
    if (response.getUnSettleBuyAmount() != null) {
        System.out.println("未结算买入: " + response.getUnSettleBuyAmount().getValue()
            + " " + response.getUnSettleBuyAmount().getCurrency());
    }
} else {
    System.err.println("取消失败: " + response.getResult().getResultCode()
        + " - " + response.getResult().getResultMessage());
}
```

### 取消前状态检查示例

```java
// 建议取消前先查询交易状态，避免对不可取消的交易发起取消
QueryDealRequest queryRequest = new QueryDealRequest();
queryRequest.setId("DEAL20260402001");

QueryDealResponse queryResponse = dealService.queryDeal(queryRequest);

if ("S".equals(queryResponse.getResult().getResultStatus())) {
    String status = queryResponse.getStatus();

    if ("PROCESSING".equals(status)) {
        // 仅 PROCESSING 状态可取消
        CancelDealRequest cancelRequest = new CancelDealRequest();
        cancelRequest.setId("DEAL20260402001");

        CancelDealResponse cancelResponse = dealService.cancelDeal(cancelRequest);
        if ("S".equals(cancelResponse.getResult().getResultStatus())) {
            System.out.println("取消成功");
        }
    } else if ("SUCCESS".equals(status)) {
        System.out.println("交易已结算，无法取消");
    } else if ("CANCELED".equals(status)) {
        System.out.println("交易已被取消");
    } else if ("FAILED".equals(status)) {
        System.out.println("交易已失败，无需取消");
    }
}
```

### 取消失败重试示例

```java
// 处理取消费用不足的场景
CancelDealRequest request = new CancelDealRequest();
request.setId("DEAL20260402001");

try {
    CancelDealResponse response = dealService.cancelDeal(request);

    if ("F".equals(response.getResult().getResultStatus())
        && "FX_DEAL_CANCEL_FEE_NOT_ENOUGH".equals(response.getResult().getResultCode())) {
        System.err.println("取消费用余额不足，请充值后重试");
        // 充值逻辑...
        // 充值完成后重试
        CancelDealResponse retryResponse = dealService.cancelDeal(request);
        if ("S".equals(retryResponse.getResult().getResultStatus())) {
            System.out.println("重试取消成功");
        }
    }
} catch (WfException e) {
    System.err.println("取消请求异常: " + e.getMessage());
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 DealService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new DealService(config)` 创建实例。
