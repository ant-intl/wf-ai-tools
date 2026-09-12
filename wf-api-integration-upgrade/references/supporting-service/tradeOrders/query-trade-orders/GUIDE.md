# Query Trade Orders 接口接入指引

## 接口说明

查询已提交贸易订单批次的处理状态和结果。使用 Submit Trade Orders 返回的批次 `id` 获取每笔订单的最终处理结果，包括订单状态、剩余额度和失败详情。

## 官方文档

- [query_trade_orders 官方文档](https://docs.worldfirst.com/wfdocs/api-sdk/query_trade_orders)

## 请求地址

`POST /api/open/v1/tradeOrders/query`

## 请求头

| Header | Required | Description |
|--------|----------|-------------|
| `Content-Type` | Yes | `application/json; charset=UTF-8` |
| `Client-Id` | Yes | WF client identifier |
| `Request-Time` | Yes | ISO 8601，e.g. `2026-04-29T10:00:00+08:00` |
| `Signature` | Yes | `algorithm=RSA256, keyVersion=2, signature=<urlEncodedBase64>` |

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | string | Yes | 批次标识，使用 submit 接口返回的 `id` |

### 请求示例

```json
{
  "id": "20260429****"
}
```

## 响应参数

| Field | Type | Description |
|-------|------|-------------|
| `result` | Result | API 调用结果（`resultStatus` S/F/U、`resultCode`、`resultMessage`） |
| `id` | string | 批次标识 |
| `batchRequestId` | string | 提交时提供的幂等键回显 |
| `status` | string | 批次处理状态（BatchStatus 枚举） |
| `tradeOrders` | array | 每笔订单的处理结果，批次处理完成后返回 |
| `createdAt` | datetime | 批次创建时间（ISO 8601 扩展格式） |
| `completedAt` | datetime | 批次处理完成时间，status 为 COMPLETED 时返回 |

### TradeOrderResult Object

| Field | Type | Description |
|-------|------|-------------|
| `referenceOrderNo` | string | 商户分配的订单号回显 |
| `orderStatus` | string | 单笔订单处理状态（OrderStatus 枚举） |
| `statusMessage` | string | 订单状态描述 |
| `failureCode` | string | 订单级错误码，orderStatus 为 REJECTED 时返回 |
| `failureMessage` | string | 订单级错误描述，orderStatus 为 REJECTED 时返回 |
| `tradeAmount` | Amount | 申报结算金额回显 |
| `transAmount` | Amount | 实际交易金额回显 |
| `remainingAmount` | Amount | 该订单剩余可申报额度，仅在 query 响应中返回 |
| `orderDirection` | string | 资金方向回显：CREDIT 或 DEBIT |

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
  "id": "20260429****",
  "batchRequestId": "req_20260429001",
  "status": "COMPLETED",
  "tradeOrders": [
    {
      "referenceOrderNo": "ORD_20260429001",
      "orderStatus": "AVAILABLE",
      "statusMessage": "Quota accumulated successfully",
      "tradeAmount": { "currency": "USD", "value": 9500 },
      "transAmount": { "currency": "USD", "value": 10000 },
      "remainingAmount": { "currency": "USD", "value": 5000 },
      "orderDirection": "CREDIT"
    }
  ],
  "createdAt": "2026-04-29T10:00:00+08:00",
  "completedAt": "2026-04-29T11:00:00+08:00"
}
```

## 错误码

| resultCode | resultStatus | 说明 |
|------------|--------------|------|
| `SUCCESS` | S | 查询成功 |
| `PARAM_ILLEGAL` | F | id 无效，不可重试 |
| `ID_NOT_FOUND` | F | 批次 ID 不存在，不可重试 |
| `ACCOUNT_NOT_EXIST` | F | 账户不存在，不可重试 |
| `CONTRACT_CHECK_FAIL` | F | 合约校验失败，不可重试 |
| `UNKNOWN_EXCEPTION` | U | 未知异常，可重试 |

## 示例代码

参考 [references/supporting-service/tradeOrders/java/](../java/) 中的参考实现代码。

### Java 模板结构

```
tradeOrders/java/
├── service/
│   └── TradeOrderService.java                       # 薄封装 Service，包含 queryTradeOrders 方法
└── model/
    ├── domain/
    │   └── TradeOrderResult.java                    # 贸易订单处理结果
    ├── request/
    │   └── QueryTradeOrdersRequest.java             # 请求参数（id）
    └── response/
        └── QueryTradeOrdersResponse.java            # 响应结果（result + 批次信息 + 订单结果列表）
