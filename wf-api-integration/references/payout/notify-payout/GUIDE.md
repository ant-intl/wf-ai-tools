# notifyPayout 回调接入指引

## 接口说明

万里汇使用此接口向集成商通知转账的结果。

集成商需要在调用 createPayout 接口时配置 `transferToDetail.transferNotifyUrl` 字段来设置接收通知的地址。

这是一个**入站回调**（WF → 集成商），与其他出站 API 不同，需要：
1. 接收 WF 的 HTTP 请求并**验签**
2. 处理业务逻辑
3. 构建响应并**签名**后返回

在收到通知后，集成商需正确发送响应。若不向 WF 发送响应信息，WF 会重新发送最多 **7 次**请求通知。

## 入站请求（WF → 集成商）

### 请求头

| Header | Description |
|--------|-------------|
| `Client-Id` | WF client identifier |
| `Signature` | `algorithm=RSA256, keyVersion=2, signature=*****`，集成商需验签 |
| `Content-Type` | `application/json; charset=UTF-8` |
| `Request-Time` | ISO 8601，如 `2019-04-04T12:08:56+08:00` |

### 请求参数

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `transferResult` | Result | **Yes** | 交易请求结果 |
| `transferRequestId` | String | **Yes** | 集成商定义的幂等请求 ID，最大 64 字符 |
| `transferId` | String | **Yes** | 万里汇定义的交易唯一标识，最大 64 字符 |
| `transferFinishTime` | String | **Yes** | 交易结束时间，ISO 8601 格式 |
| `chargeMode` | String | No | 计收费模式：`INNER_DEDUCT`（内扣）/ `OUTER_DEDUCT`（外扣） |
| `transferFromDetail` | TransferFromDetail | **Yes** | 支付方转账详情 |
| `transferToDetail` | TransferToDetail | **Yes** | 收款方转账详情 |

### TransferResult

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `resultStatus` | String | **Yes** | `S` — 成功；`F` — 失败 |
| `resultCode` | String | **Yes** | 结果码，如 `SUCCESS`、`PROCESS_FAIL` 等 |
| `resultMessage` | String | No | 结果描述 |

### transferResult.resultCode 取值

| 结果码 | resultStatus | 说明 |
|--------|-------------|------|
| `SUCCESS` | S | 转账成功 |
| `PROCESS_FAIL` | F | 业务失败，不可重试 |
| `USER_ACCOUNT_ABNORMAL` | F | 用户账户状态异常 |
| `AMOUNT_EXCEED_LIMIT` | F | 金额超限 |
| `RISK_REJECT` | F | 风控拒绝 |
| `ORDER_NOT_FOUND` | F | 订单未找到 |
| `ORDER_IS_CLOSED` | F | 订单已关闭 |
| `ORDER_IS_REVERSED` | F | 订单已冲正 |
| `CARD_INFO_NOT_MATCH` | F | 卡信息不匹配 |
| `BALANCE_NOT_ENOUGH` | F | 余额不足 |

### TransferFromDetail

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `transferFromAmount` | Amount | **Yes** | 支付方金额 |
| `transferFromMethod` | TransferFromMethod | No | 支付方转账方式 |

### TransferToDetail

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `transferToAmount` | Amount | **Yes** | 收款方金额 |
| `transferToMethod` | TransferToMethod | **Yes** | 收款方转账方式 |
| `transferQuote` | TransferQuote | No | 汇率报价信息 |
| `purposeCode` | String | No | 转账用途代码 |
| `transferNotifyUrl` | String | No | 异步通知回调 URL |
| `feeAmount` | Amount | No | 手续费金额 |

## 出站响应（集成商 → WF）

返回前**必须签名**。

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `result.resultCode` | String | **Yes** | `SUCCESS` / `UNKNOWN_EXCEPTION` / `PROCESS_FAIL` |
| `result.resultStatus` | String | **Yes** | `S` / `F` / `U` |
| `result.resultMessage` | String | No | 结果描述 |

## 处理流程

1. 从请求头提取 Signature 并调用 WfSigner.verifySignature() **验签**
2. 验签失败 → 返回 HTTP 400，不返回 SUCCESS
3. 解析 NotifyPayoutRequest
4. 以 transferRequestId 做**幂等判断**，已处理则直接返回 SUCCESS
5. **异步处理**转账结果业务逻辑
6. 构建 NotifyPayoutResponse（resultCode=SUCCESS）
7. 调用 WfSigner 对响应体签名，写入响应头 Signature
8. 返回响应

> 签名验证完成后立即返回 SUCCESS，业务逻辑异步处理，避免超时触发重试。

## WF 重试策略

未收到有效响应时，WF 重试 7 次，间隔：2 min → 10 min → 10 min → 1h → 2h → 6h → 15h。

## 错误码

| Code | resultStatus | Description |
|------|-------------|-------------|
| `SUCCESS` | S | 处理成功 |
| `PROCESS_FAIL` | F | 业务失败，WF 不再重试 |
| `UNKNOWN_EXCEPTION` | U | 未知异常，WF 将重试 |

## 示例

### 请求体示例（WF → 集成商）

```json
{
  "transferResult": {
    "resultCode": "SUCCESS",
    "resultMessage": "success",
    "resultStatus": "S"
  },
  "transferRequestId": "20103419388723400*****",
  "transferId": "20240101000000000001",
  "transferFinishTime": "2024-01-01T12:00:00+08:00",
  "chargeMode": "INNER_DEDUCT",
  "transferFromDetail": {
    "transferFromAmount": {
      "currency": "USD",
      "value": 10000
    }
  },
  "transferToDetail": {
    "transferToAmount": {
      "currency": "USD",
      "value": 9900
    },
    "transferToMethod": {
      "paymentMethodType": "BANK_ACCOUNT_DETAIL"
    },
    "feeAmount": {
      "currency": "USD",
      "value": 100
    }
  }
}
```

### 响应体示例（集成商 → WF）

```json
{
  "result": {
    "resultCode": "SUCCESS",
    "resultMessage": "success.",
    "resultStatus": "S"
  }
}
```

## 示例代码

参考同目录下 `java/` 和 `golang/` 中的模板代码。

### Java 模板结构

```
java/
├── controller/NotifyPayoutController.java  ← Spring @RestController
└── model/
    ├── request/NotifyPayoutRequest.java     ← 入站请求
    └── response/NotifyPayoutResponse.java   ← 出站响应
```

### Golang 模板结构

```
golang/
├── controller/
│   └── notify_payout_controller.go  ← http.Handler 实现
└── model/
    ├── request/notify_payout_request.go
    └── response/notify_payout_response.go
```
