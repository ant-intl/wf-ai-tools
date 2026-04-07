# inquiryTradeOrder 接口接入指引

## 接口说明

查询 submitTradeOrder 的上传处理结果。**仅适用于 PAY_INTO_CHINA 场景**。

## 请求地址

`POST /amsin/api/v1/business/account/inquiryTradeOrder`

## 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `requestId` | String | **Yes** | 与 submitTradeOrder 相同的 requestId，max 64 chars |
| `sceneCode` | String | **Yes** | 固定值：`PAY_INTO_CHINA` |
| `quotaAccumulationMethod` | String | **Yes** | `USER_ID` / `RECEIVING_ACCOUNT` / `VIRTUAL_ACCOUNT` / `BENEFICIARY` |
| `quotaAccumulationId` | String | **Yes** | 与上述方式对应的 ID |
| `tradeType` | String | **Yes** | `GOODS` 或 `SERVICE` |

## 响应参数

| Field | Type | Condition | Description |
|-------|------|-----------|-------------|
| `result` | Object | Always | `resultStatus` (S/F/U), `resultCode`, `resultMessage` |
| `requestId` | String | Always | 回显请求号 |
| `batchStatus` | String | Always | `PROCESSING`（处理中）或 `FINISHED`（处理完成）|
| `tradeOrderResults` | List\<TradeOrderResult\> | batchStatus=FINISHED | 各笔订单结果 |

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

## 轮询策略

```
1. 使用相同 requestId 调用 inquiryTradeOrder
2. batchStatus = PROCESSING → 等待后重试
3. batchStatus = FINISHED → 处理 tradeOrderResults
```

建议轮询间隔：5/10/20 min。

## 错误码

### API Level (result.resultCode)

| Code | Handling |
|------|----------|
| `UNKNOWN_EXCEPTION` | Retry (7x, 5/10/20/40/80/160/320 min) |
| `PARAM_ILLEGAL` | 修改参数，更换 requestId |
| `ORDER_NOT_EXIST` | 确认 requestId 与已提交的订单匹配 |
| `USER_NOT_EXIST` / `ACCOUNT_NOT_EXIST` | 检查 quotaAccumulationId |
| `INVALID_CLIENT` / `CONTRACT_CHECK_FAIL` | 检查配置，勿盲目重试 |

### Order Level (tradeOrderResult.errorCode)

| Code | Handling |
|------|----------|
| `PARAM_ILLEGAL` | 检查提交时的订单数据 |
| `TRADE_ORDER_SUBMITTED` | 该订单已提交，停止重试 |
| `RISK_REJECT` | 联系 WF 支持 |
| `PROCESS_FAIL` | 联系 WF 支持，勿重试 |

## 示例代码

参考同目录下 `java/` 和 `golang/` 中的模板代码。

注意：inquiryTradeOrder 的方法集成在 TradeOrderManagementClient 中（与 submitTradeOrder 共用一个客户端类），客户端代码位于 `../submit-trade-order/java/client/TradeOrderManagementClient.java`。

### Java 模板结构

```
java/
└── model/
    ├── request/InquiryTradeOrderRequest.java
    └── response/InquiryTradeOrderResponse.java
```

### Golang 模板结构

```
golang/
└── model/
    ├── request/inquiry_trade_order_request.go
    └── response/inquiry_trade_order_response.go
```

## 测试方法

| 方法 | 说明 |
|------|------|
| `testInquiryTradeOrder` | 使用 submitTradeOrder 的 requestId 查询批次状态 |
