# notifyTradeOrder 回调接入指引

## 接口说明

WF 在处理完成后主动 POST 到集成商的 `notifyUrl`。**仅适用于 PAY_INTO_CHINA 场景**。

这是一个**入站回调**（WF → 集成商），与其他出站 API 不同，需要：
1. 接收 WF 的 HTTP 请求并**验签**
2. 处理业务逻辑
3. 构建响应并**签名**后返回

## 入站请求（WF → 集成商）

### 请求头

| Header | Description |
|--------|-------------|
| `Client-Id` | WF client identifier |
| `Signature` | WF 签名，集成商需验签 |
| `Content-Type` | `application/json; charset=UTF-8` |
| `Request-Time` | ISO 8601 |

### 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `requestId` | String | **Yes** | 与 submitTradeOrder 相同的 requestId |
| `tradeOrderResults` | List\<TradeOrderResult\> | **Yes** | 各笔订单结果 |

### TradeOrderResult

| Field | Type | Description |
|-------|------|-------------|
| `referenceOrderNo` | String | 交易订单编号 |
| `orderStatus` | String | `REJECT`/`AVAILABLE`/`ACCEPT`/`PARTIAL_DECLARED` |
| `orderType` | String | `LOAN`/`REFUND` |
| `statusMessage` | String | 状态详情 |
| `transAmount` | Amount | 交易金额 |
| `tradeAmount` | Amount | 贸易金额 |
| `remainAmount` | Amount | 可用结汇额度 |

## 出站响应（集成商 → WF）

返回前**必须签名**。

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `result.resultCode` | String | **Yes** | `SUCCESS` / `UNKNOWN_EXCEPTION` / `PROCESS_FAIL` |
| `result.resultMessage` | String | **Yes** | 结果描述 |

## 处理流程

1. 从请求头提取 Signature 并调用 WfSigner.verifySignature() **验签**
2. 验签失败 → 返回 HTTP 400，不返回 SUCCESS
3. 解析 NotifyTradeOrderRequest
4. 以 requestId 做**幂等判断**，已处理则直接返回 SUCCESS
5. **异步处理** tradeOrderResults 业务逻辑
6. 构建 NotifyTradeOrderResponse（resultCode=SUCCESS）
7. 调用 WfSigner 对响应体签名，写入响应头 Signature
8. 返回响应

> 签名验证完成后立即返回 SUCCESS，业务逻辑异步处理，避免超时触发重试。

## WF 重试策略

未收到有效响应时，WF 重试 7 次，间隔：2 min → 10 min → 10 min → 1h → 2h → 6h → 15h。

## 示例代码

参考同目录下 `java/` 和 `golang/` 中的模板代码。

### Java 模板结构

```
java/
├── controller/NotifyTradeOrderController.java  ← Spring @RestController
└── model/
    ├── request/NotifyTradeOrderRequest.java    ← 入站请求
    └── response/NotifyTradeOrderResponse.java  ← 出站响应
```

### Golang 模板结构

```
golang/
└── model/
    ├── request/notify_trade_order_request.go
    └── response/notify_trade_order_response.go
```