```

## 集成使用方式

TradeOrderService 采用薄封装模式：不做参数校验，验签后自动反序列化响应体并直接返回业务响应对象。验签失败时抛出 `WfException`（错误码 `INVALID_SIGNATURE`），调用方无需手动检查验签结果或解析响应体。

### 调用流程

1. 构造 `QueryTradeOrdersRequest` 设置批次 ID
2. 调用 `TradeOrderService.queryTradeOrders(request)` 获取响应（内部已强制验签，验签失败抛 `WfException`）
3. 检查 `result.resultStatus` 是否为 `S`，处理批次和订单结果
4. 若 `status` 为 `PROCESSING`，可稍后重试查询

### 业务代码示例

```java
// 构造请求
QueryTradeOrdersRequest request = new QueryTradeOrdersRequest();
request.setId("20260429****");

// 调用 Service（内部已强制验签，验签失败抛 WfException）
QueryTradeOrdersResponse response = tradeOrderService.queryTradeOrders(request);

// 检查业务结果
if ("S".equals(response.getResult().getResultStatus())) {
    System.out.println("批次 ID: " + response.getId());
    System.out.println("批次状态: " + response.getStatus());
    System.out.println("幂等键: " + response.getBatchRequestId());

    if ("COMPLETED".equals(response.getStatus())) {
        System.out.println("处理完成时间: " + response.getCompletedAt());
        for (TradeOrderResult order : response.getTradeOrders()) {
            System.out.println("订单号: " + order.getReferenceOrderNo());
            System.out.println("  状态: " + order.getOrderStatus());
            System.out.println("  描述: " + order.getStatusMessage());

            if (order.getRemainingAmount() != null) {
                System.out.println("  剩余额度: " + order.getRemainingAmount().getValue()
                    + " " + order.getRemainingAmount().getCurrency());
            }

            if ("REJECTED".equals(order.getOrderStatus())) {
                System.err.println("  错误码: " + order.getFailureCode());
                System.err.println("  错误信息: " + order.getFailureMessage());
            }
        }
    } else {
        System.out.println("批次仍在处理中，请稍后重试");
    }
} else {
    System.err.println("查询失败: " + response.getResult().getResultMessage());
}
```

### 轮询查询完整示例

```java
String batchId = "20260429****";
int maxRetries = 10;
int intervalMs = 5000;

for (int i = 0; i < maxRetries; i++) {
    QueryTradeOrdersRequest request = new QueryTradeOrdersRequest();
    request.setId(batchId);

    QueryTradeOrdersResponse response = tradeOrderService.queryTradeOrders(request);
    if (!"S".equals(response.getResult().getResultStatus())) {
        throw new RuntimeException("查询失败: " + response.getResult().getResultMessage());
    }

    if ("COMPLETED".equals(response.getStatus())) {
        System.out.println("批次处理完成，共 " + response.getTradeOrders().size() + " 笔订单");
        for (TradeOrderResult order : response.getTradeOrders()) {
            System.out.println(order.getReferenceOrderNo() + ": " + order.getOrderStatus());
        }
        break;
    }

    System.out.println("批次状态: " + response.getStatus() + "，等待 " + intervalMs + "ms 后重试...");
    Thread.sleep(intervalMs);
}
```

> **Spring Boot 项目**：通过 `@Autowired` 注入 TradeOrderService，无需手动构建配置。
>
> **普通 Java 项目**：先使用 Builder 构建 WfClientConfig，再 `new TradeOrderService(config)` 创建实例。
